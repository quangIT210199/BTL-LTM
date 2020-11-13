/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestJson;

/**
 *
 * @author quang
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
 
import com.google.gson.stream.JsonWriter;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
 
public class GsonStreamingWriterExample {
    public static void main(String args[]) throws IOException {
        String timeStamp = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println(timeStamp);
        OutputStream out = new FileOutputStream("D:\\Hoc_Ky_1_Nam4\\LTM\\RequestSoftware\\src\\TestJson\\result.json");
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
 
        writer.beginObject(); // {
        writer.name("name").value("GP Coder"); // "name" : "gpcoder"
        writer.name("website").value("https://gpcoder.com"); // "website" : "https://gpcoder.com"
        writer.name("year").value(2017); // "year" : 2017
        
        writer.name("quang"); // "colors" :
        writer.beginObject(); // {
        writer.name("huong").value("19t");
        writer.name("hung").value("9t");
        writer.name("trung").value("23t");
        writer.endObject(); // }
        
        writer.name("posts"); // "colors" :
        writer.beginArray(); // [
        writer.value("Java Core"); // "Java Core"
        writer.value("Design Pattern"); // "Design Pattern"
        writer.value("Spring"); // "Spring"
        writer.endArray(); // ]
 
        writer.endObject(); // }
        writer.close();
 
        System.out.println("Done!");
        System.out.println(new File("D:\\Hoc_Ky_1_Nam4\\LTM\\RequestSoftware\\src\\FileJSON\\").list().length);
    }
}