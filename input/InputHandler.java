package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import spaceshipgame.Main;
/**
 * Write a description of class InputHandler here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class InputHandler implements KeyListener
{
    public InputHandler(Main game) {
        game.addKeyListener(this);
    }
    
    public class Key {
        private int numTimesPressed = 0;
        private boolean pressed = false;
        public boolean isPressed() {
            return pressed;
        }
        public int getNumTimesPressed() {
            return numTimesPressed;
        }
        public void toggle(boolean isPressed) {
            pressed = isPressed;
            if(pressed) {
                numTimesPressed++;
            }
        }
    }
    //public List<Key> Keys = new ArrayList<Key>();
    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key shift = new Key();
    public Key debug = new Key();
    public Key e = new Key();
    public Key q = new Key();
    public Key i = new Key();
    public char cKey;
    public void keyPressed(KeyEvent e) {
       toggleKey(e.getKeyCode(), true);
       cKey = e.getKeyChar();
    }
    public void keyReleased(KeyEvent e) {
       toggleKey(e.getKeyCode(), false);
       cKey = '\0';
    }
    public void keyTyped(KeyEvent e) {
        
    }
    public void toggleKey(int keyCode, boolean isPressed) {
        if(keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) up.toggle(isPressed);
        if(keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) down.toggle(isPressed);
        if(keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) left.toggle(isPressed);
        if(keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) right.toggle(isPressed);
        if(keyCode == KeyEvent.VK_SHIFT) shift.toggle(isPressed);
        if(keyCode == KeyEvent.VK_F3) debug.toggle(isPressed);
        if(keyCode == KeyEvent.VK_E) e.toggle(isPressed);
        if(keyCode == KeyEvent.VK_Q) q.toggle(isPressed);
        if(keyCode == KeyEvent.VK_I) i.toggle(isPressed);
    }
}
