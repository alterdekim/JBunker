package com.alterdekim.javabot.bot;

import com.alterdekim.javabot.entities.Synergy;
import com.alterdekim.javabot.util.Clamp;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
public class LiveFormula {

    private List<Player> playerList;
    private List<Synergy> synergies;

    private double p = 0.0d;

    public double calc() {
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
        return Clamp.clamp((i * 1.2d) + p, 0, 1);
    }

    public void sub(double p) {
        this.p -= p;
    }

    public void add(double p) {
        this.p += p;
    }

    public boolean isReproductionBonusApplied() {
        return playerList.stream().anyMatch(t -> (t.getGender().getIsMale() && !t.getHealth().getIsChildfree())) &&
                playerList.stream().anyMatch(t -> (t.getGender().getIsFemale() && !t.getHealth().getIsChildfree()));
    }

    public float getFood() {
        float food = 0;
        for( Player p : playerList ) {
            float f = p.getWork().getFoodstuffs();
            f += p.getLuggage().getFoodstuffs();
            f += p.getHobby().getFoodstuffs();
            f = f / 3.0f;
            food += f;
        }
        return food / (float) playerList.size();
    }

    public float getPower() {
        float power = 0;
        for( Player p : playerList ) {
            float f = p.getWork().getPower();
            f += p.getLuggage().getPower();
            f += p.getHobby().getPower();
            f = f / 3.0f;
            power += f;
        }
        return power / (float) playerList.size();
    }

    public float getAsocial() {
        float asocial = 0;
        for( Player p : playerList ) {
            float f = p.getWork().getAsocial();
            f += p.getLuggage().getAsocial();
            f += p.getHobby().getAsocial();
            f = f / 3.0f;
            asocial += f;
        }
        return asocial / (float) playerList.size();
    }

    public float getViolence() {
        float violence = 0;
        for( Player p : playerList ) {
            float f = p.getWork().getViolence();
            f += p.getLuggage().getViolence();
            f += p.getHobby().getViolence();
            f = f / 3.0f;
            violence += f;
        }
        return violence / (float) playerList.size();
    }

    public float getHealth() {
        float health = 0;
        for( Player p : playerList ) {
            health += p.getHealth().getHealth_index();
        }
        return health / (float) playerList.size();
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
