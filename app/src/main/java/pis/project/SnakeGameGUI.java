package pis.project;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Snake Game Deluxe
 * Erweiterung zum Klassiker 'Snake Game'.
 *
 * GUI-Teil
 *
 * @author Neha K.
 * @date 05.07.2021
 */

public class SnakeGameGUI extends PApplet {

    // RUN SKETCH/ MAIN METHOD
    public static void main(String[] args){
        String[] appArgs = {"Snake Game"};
        SnakeGameGUI snakeGameGUI = new SnakeGameGUI();
        PApplet.runSketch(appArgs, snakeGameGUI);
    }
    SnakeGame snakeGame = new SnakeGame();

    PImage apple_img, stone_img, greenMushroom_img, redMushroom_img, backgroundPicture;

    // SET GAME SIZE
    @Override
    public void settings() {
        size(snakeGame.getWidth(),snakeGame.getHeight()+30);
    }

    // SET UP GAME
    @Override
    public void setup() {
        apple_img = loadImage("clipart294937_1_5.png");

        // Obstacle
        stone_img = loadImage("rock-576682_640.png");

        // Bad items
        greenMushroom_img = loadImage("mushroom-576060_640.png");
        redMushroom_img = loadImage("red-mushroom-23893_960_720.png");

        backgroundPicture = loadImage("gras,-gruner-hintergrund-200990.jpg");
        snakeGame.setUpGame();
    }

    // SHOW SNAKE ON BOARD
    public void showSnake(){
        for (int i=0; i<snakeGame.getSnake_x().size(); i++){

            if (i==0){
               fill(154, 205, 50);
            } else {
                fill(41, 150, 23);
                noStroke();
            }
            rect(snakeGame.getSnake_x().get(i) * snakeGame.getBlockSize(), snakeGame.getSnake_y().get(i) * snakeGame.getBlockSize(), snakeGame.getBlockSize(), snakeGame.getBlockSize(), 7); // 5./last param = round
        }
    }

    // PLACE OBSTACLE AND BAD ITEMS ON BOARD
    public void placeObstacleAndGreenMushroom(){
        for (int i=0; i<snakeGame.getObstacles_x().size(); i++){
            image(stone_img, snakeGame.getObstacles_x().get(i)*snakeGame.getBlockSize(), snakeGame.getObstacles_y().get(i)*snakeGame.getBlockSize(), snakeGame.getBlockSize(), snakeGame.getBlockSize());
        }
        for (int i=0; i<snakeGame.getGreenMushroom_x().size(); i++){
            image(greenMushroom_img, snakeGame.getGreenMushroom_x().get(i)*snakeGame.getBlockSize(), snakeGame.getGreenMushroom_y().get(i)*snakeGame.getBlockSize(), snakeGame.getBlockSize(), snakeGame.getBlockSize());
        }
        for (int i = 0; i < snakeGame.getLevel(); i++){
            image(redMushroom_img, snakeGame.getRedMushroom_x()*snakeGame.getBlockSize(), snakeGame.getRedMushroom_y()*snakeGame.getBlockSize(), snakeGame.getBlockSize(), snakeGame.getBlockSize());
        }
    }

    // IF KEY HAS BEEN PRESSED
    @Override
    public void keyPressed() {
        if(key == CODED){
            switch (keyCode) {
                case DOWN -> snakeGame.changeDirection(0); // index (see x_/y_direction)
                case UP -> snakeGame.changeDirection(1);
                case RIGHT -> snakeGame.changeDirection(2);
                case LEFT -> snakeGame.changeDirection(3);
                default -> snakeGame.changeDirection(-1);
            }
        }
    }

    // DRAW GAME
    @Override
    public void draw() {
        image(backgroundPicture, 0, 0);
        fill(85,128,0); // sap green
        stroke(0);
        rect(0,400,600,30);

        showSnake();

        if(snakeGame.isGameOver() || snakeGame.win()){
            background(85,128,0); // sap green
            fill(255); // white
            textSize(30);
            textAlign(CENTER);
            if (snakeGame.isGameOver()) {
                text(" GAME OVER\n Your Score is: " + (snakeGame.getSnake_x().size() - 1), 300, 200);
            } else if (snakeGame.win()){
                text(" CONGRATULATIONS\n Your Score is: " + (snakeGame.getSnake_x().size() - 1), 300, 200);
            }
            textSize(15);
            text("The highest Score is: " + (snakeGame.getHighestScore()), 300, 350);
            text("ENTER 'R' TO RESTART GAME\n or CLICK EXIT TO LEAVE GAME", 300, 100);

            // if user restarts game
            if(keyPressed){
                if (key == 'R' || key == 'r') {
                    snakeGame.restartGame();
                    snakeGame.changeDirection(2);
                    setup();
                }
            }
        } else {
            image(apple_img, snakeGame.getApple_x()*snakeGame.getBlockSize(), snakeGame.getApple_y()*snakeGame.getBlockSize(), snakeGame.getBlockSize(), snakeGame.getBlockSize());
            placeObstacleAndGreenMushroom();

            textAlign(LEFT);
            textSize(20);
            fill(255); // white
            text("Score: "+ (snakeGame.getSnake_x().size()-1), 10,420);

            if(frameCount%10==0){    // default frame rate=60, 60/10= 6frames per sec

                // add tail in given direction and move
                snakeGame.addTail();

                // if apple has been eaten
                // place apple at random position
                snakeGame.ifAppleEatenMove();

                // level up by checking length of snake
                // place bad items and obstacle
                snakeGame.levelUp();

                // check if bad items has been eaten
                snakeGame.ifBadItemIsEaten();
            }
        }
    }
}