package elahi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;

public class LibraryHelp extends JFrame{
    
    JLabel label = new JLabel();  
    
    public LibraryHelp(){
        
        setTitle("Help on Library");
        setSize(350, 600);
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        
        setLayout(new BorderLayout());
        JLabel label=new JLabel(new ImageIcon("images/LibraryHelp.jpg"));
        add(label);
        label.setLayout(new FlowLayout());
        
        pack();  
         
    
  }
}