package elahi;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

class Play {
    final Stage primaryStage = new Stage();
    
    public void full(){
        primaryStage.setFullScreen(true);
    }
    
    Play(String MEDIA_URL) {
        //String MEDIA_URL = "D:\\Music\\Hridoy khan\\05. Hridoy Khan - Obujh Bhalobasha www.MyTuneBD.Com.mp3";
        //MEDIA_URL = "file:///" + (MEDIA_URL).replace("\\", "/").replaceAll(" ", "%20");
//        primaryStage.setTitle("Embedded Media Player");
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 540, 241);

        // create media player
        Media media = new Media (MEDIA_URL);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        MediaControl mediaControl = new MediaControl(mediaPlayer);
        scene.setRoot(mediaControl);

        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
    
    
    
}

