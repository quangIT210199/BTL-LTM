/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SearchHighLight;

import java.lang.reflect.InvocationTargetException;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class LineHighlightPainter {

    String revisedText = "Extreme programming is one approach "
            + "of agile software development which emphasizes on frequent"
            + " releases in short development cycles which are called "
            + "time boxes. This result in reducing the costs spend for "
            + "changes, by having multiple short development cycles, "
            + "rather than one long one. Extreme programming includes "
            + "pair-wise programming (for code review, unit testing). "
            + "Also it avoids implementing features which are not included "
            + "in the current time box, so the schedule creep can be minimized. ";
    String token = "Extreme programming includes pair-wise programming";

    public static void main(String args[]) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {

                public void run() {
                    new LineHighlightPainter().createAndShowGUI();
                }
            });
        } catch (InterruptedException ex) {
            // ignore
        } catch (InvocationTargetException ex) {
            // ignore
        }
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("LineHighlightPainter demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea area = new JTextArea(9, 45);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setText(revisedText);

        // Highlighting part of the text in the instance of JTextArea
        // based on token.
        highlight(area, token);

        frame.getContentPane().add(new JScrollPane(area), BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    // Creates highlights around all occurrences of pattern in textComp
    public void highlight(JTextComponent textComp, String pattern) {
        // First remove all old highlights
        removeHighlights(textComp);

        try {
            Highlighter hilite = textComp.getHighlighter();
            Document doc = textComp.getDocument();
            String text = doc.getText(0, doc.getLength());

            int pos = 0;
            // Search for pattern
            while ((pos = text.indexOf(pattern, pos)) >= 0) {
                // Create highlighter using private painter and apply around pattern
                hilite.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
                pos += pattern.length();
            }

        } catch (BadLocationException e) {
        }
    }

    // Removes only our private highlights
    public void removeHighlights(JTextComponent textComp) {
        Highlighter hilite = textComp.getHighlighter();
        Highlighter.Highlight[] hilites = hilite.getHighlights();

        for (int i = 0; i < hilites.length; i++) {
            if (hilites[i].getPainter() instanceof MyHighlightPainter) {
                hilite.removeHighlight(hilites[i]);
            }
        }
    }
    // An instance of the private subclass of the default highlight painter
    Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.red);

    // A private subclass of the default highlight painter
    class MyHighlightPainter
            extends DefaultHighlighter.DefaultHighlightPainter {

        public MyHighlightPainter(Color color) {
            super(color);
        }
    }
}
