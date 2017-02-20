package elahi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;

public class PlayMediaHelp extends JFrame{
    
    JLabel label = new JLabel();  
    
    public PlayMediaHelp(){
        
        setTitle("Help on Playing Media");
        setSize(350, 600);
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        
        setLayout(new BorderLayout());
        JLabel label=new JLabel(new ImageIcon("images/PlayMediaHelp.jpg"));
        add(label);
        label.setLayout(new FlowLayout());
        
        pack();  
         
    
  }
}
    /*Box box = Box.createHorizontalBox();
    String help = "Either click on the on-screen button \"Go to Player\" and then select the desired song"+
            "\n Or click on \"File\" and then click \"Open\"";
    TextArea helptextarea = new TextArea(help,20,26);
    box.add(new JScrollPane(helptextarea));
    add(box);
    
  }
}*/