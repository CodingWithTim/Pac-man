package Pacman;

import java.awt.Color;
import java.util.ArrayList;

public class Ghost {
	private String type; // There are four types of ghost: Blinky, Pinky, Inky, Clyde
	
	private Color color;
	private Color frightenColor = Color.GRAY;
	private int speed = 2;
	private final int width = 30;
	private final int height = 30;
	
	private boolean active;
	
	private String currentMove;
	private String nextMove;
	
	private String state; // There are four states of ghost: Chase, Scatter, Frighten, and Escape
	
	
	private int tileX, tileY;
	private int xPos, yPos, xVel, yVel;
	
	//constructor
	public Ghost(String t, int x, int y) {
		//initialize tile position
		tileX = x;
		tileY = y;
		xPos = tileX * 30;
		yPos = tileY * 30;
		
		active = false;
		
		type = t;
		//changing the color depending on the type of ghost
		if(type.equals("BLINKY")) {
			color = Color.RED;
		}
		if(type.equals("PINKY")) {
			color = Color.PINK;
		}
		if(type.equals("INKY")) {
			color = Color.CYAN;
		}
		if(type.equals("CLYDE")) {
			color = Color.ORANGE;
		}
		
		state = "SCATTER";
		currentMove = "BEGIN";
	}
	
	public void chase(int pTileX, int pTileY, int[][] walls) {
		speed = 2;
		if(type.equals("BLINKY") || type.equals("INKY")) {
			this.goToTarget(pTileX, pTileY, walls);
		}
		else {
			this.randomMovement(walls);
		}
	}
	
	public void scatter(int[][] walls) {
		speed = 2;
		if(type.equals("BLINKY")) {
			this.goToTarget(17, 1, walls);
		}
		if(type.equals("PINKY")) {
			this.goToTarget(1, 1, walls);
		}
		if(type.equals("INKY")) {
			this.goToTarget(1, 17, walls);
		}
		if(type.equals("CLYDE")) {
			this.goToTarget(17, 17, walls);
		}
	}
	
	public void frighten(int[][] walls) {
		speed = 1;
		this.randomMovement(walls);
	}
	
	public void escape(int[][] walls) {
		speed = 3;
		this.goToTarget(9, 7, walls);
	}
	
