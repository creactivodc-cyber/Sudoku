package com.example.sudoku.models;

/** Clase para generar la cuadrícula de números aleatoriamente */
public class Grid {
    
    /** Tamaño de la cuadrícula */
    int size = 6;
    /** Cuadrícula de números */
    int grid[][] = new int[size][size];
    
    /**
     * Valida si un número puede ser colocado en una posición específica de la cuadrícula
     * @param row Fila donde se desea colocar el número
     * @param column Columna donde se desea colocar el número
     * @param number Número a validar
     * @return true si el número puede ser colocado, false en caso contrario
     */ 
    boolean validation (int row, int column, int number) {
        /** Validación de filas */
        for (int i = 0; i < size; i++) {
            if (grid[i][column] == number) {
                return false;
            }
        }
        /** Validación de columnas */
        for (int j = 0; j < size; j++) {
            if (grid[row][j] == number) {
                return false;
            }
        }
        return true;
    }

}
