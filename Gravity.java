import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Manages gravity control for the game, allowing objects to fall vertically.
 * This class applies gravity to the drilling machine by continuously checking if there is an empty space
 * below it. If an empty space is found, it moves the machine downwards. Gravity is applied periodically
 * using a timer.
 */
public class Gravity {

    private final HashMap<Point2D, ImageView> imageMap;
    private final ImageView machineImageView;

    /**
     * Constructs a Gravity object with the specified machine ImageView and image map.
     *
     * @param machineImageView The ImageView representing the drilling machine.
     * @param imageMap The HashMap containing the mapping of positions to ImageViews.
     */
    public Gravity(ImageView machineImageView, HashMap<Point2D, ImageView> imageMap) {
        this.machineImageView = machineImageView;
        this.imageMap = imageMap;
    }

    /**
     * Controls the gravity effect on the drilling machine.
     * This method continuously applies gravity to the drilling machine by periodically checking if there is an
     * empty space below it. If an empty space is found and the machine is not at the bottom boundary, it moves
     * the machine downwards by one grid unit. Gravity is applied periodically using a timer.
     */
    public void gravityControl() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    int currentX = (int) machineImageView.getTranslateX();
                    int currentY = (int) machineImageView.getTranslateY();

                    //If the machine is in the sky
                    if (currentY == 0 || currentY == 50) {
                        machineImageView.setTranslateY(currentY + 50);
                    }

                    //If the machine is in the underground
                    else {
                        ImageView imageView = imageMap.get(new Point2D(currentX, currentY + 50));
                        if (imageView != null && imageView.getId().equals("empty")) {
                            machineImageView.setTranslateY(currentY + 50);
                        }
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(task, 0, 800);
    }
}
