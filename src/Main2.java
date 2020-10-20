import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main2 {
    public static void main(String[] args) throws InterruptedException {
        LayoutManager layOut = new FlowLayout(FlowLayout.CENTER);
        Frame f = new Frame();
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
});
        f.setLayout(layOut);
        Label lab = new Label("Welcome to the new program for user management, click the desirable action in this window: ",Label.CENTER);
        Button b1 = new Button("Add User");
        Button b2 = new Button("View all");
        Button b3 = new Button("Edit User");
        Button b4 = new Button("Remove User");
        Button b5 = new Button("Gooo");
        f.add(lab);
        f.add(b1);
        f.add(b2);
        f.add(b3);
        f.add(b4);
        f.setSize(600,600);
        f.setVisible(true);
        
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                TextField tf = new TextField("This is my text field");
                f.add(tf);
//                f.invalidate();
                f.validate();
            }
        });   
    }}
