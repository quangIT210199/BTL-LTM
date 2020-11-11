package TestJson;


import com.google.gson.Gson;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Connection.Request;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.parser.XmlTreeBuilder;


public class JsonTest {
    
    public static void main(String[] args) throws IOException {
        /*
            ? + ptu đầu tiên
            for() a+ = + 2 + & đến thừ 2 ở cuối
            ptu cuối không cộng &
        */
        
        getTitleOfUrl();
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
    }
    //_ga=GA1.3.1663250326.1594820702; ASP.NET_SessionId=cympcunhdopyp4ihejvqpyb2
    // Tạo Document từ URL và lấy title
    
//    private static String _getContent(String url) {
//    Connection conn = Jsoup.connect(url)
//            .header("Accept", ACCEPT)
//            .header("Accept-Encoding", ENCODING)
//            .header("Accept-Language", LANGUAGE)
//            .header("Connection", CONNECTION)
//            .header("Referer", REFERER)
//            .header("Host", HOST)
//            .header("User-Agent", USER_AGENT)
//            .timeout(1000)
//            .ignoreContentType(true);
//    String html = "";
//    try {
//        html = conn.post().html();
//        html = html.replaceAll("[\n\r]", "");
//    }catch (Exception e){
//        LOGGER.error("获取URL：" + url + "页面出错", e);
//    }
//    return html;
//}
    public static void getTitleOfUrl() {
        
        try {
            Response doc = null;
            
            doc =   Jsoup.connect("http://qldt.ptit.edu.vn/")
//                .data("ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$txtTaiKhoa", "B17DCAT148","ctl00$ContentPlaceHolder1$ctl00$ucDangNhap$txtMatKhau", "08071997")
//                    .userAgent("Mozilla/5.0")
                    
                    .execute();
           Gson gson = new Gson();
           
          String str = gson.toJson(doc.body().toString());
            System.out.println(str);
          doc.parse().html();
//        System.out.println(doc.body());
//        String jsonString = doc.body();
//        JSONObject json = new JSONObject(jsonString);
//        Map<String, String> loginCookies = doc.cookies();
//        for (Map.Entry<String, String> entry : loginCookies.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }
//        System.out.println(loginCookies);
        
           
//        Map<String, String> header = doc.headers();
//        System.out.println(header);
//        
        } catch (IOException ex) {
            Logger.getLogger(JsonTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        System.out.println("Request"); 
//        Request doc2;
//        doc2 = (Request) Jsoup.connect("http://qldt.ptit.edu.vn/Default.aspx?page=thoikhoabieu&id=b17dcat148")
//                .method(Connection.Method.GET)
//                .request();
//        
//        System.out.println("data " + doc2.data());
//        
//        System.out.println("maxBodySize "+doc2.maxBodySize());
//        System.out.println("requestBody " +doc2.requestBody());
//        System.out.println("cookies "+doc2.cookies());
//        Map<String, String> header = doc2.headers();
//        for (Map.Entry<String, String> entry : header.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }
//        System.out.println(header);
        
//        System.out.println("Request"); 
//        Request doc2;
//        doc2 = (Request) Jsoup.connect("http://qldt.ptit.edu.vn/Default.aspx?page=thoikhoabieu&id=b17dcat148")
//                .method(Connection.Method.GET)
//                .request();
//        
//        System.out.println("data " + doc2.data());
//        
//        System.out.println("maxBodySize "+doc2.maxBodySize());
//        System.out.println("requestBody " +doc2.requestBody());
//        System.out.println("cookies "+doc2.cookies());
//        Map<String, String> header = doc2.headers();
//        for (Map.Entry<String, String> entry : header.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }
//        System.out.println(header);
        
        
    }
}
