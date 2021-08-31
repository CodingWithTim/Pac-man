package Pacman;

public class Pacman {
	//variable for Pac's position and velocity
	private final int speed = 2;
	private final int width = 30;
	private final int height = 30;
	
	private String currentMove;
	private String nextMove;
	
	private int foodEaten;
	
	
	private int tileX, tileY;
	private int xPos, yPos, xVel, yVel;
	
	public Pacman(int x, int y) {
		
		//initialize Pacman's position
		tileX = x; 
		tileY = y;
		xPos = x * 30;
		yPos = y * 30;
		
		//intialize Pacman's moves
		currentMove = "NULL";
		nextMove = "NULL";
		
		foodEaten = 0;
		
	}
	
	//this method updates pac man's tile on the layout
	public void updateTile() {
		//check if pac man move to tile above
		if(yPos <= tileY * 30 - 30) {
			tileY = tileY-1;
		}
		
		//check if pac man move to tile below
		if(yPos >= tileY * 30 + 30) {
			tileY = tileY+1;
		}
		
		//check if pac man move to tile left
		if(xPos <= tileX * 30 - 30) {
			tileX = tileX-1;
		}
		
		//check if pac man move to tile left
		if(xPos >= tileX * 30 + 30) {
			tileX = tileX+1;
		}
	}
	//pac man collision detection
	
	//this method detects pac man collision with wall
	//returns true if colliding
	public boolean wallCollisionDetection(int[][] walls, String direction) {
				
		if(direction.equals("UP")) {
			if(walls[tileY-1][tileX] == 1) {
				
				//anti gliche measure
				if(!currentMove.equals(nextMove)) {
					if(Math.abs(xPos - tileX * 30) < 2) {
						return false;
					}
				}
				else {
					return false;
				}
			}
		}
		if(direction.equals("DOWN")) {
			if(walls[tileY+1][tileX] == 1) {	
				
				//anti gliche measure
				if(!currentMove.equals(nextMove)) {
					if(Math.abs(xPos - tileX * 30) < 2) {
						return false;
					}
				}
				else {
					return false;
				}
			}
		}
		if(direction.equals("LEFT")) {
			if(walls[tileY][tileX-1] == 1) {
				
				//anti gliche measure
				if(!currentMove.equals(nextMove)) {
					if(Math.abs(yPos - tileY * 30) < 2) {
						return false;
					}
				}
				else {
					return false;
				}
			}
		}
		if(direction.equals("RIGHT")) {
			if(walls[tileY][tileX+1] == 1) {
				
				//anti gliche measure
				if(!currentMove.equals(nextMove)) {
					if(Math.abs(yPos - tileY * 30) < 2) {
						return false;
					}
				}
				else {
					return false;
				}			
			}
		}
		
		return true;
	}
	
	public void eat() {
		foodEaten += 1;
	}
	
	public void move() {
		xPos += xVel;
		yPos += yVel;
	}
	
	public void up() {
		xVel = 0;
		yVel = -1 * speed;
	}
	
	public void down() {
		xVel = 0;
		yVel = speed;
	}
	
	public void left() {
		xVel = -1 * speed;
		yVel = 0;
	}
	
	public void right() {
		xVel = speed;
		yVel = 0;
	}
	
	//accessors
	public int getFoodEaten() {
		return foodEaten;
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
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public String getCurrentMove() {
		return currentMove;
	}
	
	public String getNextMove() {
		return nextMove;
	}
	
	//modifiers
	public void setXPos(int x) {
		xPos = x;
	}
	
	public void setYPos(int y) {
		yPos = y;
	}
	
	public void setCurrentMove(String m) {
		currentMove = m;
	}
	
	public void setNextMove(String m) {
		nextMove = m;
	}
}
