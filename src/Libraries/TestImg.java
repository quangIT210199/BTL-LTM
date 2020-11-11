/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Libraries;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author quang
 */
public class TestImg {
    public static void main(String[] args) throws IOException {
        
        
//        Document document = Jsoup.connect("http://www.nettruyen.com/truyen-tranh/chuyen-tinh-thanh-xuan-bi-hai-cua-toi-qua-nhien-la-sai-lam/chap-69/639930").get();
//        ArrayList<String> list_img = new ArrayList<>();
//        Elements elms = document.getElementsByClass("grab-content-chap");
//        Elements e = document.getElementsByTag("img");
//        for (int i = 0; i < e.size(); i++) {
//            String url = e.get(i).absUrl("src");
//            System.out.println(url);
//            if (url.equals("")) {
//                continue;
//            }
//            list_img.add(url);
//        }
//        
//        System.out.println("Link url: ");
//        for (int i = 0; i < list_img.size(); i++) {
//            System.out.println(list_img.get(i));
//        }
        
//        Connection.Response resQ= Jsoup.connect("http://qldt.ptit.edu.vn/")
//
//                    .method(Connection.Method.GET)
//                    .ignoreContentType(true)
//                    .ignoreHttpErrors(true)
//                    .timeout(20*1000)
//                    .execute();
//        
//        Document doc = Jsoup.parse(resQ.body());
//        
//        ArrayList<String> list_img = new ArrayList<>();
//        
//        Elements e = doc.select("[style~=(?i)\\.(png|jpe?g)]");
//        String qu = doc.cssSelector();
//        System.out.println(qu);
//        System.out.println(e.size());
//        for (int i = 0; i < e.size(); i++) {
//            System.out.println(e.get(i).attr("style"));
//        }
        
//        String str = "background-image: url('http://qldt.ptit.edu.vn/App_Themes/Standard/Images/US.gif')";
//        System.out.println(str.replaceAll("\\'", ""));
//        String q = str.replaceAll(".*\\(|\\).*", "").replaceAll("\\'", "");
//        System.out.println(q);
        
        String qu ="I don't like these \"double\" quotes";
        System.out.println(qu.replaceAll("\"", ""));
        
//        int n = 39;
//        System.out.println((char)n);
//        System.out.println(q.charAt(0));
//        if(q.charAt(0) == (char)n){
//            System.out.println("ok nhá");
//        }
//        System.out.println(str.replaceAll(".*\\(|\\).*", ""));
//        String q = str.replaceAll(".*\\(|\\).*", "");
//        System.out.println(q.replace("\\.", ""));
//        System.out.println("Phù hợp");
//        for (int i = 0; i < list_img.size(); i++) {
//            System.out.println(list_img.get(i));
//        }
        
//        URL url = new URL("http://image.phimmoizz.net/profile/1/medium.jpg");
//        URLConnection conn = url.openConnection();
//        
//        InputStream in = conn.getInputStream();
//        OutputStream out = new BufferedOutputStream(new FileOutputStream("C:\\Users\\quang\\Desktop\\Ky1Nam4\\Ky1_2020\\LTM\\RequestSoftware\\src\\Image" + "\\" + "quang.jpg"));
//            for (int b; (b = in.read()) != -1;) {
//                out.write(b);
//            }
//            out.close();
//            in.close();
    }
}
