# Simulation Rules

### Conway's Game of Life
State transition rules (pulled from [Wikipedia](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life):
 1. A live cell with less than two live neighbors dies (underpopulation).
 2. A live cell with more than three live neighbors dies (overpopulation).
 3. A dead cell with exactly three live neighbors reanimates (reproduction).
 4. Otherwise, the cell remains in the same state.
 
### Thompson's Day and Night 
State transition rules (pulled from [Wikipedia](https://en.wikipedia.org/wiki/Day_and_Night_(cellular_automaton))): 
 1. A dead cell becomes live (is born) if it has 3, 6, 7, or 8 live neighbors.
 2. A live cell remains alive (survives) if it has 3, 4, 6, 7, or 8 live neighbors.

### Schelling's Model of Segregation
State transition rules (pulled from [this page](http://nifty.stanford.edu/2014/mccown-schelling-model-segregation/)):
 1. A cell in the RED (BLUE) state moves to the location of any cell in the grid currently in the empty state if the ratio of its neighbors that are also RED (BLUE) is less than the satisfaction threshold.
 2. Otherwise, if the ratio of its neighbors in the same state is greater than the satisfaction threshold, its state does not change.
 3. An empty cell remains empty until another cell moves into it. 

### Spreading Fire
State transition rules (pulled from [this page](http://nifty.stanford.edu/2007/shiflet-fire/)):
 1. An empty cell remains empty at all future stages.
 2. A cell in the burning state transitions to the empty state.
 3. A cell in the tree state with adjacent cells in the burning state transitions to the burning state with probability 'catchFireThreshold', otherwise it remains in the tree state.
 
### WaTor World/Predator-Prey
State transition rules for water cells (pulled from [Wikipedia](https://en.wikipedia.org/wiki/Wa-Tor)):
 1. If the cell is in the water state, it only changes states if a shark or fish moves into it.

State transition rules for fish cells:
 1. If there are adjacent cells in the water state (that is, neighboring unoccupied cells), the fish randomly moves to one of these cells.
 2. If no adjacent cells are in the water state, the state does not change.
 3. When the fish moves, if it has survived a certain number of time steps (chronons), it may reproduce by leaving behind a cell in the fish state (as opposed to the usual empty state).

State transition rules for shark cells:
 1. If there are adjacent cells in the fish state, the shark randomly moves to one of these cells and devours the fish. Shark cells gain a certain amount of energy when they devour fish.
 2. If there are no adjacent cells in the fish state, the shark moves to a random adjacent cell in the empty state.
 3. If no adjacent cell is in either the fish or water states, the shark stays put.
 4. As with the fish, if the shark has survived a certain number of time steps (chronons), it may reproduce by leaving behind a cell in the shark state (as opposed to the usual empty state).
 5. If the shark cell runs out of energy, it dies and leaves an empty cell behind.
 
### Bacterial/Rock-Paper-Scissors
Description (adapted from [this page](https://www.gamedev.net/blogs/entry/2249737-another-cellular-automaton-video/)): 
 1. Each non-empty cell has a color gradient level, randing from zero to a maximum (specified in a properties file).
 2. For each cell, one neighbor is selected at random and this neighbor is used to determine the cell's next state.
 3. Cells can be attacked/eaten by other cells according to rock-paper-scissors rules.

State transition rules: 
 1. If an empty cell chooses a non-empty cell and the non-empty cell is not at the max gradient level, the non-empty cell "reproduces" into the empty cell, leaving an offspring cell with color gradient one higher than its own.
 2. If a non-empty cell chooses a non-empty cell, it either:
   
    1. Damages (or kills, if recipient is at the maximum gradient) the cell if it dominates it according to rock-paper-scissors rules.
    2. Is damaged by (or is killed by, as above) the cell if it's dominated according to R-P-S rules. 
    3. Remains the same if the other cell is of the same R-P-S type as it. 
    
### Foraging Ants 
Description (adapted from [this paper](http://cs.gmu.edu/~eclab/projects/mason/publications/alife04ant.pdf):
 1. The simulation represents ants foraging for food.
 2. There are home mounds for the ants and food cells they want to scavenge food from. 
 3. Ants leave pheromone trails for others to follow to food or back to the home mounds. The pheromone trails evaporate over time. 
 4. Ants die after some amount of time. 
 5. Ants are able to occasionally reproduce. 

State transition rules: 
 1. An ant holding food will follow the strongest pheromone trail back to the home mound. If it cannot find any trail, it will move randomly until it finds one (or finds food). 
 2. An ant not holding food will follow the strongest pheromone trail to a food source. If it cannot find any trail, it will move randomly until it finds one (or finds a food source).
 3. Ants remain on the home mount for one turn to drop off the food they gather. 
 
### Chou-Reggia Loop 
This simulation models self-directed replication, that is, simple artificial life. See [this paper](https://www.researchgate.net/profile/Yun_Peng7/publication/266350459_Simple_Systems_Exhibiting_Self-Directed_Replication/links/550ae8300cf285564096306a/Simple-Systems-Exhibiting-Self-Directed-Replication.pdf?origin=publication_list) for details, specifically the bottom of the last page for the replication rules.
