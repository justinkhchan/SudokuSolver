import java.util.Arrays;


public class main {

	private static final int GRID_LENGTH = 9;
	
	public main() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// original puzzle grid
		// easy puzzle
		/*int[][] puzzleGrid = { 
			{0, 6, 5, 4, 8, 0, 0, 3, 0},
			{0, 1, 9, 0, 0, 0, 5, 4, 0},
			{0, 0, 0, 0, 0, 9, 0, 8, 0},
			{5, 0, 0, 0, 2, 3, 4, 0, 1},
			{0, 0, 6, 0, 7, 0, 8, 0, 0},
			{7, 0, 1, 8, 9, 0, 0, 0, 3},
			{0, 8, 0, 9, 0, 0, 0, 0, 0},
			{0, 5, 7, 0, 0, 0, 6, 1, 0},
			{0, 3, 0, 0, 1, 8, 9, 5, 0}
		};*/
		// hard puzzle
		/*int[][] puzzleGrid = {
			{0, 7, 0, 8, 0, 0, 0, 6, 0},
			{2, 0, 0, 0, 0, 0, 7, 0, 8},
			{0, 0, 0, 1, 2, 0, 0, 0, 3},
			{0, 8, 0, 6, 0, 9, 0, 0, 0},
			{9, 0, 0, 0, 0, 0, 0, 0, 4},
			{0, 0, 0, 2, 0, 1, 0, 5, 0},
			{5, 0, 0, 0, 1, 4, 0, 0, 0},
			{1, 0, 3, 0, 0, 0, 0, 0, 5},
			{0, 9, 0, 0, 0, 8, 0, 4, 0}
		};*/
		// very hard puzzle?
		/*int[][] puzzleGrid = {
			{9, 0, 1, 0, 2, 0, 0, 0, 0},
			{0, 0, 0, 0, 1, 0, 0, 4, 0},
			{4, 0, 0, 0, 0, 7, 0, 8, 0},
			{7, 0, 0, 9, 0, 0, 0, 0, 6},
			{0, 8, 0, 0, 3, 0, 0, 1, 0},
			{5, 0, 0, 0, 0, 1, 0, 0, 4},
			{0, 2, 0, 3, 0, 0, 0, 0, 8},
			{0, 6, 0, 0, 7, 0, 0, 0, 0},
			{0, 0, 0, 0, 6, 0, 1, 0, 5}
		};*/
		int[][] puzzleGrid = {
				{9, 0, 1, 0, 2, 0, 0, 0, 0},
				{0, 0, 8, 0, 1, 0, 0, 4, 0},
				{4, 0, 0, 0, 0, 7, 0, 8, 0},
				{7, 0, 0, 9, 0, 0, 8, 0, 6},
				{0, 8, 0, 0, 3, 0, 0, 1, 0},
				{5, 0, 0, 0, 0, 1, 0, 0, 4},
				{0, 2, 0, 3, 0, 0, 0, 0, 8},
				{0, 6, 0, 0, 7, 0, 0, 0, 0},				
				{0, 0, 0, 0, 6, 0, 1, 0, 5}
		};
			
		int[][] currGrid = new int[GRID_LENGTH][GRID_LENGTH];	// current working grid
		int[] numbersLeft = {9, 9, 9, 9, 9, 9, 9, 9, 9};	// how many numbers have not been placed	
		boolean changesMade = false;	// tracks if any changes were made
		
		copyContents(puzzleGrid, currGrid);
		initialCount(currGrid, numbersLeft);
		
		System.out.println(Arrays.toString(numbersLeft));
		
		printGrid(currGrid);
		changesMade = true;
		
		int iterationCount = 0;
		while (changesMade == true) {
			iterationCount++;
			System.out.println("Iteration " + iterationCount);
			changesMade = false;
			changesMade = PlaceInNumbers(currGrid, numbersLeft);
			pivotGrid(currGrid);	
			// pivoting (flipping around) the grid and parsing horizontally is
			// the same as parsing vertically
			changesMade = PlaceInNumbers(currGrid, numbersLeft);
			pivotGrid(currGrid); 
			// pivoting the grid again reverts it back to
			// the original orientation
			printGrid(currGrid);
		}
		
		
		//ParseSubgrids(currGrid, 5, 0, numbersLeft, changesMade);
		printGrid(currGrid);

