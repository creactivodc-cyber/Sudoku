package com.example.sudoku.models;
import java.util.ArrayList;

/** Clase para generar la cuadrícula de números aleatoriamente */
public class Grid {
    
    /** Tamaño de la cuadrícula */
    int size = 6;

    /** Listas dinámicas para almacenar los números de las filas y columnas 
     * Se utiliza una sola lista para almacenar los números de la cuadrícula, donde el índice se calcula como (fila * tamaño + columna) para acceder a cada posición. Esto permite una gestión más sencilla de los números en la cuadrícula sin necesidad de múltiples listas para filas y columnas.
     * @return La lista de números de la cuadrícula
    */
    ArrayList<Integer> gridList = new ArrayList<>();
    
 
    /**
     * Genera aleatoriamente un número entre 1 y el tamaño de la cuadrícula (inclusive).
     * @return El número generado
    */ 
    int randomNum() {
        int num = (int) (Math.random() * size + 1);
        return num;
    };

    /**
     * Valida si un número dado ya existe en la fila, columna o bloque 2x3 correspondiente a la posición especificada.
     * @param row Fila donde se desea validar el número
     * @param column Columna donde se desea validar el número
     * @param number Número a validar
     * @return true si el número ya existe en la fila, columna o bloque; false si es válido (no existe)
     */
    boolean validation (int row, int column, int number) {
        
        // Validar en la fila
        for (int i = 0; i < size; i++) {// Iterar a través de las columnas de la fila actual
            int index = row * size + i; // Calcular el índice en la lista para la posición (row, i)
            if (i == column) continue; // Saltar la columna actual para no comparar el número con sí mismo
            if (gridList.get(index) == number) { // Si el número ya existe en la fila, retornar true (no es válido)
                System.out.println("Validando número " + number + " en la posición (" + index + ") si es igual a " + gridList.get(index) + " en la fila " + row + " columna " + column);
                return true;
            }
        }

        // Validar en la columna
        for (int i = 0; i < size; i++) { // Iterar a través de las filas de la columna actual
            int index = i * size + column; // Calcular el índice en la lista para la posición (i, column)
            if (i == row) continue;   // Saltar la fila actual para no comparar el número con sí mismo
            if (gridList.get(index) == number) { // Si el número ya existe en la columna, retornar true (no es válido)
                return true;
            }
        }

        // Validar en el bloque 2x3
        int startRow = (row / 2) * 2; // Calcular la fila de inicio del bloque 2x3 (0, 2 o 4)
        int startCol = (column / 3) * 3; // Calcular la columna de inicio del bloque 2x3 (0, 3 o 6)

        for (int i = startRow; i < startRow + 2; i++) { // Iterar a través de las filas del bloque 2x3
            for (int j = startCol; j < startCol + 3; j++) { // Iterar a través de las columnas del bloque 2x3
                int index = i * size + j; // Calcular el índice en la lista para la posición (i, j)
                if (i == row && j == column) continue; // Saltar la posición actual para no comparar el número con sí mismo
                if (gridList.get(index) == number) {    // Si el número ya existe en el bloque 2x3, retornar true (no es válido)
                    return true;
                }
            }
        }

        return false;
        
    };


    /** Método recursivo para resolver la cuadrícula utilizando backtracking.
     * @param fila Fila actual que se está resolviendo
     * @param columna Columna actual que se está resolviendo
     * @return true si se ha resuelto la cuadrícula; false si no se pudo resolver
    */
    boolean resolver(int fila, int columna) {
        if (fila == size) return true; // Si se ha llenado toda la cuadrícula, se ha resuelto
        int sigFila = (columna == size - 1) ? fila + 1 : fila; // Siguiente fila si se ha llegado al final de la columna
        int sigCol = (columna == size - 1) ? 0 : columna + 1; // Siguiente columna o volver a la primera columna si se ha llegado al final
        // genero los 6 numeros en orden aleatorio usando randomNum()
        ArrayList<Integer> nums = new ArrayList<>(); // Lista para almacenar los números generados aleatoriamente
        while (nums.size() < size) { // Generar números aleatorios hasta llenar la lista con 6 números únicos
            int n = randomNum(); // Generar un número aleatorio entre 1 y 6
            if (!nums.contains(n)) nums.add(n); // Agregar el número a la lista si no está ya presente (para asegurar que sean únicos)
        }
        for (int num : nums) { // Iterar a través de los números generados aleatoriamente
            int indice = fila * size + columna; // Calcular el índice en la lista para la posición (fila, columna)
            gridList.set(indice, num); // Colocar el número en la cuadrícula en la posición actual
            if (!validation(fila, columna, num)) { 
                if (resolver(sigFila, sigCol)) return true;
            }
            gridList.set(indice, 0); // backtrack
        }
        return false;
    };

    public void generateGrid2() {
        gridList.clear();
        for (int i = 0; i < size * size; i++) gridList.add(0);
        if (resolver(0, 0)) {
            System.out.println("Grid 6x6 generada:");
            for (int i = 0; i < size; i++) {
                String s = "";
                for (int j = 0; j < size; j++) {
                    s += gridList.get(i * size + j) + " ";
                }
                System.out.println("Fila " + (i + 1) + ": " + s);
            }
        } else {
            System.out.println("No se pudo generar");
        }
    };    

    public ArrayList<Integer> getGrid() {
        generateGrid2();
        System.out.println("Cuadrícula generada: " + gridList);
        return gridList;
    }
}
