# CBL
Repository for CBL project of Tom &amp; Domantas

This game is similar to & inspired by: the Oregan Trial, FTL, Dwarf Fortress, & Project Zomboid

The premise of this game is to journey from point A to point B as quickly as possible while maintaining certain vital stats.

# Key mechanics:


Extensive inheritance for class / object system:
  There is an extensive inheritance tree for objects in this game, thus items have many attributes & interactions become more dynamic. For example: a specific instance of a shopkeeper extends shopkeeper, human, animal.

Time system:
  The game is turn-based, but also functions on a simulated time system. Inputted actions take a certain amount of in-game time, as is calculated by various metrics applicable to the action. For example, walking from location 1 to location 2 might take 30 in-game minutes, as is calculated by distance between the two points. During this simulated time, stats are effected. Thirst, hunger, exhaustion, etc increase in relation to in-game time. The more time spent travelling between nodes also increases chance and amount of random events on the way.

Inventory & encumbrance system:
  Every object has an encumbrance (generally, it's difficulty to carry) as is calculated by parameters like it's size & weight. The player character inventory is dependant on their strength & clothing (bags will increase it, for instance). A player can carry any set of items with sum less than their maximum encumbrance without reprecution. After the maximum is exceeded, they can continue to pick things up, though exhaustion will be gained at an elevated rate proportional to the excess encumbrance to some exponent.
