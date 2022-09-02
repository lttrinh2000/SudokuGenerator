import Mechanics.*;
import UI.*;
import java.util.Random;

import Mechanics.GeneratePuzzle;

public class Driver
{
    public static void main(String []args)
    {
        SudokuBoard game = new SudokuBoard();
        GeneratePuzzle puzzle = new GeneratePuzzle(game);
        Random randomVal = new Random();

        puzzle.solve(randomVal);
        puzzle.hideSolution();
        game.printBoard();
        puzzle.solve(randomVal);
        game.printBoard();
    }
}
