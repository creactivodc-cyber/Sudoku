package com.example.sudoku.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import com.example.sudoku.models.Grid;

public class GameController {

    @FXML private Label sudokuLabel;
    @FXML private Button helpButton;
    @FXML private Button enterButton;

    @FXML void onActionHelpButton() {}
    @FXML void onActionEnterButton(){}

    private Grid grid = new Grid();
    
    int result = grid.generateGrid();

}