	private void randomMovement(int[][] walls) {
		String direction = currentMove;
		
		if(this.isIntersection(walls) == true || this.collision(walls, currentMove) == true) {
			
			boolean[] possibleDirection = new boolean[4];
			int counter = 0;
			
			if(walls[tileY-1][tileX] == 1 && currentMove.equals("DOWN") == false) {
				possibleDirection[0] = true;
				counter++;
			}
			else {
				possibleDirection[0] = false;
			}
			
			if(walls[tileY+1][tileX] == 1 && currentMove.equals("UP") == false) {
				possibleDirection[1] = true;
				counter++;
			}
			else {
				possibleDirection[1] = false;
			}
			
			if(walls[tileY][tileX-1] == 1 && currentMove.equals("RIGHT") == false) {
				possibleDirection[2] = true;
				counter++;
			}
			else {
				possibleDirection[2] = false;
			}
			
			if(walls[tileY][tileX+1] == 1 && currentMove.equals("LEFT") == false) {
				possibleDirection[3] = true;
				counter++;
			}
			else {
				possibleDirection[3] = false;
			}
			
			if(counter == 1){
				for(int i = 0; i < possibleDirection.length; i++) {
					if(possibleDirection[i] == true) {
						if(i == 0) {
							direction = "UP";
						}
						if(i == 1) {
							direction = "DOWN";
						}
						if(i == 2) {
							direction = "LEFT";
						}
						if(i == 3) {
							direction = "RIGHT";
						}
					}
				}
			}
			else if(counter == 2){
				int rand = (int) ( Math.random() * 2 + 1);
				if(rand == 1) {
					for(int i = 0; i < possibleDirection.length; i++) {
						if(possibleDirection[i] == true) {
							if(i == 0) {
								direction = "UP";
							}
							if(i == 1) {
								direction = "DOWN";
							}
							if(i == 2) {
								direction = "LEFT";
							}
							if(i == 3) {
								direction = "RIGHT";
							}
						}
					}
				}
				else {
					boolean sec = false;
					for(int i = 0; i < possibleDirection.length; i++) {
						if(possibleDirection[i] == true && sec == false) {
							sec = true;
						}
						if(possibleDirection[i] == true && sec == true) {
							if(i == 0) {
								direction = "UP";
							}
							if(i == 1) {
								direction = "DOWN";
							}
							if(i == 2) {
								direction = "LEFT";
							}
							if(i == 3) {
								direction = "RIGHT";
							}
						}
					}
				}
			}
			else if(counter == 3) {
				int num = 0;
				
				for(int i = 0; i < possibleDirection.length; i++) {
					int rand = (int) ( Math.random() * 3 + 1);
					if(possibleDirection[i] == true) {
						num += 1;
						if(rand == 1 || num == 2) {
							if(i == 0) {
								direction = "UP";
							}
							if(i == 1) {
								direction = "DOWN";
							}
							if(i == 2) {
								direction = "LEFT";
							}
							if(i == 3) {
								direction = "RIGHT";
							}
						}
					}
				}
			}
			else {
				int num = 0;
				
				for(int i = 0; i < possibleDirection.length; i++) {
					int rand = (int) ( Math.random() * 4 + 1);
					if(possibleDirection[i] == true) {
						num += 1;
						if(rand == 1 || num == 3) {
							if(i == 0) {
								direction = "UP";
							}
							if(i == 1) {
								direction = "DOWN";
							}
							if(i == 2) {
								direction = "LEFT";
							}
							if(i == 3) {
								direction = "RIGHT";
							}
						}
					}
				}
			}
			
			currentMove = direction;
		}
		
		if(direction == "UP") {
			this.up();
		}
		if(direction == "DOWN") {
			this.down();
		}
		if(direction == "LEFT") {
			this.left();
		}
		if(direction == "RIGHT") {
			this.right();
		}
		
		this.move();
		this.updateTile();
	}
	
	//methods
	//this method will locate pac man's location and chases him by finding the shortest route
	private void goToTarget(int pTileX, int pTileY, int[][] walls) {
		String direction = currentMove;
		
		if(this.isIntersection(walls) == true || this.collision(walls, currentMove) == true) {
			direction = this.newDirection(pTileX, pTileY, walls);
			currentMove = direction;
		}
		
		if(direction == "UP") {
			this.up();
		}
		if(direction == "DOWN") {
			this.down();
		}
		if(direction == "LEFT") {
			this.left();
		}
		if(direction == "RIGHT") {
			this.right();
		}
		
		this.move();
		this.updateTile();
	}
	
