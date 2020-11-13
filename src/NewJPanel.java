import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.awt.Color;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Window;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

/**
 *
 * @author quang
 */
public class NewJPanel extends javax.swing.JPanel {

    private Map<String ,String> bodyMap, headersMap, headersMapRes, cookiesMapRes;
    private Map<String ,String> cookiesMapSend = new HashMap<>();
    private DefaultTableModel headers, body, headersRes,cookiesRes;
    private String url = "";
    private String type = "", status="";
    private String str = "";
    private String dateSend = "";
    
    private Main mainQ;
    
    public Connection.Response res;
    private Document documentSearch;
    public NewJPanel() {
        initComponents();
        body = (DefaultTableModel)jTable1.getModel();
        headers = (DefaultTableModel) jTable2.getModel();
        headersRes = (DefaultTableModel) jTable3.getModel();
        cookiesRes = (DefaultTableModel) jTable4.getModel();
        this.setLocale(getDefaultLocale());
    }
    //Tìm kiếm Highlight
    class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter{
        public MyHighlightPainter(Color color){
            super(color);
        }
    }
    //Màu highlight
    Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.ORANGE);
    //Phương thức Highlight
    public void highligh(JTextComponent textComp, String pattern){
        removeHighligh(textComp);//Highlight restart Method
        
        try {
            Highlighter hilite = textComp.getHighlighter();
            javax.swing.text.Document doc = textComp.getDocument();
            
            String text = doc.getText(0, doc.getLength());
            int pos = 0;//vị trí
            //Tìm word và highLight nó lên :V
            while((pos = text.toUpperCase().indexOf(pattern.toUpperCase(), pos)) >= 0){
                //index, postion pattern, 
                hilite.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
                
                pos += pattern.length();
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    //Highlight restart
    public void removeHighligh(JTextComponent textComp){
        Highlighter hilite = textComp.getHighlighter();
        
        Highlighter.Highlight[] hilites = hilite.getHighlights();
        
        for (int i = 0; i < hilites.length; i++) {
            if(hilites[i].getPainter() instanceof MyHighlightPainter)
            {
                hilite.removeHighlight(hilites[i]);
            }
        }
    }
    
    //Kiểm tra URL
    private boolean isUrlEmpty(){
        if(url.equals("")){
            return true;
        }
        return false;
    }
    
    private void updateURL() {
        String temp = url;
        String method = (String) jComboBox1.getSelectedItem();
        if (method.equals("GET")) {
            for (int i=0; i<jTable1.getRowCount(); i++) {
                 if (i == 0) {
                     temp = temp+"?"+jTable1.getValueAt(i, 0)+"="+jTable1.getValueAt(i, 1);
                 } else {
                     temp = temp+"&"+jTable1.getValueAt(i, 0)+"="+jTable1.getValueAt(i, 1);
                 }
            }
            jTextField1.setText("");
            jTextField1.setText(temp);
        } 
        else{
            jTextField1.setText(temp);
        }
    }    
    //Gửi POST
    private void sendPost(String url, String method, Map<String, String> data,Map<String, String> head, Map<String, String> cookiesMapSend){
        long start, end;
        start = System.currentTimeMillis();
        try {
            res = Jsoup.connect(url)
                    .data(data)
                    .cookies(cookiesMapSend)
                    .headers(head)
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .timeout(20*1000)
                    .execute();
            
            //Thêm vào body
            String str = res.body();
            guiData(res.headers(), str);
            //thêm cookies
            cookiesMapRes = res.cookies();
            getCookiesRes(cookiesMapRes, url);
            
            //Thêm vô headers
            headersMapRes = res.headers();
            getHeadersRes(headersMapRes);
            
            status = res.statusCode() +" "+ res.statusMessage();
            //Lưu lịch sử
            writeFileJson(dateSend);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        end = System.currentTimeMillis();
        
        jLabel5.setText("Status: " + status);
        jLabel6.setText("Time: " + (double)((double)(end - start) / 1000) + "s");
        jLabel7.setText("Type: " + type);
    }
    
    //SendGET
    private void sendGet(String url, String method,Map<String, String> head, Map<String, String> cookiesMapSend){
        long start, end;
        start = System.currentTimeMillis();
        try {
            res = Jsoup.connect(url)
                    .headers(head)
                    .cookies(cookiesMapSend)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .timeout(20*1000)
                    .execute();

            //Thêm vào body
            str = res.body();
            //gán vào để tìm kiếm
            
            guiData(res.headers(), str);

            //thêm cookies
            cookiesMapRes = res.cookies();
            getCookiesRes(cookiesMapRes, url);
            
            //Thêm vô headers
            headersMapRes = res.headers();
            getHeadersRes(headersMapRes);
            //lấy status code
            status = res.statusCode() +" "+ res.statusMessage();
            //Lưu lịch sử
            writeFileJson(dateSend);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        end = System.currentTimeMillis();
        
        jLabel5.setText("Status: " + status);
        jLabel6.setText("Time: " + (double)((double)(end - start) / 1000) + "s");
        jLabel7.setText("Type: " + type);
    }
    //Kiểm tra dữ liệu rồi setTextArea
    private void guiData(Map<String, String> mapCheck, String data){
        jTextArea1.setEditable(false);
        String tmp;
        
        for (Map.Entry<String, String> entry : mapCheck.entrySet()) {
            if(entry.getKey().equalsIgnoreCase("Content-Type")){
                if(entry.getValue().contains("text/xml")){
                    type ="XML";
                    tmp = dataXML(data);
                    jTextArea1.setText(tmp);
                }
                else if(entry.getValue().contains("application/json")){
                    type ="JSON";
                    tmp = dataJson(data);
                    jTextArea1.setText(tmp);
                }
                else if(entry.getValue().contains("text/html")){
                    type ="HTML";
                    tmp =  Jsoup.parse(data).html();
                    jTextArea1.setText(tmp);
                }else{
                    type ="TEXT";
                    jTextArea1.setText(data);
                }
            }
        }
    }
    
    //Data XML
    private String dataXML(String str){
        String result = Jsoup.parse(str, str, Parser.xmlParser()).toString();
    
        return result;
    }
    //Data Json
    private String dataJson(String str){
        JsonParser parser = new JsonParser();
            
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        JsonElement el = parser.parse(str);
          
        str = gson.toJson(el);
        
        return str;
    }
    
    //Lấy headers sau mỗi lần Res
    private void getHeadersRes(Map<String, String> map ){
        headersRes.setRowCount(0);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            Payload pay = new Payload(entry.getKey(), entry.getValue());
//            System.out.println(entry.getKey() + " " + entry.getValue());
            headersRes.addRow(pay.toObjects());
        }
    }
    //Lấy cookies
    private void getCookiesRes(Map<String, String> map, String url){
        cookiesRes.setRowCount(0);

        for (Map.Entry<String, String> entry : map.entrySet()) {
            Payload pay = new Payload(entry.getKey(), entry.getValue());
            cookiesRes.addRow(pay.toObjects());
        }
    }
    //Lấy headers
    private  void getHeaders(){
        headersMap = new HashMap<>();
//        System.out.println(String.valueOf(headers.getValueAt(0, 1)));
        for (int i = 0; i < headers.getRowCount(); i++) {
            if(headers.getValueAt(i, 1) == null){
                headersMap.put(headers.getValueAt(i, 0).toString(), "");
            }
            else{
                headersMap.put(headers.getValueAt(i, 0).toString(), headers.getValueAt(i, 1).toString());
            }
        }
    
    }
    //Lấy body
    private void getBody(){
        bodyMap = new HashMap<>();
        for (int i=0; i<body.getRowCount(); i++) {
            if(body.getValueAt(i, 1) == null){
                bodyMap.put(body.getValueAt(i, 0).toString(), "");
            }
            else{
                bodyMap.put(body.getValueAt(i, 0).toString(), body.getValueAt(i, 1).toString());
            }
        }
        
        for (Map.Entry<String, String> entry : bodyMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
    
    //Tìm kiếm theo Selector html
    private String searchSelectorHtml(String keySearch){
        if(!"".equals(keySearch)){
            System.out.println("chạy nhé");
            documentSearch = Jsoup.parse(res.body());
            
            String select = keySearch.trim();//lấy ra selector để tìm
            Elements divs = documentSearch.select(select); // 
            
            String strHTML="";
//            strHTML = divs.stream().map((elem) -> elem.outerHtml()).reduce(strHTML, String::concat);
            strHTML = divs.stream().map((elem) -> elem.outerHtml()).reduce(strHTML, String::concat);
//            jTextArea1.setText(strHTML);
            return strHTML;
        }
        else{
            JOptionPane.showMessageDialog(null, "Tìm kiếm không được rỗng!");
            return null;
        }
    }
    //Tìm kiếm theo JSON 
    private String searchJSON(String keySearch){
        System.out.println(keySearch);
        if(!"".equals(keySearch)){
            try {
                JSONObject jOb = new JSONObject(dataJson(res.body()));

                String key = keySearch.trim();
                String tmp;

                Object ob = jOb.get(key);
                JSONArray  interventionJsonArray;
                JSONObject interventionObject;
                
                if (ob instanceof JSONArray) {
                    // Là Array
                    interventionJsonArray = (JSONArray)ob;
                    tmp ="{"+ "\""+key + "\""+ ":" + interventionJsonArray + "}";
//                    System.out.println(tmp);
//                    System.out.println(dataJson(tmp));
                    return dataJson(tmp);
                }
                else if (ob instanceof JSONObject) {
                    // Là Object
                    interventionObject = (JSONObject)ob;
                    tmp ="{"+ "\""+key + "\""+ ":" + interventionObject + "}";
//                    System.out.println(dataJson(tmp));
                    return dataJson(tmp);
                }else if(ob instanceof Object){
                    tmp ="{"+ "\""+key + "\""+ ":" + ob + "}";
//                    System.out.println(tmp);
                    return dataJson(tmp);
                }
                else {
                    System.out.println("It's something else, like a string or number");
                }
            }catch (JSONException err){
                System.out.println(err);
                JOptionPane.showMessageDialog(null, err +" Key không tồn tại");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Tìm kiếm không được rỗng!");
        }
        return null;
    }
    //Tìm kiếm XML
    private String SearchXML(String keySearch){
        if(!"".equals(keySearch)){
            documentSearch = Jsoup.parse(res.body(), "", Parser.xmlParser());
            
            Elements elements = documentSearch.getElementsByTag(keySearch);
            StringBuilder sb = new StringBuilder();
            
            for(Element e : elements) {
                sb.append(e);
            }
            
            return dataXML(sb.toString());
        }
        else{
            JOptionPane.showMessageDialog(null, "Tìm kiếm không được rỗng!");
        }
        return null;
    }
    //Tìm kiếm Text: chưa hoàn thành
    private String SearchText(String keySearch){
        if(!"".equals(keySearch)){
            String str = res.body().trim();

            if(str.contains(keySearch)){
                return keySearch;
            }
            else {
                JOptionPane.showMessageDialog(null, "Key không tồn tại!");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Tìm kiếm không được rỗng!");

        }
        return null;
    }
    
    //Hàm này để tk Form con gọi nhé
    public String SearchMethod(String keySearch){
        String tmp = null;
        System.out.println("Vào rồi nè");
        if("HTML".equals(type)){
            System.out.println("html nhé");  
            tmp = searchSelectorHtml(keySearch);
        }
        else if("JSON".equals(type)){
            System.out.println("json nhé");
            tmp= searchJSON(keySearch);
        }
        else if("XML".equals(type)){
            System.out.println("xml nhé");
            tmp = SearchXML(keySearch);
        }
        else{
            System.out.println("text nhé");
            tmp = SearchText(keySearch);
        }
        return tmp;
    }
    
    public String getTypePa(){
        System.out.println("type nè" + type);
        return type;
    }
    
    //Set giá trị cho cookies
    public void setCookies(Map<String, String> cookies){
        this.cookiesMapSend = cookies;
        for (Map.Entry<String, String> entry : cookiesMapSend.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    //Lấy link các img để lưu
    private ArrayList<String> listImgOnPage() throws IOException{   
        Document doc = Jsoup.parse(res.body());
        
        ArrayList<String> list_img = new ArrayList<>();
        
        Elements eImg = doc.getElementsByTag("img");
        
        String urlTmp;
        
        for (int i = 0; i < eImg.size(); i++) {
            
            if("".equals(eImg.get(i).attr("src"))){
                continue;
            }
            if(eImg.get(i).absUrl("src").contains("http") || eImg.get(i).absUrl("src").contains("https") || eImg.get(i).absUrl("src").contains("www") ){
                urlTmp = eImg.get(i).absUrl("src");
            }
            else {
                if(eImg.get(i).attr("src").charAt(0) == '.'){
                    urlTmp = url +"/"+ eImg.get(i).attr("src").replaceFirst("\\.", "");
                }
                else {
                    urlTmp = url +"/"+ eImg.get(i).attr("src");
                }
            }
            System.out.println("Src nhé " + urlTmp);
            list_img.add(urlTmp);
        }
        urlTmp = "";
        Elements eBgImg = doc.select("[style~=(?i)\\.(png|jpe?g)]");
        
        for (int i = 0; i < eBgImg.size(); i++) {
            
            //vì style="background-image: url(./MessageFile/banner1.jpg)" phải cắt lấy URL
            urlTmp = eBgImg.get(i).attr("style").replaceAll(".*\\(|\\).*", "");
            System.out.println(urlTmp);
            if (urlTmp.equals("")) {
                continue;
            }
            if(urlTmp.contains("http") || urlTmp.contains("https") || urlTmp.contains("www") ){
                if(urlTmp.charAt(0) == (char)39){
                    urlTmp =  urlTmp.replaceAll("\\'", "");
                }
                if(urlTmp.charAt(0) == (char)34){
                    urlTmp = urlTmp.replaceAll("\"", "");
                }
            }
            else if(urlTmp.charAt(0) == '.'){
                urlTmp =  urlTmp.replaceFirst("\\.", "");
                urlTmp = url + "/" + urlTmp;
            }
            else{
                urlTmp = url + "/" + urlTmp;
            }

            list_img.add(urlTmp);
        }
        
        System.out.println("URL Phù hợp");
        for (int i = 0; i < list_img.size(); i++) {
            System.out.println(list_img.get(i));
        }
        return list_img;
    }
    
    // lưu IMG
    private void saveImg(String src, String name, String dir) {
        try {
            URL urlImg = new URL(src);
            URLConnection conn = urlImg.openConnection();

            InputStream in = conn.getInputStream();
            OutputStream out = new BufferedOutputStream(new FileOutputStream(dir + "\\" + name));
                for (int b; (b = in.read()) != -1;) {
                    out.write(b);
                }
                out.close();
                in.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can not Dowload File !");
        }
    }
    //Lưu file
    private void saveFile(String dir) {
        try {
            ArrayList<String> list_img = listImgOnPage();
            
            if(!list_img.isEmpty()){
                for (int i = 0; i < list_img.size(); i++) {
                
                String name = i + ".png";
                saveImg(list_img.get(i), name, dir);
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Không có ảnh để tải");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error", "Error to save file !", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //Lưu cấu hình
    private void writeFileJson(String name) throws IOException{
        try {
            //Khởi tạo luồng ghi json
            OutputStream out = new FileOutputStream("D:\\Hoc_Ky_1_Nam4\\LTM\\RequestSoftware\\src\\FileJSON\\" + name +".json");
            JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
            
            //Có 3 loại ghi vào json: object , Array, String
            writer.beginObject(); // {
            //Lưu url
            writer.name("url").value(url);
            //Lưu method
            String method = jComboBox1.getSelectedItem().toString();
            writer.name("method").value(method);
            if(method.equals("POST")){
                //Lưu Body
                writer.name("body");
                writer.beginObject();// {
                for (Map.Entry<String, String> entry : bodyMap.entrySet()) {
                    writer.name(entry.getKey()).value(entry.getValue());
                }
                writer.endObject(); // }
            }
            //Lưu Headers
            writer.name("headers");
            writer.beginObject();// {
            for (Map.Entry<String, String> entry : headersMap.entrySet()) {
                writer.name(entry.getKey()).value(entry.getValue());
            }
            writer.endObject(); // }
            
            writer.name("TimeSend").value(dateSend);//Tgian gửi
            
            writer.endObject(); // }
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(NewJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jLabel5.setText("Status: ");

        jLabel6.setText("Time: ");

        jLabel7.setText("Type:");

        jButton9.setText("Tìm Kiếm");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addComponent(jButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane2)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jButton9)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane2.addTab("Body", jPanel3);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Value"
            }
        ));
        jScrollPane5.setViewportView(jTable4);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 930, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Cookies", jPanel4);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Key", "Value"
            }
        ));
        jScrollPane4.setViewportView(jTable3);

        jScrollPane6.setViewportView(jScrollPane4);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 930, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 123, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Headers", jPanel5);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Key", "Value"
            }
        ));
        jTable1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTable1PropertyChange(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton2.setText("Thêm Input");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Xóa Input");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setText("Send Request");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jLabel1.setText("Key");

        jLabel2.setText("Value");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton3)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Body", jPanel2);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"User-Agent", "Mozilla/5.0"},
                {"Accept-Encoding", "gzip, deflate, br"},
                {"Connection", "keep-alive"}
            },
            new String [] {
                "Key", "Value"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        jButton5.setText("Xóa Input");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel3.setText("Key");

        jLabel4.setText("Value");

        jButton4.setText("Thêm Input");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField5)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)
                        .addComponent(jButton4)
                        .addComponent(jButton5)))
                .addGap(4, 4, 4))
        );

        jTabbedPane1.addTab("Headers", jPanel1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "GET", "POST" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jButton6.setText("Trích Dữ liệu");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Thêm cookies");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Lưu Ảnh");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton8)
                        .addGap(27, 27, 27)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jTabbedPane2))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed

    }//GEN-LAST:event_jTextField1KeyPressed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        url = jTextField1.getText();// lấy url nhét vào biến toàn cục url
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTable1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange
        if(!isUrlEmpty()){//nếu ko rỗng thì cập nhật URL
            updateURL();
        }
    }//GEN-LAST:event_jTable1PropertyChange

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased

    }//GEN-LAST:event_jTable1KeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(!isUrlEmpty()) {
            String key = jTextField3.getText().trim();
            String value = jTextField4.getText().trim();
            boolean flag = false;
            if (!key.equals("")) {
                if (body.getRowCount() > 0) {

                    for (int i=0; i<body.getRowCount(); i++) {
                        if (key.equals(body.getValueAt(i, 0).toString())) {
                            flag = true;
                            break;
                        }
                    }
                }
                if (!flag) {
                    Payload x = new Payload(key, value);
                    body.addRow(x.toObjects());
                    updateURL();
                } else {
                    JOptionPane.showMessageDialog(null, "Key không được trùng!");
                    return;
                }
                jTextField3.setText("");
                jTextField4.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Key không được rỗng!");
                return;
            }
        } else {
            JOptionPane.showMessageDialog(null, "URL không được rỗng!");
        }
        System.out.println(url);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int row = jTable1.getSelectedRow();
        if(row < 0 || row >= jTable1.getRowCount()){
            JOptionPane.showMessageDialog(null, "Chọn dòng để xóa!!!");
        }
        else{
            if(!isUrlEmpty()){
                body.removeRow(row);
                updateURL();
            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jButton1.setEnabled(false);
        new Thread() {
            @Override
            public void run() {
                try {
                    url = jTextField1.getText().trim();//sửa đây r
//                    System.out.println(url + "vccc");
                    String method = jComboBox1.getSelectedItem().toString();
                    getHeaders();

                    if(!isUrlEmpty()){
                        dateSend = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss").format(Calendar.getInstance().getTime()); // Lưu tgian gửi
                        if(method.equals("GET")){
                            sendGet(url, method, headersMap, cookiesMapSend);
                            
                        }
                        if(method.equals("POST")){
                            getBody();
                            sendPost(url, method , bodyMap, headersMap, cookiesMapSend);
                        }
                        showListHistory();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "URL không được rỗng!");
                    }
                }
                catch (HeadlessException e) {
                    JOptionPane.showMessageDialog(null, e);
                    System.out.println(e);
                }
                jButton1.setEnabled(true);
            }
        }.start();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int row = jTable2.getSelectedRow();
        if(row < 0 || row >= jTable2.getRowCount()){
            JOptionPane.showMessageDialog(null, "Chọn dòng để xóa!!!");
        }
        else{
            headers.removeRow(row);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String key = jTextField5.getText().trim();
        String value = jTextField6.getText().trim();
        boolean flag = false;
        if (!key.equals("")) {
            if (headers.getRowCount() > 0) {
                for (int i=0; i<headers.getRowCount(); i++) {
                    if (key.equals(headers.getValueAt(i, 0).toString())) {
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) {
                Payload x = new Payload(key, value);
                headers.addRow(x.toObjects());
                for (int i=0; i<jTable2.getRowCount(); i++) {

                }
            } else {
                JOptionPane.showMessageDialog(null, "Key không được trùng!");
                return;
            }
            jTextField5.setText("");
            jTextField6.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Key không được rỗng!");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if(!isUrlEmpty()){//nếu ko rỗng thì cập nhật URL sau khi chọn GET hoặc POST
            updateURL();
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if(!jTextArea1.getText().equals("")){//Nếu dữ liệu trống thì không cần tìm kiếm
            //lấy parent cho tk Panel đang sử dụng
            Window parentWindow = SwingUtilities.windowForComponent(this); 
            // or pass 'this' if you are inside the panel
            Frame parentFrame = null;
            if (parentWindow instanceof Frame) {
                parentFrame = (Frame)parentWindow;
            }
            
            SearchResult f = new SearchResult(parentFrame, true);
            f.setVisible(true);
            
        }else{
            JOptionPane.showMessageDialog(null, "Dữ liệu trả về rỗng!");
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        //lấy parent cho tk Panel đang sử dụng
        Window parentWindow = SwingUtilities.windowForComponent(this); 
        // or pass 'this' if you are inside the panel
        Frame parentFrame = null;
        if (parentWindow instanceof Frame) {
            parentFrame = (Frame)parentWindow;
        }
        
        CookiesTable c = new CookiesTable(parentFrame, true);
        c.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        jButton8.setEnabled(false);
        //lấy parent cho tk Panel đang sử dụng
        Window parentWindow = SwingUtilities.windowForComponent(this); 
        // or pass 'this' if you are inside the panel
        Frame parentFrame = null;
        if (parentWindow instanceof Frame) {
            parentFrame = (Frame)parentWindow;
        }
        //Chạy luồng
        new Thread() {
            @Override
            public void run() {
                if(!jTextArea1.getText().equals("")){
                    
                    JFileChooser chooser = new JFileChooser();
                    chooser.setCurrentDirectory(new java.io.File("."));
                    chooser.setDialogTitle("Chọn Folder lưu ảnh!");
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    
                    if (chooser.showOpenDialog(parentWindow) == JFileChooser.APPROVE_OPTION) { 
                        System.out.println("getCurrentDirectory(): " +  chooser.getCurrentDirectory());
                        System.out.println("getSelectedFile() : " +  chooser.getSelectedFile());
                    }
                    else {
                        System.out.println("No Selection ");
                    }
                    chooser.setAcceptAllFileFilterUsed(false);
                    
                    String dir = chooser.getSelectedFile().toString();
                    saveFile(dir);
                    JOptionPane.showMessageDialog(null, "Done!!!");
                }else {
                    JOptionPane.showMessageDialog(null, "Dữ liệu không được trống!");
                }
                jButton8.setEnabled(true);
            }
        }.start();
    }//GEN-LAST:event_jButton8ActionPerformed
    //Nút tìm kiếm
    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if(!jTextArea1.getText().equals("") && !url.equals("")){
            highligh(jTextArea1, jTextField2.getText());
        }else {
            JOptionPane.showMessageDialog(null, "Dữ liệu không được trống!");
        }
    }//GEN-LAST:event_jButton9ActionPerformed
    
    private void showListHistory(){
        //lấy parent cho tk Panel đang sử dụng
        Window parentWindow = SwingUtilities.windowForComponent(this); 
        // or pass 'this' if you are inside the panel
        Frame parentFrame = null;
        if (parentWindow instanceof Frame) {
            parentFrame = (Frame)parentWindow;
        }

        mainQ = (Main) parentFrame;
        mainQ.showListFile();
    }
    
    public void ganData(String nameFile){
        String fileDir = "D:\\Hoc_Ky_1_Nam4\\LTM\\RequestSoftware\\src\\FileJSON\\" + nameFile;
        
        try {
            InputStream out = new FileInputStream(fileDir);
            JsonReader reader = new JsonReader(new InputStreamReader(out, "UTF-8"));
            reader.beginObject();
            String methodFile = "";
            while(reader.hasNext()){
                String name = reader.nextName();
                if(name.equals("url")){
                    jTextField1.setText(reader.nextString());
                }
                else if(name.equals("method")){
                    methodFile = reader.nextString();
                    
                    if(methodFile.equals("GET")){
                        jComboBox1.setSelectedIndex(0);
                    }
                    else {
                        jComboBox1.setSelectedIndex(1);
                    }
                }else if(name.equals("body") && methodFile.equals("POST")){
                    reader.beginObject();
                    body.setRowCount(0);
                    while (reader.hasNext()){
                        Payload da = new Payload(reader.nextName(), reader.nextString());
                        body.addRow(da.toObjects());
                    }
                    reader.endObject();
                }
                else if(name.equals("headers")){
                    reader.beginObject();
                    headers.setRowCount(0);
                    while (reader.hasNext()){
                        Payload da = new Payload(reader.nextName(), reader.nextString());
                        headers.addRow(da.toObjects());
                    }
                    reader.endObject();
                }
                else {// unexpected value, skip it or generate error
                    reader.skipValue();
                }
            }
            System.out.println("Done tải file lên");
            
            reader.endObject();
            reader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(NewJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NewJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
