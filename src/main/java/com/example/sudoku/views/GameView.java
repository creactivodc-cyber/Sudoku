package com.example.sudoku.views;

import com.example.soleclipsado.controllers.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameView extends Stage {

    private GameController controller;


    public GameView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sudoku/game-view.fxml"));
        Parent root = fxmlLoader.load();

        //Instancia del controlador para realizar operaciones sobre este fxml
        controller = fxmlLoader.getController();

        Scene scene = new Scene(root);
        setTitle("Sol Eclipsado");
        setScene(scene);
        setResizable(false);
    }

    public GameController getController(){
        return controller;
    }

    public static class GameViewHolder {
        private static GameView INSTANCE = null;
    }

    public static GameView getInstance() throws IOException {
        if (GameView.GameViewHolder.INSTANCE == null){
            GameView.GameViewHolder.INSTANCE = new GameView();
        }
        return GameView.GameViewHolder.INSTANCE;
    }


}
