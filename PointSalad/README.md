# PointSalad Readme

**PointSalad** is a card game. It supports local and remote players including multiple player types

## Usage

1. **Compilation and Execution:**

 You can compile the game with `javac -cp .:"lib\*" PointSalad.java`
 You can run the server with `java -cp .:"lib\*" PointSalad` 
 (will then let you set number of players and bots in the beginning of the game)
 You can also run the server with arguments for number of players and bots
 `java -cp .:"lib\*" PointSalad [#Players] [#Bots]`
  You can connect an “online” client player: `java -cp .:"lib\*" PointSalad 127.0.0.1`

2. **Command-Line Arguments:**

   - To play as a remote player, provide the IP address as the argument.
   - To play as a local player, specify the number of players and bots as arguments.


3. **Unit Testing:**

- To test the game with current tests, run unittest.bat
- For adding new tests, place them in the 'test' folder and add them to the unittest.bat file for compiling and to run them
