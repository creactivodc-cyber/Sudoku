package com.example.sudoku.models;

import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Random;


/**
 * Clase Helps, sistema encargado de dar ayudas al jugador (revelar números para ganar)
 */
public class Helps {

    /**
     * Revela un número al azar en una casilla del tablero que está vacía.
     * Regla: Si quedan 2 o menos casillas vacías, la ayuda es negada para obligar
     * al jugador a completar el juego por sí mismo.
     * @param gridLabel Contenedor el GridPane que tiene las 36 casillas.
     * @param gridValues Estructura dinámica de datos (ArrayList) que contiene la solución del Sudoku.
     * @return {@code true} Si la pista se entregó correctamente, {@code false} Si se alcanzó él
     * limite de ayudas.
     */
    public boolean revealNumber(GridPane gridLabel, ArrayList<Integer> gridValues){

        //Lista dinámica para almacenar las casillas vacías.
        ArrayList<Integer> posEmpty = new ArrayList<>();

        for (int i = 0; i < 36; i++){
            CharField cell = (CharField) gridLabel.getChildren().get(i);

            //Verificar si la casilla está en blanco.
            if (cell.getText().trim().isEmpty()) {

                //Guardar el índice en la lista de casillas disponibles.
                posEmpty.add(i);
            }
        }

        //Limitador de ayudas, verifica si quenda 2 o menos casillas por rellenar.
        if (posEmpty.size() <= 2){

            //Abortar la ayuda si tiene menos de dos casillas por rellenar.
            return false;
        }

        Random random = new Random();

        //Obtener un índice aleatorio de la lista vacía.
        int randomCell = random.nextInt(posEmpty.size());

        //Obtener la posición del tablero real (0 - 35) que estaba guardada en el espacio aleatorio.
        int posSelect = posEmpty.get(randomCell);

        //Respuesta correcta en la lista main.
        int correctAnswer = gridValues.get(posSelect);

        //Ubicar la casilla ganadora en la interfaz gráfica.
        CharField winCell = (CharField) gridLabel.getChildren().get(posSelect);

        //Establecer el número y bloquear la edición para evitar que el jugador lo cambie.
        winCell.setText(String.valueOf(correctAnswer));
        winCell.setEditable(false);

        //Resaltar la fuente de color azul para diferenciarse de las demás casillas.
        String currentStyle = winCell.getStyle();
        winCell.setStyle(currentStyle + " -fx-text-fill: blue;");

        return true;
    }
}
