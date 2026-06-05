package com.example.sudoku.controllers;

import com.example.sudoku.models.AlertBox;
import com.example.sudoku.models.Grid;
import com.example.sudoku.models.CharField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class GameController {

    @FXML private Label sudokuLabel;
    @FXML HBox HboxFields;
    @FXML Button helpButton;
    @FXML private Button enterButton;

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

    private Grid grid = new Grid();
    
    private ArrayList<String> gridValues = grid.getGrid();

    
    public void initialize() {
        
        HboxFields.getChildren().clear();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.println(gridValues.get(j).toString().charAt(0));

                CharField charField = new CharField(gridValues.get(j).toString().charAt(0));
                charField.setPrefWidth(30);
                //charField.setAlignment(Pos.CENTER);

                
                HboxFields.getChildren().add(charField);
            }

        }
    }

}
