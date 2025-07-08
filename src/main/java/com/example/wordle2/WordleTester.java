package com.example.wordle2;

import com.example.wordle2.WordleGame;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class WordleTester extends Application{

    public void start(Stage stage) throws Exception {
        WordleGame x= new WordleGame(stage);
        //stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}