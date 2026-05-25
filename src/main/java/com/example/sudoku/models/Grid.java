package com.example.sudoku.models;


// Clase para generar la cuadricula de números aleatoriamente
public class Grid {

    int size = 6;
    int grid[][] = new int[size][size];

    boolean validation (int row, int column, int number) {
        // Recorrido de filas
        for (int i = 0; i < 6; i++) {
            if (grid[i][column] == number) {
                return false;
            }
        }
        // Recorrido de columnas
        for (int j = 0; j < 6; j++) {
            if (grid[row][j] == number) {
                return false;
            }
        }
        return true;
    }

}
