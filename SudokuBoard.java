import java.util.Arrays;

class SudokuBoard {
	public int[][] board;

	public SudokuBoard() {
		board = new int[9][9];

		for (int[] row: board)
    			Arrays.fill(row, 0);
	}

	public int[][] getBoard() {
		return board;
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

		System.out.print("\n");
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
