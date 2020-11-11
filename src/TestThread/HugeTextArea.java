/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestThread;

import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class HugeTextArea implements Runnable {
    private static String str = "";
    private DefaultStyledDocument   document;

    private JFrame                  frame;

    private JTextArea               textArea;

    public HugeTextArea() {
        this.document = new DefaultStyledDocument();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                
                buildLongString(4000000, str);
            }
        };
        new Thread(runnable).start();
    }

    @Override
    public void run() {
        frame = new JFrame();
        frame.setTitle("Huge Text");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea(document);
        textArea.setLineWrap(true);
        frame.add(new JScrollPane(textArea));

        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private void buildLongString(int length, String data) {
        
//        for (int i = 0; i < length; i++) {
            try {
                document.insertString(document.getLength(),
                       data,
                        null);
                System.out.println(document);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
//        }
    }

    public static void main(String[] args) {
        
        
                    try {
        Connection.Response res = Jsoup.connect("http://qldt.ptit.edu.vn")
//                    .headers(head)
                    .method(Connection.Method.GET)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .timeout(20*1000)
                    .execute();
            
            //Thêm vào body
            str = res.parse().html();
            
            
        } catch (IOException ex) {
            
        }
                    SwingUtilities.invokeLater(new HugeTextArea());
    }

}