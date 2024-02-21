/* Implement this class. */

import java.util.Comparator;
import java.util.PriorityQueue;

public class MyHost extends Host {
    public Task running;
    public volatile boolean flag = true;
    public PriorityQueue<Task> q;
    public MyHost(){
        q = new PriorityQueue<>(new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                // First, compare by priority in decreasing order
                int dif1 = task2.getPriority() - task1.getPriority();
                int dif2 = task1.getStart() - task2.getStart();
                return dif1 == 0 ? dif2 : dif1;
            }
        });
    }

    @Override
    public void run() {
        while (flag) {
            if (q.isEmpty()) {
                if (running == null) {
                    continue;
                }
            } else {
                if (running != null && running.isPreemptible() && q.peek().getPriority() > running.getPriority()) {

                        q.add(running);
                        running = q.poll();

                    // run

                }
                if(running==null) {
                    running = q.poll();
                    //run

                }
            }
            try {
                Thread.sleep(200);
                running.setLeft(running.getLeft() -200);
                if (running.getLeft() <= 0) {
                    running.finish();
                    running = null;
                }
                //                        sleep(running.getLeft());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
//setez running
    @Override
    public synchronized void addTask(Task task) {
    q.add(task);

//doar bag in coada
    }

    @Override
    public synchronized int getQueueSize() {

        return q.size();
    }

    @Override
    public synchronized long getWorkLeft() {
        long time = 0;
        if(running!=null){
            time+=running.getLeft();
        }
        for(Task t:q){
            time+=t.getLeft();
        }
        return time;
    }

    @Override
    public void shutdown() {
        flag = false;
       // interrupt();
    }
}
