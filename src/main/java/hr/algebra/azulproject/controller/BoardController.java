package hr.algebra.azulproject.controller;

import hr.algebra.azulproject.AzulApplication;
import hr.algebra.azulproject.model.GameMove;
import hr.algebra.azulproject.model.GameState;
import hr.algebra.azulproject.model.RoleName;
import hr.algebra.azulproject.model.Tiles;
import hr.algebra.azulproject.threads.GetLastGameMoveThread;
import hr.algebra.azulproject.threads.SaveNewGameMoveThread;
import hr.algebra.azulproject.utils.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.util.Duration;

import javafx.scene.control.Label;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static hr.algebra.azulproject.AzulApplication.chatRemoteService;

public class BoardController implements Initializable, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // PLAYER 2 PLAYGROUND
    @FXML
    public transient Pane pTwoGround204, pTwoGround214, pTwoGround213, pTwoGround224, pTwoGround223, pTwoGround222,
            pTwoGround234, pTwoGround233, pTwoGround232, pTwoGround231, pTwoGround244, pTwoGround243,
            pTwoGround242, pTwoGround241, pTwoGround240;

    // PLAYER 1 PLAYGROUND
    @FXML
    public transient Pane playground04, playground14, playground13, playground24, playground23, playground22,
            playground34, playground33, playground32, playground31,
            playground44, playground43, playground42, playground41, playground40;

    // FACTORY TILES BELLOW
    @FXML
    private transient Pane factoryOneTopLeft, factoryOneTopRight, factoryOneBottomLeft, factoryOneBottomRight,
            factoryTwoTopLeft, factoryTwoTopRight, factoryTwoBottomLeft, factoryTwoBottomRight,
            factoryThreeTopLeft, factoryThreeTopRight, factoryThreeBottomLeft, factoryThreeBottomRight,
            factoryFourTopLeft, factoryFourTopRight, factoryFourBottomLeft, factoryFourBottomRight,
            factoryFiveTopLeft, factoryFiveTopRight, factoryFiveBottomLeft, factoryFiveBottomRight;

    @FXML
    private transient GridPane playerOneScoreBoard = new GridPane();
    @FXML
    private transient GridPane playerTwoScoreBoard = new GridPane();
    double storageWallOpacity = 0.2;
    private static List<Tiles> tiles;
    public static Map<String, String> storageColorsForCheck = new HashMap<>();
    public static Map<String, String> playgroundColorsForSerialization = new HashMap<>();
    public static Map<String, String> factoryPaneTilesForSerialization = new HashMap<>();
    public static String factoryPaneForReplay;
    public static List<String> playedFactoryTilesForSerialization = new ArrayList<>();
    public static final String[] IMAGE_PATHS = {
            "/images/Black.png",
            "/images/Blue.png",
            "/images/FloorLine.png",
            "/images/LBlue.png",
            "/images/Red.png",
            "/images/Yellow.png",
            "/images/floorLineMostRight.png",
            "/images/Bag.png",
            "/images/FactorySheet.png",
    };

    // PLAYER 1 STORAGE SPACE BELLOW
    @FXML
    private transient Pane storage04, storage03, storage02, storage01, storage00,
            storage14, storage13, storage12, storage11, storage10,
            storage24, storage23, storage22, storage21, storage20,
            storage34, storage33, storage32, storage31, storage30,
            storage44, storage43, storage42, storage41, storage40;

    // PLAYER 2 STORAGE SPACE BELLOW
    @FXML
    public transient Pane player2Storage04, player2Storage03, player2Storage02, player2Storage01, player2Storage00,
            player2Storage10, player2Storage11, player2Storage12, player2Storage13, player2Storage14,
            player2Storage20, player2Storage21, player2Storage22, player2Storage23, player2Storage24,
            player2Storage30, player2Storage31, player2Storage32, player2Storage33, player2Storage34,
            player2Storage40, player2Storage41, player2Storage42, player2Storage43, player2Storage44;


    @FXML
    private TextField chatMessageTextField;
    @FXML
    private TextArea chatTextArea;

    @FXML
    private Label lastGameMoveLabel;

    public BoardController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addButtonsToScoreTrack();
        initTiles();
        setTilesOnFactories();
        initBoard();
        initDraggable();

        if (AzulApplication.loggedRoleName != RoleName.SINGLE_PLAYER) {


            final Timeline timeline = new Timeline(
                    new KeyFrame(
                            Duration.millis(1000),
                            event -> {
                                List<String> chatMessages = null;
                                try {
                                    chatMessages = chatRemoteService.getAllChatMessages();
                                } catch (RemoteException e) {
                                    throw new RuntimeException(e);
                                }

                                chatTextArea.clear();

                                for (int i = chatMessages.size() - 1; i >= 0; i--) {
                                    String message = chatMessages.get(i);
                                    chatTextArea.appendText(message + "\n");
                                }
                            }
                    )
            );
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }

        GetLastGameMoveThread getLastGameMoveThread = new GetLastGameMoveThread(lastGameMoveLabel);
        Thread starterThread = new Thread(getLastGameMoveThread);
        starterThread.start();
    }

    private double startX, startY;
    private static int index = 0;
    static int turnsCounter = 2;
    static int playerOneScore = 0;
    static int playerTwoScore = 0;
    static int eventCounter = 0;
    String originalButtonStyle = "";
    public static Boolean winner = false;

    private void initDraggable() {
        List<Pane> factoryPanes = Arrays.asList(
                factoryOneTopLeft, factoryOneTopRight, factoryOneBottomLeft, factoryOneBottomRight, factoryTwoTopLeft, factoryTwoTopRight, factoryTwoBottomLeft,
                factoryTwoBottomRight, factoryThreeTopLeft, factoryThreeTopRight, factoryThreeBottomLeft, factoryThreeBottomRight, factoryFourTopLeft, factoryFourTopRight,
                factoryFourBottomLeft, factoryFourBottomRight, factoryFiveTopLeft, factoryFiveTopRight, factoryFiveBottomLeft, factoryFiveBottomRight
        );

        List<Tiles> allTiles = tiles;

        for (int i = 0; i < factoryPanes.size(); i++) {
            Pane factoryPane = factoryPanes.get(i);
            Tiles tileData = allTiles.get(i);

            // Set the Tiles object as user data for the Pane
            factoryPane.setUserData(tileData);

            // Make the Pane draggable
            makeDraggable(factoryPane);
        }
    }

    private boolean droppedOnValidLocation = false;

    private void makeDraggable(Node node) {
        node.setOnMousePressed(e -> {
            startX = e.getSceneX() - node.getTranslateX();
            startY = e.getSceneY() - node.getTranslateY();
            droppedOnValidLocation = false;
        });

        node.setOnMouseDragged(e -> {
            node.setTranslateX(e.getSceneX() - startX);
            node.setTranslateY(e.getSceneY() - startY);
        });

        // ORIGINAL ONE BELLOW

        node.setOnMouseReleased(e -> {
            // Reset the translation of the node to its original position
            node.setTranslateX(0);
            node.setTranslateY(0);

            List<Node> storages = Arrays.asList(storage04, storage03, storage02, storage01, storage00,
                    storage14, storage13, storage12, storage11, storage10,
                    storage24, storage23, storage22, storage21, storage20,
                    storage34, storage33, storage32, storage31, storage30,
                    storage44, storage43, storage42, storage41, storage40,
                    // PLAYER 2 BELLOW
                    player2Storage04, player2Storage03, player2Storage02, player2Storage01, player2Storage00,
                    player2Storage10, player2Storage11, player2Storage12, player2Storage13, player2Storage14,
                    player2Storage20, player2Storage21, player2Storage22, player2Storage23, player2Storage24,
                    player2Storage30, player2Storage31, player2Storage32, player2Storage33, player2Storage34,
                    player2Storage40, player2Storage41, player2Storage42, player2Storage43, player2Storage44);

            List<Node> playgrounds = Arrays.asList(playground04, playground14, playground13,
                    playground24, playground23, playground22,
                    playground34, playground33, playground32, playground31,
                    playground44, playground43, playground42, playground41, playground40,
                    // PLAYER 2 BELLOW
                    pTwoGround204, pTwoGround214, pTwoGround213, pTwoGround224,
                    pTwoGround223, pTwoGround222, pTwoGround234, pTwoGround233, pTwoGround232,
                    pTwoGround231, pTwoGround244, pTwoGround243, pTwoGround242, pTwoGround241,
                    pTwoGround240);

            for (Node playground : playgrounds) {
                Bounds playgroundBounds = playground.localToScene(playground.getBoundsInLocal());

                // Check if the drop coordinates are within the bounds of the current playground
                if (playgroundBounds.contains(e.getSceneX(), e.getSceneY())) {

                    double localX = e.getSceneX() - playgroundBounds.getMinX();
                    double localY = e.getSceneY() - playgroundBounds.getMinY();

                    node.setLayoutX(localX);
                    node.setLayoutY(localY);

                    try {
                        if (AzulApplication.loggedRoleName == RoleName.CLIENT && turnsCounter % 2 == 0) {
                            if (playgrounds.indexOf(playground) <= 14) {
                                if (node.getUserData() instanceof Tiles tile) {
                                    String colorWorking = node.getStyle();
                                    String color = colorWorking.substring(35, colorWorking.lastIndexOf('.'));
                                    String imagePath = Tiles.getImagePath(color);
                                    playground.setStyle("-fx-background-image: url('" + imagePath + "');");


                                    // Prepare for Serialization
                                    // Extract playground field and color, store into map
                                    String workingPlaygroundName = playground.toString();
                                    int startIndex = workingPlaygroundName.indexOf("playground");
                                    int endIndex = workingPlaygroundName.indexOf("]", startIndex);
                                    String playgroundName = workingPlaygroundName.substring(startIndex, endIndex);

                                    playedFactoryTilesForSerialization.add(node.getId());

                                    //Check did player took tile from factory, if so, remove that factory tile from map
                                    if (factoryPaneTilesForSerialization.containsKey(node.getId())) {
                                        factoryPaneTilesForSerialization.remove(node.getId());
                                    }

                                    playgroundColorsForSerialization.put(playgroundName, color);

                                    droppedOnValidLocation = true;

                                    turnsCounter++;
                                    index++;
                                    eventCounter++;

                                    // send game state to server - Networking
                                    GameState gameState = GameStateUtils.createGameState(tiles, storageColorsForCheck,
                                            playgroundColorsForSerialization, factoryPaneTilesForSerialization, playedFactoryTilesForSerialization,
                                            index, turnsCounter, playerOneScore, playerTwoScore, eventCounter, false);

                                    if (AzulApplication.loggedRoleName == RoleName.CLIENT) {
                                        NetworkingUtils.sendGameStateToServer(gameState);
                                    } else {
                                        NetworkingUtils.sendGameStateToClient(gameState);
                                    }
                                }
                            }
                        } else if (AzulApplication.loggedRoleName == RoleName.SERVER && turnsCounter % 2 != 0) {
                            if (playgrounds.indexOf(playground) > 14) {
                                if (node.getUserData() instanceof Tiles tile) {
                                    String tileCopy = ((Tiles) node.getUserData()).getColor();
                                    String colorWorking = node.getStyle();
                                    String color = colorWorking.substring(35, colorWorking.lastIndexOf('.'));
                                    String imagePath = Tiles.getImagePath(color);
                                    playground.setStyle("-fx-background-image: url('" + imagePath + "');");

                                    // Prepare for Serialization
                                    // Extract playground field and color, store into map
                                    String workingPlaygroundName = playground.toString();
                                    int startIndex = workingPlaygroundName.indexOf("pTwoGround2");
                                    int endIndex = workingPlaygroundName.indexOf("]", startIndex);
                                    String playgroundName = workingPlaygroundName.substring(startIndex, endIndex);

                                    playedFactoryTilesForSerialization.add(node.getId());

                                    //Check did player took tile from factory, if so, remove that factory tile from map
                                    if (factoryPaneTilesForSerialization.containsKey(node.getId())) {
                                        factoryPaneTilesForSerialization.remove(node.getId());
                                    }

                                    playgroundColorsForSerialization.put(playgroundName, color);

                                    droppedOnValidLocation = true;

                                    turnsCounter++;
                                    index++;
                                    eventCounter++;

                                    if (index % 20 == 0) {
                                        eventCounter = 0;
                                        CheckPlaygroundLines.checkFirstPlaygroundLines(playgrounds, storages, storageColorsForCheck, playgroundColorsForSerialization);
                                        CheckPlaygroundLines.checkSecondPlaygroundLines(playgrounds, storages, storageColorsForCheck, playgroundColorsForSerialization);
                                        CheckPlaygroundLines.checkThirdPlaygroundLines(playgrounds, storages, storageColorsForCheck, playgroundColorsForSerialization);
                                        CheckPlaygroundLines.checkFourthPlaygroundLines(playgrounds, storages, storageColorsForCheck, playgroundColorsForSerialization);
                                        CheckPlaygroundLines.checkFifthPlaygroundLines(playgrounds, storages, storageColorsForCheck, playgroundColorsForSerialization);

                                        CheckP2PlaygroundLines.checkFirstPlaygroundLines(playgrounds, storages, storageColorsForCheck, playgroundColorsForSerialization);
                                        CheckP2PlaygroundLines.checkSecondPlaygroundLines(playgrounds, storages, storageColorsForCheck, playgroundColorsForSerialization);
                                        CheckP2PlaygroundLines.checkThirdPlaygroundLines(playgrounds, storages, storageColorsForCheck, playgroundColorsForSerialization);
                                        CheckP2PlaygroundLines.checkFourthPlaygroundLines(playgrounds, storages, storageColorsForCheck, playgroundColorsForSerialization);
                                        CheckP2PlaygroundLines.checkFifthPlaygroundLines(playgrounds, storages, storageColorsForCheck, playgroundColorsForSerialization);

                                        playerTwoScore = CalculatePoints.calculateP2Scoring(storageColorsForCheck);
                                        CheckResults.displayP2Score(playerTwoScore, playerTwoScoreBoard);

                                        //Player 1 bellow

                                        playerOneScore = CalculatePoints.calculateScoring(storageColorsForCheck);
                                        CheckResults.displayP1Score(playerOneScore, playerOneScoreBoard);
                                        winner = CheckResults.checkWinner(storageColorsForCheck, playerOneScoreBoard, playerTwoScoreBoard);

                                        playedFactoryTilesForSerialization.clear();

                                        generateAdditionalTiles();

                                        // create and send game state  - Networking

                                        GameState gameState = GameStateUtils.createGameState(tiles, storageColorsForCheck,
                                                playgroundColorsForSerialization, factoryPaneTilesForSerialization, playedFactoryTilesForSerialization,
                                                index, turnsCounter, playerOneScore, playerTwoScore, eventCounter, winner);

                                        if (AzulApplication.loggedRoleName == RoleName.SERVER) {
                                            NetworkingUtils.sendGameStateToClient(gameState);
                                        } else {
                                            NetworkingUtils.sendGameStateToServer(gameState);
                                        }

                                        break;
                                    }

                                    // create and send game state  - Networking
                                    GameState gameState = GameStateUtils.createGameState(tiles, storageColorsForCheck,
                                            playgroundColorsForSerialization, factoryPaneTilesForSerialization, playedFactoryTilesForSerialization,
                                            index, turnsCounter, playerOneScore, playerTwoScore, eventCounter, false);

                                    if (AzulApplication.loggedRoleName == RoleName.SERVER) {
                                        NetworkingUtils.sendGameStateToClient(gameState);
                                    } else {
                                        NetworkingUtils.sendGameStateToServer(gameState);
                                    }
                                }
                            }
                        } else if (AzulApplication.loggedRoleName == RoleName.SINGLE_PLAYER) {

                            if (playgrounds.indexOf(playground) <= 14) {
                                if (node.getUserData() instanceof Tiles tile) {
                                    String colorWorking = node.getStyle();
                                    String color = colorWorking.substring(35, colorWorking.lastIndexOf('.'));
                                    String imagePath = Tiles.getImagePath(color);
                                    playground.setStyle("-fx-background-image: url('" + imagePath + "');");

                                    // Prepare for Serialization
                                    // Extract playground field and color, store into map
                                    String workingPlaygroundName = playground.toString();
                                    int startIndex = workingPlaygroundName.indexOf("playground");
                                    int endIndex = workingPlaygroundName.indexOf("]", startIndex);
                                    String playgroundName = workingPlaygroundName.substring(startIndex, endIndex);

                                    playedFactoryTilesForSerialization.add(node.getId());

                                    //Check did player took tile from factory, if so, remove that factory tile from map
                                    if (factoryPaneTilesForSerialization.containsKey(node.getId())) {
                                        factoryPaneForReplay = node.getId();
                                        factoryPaneTilesForSerialization.remove(node.getId());
                                    }
                                    System.out.println("FACTORY " + factoryPaneForReplay);
                                    playgroundColorsForSerialization.put(playgroundName, color);

                                    droppedOnValidLocation = true;
                                    turnsCounter++;
                                    index++;
                                    eventCounter++;

                                    GameMove gameMove = new GameMove(color, playgroundName, LocalDateTime.now(), factoryPaneForReplay);

                                    XmlUtils.saveGameMove(gameMove);

                                    SaveNewGameMoveThread saveNewGameMoveThread = new SaveNewGameMoveThread(gameMove);
                                    Thread starterThread = new Thread(saveNewGameMoveThread);
                                    starterThread.start();
                                }
                            } else {
                                if (playgrounds.indexOf(playground) > 14) {
                                    if (node.getUserData() instanceof Tiles tile) {
                                        String tileCopy = ((Tiles) node.getUserData()).getColor();
                                        String colorWorking = node.getStyle();
                                        String color = colorWorking.substring(35, colorWorking.lastIndexOf('.'));
                                        String imagePath = Tiles.getImagePath(color);
                                        playground.setStyle("-fx-background-image: url('" + imagePath + "');");

                                        // Prepare for Serialization
                                        // Extract playground field and color, store into map
                                        String workingPlaygroundName = playground.toString();
                                        int startIndex = workingPlaygroundName.indexOf("pTwoGround2");
                                        int endIndex = workingPlaygroundName.indexOf("]", startIndex);
                                        String playgroundName = workingPlaygroundName.substring(startIndex, endIndex);


                                        playedFactoryTilesForSerialization.add(node.getId());

                                        //Check did player took tile from factory, if so, remove that factory tile from map
                                        if (factoryPaneTilesForSerialization.containsKey(node.getId())) {
                                            factoryPaneForReplay = node.getId();
                                            factoryPaneTilesForSerialization.remove(node.getId());
                                        }

                                        playgroundColorsForSerialization.put(playgroundName, color);

                                        droppedOnValidLocation = true;
                                        turnsCounter++;
                                        index++;
                                        eventCounter++;

                                        if (index % 20 == 0) {
                                            eventCounter = 0;
                                            CheckPlaygroundLines.checkFirstPlaygroundLines(playgrounds, storages, storageColorsForCheck, playgroundColorsForSerialization);
                                            CheckPlaygroundLines.checkSecondPlaygroundLines(playgrounds, storages, storageColorsForCheck, playgroundColorsForSerialization);
                                            CheckPlaygroundLines.checkThirdPlaygroundLines(playgrounds, storages, storageColorsForCheck, playgroundColorsForSerialization);
                                            CheckPlaygroundLines.checkFourthPlaygroundLines(playgrounds, storages, storageColorsForCheck, playgroundColorsForSerialization);
                                            CheckPlaygroundLines.checkFifthPlaygroundLines(playgrounds, storages, storageColorsForCheck, playgroundColorsForSerialization);

                                            CheckP2PlaygroundLines.checkFirstPlaygroundLines(playgrounds, storages, storageColorsForCheck, playgroundColorsForSerialization);
                                            CheckP2PlaygroundLines.checkSecondPlaygroundLines(playgrounds, storages, storageColorsForCheck, playgroundColorsForSerialization);
                                            CheckP2PlaygroundLines.checkThirdPlaygroundLines(playgrounds, storages, storageColorsForCheck, playgroundColorsForSerialization);
                                            CheckP2PlaygroundLines.checkFourthPlaygroundLines(playgrounds, storages, storageColorsForCheck, playgroundColorsForSerialization);
                                            CheckP2PlaygroundLines.checkFifthPlaygroundLines(playgrounds, storages, storageColorsForCheck, playgroundColorsForSerialization);

                                            playerTwoScore = CalculatePoints.calculateP2Scoring(storageColorsForCheck);
                                            CheckResults.displayP2Score(playerTwoScore, playerTwoScoreBoard);

                                            //Player 1 bellow

                                            playerOneScore = CalculatePoints.calculateScoring(storageColorsForCheck);
                                            CheckResults.displayP1Score(playerOneScore, playerOneScoreBoard);
                                            CheckResults.checkWinner(storageColorsForCheck, playerOneScoreBoard, playerTwoScoreBoard);

                                            playedFactoryTilesForSerialization.clear();

                                            generateAdditionalTiles();

                                            GameMove gameMove = new GameMove(color, playgroundName, LocalDateTime.now(), factoryPaneForReplay);

                                            XmlUtils.saveGameMove(gameMove);

                                            SaveNewGameMoveThread saveNewGameMoveThread = new SaveNewGameMoveThread(gameMove);
                                            Thread starterThread = new Thread(saveNewGameMoveThread);
                                            starterThread.start();
                                            break;
                                        }

                                        GameMove gameMove = new GameMove(color, playgroundName, LocalDateTime.now(), factoryPaneForReplay);

                                        XmlUtils.saveGameMove(gameMove);

                                        SaveNewGameMoveThread saveNewGameMoveThread = new SaveNewGameMoveThread(gameMove);
                                        Thread starterThread = new Thread(saveNewGameMoveThread);
                                        starterThread.start();
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    if (droppedOnValidLocation) {
                        node.setStyle("-fx-background-color: transparent;");
                    } else {
                        // Reset node to its original position
                        node.setTranslateX(0);
                        node.setTranslateY(0);
                    }
                }
            }
        });
    }

    private void generateAdditionalTiles() {
        setTilesOnFactories();
    }

    private Button createNumberButton(String text) {
        Button button = new Button(text);
        button.setMinSize(30, 15);
        button.setText(text);

        String buttonStyle = "-fx-font-family: Arial; -fx-font-size: 10; -fx-text-fill: black;";
        button.setStyle(buttonStyle);
        originalButtonStyle = button.getStyle();


        if (Integer.parseInt(text) % 5 == 0) {
            buttonStyle += "-fx-background-color: yellow;";
            button.setStyle(buttonStyle);
        }
        button.setDisable(true);
        return button;
    }

    private void addButtonsToScoreTrack() {
        int buttonsPerRow = 20;
        int buttonsTotal = 100;
        for (int i = 1; i <= buttonsTotal; i++) {
            Button button = createNumberButton(String.valueOf(i));
            playerOneScoreBoard.add(button, (i - 1) % buttonsPerRow, (i - 1) / buttonsPerRow);

            // Clone the button to add it to playerTwoScoreBoard
            Button clonedButton = createNumberButton(String.valueOf(i));
            playerTwoScoreBoard.add(clonedButton, (i - 1) % buttonsPerRow, (i - 1) / buttonsPerRow);
        }
    }

    private void initBoard() {
        // Player 1 board bellow

        SetStorageWall.setImage(storage04, IMAGE_PATHS[3], storageWallOpacity);
        SetStorageWall.setImage(storage03, IMAGE_PATHS[0], storageWallOpacity);
        SetStorageWall.setImage(storage02, IMAGE_PATHS[4], storageWallOpacity);
        SetStorageWall.setImage(storage01, IMAGE_PATHS[5], storageWallOpacity);
        SetStorageWall.setImage(storage00, IMAGE_PATHS[1], storageWallOpacity);

        SetStorageWall.setImage(storage14, IMAGE_PATHS[0], storageWallOpacity);
        SetStorageWall.setImage(storage13, IMAGE_PATHS[4], storageWallOpacity);
        SetStorageWall.setImage(storage12, IMAGE_PATHS[5], storageWallOpacity);
        SetStorageWall.setImage(storage11, IMAGE_PATHS[1], storageWallOpacity);
        SetStorageWall.setImage(storage10, IMAGE_PATHS[3], storageWallOpacity);

        SetStorageWall.setImage(storage24, IMAGE_PATHS[4], storageWallOpacity);
        SetStorageWall.setImage(storage23, IMAGE_PATHS[5], storageWallOpacity);
        SetStorageWall.setImage(storage22, IMAGE_PATHS[1], storageWallOpacity);
        SetStorageWall.setImage(storage21, IMAGE_PATHS[3], storageWallOpacity);
        SetStorageWall.setImage(storage20, IMAGE_PATHS[0], storageWallOpacity);

        SetStorageWall.setImage(storage34, IMAGE_PATHS[5], storageWallOpacity);
        SetStorageWall.setImage(storage33, IMAGE_PATHS[1], storageWallOpacity);
        SetStorageWall.setImage(storage32, IMAGE_PATHS[3], storageWallOpacity);
        SetStorageWall.setImage(storage31, IMAGE_PATHS[0], storageWallOpacity);
        SetStorageWall.setImage(storage30, IMAGE_PATHS[4], storageWallOpacity);

        SetStorageWall.setImage(storage44, IMAGE_PATHS[1], storageWallOpacity);
        SetStorageWall.setImage(storage43, IMAGE_PATHS[3], storageWallOpacity);
        SetStorageWall.setImage(storage42, IMAGE_PATHS[0], storageWallOpacity);
        SetStorageWall.setImage(storage41, IMAGE_PATHS[4], storageWallOpacity);
        SetStorageWall.setImage(storage40, IMAGE_PATHS[5], storageWallOpacity);

        // Player 2 board bellow

        SetStorageWall.setImage(player2Storage04, IMAGE_PATHS[3], storageWallOpacity);
        SetStorageWall.setImage(player2Storage03, IMAGE_PATHS[0], storageWallOpacity);
        SetStorageWall.setImage(player2Storage02, IMAGE_PATHS[4], storageWallOpacity);
        SetStorageWall.setImage(player2Storage01, IMAGE_PATHS[5], storageWallOpacity);
        SetStorageWall.setImage(player2Storage00, IMAGE_PATHS[1], storageWallOpacity);

        SetStorageWall.setImage(player2Storage14, IMAGE_PATHS[0], storageWallOpacity);
        SetStorageWall.setImage(player2Storage13, IMAGE_PATHS[4], storageWallOpacity);
        SetStorageWall.setImage(player2Storage12, IMAGE_PATHS[5], storageWallOpacity);
        SetStorageWall.setImage(player2Storage11, IMAGE_PATHS[1], storageWallOpacity);
        SetStorageWall.setImage(player2Storage10, IMAGE_PATHS[3], storageWallOpacity);

        SetStorageWall.setImage(player2Storage24, IMAGE_PATHS[4], storageWallOpacity);
        SetStorageWall.setImage(player2Storage23, IMAGE_PATHS[5], storageWallOpacity);
        SetStorageWall.setImage(player2Storage22, IMAGE_PATHS[1], storageWallOpacity);
        SetStorageWall.setImage(player2Storage21, IMAGE_PATHS[3], storageWallOpacity);
        SetStorageWall.setImage(player2Storage20, IMAGE_PATHS[0], storageWallOpacity);

        SetStorageWall.setImage(player2Storage34, IMAGE_PATHS[5], storageWallOpacity);
        SetStorageWall.setImage(player2Storage33, IMAGE_PATHS[1], storageWallOpacity);
        SetStorageWall.setImage(player2Storage32, IMAGE_PATHS[3], storageWallOpacity);
        SetStorageWall.setImage(player2Storage31, IMAGE_PATHS[0], storageWallOpacity);
        SetStorageWall.setImage(player2Storage30, IMAGE_PATHS[4], storageWallOpacity);

        SetStorageWall.setImage(player2Storage44, IMAGE_PATHS[1], storageWallOpacity);
        SetStorageWall.setImage(player2Storage43, IMAGE_PATHS[3], storageWallOpacity);
        SetStorageWall.setImage(player2Storage42, IMAGE_PATHS[0], storageWallOpacity);
        SetStorageWall.setImage(player2Storage41, IMAGE_PATHS[4], storageWallOpacity);
        SetStorageWall.setImage(player2Storage40, IMAGE_PATHS[5], storageWallOpacity);
    }

    private void initTiles() {
        tiles = Tiles.insertTiles(500);
    }

    private int updatedTileIndex = 0;

    private void setTilesOnFactories() {
        Pane[] factoryPanes = {factoryOneTopLeft, factoryOneTopRight, factoryOneBottomLeft, factoryOneBottomRight,
                factoryTwoTopLeft, factoryTwoTopRight, factoryTwoBottomLeft, factoryTwoBottomRight,
                factoryThreeTopLeft, factoryThreeTopRight, factoryThreeBottomLeft, factoryThreeBottomRight,
                factoryFourTopLeft, factoryFourTopRight, factoryFourBottomLeft, factoryFourBottomRight,
                factoryFiveTopLeft, factoryFiveTopRight, factoryFiveBottomLeft, factoryFiveBottomRight};

        int tileIndex = 0;
        tileIndex = updatedTileIndex;

        for (Pane factoryPane : factoryPanes) {
            if (tileIndex < tiles.size()) {
                Tiles tile = tiles.get(tileIndex);
                String imagePath = Tiles.getImagePath(tile.getColor());
                factoryPane.setStyle("-fx-background-image: url('" + imagePath + "'); -fx-background-size: 100% 100%;");
                tileIndex++;
                factoryPaneTilesForSerialization.put(factoryPane.getId(), imagePath.substring(8, imagePath.lastIndexOf('.')));
            }
            updatedTileIndex = tileIndex;
        }
    }

    public void newGame() {

        addButtonsToScoreTrack();
        initTiles();
        setTilesOnFactories();
        initBoard();
        initDraggable();

        List<Node> playgrounds = Arrays.asList(playground04, playground14, playground13,
                playground24, playground23, playground22,
                playground34, playground33, playground32, playground31,
                playground44, playground43, playground42, playground41, playground40,
                // PLAYER 2 BELLOW
                pTwoGround204, pTwoGround214, pTwoGround213, pTwoGround224,
                pTwoGround223, pTwoGround222, pTwoGround234, pTwoGround233, pTwoGround232,
                pTwoGround231, pTwoGround244, pTwoGround243, pTwoGround242, pTwoGround241,
                pTwoGround240);

        for (Node playground : playgrounds) {
            clearPane((Pane) playground);
        }

        playgroundColorsForSerialization.keySet().clear();

        GameState gameState = GameStateUtils.createGameState(tiles, storageColorsForCheck,
                playgroundColorsForSerialization, factoryPaneTilesForSerialization, playedFactoryTilesForSerialization,
                index, turnsCounter, playerOneScore, playerTwoScore, eventCounter, false);

        // Commented as I need SINGLE_PLAYER option

//        if (AzulApplication.loggedRoleName == RoleName.SERVER) {
//            NetworkingUtils.sendGameStateToClient(gameState);
//        } else {
//            NetworkingUtils.sendGameStateToServer(gameState);
//        }

        for (Node playground : playgrounds) {
            clearPane((Pane) playground);
        }

    }

    private void clearPane(Pane pane) {
        pane.setStyle("-fx-background-color: transparent;");
    }

    public void saveGame() {
        FileUtils.saveGameToFile(tiles, storageColorsForCheck, playgroundColorsForSerialization,
                factoryPaneTilesForSerialization, playedFactoryTilesForSerialization,
                index, turnsCounter, playerOneScore, playerTwoScore, eventCounter, false);
        DialogUtils.printSaveSuccessfull();
    }

    // Networking bellow
    public static void updateGameBoard(GameState gameState, BoardController controller) throws IOException {

        tiles = gameState.getTiles();
        storageColorsForCheck = gameState.getStorageColorsForCheck();
        playgroundColorsForSerialization = gameState.getPlaygroundColorsForSerialization();
        factoryPaneTilesForSerialization = gameState.getFactoryPaneTilesForSerialization();
        playedFactoryTilesForSerialization = gameState.getPlayedFactoryTilesForSerialization();

        index = gameState.getIndex();
        turnsCounter = gameState.getTurnsCounter();
        playerOneScore = gameState.getPlayerOneScore();
        playerTwoScore = gameState.getPlayerTwoScore();
        eventCounter = gameState.getEventCounter();
        winner = gameState.getWinner();

        controller.setupFactories();
        controller.setupBoard();
        controller.setupScores(playerOneScore, playerTwoScore);
        controller.initDraggable();

        setupStorageTiles(storageColorsForCheck, controller);
    }

    private static void setupStorageTiles(Map<String, String> storageColorsForCheck, BoardController controller) {

        List<Node> storages = Arrays.asList(controller.storage04, controller.storage03, controller.storage02, controller.storage01, controller.storage00,
                controller.storage14, controller.storage13, controller.storage12, controller.storage11, controller.storage10,
                controller.storage24, controller.storage23, controller.storage22, controller.storage21, controller.storage20,
                controller.storage34, controller.storage33, controller.storage32, controller.storage31, controller.storage30,
                controller.storage44, controller.storage43, controller.storage42, controller.storage41, controller.storage40,
                // PLAYER 2 BELLOW
                controller.player2Storage04, controller.player2Storage03, controller.player2Storage02, controller.player2Storage01, controller.player2Storage00,
                controller.player2Storage10, controller.player2Storage11, controller.player2Storage12, controller.player2Storage13, controller.player2Storage14,
                controller.player2Storage20, controller.player2Storage21, controller.player2Storage22, controller.player2Storage23, controller.player2Storage24,
                controller.player2Storage30, controller.player2Storage31, controller.player2Storage32, controller.player2Storage33, controller.player2Storage34,
                controller.player2Storage40, controller.player2Storage41, controller.player2Storage42, controller.player2Storage43, controller.player2Storage44);

        if (!storageColorsForCheck.isEmpty()) {
            for (Map.Entry<String, String> entry : storageColorsForCheck.entrySet()) {
                String storageForCheckKey = entry.getKey();
                String color = entry.getValue();

                for (Node storage : storages) {
                    if (storage.getId().equals(storageForCheckKey.substring(storageForCheckKey.indexOf("=") + 1, storageForCheckKey.lastIndexOf("]")))) {
                        SetStorageWall.setImage((Pane) storage, "/images/" + color + ".png", 1.0);
                        break;
                    }
                }
            }
        }
    }

    public void loadGame() {

        GameState recoveredGameState = FileUtils.loadGameFromFile();

        tiles = recoveredGameState.getTiles();
        storageColorsForCheck = recoveredGameState.getStorageColorsForCheck();
        playgroundColorsForSerialization = recoveredGameState.getPlaygroundColorsForSerialization();
        factoryPaneTilesForSerialization = recoveredGameState.getFactoryPaneTilesForSerialization();
        playedFactoryTilesForSerialization = recoveredGameState.getPlayedFactoryTilesForSerialization();

        index = recoveredGameState.getIndex();
        turnsCounter = recoveredGameState.getTurnsCounter();
        playerOneScore = recoveredGameState.getPlayerOneScore();
        playerTwoScore = recoveredGameState.getPlayerTwoScore();
        eventCounter = recoveredGameState.getEventCounter();

        DialogUtils.printLoadSuccessfull();

        addButtonsToScoreTrack();
        setupFactories();
        setupScores(playerOneScore, playerTwoScore);
        setupBoard();
        initDraggable();
    }

    private void setupFactories() {

        Pane[] factoryPanes = {factoryOneTopLeft, factoryOneTopRight, factoryOneBottomLeft, factoryOneBottomRight,
                factoryTwoTopLeft, factoryTwoTopRight, factoryTwoBottomLeft, factoryTwoBottomRight,
                factoryThreeTopLeft, factoryThreeTopRight, factoryThreeBottomLeft, factoryThreeBottomRight,
                factoryFourTopLeft, factoryFourTopRight, factoryFourBottomLeft, factoryFourBottomRight,
                factoryFiveTopLeft, factoryFiveTopRight, factoryFiveBottomLeft, factoryFiveBottomRight};

        for (Pane factoryPane : factoryPanes) {
            String factoryPaneId = factoryPane.getId();
            if (factoryPaneTilesForSerialization.containsKey(factoryPaneId)) {
                String colorCode = factoryPaneTilesForSerialization.get(factoryPaneId);
                String imagePath = "/images/" + colorCode + ".png";
                factoryPane.setStyle("-fx-background-image: url('" + imagePath + "'); -fx-background-size: 100% 100%;");
            } else {
                factoryPane.setStyle("-fx-background-color: transparent;");
            }
        }
    }

    private void setupScores(int playerOneScore, int playerTwoScore) {

        // Set Scoreboard buttons to default color
        for (Node node : playerOneScoreBoard.getChildren()) {
            if (node instanceof Button button) {
                button.setStyle(originalButtonStyle);
            }
        }
        // Set Scoreboard buttons to default color
        for (Node node : playerTwoScoreBoard.getChildren()) {
            if (node instanceof Button button) {
                button.setStyle(originalButtonStyle);
            }
        }
        // Create new Scoreboard
        addButtonsToScoreTrack();

        // Bellow looping, if button number match the one which is deserialized, change color to red

        for (Node node : playerOneScoreBoard.getChildren()) {
            if (node instanceof Button button) {
                Integer buttonNumber = Integer.valueOf(button.getText());

                if (buttonNumber.equals(playerOneScore)) {
                    button.setStyle("-fx-background-color: red;");
                }
            }
        }

        // Bellow looping, if button number match the one which is deserialized, change color to red
        for (Node node : playerTwoScoreBoard.getChildren()) {
            if (node instanceof Button button) {
                Integer buttonNumber = Integer.valueOf(button.getText());

                if (buttonNumber.equals(playerTwoScore)) {
                    button.setStyle("-fx-background-color: red;");
                }
            }
        }
    }

    private void setupBoard() {
        List<Node> playgrounds = Arrays.asList(playground04, playground14, playground13,
                playground24, playground23, playground22,
                playground34, playground33, playground32, playground31,
                playground44, playground43, playground42, playground41, playground40,
                // PLAYER 2 BELLOW
                pTwoGround204, pTwoGround214, pTwoGround213, pTwoGround224,
                pTwoGround223, pTwoGround222, pTwoGround234, pTwoGround233, pTwoGround232,
                pTwoGround231, pTwoGround244, pTwoGround243, pTwoGround242, pTwoGround241,
                pTwoGround240);

        for (Node playground : playgrounds) {
            clearPane((Pane) playground);
        }

        for (Map.Entry<String, String> entry : playgroundColorsForSerialization.entrySet()) {
            String playgroundId = entry.getKey();
            String color = entry.getValue();

            // Find the corresponding playground pane based on the playgroundId
            for (Node playground : playgrounds) {
                if (playground.getId().equals(playgroundId)) {
                    String imagePath = Tiles.getImagePath(color);
                    playground.setStyle("-fx-background-image: url('" + imagePath + "'); -fx-background-size: cover;");
                }
            }
        }
        if (winner && playerOneScore > playerTwoScore) {
            DialogUtils.printWinnerMessage("CLIENT");
            Platform.exit();
        } else if (winner && playerOneScore < playerTwoScore) {
            DialogUtils.printWinnerMessage("SERVER");
            Platform.exit();
        } else if (winner && playerOneScore == playerTwoScore) {
            DialogUtils.printDrawMessage();
            Platform.exit();
        }
    }

    public void replayGame() {
        Set<GameMove> gameMoves = XmlUtils.readAllGamemoves();

        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
             AtomicInteger counter = new AtomicInteger(0);

            @Override
            public void handle(ActionEvent actionEvent) {
                GameMove gameMove = (GameMove) gameMoves.toArray()[counter.get()];
                String tileColor = gameMove.getTile();
                String tileLocation = gameMove.getPosition();
                String factoryTileLocation = gameMove.getFactoryTile();

                List<Node> playgrounds = Arrays.asList(playground04, playground14, playground13,
                        playground24, playground23, playground22,
                        playground34, playground33, playground32, playground31,
                        playground44, playground43, playground42, playground41, playground40,
                        // PLAYER 2 BELLOW
                        pTwoGround204, pTwoGround214, pTwoGround213, pTwoGround224,
                        pTwoGround223, pTwoGround222, pTwoGround234, pTwoGround233, pTwoGround232,
                        pTwoGround231, pTwoGround244, pTwoGround243, pTwoGround242, pTwoGround241,
                        pTwoGround240);

                Pane[] factoryPanes = {factoryOneTopLeft, factoryOneTopRight, factoryOneBottomLeft, factoryOneBottomRight,
                        factoryTwoTopLeft, factoryTwoTopRight, factoryTwoBottomLeft, factoryTwoBottomRight,
                        factoryThreeTopLeft, factoryThreeTopRight, factoryThreeBottomLeft, factoryThreeBottomRight,
                        factoryFourTopLeft, factoryFourTopRight, factoryFourBottomLeft, factoryFourBottomRight,
                        factoryFiveTopLeft, factoryFiveTopRight, factoryFiveBottomLeft, factoryFiveBottomRight};

                for (Node playground : playgrounds) {
                    if (playground.getId().equals(tileLocation)) {
                        String imagePath = Tiles.getImagePath(tileColor);
                        playground.setStyle("-fx-background-image: url('" + imagePath + "'); -fx-background-size: cover;");
                    }
                }
                for (Node playground : factoryPanes) {
                    if (playground.getId().equals(factoryTileLocation)) {
                        playground.setStyle("-fx-background-color: transparent;");
                    }
                }
                counter.incrementAndGet();
            }
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(gameMoves.size());
        clock.play();
    }

    public void createDocumentation() {
        Path targetPath = Path.of("/Users/antoanne/IntelliJ/AzulProject/target");
        DocumentationUtils.handleDocumentation(targetPath);
    }

    public void sendChatMessage() {
        String chatMessage = chatMessageTextField.getText();
        try {
            AzulApplication.chatRemoteService.sendChatMessage(AzulApplication.loggedRoleName + " : " + chatMessage);

            List<String> chatMessages = chatRemoteService.getAllChatMessages();
            chatTextArea.clear();
            chatMessageTextField.clear();

            for (int i = chatMessages.size() - 1; i >= 0; i--) {
                String message = chatMessages.get(i);
                chatTextArea.appendText(message + "\n");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}