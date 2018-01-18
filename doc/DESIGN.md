# Cell Society

Second project for Computer Science 308. 

Authors:
* Santo Grillo (sdg12@duke.edu)
* Ben Schwennesen (bas65@duke.edu)
* Michael Scruggs (mts40@duke.edu)
* Richard Tseng (rt120@duke.edu)

## Introduction
Our team wants this project to be very flexible to the addition of new cellular automata simulations; that is, we want our code to be easily extended to support new types of cell states and rules. 
The program will load in a simulation XML containing the simulation rules and initial state, and then allow the user to start, stop, or step through the simulation. The UI and user controls will be distinctly closed from the backend operations for the simulation.

## Overview
There are a number of components we know our program will include: 
 1. A main class (preliminary name: Launcer) which launches a main menu/splash screen. The menu will allow for selection of the simulation to start, among other options.
 2. A manager class (preliminary name: SimulationManager) that will handle the progression of the simulation; its methods will start or stop the simulation and update the states of the cells at regular time intervals.
 3. A state processer (preliminary name: StateProcessor) that will process the current state of the cells, check the states against the rules for each cell, and either update the states of the cells itself or delegate this step to another class (perhaps a StateUpdater)
 4. A cell class (preliminary name: Cell) storing the state of the cell, rules this cell follows, a data structure (some kind of collection) containing the cell's neighors (other cells). Methods will include: getState, updateState, getNeighbors, getRule. 
 5. A display class (preliminary name choices: Display, Frontend, etc.) that will create a scene for the simulation and update it per the information passed to it by the manager. 
 6. A file IO class (preliminary name: InitialStateReader) that will readin the XML data and initialize the grid of cells.
 7. A slider class which will contain a slider UI element and overloaded functionality to affect the simulation. (e.g. a slider to control the % of empty spaces in a simulation)

A simple UML diagram showing the relationship between these classes can be seen [here](./images/simple-uml.png).

## User Interface
In order for the user to interact with our program, there will be sliders, buttons, and a drop down menu. All of these pieces will be established in our Manager class. Since the sliders and buttons will be determined by the type of simulation that will be run, when the Manager calls the File IO object to set up everything, the sliders and buttons will also be created, specifically for the desired simulation. Some basic buttons that will always be implemented are Pause, Reset, Step, and Start. A universal slider will be delay time in milliseconds. Several drop down menu bars will also be implemented to give the user more control over the game. Some features will include loading a new file and “fastforwarding” through steps. Some erroneous situations that could occur would be the user importing a bad XML file. If the file is not in the desired format, or the document is not of the correct form, a prompt for the user to insert a new, correctly formatted document will be issued.

## Design Details
* The main class will launch the game and create instances of Manager. We created this component because we want to allow for users to create multiple simulations at one time.
* The manager class will have start, stop, step methods. It will also handle key presses and be able to reset the simulation. It takes information from the File I/O class, determines when to update the state processor and the display. We created this component to handle the game loop.
* The state processor class has a list of cells and a list of the empty positions on the board. Loops through list of cells to update each cell, passing the list of empty positions.
* The abstract cell class stores information, such as current state, future state (allowing for the cell to be updated), list of neighbors, rules. The cell will also have an updateState method, which is called in the state processor class with parameters such as the list of empty positions. The cell class can be extended to account for different types of cells.
* The  display class creates the 2D grid, where the cells are displayed. It also contains the objects necessary to display the toolbar, sliders, buttons, etc. It will also handle the main menu UI.
* The file I/O class handles reading in the XML formatted file that contains the initial settings for a simulation. It will pass the title of the simulation, the simulation’s author, settings for global configuration parameters, the dimensions of the grid, and the initial configuration of the states for the cells in the grid to the Manager class. It will use the built-in Java classes to parse the XML file.

Steps needed to complete the use cases:
* Apply the rules to a middle cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all its neighbors)
     * Inside a method in the StateProcessor class, have a for-each loop that will iterate over the cells and call a method updateState() on each. 
     * The cell will then check its list of rules by iterating over its set of neighbors and determining from these neighbors’ states the next state it should be in. 
* Apply the rules to an edge cell: set the next state of a cell to live by counting its number of neighbors using the Game of Life rules for a cell on the edge (i.e., with some of its neighbors missing)
     * Each cell will have a data structure keeping track of all its neighbors, meaning that no loop will be necessary, within the cell class, to determine the cell’s neighbors. With that being said, in order to update the rules of an edge cell, the same steps will be applied as trying to update a middle cell.
* Move to the next generation: update all cells in a simulation from their current state to their next state and display the result graphically
     * The step function in manager will call methods in the processor class, which will loop through the list of cells to update the cells from their current state to their next state. It will also call methods in the display class to display the result graphically.
* Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in an XML fire
     * Add to the parameter list of type double in File I/O, logic in cell class handles the double
* Switch simulations: use the GUI to change the current simulation from Game of Life to Water
     * A drop down menu will be implemented that will allow the user to load and run a new XML file. When this occurs, a new instance of Manager will be created and, depending on the user’s choice, kill or keep the old version of Manager running, in a new window.

## Design Considerations
* The primary design consideration was in regards to where and how the rules of each simulation should be implemented. The primary methods we considered were implementing the rules for each simulation within an inherited Cell class, or storing the rules in an abstract Rules type class and passing that to each cell to update. The latter would be very complex for implementation via the XML, as new files would need to contain generic instructions that could be applied to any simulation. This design is ultimately more flexible, as theoretically one could implement a new set of rules for a given simulation without having to change any of the underlying code. However, this would be incredibly difficult to realize, and any new unforeseen conditions would need to be added to every XML, causing several potential version issues and repeated work.
* Instead we chose to implement new simulations by extending the Cell class. Each type of simulation would get a corresponding inherited Cell. This has the advantage of being significantly easier to implement: new types of simulations would merely require implementing new update behavior in the corresponding class. Similarly, generating new XML files would not cause any issues with previous versions. This has the downside of being slightly less flexible, since any new type of simulation must be “hard-coded” into the program.

## Team Responsibilities

##### Richard
* Primary: File I/O, XML Generator
* Secondary: Cell

##### Santo:
* Primary: Display
* Secondary: Manager

##### Michael
* Primary: Responsible for creating the manager and processor classes. My role will involve oversight of the entire program, making sure that everyone’s pieces area compatible with everyone else’s. 

##### Ben 
* Primary: Responsible for creating the abstract cell class and the cell classes for each type of simulation. This includes programming the cell update behavior. 
* Secondary: File I/O


