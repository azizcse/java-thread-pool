import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public class Main {

    private ExecutorService outputExecutor;
    private ScheduledThreadPoolExecutor pool;
    static Main obj;

    public Main() {



        //configureOutput();
    }

    public static void main(String[] args) {

        CustomThreadPool customThreadPool = new CustomThreadPool(3);

        for (int i = 1; i <= 10; i++)
        {
            Task task = new Task("Task " + i);
            //System.out.println("Created : " + task.getName());

            customThreadPool.execute(task);

            /*try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
        /*obj = new Main();
        obj.runHundredRunnable();*/
    }

    private void configureOutput() {
        pool = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("NsdLink " + this.hashCode() + " Output");
                thread.setDaemon(true);
                return thread;
            }
        });

        outputExecutor = new SerialExecutorService(pool);
    }

    private void runHundredRunnable() {

        for (int i = 0; i < 10; i++) {

            outputExecutor.execute(new Runnable() {
                @Override
                public void run() {

                    System.out.println("Runnable thread count =" + System.currentTimeMillis());
                }
            });
        }
        System.out.println("Terminated");
    }
}
