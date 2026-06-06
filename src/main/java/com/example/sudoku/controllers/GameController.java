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

    /**
     * Inicializa la interfaz grafíca del tablero de Sudoku.
     * Recorre y posiciona 36 casillas (6x6) basadas en los valores del modelo.
     * Aplica un enmascaramiento al 60% de las casillas.
     * Calcular y dibujar dinámicamente los bordes con CSS.
     */
    public void initialize() {
        //Limpiar el gridLabel
        gridLabel.getChildren().clear();

        for (int fila = 0; fila < 6; fila++) {
            for (int col = 0; col < 6; col++) {

                /*index Sirve para almacenar la formúla matematica, convierte las coordenadas (x, y)
                      en una posición lineal. */
                int index = (fila * 6) + col;
                int number_result = gridValues.get(index);

                // Probabilidad para ocultar casilla (60%)
                boolean hideBox = Math.random() < 0.6;

                CharField charField;

                // Definir color de fondo
                /* If - else en una sola línea
                * Si hideBox es True, el fondo séra blanco, Si es False, séra gris.
                * */
                String backGroundColor = hideBox ? "-fx-background-color: #ffffff;" : "-fx-background-color: #e0e0e0;";

                /*
                * Si la casilla debe estar oculta, llama al constructor vacío (séra blanca la casilla)
                * Si no, convierte el número de la lista a una letra (int -> char), llama a otro constructor
                * para que se genere un número y no sea editable.
                * */
                if (hideBox) {
                    charField = new CharField();
                } else {
                    char numberChar = String.valueOf(number_result).charAt(0);
                    charField = new CharField(numberChar);
                    charField.setEditable(false);
                }

                int bordeArriba = (fila == 0) ? 3 : 1;
                int bordeDerecha = (col == 2 || col == 5) ? 3 : 1;
                int bordeAbajo = (fila == 1 || fila == 3 || fila == 5) ? 3 : 1;
                int bordeIzquierda = (col == 0) ? 3 : 1;

                String estiloBordes = String.format("-fx-border-color: black; -fx-border-width: %d %d %d %d;",
                        bordeArriba, bordeDerecha, bordeAbajo, bordeIzquierda);

                charField.setStyle(backGroundColor + " " + estiloBordes);

                charField.setPrefWidth(100);
                gridLabel.add(charField, col, fila);

            }
        }
    }
}
