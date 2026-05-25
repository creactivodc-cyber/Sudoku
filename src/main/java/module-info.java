module com.univalle.sudoku6x6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.univalle.sudoku6x6 to javafx.fxml;
    exports com.univalle.sudoku6x6;
}