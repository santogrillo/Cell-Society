# Inheritance Review

Authors:
* Richard Tseng (rt120)
* David Tran (dht9)

### Part 1
What is an implementation decision that your design is encapsulating (i.e., hiding) for other areas of the program?
* Defining initial state of the simulation
* Parsing the XML file
* Generating a new, reformatted XML file

What inheritance hierarchies are you intending to build within your area and what behavior are they based around?
* No inheritance hierarchies for XML file processing

What parts within your area are you trying to make closed and what parts open to take advantage of this polymorphism you are creating?
* XML Parser is closed
* XML files are open, because their values can be changed 

What exceptions (error cases) might occur in your area and how will you handle them (or not, by throwing)?
* Empty file
* Bad filepath
* Null pointer for tag that does not exist
* Handled by throwing Exception, displaying Error message

Why do you think your design is good (also define what your measure of good is)?
* Able to handle all four simulation types


### Part 2
How is your area linked to/dependent on other areas of the project?
* Give information to front end/back end

Are these dependencies based on the other class's behavior or implementation?

How can you minimize these dependencies?

Go over one pair of super/sub classes in detail to see if there is room for improvement. 

Focus on what things they have in common (these go in the superclass) and what about them varies (these go in the subclass).


### Part 3
Come up with at least five use cases for your part (most likely these will be useful for both teams).

What feature/design problem are you most excited to work on?

What feature/design problem are you most worried about working on?