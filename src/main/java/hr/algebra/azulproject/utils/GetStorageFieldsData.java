package hr.algebra.azulproject.utils;

public class GetStorageFieldsData {
    public static String getData(int playgroundValue, String colorPath) {

        String pane = "";

        if(playgroundValue < 5){
            if(colorPath.equalsIgnoreCase("Black")){
                pane = "storage03";
            }else if(colorPath.equalsIgnoreCase("Blue")){
                pane = "storage00";
            }
            else if(colorPath.equalsIgnoreCase("LBlue")){
                pane = "storage04";
            }
            else if(colorPath.equalsIgnoreCase("Red")){
                pane = "storage02";
            }else{
                pane = "storage01";
            }
        }
        else if(playgroundValue > 5 && playgroundValue < 15){
            if(colorPath.equalsIgnoreCase("Black")){
                pane = "storage14";
            }else if(colorPath.equalsIgnoreCase("Blue")){
                pane = "storage11";
            }
            else if(colorPath.equalsIgnoreCase("LBlue")){
                pane = "storage10";
            }
            else if(colorPath.equalsIgnoreCase("Red")){
                pane = "storage13";
            }else{
                pane = "storage12";
            }
        }
        else if(playgroundValue > 20 && playgroundValue < 25){
            if(colorPath.equalsIgnoreCase("Black")){
                pane = "storage20";
            }else if(colorPath.equalsIgnoreCase("Blue")){
                pane = "storage22";
            }
            else if(colorPath.equalsIgnoreCase("LBlue")){
                pane = "storage21";
            }
            else if(colorPath.equalsIgnoreCase("Red")){
                pane = "storage24";
            }else{
                pane = "storage23";
            }
        }
        else if(playgroundValue >= 30 && playgroundValue < 38){
            if(colorPath.equalsIgnoreCase("Black")){
                pane = "storage31";
            }else if(colorPath.equalsIgnoreCase("Blue")){
                pane = "storage33";
            }
            else if(colorPath.equalsIgnoreCase("LBlue")){
                pane = "storage32";
            }
            else if(colorPath.equalsIgnoreCase("Red")){
                pane = "storage30";
            }else{
                pane = "storage34";
            }
        }
        else if(playgroundValue >= 40 && playgroundValue <= 45){
            if(colorPath.equalsIgnoreCase("Black")){
                pane = "storage42";
            }else if(colorPath.equalsIgnoreCase("Blue")){
                pane = "storage44";
            }
            else if(colorPath.equalsIgnoreCase("LBlue")){
                pane = "storage43";
            }
            else if(colorPath.equalsIgnoreCase("Red")){
                pane = "storage41";
            }else{
                pane = "storage40";
            }
        }
        if(playgroundValue > 200 && playgroundValue < 205){
            if(colorPath.equalsIgnoreCase("Black")){
                pane = "player2Storage03";
            }else if(colorPath.equalsIgnoreCase("Blue")){
                pane = "player2Storage00";
            }
            else if(colorPath.equalsIgnoreCase("LBlue")){
                pane = "player2Storage04";
            }
            else if(colorPath.equalsIgnoreCase("Red")){
                pane = "player2Storage02";
            }else{
                pane = "player2Storage01";
            }
        }
        else if(playgroundValue >= 212 && playgroundValue <= 215){
            if(colorPath.equalsIgnoreCase("Black")){
                pane = "player2Storage14";
            }else if(colorPath.equalsIgnoreCase("Blue")){
                pane = "player2Storage11";
            }
            else if(colorPath.equalsIgnoreCase("LBlue")){
                pane = "player2Storage10";
            }
            else if(colorPath.equalsIgnoreCase("Red")){
                pane = "player2Storage13";
            }else{
                pane = "player2Storage12";
            }
        }
        else if(playgroundValue > 220 && playgroundValue < 225){
            if(colorPath.equalsIgnoreCase("Black")){
                pane = "player2Storage20";
            }else if(colorPath.equalsIgnoreCase("Blue")){
                pane = "player2Storage22";
            }
            else if(colorPath.equalsIgnoreCase("LBlue")){
                pane = "player2Storage21";
            }
            else if(colorPath.equalsIgnoreCase("Red")){
                pane = "player2Storage24";
            }else{
                pane = "player2Storage23";
            }
        }
        else if(playgroundValue >= 230 && playgroundValue <= 235){
            if(colorPath.equalsIgnoreCase("Black")){
                pane = "player2Storage31";
            }else if(colorPath.equalsIgnoreCase("Blue")){
                pane = "player2Storage33";
            }
            else if(colorPath.equalsIgnoreCase("LBlue")){
                pane = "player2Storage32";
            }
            else if(colorPath.equalsIgnoreCase("Red")){
                pane = "player2Storage30";
            }else{
                pane = "player2Storage34";
            }
        }
        else if(playgroundValue >= 240 && playgroundValue <= 245){
            if(colorPath.equalsIgnoreCase("Black")){
                pane = "player2Storage42";
            }else if(colorPath.equalsIgnoreCase("Blue")){
                pane = "player2Storage44";
            }
            else if(colorPath.equalsIgnoreCase("LBlue")){
                pane = "player2Storage43";
            }
            else if(colorPath.equalsIgnoreCase("Red")){
                pane = "player2Storage41";
            }else{
                pane = "player2Storage40";
            }
        }
        return pane;
    }
}