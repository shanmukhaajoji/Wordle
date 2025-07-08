module com.example.wordle2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.wordle2 to javafx.fxml;
    exports com.example.wordle2;
}