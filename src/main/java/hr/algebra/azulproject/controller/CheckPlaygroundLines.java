package hr.algebra.azulproject.controller;
import hr.algebra.azulproject.utils.ClearPlaygroundResidues;
import hr.algebra.azulproject.utils.GetStorageFieldsData;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.Map;

public class CheckPlaygroundLines {
    public static void checkFirstPlaygroundLines(List<Node> playgrounds, List<Node> storages, Map<String, String> checkStorage,Map<String, String> playgroundColorsForSerialization) {
        try{
            boolean foundPlayground = false;
            for (Node playground : playgrounds) {
                if (playground.getId().contains("playground04")) {
                    String colorStyle = playground.getStyle();
                    try{
                        String colorPath = colorStyle.substring(35, colorStyle.lastIndexOf('.'));

                        String imagePath = "/images/" + colorPath + ".png";
                        String playgroundPath = playground.toString().substring(18, playground.toString().lastIndexOf(']'));

                        int playgroundValue = checkInteger(playgroundPath);
                        String pane = GetStorageFieldsData.getData(playgroundValue, colorPath);

                        for(Node storage : storages){
                            if(storage.getId().equalsIgnoreCase(pane)){
                                SetStorageWall.setImage((Pane) storage, imagePath, 1.0);
                                checkStorage.put(storage.toString(), colorPath);
                                playgroundColorsForSerialization.remove(playground.getId());
                                ClearPlaygroundResidues.clearPlayground("#FCF8F8", playground);
                            }
                        }
                        foundPlayground = true;
                        break;
                    }
                    catch(Exception e){
                        System.out.println("Error processing playground04: " + e.getMessage());
                        continue;
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("Error processing playground04: " + e.getMessage());
        }
    }
    public static void checkSecondPlaygroundLines(List<Node> playgrounds, List<Node> storages, Map<String, String> checkStorage,Map<String, String> playgroundColorsForSerialization) {
        boolean foundPlayground = false;
        for (Node playground : playgrounds) {
            if (playground.getId().contains("playground14") || playground.getId().contains("playground13")) {
                // color14 - -fx-background-image: url('/images/Blue.png');
                String color14 = playground.getStyle();
                try{
                    // colorPath14 -LBlue
                    String colorPath14 = color14.substring(35, color14.lastIndexOf('.'));

                    // imagePath14 - /images/LBlue.png
                    String imagePath14 = "/images/" + colorPath14 + ".png";

                    // playgroundPath14 -
                    String playgroundPath14 = playground.toString().substring(18, playground.toString().lastIndexOf(']'));
                    int playgroundValue14 = checkInteger(playgroundPath14);

                    // returns storage address, like storage04
                    String pane14 = GetStorageFieldsData.getData(playgroundValue14, colorPath14);

                    String color13 = null;
                    String imagePath13 = null;
                    Node pGround13 = null;
                    for (Node p : playgrounds) {
                        if (p.getId().contains("playground13")) {
                            pGround13 = p;
                            color13 = p.getStyle();
                            String colorPath13 = color13.substring(35, color13.lastIndexOf('.'));
                            imagePath13 = "/images/" + colorPath13 + ".png";
                            break;
                        }
                    }

                    if (imagePath13 != null && imagePath13.equalsIgnoreCase(imagePath14)) {
                        for (Node storage : storages) {
                            if (storage.getId().equalsIgnoreCase(pane14)) {
                                SetStorageWall.setImage((Pane) storage, imagePath14, 1.0);
                                checkStorage.put(storage.toString(), colorPath14);

                                String id = pGround13.getId();
                                int startIndex = id.indexOf("playground");
                                String playgroundName = id.substring(startIndex);

                                playgroundColorsForSerialization.remove(playground.getId());
                                playgroundColorsForSerialization.remove(playgroundName);

                                ClearPlaygroundResidues.clearPlayground("#FCF8F8", playground, pGround13);
                            }
                        }
                        foundPlayground = true;
                        break;
                    }else{
                        break;
                    }
                }catch(Exception e){
                    System.out.println("Error processing playground14, playground13 " + e.getMessage());
                    continue;
                }
            }
        }
    }

    public static void checkThirdPlaygroundLines(List<Node> playgrounds, List<Node> storages, Map<String, String> checkStorage,Map<String, String> playgroundColorsForSerialization) {

        boolean foundPlayground = false;
        for (Node playground : playgrounds) {
            if (playground.getId().contains("playground24") || playground.getId().contains("playground23")
                || playground.getId().contains("playground22")) {
                String color24 = playground.getStyle();
                try{
                    String colorPath24 = color24.substring(35, color24.lastIndexOf('.'));
                    String imagePath24 = "/images/" + colorPath24 + ".png";
                    String playgroundPath24 = playground.toString().substring(18, playground.toString().lastIndexOf(']'));
                    int playgroundValue24 = checkInteger(playgroundPath24);
                    String pane24 = GetStorageFieldsData.getData(playgroundValue24, colorPath24);

                    String color23 = null;
                    String imagePath23 = null;
                    Node pGround23 = null;
                    for (Node p : playgrounds) {
                        if (p.getId().contains("playground23")) {
                            pGround23 = p;
                            color23 = p.getStyle();
                            String colorPath23 = color23.substring(35, color23.lastIndexOf('.'));
                            imagePath23 = "/images/" + colorPath23 + ".png";
                            break;
                        }
                    }
                    String color22 = null;
                    String imagePath22 = null;
                    Node pGround22 = null;
                    for (Node p : playgrounds) {
                        if (p.getId().contains("playground22")) {
                            pGround22 = p;
                            color22 = p.getStyle();
                            String colorPath22 = color22.substring(35, color22.lastIndexOf('.'));
                            imagePath22 = "/images/" + colorPath22 + ".png";
                            break;
                        }
                    }
                    if (imagePath23 != null && imagePath23.equalsIgnoreCase(imagePath24)
                            && imagePath22.equalsIgnoreCase(imagePath24)) {
                        for (Node storage : storages) {
                            if (storage.getId().equalsIgnoreCase(pane24)) {
                                SetStorageWall.setImage((Pane) storage, imagePath24, 1.0);
                                checkStorage.put(storage.toString(), colorPath24);

                                String id23 = pGround23.getId();
                                int startIndex23 = id23.indexOf("playground");
                                String playgroundName23 = id23.substring(startIndex23);

                                String id22 = pGround22.getId();
                                int startIndex22 = id22.indexOf("playground");
                                String playgroundName22 = id22.substring(startIndex22);

                                playgroundColorsForSerialization.remove(playground.getId());
                                playgroundColorsForSerialization.remove(playgroundName23);
                                playgroundColorsForSerialization.remove(playgroundName22);


                                ClearPlaygroundResidues.clearPlayground("#FCF8F8", playground, pGround23, pGround22);
                            }
                        }
                        foundPlayground = true;
                        break;
                    }else{
                        break;
                    }
                }catch(Exception e){
                    System.out.println("Error processing ,playground24, playground23, " +
                            "playground22: " + e.getMessage());
                    continue;
                }
            }
        }
    }

    public static void checkFourthPlaygroundLines(List<Node> playgrounds, List<Node> storages, Map<String, String> checkStorage,Map<String, String> playgroundColorsForSerialization) {
        boolean foundPlayground = false;

        for (Node playground : playgrounds) {
            if (playground.getId().contains("playground34") || playground.getId().contains("playground33")
                    || playground.getId().contains("playground32") || playground.getId().contains("playground31")) {
                String color34 = playground.getStyle();
                try{
                    String colorPath34 = color34.substring(35, color34.lastIndexOf('.'));
                    String imagePath34 = "/images/" + colorPath34 + ".png";
                    String playgroundPath34 = playground.toString().substring(18, playground.toString().lastIndexOf(']'));
                    int playgroundValue34 = checkInteger(playgroundPath34);
                    String pane34 = GetStorageFieldsData.getData(playgroundValue34, colorPath34);

                    String color33 = null;
                    String imagePath33 = null;
                    Node pGround33 = null;
                    for (Node p : playgrounds) {
                        if (p.getId().contains("playground33")) {
                            pGround33 = p;
                            color33 = p.getStyle();
                            String colorPath33 = color33.substring(35, color33.lastIndexOf('.'));
                            imagePath33 = "/images/" + colorPath33 + ".png";
                            break;
                        }
                    }
                    String color32 = null;
                    String imagePath32 = null;
                    Node pGround32 = null;
                    for (Node p : playgrounds) {
                        if (p.getId().contains("playground32")) {
                            pGround32 = p;
                            color32 = p.getStyle();
                            String colorPath32 = color32.substring(35, color32.lastIndexOf('.'));
                            imagePath32 = "/images/" + colorPath32 + ".png";
                            break;
                        }
                    }
                    String color31 = null;
                    String imagePath31 = null;
                    Node pGround31 = null;
                    for (Node p : playgrounds) {
                        if (p.getId().contains("playground31")) {
                            pGround31 = p;
                            color31 = p.getStyle();
                            String colorPath31 = color31.substring(35, color31.lastIndexOf('.'));
                            imagePath31 = "/images/" + colorPath31 + ".png";
                            break;
                        }
                    }
                    if (imagePath34.equalsIgnoreCase(imagePath33)
                            && imagePath34.equalsIgnoreCase(imagePath32) &&
                            imagePath34.equalsIgnoreCase(imagePath31)) {
                        for (Node storage : storages) {
                            if (storage.getId().equalsIgnoreCase(pane34)) {
                                SetStorageWall.setImage((Pane) storage, imagePath34, 1.0);
                                checkStorage.put(storage.toString(), colorPath34);

                                String id33 = pGround33.getId();
                                int startIndex33 = id33.indexOf("playground");
                                String playgroundName33 = id33.substring(startIndex33);

                                String id32 = pGround32.getId();
                                int startIndex32 = id32.indexOf("playground");
                                String playgroundName32 = id32.substring(startIndex32);

                                String id31 = pGround31.getId();
                                int startIndex31 = id31.indexOf("playground");
                                String playgroundName31 = id31.substring(startIndex31);

                                playgroundColorsForSerialization.remove(playground.getId());
                                playgroundColorsForSerialization.remove(playgroundName33);
                                playgroundColorsForSerialization.remove(playgroundName32);
                                playgroundColorsForSerialization.remove(playgroundName31);

                                ClearPlaygroundResidues.clearPlayground("#FCF8F8", playground, pGround33, pGround32,
                                                                        pGround31);
                            }
                        }
                        foundPlayground = true;
                        break;
                    }else{
                        break;
                    }
                }catch(Exception e){
                    System.out.println("Error processing playground34, playground33," +
                            "playground32, playground31: " + e.getMessage());
                    continue;
                }
            }
        }
    }

    public static void checkFifthPlaygroundLines(List<Node> playgrounds, List<Node> storages, Map<String, String> checkStorage,Map<String, String> playgroundColorsForSerialization) {
        boolean foundPlayground = false;

        for (Node playground : playgrounds) {
            if (playground.getId().contains("playground44") || playground.getId().contains("playground43")
                    || playground.getId().contains("playground42") || playground.getId().contains("playground41") ||
                    playground.getId().contains("playground40")) {
                String color44 = playground.getStyle();
                try{
                    String colorPath44 = color44.substring(35, color44.lastIndexOf('.'));
                    String imagePath44 = "/images/" + colorPath44 + ".png";
                    String playgroundPath44 = playground.toString().substring(18, playground.toString().lastIndexOf(']'));
                    int playgroundValue44 = checkInteger(playgroundPath44);
                    String pane44 = GetStorageFieldsData.getData(playgroundValue44, colorPath44);

                    String color43 = null;
                    String imagePath43 = null;
                    Node pGround43 = null;
                    for (Node p : playgrounds) {
                        if (p.getId().contains("playground43")) {
                            pGround43 = p;
                            color43 = p.getStyle();
                            String colorPath43 = color43.substring(35, color43.lastIndexOf('.'));
                            imagePath43 = "/images/" + colorPath43 + ".png";
                            break;
                        }
                    }
                    String color42 = null;
                    String imagePath42 = null;
                    Node pGround42 = null;
                    for (Node p : playgrounds) {
                        if (p.getId().contains("playground42")) {
                            pGround42 = p;
                            color42 = p.getStyle();
                            String colorPath42 = color42.substring(35, color42.lastIndexOf('.'));
                            imagePath42 = "/images/" + colorPath42 + ".png";
                            break;
                        }
                    }
                    String color41 = null;
                    String imagePath41 = null;
                    Node pGround41 = null;
                    for (Node p : playgrounds) {
                        if (p.getId().contains("playground41")) {
                            pGround41 = p;
                            color41 = p.getStyle();
                            String colorPath41 = color41.substring(35, color41.lastIndexOf('.'));
                            imagePath41 = "/images/" + colorPath41 + ".png";
                            break;
                        }
                    }
                    String color40 = null;
                    String imagePath40 = null;
                    Node pGround40 = null;
                    for (Node p : playgrounds) {
                        if (p.getId().contains("playground40")) {
                            pGround40 = p;
                            color40 = p.getStyle();
                            String colorPath40 = color40.substring(35, color40.lastIndexOf('.'));
                            imagePath40 = "/images/" + colorPath40 + ".png";
                            break;
                        }
                    }
                    if (imagePath44.equalsIgnoreCase(imagePath43)
                            && imagePath42.equalsIgnoreCase(imagePath44) && imagePath41.equalsIgnoreCase(imagePath44)
                            && imagePath40.equalsIgnoreCase(imagePath44)) {
                        for (Node storage : storages) {
                            if (storage.getId().equalsIgnoreCase(pane44)) {
                                SetStorageWall.setImage((Pane) storage, imagePath44, 1.0);
                                checkStorage.put(storage.toString(), colorPath44);

                                String id43 = pGround43.getId();
                                int startIndex43 = id43.indexOf("playground");
                                String playgroundName43 = id43.substring(startIndex43);

                                String id42 = pGround42.getId();
                                int startIndex42 = id42.indexOf("playground");
                                String playgroundName42 = id42.substring(startIndex42);

                                String id41 = pGround41.getId();
                                int startIndex41 = id41.indexOf("playground");
                                String playgroundName41 = id41.substring(startIndex41);

                                String id40 = pGround40.getId();
                                int startIndex40 = id40.indexOf("playground");
                                String playgroundName40 = id40.substring(startIndex40);

                                playgroundColorsForSerialization.remove(playground.getId());
                                playgroundColorsForSerialization.remove(playgroundName43);
                                playgroundColorsForSerialization.remove(playgroundName42);
                                playgroundColorsForSerialization.remove(playgroundName41);
                                playgroundColorsForSerialization.remove(playgroundName40);

                                ClearPlaygroundResidues.clearPlayground("#FCF8F8", playground, pGround43, pGround42,
                                        pGround41, pGround40);
                            }
                        }
                        foundPlayground = true;
                        break;
                    }else{
                        break;
                    }
                }catch(Exception e){
                    System.out.println("Error processing playground44, playground43, " +
                            "playground42, playground41, playground40: " + e.getMessage());
                    continue;
                }
            }
        }
    }
    private static int checkInteger(String playgroundPath) {
        int playgroundValue = 0;
        try {
            playgroundValue = Integer.parseInt(playgroundPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playgroundValue;
    }
}