### Place all ships

Stage 2/6:Place all ships

#### Description

Now that we've figured out the rules of the game let's continue with positioning the ships and drawing them on our game field! The goal of this stage is to place all the ships on the game field according to the rules.


#### Objectives

You have 5 ships:

* Aircraft Carrier is 5 cells
* Battleship is 4 cells
* Submarine is 3 cells
* Cruiser is also 3 cells
* Destroyer is 2 cells.

In this stage, you should arrange them all on the game field.

1. Start placing your ships with the largest one.
2. For each ship read two coordinates: the beginning and the end of the ship. Again, the order of the coordinates does not matter.
3. Add new ships to a game field and output it the same way as in the previous stage.
4. If the user has entered coordinates in such a way that the length of the created ship does not match the expected length, this should be considered an incorrect input. Also, the game rules state that ships cannot be adjacent to each other. For both of these cases report it with a message containing `Error` word.


#### Example

The greater-than symbol followed by a space `>` represents the user input. Note that it's not part of the input.


```
  1 2 3 4 5 6 7 8 9 10
A ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
B ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
C ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
D ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~

Enter the coordinates of the Aircraft Carrier (5 cells):

> F3 F7

  1 2 3 4 5 6 7 8 9 10
A ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
B ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
C ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
D ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
F ~ ~ O O O O O ~ ~ ~
G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~

Enter the coordinates of the Battleship (4 cells):

> A1 D1

  1 2 3 4 5 6 7 8 9 10
A O ~ ~ ~ ~ ~ ~ ~ ~ ~
B O ~ ~ ~ ~ ~ ~ ~ ~ ~
C O ~ ~ ~ ~ ~ ~ ~ ~ ~
D O ~ ~ ~ ~ ~ ~ ~ ~ ~
E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
F ~ ~ O O O O O ~ ~ ~
G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~

Enter the coordinates of the Submarine (3 cells):

> J7 J10

Error! Wrong length of the Submarine! Try again:

> J10 J8

  1 2 3 4 5 6 7 8 9 10
A O ~ ~ ~ ~ ~ ~ ~ ~ ~
B O ~ ~ ~ ~ ~ ~ ~ ~ ~
C O ~ ~ ~ ~ ~ ~ ~ ~ ~
D O ~ ~ ~ ~ ~ ~ ~ ~ ~
E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
F ~ ~ O O O O O ~ ~ ~
G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
J ~ ~ ~ ~ ~ ~ ~ O O O

Enter the coordinates of the Cruiser (3 cells):

> B9 D8

Error! Wrong ship location! Try again:

> B9 D9

  1 2 3 4 5 6 7 8 9 10
A O ~ ~ ~ ~ ~ ~ ~ ~ ~
B O ~ ~ ~ ~ ~ ~ ~ O ~
C O ~ ~ ~ ~ ~ ~ ~ O ~
D O ~ ~ ~ ~ ~ ~ ~ O ~
E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
F ~ ~ O O O O O ~ ~ ~
G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
J ~ ~ ~ ~ ~ ~ ~ O O O

Enter the coordinates of the Destroyer (2 cells):

> E6 D6

Error! You placed it too close to another one. Try again:

> I2 J2

  1 2 3 4 5 6 7 8 9 10
A O ~ ~ ~ ~ ~ ~ ~ ~ ~
B O ~ ~ ~ ~ ~ ~ ~ O ~
C O ~ ~ ~ ~ ~ ~ ~ O ~
D O ~ ~ ~ ~ ~ ~ ~ O ~
E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
F ~ ~ O O O O O ~ ~ ~
G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
I ~ O ~ ~ ~ ~ ~ ~ ~ ~
J ~ O ~ ~ ~ ~ ~ O O O
```
