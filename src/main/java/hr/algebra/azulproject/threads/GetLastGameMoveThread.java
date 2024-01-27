package hr.algebra.azulproject.threads;

import hr.algebra.azulproject.model.GameMove;
import javafx.application.Platform;
import javafx.scene.control.Label;
import java.util.Optional;

public class GetLastGameMoveThread extends GameMoveThread implements Runnable{

    private Label lastGameMoveLabel;
    public GetLastGameMoveThread(Label lastGameMoveLabel) {

        this.lastGameMoveLabel = lastGameMoveLabel;
    }

    @Override
    public void run() {

        while(true) {
            Optional<GameMove> lastGameMoveOptional = getLastGameMove();

            if(lastGameMoveOptional.isPresent()) {
                Platform.runLater(() -> {
                    lastGameMoveLabel.setText("The last game move: "
                            + lastGameMoveOptional.get().getTile() + " "
                            + lastGameMoveOptional.get().getPosition() + " "
                            + lastGameMoveOptional.get().getLocalDateTime());
                });
            }

            try{
                Thread.sleep(5000);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }
}
