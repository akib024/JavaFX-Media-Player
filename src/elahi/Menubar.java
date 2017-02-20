package elahi;

import java.awt.AWTException;
import java.awt.Frame;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Menubar{
    
    Menubar(){
        
            Stage stage = new Stage();
            stage.setTitle("AV Player");
           
            final VBox rootGroup = new VBox();
            rootGroup.setAlignment(Pos.TOP_CENTER);
            
            //VBox vbox = new VBox(60);
            //vbox.setAlignment(Pos.CENTER);
            
            StackPane root; // :O
            root = new StackPane();
            ///rootGroup.setLayoutX(90);
            
            
            
            
            final MenuBar menuBar = buildMenuBarWithMenus(stage.widthProperty());
            
            
            //rootGroup.getChildren().add(rootGroup2);
            Scene scene = new Scene(rootGroup,640, 400);
            rootGroup.getChildren().add(menuBar);
            
            root.getStyleClass().add("root");
            scene.getStylesheets().add(Menubar.class.getResource("bgtest.css").toExternalForm()); //:O :O
            Button button1 = new Button("Go to Player");
            //button1.setWrapText(true);
            button1.setPrefSize(150, 40);
            button1.setTranslateX(-180);
            button1.setTranslateY(80);
            button1.setStyle("-fx-background-color: yellow; -fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 17px;");
            //button1.setStyle("-fx-background-color: transparent;");
            //button1.setStyle("-fx-font-weight: bold;");
            //button1.setStyle("-fx-font-size: 16px;");
            //button1.setStyle("-fx-text-fill: white;");
            //button1.setMaxHeight(200);
            button1.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    ChooseFile choice = new ChooseFile();
                }
            });
            rootGroup.getChildren().add(button1);
            //rootGroup.setLayoutX(90);
            
            Button button2 = new Button("Recorder");
            button2.setPrefSize(150, 40);
            button2.setTranslateX(-180);
            button2.setTranslateY(120);
            button2.setStyle("-fx-background-color: yellow; -fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 18px;");
            button2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    AudioRecorder recorder = new AudioRecorder();
                }
            });
            rootGroup.getChildren().add(button2);
            
            Button button3 = new Button("Make Library");
            button3.setPrefSize(150, 40);
            button3.setTranslateX(-180);
            button3.setTranslateY(160);
            button3.setStyle("-fx-background-color: yellow; -fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 18px;");
            button3.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    Library lb = new Library();
                }
            });
            rootGroup.getChildren().add(button3);
            //rootGroup.getChildren().addAll( vbox);          
            
            stage.setScene(scene);
            stage.show();
            
    }
    private MenuBar buildMenuBarWithMenus(final ReadOnlyDoubleProperty menuWidthProperty)
    {
        
        
        final MenuBar menuBar = new MenuBar();
        
        
        final Menu fileMenu = new Menu("File");
        
        final MenuItem Open = new MenuItem("Open");
        Open.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    ChooseFile choice = new ChooseFile();
                }
            });
        fileMenu.getItems().add(Open);
        
        fileMenu.getItems().add(new SeparatorMenuItem()); //:O
        
        final MenuItem ex = new MenuItem("Exit");
        ex.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    System.exit(0);
                }
            });
        fileMenu.getItems().add(ex);
        
        menuBar.getMenus().add(fileMenu);
        
        // menu of notepad
        
        final Menu quicknote = new Menu("Quick Note");
        final MenuItem n = new MenuItem("Start");
        
        n.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {
                Frame f = new QuickNote();
                f.setSize(400, 500);
                f.setVisible(true);
                f.show();
            }
            
        });
        
        quicknote.getItems().add(n);
        
        menuBar.getMenus().add(quicknote);
        
        //menu of snapshot
        
        final Menu snapshot = new Menu("Capture");
        final MenuItem s = new MenuItem("Take screenshot");
        
        s.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {
                try {
                    screenShot scr = new screenShot();
                } catch (AWTException ex1) {
                    Logger.getLogger(Menubar.class.getName()).log(Level.SEVERE, null, ex1);
                } catch (IOException ex1) {
                    Logger.getLogger(Menubar.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
            
        });
        
        snapshot.getItems().add(s);
        
        menuBar.getMenus().add(snapshot);
        
        // help menu


        final Menu helpMenu = new Menu("Help");
        
        final MenuItem playmedia = new MenuItem("How To Play Media File");
        playmedia.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {
                PlayMediaHelp mediahelp = new PlayMediaHelp();
            }
        });
        
        helpMenu.getItems().add(playmedia);
        
        final MenuItem recordaudio = new MenuItem("How To Record Audio");
        recordaudio.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {
                RecorderHelp recorderhelp = new RecorderHelp();
            }            
        });
        
        helpMenu.getItems().add(recordaudio);
        
        final MenuItem makinglibrary = new MenuItem("How To Make a Library");
        makinglibrary.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {
                LibraryHelp libraryhelp = new LibraryHelp();
            }
        });
        
        helpMenu.getItems().add(makinglibrary);
        
        menuBar.getMenus().add(helpMenu);
        
        
        final Menu aboutmenu = new Menu("About");
        
        final MenuItem Information = new MenuItem("Developers");
        Information.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {
                InfoAuthors info = new InfoAuthors();
            }
            
        });
        aboutmenu.getItems().add(Information);
        
        menuBar.getMenus().add(aboutmenu);
// bind width of menu bar to width of associated stage
        menuBar.prefWidthProperty().bind(menuWidthProperty);

        return menuBar;
    }

    /*void setDefaultCloseOperation(int EXIT_ON_CLOSE) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    
}
