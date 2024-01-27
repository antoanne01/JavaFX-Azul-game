//package hr.algebra.azulproject;
//
//import hr.algebra.azulproject.chat.ChatRemoteService;
//import hr.algebra.azulproject.chat.ChatRemoteServiceImpl;
//import hr.algebra.azulproject.controller.BoardController;
//import hr.algebra.azulproject.model.GameState;
//import hr.algebra.azulproject.model.NetworkConfiguration;
//import hr.algebra.azulproject.model.RoleName;
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.rmi.NotBoundException;
//import java.rmi.RemoteException;
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
//import java.rmi.server.UnicastRemoteObject;
//
//public class AzulApplication extends Application {
//
//    public static RoleName loggedRoleName;
//    public static BoardController controller;
//    public static ChatRemoteService chatRemoteService;
//
//    @Override
//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(AzulApplication.class.getResource("view/board.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        stage.setTitle(loggedRoleName.name());
//        stage.setScene(scene);
//        stage.show();
//
//        controller = fxmlLoader.getController();
//    }
//
//    public static void main(String[] args) {
//        String inputRoleName = args[0];
//        loggedRoleName = RoleName.CLIENT;
//
//        for(RoleName rn : RoleName.values()){
//            if(rn.name().equals(inputRoleName)){
//                loggedRoleName = rn;
//                break;
//            }
//        }
//
//        // new thread listening client demands (requests)
//        new Thread(Application::launch).start();
//
//        if(loggedRoleName == RoleName.SERVER){
//            startChatService();
//            acceptRequestAsServer();
//        }
//        else{
//            startChatClient();
//            acceptRequestAsClient();
//        }
//    }
//
//    private static void startChatClient() {
//        try{
//            Registry registry = LocateRegistry.getRegistry(NetworkConfiguration.HOST, NetworkConfiguration.RMI_PORT);
//            chatRemoteService = (ChatRemoteService) registry.lookup(ChatRemoteService.REMOTE_CHAT_OBJECT_NAME);
//        }
//        catch(RemoteException | NotBoundException e){
//            e.printStackTrace();
//        }
//    }
//
//    private static void startChatService() {
//        try{
//            Registry registry = LocateRegistry.createRegistry(NetworkConfiguration.RMI_PORT);
//            chatRemoteService = new ChatRemoteServiceImpl();
//            ChatRemoteService skeleton = (ChatRemoteService) UnicastRemoteObject.exportObject(chatRemoteService, NetworkConfiguration.RANDOM_PORT_HINT);
//            registry.rebind(ChatRemoteService.REMOTE_CHAT_OBJECT_NAME, skeleton);
//            System.err.println("Registered in RMI registry");
//        }
//        catch(RemoteException e){
//            e.printStackTrace();
//        }
//    }
//
//    private static void acceptRequestAsServer(){
//        try(ServerSocket serverSocket = new ServerSocket(NetworkConfiguration.SERVER_PORT)){
//            System.err.println("listening port " + serverSocket.getLocalPort());
//
//            while(true){
//                Socket clientSocket = serverSocket.accept();
//                new Thread(() -> processSerializableClient(clientSocket)).start();
//            }
//        }
//        catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    private static void acceptRequestAsClient(){
//        try(ServerSocket serverSocket = new ServerSocket(NetworkConfiguration.CLIENT_PORT)){
//            System.err.println("listening port " + serverSocket.getLocalPort());
//
//            while(true){
//                Socket clientSocket = serverSocket.accept();
//                new Thread(() -> processSerializableClient(clientSocket)).start();
//            }
//        }
//        catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private static void processSerializableClient(Socket clientSocket) {
//        try(ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
//            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());){
//
//            GameState gameState = (GameState) ois.readObject();
//            Platform.runLater(() -> {
//                try {
//                    BoardController.updateGameBoard(gameState, controller);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//            System.out.println("Game state received");
//            oos.writeObject("Received confirmation");
//        }
//        catch (IOException | ClassNotFoundException e){
//            e.printStackTrace();
//        }
//    }
//}
//


 //bellow is JNDI

package hr.algebra.azulproject;

import hr.algebra.azulproject.chat.ChatRemoteService;
import hr.algebra.azulproject.chat.ChatRemoteServiceImpl;
import hr.algebra.azulproject.controller.BoardController;
import hr.algebra.azulproject.model.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class AzulApplication extends Application {

    public static RoleName loggedRoleName;
    public static BoardController controller;
    public static ChatRemoteService chatRemoteService;

    private static Scene mainScene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AzulApplication.class.getResource("view/board.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(loggedRoleName.name());
        stage.setScene(scene);
        stage.show();
        mainScene = scene;

        controller = fxmlLoader.getController();
    }

    public static void main(String[] args) {
        String inputRoleName = args[0];
        loggedRoleName = RoleName.CLIENT;

        for(RoleName rn : RoleName.values()){
            if(rn.name().equals(inputRoleName)){
                loggedRoleName = rn;
                break;
            }
        }

        // new thread listening client demands (requests)
        new Thread(Application::launch).start();

        if(loggedRoleName == RoleName.SERVER){
            startChatService();
            acceptRequestAsServer();
        }
        else if(loggedRoleName == RoleName.CLIENT){
            startChatClient();
            acceptRequestAsClient();
        }
    }

    private static void startChatClient() {
        try{
            Registry registry = LocateRegistry.getRegistry(ConfigurationReader.getStringValueForKey(ConfigurationKey.HOST),
                    ConfigurationReader.getIntegerValueForKey(ConfigurationKey.RMI_PORT));
            chatRemoteService = (ChatRemoteService) registry.lookup(ChatRemoteService.REMOTE_CHAT_OBJECT_NAME);
        }
        catch(RemoteException | NotBoundException e){
            e.printStackTrace();
        }
    }

    private static void startChatService() {
        try{
            Registry registry = LocateRegistry.createRegistry(ConfigurationReader.getIntegerValueForKey(ConfigurationKey.RMI_PORT));
            chatRemoteService = new ChatRemoteServiceImpl();
            ChatRemoteService skeleton = (ChatRemoteService) UnicastRemoteObject.exportObject(chatRemoteService,
                    ConfigurationReader.getIntegerValueForKey(ConfigurationKey.RANDOM_PORT_HINT));
            registry.rebind(ChatRemoteService.REMOTE_CHAT_OBJECT_NAME, skeleton);
            System.err.println("Registered in RMI registry");
        }
        catch(RemoteException e){
            e.printStackTrace();
        }
    }

    private static void acceptRequestAsServer(){
        try(ServerSocket serverSocket = new ServerSocket(ConfigurationReader.getIntegerValueForKey(ConfigurationKey.SERVER_PORT))){
            System.err.println("listening port " + serverSocket.getLocalPort());

            while(true){
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> processSerializableClient(clientSocket)).start();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void acceptRequestAsClient(){
        try(ServerSocket serverSocket = new ServerSocket(ConfigurationReader.getIntegerValueForKey(ConfigurationKey.CLIENT_PORT))){
            System.err.println("listening port " + serverSocket.getLocalPort());

            while(true){
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> processSerializableClient(clientSocket)).start();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void processSerializableClient(Socket clientSocket) {
        try(ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());){

            GameState gameState = (GameState) ois.readObject();
            //Platform.runLater(() -> BoardController.updateGameBoard(gameState, controller));
            Platform.runLater(() -> {
                try {
                    BoardController.updateGameBoard(gameState, controller);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            System.out.println("Game state received");
            oos.writeObject("Received confirmation");
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static Scene getMainScene(){
        return mainScene;
    }
}