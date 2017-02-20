package elahi;

import javax.swing.*;

public class AudioRecorder  extends JFrame{

  
  
  public AudioRecorder(){
      try {
            UIManager.setLookAndFeel(
               UIManager.getSystemLookAndFeelClassName());
         } catch (Exception e) {}
     Background background = new Background();
    setTitle("Recorder");
    //setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(356, 430);
    setVisible(true);
    setLocationRelativeTo(null);
    add(background);
    
  }
}