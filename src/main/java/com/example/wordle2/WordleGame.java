package com.example.wordle2;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.*;
import java.security.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class WordleGame implements EventHandler<ActionEvent> {
    private static final int WORDS = 6;
    private static final int WORDLENGTH = 5;

    private Button[] keys;
    private Button[] displayBtn;

    private String aWord;

    private Stage stage;
    private Scene scene;
    private FlowPane rootPane;
    private GridPane displayPane;
    private GridPane headerPane;
    private GridPane keyboardPane;
    private int m = 0;
    private ArrayList<String> words = new ArrayList<>();
    ArrayList<Integer> newOver = new ArrayList<>();
    private int in =0;
    private int ni = 0;
    public WordleGame(Stage stage) throws Exception {
        this.stage = stage;
        this.rootPane = new FlowPane();
        this.scene = new Scene(rootPane, 940, 750);
        initDisplay();
        initKeyBoard();
        this.headerPane = new GridPane();
        rootPane.getChildren().addAll(displayPane);
        rootPane.getChildren().addAll(keyboardPane);
        rootPane.getChildren().addAll(headerPane);
        stage.setTitle("Wordle");
        stage.setScene(scene);
        stage.show();
        aWord = getWord();
        getWord();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                boolean isDuplicate = false;
                String s = (event.getCode()).toString();
                ArrayList<Integer> numWord = new ArrayList<>();
                ArrayList<Integer> overall = new ArrayList<>();
                ArrayList<Integer> numGuess = new ArrayList<>();

                aWord = aWord.toUpperCase(Locale.ROOT);
                s = s.toUpperCase(Locale.ROOT);
                int size = 5;

                if (!s.equals("BACK_SPACE") && !s.equals("ENTER") && !s.equals("COMMAND") && guess.length() < size) {
                    guess += s;
                    displayBtn[place].setText(s);
                    displayBtn[place].setTextFill(Color.WHITE);
                    displayBtn[place].setFont(Font.font("comic-sans", FontWeight.BOLD, FontPosture.REGULAR, 20));
                    place += s.length();
                }



                boolean inList = false;


                if(s.equals("ENTER")) {
                    for (int i = 0; i < words.size(); i++) {
                        if (words.get(i).equals(guess)) {
                            inList = true;
                        }
                    }

                    if (guess.length() < size) {
                        Label l = new Label("Too Short of a Word");
                        l.setFont(Font.font("Arial", FontWeight.LIGHT, FontPosture.REGULAR, 20));
                        l.setStyle("-fx-display: absolute; -fx-position: flex");
                        headerPane.setAlignment(Pos.CENTER);
                        headerPane.add(l, 4, 10);
                        headerPane.setMargin(l, new Insets(5));
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Platform.runLater(() -> {
                                    headerPane.getChildren().remove(l);
                                });

                            }
                        }, 2000);
                    } else if (true) {

                        String d = "";
                        int num = 0;
                        int occur = 0;
                        for (int q = 0; q < guess.length(); q++) {
                            for (int j = q + 1; j < guess.length(); j++) {
                                if (guess.charAt(q) == guess.charAt(j)) {
                                    num++;
                                    d += guess.charAt(q);
                                    q++;
                                    isDuplicate = true;
                                }

                            }
                        }

                        for (int q = 0; q < d.length(); q++) {
                            for (int j = q + 1; j < d.length(); j++) {
                                if (d.charAt(q) == d.charAt(j)) {
                                    num--;
                                    d = d.substring(0, j);

                                }
                            }
                        }





                        ArrayList<String> dups = new ArrayList<>();

                        if(isDuplicate) {
                            for (int i = 0; i < d.length(); i++) {
                                dups.add(d.charAt(i) + "");
                            }


                            for (int i = 0; i < dups.size(); i++) {
                                if (aWord.contains(dups.get(i))) {
                                    d = dups.get(i);
                                }
                            }
                        }



                        if(isDuplicate) {
                            int q = 0;
                            int u = 0;

                            for (int j = place - 5; j < place; j++) {
                                if (aWord.substring(q, q + 1).equals(dups.get(0))) {
                                    numWord.add(in);
                                }
                                q++;
                                in++;
                            }

                            for (int j = place - 5; j < place; j++) {
                                if (guess.substring(u, u + 1).equals(dups.get(0))) {
                                    numGuess.add(ni);
                                }
                                u++;
                                ni++;
                            }
                        }



                        if (guess.equals(aWord)) {
                            Label l = new Label("You got the word");
                            l.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
                            for (int i = place - 5; i < place; i++) {
                                displayBtn[i].setStyle("-fx-background-color:#538D4E");
                            }
                            headerPane.setAlignment(Pos.CENTER);
                            headerPane.add(l, 4, 10);
                            headerPane.setMargin(l, new Insets(5));
                            guess = "";
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    Platform.runLater(() -> {

                                        stage.close();
                                    });

                                }
                            }, 3000);

                        } else {
                            for (int i = 0; i < guess.length(); i++) {
                                if (guess.charAt(i) == aWord.charAt(i)) {
                                    displayBtn[r].setStyle("-fx-background-color: #538D4E");
                                    for (int j = 0; j < keys.length; j++) {
                                        if (keys[j].getText().equals(guess.charAt(i) + "")) {
                                            keys[j].setStyle("-fx-background-color:#538D4E");
                                            keys[j].setTextFill(Color.WHITE);
                                        }
                                    }

                                } else if (aWord.contains(guess.charAt(i) + "")) {
                                    displayBtn[r].setStyle("-fx-background-color: #B49F3A");
                                    for (int j = 0; j < keys.length; j++) {
                                        if (keys[j].getText().equals(guess.charAt(i) + "")) {
                                            keys[j].setStyle("-fx-background-color:#B49F3A");
                                            keys[j].setTextFill(Color.WHITE);
                                        }
                                    }

                                } else {
                                    displayBtn[r].setStyle("-fx-background-color: gray");
                                    for (int j = 0; j < keys.length; j++) {
                                        if (keys[j].getText().equals(guess.charAt(i) + "")) {
                                            keys[j].setStyle("-fx-background-color:gray");
                                            keys[j].setTextFill(Color.WHITE);
                                        }
                                    }

                                }


                                r++;
                            }
                            guess = "";
                            size += 5;
                        }
                    } else {
                        Label l = new Label("NOT IN LIST");
                        l.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
                        l.setStyle("-fx-display: flex; -fx-position: absolute;");
                        headerPane.setAlignment(Pos.CENTER);
                        headerPane.add(l, 4, 10);
                        headerPane.setMargin(l, new Insets(5));


                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Platform.runLater(() -> {
                                    headerPane.getChildren().remove(l);
                                });

                            }
                        }, 2000);
                    }

                    if (inList) {
                        if (!displayBtn[29].getText().equals("")) {
                            Label l = new Label("The word is " + aWord);
                            l.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 25));
                            l.setStyle("-fx-display: flex; -fx-position: absolute;");
                            headerPane.setAlignment(Pos.CENTER);
                            headerPane.add(l, 4, 10);
                            headerPane.setMargin(l, new Insets(5));
                            keys[28].setDisable(true);
                            keys[27].setDisable(true);

                        }
                    }

                    if(isDuplicate){

                        //narrows down the numGuess. adds the indexes that are in numWord to overall arraylist. set the ones
                        // that are not in the numWord list to gray
                        for (int i = 0; i < numGuess.size(); i++) {
                            if (numGuess.size() == numWord.size()) {
                                break;
                            } else if (numWord.contains(numGuess.get(i))) {
                                overall.add(numGuess.get(i));
                                m++;
                            } else {
                                displayBtn[numGuess.get(i)].setStyle("-fx-background-color:gray");
                            }

                        }

                        //if there are too little indexes in the overall list, you add the last one or the first one in the numGuess list.
                        if (overall.size() < numWord.size()) {
                            if (!overall.contains(numGuess.get(numGuess.size() - 1))) {
                                overall.add(numGuess.get(numGuess.size() - 1));
                            } else{
                                overall.add(numGuess.get(0));
                            }

                        }


                        //set it to green or yellow
                        for (int i = 0; i < overall.size(); i++) {

                            if (numWord.contains(overall.get(i))) {
                                displayBtn[overall.get(i)].setStyle("-fx-background-color:#538D4E");
                            } else {
                                displayBtn[overall.get(i)].setStyle("-fx-background-color:#B49F3A");

                            }
                        }
                        for (int i = 0; i < overall.size(); i++) {
                            newOver.add(overall.get(i));
                        }
                    } else{
                        in+=(place-in);
                        ni+=(place-ni);
                    }

                }

                if (s.equals("BACK_SPACE")) {
                    guess = guess.substring(0, guess.length()-1);
                    displayBtn[place-1].setText("");
                    place -= 1;
                }
            }
        });
    }

    private void initDisplay() throws Exception{
        this.displayPane = new GridPane();
        displayBtn = new Button[WORDS * WORDLENGTH];
        int buttonIndex = 0;

        for (int r = 0; r < WORDS; r++) {
            for (int c = 0; c < WORDLENGTH; c++) {
                displayBtn[buttonIndex] = new Button("");
                displayBtn[buttonIndex].setPrefSize(75, 70);
                //css style
                displayBtn[buttonIndex].setStyle("-fx-border-color: black; -fx-background-color: #7393B3; -fx-border-width: 4px; ");
                this.displayPane.add(displayBtn[buttonIndex], c, r);
                displayPane.setMargin(displayBtn[buttonIndex], new Insets(5));
                rootPane.setAlignment(Pos.CENTER);

                buttonIndex++;
            }
        }


    }

    public String getWord() throws Exception{
        Scanner scan = null;
        File f =  null;
        f = new File("words.txt");
        scan = new Scanner(f);



        int i =0;
        while(scan.hasNextLine()){
            words.add(scan.nextLine().toUpperCase(Locale.ROOT));
        }

        Random r = new Random();
        int rand = r.nextInt(words.size());


        words.remove(words.get(rand));
        return words.get(rand);
    }

    private void initKeyBoard(){
        this.keyboardPane = new GridPane();
        String s = "qwertyuiopasdfghjkl zxcvbnm.,/'";
        keys = new Button[30];
        int num =0;
        int u = 0;
        char p = 'a';
        for (int i = 0; i < keys.length; i++) {
            keys[i] = new Button((s.charAt(u) + "").toUpperCase(Locale.ROOT));
            keys[i].setPrefSize(75, 50);
            keys[i].setOnAction(this);
            keys[i].setStyle("-fx-background-color:#D3D6DA");
            keys[i].setFont(Font.font("Arial", FontWeight.LIGHT, FontPosture.REGULAR, 15));
            u++;
        }
        keys[19].setVisible(false);
        keys[27].setText("Enter");
        keys[28].setText("Del");
        keys[29].setVisible(false);


        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 10; c++) {
                this.keyboardPane.add(keys[num], c, r);
                this.keyboardPane.setMargin(keys[num], new Insets(5));

                num++;
            }
        }

    }

    String guess = "";
    int place = 0;
    int r =0;

    @Override public void handle(ActionEvent event){
        boolean isDuplicate = false;
        String s = ((Button)event.getSource()).getText();
        ArrayList<Integer> numWord = new ArrayList<>();
        ArrayList<Integer> overall = new ArrayList<>();
        ArrayList<Integer> numGuess = new ArrayList<>();

        aWord = aWord.toUpperCase(Locale.ROOT);
        s = s.toUpperCase(Locale.ROOT);
        int size = 5;

        if (!s.equals("BACK_SPACE") && !s.equals("ENTER") && !s.equals("COMMAND") && guess.length() < size) {
            guess += s;
            displayBtn[place].setText(s);
            displayBtn[place].setTextFill(Color.WHITE);
            displayBtn[place].setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
            place += s.length();
        }



        boolean inList = false;


        if(s.equals("ENTER")) {
            for (int i = 0; i < words.size(); i++) {
                if (words.get(i).equals(guess)) {
                    inList = true;
                }
            }

            if (guess.length() < size) {
                Label l = new Label("Too Short of a Word");
                l.setFont(Font.font("comic-sans", FontWeight.BOLD, FontPosture.REGULAR, 20));
                l.setStyle("-fx-display: flex; -fx-position: absolute;");
                headerPane.setAlignment(Pos.CENTER);
                headerPane.add(l, 4, 10);
                headerPane.setMargin(l, new Insets(5));
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            headerPane.getChildren().remove(l);
                        });

                    }
                }, 2000);
            } else if (true) {

                String d = "";
                int num = 0;
                int occur = 0;
                for (int q = 0; q < guess.length(); q++) {
                    for (int j = q + 1; j < guess.length(); j++) {
                        if (guess.charAt(q) == guess.charAt(j)) {
                            num++;
                            d += guess.charAt(q);
                            q++;
                            isDuplicate = true;
                        }

                    }
                }
                for (int q = 0; q < d.length(); q++) {
                    for (int j = q + 1; j < d.length(); j++) {
                        if (d.charAt(q) == d.charAt(j)) {
                            num--;
                            d = d.substring(0, j);

                        }
                    }
                }





                ArrayList<String> dups = new ArrayList<>();

                if(isDuplicate) {
                    for (int i = 0; i < d.length(); i++) {
                        dups.add(d.charAt(i) + "");
                    }


                    for (int i = 0; i < dups.size(); i++) {
                        if (aWord.contains(dups.get(i))) {
                            d = dups.get(i);
                        }
                    }
                }






                if(isDuplicate) {
                    int q = 0;
                    int u = 0;

                    for (int j = place - 5; j < place; j++) {
                        if (aWord.substring(q, q + 1).equals(dups.get(0))) {
                            numWord.add(in);
                        }
                        q++;
                        in++;
                    }

                    for (int j = place - 5; j < place; j++) {
                        if (guess.substring(u, u + 1).equals(dups.get(0))) {
                            numGuess.add(ni);
                        }
                        u++;
                        ni++;
                    }
                }



                if (guess.equals(aWord)) {
                    Label l = new Label("You got the word");
                    l.setFont(Font.font("comic-sans", FontWeight.BOLD, FontPosture.REGULAR, 20));
                    for (int i = place - 5; i < place; i++) {
                        displayBtn[i].setStyle("-fx-background-color:#538D4E");
                    }
                    headerPane.setAlignment(Pos.CENTER);
                    headerPane.add(l, 4, 10);
                    headerPane.setMargin(l, new Insets(5));
                    guess = "";
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> {

                                stage.close();
                            });

                        }
                    }, 3000);

                } else {
                    for (int i = 0; i < guess.length(); i++) {
                        if (guess.charAt(i) == aWord.charAt(i)) {
                            displayBtn[r].setStyle("-fx-background-color: #538D4E");
                            for (int j = 0; j < keys.length; j++) {
                                if (keys[j].getText().equals(guess.charAt(i) + "")) {
                                    keys[j].setStyle("-fx-background-color:#538D4E");
                                    keys[j].setTextFill(Color.WHITE);
                                }
                            }

                        } else if (aWord.contains(guess.charAt(i) + "")) {
                            displayBtn[r].setStyle("-fx-background-color: #B49F3A");
                            for (int j = 0; j < keys.length; j++) {
                                if (keys[j].getText().equals(guess.charAt(i) + "")) {
                                    keys[j].setStyle("-fx-background-color:#B49F3A");
                                    keys[j].setTextFill(Color.WHITE);
                                }
                            }

                        } else {
                            displayBtn[r].setStyle("-fx-background-color: gray");
                            for (int j = 0; j < keys.length; j++) {
                                if (keys[j].getText().equals(guess.charAt(i) + "")) {
                                    keys[j].setStyle("-fx-background-color:gray");
                                    keys[j].setTextFill(Color.WHITE);
                                }
                            }

                        }


                        r++;
                    }
                    guess = "";
                    size += 5;
                }
            } else {
                Label l = new Label("NOT IN LIST");
                l.setFont(Font.font("comic sans", FontWeight.BOLD, FontPosture.REGULAR, 20));
                l.setStyle("-fx-display: flex; -fx-position: absolute;");
                headerPane.setAlignment(Pos.CENTER);
                headerPane.add(l, 4, 10);
                headerPane.setMargin(l, new Insets(5));


                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            headerPane.getChildren().remove(l);
                        });

                    }
                }, 2000);
            }

            if (inList) {
                if (!displayBtn[29].getText().equals("")) {
                    Label l = new Label("The word is " + aWord);
                    l.setFont(Font.font("comic-sans", FontWeight.BOLD, FontPosture.REGULAR, 25));
                    l.setStyle("-fx-display: flex; -fx-position: absolute;");
                    headerPane.setAlignment(Pos.CENTER);
                    headerPane.add(l, 4, 10);
                    headerPane.setMargin(l, new Insets(5));
                    keys[28].setDisable(true);
                    keys[27].setDisable(true);

                }
            }

            if(isDuplicate){
                for (int i = 0; i < numGuess.size(); i++) {
                    if (numGuess.size() == numWord.size()) {
                        break;
                    } else if (numWord.contains(numGuess.get(i))) {
                        overall.add(numGuess.get(i));
                        m++;
                    } else {
                        displayBtn[numGuess.get(i)].setStyle("-fx-background-color:gray");
                    }

                }

                if (overall.size() < numWord.size()) {
                    if (!overall.contains(numGuess.get(numGuess.size() - 1))) {
                        overall.add(numGuess.get(numGuess.size() - 1));
                    }

                }

                for (int i = 0; i < overall.size(); i++) {

                    if (numWord.contains(overall.get(i))) {
                        displayBtn[overall.get(i)].setStyle("-fx-background-color:#538D4E");
                    } else {
                        displayBtn[overall.get(i)].setStyle("-fx-background-color:#B49F3A");

                    }
                }
                for (int i = 0; i < overall.size(); i++) {
                    newOver.add(overall.get(i));
                }
            } else{
                in+=(place-in);
                ni+=(place-ni);
            }

        }

        if (s.equals("BACK_SPACE")) {
            guess = guess.substring(0, guess.length()-1);
            displayBtn[place-1].setText("");
            place -= 1;
        }

    }


}
