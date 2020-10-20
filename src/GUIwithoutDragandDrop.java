
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author daumoe
 */
public class GUIwithoutDragandDrop {
    public static void main(String[] args) {
        String [] method_arr = {"GET", "POST", "PUT", "DELETE"};
        
        //init JFrame
        JFrame f = new JFrame();
        JComboBox method = new JComboBox(method_arr);
        JLabel method_label = new JLabel("METHOD");
        JLabel URL_label = new JLabel("URL");
        JTextField url_input = new JTextField(10);
        
        //setup
        f.setTitle("Demo");
        method.setBounds(100, 35, 100, 35);
       
        
        //add component
        f.add(method);
        
        //show 
        f.setSize(1000, 700);
        f.setLayout(null);
        f.setVisible(true);
    }
}
