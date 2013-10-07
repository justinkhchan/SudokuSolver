
/**
 * @author Justin Chan
 * @description
 * Simple container for the Sudoku grid
 */
public class Sudoku {

	private static final int GRID_LENGTH = 9;
	private static Integer[][] sudokuGrid = new Integer[GRID_LENGTH][GRID_LENGTH];
	
	public Sudoku(int[][] grid) {
		for (int copyCount = 0; copyCount < GRID_LENGTH; copyCount++) {
			for (int colCount = 0; colCount < GRID_LENGTH; colCount++) {
				sudokuGrid[copyCount][colCount] = grid[copyCount][colCount];
			}
		}
	}
	
	public int[][] returnGrid() {
		int[][] newGrid = new int[GRID_LENGTH][GRID_LENGTH];
		for (int copyCount = 0; copyCount < GRID_LENGTH; copyCount++) {
			for (int colCount = 0; colCount < GRID_LENGTH; colCount++) {
				newGrid[copyCount][colCount] = sudokuGrid[copyCount][colCount];
			}
		}		
		return newGrid;
	}
	
}
