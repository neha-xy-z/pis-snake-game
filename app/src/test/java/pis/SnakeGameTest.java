package pis;

import static org.junit.Assert.*;
import org.junit.Test;
import pis.project.SnakeGame;

/**
 * Snake Game Deluxe
 * Erweiterung zum Klassiker 'Snake Game'.
 *
 * Test
 *
 * @author Neha K.
 * @date 05.07.2021
 */

public class SnakeGameTest{

    SnakeGame sG = new SnakeGame();

    // Testabsicht: 1
    @Test public void getSizeOfWindow() {
        assertNotEquals("Spielbrettbreite betraegt 600!",300, sG.getWidth());
        assertNotEquals("Spielbretthoehe betraegt 400!",300, sG.getHeight());
    }

    // Testabsicht: 1
    @Test public void restartGame() {
        sG.setUpGame();
        sG.getSnake_x().set(0, 15);
        sG.getSnake_y().set(0, 15);
        sG.ifAppleEatenMove();
        sG.levelUp();

        int x = sG.getSnake_x().get(0);
        int y = sG.getSnake_y().get(0);
        assertEquals("Schlange bewegt sich im Spielfeld.",15, x);
        assertEquals("Schlange bewegt sich im Spielfeld.",15, y);

        sG.restartGame();
        int new_x = sG.getSnake_x().size();
        int new_y = sG.getSnake_y().size();
        assertEquals("Schlange befindet sich wieder am Startpunkt!",0, new_x);
        assertEquals("Schlange befindet sich wieder am Startpunkt!",0, new_y);
    }

    // Testabsicht: 2
    @Test public void add_and_remove_tail(){
        sG.setUpGame();
        sG.addTail();
        assertEquals("Schlange erhaelt einen Schwanzteil.", 2, sG.getSnake_x().size());
        assertEquals("Schlange erhaelt einen Schwanzteil.", 2, sG.getSnake_y().size());

        sG.shortenTail();
        assertEquals("Der Schlange wurde ein Schwanzteil entfernt!",1, sG.getSnake_x().size());
        assertEquals("Der Schlange wurde ein Schwanzteil entfernt!",1, sG.getSnake_y().size());
    }

    // Testabsicht: 2
    @Test public void ifGreenMushroomHasBeenEatenReduceArrayList(){
        sG.setUpGame();
        sG.getSnake_x().set(0, 15);
        sG.ifAppleEatenMove();

        sG.getSnake_x().set(0, sG.getApple_x());
        sG.getSnake_y().set(0,sG.getApple_y());
        sG.ifAppleEatenMove();

        sG.levelUp();
        assertEquals("Ein gruener Pilz befindet sich auf dem Spielbrett.",1, sG.getGreenMushroom_x().size());
        assertEquals("Ein gruener Pilz befindet sich auf dem Spielbrett.",1, sG.getGreenMushroom_y().size());

        int mush_x = sG.getGreenMushroom_x().get(0);
        int mush_y = sG.getGreenMushroom_y().get(0);

        sG.getSnake_x().set(0, mush_x);
        sG.getSnake_y().set(0, mush_y);

        sG.ifBadItemIsEaten();
        assertEquals("Schlange hat den gruenen Pilz gefressen! Es befindet sich nicht mehr auf dem Spielbrett.",0, sG.getGreenMushroom_x().size());
        assertEquals("Schlange hat den gruenen Pilz gefressen! Es befindet sich nicht mehr auf dem Spielbrett.",0, sG.getGreenMushroom_y().size());
    }

    // Testabsicht: 4
    @Test public void redMushroomHasBeenEatenAndIsLevelUpgraded(){
        sG.setUpGame();
        sG.getSnake_x().set(0, 15);
        sG.ifAppleEatenMove();

        sG.levelUp();
        assertEquals("Roter Pilz befindet sich nicht auf dem Spielbrett",0, sG.getRedMushroom_x());
        assertEquals("Level nicht gestiegen!",0, sG.getLevel());
        for(int i = 0; i < 10; i++) {
            sG.getSnake_x().set(0, sG.getApple_x());
            sG.getSnake_y().set(0, sG.getApple_y());
            sG.ifAppleEatenMove();
            sG.addTail();
            sG.levelUp();
        }
        assertEquals("Spieler hat einen neuen Roten Pilz auf dem Spielbrett erhalten! Level aufgestiegen!",1, sG.getLevel());

        sG.getSnake_x().set(0,sG.getRedMushroom_x());
        sG.getSnake_y().set(0,sG.getRedMushroom_y());

        sG.ifBadItemIsEaten();
        assertEquals("Schlange hat den roten Pilz gefressen! Es befindet sich nicht mehr auf dem Spielbrett.",-1, sG.getRedMushroom_x());
        assertEquals("Schlange hat den roten Pilz gefressen! Es befindet sich nicht mehr auf dem Spielbrett.",-1, sG.getRedMushroom_y());
    }

    // Testabsicht: 1
    @Test public void checkMovement(){
        sG.setUpGame();
        sG.changeDirection(1);
        sG.addTail();

        int x = sG.getSnake_x().get(0);
        int y = sG.getSnake_y().get(0);
        assertEquals("Schange bewegt sich nach oben! Bleibt somit auf der 'Spalte' Null.",0, x);
        assertEquals("Schange bewegt sich nach oben! Wechselt somit auf der 'Zeile' 14.",14, y);
    }

