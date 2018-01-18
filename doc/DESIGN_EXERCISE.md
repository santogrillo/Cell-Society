# Cell Society Design Exercise

## High Level Design

1. How does a Cell know what rules to apply for its simulation?
    * Rules stored in cell class
2. How does a Cell know about its neighbors? How can it update itself without effecting its neighbors update?
    * Neighbors set
    * Need to store the state the cells are going to transition to before actually transitioning them. Once every cell know what it’s going to transition to, we can actually update them.
3. What is the grid? Does it have any behaviors? Who needs to know about it?
    * 2D array of relative positions that are translated to absolute positions with (x*CELL_SIZE, y*CELL_SIZE)
    * Doesn’t necessarily need to have behaviors of its own
    * Manager needs to know about it
4. What information about a simulation needs to be the configuration file?
    * Types of cell states 
    * Rules
    * Size of the grid, size of blocks (SIZE_GRID % SIZE_BLOCK == 0)
5. How is the GUI updated after all the cells have been updated?
    * Received a set/list of blocks that were updated (likely from the StateProcessor) and changes their colors

## Class-Responsibility-Collaborator Cards
[CRC Cards](https://coursework.cs.duke.edu/CompSci308_2017Fall/cellsociety_team16/raw/master/images/crc-cards.jpg)

## Use Cases 
* Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)
* Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)
* Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically
* Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in an XML fire
* Switch simulations: use the GUI to change the current simulation from Game of Life to Wator
