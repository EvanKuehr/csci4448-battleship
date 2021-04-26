# CSCI4448 - Battleship Game
Names: Obaid Ebadi, Nate Jones, Evan Kuehr, Andrew Mitchell

# Description
Battleship is a classic two player game in which players use strategy to to search for the enemy’s fleet of ships and try to take them down one by one. The winner of the game is the player that can take out the entire enemy’s fleet before them. There are some modifications we made to the game, including the concept of a “captains quarters”, underwater ship placement, and a player being able to move their entire fleet.

## Install and Run Game
1. have Java installed
2. have Node installed
3. have git installed
4. `git clone https://github.com/EvanKuehr/csci4448-battleship.git`
5. in one terminal
   1. `cd battleship`
   2. `java -jar build/libs/battleship-0.0.1-SNAPSHOT.jar`
6. in another terminal
   1. `cd ui`
   2. `npm install`
   3. `npm yarn dev`
7. for each player
   1. open a browser to local host port given by second terminal
   2. first player CREATE GAME
   3. second player JOIN GAME using room code given to first player
