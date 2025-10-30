package game.hive;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RobolectricTestRunner.class)
public class HiveTest {
    public HMainActivity activity;

    @Before
    public void setup() throws Exception {
        activity = Robolectric.buildActivity(HMainActivity.class).create().resume().get();
    }
    @Test

}
