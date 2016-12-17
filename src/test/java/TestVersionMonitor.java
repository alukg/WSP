import bgu.spl.a2.VersionMonitor;
import org.junit.*;

import static org.junit.Assert.*;

public class TestVersionMonitor {
    private VersionMonitor vs;
    private Thread thread0;
    private Thread thread1;
    private Thread thread2;
    private Thread thread3;

    /**
     * Setup objects to work with for the tests.
     */
    @Before
    public void setUp() {
        vs = new VersionMonitor();
        thread0 = null;
        thread1 = null;
        thread2 = null;
        thread3 = null;
    }

    /**
     * Checks that the function returns the right value.
     */
    @Test
    public void getVersion() {
        assertEquals(0, vs.getVersion());
    }

    /**
     * Checks that the function increase in synchronize without mistakes.
     */
    @Test
    public void inc() {
        thread1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++)
                vs.inc();
        });
        thread2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++)
                vs.inc();
        });
        thread3 = new Thread(() -> {
            for (int i = 0; i < 10000; i++)
                vs.inc();
        });
        thread1.start();
        thread2.start();
        thread3.start();
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {

        }
        assertEquals(300, vs.getVersion());
    }

    /**
     * Checks that the thread actually wait until other thread insert into the inc function.
     */
    @Test
    public void await() {
        thread0 = new Thread(() -> {
            try {
                vs.await(0);
            } catch (InterruptedException e) {
            }
            assertEquals(1, vs.getVersion());
        });
        thread1 = new Thread(() -> vs.inc());
        thread0.start();
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
        }
        thread1.start();
    }
}