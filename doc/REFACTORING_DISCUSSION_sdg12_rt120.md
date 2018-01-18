Refactoring Lab
sdg12 rt120

* removing duplicated code:
* refactor the update methods in Conway, WaTor, and Fire processors
* refctor defaults in Satisfaction, Fire, and FPS Sliders

* We chose these sections because the functionality in each of the methods of these inherited classes was essentially the same. 
Therefore it made sense to move the repeated lines to the base class in each case. For the sliders, this also ensured a default setting for the scale of the slider.

* Changed exception handling within XML Parser to do more than print a stacktrace.