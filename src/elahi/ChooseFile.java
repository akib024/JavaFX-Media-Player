package elahi;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

class ChooseFile {
    File file;
    ChooseFile(){
        final Stage primaryStage = null;
        
        FileChooser fileChooser = new FileChooser();
                
                //Open directory from existing directory
                if(file != null){
                    File existDirectory = file.getParentFile();
                    fileChooser.setInitialDirectory(existDirectory);
                }

                //Set extension filter
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Media files ", "*.mp3", "*.mp4", "*.au", "*.wav", "*.flv" );
                fileChooser.getExtensionFilters().add(extFilter);
                
                //Show open file dialog, with primaryStage blocked.
                file = fileChooser.showOpenDialog(primaryStage);
                
                String string = file.toURI().toString();;
                Play music = new Play(string);
                //music.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
}
