import bgu.spl.a2.Deferred;
import bgu.spl.a2.Task;
import org.junit.*;

import static org.junit.Assert.*;

public class TestDeferred {
    Deferred deferred;

    @Before
    public void setUp() {
        deferred = new Deferred<Integer>();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void get() {
        deferred.resolve(5);
        assertEquals(5, deferred.get());
        assertNotEquals(4, deferred.get());
    }

    @Test
    public void isResolved() {
        assertEquals(false,deferred.isResolved());
        deferred.resolve(5);
        assertEquals(true, deferred.isResolved());
    }

    @Test
    public void resolve() {
        Deferred testedDef = new Deferred<Integer>();

        assertEquals(false,testedDef.isResolved());
        assertEquals(false,deferred.isResolved());
        deferred.resolve(5);
        assertEquals(true,deferred.isResolved());

        class OneShotTask implements Runnable {
            private Deferred def;

            private OneShotTask(Deferred newDef) {
                def = newDef;
            }

            public void run() {
                def.resolve(6);
            }
        }

        deferred.whenResolved(new OneShotTask(testedDef));
        assertEquals(6, testedDef.get());
        assertEquals(true, testedDef.isResolved());
    }

    @Test
    public void whenResolved() {
        assertEquals(false,deferred.isResolved());
        deferred.resolve(5);
        assertEquals(true, deferred.isResolved());
        Runnable test = ()->{deferred.resolve(6);};
        deferred.whenResolved(test);
        assertEquals(6, deferred.get());
    }

}