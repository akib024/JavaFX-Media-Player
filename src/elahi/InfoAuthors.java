package elahi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;

public class InfoAuthors extends JFrame{
    
    JLabel label = new JLabel();  
    
    public InfoAuthors(){
        
        setTitle("Developers");
        setSize(350, 600);
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        
        setLayout(new BorderLayout());
        JLabel label=new JLabel(new ImageIcon("images/info1.jpg"));
        add(label);
        label.setLayout(new FlowLayout());
        
        pack();  
         
    
  }
}