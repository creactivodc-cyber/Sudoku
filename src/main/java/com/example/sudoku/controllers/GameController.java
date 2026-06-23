package com.example.sudoku.controllers;

import com.example.sudoku.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class GameController {

    @FXML private Label sudokuLabel;
    @FXML private HBox HboxFields;
    @FXML private GridPane gridLabel;
    @FXML private Button helpButton;
    @FXML private Button enterButton;

    //Instancia principal del Modelo de negocio, contiene las reglas del juego y su estado.
    private Game game = new Game();

    /**
     * Inicializar la vista base del juego al abrir la ventana.
     * Limpia el tablero y establece el boton en su estado inicial.
     */
    public void initialize() {
        gridLabel.getChildren().clear();
        enterButton.setText(game.getButtonState());
    }

    /**
     * Boton principal de la partida, genera el tablero como tambien, comprueba
     * la posible victoria.
     */
    @FXML
    void onActionEnterButton(){
        if (game.getButtonState().equals("JUGAR")) {

            //Genera la logica interna y luego pinta la interfaz.
            game.startNewGame();
            renderBoard();

            //Cambio de estado del boton.
            game.setButtonState("COMPROBAR");
            enterButton.setText(game.getButtonState());
        } else if (game.getButtonState().equals("COMPROBAR")) {

            //El meotodo realiza la verificacion final.
            verifyConditionWin();
        }
    }

    /**
     * Consultar al Modelo si hay posibilidad de pedir una ayuda (revelar un numero)
     * Actualiza el tablero, agregando el numero.
     * Si quedan 2 o menos casillas vacias, la ayuda no sera valida y no se revelara.
     */
    @FXML
    void onActionHelpButton() {

        //Bloqueo para evitar dar ayudas si el juego no se ha iniciado.
        if (game.getButtonState().equals("JUGAR"))
            return;

        //Solicitar el indice para la ayuda.
        int revealedIndex = game.requestHelp();

        if (revealedIndex == -1) {

            //Cuando se niega la ayuda porque quedan 2 o menos casillas por revelar
            //Entonces aparece este cuadro de alerta
            AlertBox alertBox = new AlertBox();
            alertBox.showAlertBox(
                    "Límite de Ayudas",
                    "No hay más ayudas disponibles.",
                    "Te quedan 2 números o menos para terminar."
            );
        } else {

            //El modelo da la ayuda, luego actualiza la vista.
            CharField winCell = (CharField) gridLabel.getChildren().get(revealedIndex);
            int correctAnswer = game.getGridValues().get(revealedIndex);

            winCell.setText(String.valueOf(correctAnswer));
            winCell.setEditable(false);
            winCell.setStyle(winCell.getStyle() + " -fx-text-fill: blue;");
        }
    }

    /**
     * Boton para reiniciar toda la partida con una confirmacion para proceder.
     */
    @FXML
    void onActionResetButton() {
        AlertBox alertBox = new AlertBox();
        boolean safeConfirm = alertBox.showConfirmationBox(
                "CONFIRMACIÓN DE ACCIÓN",
                "",
                "¿Estás seguro de que deseas reiniciar el tablero?\n" +
                        "Perderás todo el progreso de la partida actual."
        );

        if (safeConfirm) {
            System.out.println("El usuario aceptó reiniciar.");
            game.resetGameData();
            initialize();
        }
    }

    /**
     * Construccion visual del Sudoku en la interfaz.
     * Recorre las coordenadas y asigna la probablidad de ocultamiento de las casillas.
     * Dibuja los bordes de los sub-bloques y añade Listeners de validacion.
     */
    private void renderBoard() {
        gridLabel.getChildren().clear();

        for (int fila = 0; fila < 6; fila++) {
            for (int col = 0; col < 6; col++) {

                //Conversion de matriz 2D a indice 1D para consultar al modelo.
                int index = (fila * 6) + col;
                int number_result = game.getGridValues().get(index);

                //Probabilidad del 60% para generar una casilla vacia.
                boolean hideBox = Math.random() < 0.6;
                CharField charField;
                String backGroundColor = hideBox ? "-fx-background-color: #ffffff;" : "-fx-background-color: #cecece;";

                //Sincronizacion inicial entre CharField (Vista) y PlayerBorad (Modelo)
                if (hideBox) {
                    charField = new CharField();
                    game.setPlayerMove(fila, col, 0);
                } else {
                    char numberChar = String.valueOf(number_result).charAt(0);
                    charField = new CharField(numberChar);
                    charField.setEditable(false);
                    game.setPlayerMove(fila, col, number_result);
                }

                //Calculo para el grosor de los bordes (Cuadriculas 2x3)
                int bordeArriba = (fila == 0) ? 3 : 1;
                int bordeDerecha = (col == 2 || col == 5) ? 3 : 1;
                int bordeAbajo = (fila == 1 || fila == 3 || fila == 5) ? 3 : 1;
                int bordeIzquierda = (col == 0) ? 3 : 1;

                String estiloBordes = String.format("-fx-border-color: black; -fx-border-width: %d %d %d %d;",
                        bordeArriba, bordeDerecha, bordeAbajo, bordeIzquierda);

                charField.setStyle(backGroundColor + " " + estiloBordes + " -fx-text-fill: black;");
                charField.setPrefWidth(100);
                gridLabel.add(charField, col, fila);

                //Listener en tiempo real solo a las casillas jugables
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
     * Procesar en tiempo real las entradas del usuario para sincronizar con el modelo logico
     * y aplica colores en la fuente (rojo/negro) segun la validez del ~movimiento.
     * @param currentCell Casilla visual de que esta siendo modificada.
     * @param valueEnter El texto digitando en formato String.
     * @param currentRow Fila actual de la casilla
     * @param currentCol Columna actual de la casilla.
     */
    private void validateBlockRealTime(CharField currentCell, String valueEnter, int currentRow, int currentCol) {

        //Borrado de las casillas, restaura el estilo visual y limpia el modelo.
        if (valueEnter.trim().isEmpty()) {
            game.setPlayerMove(currentRow, currentCol, 0);
            currentCell.setStyle(currentCell.getStyle().replace("-fx-text-fill: red;", "-fx-text-fill: black;"));
            return;
        }

        int numericValue = Integer.parseInt(valueEnter);

        //Informar al Modelo el nuevo movimiento.
        game.setPlayerMove(currentRow, currentCol, numericValue);

        //delegar la verificacion al Modelo.
        boolean isValid = game.isMoveValid(currentRow, currentCol, numericValue);

        //Condicional basada en al respuesta del modelo, genera una retroalimentacion visual.
        if (!isValid) {
            if (!currentCell.getStyle().contains("-fx-text-fill: red;")) {
                currentCell.setStyle(currentCell.getStyle().replace("-fx-text-fill: black;", "-fx-text-fill: red;"));
            }
        } else {
            currentCell.setStyle(currentCell.getStyle().replace("-fx-text-fill: red;", "-fx-text-fill: black;"));
        }
    }

    /**
     * Consultar al modelo si el jugador a completado correctamente el tablero
     * Genera un cuadro de dialogo como respuesta.
     */
    private void verifyConditionWin() {
        AlertBox alertBox = new AlertBox();

        if (!game.isBoardFull()) {
            alertBox.showAlertBox("Partida Incompleta", "Todavía quedan casillas vacías.", "Debes rellenar todo el tablero antes de comprobar.");
        } else if (!game.isGameWon()) {
            alertBox.showAlertBox("Revisión de Tablero", "Hay números incorrectos.", "Revisa tus respuestas.");
        } else {
            alertBox.showAlertBox("¡VICTORIA!", "¡Felicitaciones!", "Lograste resolver el Sudoku correctamente :D.");
        }
    }
}
