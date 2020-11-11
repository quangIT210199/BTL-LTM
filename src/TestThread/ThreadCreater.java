/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestThread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class ThreadCreater {

    // use getShape method to get object of type shape
    public Thread threadRunner(int start, int end, int threadCount, String url) {
    Thread thread = null;
    int tempEnd = 0;
    for (int i = 0; i <= end; i = i + 10) {
        start = i;
        tempEnd = i + 10;
        thread = getThread(start, tempEnd, threadCount, url);
        thread.start();
    }

    return null;
    }

    public static Thread getThread(int start, int end, int threadCount, String url) {
    Thread thread = new Thread() {
        public void run() {
        try {
            sendGET(start, end, url);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        }

        private void sendGET(int start, int end, String url) throws Exception {
//        url += "start=" + start + "&end=" + end;
//        URL obj = new URL(url);
//        // Send post request
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//        // basic reuqest header to simulate a browser request
//        con.setRequestMethod("GET");
//        con.setRequestProperty("User-Agent",
//            "Mozilla/5.0 (Windows NT 6.3; rv:36.0) Gecko/20100101 Firefox/51.0");
//        con.setRequestProperty("Upgrade-Insecure-Requests", "1");
//        con.setRequestProperty("Connection", "keep-alive");
//        con.setDoOutput(true);
//
//        // reading the HTML output of the POST HTTP request
//        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//        String inputLine;
//        while ((inputLine = in.readLine()) != null)
//            System.out.println(inputLine);
//        in.close();
            Connection.Response res = Jsoup.connect(url)
    //                    .headers(head)
            .method(Connection.Method.GET)
            .ignoreContentType(true)
            .ignoreHttpErrors(true)
            .timeout(20*1000)
            .execute();
                    
            String str = res.body();
            System.out.println(str);
        }
    };
    return thread;
    }

    public static void main(String[] args) {
    ThreadCreater obj = new ThreadCreater();
    int start = 0;
    int end = 100;
    int threadCount = 10;
    String url = "http://qldt.ptit.edu.vn";

    obj.threadRunner(start, end, threadCount, url);
    }

}
//    private void buildLongString(int length, String data) {
//        Document doc = jTextArea1.getDocument();
////        Document blank = new DefaultStyledDocument();
//        
//        System.out.println(data.length());
//        
//            try {
//                doc.insertString(doc.getLength(),
//                        data,
//                        null);
////                System.out.println(jTextArea1.getText());
//            } catch (BadLocationException e) {
//                e.printStackTrace();
//            }
//        
//        jTextArea1.setDocument(doc);
//    }
//    
//    private void HugeTextArea(String data) {
////        document = new DefaultStyledDocument();
//        new Thread() {
//            @Override
//            public void run() {
//                buildLongString(400000, data);
//            }
//        }
//        .start();
//    }