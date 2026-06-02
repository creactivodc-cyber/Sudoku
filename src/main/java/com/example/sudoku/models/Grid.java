package com.example.sudoku.models;
import java.util.ArrayList;

/** Clase para generar la cuadrícula de números aleatoriamente */
public class Grid {
    
    /** Tamaño de la cuadrícula */
    int size = 6;

    /** Listas dinámicas para almacenar los números de las filas y columnas */
    ArrayList<Integer> gridRowList = new ArrayList<>();
    ArrayList<Integer> gridColumnList;
    
    /**
     * Valida si un número puede ser colocado en una posición específica de la cuadrícula
     * @param row Fila donde se desea colocar el número
     * @param column Columna donde se desea colocar el número
     * @param number Número a validar
     * @return true si el número puede ser colocado, false en caso contrario
     */ 
    /*boolean validation (int row, int column, int number) {
        /** Validación de filas *
        if (gridRowList.get(row) == number) {
            return false;
        }
        /** Validación de columnas *
        if (gridColumnList.get(column) == number) {
            return false;
        }
        
        return true;
    };*/

    int randomNum() {
        int num = (int) (Math.random() * (size + 1));
        return num;
    };

    public int generateGrid() {
    for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            int randomNumber;
            gridColumnList = new ArrayList<>();
            //do {
                randomNumber = randomNum();
                System.out.println(randomNumber + " ");
            //} while (/*validation(i, j, randomNumber) == true*/ false);
            gridColumnList.add(randomNumber);
        };
    };

    return 0;
    };
}
