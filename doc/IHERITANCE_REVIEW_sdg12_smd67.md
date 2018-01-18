Inheritance Review
sdg12, smd67, tjy8, vms23

Part I


Different classes, separate classes for cell, XML, etc.
self contained modules
Front end doesn't have much inheritance, but backend does 


Many different types of cells with cell parent
Simulation display class, loads splash screen


every cell has image, takes into account simulations such as 'segragation' with X, O
Abstractions between front, back end
Handle fatal errors by stopping and displaying error information to user
Design is good because it is clear and minimises repetitiveness 



Part II


Front end and back end share certain information


Need to decide which information to store in back end vs front
Cell state can be stored in cell object, and then front end can call cell object for properties


Dependencies based both on behavior and implementation, depending on usage case
Can minimise dependencies by clear communication and planning with team



Part III


Use Cases


Pausing the simulation
Switching/loading in new simulations
Loading in new configuration
Resetting to initial state
Stepping thru simulations


Most excited for UI and connecting everything together
Most worried about back end since that is the bulk of the program and makes program actually work

