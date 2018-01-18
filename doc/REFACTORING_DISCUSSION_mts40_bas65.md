# Refactoring Exercise

Authors:
* Ben Schwennesen (bas65)
* Michael Scruggs (mts40)

### Part 1: Duplicate Refactoring 
We added an abstract cell, which implements our cell interface, in order to remove duplicate code. Some pieces of code that were able to be consolidated in the 
abstract class are methods that better manage how each cell will get and update its state.
* Note that these changes were done in the bas65 branch and not in the specially made refactoring branch 


### Part 2 Checklist Refactoring
One thing we fixed was a problem with some floating variables within the SimulationProcessor class. This was not a serious issue, but rather made it easier
to understand. This change made sense as String values that represent each new simulation was used multiple times but never changed.

We also had a discussion on implementation of different shapes for each cell within the grid. We decided that it was best to maintain the array of cells 
but have different methods for determining cell neighbors and etc. While this method works well for triangular cells, it is much more difficult for the hexagons.
We weighed the options of accounting of the new shape in the backend or the frontend, and decided that it should be handled in the backend.