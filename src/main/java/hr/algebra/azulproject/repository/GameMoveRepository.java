package hr.algebra.azulproject.repository;

import hr.algebra.azulproject.model.GameMove;

import java.util.List;

public interface GameMoveRepository {

    void saveNewGameMove(GameMove gameMove);
    List<GameMove> getAllGameMoves();
}
