package com.example.sudoku.models;
import java.util.ArrayList;

/** Clase para generar la cuadrícula de números aleatoriamente */
public class Grid {
    
    /** Tamaño de la cuadrícula */
    int size = 6;

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
    int randomNum() {
        int num = (int) (Math.random() * size + 1);
        return num;
    };


    boolean validation (int row, int column, int number) {
        
        // Validar en la fila
        for (int i = 0; i < size; i++) {
            int index = row * size + i;
            if (i == column) continue;
            if (gridList.get(index) == number) {
                System.out.println("Validando número " + number + " en la posición (" + index + ") si es igual a " + gridList.get(index) + " en la fila " + row + " columna " + column);
                return true;
            }
        }

        // Validar en la columna
        for (int i = 0; i < size; i++) {
            int index = i * size + column;
            if (i == row) continue;  
            if (gridList.get(index) == number) {
                return true;
            }
        }

        // Validar en el bloque 2x3
        int startRow = (row / 2) * 2;
        int startCol = (column / 3) * 3;

        for (int i = startRow; i < startRow + 2; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                int index = i * size + j;
                if (i == row && j == column) continue;
                if (gridList.get(index) == number) {
                    return true;
                }
            }
        }

        return false;
        
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
        gridList.clear();
        /** Inicializar la cuadrícula con ceros */
        for (int i = 0; i < size * size; i++) {
            gridList.add(0);
        }

        /** Algoritmo de backtracking para llenar la cuadrícula */
        int fila = 0;
        int columna = 0;
        int[] ultimoIntento = new int[size * size];
        while (fila < size) {
            int indice = fila * size + columna;
            boolean encontrado = false;
            for (int num = ultimoIntento[indice] + 1; num <= size; num++) {
                gridList.set(indice, num);
                if (fila == 0 && columna == 0) {
                    encontrado = true;
                    ultimoIntento[indice] = num;
                    System.out.println("(" + fila + "," + columna + ") = " + num + " OK");
                    break;
                } else if (!validation(fila, columna, num)) {
                    encontrado = true;
                    ultimoIntento[indice] = num;
                    System.out.println("(" + fila + "," + columna + ") = " + num + " OK");
                    break;
                } else {
                    gridList.set(indice, 0);
                }
            }
            if (encontrado) {
                // avanzo a la siguiente celda
                columna++;
                if (columna == size) {
                    columna = 0;
                    fila++;
                }
            } else {
                // retrocedo (backtrack)
                ultimoIntento[indice] = 0;
                gridList.set(indice, 0);
                columna--;
                if (columna < 0) {
                    columna = size - 1;
                    fila--;
                }
                // si fila se vuelve negativa, no hay solucion
                if (fila < 0) {
                    System.out.println("No hay solucion posible.");
                    return;
                }
            }
        }
    }
            
    public ArrayList<Integer> getGrid() {
        generateGrid2();
        System.out.println("Cuadrícula generada: " + gridList);
        return gridList;
    }
}
