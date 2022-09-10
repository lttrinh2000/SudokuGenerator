package GameMechanic;

import java.util.Arrays;

public class SudokuBoard {
	public int[][] board;
	public int[][] originalBoard;

	public SudokuBoard() {
		board = new int[9][9];

		for (int[] row: board)
    			Arrays.fill(row, 0);
		
		originalBoard = board;
	}

	public SudokuBoard(int[][] existBoard) {
		board = existBoard;
	}

	public int[][] getBoard() {
		return board;
	}

	public int[][] getOriginalBoard() {
		return originalBoard;
	}

	public void setValToBoard(int val, int x, int y) {
		if (val >= 1 && val <= 9 && x >= 0 && x <= 8 && y >= 0 && y <= 8) {
			board[x][y] = val;
		}
		else {
			System.out.println("Assign value fail at index " + x + " " + y);
		}
	}

	public void printBoard() {
		for (int i=0; i<9; i++) {
			if ( i % 3 == 0 && i != 0)
				System.out.println( "-  -  -  -  -  -  -  -" );
			for (int j=0; j<9; j++) {
				if ( j % 3 == 0 && j != 0)
					System.out.print(" | ");
				System.out.print(board[i][j] + " ");
			}
			System.out.print("\n");
		}

		System.out.println("=======================");
	}

	public int[] findEmpty() {
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				if (board[i][j] == 0)
					return new int[]{i,j};
			}
		}

		return new int[]{};
	}

}
