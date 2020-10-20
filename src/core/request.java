/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.Document;
import org.jsoup.Jsoup;

/**
 *
 * @author daumoe
 */
public class request {
       public static void main(String[] args) {
           
           //Doc: https://www.journaldev.com/7148/java-httpurlconnection-example-java-http-request-get-post
           try {
               URL url = new URL("http://qldt.ptit.edu.vn");
               try {
                   HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                   conn.setRequestMethod("GET");
                   conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                   conn.setRequestProperty("Content-Language", "en-US");
                   conn.setDoOutput(true);
//                   DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                   int respCode = conn.getResponseCode();
                   if (respCode == HttpURLConnection.HTTP_OK) {
                       BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                       String inputLine = null;
                       StringBuffer resp = new StringBuffer();
//                       File myObj = new File("D:\\test.html");
//                       myObj.createNewFile();
//                       FileWriter respFile = new FileWriter("D:\\test.html");
//                       while((inputLine = in.readLine()) != null) {
//                           respFile.write(inputLine);
//                           respFile.write("\n");
//                       }
//                       respFile.close();
                        while((inputLine = in.readLine()) != null) {
                            resp.append(inputLine);
                        }
                       Document x = (Document) Jsoup.parse();
                       System.out.println(x);
                       in.close();
                   }
                   
               } catch (IOException ex) {
                   Logger.getLogger(request.class.getName()).log(Level.SEVERE, null, ex);
               }
           } catch (MalformedURLException ex) {
               Logger.getLogger(request.class.getName()).log(Level.SEVERE, null, ex);
           }
    }
}
