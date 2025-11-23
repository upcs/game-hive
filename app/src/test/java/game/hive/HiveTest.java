package game.hive;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import game.hive.State.HGameState;
import game.hive.State.Hex;
import game.hive.State.HexSpace;


@RunWith(RobolectricTestRunner.class)
public class HiveTest {
    public HMainActivity activity;

    @Before
    public void setup() throws Exception {
        activity = Robolectric.buildActivity(HMainActivity.class).create().resume().get();
    }

    // movePiece test case
    // moving a piece from (row,col) to (diffRow,diffCol) for the correct player
    // should pass and turn
    //  Felipe's test for center
    @Test
    public void testFirstPieceForcedToCenter() {
        HGameState state = new HGameState();

        boolean result = state.placePiece(0, 0, "Ant", state.getActivePlayer());
        assertTrue(result);

//        public void movePiece_validMoveMovesPieceAndFlipsTurn() {
//        HGameState state = new HGameState();

        // First place a piece for player 1 at the center
        // ignores this and places at 15,15
        assertTrue(state.placePiece(10, 10, "Ant", 1));
        assertEquals(0, state.getActivePlayer()); // now it's player 0's turn

        //move that piece for player 0
        boolean moved = state.movePiece(15, 15, 16, 15, 0);
        assertTrue("valid move should return true", moved);

        //old location should be empty
        assertNull("original hex should now be empty",
                state.getBoard().get(15).get(15).getHex());

        //new location should contain piece
        assertNull("destination hex should contain the piece",
                state.getBoard().get(16).get(15).getHex());

        //turn should flip back to player 1
        assertEquals("after moving, activePlayer should flip to 1",
                1, state.getActivePlayer());
    }



    //copy constructor must create a deep copy...
    //changing the copy's board should not change the original.
    /**
     * Copy constructor must create a deep copy of the board:
     * changing the copy's board must NOT change the original.
     */
    @Test
    public void copyConstructor_makesDeepCopyOfBoard() {
        HGameState original = new HGameState();

        // First placement goes to forced center: (16, 8)
        assertTrue("First placement should succeed",
                original.placePiece(10, 10, "Ant", 1));

        HexSpace origCenter = original.getBoard().get(16).get(8);
        assertNotNull("Original center should have a piece before copy",
                origCenter.getHex());

        // Make a copy using the copy constructor
        HGameState copy = new HGameState(original);

        // Change ONLY the copy's center hex
        copy.getBoard().get(16).get(8).setHex(null);

        // Copy's center should now be empty
        assertNull("Copy's center should be empty after clearing it",
                copy.getBoard().get(16).get(8).getHex());

        // Original should still have its piece → proves deep copy
        assertNotNull("Original center shouldn't be affected by changes in copy",
                origCenter.getHex());
    }



    /**
     * Moving a piece from (10,10) to (8,10) for the current active player
     * should succeed, update the board, and flip the turn.
     */
    @Test
    public void movePiece_validMoveMovesPieceAndFlipsTurn() {
        HGameState state = new HGameState();

        // activePlayer starts as 1 (BLACK).
        // mark black's bee as already placed so moves allowed
        boolean[] bees = state.getIsBeePlaced();
        bees[1] = true;

        // put a single black QueenBee on the board at (10,10)
        int fromX = 10;
        int fromY = 10;
        state.getBoard().get(fromX).get(fromY)
                .setHex(new Hex(Hex.Color.BLACK, "QueenBee"));

        // choose a neighbor of (10,10) for even row 10, (8,10) is a neighbor.
        int toX = 8;
        int toY = 10;

        // destination must start empty
        assertNull("Destination should start empty",
                state.getBoard().get(toX).get(toY).getHex());

        // attempt to move the queen as player 1
        boolean moved = state.movePiece(fromX, fromY, toX, toY, 1);
        assertTrue("Valid move should return true", moved);

        // old location should now be empty
        assertNull("Original hex should now be empty",
                state.getBoard().get(fromX).get(fromY).getHex());

        // new location should now contain the queen
        assertNotNull("Destination hex should contain the piece",
                state.getBoard().get(toX).get(toY).getHex());
        assertEquals("QueenBee",
                state.getBoard().get(toX).get(toY).getHex().getName());

        // after a successful move, activePlayer should flip 1 to 0
        assertEquals("After moving, activePlayer should flip to 0",
                0, state.getActivePlayer());
    }



