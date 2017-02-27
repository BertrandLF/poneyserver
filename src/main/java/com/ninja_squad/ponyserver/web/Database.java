package com.ninja_squad.ponyserver.web;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.ninja_squad.ponyserver.web.pony.Pony;
import com.ninja_squad.ponyserver.web.race.Bet;
import com.ninja_squad.ponyserver.web.race.Race;
import com.ninja_squad.ponyserver.web.race.RaceStatus;
import com.ninja_squad.ponyserver.web.user.User;
import org.springframework.stereotype.Service;

/**
 * The fake database, containing the races, bets and users.
 * @author JB Nizet
 */
@Service
public class Database {
    private List<User> users = new CopyOnWriteArrayList<>();
    private List<Race> races = new CopyOnWriteArrayList<>();

    private Map<BetKey, String> bets = new ConcurrentHashMap<>();

    private static final List<Pony> PONEYS = Arrays.asList(
            new Pony(1,"Orange Clockwork","Orange"),
            new Pony(2,"Purple Haze","Purple"),
            new Pony(3,"Yellow Submarine","Yellow"),
            new Pony(4,"Green Day","Green"),
            new Pony(5,"Blue Hotel","Blue"),
            new Pony(6,"Pink Floyd","Purple")
    );

    private static class BetKey {
        private String login;
        private Long raceId;

        private BetKey(String login, Long raceId) {
            this.login = login;
            this.raceId = raceId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            BetKey betKey = (BetKey) o;

            if (!login.equals(betKey.login)) {
                return false;
            }
            if (!raceId.equals(betKey.raceId)) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = login.hashCode();
            result = 31 * result + raceId.hashCode();
            return result;
        }
    }

    private Date twoMinutesFromNow() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, 2);
        return now.getTime();
    }

    public Database() {
        for (int i = 1; i <= 25; i++) {
            RaceStatus status =  i > 20 ? RaceStatus.FINISHED : RaceStatus.READY;
            races.add(new Race((long) i, "Course " + i, status, randomPonies(), twoMinutesFromNow()));
        }
        User bertrand = new User();
        bertrand.setFirstName("Bertrand");
        bertrand.setLastName("Le Foulgoc");
        bertrand.setLogin("blf");
        bertrand.setPassword("password");
        bertrand.setEmail("bob@sponge.com");
        bertrand.setBirthYear(1980);
        users.add(bertrand);
    }

    private static Set<Pony> randomPonies() {
        List<Pony> list = new ArrayList<>(PONEYS);
        Collections.shuffle(list);
        Set<Pony> result = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            result.add(list.get(i));
        }
        return result;
    }

    public synchronized void addUser(User user) {
        users.add(user);
    }

    public synchronized  List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public synchronized List<Race> getRaces() {
        return new ArrayList<>(races);
    }

    public synchronized Race getRace(Long raceId) {
        for (Race race : races) {
            if (race.getId().equals(raceId)) {
                return race;
            }
        }
        return null;
    }

    public synchronized void addBet(String login, Bet bet) {
        BetKey key = new BetKey(login, bet.getRaceId());
        bets.put(key, bet.getPony());
    }

    public synchronized void deleteBet(String login, Long raceId) {
        BetKey key = new BetKey(login, raceId);
        bets.remove(key);
    }

    public synchronized String getBetPony(String login, Long raceId) {
        return bets.get(new BetKey(login, raceId));
    }
}
