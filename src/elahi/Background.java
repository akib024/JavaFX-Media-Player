
package elahi;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

class Background extends JLabel{
  boolean stopCapture = false;
  ByteArrayOutputStream byteArrayOutputStream;
  AudioFormat audioFormat;
  TargetDataLine targetDataLine;
  AudioInputStream audioInputStream;
  SourceDataLine sourceDataLine;
  String string;
  private BufferedImage img;
  
  FileOutputStream fout;
  AudioFileFormat.Type fileType;

  Background(){
    
    final JButton captureBtn = new JButton("Capture");
    final JButton stopBtn = new JButton("Stop");
    final JButton playBtn = new JButton("Save");

    captureBtn.setEnabled(true);
    stopBtn.setEnabled(false);
    playBtn.setEnabled(false);
    
    captureBtn.setBounds(40, 40, 100, 50);
    captureBtn.setFocusPainted(false);
    captureBtn.setBorderPainted(false);
    captureBtn.setBackground(Color.BLACK);
    captureBtn.setFont(new Font("sansserif", Font.CENTER_BASELINE+Font.ITALIC, 13));
    captureBtn.addActionListener(
      new ActionListener(){
        public void actionPerformed(
                        ActionEvent e){
          captureBtn.setEnabled(false);
          stopBtn.setEnabled(true);
          playBtn.setEnabled(false);
         
          captureAudio();
        }
      }
    );
    add(captureBtn);

    stopBtn.setBounds(40, 120, 100, 50);
    stopBtn.setFocusPainted(false);
    stopBtn.setBorderPainted(false);
    stopBtn.setBackground(Color.BLACK);
    stopBtn.setFont(new Font("sansserif", Font.CENTER_BASELINE+Font.ITALIC, 13));
    stopBtn.addActionListener(
      new ActionListener(){
        public void actionPerformed(
                        ActionEvent e){
          captureBtn.setEnabled(false);
          stopBtn.setEnabled(false);
          playBtn.setEnabled(true);
          //Terminate the capturing of
          // input data from the
          // microphone.
          stopCapture = true;
        }//end actionPerformed
      }//end ActionListener
    );//end addActionListener()
    add(stopBtn);
    
    playBtn.setBounds(40, 200, 100, 50);
    playBtn.setFocusPainted(false);
    playBtn.setBorderPainted(false);
    playBtn.setBackground(Color.BLACK);
    playBtn.setFont(new Font("sansserif", Font.CENTER_BASELINE+Font.ITALIC, 13));
    playBtn.addActionListener(
      new ActionListener(){
        public void actionPerformed(
                        ActionEvent e){
            captureBtn.setEnabled(true);
          stopBtn.setEnabled(false);
          playBtn.setEnabled(false);
          
          string = JOptionPane.showInputDialog("Enter the name");
            string = string + ".wav";
            System.out.println(string);

            saveAudio();
            //System.exit(0);
        }//end actionPerformed

              
      }//end ActionListener
    );//end addActionListener()
    //playBtn.setSize(100, 100);
    add(playBtn);
    try {
	      img = ImageIO.read(new File("images/audio-recorder.png"));
	    } catch(IOException e) {
	      e.printStackTrace();
	    }
  }
  
private void captureAudio(){
    try{
      //Get everything set up for
      // capture
      audioFormat = getAudioFormat();
      DataLine.Info dataLineInfo =
                new DataLine.Info(
                  TargetDataLine.class,
                   audioFormat);
      targetDataLine = (TargetDataLine)
                   AudioSystem.getLine(
                         dataLineInfo);
      targetDataLine.open(audioFormat);
      targetDataLine.start();

      //Create a thread to capture the
      // microphone data and start it
      // running.  It will run until
      // the Stop button is clicked.
      Thread captureThread =
                new Thread(
                  new CaptureThread());
      captureThread.start();
    } catch (Exception e) {
      System.out.println(e);
      //System.exit(0);
    }//end catch
  }//end captureAudio method

  //This method plays back the audio
  // data that has been saved in the
  // ByteArrayOutputStream
  private void saveAudio() {
    try{
      //Get everything set up for
      // playback.
      //Get the previously-saved data
      // into a byte array object.
      byte audioData[] =
                 byteArrayOutputStream.
                         toByteArray();
      //Get an input stream on the
      // byte array containing the data
      InputStream byteArrayInputStream
            = new ByteArrayInputStream(
                            audioData);
      AudioFormat audioFormat =
                      getAudioFormat();
      audioInputStream =
        new AudioInputStream(
          byteArrayInputStream,
          audioFormat,
          audioData.length/audioFormat.
                       getFrameSize());
      DataLine.Info dataLineInfo =
                new DataLine.Info(
                  SourceDataLine.class,
                          audioFormat);
      sourceDataLine = (SourceDataLine)
                   AudioSystem.getLine(
                         dataLineInfo);
      sourceDataLine.open(audioFormat);
      sourceDataLine.start();

      //Create a thread to play back
      // the data and start it
      // running.  It will run until
      // all the data has been played
      // back.
      Thread saveThread =
          new Thread(new SaveThread());
      saveThread.start();
    } catch (Exception e) {
      System.out.println(e);
      //System.exit(0);
    }//end catch
  }//end playAudio

  //This method creates and returns an
  // AudioFormat object for a given set
  // of format parameters.  If these
  // parameters don't work well for
  // you, try some of the other
  // allowable parameter values, which
  // are shown in comments following
  // the declarations.
  private AudioFormat getAudioFormat(){
    float sampleRate = 8000.0F;
    //8000,11025,16000,22050,44100
    int sampleSizeInBits = 16;
    //8,16
    int channels = 1;
    //1,2
    boolean signed = true;
    //true,false
    boolean bigEndian = false;
    //true,false
    return new AudioFormat(
                      sampleRate,
                      sampleSizeInBits,
                      channels,
                      signed,
                      bigEndian);
  }//end getAudioFormat
//===================================//

//Inner class to capture data from
// microphone
class CaptureThread extends Thread{
  //An arbitrary-size temporary holding
  // buffer
  byte tempBuffer[] = new byte[10000];
  public void run(){
    byteArrayOutputStream =
           new ByteArrayOutputStream();
    stopCapture = false;
    try{//Loop until stopCapture is set
        // by another thread that
        // services the Stop button.
      while(!stopCapture){
        //Read data from the internal
        // buffer of the data line.
        int cnt = targetDataLine.read(
                    tempBuffer,
                    0,
                    tempBuffer.length);
        if(cnt > 0){
          //Save data in output stream
          // object.
          byteArrayOutputStream.write(
                   tempBuffer, 0, cnt);
         
        
         
        
        }//end if
      }//end while
      byteArrayOutputStream.close();
    }catch (Exception e) {
      System.out.println(e);
      //System.exit(0);
    }//end catch
  }//end run
}//end inner class CaptureThread
//===================================//
//Inner class to play back the data
// that was saved.
class SaveThread extends Thread{
  byte tempBuffer[] = new byte[10000];

  public void run(){
      File file=new File(string);
    try{
      int cnt;
      //Keep looping until the input
      // read method returns -1 for
      // empty stream.
      
       if (AudioSystem.isFileTypeSupported(AudioFileFormat.Type.WAVE, 
              audioInputStream)) {
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, file);
       } 
     
    }catch (Exception e) {
      System.out.println(e);
      //System.exit(0);
    }//end catch
  }//end run
}//end inner class PlayThread
//===================================//
@Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    // paint the background image and scale it to fill the entire space
	    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	  }

    
}
