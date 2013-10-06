import java.util.*;


/**
 * @author Justin Chan
 * @description 
 * 	Sudoku solving program consisting of three main strategies:
 * 		1) Parsing three columns within three adjacent subgrids
 * 		2) Utilizing pencil marks, and using elimination to leave only one possible value
 * 		3) If the above two methods do not succeed, use the backtracking search method to
 * 			exhaust all possibilities, but check for contradictions in order to process
 * 			much fewer possibilities  
 * 
 */

public class Main {

	private static final int GRID_LENGTH = 9;
	private static final int SUB_LENGTH = 3;

	/**
	 * Initializing Method
	 */
	public static void main(String[] args) {
		
		// original puzzle grid
		// easy puzzle
		int[][] puzzleGrid = { 
			{0, 6, 5, 4, 8, 0, 0, 3, 0},
			{0, 1, 9, 0, 0, 0, 5, 4, 0},
			{0, 0, 0, 0, 0, 9, 0, 8, 0},
			{5, 0, 0, 0, 2, 3, 4, 0, 1},
			{0, 0, 6, 0, 7, 0, 8, 0, 0},
			{7, 0, 1, 8, 9, 0, 0, 0, 3},
			{0, 8, 0, 9, 0, 0, 0, 0, 0},
			{0, 5, 7, 0, 0, 0, 6, 1, 0},
			{0, 3, 0, 0, 1, 8, 9, 5, 0}
		};
		
		// medium puzzle
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
		
		// hard puzzle
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
		
		// very hard puzzle
		/*int[][] puzzleGrid = {
			{0, 0, 4, 0, 0, 6, 0, 0, 7},
			{0, 7, 0, 0, 3, 0, 0, 8, 0},
			{5, 0, 0, 8, 0, 0, 6, 0, 0},
			{9, 0, 0, 4, 0, 0, 1, 0, 0},
			{0, 1, 0, 0, 5, 0, 0, 4, 0},
			{0, 0, 7, 0, 0, 1, 0, 0, 3},
			{0, 0, 3, 0, 0, 5, 0, 0, 1},
			{0, 9, 0, 0, 4, 0, 0, 2, 0},
			{8, 0, 0, 9, 0, 0, 4, 0, 0}
		};*/
		
		// "hardest" puzzle (long processing time)
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
		
		// "hardest" puzzle 2 (takes very, very long to process)
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
		
		copyContents(puzzleGrid, currGrid);	// preserve original grid just in case
		initialCount(currGrid, numbersLeft);
		initialPencils(currGrid, pencilMarks);
		clearNumbers(currGrid, pencilMarks);
		
		printGrid(currGrid, pencilMarks);
		changesMade = true;
		
		int iterationCount = 0;
		while (changesMade == true) {	// new changes made, re-parse
			iterationCount++;
			System.out.println(">> Iteration " + iterationCount);
			changesMade = false;
			if (placeInNumbers(currGrid, pencilMarks, numbersLeft)) {
				changesMade = true;
			}
			pivotGridAndMarks(currGrid, pencilMarks);	
			// pivoting (flipping around) the grid and parsing horizontally is
			// the same as parsing vertically
			if (placeInNumbers(currGrid, pencilMarks, numbersLeft)) {
				changesMade = true;
			}
			pivotGridAndMarks(currGrid, pencilMarks); 
			// pivoting the grid again reverts it back to
			// the original orientation
			if (clearNumbers(currGrid, pencilMarks)) {	// update pencil marks
				changesMade = true;
			}
			if (clearColumns(currGrid, pencilMarks)) {
				changesMade = true;
			}
			pivotGridAndMarks(currGrid, pencilMarks);
			if (clearColumns(currGrid, pencilMarks)) {
				changesMade = true;
			}
			pivotGridAndMarks(currGrid, pencilMarks);
			printGrid(currGrid, pencilMarks);
		}
		
		System.out.println(">> Used all parsing and pattern matching methods:");
		printGrid(currGrid, pencilMarks);
		
		if (!checkValidity(currGrid, pencilMarks)) {	// regular pattern methods unable to solve grid
			// Search code
			System.out.println("Cannot solve with regular patterns, using search method... " +
					"(can take up to several minutes)");
			
			// create a copy of the grid just in case
			int[][] searchGrid = new int[GRID_LENGTH][GRID_LENGTH];
			copyContents(currGrid, searchGrid);
			
			if (!searchSolve(searchGrid, pencilMarks)) {
				System.out.println("Impossible grid detected!");
			}
			
		}

	}
	
	/****************************************
	 * 
	 * SEARCH SOLVE (BACKTRACKING) METHODS
	 * 
	 *****************************************/

