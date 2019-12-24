import java.util.Random;
import java.util.ArrayList;
import java.awt.Point;

public class MyAgent extends Agent
{
    Random r;

    /**
     * Constructs a new agent, giving it the game and telling it whether it is Red or Yellow.
     * 
     * @param game The game the agent will be playing.
     * @param iAmRed True if the agent is Red, False if the agent is Yellow.
     */
    public MyAgent(Connect4Game game, boolean iAmRed)
    {
        super(game, iAmRed);
        r = new Random();
    }

    /**
     * The move method is run every time it is this agent's turn in the game. You may assume that
     * when move() is called, the game has at least one open slot for a token, and the game has not
     * already been won.
     * 
     * By the end of the move method, the agent should have placed one token into the game at some
     * point.
     * 
     * After the move() method is called, the game engine will check to make sure the move was
     * valid. A move might be invalid if:
     * - No token was place into the game.
     * - More than one token was placed into the game.
     * - A previous token was removed from the game.
     * - The color of a previous token was changed.
     * - There are empty spaces below where the token was placed.
     * 
     * If an invalid move is made, the game engine will announce it and the game will be ended.
     * 
     */
    public void move()
    {
        if (iCanWin() > -1) {
            moveOnColumn(verifyMove(iCanWin()));
            System.out.println("MyAgent moved by iCanWin()");
        } else if (theyCanWin() > -1) {
            moveOnColumn(verifyMove(theyCanWin()));
            System.out.println("MyAgent moved by theyCanWin()");
        } else if (iMayWin() > -1) {
            moveOnColumn(verifyMove(iMayWin()));
            System.out.println("MyAgent moved by iMayWin()");
        } else {
            moveOnColumn(verifyMove(randomMove()));
            System.out.println("MyAgent moved by randomMove() from move()");
        }
    }
    
    /**
     * Temporary helper for the move() method.
     * 
     * Verifies that a column is not full.
     */
    private int verifyMove(int column) {
        if (myGame.getColumn(column).getIsFull()) {
            return column;
        } else {
            System.out.println("MyAgent movedby randomMove() - previous move log voided.");
            return this.randomMove();
        }
    }

    /**
     * Drops a token into a particular column so that it will fall to the bottom of the column.
     * If the column is already full, nothing will change.
     * 
     * @param columnNumber The column into which to drop the token.
     */
    public void moveOnColumn(int columnNumber)
    {
        int lowestEmptySlotIndex = getLowestEmptyIndex(myGame.getColumn(columnNumber));   // Find the top empty slot in the column
                                                                                                  // If the column is full, lowestEmptySlot will be -1
        if (lowestEmptySlotIndex > -1)  // if the column is not full
        {
            Connect4Slot lowestEmptySlot = myGame.getColumn(columnNumber).getSlot(lowestEmptySlotIndex);  // get the slot in this column at this index
            if (iAmRed) // If the current agent is the Red player...
            {
                lowestEmptySlot.addRed(); // Place a red token into the empty slot
            }
            else // If the current agent is the Yellow player (not the Red player)...
            {
                lowestEmptySlot.addYellow(); // Place a yellow token into the empty slot
            }
        }
    }

    /**
     * Returns the index of the top empty slot in a particular column.
     * 
     * @param column The column to check.
     * @return the index of the top empty slot in a particular column; -1 if the column is already full.
     */
    public int getLowestEmptyIndex(Connect4Column column) {
        int lowestEmptySlot = -1;
        for  (int i = 0; i < column.getRowCount(); i++)
        {
            if (!column.getSlot(i).getIsFilled())
            {
                lowestEmptySlot = i;
            }
        }
        return lowestEmptySlot;
    }

    /**
     * Returns a random valid move. If your agent doesn't know what to do, making a random move
     * can allow the game to go on anyway.
     * 
     * @return a random valid move.
     */
    public int randomMove()
    {
        int i = r.nextInt(myGame.getColumnCount());
        while (getLowestEmptyIndex(myGame.getColumn(i)) == -1)
        {
            i = r.nextInt(myGame.getColumnCount());
        }
        return i;
    }

