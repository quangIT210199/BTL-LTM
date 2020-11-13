/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestJson;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
 
import com.google.gson.stream.JsonReader;
/**
 *
 * @author quang
 */
public class GsonStreamingReaderExample {
public static void main(String[] args) throws IOException {
        InputStream out = new FileInputStream("D:\\Hoc_Ky_1_Nam4\\LTM\\RequestSoftware\\src\\TestJson\\result.json");
        JsonReader reader = new JsonReader(new InputStreamReader(out, "UTF-8"));
        reader.beginObject();
 
        while (reader.hasNext()) {
            String name = reader.nextName();
 
            if (name.equals("name")) {
                System.out.println(reader.nextString());
            } else if (name.equals("website")) {
                System.out.println(reader.nextString());
            } else if (name.equals("year")) {
                System.out.println(reader.nextInt());
            }else if(name.equals("quang")){
                reader.beginObject();
                while (reader.hasNext()){
                    System.out.println("name nhé: " + reader.nextName());
                    System.out.println("value nhé: " + reader.nextString());
                }
                reader.endObject();
            }
            else if (name.equals("posts")) {
 
                // it's an array.
                reader.beginArray();
                while (reader.hasNext()) {
                    System.out.println(reader.nextString());
                }
                reader.endArray();
            } else {// unexpected value, skip it or generate error
                reader.skipValue();
            }
        }
 
        reader.endObject();
        reader.close();
    }    
}
