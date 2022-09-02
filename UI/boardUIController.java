package UI;
import Mechanics.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class boardUIController implements Initializable{
    @FXML
    AnchorPane tfContainer;

    List<TextField> inputList = new ArrayList<>();

    public void addAllChilds(AnchorPane container) {
        for (Node child : container.getChildren()) {
            if (child instanceof TextField)
                inputList.add( (TextField)child );
        }
    }

    public void setValueToBoard( int[][] board ) {

        // Convert 2D array into 1D array
        int[] valArray = Stream.of(board) //we start with a stream of objects Stream<int[]>
                    .flatMapToInt(IntStream::of) //we I'll map each int[] to IntStream
                    .toArray();

        for (int i=0; i < inputList.size(); i++) {
            TextField t = inputList.get(i);
            t.setText( String.valueOf(valArray[i]) );
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addAllChilds(tfContainer);
        SudokuBoard game = new SudokuBoard();
        GeneratePuzzle puzzle = new GeneratePuzzle(game);
        Random randomVal = new Random();

        puzzle.solve(randomVal);
        puzzle.hideSolution();

        int[][] board = game.getBoard();
        setValueToBoard(board);
        
    }
    
}