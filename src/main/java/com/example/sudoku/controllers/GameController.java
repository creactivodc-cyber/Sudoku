package com.example.sudoku.controllers;

import com.example.sudoku.models.AlertBox;
import com.example.sudoku.models.CharField;
import com.example.sudoku.models.Grid;
import com.example.sudoku.models.Helps;
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

    @FXML void onActionHelpButton() {
        Helps helps = new Helps();

        boolean help = helps.revealNumber(gridLabel, gridValues);

        if (!help) {
            AlertBox alertBox = new AlertBox();
            alertBox.showAlertBox(
                    "Límite de Ayudas",
                    "No hay más ayudas disponibles.",
                    "¡Ya casi lo logras! Te quedan 2 números o menos para terminar."
            );
        }
    }

    @FXML void onActionEnterButton(){}

    @FXML void onActionResetButton() {
        AlertBox alertBox = new AlertBox();
//        alertBox.showAlertBox(
//                "Reinicio de partida.",
//                "",
//                "Reiniciar la partida implica cambiar de tablero."
//        );

        boolean safeConfirm = alertBox.showConfirmationBox(
                "CONFIRMACIÓN DE ACCIÓN",
                "",
                "¿Estás seguro de que deseas reiniciar el tablero?\n" +
                        "Perderás todo el progreso de la partida actual."
        );

        if (safeConfirm) {
            System.out.println("El usuario aceptó reiniciar.");
            initialize();
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
                String backGroundColor = hideBox ? "-fx-background-color: #ffffff;" : "-fx-background-color: #cecece;";

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

                //Establecer por defecto el estilo en color negro con bordes negros y fondo negro al Char.
                charField.setStyle(backGroundColor + " " + estiloBordes + " -fx-text-fill: black;");

                charField.setPrefWidth(100);
                gridLabel.add(charField, col, fila);

                /**
                 * Si la casilla es editable (Está oculta), se agrega un Listener.
                 * Evento para escuchar cada tecla presionada por el jugador para validar.
                 */
                if(hideBox) {
                    final int f = fila;
                    final int c = col;

                    charField.textProperty().addListener((observable, oldValue, newValue) -> {
                        validateBlockRealTime(charField, newValue, f, c);
                    });
                }
            }
        }
    }

    /**
     * Validación en tiempo real si el número del jugador existe.
     * La comparación válida dentro del bloque 2x3 Si el número se repite,
     * cambiará el color de texto si el número se repite para alertar.
     * @param currentCell La casilla (CharField) que el jugador edita actualmente.
     * @param valueEnter El texto/número que el jugador acaba de dígitar.
     * @param currentRow La posición actual en Y (row - fila).
     * @param currentCol La posición actual en x (col - columna).
     */
    private void validateBlockRealTime(CharField currentCell, String valueEnter, int currentRow, int currentCol) {

        //Si el jugador borra el contenido, entonces se restaura el color negro y se aborta la validación.
        if (valueEnter.trim().isEmpty()) {
            currentCell.setStyle(currentCell.getStyle().replace("-fx-text-fill: red;", "-fx-text-fill: black;"));
            return;
        }

        //Atributo para establecer si se repite.
        boolean repeat = false;

        //Validación fila completa.
        for (int c = 0; c < 6; c++){

            //Medida de seguridad, omitir la iteración si estamos en la casilla exacta que el jugador acaba de modificar.
            if (c == currentCol) continue;

            //Fórmula matemática para convertir la coordenada 2D a un índice 1D
            int indexComparation = (currentRow * 6) + c;

            /* Extracción y Casting: Obtenemos el nodo genérico de la cuadrícula visual (Node)
            y lo forzamos (casting) a ser tratado como un objeto de la clase CharField. */
            CharField otherCell = (CharField) gridLabel.getChildren().get(indexComparation);

            //Verificar si el texto ingresado coincide exactamente con el texto de la casilla evaluada.
            if (valueEnter.equals(otherCell.getText())) {
                repeat = true;
                break;
            }
        }

        //Validación de la columna total
        if (!repeat) {
            for (int r = 0; r < 6; r++) {

                // Evitar compararse consigo misma
                if (r == currentRow) continue;

                //Calcular el índice lineal variando en la variable de la fila (r).
                int indexComparation = (r * 6) + currentCol;
                CharField otherCell = (CharField) gridLabel.getChildren().get(indexComparation);

                if (valueEnter.equals(otherCell.getText())) {
                    repeat = true;
                    break;
                }
            }
        }

        if (!repeat) {
            //Cálculos para encontrar la esquina superior izquierda del bloque 2x3.
            int beginRowBlock = (currentRow / 2) * 2;
            int beginColBlock = (currentCol / 3) * 3;

            //Solamente se recorre las 6 casillas del bloque correspondiente.
            for (int r = beginRowBlock; r < beginRowBlock + 2; r++){
                for (int c = beginColBlock; c < beginColBlock + 3; c++){

                    //Evitar que la casilla se compare consigo misma.
                    if (r == currentRow && c == currentCol) continue;

                    //Convertir las coordenadas en dos dimensiones (2D) a índice lineal (1D).
                    int indexComparation = (r * 6) + c;

                    CharField otherCell = (CharField) gridLabel.getChildren().get(indexComparation);

                    // Comparación de cadenas, verificar si el texto ingresado coincide exactamente con el texto de la casilla evaluada.
                    if (valueEnter.equals(otherCell.getText())) {
                        repeat = true;
                        break;
                    }
                }
            }
        }

        //Remplazo de la cadena CSS para no destruir la configuración de los bordes.
        if (repeat) {
            if (!currentCell.getStyle().contains("-fx-text-fill: red;")) {
                currentCell.setStyle(currentCell.getStyle().replace("-fx-text-fill: black;", "-fx-text-fill: red;"));
            }
        } else {
            currentCell.setStyle(currentCell.getStyle().replace("-fx-text-fill: red;", "-fx-text-fill: black;"));
        }
    }
}
