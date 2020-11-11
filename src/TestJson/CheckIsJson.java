package TestJson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class CheckIsJson {
    public static void main(String[] args) {
        try {
            Connection.Response res = Jsoup.connect("http://api.openweathermap.org/data/2.5/weather?q=haNoi&&units=metric&appid=7ef4ccb00ed20ff77b4b38f34bb7b32f&fbclid=IwAR1RUe_zaZQ9V0q2tgx99CNG9fze6XTQYQPyLTsx1dTwAB2EeTYcicD0WcY")
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .timeout(20*1000)
                    .execute();
            
            String str = res.body();
            if(isJSONValid(str)){
                JsonParser parser = new JsonParser();
            
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                JsonElement el = parser.parse(str);

                str = gson.toJson(el);
                System.out.println(str);
            }else {
                System.out.println("Khong phai data json");
            }
            
            System.out.println(res.statusCode() + " " + res.statusMessage());
        } catch (IOException ex) {
            Logger.getLogger(CheckIsJson.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public static boolean isJSONValid(String test) {
    try {
        new JSONObject(test);
    } catch (JSONException ex) {
        // edited, to include @Arthur's comment
        // e.g. in case JSONArray is valid as well...
        try {
            new JSONArray(test);
        } catch (JSONException ex1) {
            return false;
        }
    }
    return true;
    }
}
