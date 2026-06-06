package com.example.sudoku.models;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

/** Clase para la generación de caracteres en la grilla del Sudoku */
public class CharField extends TextField {

    /** Atributo para almacenar el número y luego convertirlo a Char */
    private char num_char;

    /** Constructor para las casillas fijas.
     * Inicializa la casilla mostrando la pista generada automáticamente
     * por la máquina.
     * @param letter El carácter numérico que se asignara y mostrara en la casilla.
     */
    public CharField(char letter) {
        this.num_char = letter;
        super.setText(String.valueOf(letter));
        stylesApply();
    }

    /**
     * Constructor para las casillas vacías.
     * Inicializa la casilla sin texto, lista para que el jugador digite
     * su número.
     */
    public CharField() {
        super.setText("");
        stylesApply();
    }

    /**
     * Aplica los estilos visuales predeterminados a la casilla.
     * Define la fuente, centra el texto y establece un tamaño fijo de 100x100.
     */
    private void stylesApply(){
        this.setFont(Font.font("Arial Bold", 20));
        this.setAlignment(Pos.CENTER);
        this.setPrefSize(100, 100);

    }


//    public void setNum(char c) {
//        this.num_char = c;
//        super.setText(String.valueOf(c));
//    }


    //Valida que si el caracter que se le envio a comprobar coincide con su caracter oculto entonces se muestra
    /*public int validateInputCharacter(char c) {
        if (num_char == c) {
            setText(num_char);
            setFont(Font.font("Arial Bold", 19));
            return 1;
        }
        return 0;
    }*/
}