    // Testabsicht: 2
    @Test public void appleHasBeenMoved(){
        sG.setUpGame();
        assertEquals("Apfel wurde am Startpunkt positioniert.",15, sG.getApple_x());
        assertEquals("Apfel wurde am Startpunkt positioniert.",15, sG.getApple_y());

        sG.getSnake_x().set(0, 15);
        sG.ifAppleEatenMove();

        assertEquals("Apfel wurde neu positioniert!",sG.getApple_x(), sG.getApple_x());
        assertEquals("Apfel wurde neu positioniert!",sG.getApple_y(), sG.getApple_y());
    }

    // Testabsicht: 5
    @Test public void checkOverlap(){
        sG.setUpGame();
        for(int i = 0; i < 11; i++) {
            sG.getSnake_x().set(0, sG.getApple_x());
            sG.getSnake_y().set(0, sG.getApple_y());
            sG.ifAppleEatenMove();
            sG.addTail();
            sG.levelUp();
        }
        assertFalse("Apfel und Gruener Pilz befinden sich nicht auf derselben Position!",
                sG.isOverlappingWithArrayList(sG.getGreenMushroom_x(), sG.getGreenMushroom_y(), sG.getApple_x(), sG.getApple_y()));
        assertFalse("Apfel und Stein befinden sich nicht auf derselben Position!",
                sG.isOverlappingWithArrayList(sG.getObstacles_x(), sG.getObstacles_y(), sG.getApple_x(), sG.getApple_y()));
        assertFalse("Apfel und Roter Pilz befinden sich nicht auf derselben Position!",
                sG.isOverlapping(sG.getRedMushroom_x(), sG.getRedMushroom_y(), sG.getApple_x(), sG.getApple_y()));

        assertFalse("Stein und Roter Pilz befinden sich nicht auf derselben Position!",
                sG.isOverlappingWithArrayList(sG.getObstacles_x(), sG.getObstacles_y(),sG.getRedMushroom_x(), sG.getRedMushroom_y()));

        assertFalse("Stein und Gruener Pilz befinden sich nicht auf derselben Position!",
                (sG.isOverlappingWithArrayList(sG.getObstacles_x(), sG.getObstacles_y(), sG.getApple_x(), sG.getApple_y())) &&
                        sG.isOverlappingWithArrayList(sG.getGreenMushroom_x(), sG.getGreenMushroom_y(), sG.getApple_x(), sG.getApple_y()));
    }

    // Testabsicht: 3
    @Test public void checkGameOver(){
        sG.setUpGame();
        sG.getSnake_y().set(0, 30);
        assertTrue("Schlange außerhalb des Spielfelds!", sG.isGameOver());
        sG.setUpGame();
        sG.shortenTail();
        assertTrue("Kopf der Schlange kann nicht gekürzt werden!", sG.isGameOver());
        sG.setUpGame();
        sG.getSnake_x().set(0, 3);
        sG.addTail();
        sG.addTail();
        sG.changeDirection(3);
        assertTrue("Schlange darf sich nicht selbst fressen!", sG.isGameOver());
    }

    // Testabsicht: 1
    @Test public void x_equals_y() {
        sG.setUpGame();
        sG.addTail();
        assertEquals("Schlangenschwanzteile: Positionen x und y im ArrayList haben die selbe Anzahl!", sG.getSnake_x().size(), sG.getSnake_y().size());
        assertEquals("Steine: Positionen x und y im ArrayList haben die selbe Anzahl!",sG.getObstacles_x().size(), sG.getObstacles_y().size());
        assertEquals("Grüne Pilze: Positionen x und y im ArrayList haben die selbe Anzahl!",sG.getGreenMushroom_x().size(), sG.getGreenMushroom_y().size());
    }

    // Testabsicht: 1
    @Test public void areObjectsInsideBorder() {
        sG.setUpGame();
        sG.getSnake_x().set(0, 15);
        sG.ifAppleEatenMove();
        assertTrue("Apfel befindet sich innerhalb des Spielbretts!",
                ((sG.getApple_x() >= 0 && sG.getApple_x() <= 30) && (sG.getApple_y() >= 0 && sG.getApple_y() <= 20)));

        sG.getSnake_x().set(0, sG.getApple_x());
        sG.getSnake_y().set(0,sG.getApple_y());
        sG.ifAppleEatenMove();
        sG.levelUp();

        for(int i = 0; i < sG.getObstacles_x().size(); i++){
            assertTrue("Stein befindet sich innerhalb des Spielbretts!",
                    ((sG.getObstacles_x().get(i) >= 0 && sG.getObstacles_x().get(i) <= 30) && (sG.getObstacles_y().get(i) >= 0 && sG.getObstacles_y().get(i) <= 20)));
        }

        for(int i = 0; i < sG.getGreenMushroom_x().size(); i++){
            assertTrue("Grüner Pilz befindet sich innerhalb des Spielbretts!",
                    ((sG.getGreenMushroom_x().get(i) >= 0 && sG.getGreenMushroom_x().get(i) <= 30) && (sG.getGreenMushroom_y().get(i) >= 0 && sG.getGreenMushroom_y().get(i) <= 20)));
        }
    }
}