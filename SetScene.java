import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * The SetScene class is responsible for setting up the game scene, including creating the game environment,
 * handling player interactions, managing game elements, and displaying game over messages.
 * It contains methods to initialize the game scene, handle game over events, and select random images based on weights.
 */
public class SetScene {
    double fuelConsumptionRate;
    double elapsedSeconds;

    /**
     * Initializes the game scene by creating the game environment, setting up player interactions,
     * managing game elements, and displaying the scene.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     */
    public void setting(Stage primaryStage) {
        HashMap<Point2D, ImageView> imageMap = new HashMap<>();

        //Create the game screen
        Pane screen = new Pane();
        screen.setPrefSize(800, 800);

        Random random = new Random();

        // Fill the background with the color orange.
        BackgroundFill backgroundFill = new BackgroundFill(Color.ORANGE, null, null);
        screen.setBackground(new Background(backgroundFill));

        Rectangle blueSky = new Rectangle(800, 153, Color.BLUE);
        screen.getChildren().add(blueSky);

        //Puts the top images to the 150 of the Y coordinates along the x-axis
        for (int x = 0; x < 800; x += 50) {
            Image top = new Image("file:assets/underground/top_01.png");
            ImageView topView = new ImageView(top);
            topView.setId("soil");
            topView.setFitWidth(50);
            topView.setFitHeight(50);
            topView.setTranslateX(x);
            topView.setTranslateY(150); // 3. satırın y koordinatı
            screen.getChildren().add(topView);
            imageMap.put(new Point2D(x, 150), topView);
        }

        //Gets the images from the assets file which is the src folder.
        Image soil = new Image("file:assets/underground/soil_01.png");
        Image obstacle = new Image("file:assets/underground/obstacle_01.png");
        Image lava = new Image("file:assets/underground/lava_02.png");
        Image amazonite = new Image("file:assets/underground/valuable_amazonite.png");
        Attributes amazoniteObject = new Attributes("amazonite", 500000, 120);
        Image bronzium = new Image("file:assets/underground/valuable_bronzium.png");
        Attributes bronziumObject = new Attributes("bronzium", 60, 10);
        Image diamond = new Image("file:assets/underground/valuable_diamond.png");
        Attributes diamondObject = new Attributes("diamond", 100000, 100);
        Image ruby = new Image("file:assets/underground/valuable_ruby.png");
        Attributes rubyObject = new Attributes("ruby", 20000, 80);

        //Puts the images to the list
        List<WeightedElement<Image>> weightedImages = new ArrayList<>();

        //Gives the appropriate weights
        weightedImages.add(new WeightedElement<>(soil, 50));
        weightedImages.add(new WeightedElement<>(bronzium, 3));
        weightedImages.add(new WeightedElement<>(obstacle, 2));
        weightedImages.add(new WeightedElement<>(lava, 3));
        weightedImages.add(new WeightedElement<>(diamond, 3));
        weightedImages.add(new WeightedElement<>(amazonite, 3));
        weightedImages.add(new WeightedElement<>(ruby, 3));

        //Fill the middle part with the soil and random valuables
        for (int i = 50; i < 750; i += 50) {
            for (int j = 200; j < 750; j +=50) {
                Image randomImage = selectRandomImage(weightedImages, random);
                ImageView imageView = new ImageView(randomImage);
                if (randomImage.equals(soil)){
                    imageView.setId("soil");
                }
                if (randomImage.equals(obstacle)){
                    imageView.setId("obstacle");
                }
                if (randomImage.equals(lava)){
                    imageView.setId("lava");
                }
                if (randomImage.equals(amazonite)){
                    imageView.setId("amazonite");
                }
                if (randomImage.equals(bronzium)){
                    imageView.setId("bronzium");
                }
                if (randomImage.equals(diamond)){
                    imageView.setId("diamond");
                }
                if (randomImage.equals(ruby)){
                    imageView.setId("ruby");
                }
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                imageView.setLayoutX(i);
                imageView.setLayoutY(j);
                screen.getChildren().add(imageView);
                imageMap.put(new Point2D(i, j), imageView);
            }
        }

        //Puts the obstacles to the left side of the screen along the y-axis
        for (int a = 200; a < 800; a+=50) {
            ImageView obstacleView = new ImageView(obstacle);
            obstacleView.setId("obstacle");
            obstacleView.setFitWidth(50);
            obstacleView.setFitHeight(50);
            obstacleView.setLayoutX(0);
            obstacleView.setLayoutY(a);
            screen.getChildren().add(obstacleView);
            imageMap.put(new Point2D(0,a), obstacleView);
        }

        //Puts the obstacles to the right side of the screen along the y-axis
        for (int a = 200; a < 800; a+=50) {
            ImageView obstacleView = new ImageView(obstacle);
            obstacleView.setId("obstacle");
            obstacleView.setFitWidth(50);
            obstacleView.setFitHeight(50);
            obstacleView.setLayoutX(750);
            obstacleView.setLayoutY(a);
            screen.getChildren().add(obstacleView);
            imageMap.put(new Point2D(750,a), obstacleView);
        }

        //Puts the obstacles to the bottom of the screen along the x-axis
        for (int a = 0; a < 800; a+=50) {
            ImageView obstacleView = new ImageView(obstacle);
            obstacleView.setId("obstacle");
            obstacleView.setFitWidth(50);
            obstacleView.setFitHeight(50);
            obstacleView.setLayoutX(a);
            obstacleView.setLayoutY(750);
            screen.getChildren().add(obstacleView);
            imageMap.put(new Point2D(a,750), obstacleView);
        }

        //The array containing the fuel level
        double[] fuel = {10000};
        Text fuelText = new Text();
        fuelText.setFont(Font.font("Arial", 30));
        fuelText.setFill(Color.WHITE);
        fuelText.setLayoutX(10);
        fuelText.setLayoutY(40);
        screen.getChildren().add(fuelText);

        //Display the haul text
        int haul = 0;
        Text haulText = new Text();
        haulText.setText("haul:" + haul);
        haulText.setFont(Font.font("Arial", 30));
        haulText.setFill(Color.WHITE);
        haulText.setLayoutX(10);
        haulText.setLayoutY(90);

        //Display the money text
        int money = 0;
        Text moneyText = new Text();
        moneyText.setText("money:" + money);
        moneyText.setFont(Font.font("Arial", 30));
        moneyText.setFill(Color.WHITE);
        moneyText.setLayoutX(10);
        moneyText.setLayoutY(140);

        //Uses the left image in the beginning
        Image leftMachine = new Image("file:assets/drill/drill_01.png");
        ImageView leftMachineView = new ImageView(leftMachine);
        leftMachineView.setFitWidth(57);
        leftMachineView.setFitHeight(60);
        leftMachineView.setTranslateX(400);
        leftMachineView.setTranslateY(0);
        screen.getChildren().add(leftMachineView);

        //Initializes the empty image
        Image empty = new Image("file:assets/underground/empty_15.png");
        ImageView emptyView = new ImageView(empty);
        emptyView.setId("empty");

        //Initializes controlDrill with the given parameters
        ControlDrill controlDrill = new ControlDrill(screen,leftMachineView, imageMap, haul,haulText, money, moneyText, diamondObject, amazoniteObject,bronziumObject, rubyObject);
        Gravity gravity = new Gravity(leftMachineView, imageMap);
        gravity.gravityControl();
        screen.getChildren().add(haulText);
        screen.getChildren().add(moneyText);
        Scene scene = new Scene(screen);

        //Starts the timer to show fuel consumption on the screen
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {

                //Takes the current time
                if (lastUpdate == 0) {
                    lastUpdate = now;
                }

                // Calculates the elapsed time
                long elapsedNanos = now - lastUpdate;

                // Converts the time elapsed in nanoseconds to seconds
                elapsedSeconds = elapsedNanos / 1_000_000_000.0;

                // Reduces fuel
                fuelConsumptionRate = 0.5;

                //Plays the game according to the pressed key
                scene.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.UP) {

                        // Assign the new fuelConsumptionRate value due to drilling
                        fuelConsumptionRate = 10000.0;
                        fuel[0] -= elapsedSeconds * fuelConsumptionRate;
                        controlDrill.move(event.getCode());
                    }
                    else if (event.getCode() == KeyCode.DOWN){
                        fuelConsumptionRate = 10000.0;
                        fuel[0] -= elapsedSeconds * fuelConsumptionRate;
                        controlDrill.move(event.getCode());

                    }
                    else if (event.getCode() == KeyCode.LEFT){
                        fuelConsumptionRate = 10000.0;
                        fuel[0] -= elapsedSeconds * fuelConsumptionRate;
                        controlDrill.move(event.getCode());
                    }
                    else if (event.getCode() == KeyCode.RIGHT){
                        fuelConsumptionRate = 10000.0;
                        fuel[0] -= elapsedSeconds * fuelConsumptionRate;
                        controlDrill.move(event.getCode());
                    }
                });

                // Update the last update time
                lastUpdate = now;
                fuel[0] -= elapsedSeconds * fuelConsumptionRate;

                //If fuel runs out, finish the game
                if (fuel[0] <= 0) {
                    fuel[0] = 0;
                    gameOver(screen,controlDrill);
                    Platform.runLater(() -> {
                        // If the user turns off the screen, stop the terminal operation
                        Stage stage = (Stage) screen.getScene().getWindow();
                        stage.setOnCloseRequest(event -> {
                            System.exit(0);
                        });
                    });
                }

                //Else update the fuel text on the screen
                else {
                    fuelText.setText("fuel: " + String.format("%.3f", fuel[0]));
                }
            }
        };

        // Start the timer
        timer.start();

        primaryStage.setScene(scene);

        // Prevents the screen from growing and shrinking
        primaryStage.setResizable(false);

        primaryStage.setTitle("HU-Load");
        primaryStage.show();
    }

    /**
     * Displays the game over screen with "GAME OVER" message
     * and the amount of money collected by the player.
     *
     * @param screen The Pane where the game over screen will be displayed.
     * @param controlDrill The ControlDrill object used to retrieve the amount of money collected.
     */
    private void gameOver(Pane screen, ControlDrill controlDrill){
        // Create a green screen covering the entire game area.
        Rectangle greenScreen = new Rectangle(800, 800, Color.GREEN);
        screen.getChildren().add(greenScreen);

        // Display the "GAME OVER" message.
        Text finishText = new Text();
        finishText.setText("GAME OVER");
        finishText.setFont(Font.font("Arial", 50));
        finishText.setFill(Color.WHITE);
        finishText.setLayoutX(250);
        finishText.setLayoutY(350);
        screen.getChildren().add(finishText);

        // Display the amount of money collected by the player.
        Text moneyText = new Text();
        moneyText.setText("Collected Money: " + controlDrill.getMoney());
        moneyText.setFont(Font.font("Arial", 50));
        moneyText.setFill(Color.WHITE);
        moneyText.setLayoutX(120);
        moneyText.setLayoutY(430);
        screen.getChildren().add(moneyText);
        screen.setDisable(true);
    }

    /**
     * Selects a random image from a list of weighted elements based on their weights.
     *
     * @param weightedElements A list of weighted elements where each element contains an image and its weight.
     * @param random The Random object used to generate random numbers.
     * @return The randomly selected image from the list, or null if the list is empty.
     */
    private Image selectRandomImage(List<WeightedElement<Image>> weightedElements, Random random) {
        // Calculate the total weight of all elements in the list.
        int totalWeight = weightedElements.stream().mapToInt(WeightedElement::getWeight).sum();

        // Generate a random weight within the range of totalWeight.
        int randomWeight = random.nextInt(totalWeight);

        // Iterate through the weighted elements and find the element corresponding to the random weight.
        int currentWeight = 0;
        for (WeightedElement<Image> weightedElement : weightedElements) {
            currentWeight += weightedElement.getWeight();
            if (randomWeight < currentWeight) {
                return weightedElement.getElement();
            }
        }

        // Return null if the list is empty or if the random weight is greater than or equal to totalWeight.
        return null;
    }

    /**
     * Represents an element with an associated weight.
     *
     * @param <Type> The type of element.
     */
    private static class WeightedElement<Type> {
        private Type element; // The element.
        private int weight; // The weight associated with the element.

        /**
         * Constructs a new WeightedElement with the specified element and weight.
         *
         * @param element The element.
         * @param weight The weight associated with the element.
         */
        public WeightedElement(Type element, int weight) {
            this.element = element;
            this.weight = weight;
        }

        /**
         * Retrieves the element.
         *
         * @return The element.
         */
        public Type getElement() {
            return element;
        }

        /**
         * Retrieves the weight associated with the element.
         *
         * @return The weight.
         */
        public int getWeight() {
            return weight;
        }
    }

}