	public boolean collision(int[][] walls, String dir) {
		if(dir.equals("UP")) {
			if(walls[tileY-1][tileX] == 0) {
				return true;
			}
		}
		if(dir.equals("DOWN")) {
			if(walls[tileY+1][tileX] == 0) {
				return true;
			}
		}
		if(dir.equals("LEFT")) {
			if(walls[tileY][tileX-1] == 0) {
				return true;
			}
		}
		if(dir.equals("RIGHT")) {
			if(walls[tileY][tileX+1] == 0) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isIntersection(int[][] walls) {
		int counter = 0;
		
		if(Math.abs(tileX*30 - xPos) == 0 && Math.abs(tileY*30 - yPos) == 0) {
			if(walls[tileY-1][tileX] == 1 && currentMove.equals("DOWN") == false) {
				counter++;
			}
			if(walls[tileY+1][tileX] == 1 && currentMove.equals("UP") == false) {
				counter++;
			}
			if(walls[tileY][tileX-1] == 1 && currentMove.equals("RIGHT") == false) {
				counter++;
			}
			if(walls[tileY][tileX+1] == 1 && currentMove.equals("LEFT") == false) {
				counter++;
			}
		}
		
		if(counter > 1) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String newDirection(int targetTileX, int targetTileY, int[][] walls) {
		//0 = up, 1 = down, 2 = left, 3 = right
		boolean[] possibleDirection = new boolean[4];
		int counter = 0;
		
		if(currentMove.equals("BEGIN") == false) {
			if(walls[tileY-1][tileX] == 1 && currentMove.equals("DOWN") == false) {
				possibleDirection[0] = true;
				counter++;
			}
			else {
				possibleDirection[0] = false;
			}
			
			if(walls[tileY+1][tileX] == 1 && currentMove.equals("UP") == false) {
				possibleDirection[1] = true;
				counter++;
			}
			else {
				possibleDirection[1] = false;
			}
			
			if(walls[tileY][tileX-1] == 1 && currentMove.equals("RIGHT") == false) {
				possibleDirection[2] = true;
				counter++;
			}
			else {
				possibleDirection[2] = false;
			}
			
			if(walls[tileY][tileX+1] == 1 && currentMove.equals("LEFT") == false) {
				possibleDirection[3] = true;
				counter++;
			}
			else {
				possibleDirection[3] = false;
			}
		}
		else {
			if(walls[tileY-1][tileX] == 1) {
				possibleDirection[0] = true;
				counter++;
			}
			else {
				possibleDirection[0] = false;
			}
			
			if(walls[tileY+1][tileX] == 1) {
				possibleDirection[1] = true;
				counter++;
			}
			else {
				possibleDirection[1] = false;
			}
			
			if(walls[tileY][tileX-1] == 1) {
				possibleDirection[2] = true;
				counter++;
			}
			else {
				possibleDirection[2] = false;
			}
			
			if(walls[tileY][tileX+1] == 1) {
				possibleDirection[3] = true;
				counter++;
			}
			else {
				possibleDirection[3] = false;
			}
		}

		if(counter == 1){
			for(int i = 0; i < possibleDirection.length; i++) {
				if(possibleDirection[i] == true) {
					if(i == 0) {
						return "UP";
					}
					if(i == 1) {
						return "DOWN";
					}
					if(i == 2) {
						return "LEFT";
					}
					if(i == 3) {
						return "RIGHT";
					}
				}
			}
		}
		else {
			int dx = targetTileX - tileX;
			int dy = targetTileY - tileY;
			
			if(Math.abs(dx) > Math.abs(dy)) {
				if(dx >= 0 && possibleDirection[3] == true) {
					return "RIGHT";
				}
				else if(dx < 0 && possibleDirection[2] == true) {
					return "LEFT";
				}
				else if(possibleDirection[0] == true) {
					return "UP";
				}
				else {
					return "DOWN";
				}
			}
			else {
				if(dy >= 0 && possibleDirection[1] == true) {
					return "DOWN";
				}
				else if(dy < 0 && possibleDirection[0] == true) {
					return "UP";
				}
				else if(possibleDirection[2] == true) {
					return "LEFT";
				}
				else {
					return "RIGHT";
				}
			}
		}
		return "ERROR";
	}
	
	//movement 
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
	
	//this method updates ghost's tile on the layout
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
	
	//modifier
	public void setState(String s) {
		state = s;
	}
	
	public void setActive(boolean a) {
		active = a;
	}
	
	
	//accessor
	public Color getFrightenColor() {
		return frightenColor;
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
	
	public String getState() {
		return state;
	}
	
	public Color getColor() {
		return color;
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
	
	public void setTileX(int x) {
		tileX = x;
	}
	
	public void setTileY(int y) {
		tileY = y;
	}
	
	public void setCurrentMove(String m) {
		currentMove = m;
	}
}
