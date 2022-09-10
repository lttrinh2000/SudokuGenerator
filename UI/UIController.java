package UI;
import GameMechanic.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class UIController implements Initializable{
    @FXML
    private AnchorPane tfContainer;
    @FXML
    private Label announcement;

    private List<TextField> inputList = new ArrayList<>();
    private TextField[][] tfArray = new TextField[9][9];
    private SudokuBoard existGame;
    private int[][] originalBoard = new int[9][9];
    private Random randomVal = new Random();

    public void copyOriginalBoard( SudokuBoard game ) {
        existGame = game;
        int[][] board = game.getBoard();

		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				originalBoard[i][j] = board[i][j];
			}
		}
    }

    public TextField[][] addAllChilds(AnchorPane container) {
        for (Node child : container.getChildren()) {
            if (child instanceof TextField)
                inputList.add( (TextField)child );
        }

        int tf = 0;
        for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                TextField text = inputList.get(tf);
                tfArray[i][j] = text;
                text.setId(i + "," + j);
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
                            updateUI("Generated New Puzzle", text, Color.BLACK);
                    }
                }
            }
        ));
        
        timer.play();
    }

    public void updateUI(String res, TextField tf,  Color c) {
        Timeline timer = new Timeline(new KeyFrame(
            Duration.millis(1),
            event -> {
                    announcement.setTextFill(c);
                    announcement.setText(res);
                    tf.setBorder( new Border(new BorderStroke(c, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)) );
            }
        ));
            
        timer.play();
    }

    public int[] getIndex( TextField tf ) {

        String id = tf.getId();
        List<String> indexes = Arrays.asList(id.split(","));
        int x =  Integer.parseInt(indexes.get(0));
        int y = Integer.parseInt(indexes.get(1));

        return new int[]{x,y};
    }

    public void createGame() {
        addAllChilds(tfContainer);
        SudokuBoard game = new SudokuBoard();
        GeneratePuzzle puzzle = new GeneratePuzzle(game);
        int[][] board = game.getBoard();

        puzzle.solve(board, randomVal);
        puzzle.hideSolution();
        game.printBoard();
        copyOriginalBoard(game);
        setValueToBoardUI(board);
    }

    public void textFieldInput( ActionEvent e ) {
        TextField tf = (TextField)e.getTarget();
        GeneratePuzzle checkInputValid = new GeneratePuzzle();
        int[][] board = existGame.getBoard();
        int[] index = getIndex(tf);
        int val = Integer.valueOf(tf.getText());
        int x = index[0];
        int y = index[1];

        if (val >=1 && val <=9) {
            if ( checkInputValid.validNum(board, val, x, y) ) {
                existGame.setValToBoard(val, x, y);
                updateUI("Number is valid", tf, Color.GREEN);
            }
            else {
                updateUI("Number is violate Sudoku rule", tf, Color.RED);
            }
        }
        else {
            updateUI("Please enter number from 1 to 9", tf, Color.RED);
        }
    }

    public void resetButton( ActionEvent e ) {
        existGame.printBoard();
        setValueToBoardUI(existGame.getBoard());
    }

    public void generateButton( ActionEvent e ) {
        for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++) {
                TextField tf = tfArray[i][j];
                tf.setDisable(false);
            }
        }
        createGame();
    }

    public void solveButton( ActionEvent e ) {
        GeneratePuzzle solution = new GeneratePuzzle(existGame);

        while (true) {
            if ( solution.solve(originalBoard, randomVal) )
                break;
        }
        setValueToBoardUI(originalBoard);
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createGame();
    }
    
}