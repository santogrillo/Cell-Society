package neighbor_setters;

import cells.Cell;
import neighbor_setters.NeighborSetter.Wrapping;

/**
 * Implementing classes set cell neighbors for the various grid types.
 */
public abstract class NeighborSetter {
	public enum Wrapping {
		FINITE, TOROIDAL,INFINITE
	}
	public enum Direction {
		CARDINAL, DIAGONAL, ALL
	}
	private int maxRows,maxCols;
    
	/**
     * Sets the neighbors of each cell with according to the grid and edge types selected in the
     * user-interface: including diagonals, cardinal directions, or both as neighbors; finite or
     * toroidal edge behavior.
     * */
    public Cell[][] setNeighbors(Cell[][] initialCells, Wrapping wrap){
    	maxRows=initialCells.length;
    	maxCols=initialCells[0].length;
    	setNeighborsLoop(initialCells,wrap);
    	return initialCells;
    }
    
    public void setNeighborsLoop(Cell[][] initialCells,Wrapping wrap) {
    	for(int row =0;row<initialCells.length;row++) {
    		for(int col=0;col<initialCells[row].length;col++) {
    			initialCells[row][col].clearNeighbors();
    			if(isCorner(row,col)) {
    				setCorner(initialCells,row,col,wrap);
    			}
    			else if(isSpecial(row,col)) {
    				setSpecial(initialCells,row,col,wrap);
    			}else
    				setInterior(initialCells,row,col);
    		}
    	}
    }
	
    private void setSpecial(Cell[][] initialCells, int row, int col,Wrapping wrap) {
    	if(row==0 || row==initialCells.length-1) {
    		setSpecialRow(initialCells,row,col,wrap);
    	}else
    		setSpecialColumn(initialCells,row,col,wrap);
    }
    
	private void setSpecialColumn(Cell[][] initialCells, int row, int col, Wrapping wrap) {
		setDefaultColumn(initialCells,row,col);
		if(isToroidal(wrap)) {
			setToroidalColumn(initialCells,row,col);
		}
	}
	
	private void setSpecialRow(Cell[][] initialCells, int row, int col, Wrapping wrap) {
		setDefaultRow(initialCells,row,col);
		if(isToroidal(wrap)) {
			setToroidalRow(initialCells,row,col);
		}
	}
	
	abstract public void setInterior(Cell[][] initialCells, int row, int col);
	
	abstract public void setCorner(Cell[][] cells,int row,int col,Wrapping wrap);
	
	abstract public void setDefaultColumn(Cell[][]initialCells,int row,int col);
	
	abstract public void setToroidalColumn(Cell[][]initialCells,int row,int col);
	
	abstract public void setDefaultRow(Cell[][]initialCells,int row,int col);
	
	abstract public void setToroidalRow(Cell[][]initialCells,int row,int col);
	
	private boolean isSpecial(int row,int col) {
    	return (((row==0)||(row==maxRows-1)) || ((col==0)||(col==maxCols-1)));
    } 
	
	protected int getMaxRows() {
		return maxRows;
	}
	
	protected int getMaxCols() {
		return maxCols;
	}	
	
	private boolean isCorner(int row,int col) {
	   	return (((row==0)||(row==maxRows-1)) && ((col==0)||(col==maxCols-1)));
	}
	
	public boolean isToroidal(Wrapping wrap) {
		return (wrap==Wrapping.TOROIDAL);
	}
    
	protected int getCornerVals(int index) {
	    if(index==0)
	    	return index+1;
	    return index-1;
	}
	
	protected int[] getCornerArray(int cur,int max) {
    	int topVal,bottomVal;
    	if(cur==0) {
    		topVal=max-1;
    		bottomVal=cur+1;
    	}else {
    		topVal=cur-1;
    		bottomVal=0;
    	}
    	int[] cornerVals = {bottomVal,topVal};
    	return cornerVals;
    }
    
    
   
    
}
