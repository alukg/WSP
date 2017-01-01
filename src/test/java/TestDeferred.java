//import bgu.spl.a2.Deferred;
//import bgu.spl.a2.Task;
//
//
//
//
//public class TestDeferred {
//    Deferred deferred;
//
//    /**
//     * Setup Deferred object to work with for the tests.
//     */
//    @Before
//    public void setUp() { deferred = new Deferred<Integer>(); }
//
//    @After
//    public void tearDown() {
//
//    }
//
//    /**
//     * Checks if the get function get the right value.
//     */
//    @Test
//    public void get() {
//        deferred.resolve(5);
//        assertEquals(5, deferred.get());
//        assertNotEquals(4, deferred.get());
//    }
//
//    /**
//     * Checks first that the Deferred is unresolved at the start and than changes.
//     */
//    @Test
//    public void isResolved() {
//        assertEquals(false,deferred.isResolved());
//        deferred.resolve(5);
//        assertEquals(true, deferred.isResolved());
//    }
//
//    /**
//     *  Checks that the function run the callback that saved and change the value of the Deferred object.
//     */
//    @Test
//    public void resolve() {
//        Deferred testedDef = new Deferred<Integer>();
//
//        assertEquals(false,testedDef.isResolved());
//        assertEquals(false,deferred.isResolved());
//        deferred.resolve(5);
//        assertEquals(true,deferred.isResolved());
//
//        class OneShotTask implements Runnable {
//            private Deferred def;
//
//            private OneShotTask(Deferred newDef) {
//                def = newDef;
//            }
//
//            public void run() {
//                def.resolve(6);
//            }
//        }
//
//        deferred.whenResolved(new OneShotTask(testedDef));
//        assertEquals(6, testedDef.get());
//        assertEquals(true, testedDef.isResolved());
//    }
//
//    /**
//     * First checks that the function run the callback when the Deferred object already solved.
//     * Than checks that the function saves the callback and don't run it until the resolve function is run.
//     */
//    @Test
//    public void whenResolved() {
//        assertEquals(false,deferred.isResolved());
//        deferred.resolve(5);
//        assertEquals(true, deferred.isResolved());
//        Runnable test = ()->{deferred.resolve(6);};
//        deferred.whenResolved(test);
//        assertEquals(6, deferred.get());
//
//        deferred = new Deferred<Integer>();
//        Deferred testedDef = new Deferred<Integer>();
//        class OneShotTask implements Runnable {
//            private Deferred def;
//
//            private OneShotTask(Deferred newDef) {
//                def = newDef;
//            }
//
//            public void run() {
//                def.resolve(6);
//            }
//        }
//        deferred.whenResolved(new OneShotTask(testedDef));
//        assertEquals(false,testedDef.isResolved());
//        deferred.resolve(5);
//        assertEquals(6, testedDef.get());
//    }
//
//}