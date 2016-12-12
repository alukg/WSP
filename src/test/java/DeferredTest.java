import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeferredTest {
    Deferred deferred;

    @BeforeEach
    void setUp() {
        deferred = new Deferred<Integer>();
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void get() {
        deferred.resolve(5);
        assertEquals(5,deferred.get());
        assertNotEquals(4,deferred.get());
    }

    @Test
    void isResolved() {

    }

    @Test
    void resolve() {

    }

    @Test
    void whenResolved() {

    }

}