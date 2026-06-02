package com.example.sudoku.controllers;

import com.example.sudoku.models.AlertBox;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.event.MouseEvent;

public class GameController {

    @FXML private Label sudokuLabel;
    @FXML private Button helpButton;
    @FXML private Button enterButton;

    @FXML void onActionHelpButton(){}

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
}
