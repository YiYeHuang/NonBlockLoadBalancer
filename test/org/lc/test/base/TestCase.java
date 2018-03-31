package org.lc.test.base;


import org.lb.pojo.Request;
import org.lb.core.ServiceInvoker;
import org.lb.launch.LaunchService;

public class TestCase {
    public static void main(String[] args) throws Exception {

        LaunchService.main(new String[] {});

        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            new RequestSender().start();
        }
    }

    static class RequestSender extends Thread {
        private static long id = -1;

        public RequestSender() {

        }

        static synchronized long nextId() {
            return ++id;
        }

        @Override
        public void run() {
            ServiceInvoker rd = ServiceInvoker.getInstance();


            for (int i = 0; i < 100; i++) {
                rd.dispatchRequest(new Request(nextId(), 1));
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException ex) {
                    // dont do anything
                }
            }
        }
    }
}