	/**
	 * Initiate the recursive searching solving function
	 */	
	private static boolean searchSolve(int[][] searchGrid, HashSet[][] pencilMarks) {
		Sudoku newSudoku = new Sudoku(searchGrid);
		return searchRecurse(newSudoku, pencilMarks);
	}
	
	/**
	 * Main recursive search solving function 
	 */		
	private static boolean searchRecurse(Sudoku currSudoku, HashSet[][] pencilMarks) {
		int rowCount = 0;
		int colCount = 0;
		
		int[][] currGrid = new int[GRID_LENGTH][GRID_LENGTH];
		currGrid = currSudoku.returnGrid();
		
		// check if reached last cell of the row,
		// if reached last row of the grid,
		// and if the value is already filled in
		while ((rowCount < GRID_LENGTH) && (colCount < GRID_LENGTH) 
				&& currGrid[rowCount][colCount] != 0) {
			 colCount++;
			 // advance to next row
			 if (colCount == GRID_LENGTH) {
				 colCount = 0;
				 rowCount++;
			 }
		}
	
		if (colCount < GRID_LENGTH && rowCount < GRID_LENGTH) {
			for (int pencilCount = 0; pencilCount < pencilMarks[rowCount][colCount].size(); pencilCount++) {
				currGrid[rowCount][colCount] = (int) pencilMarks[rowCount][colCount].toArray()[pencilCount];

				// valid grid found
				if (checkValidity(currGrid, pencilMarks)) {
					printGrid(currGrid, pencilMarks);
					return true;
				}

				// Bind the data into a simple Sudoku object container
				Sudoku newSudoku = new Sudoku(currGrid);
				if (!checkContradiction(currGrid, pencilMarks)) {
					if (searchRecurse(newSudoku, pencilMarks)) { // valid grid found
						return true;
					}
				}
				currGrid[rowCount][colCount] = 0;
			}
			currGrid[rowCount][colCount] = 0;
		}
	
		// no valid grids found
		return false;
	}
	
	/****************************************
	 * 
	 * CHECK CONTRADICTION METHODS
	 * 
	 *****************************************/
	
	/**
	 * Check for contradictions within a grid
	 */		
	private static boolean checkContradiction(int[][] currGrid, HashSet[][] pencilMarkers) {
		boolean contradiction = false;
		if (checkColContra(currGrid)) {
			contradiction = true;
		}
		pivotGridAndMarks(currGrid, pencilMarkers);
		if (checkColContra(currGrid)) {
			contradiction = true;
		}
		pivotGridAndMarks(currGrid, pencilMarkers);
		if (checkSubgridsContra(currGrid)) {
			contradiction = true;
		}
		return contradiction;
	}