    /**
     * Returns a suggested column that would allow the agent to win.
     * 
     * You might want your agent to check to see if it has a winning move available to it so that
     * it can go ahead and make that move. Implement this method to return what column would
     * allow the agent to win.
     *
     * @return the column that would allow the agent to win. -1 if no suggestions.
     */
    public int iCanWin()
    {   
        /*final int CONNECT_TO_WIN = 4;
        int rows = myGame.getRowCount();
        int cols = myGame.getColumnCount();
        
        for (int i = 0; i < cols; i++) {
            Connect4Column currentColumn = myGame.getColumn(i);
            if (!currentColumn.getIsFull()) {
                for (int j = 0; j < rows; j++) {
                    Connect4Slot currentSlot = currentColumn.getSlot(i);
                    if (!currentSlot.getIsFiled()) {
                        
                        /*
                           // Efficieny checking
                        if (i < cols - CONNECT_TO_WIN) {
                            // Check R, D, U
                            if (j < rows - CONNECT_TO_WIN) {
                                // Check R, U
                            } else if (j = rows - CONNECT_TO_WIN {
                                //Check
                            }
                        } else if (i = cols + CONNECT_TO_WIN) {
                            // Check L, D, U
                            if (j) {}
                        } else {
                            // Check R, D, L, U
                            if (j) {}
                        }
                        
                        
                        if (i + CONNECT_TO_WIN < cols) {
                            // Check L
                        } else if (i*/
                        
                        
                        /*Connect4Slot upperLeft =    null;
                        Connect4Slot left =         null;
                        Connect4Slot lowerleft =    null;
                        Connect4Slot lower =        null;
                        Connect4Slot lowerRight =   null;
                        Connect4Slot right =        null;
                        Connect4Slot upperRight =   null;
                        
                        Connect4Slot[] primarySlots = [upperLeft, left, lowerLeft, lower, lowerRight, right, upperRight, upperRight];
                        
                        try {
                            upperLeft =     myGame.getColumn(i - 1).getRow(j + 1);
                            left =          myGame.getColumn(i - 1).getRow(j);
                            lowerLeft =     myGame.getColumn(i - 1).getRow(j - 1);
                            lower =         myGame.getColumn(i).    getRow(j - 1);
                            lowerRight =    myGame.getColumn(i + 1).getRow(j - 1);
                            right =         myGame.getColumn(i + 1).getRow(j);
                            upperRight =    myGame.getColumn(i + 1).getRow(j + 1);
                        } catch (ArrayIndexOutOfBoundsException e) {}
                        
                        ArrayList<Connect4Slot> secondarySlots = new ArrayList<Connect4Slot>();
                        for (int s = 0; s < primarySlots.length; s++) {
                            if (primarySlots[s] instanceof Connect4Slot && primarySlots[s].getIsRed()) {
                                secondarySlots.add(primarySlots[s];
                            }
                        }
                        
                        ArraayList<Connect4Slot> tertiarySlots = new Arraylist<Connect4Slot>();
                        for (Connect4Slot t : tertiarySlots) {
                            if (){}
                        }
                }
            }
        }*/
        int win = -1;
        
        for (int c = 0; c < myGame.getColumnCount(); c++) { // Loop breaking learned from http://stackoverflow.com/questions/886955/breaking-out-of-nested-loops-in-java
            Connect4Column column = myGame.getColumn(c);
            //if (!column.getIsFull()) {
                for (int r = 0; r < column.getRowCount(); r++) {
                    winCheck:
                    try {
                        if (myGame.getColumn(c).getSlot(r).getIsRed()) {
                            ArrayList<Point> secondaryConnect = this.surrounding(c, r);
                            if (secondaryConnect.size() == 0) {
                                break winCheck;
                            } else {
                                Point s = secondaryConnect.get(0);
                                ArrayList<Point> tertiaryConnect = this.surrounding((int) s.getX(), (int) s.getY());
                                if (tertiaryConnect.size() == 0) {
                                    break winCheck;
                                } else {
                                    Point t = tertiaryConnect.get(0);
                                    ArrayList<Point> quarternaryConnect = this.surrounding((int) t.getX(), (int) t.getY());
                                    if (quarternaryConnect.size() == 0) {
                                        break winCheck;
                                    } else {
                                        win = (int) quarternaryConnect.get(0).getX();
                                    }
                                }
                            }
                        } else {
                            break winCheck;
                        }
                    } catch (NullPointerException e) {}
                }
            //}
        }
        
        return win;
    }
    
    public int iMayWin() {
        int win = -1;
        
        for (int c = 0; c < myGame.getColumnCount(); c++) { // Loop breaking learned from http://stackoverflow.com/questions/886955/breaking-out-of-nested-loops-in-java
            Connect4Column column = myGame.getColumn(c);
            //if (!column.getIsFull()) {
                for (int r = 0; r < column.getRowCount(); r++) {
                    winCheck:
                    try {
                        if (myGame.getColumn(c).getSlot(r).getIsRed()) {
                            myGame.getColumn(c).getSlot(r).highlight();
                            ArrayList<Point> secondaryConnect = this.surrounding(c, r);
                            for (Point s : secondaryConnect) {
                                ArrayList<Point> tertiaryConnect = this.surrounding((int) s.getX(), (int) s.getY());
                                
                                for (Point t : tertiaryConnect) {
                                    ArrayList<Point> quarternaryConnect = this.surrounding((int) t.getX(), (int) t.getY());
                                    
                                    if (quarternaryConnect.size() > 0) {
                                        win = (int) quarternaryConnect.get(0).getX();
                                        break winCheck;
                                    } else if (tertiaryConnect.size() > 0) {
                                        win = (int) tertiaryConnect.get(0).getX();
                                        break winCheck;
                                    } else if (secondaryConnect.size() > 0) {
                                        win = (int) secondaryConnect.get(0).getX();
                                        break winCheck;
                                    }
                                }
                            }
                        } else {
                            break winCheck;
                        }
                    } catch (NullPointerException e) {}
                }
            //}
        }
        
        return win;
    }
    