    @Test
    public void testBeeRestrictions() { //Ethan Brown
        //create new gameState
        HGameState start = new HGameState();

        //start by having player place ant in a diagonal line
        start.placePiece(16, 8, "Ant", 1);
        start.placePiece(17, 8, "Ant", 0);
        start.placePiece(15, 7, "Ant", 1);
        start.placePiece(18, 9, "Ant", 0);


        //player 1 attempts to move a piece legally but should be stopped because they do not have a bee yet
        start.movePiece(15, 7, 14, 8, 1);

        //check to make sure this move did not happen and the destination is still null
        HexSpace Destination = start.getBoard().get(14).get(8);
        assertNull(Destination.getHex());
        //should still be player 1's turn since their last command failed so now they place bee
        start.placePiece(17, 7, "QueenBee", 1);
        //check to make sure isBeePlaced boolean is true for player 1
        assertTrue(start.getIsBeePlaced()[1]);
        //player 0 still wants to place ants
        start.placePiece(19, 9, "Ant", 0);
        //player 1 tries to make the same move again but this time it should work
        start.movePiece(15, 7, 14, 8, 1);

        //check to make sure the Ant moved
        HexSpace updatedDestination = start.getBoard().get(14).get(8);

        Assert.assertNotNull(updatedDestination.getHex());
        //make sure that it is the ant that moved
        assertEquals("Ant", updatedDestination.getHex().getName());
        //player 0 will try and place their 4th piece but since it is not the queen it should not work
        start.placePiece(20, 10, "Spider", 0);
        //check to make sure the spider was not placed
        assertNull(start.getBoard().get(20).get(10).getHex());
        //try again this time with the bee
        start.placePiece(20, 10, "QueenBee", 0);
        //make sure the bee was placed
        assertEquals("QueenBee", start.getBoard().get(20).get(10).getHex().getName());
        //check to make sure the isBeePlaced boolean is true for player 0
        assertTrue(start.getIsBeePlaced()[0]);


    }

    @Test
    public void testColorPlacingRestrictions() { // Ethan Brown
        //create new gameState
        HGameState start = new HGameState();

        //start by having player place ant in a diagonal line
        start.placePiece(16, 8, "Ant", 1);
        start.placePiece(17, 8, "Ant", 0);
        start.placePiece(15, 7, "Ant", 1);
        start.placePiece(18, 9, "Ant", 0);

        //try and place black bee on the white side
        start.placePiece(19, 9, "QueenBee", 1);
        //this should fail and the place should remain empty
        assertNull(start.getBoard().get(19).get(9).getHex());
        //try again to place black bee this time not touching anything
        start.placePiece(20, 8, "QueenBee", 1);
        //this should fail and the place should remain empty
        assertNull(start.getBoard().get(20).get(8).getHex());
        //try again touching both black and white hexes
        start.placePiece(18, 8, "QueenBee", 1);
        //this should fail and the place should remain empty
        assertNull(start.getBoard().get(18).get(8).getHex());

        //finally try and place piece touching just black hexes
        start.placePiece(17, 7, "QueenBee", 1);
        //this should succeed and the bee should be in that spot
        assertEquals("QueenBee", start.getBoard().get(17).get(7).getHex().getName());

        //should finally be player 0's turn again
        start.placePiece(19, 9, "Ant", 0);

        //player 1 moves the bee
        start.movePiece(17, 7, 18, 8, 1);

        //player 0 tries to place white bee touching the black bee that just moved as well as white ants
        start.placePiece(19, 8, "QueenBee", 0);
        //check to make sure this does not work
        assertNull(start.getBoard().get(19).get(8).getHex());


    }

    @Test
    public void dontBreakHive() {

    }

}
