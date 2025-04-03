import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
public class ControlDrill {
    private Text haulText;
    private Text moneyText;
    private HashMap<Point2D, ImageView> imageMap;
    private ImageView machineImageView;
    int haul;
    int money;
    private Attributes amazoniteObject;
    private Attributes diamondObject;
    private Attributes bronziumObject;
    private Attributes rubyObject;
    private Pane screen;


    /**
     * Constructs a ControlDrill object with the specified parameters.
     *
     * @param screen The Pane representing the game screen.
     * @param machineImageView The ImageView representing the drilling machine.
     * @param imageMap The HashMap containing the mapping of positions to ImageViews.
     * @param haul The current haul value.
     * @param haulText The Text object displaying the haul value.
     * @param money The current amount of money collected.
     * @param moneyText The Text object displaying the money amount.
     * @param diamondObject The Attributes object representing diamond attributes.
     * @param amazoniteObject The Attributes object representing amazonite attributes.
     * @param bronziumObject The Attributes object representing bronzium attributes.
     * @param rubyObject The Attributes object representing ruby attributes.
     */

    public ControlDrill(Pane screen, ImageView machineImageView, HashMap<Point2D, ImageView> imageMap, int haul, Text haulText, int money, Text moneyText, Attributes diamondObject, Attributes amazoniteObject, Attributes bronziumObject, Attributes rubyObject) {
        this.screen = screen;
        this.machineImageView = machineImageView;
        this.imageMap = imageMap;
        this.haul = haul;
        this.amazoniteObject = amazoniteObject;
        this.diamondObject = diamondObject;
        this.bronziumObject = bronziumObject;
        this.rubyObject = rubyObject;
        this.haulText=haulText;
        this.money = money;
        this.moneyText = moneyText;
    }

    /**
     * Retrieves the current amount of money collected by the player.
     *
     * @return The current amount of money.
     */
    public int getMoney() {
        return money;
    }

