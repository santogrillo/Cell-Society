package neighbor_setters;

import cells.Cell;

public class CardinalNeighborSetter extends NeighborSetter{
	
	    public void setInterior(Cell[][] initialCells,int row,int col) {
	    	Cell currentCell = initialCells[row][col];
	    	for(int i=row-1;i<=row+1;i++) {
	    		if(i!=row)
	    			currentCell.addNeighbors(initialCells[i][col]);
	    			
	    	}
	    	for(int i=col-1;i<=col+1;i++) {
	    		if(i!=col)
	    			currentCell.addNeighbors(initialCells[row][i]);
	    	}
	    }
	    
	    public void setDefaultColumn(Cell[][]initialCells,int row,int col) {
	    	Cell currentCell = initialCells[row][col];
			currentCell.addNeighbors(initialCells[row-1][col]);
			currentCell.addNeighbors(initialCells[row+1][col]);
			if(col==0) {
				currentCell.addNeighbors(initialCells[row][col+1]);
			}else {
					currentCell.addNeighbors(initialCells[row][col-1]);
			}
	    }
	    
	    /**
	     * Handles if the cell is located on an edge column with toroidal wrapping.
	     * Adds the final cell needed in this situation, either to the left or to the right.
	     * 
	     * */
	    public void setToroidalColumn(Cell[][]initialCells,int row,int col) {
	    	Cell currentCell=initialCells[row][col];
	    	if(col==0) {
	    		currentCell.addNeighbors(initialCells[row][this.getMaxCols()-1]);
	    	}else {
	    		currentCell.addNeighbors(initialCells[row][0]);
	    	}
	    }
	    
	    /**
	     * Does work if the cell is located on an edge row (top or bottom)
	     * Adds three cells as neighbors.
	     * */
	    public void setDefaultRow(Cell[][]initialCells,int row, int col){
	    	Cell currentCell = initialCells[row][col];
			currentCell.addNeighbors(initialCells[row][col-1]);
			currentCell.addNeighbors(initialCells[row][col+1]);
			if(row==0) {
				currentCell.addNeighbors(initialCells[row+1][col]);
			}else {
				currentCell.addNeighbors(initialCells[row-1][col]);
			}
	    }
	    
	    /**
	     * Handles the final neighbor cell if an edge row cell is toroidal
	     * */
	    public void setToroidalRow(Cell[][]initialCells, int row,int col) {
	    	Cell currentCell = initialCells[row][col];
	    	if(row==0) {
				currentCell.addNeighbors(initialCells[row][initialCells[row].length-1]);
			} else {
				currentCell.addNeighbors(initialCells[row][0]);
			}
	    }
	    
	    /**
	     * Handles if the cell is a corner cell and not toroidal by adding the two necessary cells
	     * 
	     * */
	    public void setCorner(Cell[][]initialCells,int row,int col,Wrapping wrap) {
	    	Cell currentCell = initialCells[row][col];
	    	if(isToroidal(wrap)) {
	    		handleCornerToroidal(initialCells,row,col);
	    	}
	    	else {
	    		int newRow = getCornerVals(row);
	    		int newCol = getCornerVals(col);
	    		currentCell.addNeighbors(initialCells[row][newCol]);
	    		currentCell.addNeighbors(initialCells[newRow][col]);
	    	}
	    }
	    
	    /**
	     * Handles if the cell is located on the corner and is toroidal
	     * 
	     * */
	    private void handleCornerToroidal(Cell[][]initialCells,int row,int col) {
	    	Cell currentCell = initialCells[row][col];
	    	int[] rows = getCornerArray(row,this.getMaxRows());
	    	int[] cols = getCornerArray(col,this.getMaxCols());
	    	for(int i=0;i<rows.length;i++) {
	    		currentCell.addNeighbors(initialCells[i][col]);
	    	}
	    	for(int i=0;i<cols.length;i++) {
	    		currentCell.addNeighbors(initialCells[row][i]);
	    	}
	    }

}
