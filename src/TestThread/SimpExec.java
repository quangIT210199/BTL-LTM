package TestThread;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class SimpExec {
    public static void main(String args[]) {

        ExecutorService es = Executors.newFixedThreadPool(4);

        es.execute(new MyThread("A"));
//        es.execute(new MyThread("B"));
//        es.execute(new MyThread("C"));
//        es.execute(new MyThread("D"));

        es.shutdown();
    }
}

class MyThread implements Runnable {
    String name;

    MyThread(String n) {
        name = n;
        new Thread(this);
    }

    public void run() {
        try {
            try {
            Connection.Response res = Jsoup.connect("http://qldt.ptit.edu.vn")
//                    .headers(head)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .timeout(20*1000)
                    .execute();
            
            //Thêm vào body
            String str = res.parse().html();
            
                System.out.println(str);
        } catch (IOException ex) {
            
        }
            
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        if (name=="A"){
//            for (int i=1;i<=10;i++){
//                System.out.println(i);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
//        if (name=="B"){
//            for (int i=10;i<=20;i++){
//                System.out.println(i);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
//        if (name=="C"){
//            for (int i=20;i<=30;i++){
//                System.out.println(i);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
//        if (name=="D"){
//            for (int i=30;i<=40;i++){
//                System.out.println(i);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
    }
}
