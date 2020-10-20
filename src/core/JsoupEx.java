/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.IOException;
import org.jsoup.Jsoup;  
import org.jsoup.nodes.Document;

/**
 *
 * @author daumoe
 */
public class JsoupEx {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("http://qldt.ptit.edu.vn").post();
        System.out.println(doc);  
    }
}
