import java.util.Random;

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
    }
}
