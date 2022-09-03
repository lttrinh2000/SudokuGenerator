package UI;
import Mechanics.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class boardUIController implements Initializable{
    @FXML
    AnchorPane tfContainer;

    List<TextField> inputList = new ArrayList<>();
    TextField[][] tfArray = new TextField[9][9];
    int[][] hidenSolBoard;
    Random randomVal = new Random();

    public void copyOriginalBoard( int[][] board ) {
        hidenSolBoard = board;
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

    public void setValueToBoard( int[][] board ) {

        for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                tfArray[i][j].setText(String.valueOf(board[i][j]));
            }
        }
    }

    public SudokuBoard createGame() {
        TextField[][] boardUI = addAllChilds(tfContainer);
        SudokuBoard game = new SudokuBoard();
        GeneratePuzzle puzzle = new GeneratePuzzle(game);

        puzzle.solve(randomVal, boardUI);
        game.printBoard();
        puzzle.hideSolution();

        int[][] board = game.getBoard();
        copyOriginalBoard(board);
        setValueToBoard(board);

        return game;
    }

    public void resetButton( ActionEvent e ) {
        setValueToBoard(hidenSolBoard);
    }

    public void generateButton( ActionEvent e ) {
        createGame();
    }

    public void solveButton( ActionEvent e ) {

        SudokuBoard existGame = new SudokuBoard(hidenSolBoard);
        GeneratePuzzle revealSol = new GeneratePuzzle(existGame);
        revealSol.solve(randomVal, tfArray);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createGame();
    }
    
}