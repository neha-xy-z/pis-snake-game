package pis.project;

/**
 * Snake Game Deluxe
 * Erweiterung zum Klassiker 'Snake Game'.
 *
 * Interface
 *
 * @author Neha K.
 * @date 05.07.2021
 */

public interface SnakeGameInterface {
    // SETS UP GAME
    void setUpGame();
    // ADDS TAIL ON SNAKE
    void addTail();
    // SHORTENS TAIL
    void shortenTail();
    // IF APPLE HAS BEEN EATEN MOVE THE APPLE
    void ifAppleEatenMove();
    // CHANGE THE DIRECTION OF SNAKE
    void changeDirection(int newDirection);
    // CHECK IF GAME IS OVER
    boolean isGameOver();
}
