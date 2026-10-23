# CBL
Repository for CBL project of Tom &amp; Domantas

This game is similar to & inspired by: the Oregon Trial, FTL, Dwarf Fortress, & Project Zomboid;
(random event system)	(map)	(complex structures and interactibility)	(inventory system);

The premise of this game is to journey from point A to point B as quickly as possible while maintaining certain vital stats.

# Building and running
```
# Building
## clone the repository and navigate to the project diectory
git clone https://github.com/tlKleinsteuber/CBL.git
cd CBL\CBL\src\main\java\CBL.java

## Make sure to have java installed on your device
```

```
# Running
To run the program from terminal, enter the command:
java CBL.java
```

# Key mechanics:

## Winning:
 To win the game, the player must reach Eindhoven without dying or running out of time.

## Extensive inheritance for class / object system:
  There is an extensive inheritance tree for objects in this game, thus items have many attributes & interactions become more dynamic.

## Time system:
  The game is turn-based, but also functions on a simulated time system. When moving between cities (nodes) or resting, a certain amount of time passes. During this simulated time, stats are effected. Thirst, hunger and exhaustion increase in relation to in-game time and other atributes.
  
## Time limit:
  At the start of the game you have set amount of time to make to the end. Through the time system you lose time, and must make it to the end before time runs out to win. The time limit is X.

## Inventory & items:
  Every object has an encumbrance (in this case - size) & weight. The player character has an inventory, which allows them to carry any set of items with a sum less than their maximum encumbrance. The maximum space of the inventory cannot be exceeded. 
  There are multiple items, each one allows to manage one or a few stats, or one specific item allows to increase the time left. Each item has a buy and sell price (2 lower than buy) and may be bought from shops. Items can be used while in any city and disappear after use.

## Map:
 The map is made up of randomly generated interconnected nodes, excluding the start and end nodes, which have predetermined positions, and a set of 8 guaranteed nodes between them, the 'Main Path'. There is always a path from start to end ('Main Path'), however, not all nodes are part of that path, a. k. a. offshoots, which usually require going back and forward on the same path, however, they usually give a strategic advantage/buff.
 The connections between each node have 2 attributes: distance and quality, both affecting the time it takes to travel between each node. The connections of the 'Main Path' have a lower quality cap compared to connections with and between offshoots. Each node represents a city.

## Cities:
 Cities are randomly picked from the pool of predetermined "made-up" European cities. Each city has it own randomly generated buildings (shops, etc.), which can be moved between each other at no cost.

## Buildings:
 Each building serves a distinct function: shops allow to purchase and sell items, casinos allow to bet money and go into debt, and temples allow to be cured     of curses.

## Random events:
 When travelling between nodes there is a chance for a random event to occur. Random events take from a pool of events, the same event may occur multiple times. Each event affects the gameplay in some way, such as decreasing hunger, changing the current time, etc. There is an event where the player becomes cursed.

## Stats:
 The player has 3 survival stats: hunger (increases with time spent), thirst (has a lower cap, but also decreases less with time spent) and exhaustion (increases even less with time spent travelling, but is affected by the carry weight of all items in the inventory). If any of these stats exceed their cap, the game is over!
 There is also the fame stats, gotten from a random event, it does absolutely nothing (inspired by the wisdom stat in Final Fantasy 1)
 The player may acquire money through their travels, which can be exchanged for items and services.

## Rest:
 The player may rest at any time in any city, reducing their exhaustion but increasing hunger & thirst and losing some time.
 
## Curse status:
 A specific random event will give the player a curse status effect, which doubles all stat increases while travelling. A curse can be gotten rid of in a temple in exchange for money.
