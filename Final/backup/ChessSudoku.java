package finalproject;

import java.util.*;
import java.io.*;


public class ChessSudoku
{
	/* SIZE is the size parameter of the Sudoku puzzle, and N is the square of the size.  For
	 * a standard Sudoku puzzle, SIZE is 3 and N is 9.
	 */
	public int SIZE, N;

	/* The grid contains all the numbers in the Sudoku puzzle.  Numbers which have
	 * not yet been revealed are stored as 0.
	 */
	public int[][] grid;

	/* Booleans indicating whether of not one or more of the chess rules should be
	 * applied to this Sudoku.
	 */
	public boolean knightRule;
	public boolean kingRule;
	public boolean queenRule;


	// Field that stores the same Sudoku puzzle solved in all possible ways
	public HashSet<ChessSudoku> solutions = new HashSet<ChessSudoku>();

	private boolean isInRow(int row, int number) {
		for (int i = 0; i < N; i++)
			if (grid[row][i] == number) return true;

		return false;
	}

	private boolean isInCol(int col, int number) {
		for (int i = 0; i < N; i++)
			if (grid[i][col] == number) return true;

		return false;
	}

	private boolean isInBox(int row, int col, int number) {
		int c = col - col % SIZE, r = row - row % SIZE;

		for (int i = r; i < r + SIZE; i++)
			for (int j = c; j < c + SIZE; j++)
				if (grid[i][j] == number) return true;

		return false;
	}

	private boolean isInKingMove(int row, int col, int number) {
		int[] r = {row-1, row+1}, c = {col-1, col+1};

		for (int i=0; i < 2; i++) {
			if (r[i] < 0 || r[i] >= N) r[i] = row;
			if (c[i] < 0 || c[i] >= N) c[i] = col;
			if (c[1-i] < 0 || c[1-i] >= N) c[1-i] = col;

			if (grid[r[i]][c[i]] == number || grid[r[i]][c[1-i]] == number) return true;
		}

		return false;
	}

	private boolean isInKnightMove(int row, int col, int number) {
		int[] r = {row-2, row-1, row+1, row+2};
		int[] c = {col-1, col-2, col+2, col+1};

		for (int i=0; i < 4; i++) {
			if (r[i] < 0 || r[i] >= N) r[i] = row;
			if (c[i] < 0 || c[i] >= N) c[i] = col;
			if (c[3-i] < 0 || c[3-i] >= N) c[3-i] = col;

			if (grid[r[i]][c[i]] == number || grid[r[i]][c[3-i]] == number) return true;
		}

		return false;
	}

	private boolean inInDiagonalOnCorner(int r, int c, int rDiff, int cDiff, int number){
		while (0 <= r  && r < N && 0 <= c && c < N){
			if (grid[r][c] == number) return true;
			r += rDiff;
			c += cDiff;
		}
		return false;
	}

	private boolean isInDiagonal(int row, int col, int number) {
		if (inInDiagonalOnCorner(row+1, col+1, 1, 1, number)) return true;
		if (inInDiagonalOnCorner(row+1, col-1, 1, -1, number)) return true;
		if (inInDiagonalOnCorner(row-1, col+1, -1, 1, number)) return true;
		return (inInDiagonalOnCorner(row - 1, col - 1, -1, -1, number));
	}

	private boolean isValid(int row, int col, int number) {
		if (knightRule && (isInKnightMove(row,col,number))) return false;
		if (kingRule && (isInKingMove(row,col,number))) return false;
		if (number == N && queenRule && (isInDiagonal(row,col,number))) return false;

		return !isInRow(row, number)  &&  !isInCol(col, number)  &&  !isInBox(row, col, number);
	}

	private ChessSudoku makeCopy() {
		ChessSudoku copy = new ChessSudoku(SIZE);

		copy.grid = new int[N][N];
		for( int i = 0; i < N; i++ ) {
			for (int j = 0; j < N; j++) {
				copy.grid[i][j] = this.grid[i][j];
			}
		}
		return copy;
	}

