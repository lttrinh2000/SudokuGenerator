package UI;
import GameMechanic.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


public class UIController implements Initializable{
    @FXML
    private AnchorPane tfContainer;
    private TextField[][] boardUI;
    private List<TextField> inputList = new ArrayList<>();
    private TextField[][] tfArray = new TextField[9][9];
    private SudokuBoard existGame;
    private Random randomVal = new Random();

    public void copyOriginalBoard( SudokuBoard game ) {
        existGame = game;
    }

    public TextField[][] addAllChilds(AnchorPane container) {
        for (Node child : container.getChildren()) {
            if (child instanceof TextField)
                inputList.add( (TextField)child );
        }

        int tf = 0;
        for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                tfArray[i][j] = inputList.get(tf);
                tf++;
            }
        }

        return tfArray;
    }

    public void setValueToBoardUI( int[][] board ) {
        Timeline timer = new Timeline(new KeyFrame(
            Duration.millis(1),
            event -> {
                for (int i=0; i<9; i++) {
                    for (int j=0; j<9; j++) {
                        TextField text = tfArray[i][j];
                        
                        if (board[i][j] != 0) {
                            text.clear();
                            text.setText(String.valueOf(board[i][j]));
                            text.setDisable(true);
                        }
                        else
                            text.setText("");
                    }
                }
            }
        ));
        
        timer.play();
    }


    public void createGame() {
        boardUI = addAllChilds(tfContainer);
        SudokuBoard game = new SudokuBoard();
        GeneratePuzzle puzzle = new GeneratePuzzle(game);

        puzzle.solve(randomVal, boardUI, false);
        puzzle.hideSolution();
        game.printBoard();
        copyOriginalBoard(game);
        setValueToBoardUI(game.getBoard());
    }

    public void textFieldInput( ActionEvent e ) {
        TextField tf = (TextField)e.getTarget();
        int val = Integer.valueOf(tf.getText());

        if (val >=1 && val <=9) {

        }
    }

    public void resetButton( ActionEvent e ) {
        existGame.printBoard();
        setValueToBoardUI(existGame.getBoard());
    }

    public void generateButton( ActionEvent e ) {
        for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                tfArray[i][j].setDisable(false);
            }
        }
        createGame();
    }

    public void solveButton( ActionEvent e ) throws InterruptedException {
        GeneratePuzzle solution = new GeneratePuzzle(existGame);
        while (true) {
            if ( solution.solve(randomVal, boardUI, true) )
                break;
        }
        setValueToBoardUI(existGame.getBoard());
        existGame.printBoard();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createGame();
    }
    
}