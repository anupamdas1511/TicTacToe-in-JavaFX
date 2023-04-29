package com.fx.tictactoejavafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Arrays;

public class HelloApplication extends Application {
    // All GUI variables
    private final GridPane gridPane = new GridPane();
    private final BorderPane borderPane = new BorderPane();
    private final Label title = new Label("Tic Tac Toe Game");
    private final Button restartButton = new Button("Restart Now");
    Font font = Font.font("Roboto", FontWeight.BOLD, 30);
    private final Button[] btns = new Button[9];

    // All Logic Variables
    private boolean gameOver = false;
    private int activePlayer = 0;
    private final String[] players = {"O", "X"};
    private final int[] gameStates = {-1, -1, -1, -1, -1, -1, -1, -1, -1};
    private final int[][] winningPositions = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6}
    };

    @Override
    public void start(Stage stage) {
        this.createGUI();
        this.handleEvent();
        Scene scene = new Scene(borderPane, 550, 650);
        stage.setTitle("Tic Tac Toe");
        stage.setScene(scene);
        stage.show();
    }

    // This function is for creating gui
    private void createGUI() {
        // creating title
        title.setFont(font);
        // creating restart button
        restartButton.setFont(font);
        restartButton.setDisable(!gameOver);

        // setting title and restart button to borderpane
        borderPane.setTop(title);
        borderPane.setBottom(restartButton);
        // setting borderpane components to center
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(restartButton, Pos.CENTER);
        // adding padding to borderpane
        borderPane.setPadding(new Insets(20, 20, 20, 20));

        // working on 9 game buttons
        int label = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button();
                button.setId(String.valueOf(label));
                button.setFont(font);
                button.setPrefHeight(150);
                button.setPrefWidth(150);
                gridPane.add(button, j, i); // component, column, row
                gridPane.setAlignment(Pos.CENTER);
                btns[label++] = button;
            }
        }
        borderPane.setCenter(gridPane);
    }

    // method for handling events
    private void handleEvent() {
        restartButton.setOnAction(actionEvent -> {
            gameOver = false;
            activePlayer = 0;
            for (Button button : btns) {
                button.setGraphic(null);
            }
            Arrays.fill(gameStates, -1);
            restartButton.setDisable(true);
            System.out.println("Restart button clicked");
        });
        for (Button button : btns) {
            button.setOnAction(actionEvent -> {
                Button btn = (Button) actionEvent.getSource();
                int idS = Integer.parseInt(btn.getId());
                if (gameOver) {
                    // game Over and print msg
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setContentText("Game Over!! Try to restart the game");
                    alert.show();
                } else {
                    // game is not over to do chances
                    if (gameStates[idS] == -1) {
                        // proceed
                        if (activePlayer == 0) {
                            btn.setGraphic(new ImageView(
                                    new Image("file:src/main/resources/assets/circle.png", 100, 100, false, false)
                            ));
                        } else {
                            btn.setGraphic(new ImageView(
                                    new Image("file:src/main/resources/assets/cross.png", 100, 100, false, false)
                            ));
                        }
                        gameStates[idS] = activePlayer;
                        checkForWinner();
                        activePlayer = (activePlayer == 1) ? 0 : 1;
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setContentText("Player " + players[activePlayer] + " has already marked this place");
                        alert.show();
                    }
                }
            });
        }
    }

    // This method checks for winners
    private void checkForWinner() {
        if (gameOver)
            return;
        for (int i = 0; i < 8; i++) {
            if (isWinner(winningPositions[i])) {
                // activePlayer is the winner
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Message");
                alert.setContentText("Player " + players[activePlayer] + " won");
                alert.show();
                gameOver = true;
                restartButton.setDisable(false);
                break;
            }
        }
    }

    private boolean isWinner(int[] pos) {
        if (gameStates[pos[0]] == -1) {
            return false;
        }
        return (gameStates[pos[0]] == gameStates[pos[1]])
                && (gameStates[pos[0]] == gameStates[pos[2]]);
    }

    public static void main(String[] args) {
        launch();
    }
}