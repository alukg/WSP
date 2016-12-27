package bgu.spl.a2;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * represents a work stealing thread pool - to understand what this class does
 * please refer to your assignment.
 * <p>
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add can only be
 * private, protected or package protected - in other words, no new public
 * methods
 */
public class WorkStealingThreadPool {
    private Thread[] threads;
    private Processor[] processors;
    private Task task;
    private int numOfThreads;
    private VersionMonitor vm;
    public AtomicInteger taskcreated = new AtomicInteger(1);
    public AtomicInteger taskfinished = new AtomicInteger(0);
    /**
     * creates a {@link WorkStealingThreadPool} which has nthreads
     * {@link Processor}s. Note, threads should not get started until calling to
     * the {@link #start()} method.
     * <p>
     * Implementors note: you may not add other constructors to this class nor
     * you allowed to add any other parameter to this constructor - changing
     * this may cause automatic tests to fail..
     *
     * @param nthreads the number of threads that should be started by this
     *                 thread pool
     */
    public WorkStealingThreadPool(int nthreads) {
        numOfThreads = nthreads;
        threads = new Thread[nthreads];
        processors = new Processor[nthreads];
        for (int id = 0; id < nthreads; id++) {
            processors[id] = new Processor(id, this);
        }
        vm= new VersionMonitor(this);
    }

    /**
     * submits a task to be executed by a processor belongs to this thread pool
     *
     * @param task the task to execute
     */
    public void submit(Task<?> task) {
        this.task = task;
        System.out.println("Submit first task to thread.");
        Random rand = new Random();
        int firstProc = rand.nextInt(processors.length);
        processors[firstProc].addTask(task);
    }

    /**
     * closes the thread pool - this method interrupts all the threads and wait
     * for them to stop - it is returns *only* when there are no live threads in
     * the queue.
     * <p>
     * after calling this method - one should not use the queue anymore.
     *
     * @throws InterruptedException          if the thread that shut down the threads is
     *                                       interrupted
     * @throws UnsupportedOperationException if the thread that attempts to
     *                                       shutdown the queue is itself a processor of this queue
     */
    public void shutdown() throws InterruptedException {
        while (!task.getResult().isResolved()) {
            Thread.currentThread().sleep(1000);
        }
        System.out.println("taskcreated: " + taskcreated + " taskfinsihed: " + taskfinished);
        for (Thread thread : threads){
            thread.interrupt();
        }

    }

    /**
     * start the threads belongs to this thread pool
     */
    public void start() {
        for (int id = 0; id < numOfThreads; id++) {
            threads[id] = new Thread(processors[id]);
            threads[id].start();
        }
    }

    public int getNumOfProccessors(){
        return processors.length;
    }

    public Processor[] getProcessors(){
        return  processors;
    }

    public VersionMonitor getVersionMonitor(){
        return vm;
    }
}
