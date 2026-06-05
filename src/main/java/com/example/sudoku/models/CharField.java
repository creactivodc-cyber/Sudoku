package com.example.sudoku.models;

import javafx.scene.control.TextArea;
import javafx.scene.text.Font;


public class CharField extends TextArea {

    public char num_char;

    public CharField(char letter) {
        this.num_char = letter;
        setText(String.valueOf(letter));
        //System.out.println("Caracter mostrado: " + letter);
    }


    public void setText(char c) {
        setText(String.valueOf(c));
        setFont(Font.font("Arial Bold", 19));
    }


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