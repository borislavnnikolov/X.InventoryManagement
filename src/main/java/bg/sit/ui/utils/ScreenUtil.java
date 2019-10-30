/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.sit.ui.utils;

import java.util.HashMap;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Dell
 */
public class ScreenUtil {

    private static HashMap<String, Pane> screenMap = new HashMap<>();
    private static Scene currentScreen;

    public static void setStage(Scene scene)
    {
        currentScreen = scene;
    }
    
    public static void addScreen(String name, Pane pane) {
        screenMap.put(name, pane);
    }

    public static void removeScreen(String name) {
        screenMap.remove(name);
    }

    public static void activate(String name) {
        currentScreen.setRoot(screenMap.get(name));
    }
}
