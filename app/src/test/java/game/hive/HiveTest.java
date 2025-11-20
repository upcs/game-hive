package game.hive;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import game.hive.State.HGameState;
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
    @Test
    public void testFirstPieceForcedToCenter() {
        HGameState state = new HGameState();

        boolean result = state.placePiece(0, 0, "Ant", state.getActivePlayer());
        assertTrue(result);

        public void movePiece_validMoveMovesPieceAndFlipsTurn() {
        HGameState state = new HGameState();

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
        assertNotNull("destination hex should contain the piece",
                state.getBoard().get(16).get(15).getHex());

        //turn should flip back to player 1
        assertEquals("after moving, activePlayer should flip to 1",
                1, state.getActivePlayer());
    }



    //copy constructor must create a deep copy...
    //changing the copy's board should not change the original.
    @Test
    public void copyConstructor_makesDeepCopyOfBoard() {
        HGameState original = new HGameState();
        original.placePiece(10, 10, "Ant", 1); // puts piece at (15,15)

        //capture reference to original center space and its hex
        HexSpace origCenter = original.getBoard().get(15).get(15);
        assertNotNull("original center should have a piece before copy",
                origCenter.getHex());

        //make a copy
        HGameState copy = new HGameState(original);

        //change the copy's center hex
        copy.getBoard().get(15).get(15).setHex(null);

        //original should still have its piece
        assertNotNull("original center shouldnt be affected by changes in copy",
                origCenter.getHex());
    }

        HexSpace hex = state.getBoard().get(15).get(15);
        assertNotNull(hex.getHex());
        assertEquals("Ant", hex.getHex().getName());

        // Check the board did NOT place at the passed location
        assertNull(state.getBoard().get(0).get(0).getHex());
    }
    @Test
    public void forceBeeFirstFourPlaced(){ //Ethan Brown

    }
    @Test
    public void stopMoveBeforeBee(){ // Ethan Brown

    }
    @Test
    public void dontBreakHive(){

    }

}
