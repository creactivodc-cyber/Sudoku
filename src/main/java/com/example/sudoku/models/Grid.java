package com.example.sudoku.models;
import java.util.ArrayList;

/** Clase para generar la cuadrícula de números aleatoriamente */
public class Grid {
    
    /** Tamaño de la cuadrícula */
    int size = 6;
    int suma[] = new int[size];

    /** Listas dinámicas para almacenar los números de las filas y columnas */
    ArrayList<Integer> gridList = new ArrayList<>();
    
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
            return true;
        }
        /** Validación de columnas *
        if (gridColumnList.get(column) == number) {
            return true;
        }
        
        return true;
    };*/

   
    int randomNum() {
        int num = (int) (Math.random() * (size + 1));
        return num;
    };

    public void generateGrid() {
    for (int i = 0; i < size; i++) {
        ArrayList<Integer> gridColumnList = new ArrayList<>();
        for (int j = 0; j < size; j++) {
            int randomNumber;
            do {
                randomNumber = randomNum(); 
            } while (gridColumnList.contains(randomNumber) || randomNumber == 0);
            gridColumnList.add(randomNumber);
        };
        gridList.addAll(gridColumnList.subList(0, size));
            
    };
    };
 
    public ArrayList<Integer> getGrid() {
        generateGrid();
        return gridList;
    }
}
