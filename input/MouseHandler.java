package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import spaceshipgame.Main;

public class MouseHandler implements MouseListener, MouseMotionListener {
	
	private int x, y;
	private boolean isClick;
	private boolean isRightClick;
	public MouseHandler(Main game) {
		game.addMouseListener(this);
		game.addMouseMotionListener(this);
	}
	
	public void getPosition(MouseEvent e) {
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton() == MouseEvent.BUTTON1) {
			isClick = true;
		}
		if(e.getButton() == MouseEvent.BUTTON3) {
			isRightClick = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton() == MouseEvent.BUTTON1) {
			isClick = false;
		}
		if(e.getButton() == MouseEvent.BUTTON3) {
			isRightClick = false;
		}
//		System.out.println(e.getButton() + " was released");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		x = e.getX();
		y = e.getY();
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		x = e.getX();
		y = e.getY();
	}
	public boolean ifClicked(int xMin, int xMax, int yMin, int yMax) {
		if(isClick) {
			if( x > xMin && x < xMax && y > yMin && y < yMax) return true;
			else return false;
		}
		return false;
	}
	public boolean ifClicked(int[] size) {
		int xMin = size[0];
		int xMax = xMin + size[2];
		int yMin = size[1];
		int yMax = yMin + size[3];
		if(isClick) {
			if( x > xMin && x < xMax && y > yMin && y < yMax) return true;
			else return false;
		}
		return false;
	}
	
	public boolean ifRightClicked(int xMin, int yMin, int width, int height) {
		int xMax = xMin + width;
		int yMax = yMin + height;
		if(isRightClick) {
			if( x > xMin && x < xMax && y > yMin && y < yMax) return true;
			else return false;
		}
		return false;
	}
	public boolean ifRightClicked(int[] size) {
		int xMin = size[0];
		int xMax = xMin + size[2];
		int yMin = size[1];
		int yMax = yMin + size[3];
		if(isRightClick) {
			if( x > xMin && x < xMax && y > yMin && y < yMax) return true;
			else return false;
		}
		return false;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

}
