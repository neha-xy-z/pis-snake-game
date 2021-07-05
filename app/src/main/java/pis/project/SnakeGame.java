package pis.project;

import java.util.ArrayList;

/**
 * Snake Game Deluxe
 * Erweiterung zum Klassiker 'Snake Game'.
 *
 * Logik-Teil
 *
 * @author Neha K.
 * @date 05.07.2021
 */

public class SnakeGame implements SnakeGameInterface {

    private final int width = 600, height = 400;
    private ArrayList<Integer> snake_x = new ArrayList<>(),
            snake_y = new ArrayList<>(),
            greenMushroom_x = new ArrayList<>(),
            greenMushroom_y = new ArrayList<>(),
            obstacles_x =new ArrayList<>(),
            obstacles_y = new ArrayList<>();
    private final int gameWidth = (width / getBlockSize()), gameHeight = (height / getBlockSize());
    private int apple_x, apple_y, redMushroom_x, redMushroom_y, direction = 2; // direction: 0=down, 1=up, 2=right, 3=left
    private int level = 0, score = 0, currentScore = 0, currentScoreExtreme = 0, highestScore = 0;
    private final int[] x_direction = {0, 0, 1, -1}, y_direction = {1, -1, 0, 0};

    // RESTARTS GAME
    public void restartGame() {
        snake_x.clear();
        snake_y.clear();
        obstacles_x.clear();
        obstacles_y.clear();
        greenMushroom_x.clear();
        greenMushroom_y.clear();
        level = 0;
        score = 0;
        currentScore = 0;
        currentScoreExtreme = 0;
    }

    // SETS UP GAME
    @Override
    public void setUpGame() {
        snake_x.add(0);
        snake_y.add(15);
        apple_x = 15;
        apple_y = 15;
    }

    // SHORTENS TAIL
    @Override
    public void shortenTail() {
        snake_x.remove(snake_x.size() - 1);
        snake_y.remove(snake_y.size() - 1);
    }

    // IF BAD ITEM HAS BEEN EATEN SHORTEN TAIL
    public void ifBadItemIsEaten() {

        if (snake_x.get(0) == redMushroom_x && snake_y.get(0) == redMushroom_y){

            for(int i = 0; i < 3; i++){
                shortenTail();
                highestScore--;
            }
            redMushroom_x = -1;
            redMushroom_y = -1;
        }

        for (int i = 0; i < greenMushroom_x.size(); i++) {
            if (snake_x.get(0).equals(greenMushroom_x.get(i)) && snake_y.get(0).equals(greenMushroom_y.get(i))) {
                shortenTail();
                greenMushroom_x.remove(i);
                greenMushroom_y.remove(i);
                currentScore --;
                highestScore--;
            }
        }
    }

    // IF KEY HAS BEEN PRESSED, SET DIRECTION
    @Override
    public void changeDirection(int newDirection) {
        if (newDirection != -1) {
            direction = newDirection;
        }
    }

    // ADD TAIL IN GIVEN DIRECTION (SNAKE MOVEMENT)
    @Override
    public void addTail() {
        snake_x.add(0, snake_x.get(0) + x_direction[direction]);
        snake_y.add(0, snake_y.get(0) + y_direction[direction]);
    }

    // MOVE APPLE
    @Override
    public void ifAppleEatenMove() {

        // if apple has been eaten
        if (snake_x.get(0) == apple_x && snake_y.get(0) == apple_y) {
            score++;
            currentScore++;
            currentScoreExtreme++;

            if (score > highestScore){
                highestScore = score;
            }

            // place apple at random position
            apple_x = (int) (Math.random() * (gameWidth));
            apple_y = (int) (Math.random() * (gameHeight));

            if (isOverlapping(apple_x, apple_y, redMushroom_x, redMushroom_y) || isOverlappingWithArrayList(greenMushroom_x, greenMushroom_y, apple_x, apple_y)  || isOverlappingWithArrayList(obstacles_x, obstacles_y, apple_x, apple_y)) {
                apple_x = (int) (Math.random() * (gameWidth));
                apple_y = (int) (Math.random() * (gameHeight));
            }

        } else {
            // remove tail continuously or else it always gets a new block(tail)
            shortenTail();
        }
    }

    // CHECK IF ITEMS ON BOARD ARE OVERLAPPING
    public boolean isOverlapping(int a, int b, int x, int y){
        return a == x && b == y;
    }

