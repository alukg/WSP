import bgu.spl.a2.Deferred;
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

    }

    @Test
    public void resolve() {

    }

    @Test
    public void whenResolved() {

    }

}