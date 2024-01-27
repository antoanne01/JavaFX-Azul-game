package hr.algebra.azulproject.threads;

import hr.algebra.azulproject.model.GameMove;

public class SaveNewGameMoveThread extends GameMoveThread implements Runnable{

    private GameMove gameMove;
    public SaveNewGameMoveThread(GameMove gameMove){

        this.gameMove = gameMove;
    }

    @Override
    public void run() {
        saveNewGameMove(gameMove);
    }
}
