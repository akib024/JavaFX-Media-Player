package elahi;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;

public class RecorderHelp extends JFrame{
    
    JLabel label = new JLabel();  
    
    public RecorderHelp(){
        
        setTitle("Help on Recorder");
        setSize(350, 600);
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        
        setLayout(new BorderLayout());
        JLabel label=new JLabel(new ImageIcon("images/RecorderHelp.jpg"));
        add(label);
        label.setLayout(new FlowLayout());
        
        pack();  
         
    
  }
}
    
   /* Box box = Box.createHorizontalBox();
    String help = "Click on the on-screen button \"Recorder\""+
            "\nAmong three options, select \"Capture\" and record."+
            "\nWhen finished, click on \"Stop\" and then \"Save\""+
            "\n It will be saved in .wav format.";
    TextArea helptextarea = new TextArea(help,20,26);
    box.add(new JScrollPane(helptextarea));
    add(box);
    
  }
}*/