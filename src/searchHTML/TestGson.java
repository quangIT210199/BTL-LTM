/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchHTML;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sun.rmi.runtime.Log;

public class TestGson {
    public static void main(String[] args) {
//        String json = "[{\"blobJson\":\"x\",\"deviceMfg\":10,\"eventCode\":0,\"sensorClass\":3,\"sensorUUID\":\"136199\",\"timeStamp\":1.483384640123117E9,\"uID\":\"136199_3_10\"},{\"blobJson\":\"x\",\"deviceMfg\":10,\"eventCode\":0,\"sensorClass\":3,\"sensorUUID\":\"136199\",\"timeStamp\":1.483379834470379E9,\"uID\":\"136199_3_10\"},{\"blobJson\":\"x\",\"deviceMfg\":10,\"eventCode\":0,\"sensorClass\":3,\"sensorUUID\":\"136199\",\"timeStamp\":1.483384639621985E9,\"uID\":\"136199_3_10\"}]";
//
//        JsonParser jp = new JsonParser();
//        JsonElement root = jp.parse(json);
//        JsonArray rootArr = root.getAsJsonArray();
//
//        JsonObject rootObj = rootArr.get(0).getAsJsonObject();
//        System.out.println(rootObj.toString());
//        rootObj.entrySet().forEach(entry -> System.out.println(entry.getKey()+": "+entry.getValue().getAsString()));

//        String s = "{menu:{\"1\":\"sql\", \"2\":\"android\", \"3\":\"mvc\"}}";
//        JSONObject jObject  = new JSONObject(s);
//        JSONObject  menu = jObject.getJSONObject("menu");
//        System.out.println(menu);
//        
//        Map<String,String> map = new HashMap<String,String>();
//        Iterator iter = menu.keys();
//        while(iter.hasNext()){
//          String key = (String)iter.next();
//          String value = menu.getString(key);
//          map.put(key,value);
//        }

        String test = "{\"coord\":{\"lon\":105.84,\"lat\":21.02},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"base\":\"stations\",\"main\":{\"temp\":25,\"feels_like\":27.49,\"temp_min\":25,\"temp_max\":25,\"pressure\":1016,\"humidity\":69},\"visibility\":10000,\"wind\":{\"speed\":1,\"deg\":0},\"clouds\":{\"all\":75},\"dt\":1604131672,\"sys\":{\"type\":1,\"id\":9308,\"country\":\"VN\",\"sunrise\":1604098724,\"sunset\":1604139697},\"timezone\":25200,\"id\":1581130,\"name\":\"Hanoi\",\"cod\":200}";
        try {
            JSONObject jOb = new JSONObject(test);
            System.out.println(jOb);
            
            String str = "visibility";
//            System.out.println(jOb.getJSONArray("weather"));
            Object ob = jOb.get(str);
//            System.out.println(ob);
            
            JSONArray  interventionJsonArray;
            JSONObject interventionObject;
            if (ob instanceof JSONArray) {
                // It's an array
                interventionJsonArray = (JSONArray)ob;
                System.out.println(interventionJsonArray);
            }
            else if (ob instanceof JSONObject) {
                System.out.println("quang");
                // It's an object
                interventionObject = (JSONObject)ob;
                System.out.println(interventionObject);
            }else if(ob instanceof Object){
                System.out.println(ob);
            }
        }catch (JSONException err){
            System.out.println(err);
        }
    }
}
