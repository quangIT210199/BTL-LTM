/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SearchHighLight;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

public class TextAndNewLinesTest extends JFrame
{
    public TextAndNewLinesTest()
        throws Exception
    {
        String text =
            "one two three four five\r\n" +
            "one two three four five\r\n" +
            "one two three four five\r\n" +
            "one two three four five\r\n" +
            "one two three four five\r\n";

        JTextPane textPane = new JTextPane();
        textPane.setText(text);
        JScrollPane scrollPane = new JScrollPane( textPane );
        getContentPane().add( scrollPane );

        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet keyWord = new SimpleAttributeSet();
        StyleConstants.setBackground(keyWord, Color.CYAN);

        String search = "three";
        int offset = 0;

        int length = textPane.getDocument().getLength();
        text = textPane.getDocument().getText(0, length);

        while ((offset = text.indexOf(search, offset)) != -1)
        {
            doc.setCharacterAttributes(offset, search.length(), keyWord, false); 
            offset += search.length();
        }
    }

    public static void main(String[] args)
        throws Exception
    {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new TextAndNewLinesTest();
        frame.setTitle("Text and New Lines");
        frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
        frame.setSize(400, 120);
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
    }
}