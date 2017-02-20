package elahi;
 
      
/*public class QuickNote extends JFrame {
    
  
    
    @Override
    public void paint(Graphics g) {    
     //txt.setOsetOpaque(false);    
     //setLineWrap(true);    
     //setWrapStyleWord(true);   
     ImageIcon icon=new ImageIcon("C:\\Users\\nujhum\\Desktop\\notepad\\note.jpg");  
     Image image=icon.getImage();   
     g.drawImage(image,0,0,null,this);
     
     super.paint(g);  
    }   
    
    */

import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.io.*;
import javax.swing.*;
public class QuickNote extends Frame
{
    String filename;
    JTextArea txt;
    Clipboard clip = getToolkit().getSystemClipboard();
    
    QuickNote()
    {
        setLayout(new GridLayout(1,1));
        txt = new JTextArea();
        txt.setOpaque(false);
        
        add(txt);
        
        MenuBar menubar = new MenuBar();
        
        Menu File = new Menu("File");
        MenuItem newmenu = new MenuItem("New");
        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save");
        
        
        newmenu.addActionListener(new New());
        File.add(newmenu);
        open.addActionListener(new Open());
        File.add(open);
        save.addActionListener(new Save());
        File.add(save);

        menubar.add(File);
        
        Menu Edit = new Menu("Edit");
        MenuItem cut = new MenuItem("Cut");
        MenuItem copy = new MenuItem("Copy");
        MenuItem paste = new MenuItem("Paste");
        
        cut.addActionListener(new Cut());
        Edit.add(cut);
        copy.addActionListener(new Copy());
        Edit.add(copy);
        paste.addActionListener(new Paste());
        Edit.add(paste);
        
        menubar.add(Edit);
     
        setMenuBar(menubar);

        mylistener mylist = new mylistener();
        addWindowListener(mylist);
}
    
    @Override
    public void paint(Graphics g) {    
     //txt.setOsetOpaque(false);    
     //setLineWrap(true);    
     //setWrapStyleWord(true);   
     ImageIcon icon=new ImageIcon("images/note.jpg");  
     Image image=icon.getImage();   
     g.drawImage(image,0,0,null,txt);
     
     super.paint(g);  
    } 

class mylistener extends WindowAdapter{
    public void windowClosing (WindowEvent e){
        System.exit(0);
    }
}

class New implements ActionListener{
    public void actionPerformed(ActionEvent e){

        txt.setText(" ");
        setTitle(filename);
    }
}

class Open implements ActionListener{


    @Override
    public void actionPerformed(ActionEvent e){

        FileDialog fd = new FileDialog(QuickNote.this, "select File",FileDialog.LOAD);
        fd.show();
        if (fd.getFile()!=null){

            filename = fd.getDirectory() + fd.getFile();
            setTitle(filename);
            ReadFile();
        }
        txt.requestFocus();
    }
}

class Save implements ActionListener{

    public void actionPerformed(ActionEvent e){

        FileDialog fd = new FileDialog(QuickNote.this,"Save File",FileDialog.SAVE);
        fd.show();
        if (fd.getFile()!=null){

            filename = fd.getDirectory() + fd.getFile();
            setTitle(filename);
            try{
                DataOutputStream d = new DataOutputStream(new FileOutputStream(filename));
                String line = txt.getText();
                BufferedReader br = new BufferedReader(new StringReader(line));
                while((line = br.readLine())!=null){
                    d.writeBytes(line + "\r\n");
                    d.close();
                }
            }
            catch(Exception ex){

                System.out.println("File not found");
            }
            txt.requestFocus();
        }
    }
}

void ReadFile()
{
    BufferedReader d;
    StringBuffer sb = new StringBuffer();
    try
    {
        d = new BufferedReader(new FileReader(filename));
        String line;
        while((line=d.readLine())!=null)
          sb.append(line + "\n");
        txt.setText(sb.toString());
        d.close();
    }
    catch(FileNotFoundException fe){
        System.out.println("File not Found");
    }
    catch(IOException ioe){}
}

class Cut implements ActionListener{

    public void actionPerformed(ActionEvent e){

        String sel = txt.getSelectedText();
        StringSelection ss = new StringSelection(sel);
        clip.setContents(ss,ss);
        txt.replaceRange(" ",txt.getSelectionStart(),txt.getSelectionEnd());
    }
}

class Copy implements ActionListener{

    public void actionPerformed(ActionEvent e){

        String sel = txt.getSelectedText();
        StringSelection clipString = new StringSelection(sel);
        clip.setContents(clipString,clipString);
    }
}

class Paste implements ActionListener{

    public void actionPerformed(ActionEvent e){
        Transferable cliptran = clip.getContents(QuickNote.this);
        try
        {
            String sel = (String) cliptran.getTransferData(DataFlavor.stringFlavor);
            txt.replaceRange(sel,txt.getSelectionStart(),txt.getSelectionEnd());
        }
        catch(Exception exc)
        {
            System.out.println("not string flavour");
        }
    }
}
}