package com.example.sudoku.models;

import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;


public class CharField extends TextField {

    private char num_char;

    public CharField(char letter) {
        this.num_char = letter;
        super.setText(String.valueOf(letter));
        this.setFont(Font.font("Arial Bold", 20));
        this.setAlignment(Pos.CENTER);
        this.setPrefSize(100, 100);
        //System.out.println("Caracter mostrado: " + letter);
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