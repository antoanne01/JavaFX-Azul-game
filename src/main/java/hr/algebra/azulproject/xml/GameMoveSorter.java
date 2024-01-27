package hr.algebra.azulproject.xml;

import hr.algebra.azulproject.model.GameMove;

import java.util.Comparator;

public class GameMoveSorter implements Comparator<GameMove> {
    @Override
    public int compare(GameMove gm1, GameMove gm2) {
        return gm1.getLocalDateTime().compareTo(gm2.getLocalDateTime());
    }
}
