# Project 2 - 150 points

## The George Washington University
## Software Engineering CSCI 2113

### Name: Adnan Shilleh

### Project Description

This project will develop an Maze Game. 

* The Maze Game will have a randomly generated maze. 
* The player will start at a random location in the maze. 
* There will be a number of enemy generators. These generators will periodically spawn an enemy.
* Each enemy will randomly move through the maze, dropping coins as they move.
* The player will score points as they pickup coins.
* The player can kill the enemy from behind or the sides, with an attack.
* If the enemy moves over the player, they die and the game is over.

### Game Requirements

#### Game Board – 25x25 cells

* Need buttons to start, pause, and end the game
* Need a display of the Score and Elapsed time.
* Needs to show final score and elapsed time and the top ten scores of all time.
* Each cell 30x30 pixels

#### Maze Generation

* Create a maze using Aldous-Broder algorithm
  * [http://weblog.jamisbuck.org/2011/1/17/maze-generation-aldous-broder-algorithm](http://weblog.jamisbuck.org/2011/1/17/maze-generation-aldous-broder-algorithm)
  * Tell the player when you are generating a maze

#### Player, Enemy, and Coin Tokens

* Use your imagination, should be smaller than the cells, minus the walls.
* Should be able to distinguish the front of the enemy.
* Coins should be smaller than the player and Enemy and should be cleared when picked up

### Optional Requirements

These requirements can be implemented for extra points, up to 10 points for one requirement, up to 20 points for two requirements, up to 30 points for 4 requirements. Only attempt these if you have completed the initial requirements successfully. You must explicitly state which of these requirements you completed in your Project Readme file and the design document.

* Power-ups
  * Shields
  * Run Mode
* Demo mode
* Change difficulty (number of generators and faster spawn time)
* Music
* Reset and new maze options
* ???


### Submission Requirements

* Use Violet UML and create class diagram for the project.
* Create a maven project for the project that creates an executable jar file.
* Create a design document describes the project.
* Document any optional requirements implemented in Readme.