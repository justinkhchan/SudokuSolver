import java.util.*;


/**
 * @author Justin
 *
 */
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
		// very hard puzzle
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
		// impossible puzzle 2?
		int[][] puzzleGrid = {
			{0, 0, 4, 0, 0, 6, 0, 0, 7},
			{0, 7, 0, 0, 3, 0, 0, 8, 0},
			{5, 0, 0, 8, 0, 0, 6, 0, 0},
			{9, 0, 0, 4, 0, 0, 1, 0, 0},
			{0, 1, 0, 0, 5, 0, 0, 4, 0},
			{0, 0, 7, 0, 0, 1, 0, 0, 3},
			{0, 0, 3, 0, 0, 5, 0, 0, 1},
			{0, 9, 0, 0, 4, 0, 0, 2, 0},
			{8, 0, 0, 9, 0, 0, 4, 0, 0}
		};
		/*int[][] puzzleGrid = {
			{1, 0, 0, 0, 0, 7, 0, 9, 0},
			{0, 3, 0, 0, 2, 0, 0, 0, 8},
			{0, 0, 9, 6, 0, 0, 5, 0, 0},
			{0, 0, 5, 3, 0, 0, 9, 0, 0},
			{0, 1, 0, 0, 8, 0, 0, 0, 2},
			{6, 0, 0, 0, 0, 4, 0, 0, 0},
			{3, 0, 0, 0, 0, 0, 0, 1, 0},
			{0, 4, 0, 0, 0, 0, 0, 0, 7},
			{0, 0, 7, 0, 0, 0, 3, 0, 0}
		};*/
		// impossible puzzle 2?
		/*int[][] puzzleGrid = {
			{8, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 3, 6, 0, 0, 0, 0, 0},
			{0, 7, 0, 0, 9, 0, 2, 0, 0},
			{0, 5, 0, 0, 0, 7, 0, 0, 0},
			{0, 0, 0, 0, 4, 5, 7, 0, 0},
			{0, 0, 0, 1, 0, 0, 0, 3, 0},
			{0, 0, 1, 0, 0, 0, 0, 6, 8},
			{0, 0, 8, 5, 0, 0, 0, 1, 0},
			{0, 9, 0, 0, 0, 0, 4, 0, 0}
		};*/
		
		int[][] currGrid = new int[GRID_LENGTH][GRID_LENGTH];	// current working grid
		HashSet[][] pencilMarks = new HashSet[GRID_LENGTH][GRID_LENGTH];	// pencil marks for each cell
		int[] numbersLeft = {9, 9, 9, 9, 9, 9, 9, 9, 9};	// how many numbers have not been placed	
		boolean changesMade = false;	// tracks if any changes were made
		
		copyContents(puzzleGrid, currGrid);
		initialCount(currGrid, numbersLeft);
		initialPencils(currGrid, pencilMarks);
		clearNumbers(currGrid, pencilMarks);
		
		printGrid(currGrid, pencilMarks);
		changesMade = true;
		
		int iterationCount = 0;
		while (changesMade == true) {
			iterationCount++;
			System.out.println("Iteration " + iterationCount);
			changesMade = false;
			if (PlaceInNumbers(currGrid, pencilMarks, numbersLeft)) {
				changesMade = true;
			}
			pivotGridAndMarks(currGrid, pencilMarks);	
			// pivoting (flipping around) the grid and parsing horizontally is
			// the same as parsing vertically
			if (PlaceInNumbers(currGrid, pencilMarks, numbersLeft)) {
				changesMade = true;
			}
			pivotGridAndMarks(currGrid, pencilMarks); 
			// pivoting the grid again reverts it back to
			// the original orientation
			if (clearNumbers(currGrid, pencilMarks)) {	// update pencil marks
				changesMade = true;
			}
			if (clearMarkers(currGrid, pencilMarks)) {	// update pencil marks
				changesMade = true;
			}
			pivotGridAndMarks(currGrid, pencilMarks);
			if (clearMarkers(currGrid, pencilMarks)) {	// update pencil marks
				changesMade = true;
			}
			pivotGridAndMarks(currGrid, pencilMarks);
			printGrid(currGrid, pencilMarks);
		}
		
		//ParseSubgrids(currGrid, 5, 0, numbersLeft, changesMade);
		printGrid(currGrid, pencilMarks);

	}
	
	/****************************************
	 * 
	 * PENCIL MARKER METHODS
	 * 
	 *****************************************/
	
	private static boolean clearMarkers(int[][] currGrid,
			HashSet[][] pencilMarks) {
		boolean changesMade = false;
		if (clearColumns(currGrid, pencilMarks)) {
			changesMade = true;
		}
		
		return changesMade;
	}

	private static boolean clearColumns(int[][] currGrid,
			HashSet[][] pencilMarks) {
		boolean changesMade = false;
		for (int row = 0; row < GRID_LENGTH; row++) {
			for (int col = 0; col < GRID_LENGTH; col++) {
				if (currGrid[row][col] == 0) {
					ArrayList colVals = new ArrayList();
					colVals.add(col);
					if (removePencilsCol(currGrid, pencilMarks, row, col, colVals)) {
						changesMade = true;
					}
				}
			}
		}
		
		/*ArrayList colVals = new ArrayList();
		colVals.add(0);
		if (removePencilsCol(currGrid, pencilMarks, 1, 0, colVals)) {
			changesMade = true;
		}*/
		
		return changesMade;
	}

	private static boolean removePencilsCol(int[][] currGrid,
			HashSet[][] pencilMarks, int row, int col, ArrayList colVals) {
		boolean changesMade = false;
		//System.out.println(colVals.size());
		for (int traverseCol = col; traverseCol < GRID_LENGTH; traverseCol++) {
			
			if (currGrid[row][traverseCol] == 0) {
				colVals.add(traverseCol);
				HashSet totalPencils = new HashSet();
				for (int currVals = 0; currVals < colVals.size(); currVals++) {
					totalPencils.addAll(pencilMarks[row][(int)colVals.get(currVals)]);
					//System.out.print(pencilMarks[row][(int)colVals.get(currVals)]);
				}
				
				if (totalPencils.size() < colVals.size()) {
					//System.out.println("total: " + totalPencils);
					//System.out.println(colVals.size());
					for (int newCol = 0; newCol < GRID_LENGTH; newCol++) {
						if (currGrid[row][newCol] == 0) {
							boolean containsFlag = false;
							for (int containCount = 0; containCount < colVals.size(); containCount++) {
								if ((int)colVals.get(containCount) == newCol) {
									containsFlag = true;
								}
							}
							if (!containsFlag) {
								for (int pencilParse = 0; pencilParse < pencilMarks[row][newCol].size(); pencilParse++) {
									if (totalPencils.contains(pencilMarks[row][newCol].toArray()[pencilParse])) {
										//System.out.println("Removing " + pencilMarks[row][newCol].toArray()[pencilParse] + " from col " + newCol);
										pencilMarks[row][newCol].remove(pencilMarks[row][newCol].toArray()[pencilParse]);
										if (pencilMarks[row][newCol].size() == 1) {
											currGrid[row][newCol] = (int)pencilMarks[row][newCol].toArray()[0];
											System.out.println("Put in " + (int)pencilMarks[row][newCol].toArray()[0] + " at row " + 
												row + " col " + newCol);
										}
										pencilParse++;
										changesMade = true;
									}
								}
							}
						}
					}
					/*for (int currVals = 0; currVals < colVals.size(); currVals++) {
						System.out.print(pencilMarks[row][(int)colVals.get(currVals)] + " ");
					}
					System.out.println();*/
				}
				
				if (traverseCol < GRID_LENGTH) {
					if (removePencilsCol(currGrid, pencilMarks, row, traverseCol+1, colVals)) {
						changesMade = true;
					}
				}
				if (colVals.size() > 1) {
					colVals.remove(colVals.size()-1);
				}
			}
			
		}
		
		return changesMade;
	}

	private static boolean clearNumbers(int[][] currGrid,
			HashSet[][] pencilMarks) {
		boolean changesMade = false;
		for (int row = 0; row < GRID_LENGTH; row++) {
			for (int col = 0; col < GRID_LENGTH; col++) {
				if (currGrid[row][col] != 0) {
					if (removePencilMethods(currGrid, pencilMarks,
							row, col)) {
						changesMade = true;
					}
				}
			}
		}
		return changesMade;
	}

	private static boolean removePencilMethods(int[][] currGrid,
			HashSet[][] pencilMarks, int row, int col) {
		boolean changesMade = false;
		if (removePencilRow(currGrid, pencilMarks, row, col)) {
			changesMade = true;
		}
		if (removePencilCol(currGrid, pencilMarks, row, col)) {
			changesMade = true;
		}
		if (removePencilSubgrid(currGrid, pencilMarks, row, col)) {
			changesMade = true;
		}
		return changesMade;
	}

	private static boolean removePencilSubgrid(int[][] currGrid,
			HashSet[][] pencilMarks, int row, int col) {
		boolean changesMade = false;
		int subGridRowSection = row / 3;
		int subGridColSection = col / 3;
		int gridValue = currGrid[row][col];
		
		for (int rowInc = 0; rowInc < 3; rowInc++) {
			if (rowInc != row % 3) {
				for (int colInc = 0; colInc < 3; colInc++) {
					if (colInc != col % 3) {
						if (pencilMarks[(subGridRowSection*3) + rowInc][(subGridColSection*3) + colInc].remove(gridValue)) {
							changesMade = true;
						}						
					}
				}
			}
		}
		
		return changesMade;
	}

	private static boolean removePencilRow(int[][] currGrid,
			HashSet[][] pencilMarks, int row, int col) {
		boolean changesMade = false;
		int gridValue = currGrid[row][col];
		for (int travCol = 0; travCol < GRID_LENGTH; travCol++) {
			if (travCol != col) {
				if (pencilMarks[row][travCol].remove(gridValue)) {
					changesMade = true;
				}
			}
		}
		return changesMade;
	}
	
	private static boolean removePencilCol(int[][] currGrid,
			HashSet[][] pencilMarks, int row, int col) {
		boolean changesMade = false;
		int gridValue = currGrid[row][col];
		for (int travRow = 0; travRow < GRID_LENGTH; travRow++) {
			if (travRow != row) {
				if (pencilMarks[travRow][col].remove(gridValue)) {
					changesMade = true;
				}
			}
		}
		return changesMade;
	}
	/**
	 * Simply enter values into the pencilMarks HashSet array
	 */
	private static void initialPencils(int[][] currGrid, HashSet[][] pencilMarks) {
		for (int row = 0; row < GRID_LENGTH; row++) {
			for (int col = 0; col < GRID_LENGTH; col++) {
				pencilMarks[row][col] = new HashSet();
				if (currGrid[row][col] == 0) {
					for (int addVal = 1; addVal < 10; addVal++) {
						pencilMarks[row][col].add(addVal);
					}
				} else {
					pencilMarks[row][col].add(currGrid[row][col]);
					
				}
			}
		}
		
	}

	
	/****************************************
	 * 
	 * PARSE BY COLUMN METHODS
	 * 
	 *****************************************/
	
	/**
	 * Goes through each number in the grid and passes them  to ParseSubgrids
	 * to derive more numbers
	 */
	private static boolean PlaceInNumbers(int[][] currGrid, HashSet[][] pencilMarks, int[] numbersLeft) {
		boolean changesMade = false;
		for (int row = 0; row < GRID_LENGTH; row++) {
			for (int col = 0; col < GRID_LENGTH; col++) {
				int currNumber = currGrid[row][col];
				if (currNumber != 0) {
					if (ParseSubgrids(currGrid, pencilMarks, row, col, numbersLeft)) {
						changesMade = true;
					}
				}
			}
		}
		return changesMade;
		
	}

	/**
	 * Uses the current number and checks against adjacent columns
	 */
	private static boolean ParseSubgrids(int[][] currGrid, HashSet[][] pencilMarks, int row, int col,
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
								if (parseMatch(currGrid, pencilMarks, row, col, numbersLeft, currRow, parseCurrCol)) {
									changesMade = true;
								}
							}
						}
						// if column is full with over values
						// or if the value cannot be put in because of other rows
						if (currGrid[currRow][(currColSection * 3)] != currGridValue &&
								currGrid[currRow][(currColSection * 3)+1] != currGridValue && 
								currGrid[currRow][(currColSection * 3)+2] != currGridValue ) {
							boolean checkCol1 = checkValWithCol(currGrid, (currColSection * 3), currRow, currGridValue);
							boolean checkCol2 = checkValWithCol(currGrid, (currColSection * 3)+1, currRow, currGridValue);
							boolean checkCol3 = checkValWithCol(currGrid, (currColSection * 3)+2, currRow, currGridValue);
							
							if (checkCol1 && checkCol2 && checkCol3) {
								int targetRow = (subGridRowSection * 3) + (3 - (currRow % 3) - (row % 3));
								int targetColumn = 3 - (currColSection % 3) - (subGridColSection %3);
								if (parseMatch(currGrid, pencilMarks, row, col, numbersLeft, targetRow, parseCurrCol)) {
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

	/**
	 * Found two "matches" in two columns, so check the three cells within 
	 * the third column for a match
	 */
	private static boolean parseMatch(int[][] currGrid, HashSet[][] pencilMarks, int row, int col,
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

			// check to see if the cells are already occupied
			boolean checkCol1 = checkValWithCol(currGrid, parseTargetCol,
					parseTargetRow, currGridValue);
			boolean checkCol2 = checkValWithCol(currGrid, parseTargetCol+1,
					parseTargetRow, currGridValue);
			boolean checkCol3 = checkValWithCol(currGrid, parseTargetCol+2,
							parseTargetRow, currGridValue);

			if (checkCol1 && checkCol2) {
				currGrid[parseTargetRow][parseTargetCol+2] = currGridValue;
				System.out.println("Put in " + currGridValue + " at row " + 
						parseTargetRow + " col " + (parseTargetCol+2));	
				pencilMarks[parseTargetRow][parseTargetCol+2].clear();
				pencilMarks[parseTargetRow][parseTargetCol+2].add(currGridValue);
				numbersLeft[currGridValue-1]--;
				changesMade = true;
			} else if (checkCol1 && checkCol3) {
				currGrid[parseTargetRow][parseTargetCol+1] = currGridValue;
				System.out.println("Put in " + currGridValue + " at row " + 
						parseTargetRow + " col " + (parseTargetCol+1));
				pencilMarks[parseTargetRow][parseTargetCol+1].clear();
				pencilMarks[parseTargetRow][parseTargetCol+1].add(currGridValue);
				numbersLeft[currGridValue-1]--;
				changesMade = true;
			} else if (checkCol2 && checkCol3) {
				currGrid[parseTargetRow][parseTargetCol] = currGridValue;
				System.out.println("Put in " + currGridValue + " at row " + 
						parseTargetRow + " col " + parseTargetCol);
				pencilMarks[parseTargetRow][parseTargetCol].clear();
				pencilMarks[parseTargetRow][parseTargetCol].add(currGridValue);
				numbersLeft[currGridValue-1]--;
				changesMade = true;
			}

		}
		return changesMade;
	}

	/**
	 * Checks if a value exists as well as checks the column for a match
	 */
	private static boolean checkValWithCol(int[][] currGrid,
			int parseTargetCol, int parseTargetRow, int currGridValue) {
		boolean checkCol1 = existsInCol(currGridValue, parseTargetCol, currGrid);
		if (currGrid[parseTargetRow][parseTargetCol] != 0) {
			checkCol1 = true;
		}
		return checkCol1;
	}

	/**
	 * Checks the column for a match
	 */
	private static boolean existsInCol(int value, int col, int[][] currGrid) {
		boolean existsFlag = false;
		for (int row = 0; row < GRID_LENGTH; row++) {
			if (currGrid[row][col] == value) {
				existsFlag = true;
			}
		}
		return existsFlag;
	}

	/**
	 * Checks the row for a match
	 */
	private boolean existsInRow(int value, int row, int[][] currGrid) {
		boolean existsFlag = false;
		for (int col = 0; col < GRID_LENGTH; col++) {
			if (currGrid[row][col] == value) {
				existsFlag = true;
			}
		}
		return existsFlag;
	}

	/****************************************
	 * 
	 * INITIALIZE / ADMIN METHODS
	 * 
	 *****************************************/
	
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
	
	/**
	 * Copies the contents of one pencil marks grid over to another
	 */
	private static void copyContents(HashSet[][] oldGrid, HashSet[][] newGrid) {
		for (int copyCount = 0; copyCount < GRID_LENGTH; copyCount++) {
			System.arraycopy(oldGrid[copyCount], 0, newGrid[copyCount], 0, oldGrid[copyCount].length);
		}
	}	

	/**
	 * Formats and prints a grid
	 */
	private static void printGrid(int[][] puzzleGrid, HashSet[][] pencilMarks) {
		for (int row = 0; row < GRID_LENGTH; row++) {
			if (row % 3 == 0) {
				System.out.println("----------------------\t\t------------------------------------------------------------------");
			}
			for (int col = 0; col < GRID_LENGTH; col++) {
				if (col % 3 == 0) {
					System.out.print("|");
				}
				System.out.print(puzzleGrid[row][col] + " ");
			}
			System.out.print("|\t\t");
			for (int col = 0; col < GRID_LENGTH; col++) {
				if (col % 3 == 0) {
					System.out.print("|");
				}
				System.out.print(formatPencils(pencilMarks[row][col]) + "\t");
			}
			System.out.println("|");
		}
		System.out.println("----------------------\t\t------------------------------------------------------------------");
	}
	
	private static String formatPencils(HashSet pencilMark) {
		String returnString = "";
		for (int count = 0; count < pencilMark.size(); count++) {
			returnString = returnString + pencilMark.toArray()[count];
		}
		return returnString;
	}
	
	/**
	 * Pivots (flips around) the grid, running it again
	 * will revert it back to normal
	 */
	private static void pivotGridAndMarks(int[][] puzzleGrid, HashSet[][] pencilMarks) {
		int[][] newGrid = new int[GRID_LENGTH][GRID_LENGTH];	// new grid instances
		HashSet[][] newPencils = new HashSet[GRID_LENGTH][GRID_LENGTH];
		for (int row = 0; row < GRID_LENGTH; row++) {
			for (int col = 0; col < GRID_LENGTH; col++) {
				newGrid[col][row] = puzzleGrid[row][col];
				newPencils[col][row] = pencilMarks[row][col];
			}
		}
		copyContents(newGrid, puzzleGrid);
		copyContents(newPencils, pencilMarks);
	}

}

