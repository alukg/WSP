package bgu.spl.a2;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * an abstract class that represents a task that may be executed using the
 * {@link WorkStealingThreadPool}
 * <p>
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add to this class can
 * only be private!!!
 *
 * @param <R> the task result type
 */
public abstract class Task<R> {
    private Task thisTask = this;
    private Deferred deferred = new Deferred<R>();
    private boolean is_started = false;
    private boolean returned = false;
    private Runnable end_callback;
    private Processor currProc;
    private AtomicInteger childsLocks = new AtomicInteger(0);

    /**
     * start handling the task - note that this method is protected, a handler
     * cannot call it directly but instead must use the
     * {@link #handle(bgu.spl.a2.Processor)} method
     */
    protected abstract void start();

    /**
     * start/continue handling the task
     * <p>
     * this method should be called by a processor in order to start this task
     * or continue its execution in the case where it has been already started,
     * any sub-tasks / child-tasks of this task should be submitted to the queue
     * of the handler that handles it currently
     * <p>
     * IMPORTANT: this method is package protected, i.e., only classes inside
     * the same package can access it - you should *not* change it to
     * public/private/protected
     *
     * @param handler the handler that wants to handle the task
     */
    /*package*/
    final void handle(Processor handler) {
        if (!is_started) {
            is_started = true;
            currProc = handler;
            start();
        } else {
            System.out.println("start returned task");
            end_callback.run();
        }
    }

    /**
     * This method schedules a new task (a child of the current task) to the
     * same processor which currently handles this task.
     *
     * @param task the task to execute
     */
    protected final void spawn(Task<?>... task) {
        int oldV;
//        int num;
        for (Task t : task) {
            do {
                oldV = childsLocks.get();
            } while (!childsLocks.compareAndSet(oldV, childsLocks.get() + 1));
            currProc.addTask(t);
//          do {
//                num = currProc.getPool().taskcreated.get();
//            } while (!currProc.getPool().taskcreated.compareAndSet(num, currProc.getPool().taskcreated.get() + 1));
        }
    }

    /**
     * add a callback to be executed once *all* the given tasks results are
     * resolved
     * <p>
     * Implementors note: make sure that the callback is running only once when
     * all the given tasks completed.
     *
     * @param tasks
     * @param callback the callback to execute once all the results are resolved
     */
    protected final void whenResolved(Collection<? extends Task<?>> tasks, Runnable callback) {
        end_callback = callback;

        class OneShotTask implements Runnable {
            private Task fatherTask;

            private OneShotTask(Task task) {
                fatherTask = task;
            }

            public void run() {
                int oldV;
                do {
                    oldV = fatherTask.childsLocks.get();
                } while (!fatherTask.childsLocks.compareAndSet(oldV, fatherTask.childsLocks.get() - 1));
                if (currProc.getPool().taskfinished.get() > 6)
                    System.out.println(fatherTask.childsLocks.get() + " " + returned + "\n" +
                            currProc.getPool().getProcessors()[0].getTasks().size() + "\n" +
                            currProc.getPool().getProcessors()[1].getTasks().size() + "\n" +
                            currProc.getPool().getProcessors()[2].getTasks().size() + "\n" +
                            currProc.getPool().getProcessors()[3].getTasks().size() + "\n");
                synchronized (fatherTask) {
                    if (fatherTask.childsLocks.get() == 0 && !fatherTask.returned) {
                        currProc.addTask(fatherTask);
                        fatherTask.returned = true;
                    }
                }
            }
        }

        for (Task task : tasks) {
            task.deferred.whenResolved(new OneShotTask(thisTask));
        }

    }

    /**
     * resolve the internal result - should be called by the task derivative
     * once it is done.
     *
     * @param result - the task calculated result
     */
    protected final void complete(R result) {
        int num2;
        do {
            num2 = currProc.getPool().taskfinished.get();
        } while (!currProc.getPool().taskfinished.compareAndSet(num2, currProc.getPool().taskfinished.get() + 1));
        deferred.resolve(result);
    }

    /**
     * @return this task deferred result
     */
    public final Deferred<R> getResult() {
        return deferred;
    }

    public void setProc(Processor p){
        this.currProc = p;
    }
}
