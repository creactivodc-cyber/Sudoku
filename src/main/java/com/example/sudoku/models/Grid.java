package com.example.sudoku.models;
import java.util.ArrayList;

/** Clase para generar la cuadrícula de números aleatoriamente */
public class Grid {
    
    /** Tamaño de la cuadrícula */
    int size = 6;
    int grid[][] = new int[size][size];

    /** Cuadrícula predefinida para pruebas */
    int grid2[][] = {
        {5, 1, 4, 3, 6, 2},
        {3, 6, 2, 1, 4, 5},
        {6, 2, 1, 5, 3, 4},
        {4, 3, 5, 2, 1, 6},
        {2, 4, 6, 3, 5, 1},
        {1, 5, 3, 4, 2, 6}
    };

    /** Listas dinámicas para almacenar los números de las filas y columnas */
    ArrayList<Integer> gridList = new ArrayList<>();

    /* Lista predefinida para pruebas */
    ArrayList<Integer> gridList2 = new ArrayList<Integer>() {{
        add(5); add(1); add(4); add(3); add(6); add(2);
        add(3); add(6); add(2); add(1); add(4); add(5);
        add(6); add(2); add(1); add(5); add(3); add(4);
        add(4); add(3); add(5); add(2); add(1); add(6);
        add(2); add(4); add(6); add(3); add(5); add(1);
        add(1); add(5); add(3); add(4); add(2); add(6);
    }};

    
 
    /**
     * Valida si un número puede ser colocado en una posición específica de la cuadrícula
     * @param row Fila donde se desea colocar el número
     * @param column Columna donde se desea colocar el número
     * @param number Número a validar
     * @return true si el número puede ser colocado, false en caso contrario
    */ 
    boolean validation (int row, int column, int number, int[][] grid) {
        /** Validación de filas */
        for (int i = 0; i < size; i++) {
             if (grid[row][i] == number) {
                return true;
            }
        }
    
        /** Validación de columnas */
        for (int i = 0; i < size; i++) {
            if (grid[i][column] == number) {
                return true;
            }
        }
        return false;
    };

   
    int randomNum() {
        int num = (int) (Math.random() * size + 1);
        return num;
    };

    public void generateGrid() {
        for (int i = 0; i < size; i++) {
            ArrayList<Integer> gridColumnList = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                int randomNumber;
                //do {
                    randomNumber = randomNum(); 
                //} while (gridColumnList.contains(randomNumber) || randomNumber == 0);
                gridColumnList.add(randomNumber);
            };
            gridList.addAll(gridColumnList.subList(0, size));
            System.out.println("Fila " + (i + 1) + ": " + gridColumnList);
            
        };
    };

    public void generateGrid2() {

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < size; j++) {
                int randomNumber;
                randomNumber = randomNum();
                
                if (!validation(i, j, randomNumber, grid) && randomNumber != 0) {
                    grid[i][j] = randomNumber;
                }
                
            };     
            

            //gridList.addAll(gridColumnList.subList(0, size));
            System.out.println("Fila " + (i + 1) + ": " + grid[i][0] + " " + grid[i][1] + " " + grid[i][2] + " " + grid[i][3] + " " + grid[i][4] + " " + grid[i][5]);
        };
    };
 
    public ArrayList<Integer> getGrid() {
        //generateGrid2();
        return gridList2;
    }
}
