package elahi;

import java.io.File;
import java.util.Vector;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

class Library {
    Stage stage = new Stage();
    Group group = new Group();
    
    VBox vb = new VBox(5);
    
    //Button[] songs = new Button[10];;
    
    Button add = new Button("+1 Add");
    Button done = new Button("DONE");
    //StackPane root = new StackPane();
    
    File file;
    final Stage primaryStage = null;
    
    Library(){
        final Vector <String> source = new Vector<String>();
        if(source.size() == 10){
            JOptionPane.showMessageDialog(null,
            "Eggs are not supposed to be green.",
            "Inane custom dialog",
            JOptionPane.INFORMATION_MESSAGE);
        }
        else{
        
        }vb.setAlignment(Pos.TOP_LEFT);
        Scene scene = new Scene(group, 400, 400, Color.BLACK);
        //scene.getStylesheets().add(Library.class.getResource("bgtest2.css").toExternalForm());
        //stage.setStyle("-fx-background-color: black;");
        done.setTranslateX(200);
        done.setTranslateY(350);
        done.setStyle("-fx-background-color: orange; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 17px;");
        //done.setPrefSize(80, 30);
        
        add.setTranslateX(20);
        add.setTranslateY(350);
        add.setStyle("-fx-background-color: orange; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 17px;");
        add.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    if(source.size() == 10){
                        JOptionPane.showMessageDialog(null,
                        "You can't add more than ten media files.",
                        "INFORMATION_MESSAGE",
                        JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        FileChooser fileChooser = new FileChooser();
                
                        //Open directory from existing directory
                        if(file != null){
                        File existDirectory = file.getParentFile();
                        fileChooser.setInitialDirectory(existDirectory);
                        }

                        //Set extension filter
                        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("AVI files ", "*.avi", "*.mp3", "*.mp4","*.au","*.wav");
                        fileChooser.getExtensionFilters().add(extFilter);
                
                        //Show open file dialog, with primaryStage blocked.
                        file = fileChooser.showOpenDialog(primaryStage);
                        //System.out.println(file.toURI());
                
                        source.add(file.toURI().toString());
                        String string = source.size() + ". " + file.getName()    ;
                        Button song = new Button(string);
                        song.setStyle("-fx-background-color: black; -fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 17px;");
                
                        vb.getChildren().add(song);
                    }
                    
                
                }
        });
        group.getChildren().add(add);
        
        done.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    if(source.size() == 0){
                        JOptionPane.showMessageDialog(null,
                        "You didn't load any file.",
                        "INFORMATION_MESSAGE",
                        JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        PlayMedia pm = new PlayMedia(source);
                    }
                        
                }
        });
         
        group.getChildren().add(done);
        group.getChildren().addAll( vb);
        
//        group.getChildren().addAll(vb);
        stage.setScene(scene);
        //stage.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        stage.show();
    }
    
}
