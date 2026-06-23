package com.example.sudoku.models;

import java.util.ArrayList;

/**
 * Clase principal de llevar el flujo del juego.
 * Mantener el estado de la partida, validar movimientos del jugador,
 * gestionar las ayudas y condicion de victoria.
 */
public class Game {
    private Grid grid;
    private ArrayList<Integer> gridValues;
    private String buttonState;

    /**
     * Arreglo 1D que representa el tablero del jugador.
     * Tamaño 36 elementos (6x6), el valor 0 representa una casilla vacia,
     */
    private int[] playerBoard;

    /**
     * Constructor
     * Inicializa la estructura de datos y establece el estado inicial del boton.
     */
    public Game() {
        this.buttonState = "JUGAR";
        this.gridValues = new ArrayList<>();
        this.playerBoard = new int[36];
    }

    /**
     * Inicia una nueva partida.
     * Genera un nuevo tablero base (Grid), extrae la solucion correcta,
     * reinicia la memoria de los movimientos del jugador.
     */
    public void startNewGame() {
        this.grid = new Grid();
        this.gridValues = grid.getGrid();
        this.playerBoard = new int[36];
    }

    /**
     * Restablece los datos de la partida actual.
     * Limpia la solucion y convierte el tablero en uno vacio (todo en 0).
     */
    public void resetGameData() {
        this.buttonState = "JUGAR";
        if (this.gridValues != null) {
            this.gridValues.clear();
        }
        this.playerBoard = new int[36];
    }

    /**
     * Registra movimientos del jugador en la memoria del juego.
     * Convierte las coordenadas 2D en un indice 1D para actualizar el arreglo.
     * @param fila La posicion en el eje y (0 a 5).
     * @param col La posicion en el eje x (0 a 5).
     * @param value El numero ingresado por el jugador (Setea a 0 si se borro la casilla).
     */
    public void setPlayerMove(int fila, int col, int value) {
        int index = (fila * 6) + col;
        playerBoard[index] = value;
    }

    /**
     * Valida matematicamente si el numero ingresado cumple con las reglas del sudoku.
     * Comprueba que el numero no se repita en la misma fila, columna o sub-bloque (2x3).
     * @param fila La posicion en el eje Y de la casilla a evaluar.
     * @param col La posicion en el eje X de la casilla a evaluar.
     * @param value El numero ingresado que se va a validar.
     * @return {@code true} Si el movimiento es valido o es un borrado (0) {@code false} si incumple con las reglas.
     */
    public boolean isMoveValid(int fila, int col, int value) {

        //Si el jugador borra la casilla, siempre es un estado valido.
        if (value == 0) return true;

        //Validar toda la fila
        for (int c = 0; c < 6; c++) {

            //Evita compararse con la misma casilla.
            if (c == col) continue;
            if (playerBoard[(fila * 6) + c] == value) return false;
        }

        //Validar toda la columna
        for (int r = 0; r < 6; r++) {
            if (r == fila) continue;
            if (playerBoard[(r * 6) + col] == value) return false;
        }

        /**
         * validar sub-bloques 2x3
         * Calcula la esquina superior izquierda del bloque correspondiente.
         */
        int beginRowBlock = (fila / 2) * 2;
        int beginColBlock = (col / 3) * 3;
        for (int r = beginRowBlock; r < beginRowBlock + 2; r++) {
            for (int c = beginColBlock; c < beginColBlock + 3; c++) {
                if (r == fila && c == col) continue;
                if (playerBoard[(r * 6) + c] == value) return false;
            }
        }

        return true;
    }

    /**
     * Verificar si el tablero esta completo
     * @return {@code true} Si no hay espacios vacios (0), {@code false} de lo contrario.
     */
    public boolean isBoardFull() {
        for (int val : playerBoard) {

            //Si se encuentra al menos un 0, entonces el tablero no esta completo,28/
            if (val == 0) return false;
        }
        return true;
    }

    /**
     * Determinar si el jugador ha ganado.
     * Comparar el tablero actual con el tablero con el tablero con los valores correctos.
     * @return {@code true} Si el tablero esta lleno y coincide 100% con la solucion,
     *         {@code false} si hay errores o vacios.
     */
    public boolean isGameWon() {

        //No se puede ganar si el tablero no esta completo
        if (!isBoardFull()) return false;

        //Comparar indice por indice el tablero del jugdor con el tablero resuelto
        for (int i = 0; i < 36; i++) {
            if (playerBoard[i] != gridValues.get(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Obtener la lista con la solucion del tablero.
     * @return ArrayList con los 36 numeros correctos.
     */
    public ArrayList<Integer> getGridValues() {
        return gridValues;
    }

    /**
     * Obtener el estado actual de la partida, representado por el texto
     * del boton principal.
     * @return String que indica el estado (JUGAR o COMPROBAR).
     */
    public String getButtonState() {
        return buttonState;
    }

    /**
     * Establece el estado de la partida.
     * @param buttonState Eñ nuevo estado de la partida (JUGAR o COMPROBAR).
     */
    public void setButtonState(String buttonState) {
        this.buttonState = buttonState;
    }

    /**
     * Metodo para revelar numeros en las casillas (ayudas).
     * Revela de manera al azar los numeros del tablero resuelto.
     * Esto es valido hasta que queden 2 numeros o menos para solucionar la partida.
     * @return El indice lineal (0-35) de la casilla revelada, o {@code -1} si la ayuda es denegada
     *         por el limite.
     */
    public int requestHelp() {
        ArrayList<Integer> emptyPositions = new ArrayList<>();

        //Recopilar todos los indices que tienen 0 (vacios).
        for (int i = 0; i < 36; i++) {
            if (playerBoard[i] == 0) {
                emptyPositions.add(i);
            }
        }

        //Condicional, denegara la ayuda porque quedan 2 o menos casillas por resolver.
        if (emptyPositions.size() <= 2) {
            return -1;
        }

        //Seleccion aleatoria de uno de los indices vacios.
        java.util.Random random = new java.util.Random();
        int randomIndex = emptyPositions.get(random.nextInt(emptyPositions.size()));

        //Poner la respuesta correcta en la memoria del jugador.
        int correctValue = gridValues.get(randomIndex);
        playerBoard[randomIndex] = correctValue;

        //Retornar el indice para la vista pueda actualizar.
        return randomIndex;
    }
}
