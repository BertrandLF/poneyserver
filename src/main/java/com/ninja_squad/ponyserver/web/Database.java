package com.ninja_squad.ponyserver.web;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.ninja_squad.ponyserver.web.pony.Pony;
import com.ninja_squad.ponyserver.web.pony.PonyNames;
import com.ninja_squad.ponyserver.web.race.Bet;
import com.ninja_squad.ponyserver.web.pony.PonyColor;
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
    private static List<Pony> ponies = new CopyOnWriteArrayList<>();

    private Map<BetKey, Long> bets = new ConcurrentHashMap<>();

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

    private Date twoMinutesFromNow(int times) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, 2 * times);
        return now.getTime();
    }

    public Database() {
        for (int i = 1; i <= 25; i++) {
            RaceStatus status =  i > 20 ? RaceStatus.FINISHED : RaceStatus.READY;
            races.add(new Race((long) i, "Course " + i, status, randomPonies(), twoMinutesFromNow(i)));
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
        int index = 0;
        Set<Pony> result = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            Pony pony = new Pony(index, PonyNames.randomName(), PonyColor.randomPonyColor().name());
            result.add(pony);
            ponies.add(pony);
            index++;
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

    public synchronized Pony getPony(long ponyId) {
        for (Pony pony : ponies) {
            if (pony.getId() == ponyId) {
                return pony;
            }
        }
        return null;
    }

    public synchronized void addBet(String login, Bet bet) {
        BetKey key = new BetKey(login, bet.getRaceId());
        bets.put(key, bet.getPonyId());
    }

    public synchronized void deleteBet(String login, Long raceId) {
        BetKey key = new BetKey(login, raceId);
        bets.remove(key);
    }

    public synchronized Long getBetPony(String login, Long raceId) {
        return bets.get(new BetKey(login, raceId));
    }
}
