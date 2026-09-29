# CBL
Repository for CBL project of Tom &amp; Domantas

This game is similar to & inspired by: the Oregon Trial, FTL, Dwarf Fortress, & Project Zomboid;
(random event system)	(map)	(compex structures and interactibility)	(inventory system);


The premise of this game is to journey from point A to point B as quickly as possible while maintaining certain vital stats.

# Key mechanics:


Extensive inheritance for class / object system:
  There is an extensive inheritance tree for objects in this game, thus items have many attributes & interactions become more dynamic. For example: a specific instance of a shopkeeper extends shopkeeper, human, animal.

Time system:
  The game is turn-based, but also functions on a simulated time system. Inputted actions take a certain amount of in-game time, as is calculated by various metrics applicable to the action. For example, walking from location 1 to location 2 might take 30 in-game minutes, as is calculated by distance between the two points. During this simulated time, stats are effected. Thirst, hunger, exhaustion, etc increase in relation to in-game time. The more time spent travelling between nodes also increases chance and amount of random events on the way.
  
Time limit:
  At the start of the game you have set amount of time to make to the end. Through the time system you lose time, and must make it to the end before time runs out to win. The time limit is set according to the number of connections present on the main path and their total time and then put through a calculation to get the final time, which is usually enough to both make it and epxlore at least one offshoot.

Inventory & encumbrance system:
  Every object has an encumbrance (generally, it's difficulty to carry) as is calculated by parameters like it's size & weight. The player character inventory is dependant on their strength & clothing (bags will increase it, for instance). A player can carry any set of items with sum less than their maximum encumbrance without reprecution. After the maximum is exceeded, they can continue to pick things up, though exhaustion will be gained at an elevated rate proportional to the excess encumbrance to some exponent.

Map:
 The map is made up of randomly generated interconnected nodes, excluding the start and end nodes, which have predetermined positions. There is always a path from start to end, however, not all nodes are part of that path, a. k. a. offshoots, which usually require going back and forward on the same path, however, they usually give a strategic advantage/buff. Each node represents a city.

Cities:
 Cities are randomly picked from the pool of predetermined "made-up" cities. Each city has it own randomly generated locations (shops, etc.), which can be moved between each other at no cost.

Random events:
 When travelling between nodes there is a chance for a random event (or multiple) to occur. Random events take from a pool of events, the same event may occur multiple times. Each event has a title, description and gameplay affects, such as changing stats and/or items, may even affect time.