    public boolean isOverlappingWithArrayList(ArrayList<Integer> a, ArrayList<Integer> b, int x, int y){
        return (a.contains(x) && b.contains(y)) || (a.contains(y) && b.contains(x));
    }

    // LEVELING
    public void levelUp(){

        if (currentScore == 2) {

            int stoneX = (int) (Math.random() * (gameWidth)), stoneY = (int) (Math.random() * (gameHeight)), mushroomX = (int) (Math.random() * (gameWidth)), mushroomY = (int) (Math.random() * (gameHeight));

            if(isOverlapping(apple_x, apple_y, stoneX, stoneY) || isOverlappingWithArrayList(greenMushroom_x, greenMushroom_y, stoneX, stoneY) || isOverlapping(redMushroom_x, redMushroom_y, mushroomX, mushroomY)) {
                if(obstacles_x.size() != 20) {
                    obstacles_x.add((int) (Math.random() * (gameWidth)));
                    obstacles_y.add((int) (Math.random() * (gameHeight)));
                }
            } else {
                // place obstacle at random position
                if(obstacles_x.size() != 20) {
                    obstacles_x.add(stoneX);
                    obstacles_y.add(stoneY);
                }
            }
            if(isOverlapping(apple_x, apple_y, mushroomX, mushroomY) || isOverlappingWithArrayList(obstacles_x, obstacles_y, mushroomX, mushroomY) || isOverlapping(redMushroom_x, redMushroom_y, mushroomX, mushroomY)) {

                greenMushroom_x.add((int) (Math.random() * (gameWidth)));
                greenMushroom_y.add((int) (Math.random() * (gameHeight)));

            } else {
                // place bad item at random position
                greenMushroom_x.add(mushroomX);
                greenMushroom_y.add(mushroomY);
            }
            currentScore = 0;
        }
        if (currentScoreExtreme == 10){

            // place bad item at random position
            redMushroom_x = (int) (Math.random() * (gameWidth));
            redMushroom_y = (int) (Math.random() * (gameHeight));

            if (isOverlapping(apple_x, apple_y, redMushroom_x, redMushroom_y) && (isOverlappingWithArrayList(greenMushroom_x, greenMushroom_y, redMushroom_x, redMushroom_y)) && (isOverlappingWithArrayList(obstacles_x, obstacles_y, redMushroom_x, redMushroom_y))) {
                redMushroom_x = (int) (Math.random() * (gameWidth));
                redMushroom_y = (int) (Math.random() * (gameHeight));
            }
            level++;
            currentScoreExtreme = 0;
        }
    }

    // CHECK IF PLAYER WON
    public boolean win(){
        return snake_x.size() == 50;
    }

    // CHECK IF GAME IS OVER
    @Override
    public boolean isGameOver() {
        // if snake outside of window size
        if (snake_x.get(0) < 0 || snake_y.get(0) < 0 || snake_x.get(0) >= gameWidth || snake_y.get(0) >= gameHeight) {
            return true;
        }

        // if snake touches itself
        for (int i = 1; i < snake_x.size(); i++) {
            if (snake_x.get(0).equals(snake_x.get(i)) && snake_y.get(0).equals(snake_y.get(i))) {
                return true;
            }
        }

        // if snake size becomes zero through shortening
        if (snake_x.size() == 0){
            return true;
        }

        // if snake touches obstacle
        for (int i = 0; i < obstacles_x.size(); i++) {
            if (snake_x.get(0).equals(obstacles_x.get(i)) && snake_y.get(0).equals(obstacles_y.get(i))) {
                return true;
            }
        }
        return false;
    }

    // GETTER
    public ArrayList<Integer> getSnake_x() {
        return snake_x;
    }

    public ArrayList<Integer> getSnake_y() {
        return snake_y;
    }

    public ArrayList<Integer> getObstacles_y() {
        return obstacles_y;
    }

    public ArrayList<Integer> getObstacles_x() {
        return obstacles_x;
    }

    public ArrayList<Integer> getGreenMushroom_x() {
        return greenMushroom_x;
    }

    public ArrayList<Integer> getGreenMushroom_y() {
        return greenMushroom_y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getBlockSize() {
        return 20;
    }

    public int getApple_x() {
        return apple_x;
    }

    public int getApple_y() {
        return apple_y;
    }

    public int getLevel() {
        return level;
    }

    public int getRedMushroom_x() {
        return redMushroom_x;
    }

    public int getRedMushroom_y() {
        return redMushroom_y;
    }

    public int getHighestScore() {
        return highestScore;
    }
}