	private boolean simpleSolution() {
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {

				if (grid[r][c] == 0) {
					for (int n = 1; n <= N; n++) {
						if (isValid(r, c, n)) {
							grid[r][c] = n;
							if (simpleSolution()) return true;
							grid[r][c] = 0;
						}
					}
					return false;
				}

			}
		}
		return true;
	}

	private boolean findAllSolutions(int r, int c) {
		if (r == N - 1 && c == N) {
			return true;
		}

		if (c == N) {
			r++;
			c = 0;
		}

		boolean solved = false;

		if (grid[r][c] != 0) return findAllSolutions(r, c + 1);

		for (int n = 1; n <= N; n++) {
			if (isValid(r, c, n)) {
				grid[r][c] = n;
				if (findAllSolutions(r, c + 1)) return true;
				grid[r][c] = 0;
			}
		}
		grid[r][c] = 0;
		return false;
	}

	private void addSolutions(int r, int c) {
		if (r == N - 1 && c == N) {
			solutions.add(makeCopy());
			return;
		}

		if (c == N) {
			r++;
			c = 0;
		}

		boolean solved = false;

		if (grid[r][c] != 0) return findAllSolutions(r, c + 1);

		for (int n = 1; n <= N; n++) {
			if (isValid(r, c, n)) {
				grid[r][c] = n;
				if (findAllSolutions(r, c + 1)) return true;
				grid[r][c] = 0;
			}
		}
		grid[r][c] = 0;
		return false;
	}

	/* The solve() method should remove all the unknown characters ('x') in the grid
	 * and replace them with the numbers in the correct range that satisfy the constraints
	 * of the Sudoku puzzle. If true is provided as input, the method should find finds ALL 
	 * possible solutions and store them in the field named solutions. */
	public void solve(boolean allSolutions) {
		if (!allSolutions) simpleSolution();
		else findAllSolutions(0, 0);
	}


	/*****************************************************************************/
	/* NOTE: YOU SHOULD NOT HAVE TO MODIFY ANY OF THE METHODS BELOW THIS LINE. */
	/*****************************************************************************/

	/* Default constructor.  This will initialize all positions to the default 0
	 * value.  Use the read() function to load the Sudoku puzzle from a file or
	 * the standard input. */
	public ChessSudoku( int size ) {
		SIZE = size;
		N = size*size;

		grid = new int[N][N];
		for( int i = 0; i < N; i++ ) 
			for( int j = 0; j < N; j++ ) 
				grid[i][j] = 0;
	}


	/* readInteger is a helper function for the reading of the input file.  It reads
	 * words until it finds one that represents an integer. For convenience, it will also
	 * recognize the string "x" as equivalent to "0". */
	static int readInteger( InputStream in ) throws Exception {
		int result = 0;
		boolean success = false;

		while( !success ) {
			String word = readWord( in );

			try {
				result = Integer.parseInt( word );
				success = true;
			} catch( Exception e ) {
				// Convert 'x' words into 0's
				if( word.compareTo("x") == 0 ) {
					result = 0;
					success = true;
				}
				// Ignore all other words that are not integers
			}
		}

		return result;
	}


	/* readWord is a helper function that reads a word separated by white space. */
	static String readWord( InputStream in ) throws Exception {
		StringBuffer result = new StringBuffer();
		int currentChar = in.read();
		String whiteSpace = " \t\r\n";
		// Ignore any leading white space
		while( whiteSpace.indexOf(currentChar) > -1 ) {
			currentChar = in.read();
		}

		// Read all characters until you reach white space
		while( whiteSpace.indexOf(currentChar) == -1 ) {
			result.append( (char) currentChar );
			currentChar = in.read();
		}
		return result.toString();
	}


	/* This function reads a Sudoku puzzle from the input stream in.  The Sudoku
	 * grid is filled in one row at at time, from left to right.  All non-valid
	 * characters are ignored by this function and may be used in the Sudoku file
	 * to increase its legibility. */
	public void read( InputStream in ) throws Exception {
		for( int i = 0; i < N; i++ ) {
			for( int j = 0; j < N; j++ ) {
				grid[i][j] = readInteger( in );
			}
		}
	}


	/* Helper function for the printing of Sudoku puzzle.  This function will print
	 * out text, preceded by enough ' ' characters to make sure that the printint out
	 * takes at least width characters.  */
	void printFixedWidth( String text, int width ) {
		for( int i = 0; i < width - text.length(); i++ )
			System.out.print( " " );
		System.out.print( text );
	}


	/* The print() function outputs the Sudoku grid to the standard output, using
	 * a bit of extra formatting to make the result clearly readable. */
	public void print() {
		// Compute the number of digits necessary to print out each number in the Sudoku puzzle
		int digits = (int) Math.floor(Math.log(N) / Math.log(10)) + 1;

		// Create a dashed line to separate the boxes 
		int lineLength = (digits + 1) * N + 2 * SIZE - 3;
		StringBuffer line = new StringBuffer();
		for( int lineInit = 0; lineInit < lineLength; lineInit++ )
			line.append('-');

		// Go through the grid, printing out its values separated by spaces
		for( int i = 0; i < N; i++ ) {
			for( int j = 0; j < N; j++ ) {
				printFixedWidth( String.valueOf( grid[i][j] ), digits );
				// Print the vertical lines between boxes 
				if( (j < N-1) && ((j+1) % SIZE == 0) )
					System.out.print( " |" );
				System.out.print( " " );
			}
			System.out.println();

			// Print the horizontal line between boxes
			if( (i < N-1) && ((i+1) % SIZE == 0) )
				System.out.println( line.toString() );
		}
	}


	/* The main function reads in a Sudoku puzzle from the standard input, 
	 * unless a file name is provided as a run-time argument, in which case the
	 * Sudoku puzzle is loaded from that file.  It then solves the puzzle, and
	 * outputs the completed puzzle to the standard output. */
	public static void main( String args[] ) throws Exception {
		InputStream in = new FileInputStream("knightSudokuMedium3x3.txt");

		// The first number in all Sudoku files must represent the size of the puzzle.  See
		// the example files for the file format.
		int puzzleSize = readInteger( in );
		if( puzzleSize > 100 || puzzleSize < 1 ) {
			System.out.println("Error: The Sudoku puzzle size must be between 1 and 100.");
			System.exit(-1);
		}

		ChessSudoku s = new ChessSudoku( puzzleSize );
		
		// You can modify these to add rules to your sudoku
		s.knightRule = false;
		s.kingRule = false;
		s.queenRule = false;
		
		// read the rest of the Sudoku puzzle
		s.read( in );

		System.out.println("Before the solve:");
		s.print();
		System.out.println();

		// Solve the puzzle by finding one solution.
		s.solve(false);

		// Print out the (hopefully completed!) puzzle
		System.out.println("After the solve:");
		s.print();
	}
}

