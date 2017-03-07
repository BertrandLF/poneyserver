package com.ninja_squad.ponyserver.web.race;

import javax.annotation.PreDestroy;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ninja_squad.ponyserver.web.pony.Pony;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Service used to start a race. Starting a race starts a thread which updates ponies' postions in a random
 * way, from 0 to 100, every second. At each second, the position can increase of 1, 2 or 3.
 *
 * @author JB Nizet
 */
@Service
public class RaceRunner {

    /**
     * The messaging template allowing to broadcast the running races.
     */
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private ExecutorService executor = Executors.newCachedThreadPool();

    public void startRace(Race race) {
        race.setStatus(RaceStatus.STARTED);
        executor.submit(new RaceCallable(race));
    }

    @PreDestroy
    public void destroy() {
        executor.shutdownNow();
    }

    private class RaceCallable implements Callable<Void> {
        private Random random = new Random();
        private Race race;

        private RaceCallable(Race race) {
            this.race = race;
        }

        @MessageMapping("/race")
        public Void call() throws Exception {

            while (race.getStatus() != RaceStatus.FINISHED) {
                movePonies();
                raceTick();
                if (race.getMaxPosition() >= 100) {
                    race.setStatus(RaceStatus.FINISHED);
                    sendRace();
                }
            }
            return null;
        }

        private void movePonies() {
            for (Pony pony : race.getPonies()) {
                pony.move(random.nextInt(5) + 1);
                if (pony.isBoosted()) {
                    pony.move(3);
                    pony.wasBoosted();
                }
            }
        }

        private void raceTick() throws InterruptedException {
            sendRace();
            Thread.sleep(1000L);
        }

        private void sendRace() {
            messagingTemplate.convertAndSend("/race/" + race.getId(), race);
        }
    }
}
