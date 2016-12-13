import bgu.spl.a2.VersionMonitor;
import org.junit.*;

import static org.junit.Assert.*;

public class TestVersionMonitor {
    private VersionMonitor vs;
    private Thread thread0;
    private Thread thread1;
    private Thread thread2;
    private Thread thread3;

    @Before
    public void setUp() {
        vs = new VersionMonitor();
        thread0 = null;
        thread1 = null;
        thread2 = null;
        thread3 = null;
    }

    @After
    public void tearDown() {

    }

    @Test
    public void getVersion() {
        assertEquals(0, vs.getVersion());
    }

    @Test
    public void inc() {
        thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++)
                vs.inc();
        });
        thread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++)
                vs.inc();
        });
        thread3 = new Thread(() -> {
            for (int i = 0; i < 100; i++)
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

    @Test
    public void await() {
        thread0 = new Thread(() -> {
            try {
                thread1.sleep(3000);
            } catch (InterruptedException e) {
            }
            vs.inc();
        });
        thread1 = new Thread(() -> {
            try {
                vs.await(0);
            } catch (InterruptedException e) {
            }
            vs.inc();
        });
        thread0.start();
        thread1.start();
        try {
            thread0.join();
            thread1.join();
        } catch (InterruptedException e) {

        }
        assertEquals(2, vs.getVersion());
    }

}