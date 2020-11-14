/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SearchHighLight;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;

public class TestEditorPane01 {

    public static void main(String[] args) {
        new TestEditorPane01();
    }

    public TestEditorPane01() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new EditorPane());
                frame.setSize(400, 400);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            }
        });
    }

    public class EditorPane extends JPanel {

        private JTextPane editor = new JTextPane();
        private int lastMatch;
        private String find = "Method";
        private DefaultHighlighter.DefaultHighlightPainter highlightPainter;
        private Object highlightTag;

        private JTextField searchField;
        private JButton searchButton;

        public EditorPane() {
            setLayout(new BorderLayout());
            editor = new JTextPane();
            try (FileReader reader = new FileReader(new File("D:\\Hoc_Ky_1_Nam4\\LTM\\RequestSoftware\\src\\SearchHighLight\\Text.txt"))) {
                editor.read(reader, "script");
            } catch (IOException exp) {
                exp.printStackTrace();
            }
            add(new JScrollPane(editor));

            JPanel searchPane = new JPanel(new GridBagLayout());
            searchField = new JTextField(10);
            searchButton = new JButton("Search");
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            searchPane.add(searchField, gbc);

            gbc.gridx++;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0;
            searchPane.add(searchButton, gbc);

            searchButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String text = searchField.getText();
                    if (!text.equals(find)) {
                        find = text;
                        lastMatch = 0;
                    }
                    highlightNext();
                }
            });

            add(searchPane, BorderLayout.SOUTH);

        }

        public void highlightNext() {
            //editor là Jpane
            Document document = editor.getDocument();
            try {

                if (lastMatch + find.length() >= document.getLength()) {
                    lastMatch = 0;
                }

                for (; lastMatch + find.length() < document.getLength(); lastMatch++) {
                    String match = document.getText(lastMatch, find.length());
                    if (find.equalsIgnoreCase(match)) {
                        if (highlightTag != null) {
                            editor.getHighlighter().removeHighlight(highlightTag);
                        }

                        if (highlightPainter == null) {
                            highlightPainter = new javax.swing.text.DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
                        }

                        highlightTag = editor.getHighlighter().addHighlight(lastMatch, lastMatch + find.length(), highlightPainter);

                        Rectangle viewRect = editor.modelToView(lastMatch);
                        editor.scrollRectToVisible(viewRect);

                        lastMatch += find.length();
                        break;
                    }
                }
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }

        }

    }
}
