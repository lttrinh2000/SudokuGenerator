package UI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import GameMechanic.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class UIController implements Initializable{
    @FXML
    AnchorPane tfContainer;
    TextField[][] boardUI;
    List<TextField> inputList = new ArrayList<>();
    TextField[][] tfArray = new TextField[9][9];
    SudokuBoard existGame;
    Random randomVal = new Random();

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
        new Thread(() -> {
                for (int i=0; i<9; i++) {
                    for (int j=0; j<9; j++) {
                        TextField text = tfArray[i][j];
                        if (board[i][j] != 0) {
                            try {
                                text.clear();
                                text.setText(String.valueOf(board[i][j]));

                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        else
                            text.setText("");
                    }
                }
        }).start();
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

    public void resetButton( ActionEvent e ) {
        setValueToBoardUI(existGame.getBoard());
    }

    public void generateButton( ActionEvent e ) {
        createGame();
    }

    public void solveButton( ActionEvent e ) throws InterruptedException {
        GeneratePuzzle solution = new GeneratePuzzle(existGame);
        while (true) {
            if ( solution.solve(randomVal, boardUI, true) )
                break;
        }
 
        existGame.printBoard();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createGame();
    }
    
}