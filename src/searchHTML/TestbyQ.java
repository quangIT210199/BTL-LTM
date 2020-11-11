/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchHTML;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class TestbyQ {
    public static void main(String[] args) {
        try {
            Connection.Response res = Jsoup.connect("http://qldt.ptit.edu.vn/")
//                    .headers(head)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .timeout(20*1000)
                    .execute();
            
            Document doc = res.parse();
            
            Elements divs = doc.select(".base");
//            System.out.println(divs.outerHtml());
//            String str= "";
            StringBuilder str2 = new StringBuilder();
            long start, end;
            start = System.currentTimeMillis();
            for(Element elem : divs){
                str2.append(elem.outerHtml());
//                str += elem.outerHtml();
                System.out.println(elem.outerHtml()); //get all elements inside div
            }
            end = System.currentTimeMillis();
            System.out.println((double)((double)(end - start) / 1000) + "s");
            System.out.println(str2);
//            System.out.println(Jsoup.parse(str, "", Parser.htmlParser()));
        } catch (IOException ex) {
            Logger.getLogger(TestbyQ.class.getName()).log(Level.SEVERE, null, ex);
        }
//        String html = "<html>\n" +
//"<head></head>\n" +
//"    <body>\n" +
//"            <div id=\"quang\">\n" +
//"                <h1>Title</h1>\n" +
//"            </div>\n" +
//"            <div>\n" +
//"                <img src=\"/xx.jpg\" />\n" +
//"            </div>\n" +
//"            <div>\n" +
//"                <p>Paragraph 1</p>\n" +
//"                <p>Paragraph 2</p>\n" +
//"            </div>\n" +
//"            <div>\n" +
//"                <h2><b>End</b></h2>\n" +
//"            </div>\n" +
//"        </body>\n" +
//"</html>";
//        Document doc = Jsoup.parse(html);
//Elements divs = doc.select("div#quang");
//
//        for(Element elem : divs){
//  System.out.println(elem.outerHtml()); //get all elements inside div
//}
    }
}
