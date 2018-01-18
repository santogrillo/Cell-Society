Inheritance Review Exercise 
==========

Discussion Group: 

* mts40 
* zk174
* ll253
* bas65
* jpp20

## Part 1
1. Whereas we are hiding our rule-checking/state-advancement procedure from our State Processor (keeping them in the cells themselves), the group we dicussed our design with made the opposite decision, keeping rule-checking in their State Manager and hiding it from their cells. 
2. We are going to have a base abstract Cell class and have the specific cells for each game inherit from this base class. The main behavior we want to get out of this is rule-checking behavior, which will be different for each type of simulation. 
3. We do not understand this question. 
4. A cell might end up having an undefined state for some reason, or a location which is out of bounds. We will throw errors if this occurs and deal with them at that point. 
5. Our design is good because it's easily understood and flexible, in that it will be easy to create new types, even without much prior exposure to the design. Our measure of goodness is mainly based on this flexibilty.

## Part 2 
1. Our component is liked to the XML input in that it will contain initial cell positions and states. It's also related to the manager because the cells and state processor will need to pass information to the manager (as well as have information passed to it). 
2. Yes, these dependencies were planned out in the design process and are intended.
3. As it stands, the dependencies are unidirectional and well-minimized already. In the case that they become more extensive, we will seek to minimize them by keeping thing unidirectional and not straying from the original purpose intended for our classes. 
4. We haven't started our part of the project yet, but we will achieve this by keeping as much behavior as possible in the superclass. 

## Part 3

Use cases:
1. *Identifying next state* (cells): check the cell's current state and the state of its neighbors and compare these against the rules for the simulation type, which will then yield the next state. The next state will be stored in a field *nextState*, so as to not affect other cells' next state computations 
2. *Transitioning state* (cells): simply change the field *currentState* to *nextState*
3. *Updating all cells' states* (state processor): iterate over allcells in the grid and have them identify their next state. Then, in a separate loop, have the cells transition to the next state
4. *Displaying state transtitions* (state processor): pass the updated grid back to the display class and have it display the grid according to the new states. 
5. *Constructing cells* (cells): pass the cell its initial state and locationwithin the grid, from which it will be able to construct its own list of neighbors. 

Other questions:
2. I'm most excited to handle the rule-checking/next state identification. 
3. I'm most worried about working specifically on the Segregation cell class, as this will require knowledge of which states are in the 'empty' state. Maintaining flexibility of the overall design while also keeping track of this 'empty' state list might require extension of the state processor. 