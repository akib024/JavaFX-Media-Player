/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elahi;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author nujhum
 */
class screenShot {
    screenShot() throws AWTException, IOException{
        Robot robot = new Robot();
    BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
    ImageIO.write(screenShot, "JPG", new File("screenShot.jpg"));
    }
    
}
