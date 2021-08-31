package Pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Draw 
extends JPanel
implements ActionListener, KeyListener{
	//start the timer
	//set to 20 milli second per draw
	private Timer t = new Timer(20, this);
	private Map bg;
	private Pacman player;
	
	private Ghost blinky;
	private Ghost pinky;
	private Ghost inky;
	private Ghost clyde;
	
	private ArrayList<Ghost> ghosts;
	
	private ArrayList<food> foods;
	private ArrayList<PowerFood> powerFoods;
	
	private long time; // this variable records time for ghost
	private long totalTime;
	private long frightenTime;
	private long finishedTime;
	
	private int end;
	
	private boolean blinkyStarted;
	private boolean	pinkyStarted;
	private boolean inkyStarted;
	private boolean clydeStarted;
	
	public Draw(Map background, Pacman p, Ghost a, Ghost b, Ghost c, Ghost d, int w, int h) {
		t.start();
		
		time = System.nanoTime();
		totalTime = System.nanoTime();
		frightenTime = -999;
		finishedTime = -999;
		
		end = 0;
		
		blinkyStarted = false;
		pinkyStarted = false;
		inkyStarted = false;
		clydeStarted = false;
		
		bg = background;
		player = p;
		
		blinky = a;
		pinky = b;
		inky = c;
		clyde = d;
		
		ghosts = new ArrayList<Ghost>();
		ghosts.add(blinky);
		ghosts.add(pinky);
		ghosts.add(inky);
		ghosts.add(clyde);
		
		foods = new ArrayList<food>();
		powerFoods = new ArrayList<PowerFood>();
		
		int[][] layout = bg.getLayout();
		for(int y = 0; y < layout.length; y++) {
			for(int x = 0; x < layout[0].length; x++) {
				if(layout[y][x] == 1) {
					if(x == 1 && y == 3) {
						powerFoods.add(new PowerFood(1, 3));
					}
					else if(x == 3 && y == 15) {
						powerFoods.add(new PowerFood(3, 15));
					}
					else if(x == 15 && y == 1) {
						powerFoods.add(new PowerFood(15, 1));
					}
					else if(x == 17 && y == 16) {
						powerFoods.add(new PowerFood(17, 16));
					}
					else {
						foods.add(new food(x, y));
					}
				}
			}
		}
		
		//add a key listener for key detection 
		this.addKeyListener(this);
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
	}
	
	//This methods demonstrate what to draw
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//change Graphics to 2d Graphics
		Graphics2D g2d = (Graphics2D) g;
		
		//paint background first
		g2d.setColor(Color.BLACK);
		g2d.fill(bg.getBackground());
		g2d.setColor(Color.BLUE);
		g2d.draw(bg.getBackground());
		
		//paint walls
		g2d.setColor(Color.BLUE);
		int[][] layout = bg.getLayout();
		
		for(int y = 0; y < layout.length; y++) {
			for(int x = 0; x < layout[0].length; x++) {
				if(layout[y][x] == 0) {
					g2d.fill(new Rectangle(x*30, y*30, 30, 30));
				}
			}
		}
		
		//paint food
		g2d.setColor(Color.WHITE);
		for(food i : foods) {
			if(i.getActive() == true) {
				g2d.fillOval(i.getXPos(), i.getYPos(), 3, 3);
			}
		}
		
		for(PowerFood i : powerFoods) {
			if(i.getActive() == true) {
				g2d.fillOval(i.getXPos(), i.getYPos(), 10, 10);
			}
		}
		
		//paint ghosts
		if(blinky.getState() == "FRIGHTEN") {
			g2d.setColor(blinky.getFrightenColor());
		}
		else {
			g2d.setColor(blinky.getColor());
		}
		g2d.fillRect(blinky.getXPos(), blinky.getYPos(), blinky.getWidth(), blinky.getHeight());
		
		if(pinky.getState() == "FRIGHTEN") {
			g2d.setColor(blinky.getFrightenColor());
		}
		else {
			g2d.setColor(pinky.getColor());
		}
		g2d.fillRect(pinky.getXPos(), pinky.getYPos(), pinky.getWidth(), pinky.getHeight());
		
		if(inky.getState() == "FRIGHTEN") {
			g2d.setColor(blinky.getFrightenColor());
		}
		else {
			g2d.setColor(inky.getColor());
		}
		g2d.fillRect(inky.getXPos(), inky.getYPos(), inky.getWidth(), inky.getHeight());
		
		if(clyde.getState() == "FRIGHTEN") {
			g2d.setColor(blinky.getFrightenColor());
		}
		else {
			g2d.setColor(clyde.getColor());
		}
		g2d.fillRect(clyde.getXPos(), clyde.getYPos(), clyde.getWidth(), clyde.getHeight());
		
		//paint Pacman
		g2d.setColor(Color.YELLOW);
		g2d.fillOval(player.getXPos(), player.getYPos(), player.getWidth(), player.getHeight());
		
		//display score
		g2d.setColor(Color.WHITE);
		g2d.drawString("Food Eaten: " + String.valueOf(player.getFoodEaten()), 10, 20);
		
		//display time
		long difference = (System.nanoTime() - totalTime) / 1000000000;
		g2d.drawString("Time: " + String.valueOf(difference), 150, 20);
		
		if(end == 1) {
			g2d.setColor(Color.BLACK);
			g2d.fillRect(0, 0, 700, 700);
			
			g2d.setColor(Color.WHITE);
			g2d.drawString("お前わ死んでいる", 250, 250);
		}
		if(end == 2) {
			g2d.setColor(Color.BLACK);
			g2d.fillRect(0, 0, 700, 700);
			
			if(finishedTime == -999) {
				finishedTime = difference;
			}
			g2d.setColor(Color.WHITE);
			g2d.drawString("Your Time: " + finishedTime, 250, 250);
		}
	}

	//call redraw and update every 5 milli second
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	
		this.startGhost();
		
		this.updatePacman();
		this.updateGhost();
		this.updateFood();
		this.updatePowerFood();
		
		if(this.endGame() == 1) {
			end = 1;
		}
		if(this.endGame() == 2) {
			end = 2;
		}
	}
	
	private void startGhost() {
		long endTime = System.nanoTime();
		long difference = (endTime - time) / 1000000;
		
		if(difference > 0 && blinkyStarted == false) {
			blinky.setActive(true);
			blinkyStarted = true;
		}
		
		if(difference > 5000 && pinkyStarted == false) {
			pinky.setActive(true);
			pinky.setTileX(9);
			pinky.setTileY(7);
			pinky.setXPos(9 * 30);
			pinky.setYPos(7 * 30);
			pinkyStarted = true;
		}
		
		if(difference > 10000 && inkyStarted == false) {
			inky.setActive(true);
			inky.setTileX(9);
			inky.setTileY(7);
			inky.setXPos(9 * 30);
			inky.setYPos(7 * 30);
			inkyStarted = true;
		}
		
		if(difference > 15000 && clydeStarted == false) {
			clyde.setActive(true);
			clyde.setTileX(9);
			clyde.setTileY(7);
			clyde.setXPos(9 * 30);
			clyde.setYPos(7 * 30);
			clydeStarted = true;
		}
	}
	
	private void updateGhost() {
		long endTime = System.nanoTime();
		long difference = (endTime - time) / 1000000;
		
		if(difference > 10000) {
			for(Ghost i : ghosts) {
				if(i.getActive() == true) {
					i.setState("CHASE");
				}
			}
		}
		
		if(difference > 30000) {
			for(Ghost i : ghosts) {
				if(i.getActive() == true) {
					i.setState("SCATTER");
				}
			}
			
			time = System.nanoTime();
		}
		
		for(Ghost i : ghosts) {
			
			if(i.getState().equals("ESCAPE")) {
				if(i.getTileX() == 9 && i.getTileY() == 7) {
					i.setActive(true);
					i.setState("CHASE");
				}
			}
			
			if(i.getActive() == true) {
				if(i.getState().equals("CHASE")) {
					i.chase(player.getTileX(), player.getTileY(), bg.getLayout());
				}
				if(i.getState().equals("SCATTER")) {
					i.scatter(bg.getLayout());
				}
			}
			else {
				if(i.getState().equals("FRIGHTEN")) {
					i.frighten(bg.getLayout());
				}
				if(i.getState().equals("ESCAPE")) {
					i.escape(bg.getLayout());
				}
			}
		}
	}
	
	private void updateFood() {
		for(food i : foods) {
			if(i.getActive() == true && i.getTileX() == player.getTileX() && i.getTileY() == player.getTileY()) {
				i.setActive(false);
				player.eat();
			}
		}
	}
	
	private void updatePowerFood() {
		if(frightenTime != -999) {
			long difference = (System.nanoTime() - frightenTime) / 1000000;
			
			if(difference > 10000) {
				frightenTime = -999;
				for(Ghost g : ghosts) {
					g.setState("CHASE");
					g.chase(player.getTileX(), player.getTileY(), bg.getLayout());
					g.setActive(true);
				}
			}
		}
		
		for(PowerFood i : powerFoods) {
			if(i.getActive() == true && i.getTileX() == player.getTileX() && i.getTileY() == player.getTileY()) {
				i.setActive(false);
				frightenTime = System.nanoTime();
				for(Ghost g : ghosts) {
					if(g.getActive() == true) {
						g.setState("FRIGHTEN");
						g.frighten(bg.getLayout());
						g.setActive(false);
					}
				}
			}
		}
	}
	
	private int endGame() {
		boolean done = true;
		for(food i : foods) {
			if(i.getActive() == true) {
				done = false;
			}
		}
		
		if(done == true) {
			return 2;
		}
		
		for(Ghost i : ghosts) {
			if(player.getTileX() == i.getTileX() && player.getTileY() == i.getTileY()) {
				if(i.getState().equals("FRIGHTEN")) {
					i.setState("ESCAPE");
					i.escape(bg.getLayout());
				}
				if(i.getState().equals("ESCAPE") == false){
					return 1;
				}
			}
		}
		return 0;
	}
	
	//this method updates the graphics by checking collisions
	//this is going to be hard XD
	//the idea is that pac man will constantly check for collision in the upward, rightward, downward,
	//and leftward direction depending on which key is pressed
	//once the key is pressed it will continue in its current path until the new direction will not result
	//in a collision
	private void updatePacman() {
		if(player.wallCollisionDetection(bg.getLayout(), player.getCurrentMove()) == true){
			if(player.wallCollisionDetection(bg.getLayout(), player.getNextMove()) == false) {
				if(player.getNextMove().equals("UP")) {
					player.up();
				}
				if(player.getNextMove().equals("DOWN")) {
					player.down();
				}
				if(player.getNextMove().equals("LEFT")) {
					player.left();
				}
				if(player.getNextMove().equals("RIGHT")) {
					player.right();
				}
				
				player.setCurrentMove(player.getNextMove());
				player.move();
			}
		}
		else {
			if(player.wallCollisionDetection(bg.getLayout(), player.getNextMove()) == false) {
				if(player.getNextMove().equals("UP")) {
					player.up();
				}
				if(player.getNextMove().equals("DOWN")) {
					player.down();
				}
				if(player.getNextMove().equals("LEFT")) {
					player.left();
				}
				if(player.getNextMove().equals("RIGHT")) {
					player.right();
				}
				
				player.setCurrentMove(player.getNextMove());
				player.move();
			}
			else {
				player.move();
			}
		}
		
		player.updateTile();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {	} // Not Needed

	@Override
	public void keyPressed(KeyEvent e) {
		//retrieve key code for the key that is pressed
		int code = e.getKeyCode();
		
		//check collision first
		//check the key code for appropriate movement
		if(code == KeyEvent.VK_W) {
			if(player.getCurrentMove() == "NULL") {
				player.setCurrentMove("UP");
			}
			
			player.setNextMove("UP");
		}
		if(code == KeyEvent.VK_S) {
			if(player.getCurrentMove() == "NULL") {
				player.setCurrentMove("DOWN");
			}
			
			player.setNextMove("DOWN");
		}
		if(code == KeyEvent.VK_A) {
			if(player.getCurrentMove() == "NULL") {
				player.setCurrentMove("LEFT");
			}
			
			player.setNextMove("LEFT");
		}
		if(code == KeyEvent.VK_D) {
			if(player.getCurrentMove() == "NULL") {
				player.setCurrentMove("RIGHT");
			}
			
			player.setNextMove("RIGHT");
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {	} // Not Needed
	
}
