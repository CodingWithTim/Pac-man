package Pacman;

public class PowerFood {
	private int tileX, tileY;
	private int xPos, yPos;
	
	private boolean active;
	
	public PowerFood(int x, int y) {
		active = true;
		tileX = x;
		tileY = y;
		xPos = tileX * 30 + 10;
		yPos = tileY * 30 + 10;
	}
	
	public void setActive(boolean a) {
		active = a;
	}
	
	public boolean getActive() {
		return active;
	}
	
	public int getTileX() {
		return tileX;
	}
	
	public int getTileY() {
		return tileY;
	}
	
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
}
