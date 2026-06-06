package com.example.sudoku.controllers;

import com.example.sudoku.models.AlertBox;
import com.example.sudoku.models.CharField;
import com.example.sudoku.models.Grid;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class GameController {

    @FXML private Label sudokuLabel;
    @FXML private HBox HboxFields;
    @FXML private GridPane gridLabel;
    @FXML private Button helpButton;
    @FXML private Button enterButton;

    private Grid grid = new Grid();

    private ArrayList<Integer> gridValues = grid.getGrid();

    @FXML void onActionHelpButton() {}

    @FXML void onActionEnterButton(){
        AlertBox alertBox = new AlertBox();
        alertBox.showAlertBox(
                "Confirmacion",
                "Prueba",
                "Ola"
        );

        boolean safeConfirm = alertBox.showConfirmationBox(
                "Confirmar Acción",
                "¿Estás seguro de que deseas salir?",
                "Perderás todo el progreso de la partida actual."
        );
        if (safeConfirm) {
            System.out.println("El usuario aceptó salir.");
        } else {
            System.out.println("El usuario canceló la acción.");
        }
    }

    public void initialize() {
        gridLabel.getChildren().clear();

        for (int fila = 0; fila < 6; fila++) {
            for (int col = 0; col < 6; col++) {

                int index = (fila * 6) + col;
                int number_result = gridValues.get(index);

                //Probabilidad para ocultar casilla (60%)
                boolean hideBox = Math.random() < 0.6;

                CharField charField;

                if (hideBox) {
                    charField = new CharField();
                    charField.setStyle("-fx-background-color: #ffffff;");
                } else {
                    char numberChar = String.valueOf(number_result).charAt(0);
                    charField = new CharField(numberChar);
                    charField.setEditable(false);
                    charField.setStyle("-fx-background-color: #e0e0e0;");
                }

                charField.setPrefWidth(100);
                gridLabel.add(charField, col, fila);

                //charField.setAlignment(Pos.CENTER);
                //System.out.println(gridValues.get(col).toString().charAt(0));
                //CharField charField = new CharField(gridValues.get(col).toString().charAt(0));

            }
        }
    }
}