    /**
     * Returns the column that would allow the opponent to win.
     * 
     * You might want your agent to check to see if the opponent would have any winning moves
     * available so your agent can block them. Implement this method to return what column should
     * be blocked to prevent the opponent from winning.
     *
     * @return the column that would allow the opponent to win.
     */
    public int theyCanWin()
    {
        int win = -1;
        
        for (int c = 0; c < myGame.getColumnCount(); c++) { // Loop breaking learned from http://stackoverflow.com/questions/886955/breaking-out-of-nested-loops-in-java
            Connect4Column column = myGame.getColumn(c);
            //if (!column.getIsFull()) {
                for (int r = 0; r < column.getRowCount(); r++) {
                    winCheck:
                    try {
                        if (!myGame.getColumn(c).getSlot(r).getIsRed()) {
                            ArrayList<Point> secondaryConnect = this.surrounding(c, r);
                            if (secondaryConnect.size() == 0) {
                                break winCheck;
                            } else {
                                for (Point s : secondaryConnect) {
                                    ArrayList<Point> tertiaryConnect = this.surrounding((int) s.getX(), (int) s.getY());
                                    if (tertiaryConnect.size() == 0) {
                                        break winCheck;
                                    } else {
                                        for (Point t : tertiaryConnect) {
                                            ArrayList<Point> quarternaryConnect = this.surrounding((int) t.getX(), (int) t.getY());
                                            if (quarternaryConnect.size() == 0) {
                                                break winCheck;
                                            } else {
                                                win = (int) quarternaryConnect.get(0).getX();
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            break winCheck;
                        }
                    } catch (NullPointerException e) {}
                }
            //}
        }
        
        return win;
    }
    
    public int theyMayWin() {
        int win = -1;
        
        for (int c = 0; c < myGame.getColumnCount(); c++) {
            for (int r = 0; r < myGame.getColumnCount(); r++) {
                winCheck:
                try {
                    if (!myGame.getColumn(c).getSlot(r).getIsRed()) {
                        ArrayList<Point> secondaryConnect = this.surrounding(c, r);
                        for (Point s : secondaryConnect) {
                            ArrayList<Point> tertiaryConnect = this.surrounding((int) s.getX(), (int) s.getY());
                            
                            for (Point t : tertiaryConnect) {
                                ArrayList<Point> quarternaryConnect = this.surrounding((int) t.getX(), (int) t.getY());
                                
                                if (quarternaryConnect.size() > 0) {
                                    win = (int) quarternaryConnect.get(0).getX();
                                    break winCheck;
                                } else if (tertiaryConnect.size() > 0) {
                                    win = (int) tertiaryConnect.get(0).getX();
                                    break winCheck;
                                } else if (secondaryConnect.size() > 0) {
                                    win = (int) secondaryConnect.get(0).getX();
                                    break winCheck;
                                }
                            }
                        }
                    } else {
                        break winCheck;
                    }
                } catch (NullPointerException e) {}
            }
        }
        
        return win;
    }

    /**
     * Returns the name of this agent.
     *
     * @return the agent's name
     */
    public String getName()
    {
        return "My Agent";
    }
    
    /**
     * Returns the slots filled with the same color immediate in position to a slot.
     * 
     * @param column Index of column slot of interest is located in
     * @param row Index of row slot of interest is located in
     * 
     * @return Where the surrounding slots are located, where x is the column and y is the row.
     */
    public ArrayList<Point> surrounding(int column, int row) {
        ArrayList<Point> slots = new ArrayList<Point>();
        
        for (int c = -1; c <= 1; c++) {
            for (int r = -1; r <= 1; r++) {
                try {
                    Connect4Slot slot = myGame.getColumn(column + c).getSlot(row + r);
                    
                    if (slot.getIsRed() == myGame.getColumn(c).getSlot(r).getIsRed()) {
                        slots.add(new Point(column + c, row + r));
                    }
                } catch (NullPointerException e) {}
            }
        }
        
        //System.out.println("this.surrounding(" + column + ", " + row + ") = " + slots);
        return slots;
    }
}

/**
 * Returns chains the largest and leftmost chain of colo
 */