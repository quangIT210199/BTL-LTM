/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SearchHighLight;

import java.awt.Color;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

public class Foo001 {
   public static void main(String[] args) throws BadLocationException {

      JTextArea textArea = new JTextArea(10, 30);

      String text = "hello world. How are you?";

      textArea.setText(text);

      Highlighter highlighter = textArea.getHighlighter();
      HighlightPainter painter = 
             new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
      int p0 = text.indexOf("world");
      int p1 = p0 + "world".length();
      highlighter.addHighlight(p0, p1, painter );

      JOptionPane.showMessageDialog(null, new JScrollPane(textArea));          
   }
}