    /**
     * Displays the game over screen with a dark red background and "GAME OVER" message.
     * This method adds a dark red rectangle to cover the game area and places a "GAME OVER" message
     * in the center of the screen with white color and Arial font.
     */
    private void gameOver(){
        Rectangle redScreen = new Rectangle(800, 800, Color.DARKRED);
        screen.getChildren().add(redScreen);
        Text finishText = new Text();
        finishText.setText("GAME OVER");
        finishText.setFont(Font.font("Arial", 50));
        finishText.setFill(Color.WHITE);
        finishText.setLayoutX(250);
        finishText.setLayoutY(400);
        screen.getChildren().add(finishText);

        Platform.runLater(() -> {
            // If the user turns off the screen, stop the terminal operation
            Stage stage = (Stage) screen.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                System.exit(0);
            });
        });
    }

    /**
     * Moves the drilling machine ImageView based on the specified KeyCode.
     * Updates the machine's ImageView image according to the direction of movement.
     * Checks for obstacles, valuable items, and boundaries during movement and performs corresponding actions.
     *
     * @param keyCode The KeyCode indicating the direction of movement (UP, DOWN, LEFT, RIGHT).
     */
    public void move(KeyCode keyCode) {

        Image empty = new Image("file:assets/underground/empty_15.png");
        ImageView emptyView = new ImageView(empty);
        emptyView.setId("empty");

        switch (keyCode) {
            case UP:
                Image upMachine = new Image("file:assets/drill/drill_25.png");
                machineImageView.setImage(upMachine);
                int p = (int) machineImageView.getTranslateX();
                int r = (int) machineImageView.getTranslateY();
                ImageView up = imageMap.get(new Point2D(p, r-50));

                try {
                    if (machineImageView.getTranslateY() <=150) {
                        if ((machineImageView.getTranslateY() - 50) <= 0) {
                            machineImageView.setTranslateY(50);
                        }
                        machineImageView.setTranslateY(machineImageView.getTranslateY() - 50);
                    }
                    else if (up.getId().equals("empty")) {
                        machineImageView.setTranslateY(machineImageView.getTranslateY() - 50);
                    }
                }catch (NullPointerException ignored){}
                break;

            case DOWN:
                Image downMachine = new Image("file:assets/drill/drill_40.png");
                machineImageView.setImage(downMachine);

                if ((machineImageView.getTranslateY() + 50) >= 700) {
                    machineImageView.setTranslateY(700);
                } else {
                    machineImageView.setTranslateY(machineImageView.getTranslateY() + 50);
                }

                int x = (int) machineImageView.getTranslateX();
                int y = (int) machineImageView.getTranslateY();
                ImageView down = imageMap.get(new Point2D(x, y));
                checkValuable(down, emptyView, empty,x,y,KeyCode.DOWN);
                break;

            case LEFT:
                Image leftMachine = new Image("file:assets/drill/drill_01.png");
                machineImageView.setImage(leftMachine);
                if ((machineImageView.getTranslateX() - 50) < 50) {
                    machineImageView.setTranslateX(50);
                }
                machineImageView.setTranslateX(machineImageView.getTranslateX() - 50);

                int a = (int) machineImageView.getTranslateX();
                int b = (int) machineImageView.getTranslateY();
                ImageView left = imageMap.get(new Point2D(a, b));
                checkValuable(left,emptyView,empty,a,b, KeyCode.LEFT);
                break;

            case RIGHT:
                Image rightMachine = new Image("file:assets/drill/drill_56.png");
                machineImageView.setImage(rightMachine);
                if ((machineImageView.getTranslateX() + 50) > 700){
                    machineImageView.setTranslateX(700);
                }
                machineImageView.setTranslateX(machineImageView.getTranslateX() + 50);
                int k = (int) machineImageView.getTranslateX();
                int m = (int) machineImageView.getTranslateY();
                ImageView right = imageMap.get(new Point2D(k, m));
                checkValuable(right,emptyView,empty,k,m, KeyCode.RIGHT);
                break;

            default:
                break;
        }
    }

    /**
     * Updates the total haul by adding the given weight to the current haul.
     *
     * @param weight The weight to be added to the total haul.
     * @return The updated total haul after adding the given weight.
     */
    private int totalHaul(int weight){
        haul += weight;
        return haul;
    }

    /**
     * Updates the total worth by adding the given worth to the current money.
     *
     * @param worth The worth to be added to the total worth.
     * @return The updated total worth after adding the given worth.
     */
    private int totalWorth(int worth){
        money += worth;
        return money;
    }

    /**
     * Checks the type of the ImageView at the specified position and performs corresponding actions.
     * If the ImageView represents soil, it replaces it with an empty ImageView.
     * If the ImageView represents an obstacle, it adjusts the position of the machineImageView based on the direction specified by keyCode.
     * If the ImageView represents lava, it triggers the game over process.
     * If the ImageView represents valuable items such as amazonite, bronzium, diamond, or ruby,
     * it updates the haul and money values accordingly, and replaces the ImageView with an empty ImageView.
     *
     * @param checkView   The ImageView to be checked.
     * @param emptyView   The empty ImageView to replace the valuable ImageView after processing.
     * @param empty       The Image representing an empty ImageView.
     * @param k           The x-coordinate of the position of the ImageView.
     * @param m           The y-coordinate of the position of the ImageView.
     * @param keyCode     The KeyCode indicating the direction of movement.
     */
    private void checkValuable(ImageView checkView, ImageView emptyView, Image empty, int k, int m, KeyCode keyCode){
        if (checkView != null){
            if (checkView.getId().equals("soil")) {
                checkView.setImage(empty);
                imageMap.put(new Point2D(k, m), emptyView);
            }
            if (checkView.getId().equals("obstacle")) {
                if (keyCode.equals(KeyCode.RIGHT)){
                    machineImageView.setTranslateX(k -50);
                } else if (keyCode.equals(KeyCode.LEFT)) {
                    machineImageView.setTranslateX(k+50);
                } else if (keyCode.equals(KeyCode.DOWN)) {
                    machineImageView.setTranslateY(m -50);
                }
            }
            if (checkView.getId().equals("lava")) {
                gameOver();
                return;
            }
            if (checkView.getId().equals("amazonite")) {
                checkView.setImage(empty);
                imageMap.put(new Point2D(k, m), emptyView);
                haul = totalHaul(amazoniteObject.getWeight());
                haulText.setText("haul:" + haul);
                money = totalWorth(amazoniteObject.getWorth());
                moneyText.setText("money:" + money);
            }
            if (checkView.getId().equals("bronzium")) {
                checkView.setImage(empty);
                imageMap.put(new Point2D(k, m), emptyView);
                haul = totalHaul(bronziumObject.getWeight());
                haulText.setText("haul:" + haul);
                money = totalWorth(bronziumObject.getWorth());
                moneyText.setText("money:" + money);
            }
            if (checkView.getId().equals("diamond")) {
                checkView.setImage(empty);
                imageMap.put(new Point2D(k, m), emptyView);
                haul = totalHaul(diamondObject.getWeight());
                haulText.setText("haul:" + haul);
                money = totalWorth(diamondObject.getWorth());
                moneyText.setText("money:" + money);
            }
            if (checkView.getId().equals("ruby")) {
                checkView.setImage(empty);
                imageMap.put(new Point2D(k, m), emptyView);
                haul = totalHaul(rubyObject.getWeight());
                haulText.setText("haul:" + haul);
                money = totalWorth(rubyObject.getWorth());
                moneyText.setText("money:" + money);
            }
        }
    }
}