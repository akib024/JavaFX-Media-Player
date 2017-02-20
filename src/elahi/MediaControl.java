
package elahi;

import java.awt.AWTException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class MediaControl extends BorderPane{

    private MediaPlayer mp;
    private MediaView mediaView;
    private final boolean repeat = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Duration duration;
    private Slider timeSlider;
    private Label playTime;
    private Slider volumeSlider;
    HBox mediaBar = new HBox();
    
    

    public MediaControl(final MediaPlayer mp) {
        mediaBar.setFillHeight(true);
        this.mp = mp;
        setStyle("-fx-background-color: #bfc2c7;");
        mediaView = new MediaView(mp);
        StackPane mvPane = new StackPane() {
        };
        
        
        final DoubleProperty width = mediaView.fitWidthProperty();
        final DoubleProperty height = mediaView.fitHeightProperty();
    
        width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
    
        mediaView.setPreserveRatio(true);
        
        final Timeline slideIn = new Timeline();
        final Timeline slideOut = new Timeline();
        this.setOnMouseExited(new EventHandler<MouseEvent>() {
        //mvPane.setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                slideOut.play();
            }
        });
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
        //mvPane.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                slideIn.play();
            }
        });
        
        mvPane.getChildren().add(mediaView);
        mvPane.setStyle("-fx-background-color: black;");
        setCenter(mvPane);

        
        mediaBar.setAlignment(Pos.BASELINE_LEFT);
        mediaBar.setPadding(new Insets(10, 20, 10, 20));
        mediaBar.setStyle("-fx-background-color: #00BFFF;");
        //mediaBar.setDisable(true);
        BorderPane.setAlignment(mediaBar, Pos.BOTTOM_LEFT);
        
        
        //add stop
        final Image stop    = new Image(getClass().getResourceAsStream("stop-button-th.jpg"));
        Button stp = new Button("", new ImageView(stop));
        stp.setStyle("-fx-background-color: #00BFFF;");
        stp.setOnAction(new EventHandler<ActionEvent>() {
           
            @Override
            public void handle(ActionEvent e) {
                //try {
                    mp.stop();
                }
            
        });
        mediaBar.getChildren().add(stp);
        
        
        
        final ToggleButton toggle      = new ToggleButton();
        toggle.setStyle("-fx-background-color: #00BFFF;");
        final Image        unselected  = new Image(getClass().getResourceAsStream("play.jpg"));
        final Image        selected    = new Image(getClass().getResourceAsStream("pause.jpg"));
        final ImageView    toggleImage = new ImageView();
        toggle.setGraphic(toggleImage);
        toggle.setAlignment(Pos.BOTTOM_RIGHT);
        toggleImage.imageProperty().bind(Bindings
            .when(toggle.selectedProperty())
            .then(selected)
            .otherwise(unselected)
        );
        toggle.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Status status = mp.getStatus();

                if (status == Status.UNKNOWN || status == Status.HALTED) {
                    // don't do anything in these states
                    return;
                }

                if (status == Status.PAUSED
                        || status == Status.READY
                        || status == Status.STOPPED) {
                    // rewind the movie if we're sitting at the end
                    if (atEndOfMedia) {
                        mp.seek(mp.getStartTime());
                        atEndOfMedia = false;
                    }
                    mp.play();
                } else {
                    mp.pause();
                }
            }
        });
        mp.currentTimeProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                updateValues();
            }
        });

        mp.setOnPlaying(new Runnable() {
            public void run() {
                if (stopRequested) {
                    mp.pause();
                    stopRequested = false;
                } else {
                    //Play.setVisible(false);
                    //Pause.setVisible(true);
                    //playButton.getStylesheets().add(MediaControl.class.getResource("button.css").toExternalForm());
                    //playButton.setText("||", new ImageView(img1));
                }
                
                slideOut.getKeyFrames().addAll(
                        new KeyFrame(new Duration(0),
                        //new KeyValue(vbox.translateYProperty(), h - 100),
                        new KeyValue(mediaBar.opacityProperty(), 1)
                        ),
                        
                        new KeyFrame(new Duration(600),
                        //new KeyValue(mediaBar.translateYProperty(), h),
                        new KeyValue(mediaBar.opacityProperty(), 0.0)
                        )
                );
                
                slideIn.getKeyFrames().addAll(
                        new KeyFrame(new Duration(0),
                        //new KeyValue(mediaBar.translateYProperty(), h),
                        new KeyValue(mediaBar.opacityProperty(), 0.0)
                        ),
                        new KeyFrame(new Duration(300),
                        //new KeyValue(vbox.translateYProperty(), h - 100),
                        new KeyValue(mediaBar.opacityProperty(), 1)
                        )
                );
            }
        });

        mp.setOnPaused(new Runnable() {
            public void run() {
                System.out.println("onPaused");
                //Play.setVisible(true);
                //Pause.setVisible(false);
            }
        });

        mp.setOnReady(new Runnable() {
            public void run() {
                duration = mp.getMedia().getDuration();
                updateValues();
            }
        });

        mp.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
        mp.setOnEndOfMedia(new Runnable() {
            public void run() {
                if (!repeat) {
                  //  Play.setVisible(true);
                    //Pause.setVisible(false);
                    stopRequested = true;
                    atEndOfMedia = true;
                }
            }
        });

        mediaBar.getChildren().add(toggle);
        // Add spacer
        Label spacer = new Label("   ");
        //mediaBar.getChildren().add(spacer);

        // Add Time label
        Label timeLabel = new Label("Time: ");
        timeLabel.setFont(Font.font(null, FontWeight.EXTRA_LIGHT, 20));
        timeLabel.setTextFill(Color.WHITE);
        mediaBar.getChildren().add(timeLabel);

        // Add time slider
        timeSlider = new Slider();
        HBox.setHgrow(timeSlider, Priority.ALWAYS);
        timeSlider.setMinWidth(50);
        timeSlider.setMaxWidth(Double.MAX_VALUE);
        timeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (timeSlider.isValueChanging()) {
                    
                    // multiply duration by percentage calculated by slider position
                    mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
                }
            }
        });
        mediaBar.getChildren().add(timeSlider);

        // Add Play label
        playTime = new Label();
        playTime.setPrefWidth(130);
        playTime.setMinWidth(50);
        mediaBar.getChildren().add(playTime);
        
        //add snap
        final Image shot    = new Image(getClass().getResourceAsStream("CameraO.jpg"));
        Button snap = new Button("", new ImageView(shot));
        snap.setStyle("-fx-background-color: #00BFFF;");
        snap.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                try {
                    screenShot SC = new screenShot();
                } catch (AWTException ex) {
                    Logger.getLogger(MediaControl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MediaControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        mediaBar.getChildren().add(snap);

        // Add the volume label
        Label volumeLabel = new Label("Vol: ");
        volumeLabel.setFont(Font.font(null, FontWeight.EXTRA_LIGHT, 20));
        volumeLabel.setTextFill(Color.WHITE);
        mediaBar.getChildren().add(volumeLabel);
        
        
        
        // Add Volume slider
        volumeSlider = new Slider();
        volumeSlider.setPrefWidth(70);
        volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
        volumeSlider.setMinWidth(30);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (volumeSlider.isValueChanging()) {
                    mp.setVolume(volumeSlider.getValue() / 100.0);
                }
            }
        });
        mediaBar.getChildren().add(volumeSlider);
        
        setBottom(mediaBar);
    }

    protected void updateValues() {
        if (playTime != null && timeSlider != null && volumeSlider != null) {
            Platform.runLater(new Runnable() {
                public void run() {
                    Duration currentTime = mp.getCurrentTime();
                    playTime.setFont(Font.font(null, FontWeight.EXTRA_LIGHT, 20));
                    playTime.setTextFill(Color.WHITE);
                    
                    playTime.setText(formatTime(currentTime, duration));
                    
                    timeSlider.setDisable(duration.isUnknown());
                    if (!timeSlider.isDisabled()
                            && duration.greaterThan(Duration.ZERO)
                            && !timeSlider.isValueChanging()) {
                        timeSlider.setValue(currentTime.divide(duration).toMillis()
                                * 100.0);
                    }
                    if (!volumeSlider.isValueChanging()) {
                        volumeSlider.setValue((int) Math.round(mp.getVolume()
                                * 100));
                    }
                }
            });
        }
    }

    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60
                    - durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds, durationMinutes,
                        durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes,
                        elapsedSeconds);
            }
        }
    }


}