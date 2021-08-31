package Pacman;

public class food {
	private int tileX, tileY;
	private int xPos, yPos;
	
	private boolean active;
	
	public food(int x, int y) {
		active = true;
		tileX = x;
		tileY = y;
		xPos = tileX * 30 + 14;
		yPos = tileY * 30 + 14;
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
