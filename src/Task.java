import java.util.Date;

public class Task implements Runnable{
    private String name;

    public Task(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Executing : " + name + ", Current Seconds : " + new Date().getSeconds());
    }
}
