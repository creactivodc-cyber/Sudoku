package com.example.sudoku.models;

public interface IAlertBox {
    void showAlertBox(String title, String header, String message);
    boolean showConfirmationBox(String title, String header, String message);
}
