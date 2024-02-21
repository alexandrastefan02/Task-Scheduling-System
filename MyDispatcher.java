/* Implement this class. */

import java.util.List;

import static java.util.Collections.min;

public class MyDispatcher extends Dispatcher {
    public int i=-1;
    public MyDispatcher(SchedulingAlgorithm algorithm, List<Host> hosts) {
        super(algorithm, hosts);
    }

    @Override
    public  synchronized void addTask(Task task) {
        MyHost host = (MyHost)hosts.get(0);
        switch (algorithm) {
            case ROUND_ROBIN:
                i = (i+1)%hosts.size();
                host = (MyHost)hosts.get(i);
                host.addTask(task);
                break;
            case SHORTEST_QUEUE:
                Host smallestQueueHost = hosts.get(0);
                int minQ = smallestQueueHost.getQueueSize();
                if(((MyHost)smallestQueueHost).running!=null){
                    minQ+=1;
                }
                for (Host h : hosts) {
                   int curr = ((MyHost)h).getQueueSize();
                   if(((MyHost) h).running!=null){
                       curr+=1;
                   }
                   if(curr<minQ){
                       minQ = curr;
                       smallestQueueHost = (MyHost) h;
                   }
                   else if(curr ==minQ){
                       if(hosts.indexOf(h)<hosts.indexOf(smallestQueueHost)) {
                           minQ = curr;
                           smallestQueueHost = (MyHost) h;
                       }
                   }

                }
                host = (MyHost) smallestQueueHost;
                host.addTask(task);
                // code to be executed if expression matches value2
                break;
            case SIZE_INTERVAL_TASK_ASSIGNMENT:
                if(task.getType() ==TaskType.SHORT){
                    host = (MyHost)hosts.get(0);
                }
                if(task.getType() ==TaskType.MEDIUM){
                    host = (MyHost)hosts.get(1);
                }
                if(task.getType() ==TaskType.LONG){
                    host = (MyHost)hosts.get(2);
                }
                // code to be executed if expression matches value2
                host.addTask(task);
                break;
            case LEAST_WORK_LEFT:
                Host leastWorkHost = hosts.get(0);
                for(Host h:hosts){
                    if(h.getWorkLeft()<leastWorkHost.getWorkLeft()){
                        leastWorkHost = h;
                    }
                    else if(h.getWorkLeft()==leastWorkHost.getWorkLeft()){
                        if(hosts.indexOf(h)<hosts.indexOf(leastWorkHost)) {
                            leastWorkHost = h;
                        }
                    }
                }
                host = (MyHost) leastWorkHost;
                host.addTask(task);
                // code to be executed if expression matches value2
                break;
            // additional cases as needed
            default:
                // code to be executed if none of the cases match
        }
        //host.addTask(task);

    }
}