package game.hive;

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
    @Test
    public void testFirstPieceForcedToCenter() {
        HGameState state = new HGameState();

        boolean result = state.placePiece(0, 0, "Ant", state.getActivePlayer());
        assertTrue(result);

        HexSpace hex = state.getBoard().get(15).get(15);
        assertNotNull(hex.getHex());
        assertEquals("Ant", hex.getHex().getName());

        // Check the board did NOT place at the passed location
        assertNull(state.getBoard().get(0).get(0).getHex());
    }
}