		/*makeChange(currGrid);
		printGrid(puzzleGrid);
		printGrid(currGrid);*/

	}
	
	/*
	 * Goes through each number in the grid and passes them  to ParseSubgrids
	 * to derive more numbers
	 */
	private static boolean PlaceInNumbers(int[][] currGrid, int[] numbersLeft) {
		boolean changesMade = false;
		for (int row = 0; row < GRID_LENGTH; row++) {
			for (int col = 0; col < GRID_LENGTH; col++) {
				int currNumber = currGrid[row][col];
				if (currNumber != 0) {
					if (ParseSubgrids(currGrid, row, col, numbersLeft)) {
						changesMade = true;
					}
				}
			}
		}
		return changesMade;
		
	}

	/*
	 * Uses the current number and checks against adjacent columns
	 */
	private static boolean ParseSubgrids(int[][] currGrid, int row, int col,
			int[] numbersLeft) {
		int subGridRowSection = row / 3;
		int subGridColSection = col / 3;

		int currRow = 0;
		int currGridValue = currGrid[row][col];
		
		int parseCurrCol = 0;
		int parseCurrRow = 0;

		boolean changesMade = false;

		for (int rowInc = 0; rowInc < 3; rowInc++) {
			currRow = ((subGridRowSection * 3) + rowInc);
			if (currRow != row) {	// don't parse the current row
				for (int currColSection = 0; currColSection < 3; currColSection++) {
					if (currColSection != subGridColSection) { // don't parse the current column set
						//System.out.println(currRow + ", " + (currCol * 3) + " to " + (currCol * 3 + 2));
						for (int parseCol = 0; parseCol < 3; parseCol++) {
							parseCurrCol = (currColSection * 3) + parseCol;
							if (currGrid[currRow][parseCurrCol] == currGridValue) {	// match found
								if (parseMatch(currGrid, row, col, numbersLeft, currRow, parseCurrCol)) {
									changesMade = true;
								}
							}
						}
						// if column is full or cannot be put in
						if (currGrid[currRow][(currColSection * 3)] != currGridValue &&
								currGrid[currRow][(currColSection * 3)+1] != currGridValue && 
								currGrid[currRow][(currColSection * 3)+2] != currGridValue ) {
							boolean checkCol1 = existsInCol(currGridValue, (currColSection * 3), currGrid);
							boolean checkCol2 = existsInCol(currGridValue, (currColSection * 3)+1, currGrid);
							boolean checkCol3 = existsInCol(currGridValue, (currColSection * 3)+2, currGrid);
							// check to see if the cells are already occupied
							if (currGrid[currRow][(currColSection * 3)] != 0) {
								checkCol1 = true;
							}
							if (currGrid[currRow][(currColSection * 3)+1] != 0) {
								checkCol2 = true;
							}
							if (currGrid[currRow][(currColSection * 3)+2] != 0) {
								checkCol3 = true;
							}
							if (checkCol1 && checkCol2 && checkCol3) {
								int targetRow = (subGridRowSection * 3) + (3 - (currRow % 3) - (row % 3));
								int targetColumn = 3 - (currColSection % 3) - (subGridColSection %3);
								if (parseMatch(currGrid, row, col, numbersLeft, targetRow, parseCurrCol)) {
									changesMade = true;
								}
							}
						}

					}
				}
			}
		}
		return changesMade;
	}

	/*
	 * Found two "matches" in two columns, so check the three cells within 
	 * the third column for a match
	 */
	private static boolean parseMatch(int[][] currGrid, int row, int col,
			int[] numbersLeft, int currRow,
			int parseCurrCol) {
		int parseTargetCol;
		int parseTargetRow;
		int subGridRowSection = row / 3;
		int currGridValue = currGrid[row][col];
		boolean changesMade = false;
		//System.out.println("Match found at: " + currRow + ", " + parseCurrCol);
		parseTargetRow = (subGridRowSection * 3) + (3 - (currRow % 3) - (row % 3));
		parseTargetCol =  (3 - (parseCurrCol / 3) - (col / 3)) * 3;
		//System.out.println("Parsing: " + parseTargetRow + ", " + parseTargetCol);
		
		// check to make sure the value isn't already set
		if (currGrid[parseTargetRow][parseTargetCol] != currGridValue &&
				currGrid[parseTargetRow][parseTargetCol+1] != currGridValue && 
				currGrid[parseTargetRow][parseTargetCol+2] != currGridValue ) {
			boolean checkCol1 = existsInCol(currGridValue, parseTargetCol, currGrid);
			boolean checkCol2 = existsInCol(currGridValue, parseTargetCol + 1, currGrid);
			boolean checkCol3 = existsInCol(currGridValue, parseTargetCol + 2, currGrid);
			
			// check to see if the cells are already occupied
			if (currGrid[parseTargetRow][parseTargetCol] != 0) {
				checkCol1 = true;
			}
			if (currGrid[parseTargetRow][parseTargetCol+1] != 0) {
				checkCol2 = true;
			}
			if (currGrid[parseTargetRow][parseTargetCol+2] != 0) {
				checkCol3 = true;
			}

			if (checkCol1 && checkCol2) {
				currGrid[parseTargetRow][parseTargetCol+2] = currGridValue;
				numbersLeft[currGridValue-1]--;
				changesMade = true;
			} else if (checkCol1 && checkCol3) {
				currGrid[parseTargetRow][parseTargetCol+1] = currGridValue;
				numbersLeft[currGridValue-1]--;
				changesMade = true;
			} else if (checkCol2 && checkCol3) {
				currGrid[parseTargetRow][parseTargetCol] = currGridValue;
				numbersLeft[currGridValue-1]--;
				changesMade = true;
			}

		}
		return changesMade;
	}

	
	private static boolean existsInCol(int value, int col, int[][] currGrid) {
		boolean existsFlag = false;
		for (int row = 0; row < GRID_LENGTH; row++) {
			if (currGrid[row][col] == value) {
				existsFlag = true;
			}
		}
		return existsFlag;
	}

	private boolean existsInRow(int value, int row, int[][] currGrid) {
		boolean existsFlag = false;
		for (int col = 0; col < GRID_LENGTH; col++) {
			if (currGrid[row][col] == value) {
				existsFlag = true;
			}
		}
		return existsFlag;
	}
	
	/**
	 * Initially counts how many numbers are still needed
	 */
	private static void initialCount(int[][] currGrid, int[] numbersLeft) {
		for (int row = 0; row < GRID_LENGTH; row++) {
			for (int col = 0; col < GRID_LENGTH; col++) {
				int currNumber = currGrid[row][col];
				if (currNumber != 0) {
					numbersLeft[currNumber -1]--;
				}
			}
		}
	}

	/**
	 * Copies the contents of one grid over to another
	 */
	private static void copyContents(int[][] oldGrid, int[][] newGrid) {
		for (int copyCount = 0; copyCount < GRID_LENGTH; copyCount++) {
			System.arraycopy(oldGrid[copyCount], 0, newGrid[copyCount], 0, oldGrid[copyCount].length);
		}
	}
	
	

	/*private static void makeChange(int[][] puzzleGrid) {
		puzzleGrid[0][0] = 5;
	}*/

	/*
	 * Formats and prints a grid
	 */
	private static void printGrid(int[][] puzzleGrid) {
		for (int row = 0; row < GRID_LENGTH; row++) {
			if (row % 3 == 0) {
				System.out.println("----------------------");
			}
			for (int col = 0; col < GRID_LENGTH; col++) {
				if (col % 3 == 0) {
					System.out.print("|");
				}
				System.out.print(puzzleGrid[row][col] + " ");
			}
			System.out.println("|");
		}
		System.out.println("----------------------");
	}
	
	private static void pivotGrid(int[][] puzzleGrid) {
		int[][] newGrid = new int[GRID_LENGTH][GRID_LENGTH];	// current working grid
		for (int row = 0; row < GRID_LENGTH; row++) {
			for (int col = 0; col < GRID_LENGTH; col++) {
				newGrid[col][row] = puzzleGrid[row][col];
			}
		}
		copyContents(newGrid, puzzleGrid);
	}

}
