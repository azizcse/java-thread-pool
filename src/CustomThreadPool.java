import java.util.concurrent.LinkedBlockingQueue;

public class CustomThreadPool {
    //Thread pool size
    private final int poolSize;
    // FIFO ordering
    private final LinkedBlockingQueue<Runnable> queue;
    //Internally pool is an array
    private final WorkerThread[] workers;

    public CustomThreadPool(int size) {

        this.poolSize = size;
        queue = new LinkedBlockingQueue<Runnable>();
        workers = new WorkerThread[poolSize];

        for (int i = 0; i < poolSize; i++) {
            workers[i] = new WorkerThread();
            workers[i].start();
        }

    }

    public void execute(Runnable runnable){
        synchronized (queue){
            queue.add(runnable);
            queue.notify();
        }
    }


    private class WorkerThread extends Thread {
        public void run() {
            Runnable task;

            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            System.out.println("Queue is waiting");
                            queue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("An error occurred while queue is waiting: " + e.getMessage());
                        }
                    }
                    task = (Runnable) queue.poll();
                }

                try {
                    task.run();
                } catch (RuntimeException e) {
                    System.out.println("Thread pool is interrupted due to an issue: " + e.getMessage());
                }
            }
        }
    }

    public void shutdown() {
        System.out.println("Shutting down thread pool");
        for (int i = 0; i < poolSize; i++) {
            workers[i] = null;
        }
    }
}
