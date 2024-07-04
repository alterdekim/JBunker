package com.alterdekim.javabot.bot;

import com.alterdekim.javabot.entities.Synergy;
import com.alterdekim.javabot.util.Clamp;

import java.util.List;

public class LiveFormula {
    public static double calc(List<Player> playerList, List<Synergy> synergies) {
        double i = 0;
        for( Player p : playerList ) {
            double age = 1.0D - (((double) p.getAge()) / 75.0D);
            age = p.getGender().getCanDie() ? age : 1.0D;
            double gender = p.getGender().getIsMale() ? 1 : 0;
            gender += p.getGender().getIsFemale() ? 1 : 0;
            gender = p.getHealth().getIsChildfree() ? 0 : gender;
            gender *= 0.5D;
            double work = p.getWork().getValue();
            double luggage = p.getLuggage().getValue();
            luggage = p.getLuggage().getGarbage() ? 0 : luggage;
            double hobby = p.getHobby().getValue();
            double health = p.getHealth().getHealth_index().doubleValue();
            i += ((age + gender + work + luggage + hobby + health) / 6.0d);
        }
        i = i / ((double) playerList.size());
        double _i = i;
        if( playerList.stream().anyMatch(t -> (t.getGender().getIsMale() && !t.getHealth().getIsChildfree())) &&
                playerList.stream().anyMatch(t -> (t.getGender().getIsFemale() && !t.getHealth().getIsChildfree())) ) {
            i += 0.3 * _i;
        }
        for( Synergy s : synergies ) {
            Boolean fb = LiveFormula.entity(playerList, s.getFirstType(), s.getFirstEntityId());
            Boolean eb = LiveFormula.entity(playerList, s.getSecondType(), s.getSecondEntityId());
            if( fb && eb ) i += s.getProbabilityValue().doubleValue() * _i;
        }
        return Clamp.clamp(i * 1.2d, 0, 1);
    }

    private static Boolean entity(List<Player> players, SectionType ct, Long eid) {
        return switch (ct) {
            case GENDER -> LiveFormula.searchForBio(players, eid);
            case WORK -> LiveFormula.searchForWork(players, eid);
            case HOBBY -> LiveFormula.searchForHobby(players, eid);
            case HEALTH -> LiveFormula.searchForHealth(players, eid);
            case LUGGAGE -> LiveFormula.searchForLuggage(players, eid);
            default -> false;
        };
    }

    private static Boolean searchForBio(List<Player> players, Long id) {
        return players.stream().anyMatch(p -> p.getGender().getId().equals(id));
    }

    private static Boolean searchForWork(List<Player> players, Long id) {
        return players.stream().anyMatch(p -> p.getWork().getId().equals(id));
    }

    private static Boolean searchForHobby(List<Player> players, Long id) {
        return players.stream().anyMatch(p -> p.getHobby().getId().equals(id));
    }

    private static Boolean searchForHealth(List<Player> players, Long id) {
        return players.stream().anyMatch(p -> p.getHealth().getId().equals(id));
    }

    private static Boolean searchForLuggage(List<Player> players, Long id) {
        return players.stream().anyMatch(p -> p.getLuggage().getId().equals(id));
    }
}