	/**
	 * Check for contradictions within a column
	 */	
	private static boolean checkColContra(int[][] currGrid) {
		for (int row = 0; row < GRID_LENGTH; row++) {
			HashSet totalNumbers = new HashSet();
			for (int col = 0; col < GRID_LENGTH; col++) {
				int currValue = currGrid[row][col];
				if (currValue != 0) {
					if (totalNumbers.contains(currValue)) {
						return true;
					} else {
						totalNumbers.add(currValue);
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Check for contradictions within a subgrid
	 */	
	private static boolean checkSubgridsContra(int[][] currGrid) {

		for (int rowSection = 0; rowSection < SUB_LENGTH; rowSection++) {
			for (int colSection = 0; colSection < SUB_LENGTH; colSection++) {
				for (int rowInc = 0; rowInc < SUB_LENGTH; rowInc++) {
					for (int colInc = 0; colInc < SUB_LENGTH; colInc++) {
						HashSet totalNumbers = new HashSet();
						int currValue = currGrid[(rowSection*SUB_LENGTH)+rowInc][(colSection*SUB_LENGTH)+colInc];
						if (currValue != 0) {
							if (totalNumbers.contains(currValue)) {
								return true;
							} else {
								totalNumbers.add(currValue);
							}
						}
					}
				}
			}
		}

		return false;
	}	
	
	/****************************************
	 * 
	 * CHECK VALIDITY METHODS
	 * 
	 *****************************************/
	
	/**
	 * Check a grid to see if it is a valid solved Sudoku grid
	 */	
	private static boolean checkValidity(int[][] currGrid, HashSet[][] pencilMarks) {
		if (!checkColumns(currGrid)) {
			return false;
		}
		pivotGridAndMarks(currGrid, pencilMarks);
		if (!checkColumns(currGrid)) {
			pivotGridAndMarks(currGrid, pencilMarks);	// make sure grid is pivoted back to 
														// right direction even if failed
			return false;
		} else {
			pivotGridAndMarks(currGrid, pencilMarks); // re-pivot
		}
		if (!checkSubgrids(currGrid)) {
			return false;
		}
		return true;
	}

	/**
	 * Check a subgrid to see if it is valid
	 */
	private static boolean checkSubgrids(int[][] currGrid) {
		HashSet oneToNine = new HashSet();
		for (int number = 1; number <= GRID_LENGTH; number++) {
			oneToNine.add(number);
		}
		
		HashSet testGridSet = new HashSet();
		for (int rowSection = 0; rowSection < SUB_LENGTH; rowSection++) {
			for (int colSection = 0; colSection < SUB_LENGTH; colSection++) {
				testGridSet.clear();
				for (int rowInc = 0; rowInc < SUB_LENGTH; rowInc++) {
					for (int colInc = 0; colInc < SUB_LENGTH; colInc++) {
						testGridSet.add(
								currGrid[(rowSection*SUB_LENGTH)+rowInc][(colSection*SUB_LENGTH)+colInc]);
					}
				}
				if (!oneToNine.equals(testGridSet)) {
					return false;
				}
			}
		}

		return true;
	}
	
	/**
	 * Check a column to see if it is valid 
	 */
	private static boolean checkColumns(int[][] currGrid) {
		HashSet oneToNine = new HashSet();
		for (int number = 1; number <= GRID_LENGTH; number++) {
			oneToNine.add(number);
		}
		
		HashSet testColumnSet = new HashSet();
		
		for (int row = 0; row < GRID_LENGTH; row++) {
			testColumnSet.clear();
			for (int col = 0; col < GRID_LENGTH; col++) {
				testColumnSet.add(currGrid[row][col]);
			}
			if (!oneToNine.equals(testColumnSet)) {
				return false;
			}
		}
		
		return true;
	}
	
	/****************************************
	 * 
	 * PENCIL MARKER METHODS
	 * 
	 *****************************************/

	/**
	 * Check each unoccupied cell to see if any common markers can be 
	 * removed from ohter cells
	 */
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
		
		return changesMade;
	}

	
	/**
	 * Recursive method that removes any shared pencil marks
	 */
	private static boolean removePencilsCol(int[][] currGrid,
			HashSet[][] pencilMarks, int row, int col, ArrayList colVals) {
		boolean changesMade = false;
		for (int traverseCol = col; traverseCol < GRID_LENGTH; traverseCol++) {
			
			if (currGrid[row][traverseCol] == 0) {
				colVals.add(traverseCol);
				HashSet totalPencils = new HashSet();
				for (int currVals = 0; currVals < colVals.size(); currVals++) {
					totalPencils.addAll(pencilMarks[row][(int)colVals.get(currVals)]);
				}
				
				// when the total numbers across different cells are also equal to the total cells selected,
				// all other cells within the same column can have those numbers removed from their pencil marks
				if (totalPencils.size() < colVals.size()) {
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
										// Remove the shared pencil marks from other cells
										pencilMarks[row][newCol].remove(pencilMarks[row][newCol].toArray()[pencilParse]);
										if (pencilMarks[row][newCol].size() == 1) { 
											// eliminated all pencil marks to leave one
											// can assign the value as it is the last option left
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
				}
				
				if (traverseCol < GRID_LENGTH) {
					// recursively check all other combinations
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

	/**
	 * Runs pencil mark removing methods if a cell with a value is found
	 */
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

	/**
	 * Runs the three pencil marking methods, returns true 
	 * if any changes were made 
	 */
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

	/**
	 * Checks one subgrid against one value within the subgrid,
	 * removes any pencil marks if it matches the value
	 */
	private static boolean removePencilSubgrid(int[][] currGrid,
			HashSet[][] pencilMarks, int row, int col) {
		boolean changesMade = false;
		int subGridRowSection = row / SUB_LENGTH;
		int subGridColSection = col / SUB_LENGTH;
		int gridValue = currGrid[row][col];
		
		for (int rowInc = 0; rowInc < SUB_LENGTH; rowInc++) {
			if (rowInc != row % SUB_LENGTH) {
				for (int colInc = 0; colInc < SUB_LENGTH; colInc++) {
					if (colInc != col % SUB_LENGTH) {
						if (pencilMarks[(subGridRowSection*SUB_LENGTH) + rowInc][(subGridColSection*SUB_LENGTH) + colInc].remove(gridValue)) {
							changesMade = true;
						}						
					}
				}
			}
		}
		
		return changesMade;
	}
	
	/**
	 * Checks one row against one value within the row,
	 * removes any pencil marks if it matches the value
	 */
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
	
	/**
	 * Checks one column against one value within the column,
	 * removes any pencil marks if it matches the value
	 */
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
	private static boolean placeInNumbers(int[][] currGrid, HashSet[][] pencilMarks, int[] numbersLeft) {
		boolean changesMade = false;
		for (int row = 0; row < GRID_LENGTH; row++) {
			for (int col = 0; col < GRID_LENGTH; col++) {
				int currNumber = currGrid[row][col];
				if (currNumber != 0) {
					if (parseSubgrids(currGrid, pencilMarks, row, col, numbersLeft)) {
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
	private static boolean parseSubgrids(int[][] currGrid, HashSet[][] pencilMarks, int row, int col,
			int[] numbersLeft) {
		int subGridRowSection = row / SUB_LENGTH;
		int subGridColSection = col / SUB_LENGTH;

		int currRow = 0;
		int currGridValue = currGrid[row][col];
		
		int parseCurrCol = 0;
		int parseCurrRow = 0;

		boolean changesMade = false;

		for (int rowInc = 0; rowInc < SUB_LENGTH; rowInc++) {
			currRow = ((subGridRowSection * SUB_LENGTH) + rowInc);
			if (currRow != row) {	// don't parse the current row
				for (int currColSection = 0; currColSection < SUB_LENGTH; currColSection++) {
					if (currColSection != subGridColSection) { // don't parse the current column set
						//System.out.println(currRow + ", " + (currCol * SUB_LENGTH) + " to " + (currCol * SUB_LENGTH + 2));
						for (int parseCol = 0; parseCol < SUB_LENGTH; parseCol++) {
							parseCurrCol = (currColSection * SUB_LENGTH) + parseCol;
							if (currGrid[currRow][parseCurrCol] == currGridValue) {	// match found
								if (parseMatch(currGrid, pencilMarks, row, col, numbersLeft, currRow, parseCurrCol)) {
									changesMade = true;
								}
							}
						}
						// if column is full with over values
						// or if the value cannot be put in because of other rows
						if (currGrid[currRow][(currColSection * SUB_LENGTH)] != currGridValue &&
								currGrid[currRow][(currColSection * SUB_LENGTH)+1] != currGridValue && 
								currGrid[currRow][(currColSection * SUB_LENGTH)+2] != currGridValue ) {
							boolean checkCol1 = checkValWithCol(currGrid, (currColSection * SUB_LENGTH), currRow, currGridValue);
							boolean checkCol2 = checkValWithCol(currGrid, (currColSection * SUB_LENGTH)+1, currRow, currGridValue);
							boolean checkCol3 = checkValWithCol(currGrid, (currColSection * SUB_LENGTH)+2, currRow, currGridValue);
							
							if (checkCol1 && checkCol2 && checkCol3) {
								int targetRow = (subGridRowSection * SUB_LENGTH) + (SUB_LENGTH - (currRow % SUB_LENGTH) - (row % SUB_LENGTH));
								int targetColumn = SUB_LENGTH - (currColSection % SUB_LENGTH) - (subGridColSection % SUB_LENGTH);
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
		int subGridRowSection = row / SUB_LENGTH;
		int currGridValue = currGrid[row][col];
		boolean changesMade = false;

		parseTargetRow = (subGridRowSection * SUB_LENGTH) + (SUB_LENGTH - (currRow % SUB_LENGTH) - (row % SUB_LENGTH));
		parseTargetCol =  (SUB_LENGTH - (parseCurrCol / SUB_LENGTH) - (col / SUB_LENGTH)) * SUB_LENGTH;
		
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
		System.out.println("[ Grid ]          \t\t[ Pencil Marks ]");
		for (int row = 0; row < GRID_LENGTH; row++) {
			if (row % SUB_LENGTH == 0) {
				System.out.println("----------------------\t\t-----------------------------------------------------------------------");
			}
			for (int col = 0; col < GRID_LENGTH; col++) {
				if (col % SUB_LENGTH == 0) {
					System.out.print("|");
				}
				System.out.print(puzzleGrid[row][col] + " ");
			}
			System.out.print("|\t\t");
			for (int col = 0; col < GRID_LENGTH; col++) {
				if (col % SUB_LENGTH == 0) {
					System.out.print("|");
				}
				System.out.print(formatPencils(pencilMarks[row][col]) + "\t");
			}
			System.out.println("|");
		}
		System.out.println("----------------------\t\t-----------------------------------------------------------------------");
	}
	
	/**
	 * Simple function to format and compact the pencil marks
	 */
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

