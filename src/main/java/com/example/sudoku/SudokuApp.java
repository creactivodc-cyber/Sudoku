package com.example.sudoku;

import com.example.sudoku.views.GameView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class SudokuApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {

      GameView s = GameView.getInstance();
      s.show();

    }
}
