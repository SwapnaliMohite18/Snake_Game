import java.awt.*; 
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;//contains components and utilities for building GUIs 
import java.util.Random;
//imports all classes and interfaces from the java.awt package, including subpackages if any.
//import java.awt.event.ActionEvent;// It represent when a user performs an action, such as clicking a button or selecting a menu item, in GUI application.
//import java.awt.event.ActionListener;//It allows developers to create interactive applications where components respond to user actions
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;

import javax.swing.JPanel;//components, allowing you to organize and arrange them according to your application's needs.

public class GamePanel extends JPanel implements ActionListener{
	
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNIT = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;//for screen grid total
	static final int DELAY = 100;//for snake speed means when more delay then slow speed of snake and vice-versa
	final int x[] = new int[GAME_UNIT];//it holds all the coordinates of snake body parts including head .array size = Game_unit
	final int y[] = new int[GAME_UNIT];//it holds all the coordinates of snake body parts 
	int bodyParts = 6;//at the start snake has 6 length of body
	int appleEaten;
	int appleX;
	int appleY;
	char direction = 'R';//snake begin by going right 
	boolean running = false;
	Timer timer;
	Random random;//instance of random class
	int HighScore;
	
    
	GamePanel(){
		
    	random = new Random();
    	this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
    	this.setBackground(Color.black);
    	this.setFocusable(true);
    	this.addKeyListener(new MyKeyAdapter());
    	
    	try {
			BufferedReader reader = new BufferedReader(new FileReader("HighScore.txt"));
			//System.out.println("High Score:"+reader.readLine());
			HighScore = Integer.parseInt(reader.readLine());
			reader.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        startGame();
    	
	}
	
    public void startGame() {
    	
    	newApple();
    	running = true;
    	//DELAY specifies the delay, in milliseconds, before the timer's action event is fired for the first time.
    	//this indicates ActionListener that specifies the object that will handle the timer's action events
    	timer = new Timer(DELAY,this);
    	timer.start();
		
    }
    
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
     	draw(g);
		
	}
    public void draw(Graphics g) {
    	
    	//if block for gameOver Screen
      if(running) {
    		
	    	//loop for draw lines(grid) across screen
    		/*
	    	for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
	    		
	    		g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
	    		g.drawLine(0,i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
	    	}*/
    		
    		
	    	//for fill apple using newApple()
	    	g.setColor(Color.red);
	    	g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
	    	
	    	
	    	//snake body and head using move()
	    	for(int i =0; i< bodyParts; i++) {
	    		if(i==0){
	    			g.setColor(Color.green);
	    			g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
	    		}
	    		else {
	    			g.setColor(new Color(45,180,0));//for single color of snake
	    			//g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));//for multicolor snake
	    			
	    			g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
	    		}
	    	}
	    	
	    	
	    	//Current Score text
	    	 g.setColor(Color.green);
	    	 g.setFont(new Font("Ink Free",Font.BOLD,40));
	    	 FontMetrics metrics = getFontMetrics(g.getFont());
	    	 
	    	 //Score value
	    	 g.drawString("Score:"+appleEaten, (SCREEN_WIDTH - metrics.stringWidth("Score:"+appleEaten))/2, g.getFont().getSize());
	    	 
    	}
      else {
    	   	
    	   gameOver(g);
    	   
    	}
    }
    
    
    //position of apple
    public void newApple() {
    	
    	appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
    	appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    	
     }
    
    public void move(){
    	
    	//iterate all the body parts of snake
    	for(int i = bodyParts; i>0;i--) {
    		x[i] = x[i-1];
    		y[i] = y[i-1];
    	}
    	
    	//examine direction
    	switch(direction) {
	    	case 'U':
	    		y[0] = y[0] - UNIT_SIZE;
	    		break;
	    	case 'D':
	    		y[0] = y[0] + UNIT_SIZE;
	    		break;
	    	case 'L':
	    		x[0] = x[0] - UNIT_SIZE;
	    		break;
	    	case 'R':
	    		x[0] = x[0] + UNIT_SIZE;
	    		break;
    		
    	}
    	
    }
    
    //Snake eats apple
    public void checkApple() {
    	
    	//x[0] & y[0] are snake positions 
    	if((x[0] == appleX) && (y[0] ==appleY)) {
    		bodyParts++;
    		appleEaten++;
    		newApple();
    	}
    	
    }
    
    //its for Snake Accident 
    public void checkCollisions() {
    	
    	//checks if head collides with body
    	for(int i = bodyParts;i>0;i--) {
    		if((x[0] == x[i]) && (y[0] == y[i])) {
    			running = false;
    		}
    	}
    	//check if head touches left border
    	if(x[0] < 0) {
    		running = false;
    	}
    	//check if head touches right border
    	if(x[0] > SCREEN_WIDTH) {
    		running = false;
    	}
    	//check if head touches Top border
    	if(y[0] < 0) {
    		running = false;
    	}
    	//check if head touches bottom border
    	if(y[0] > SCREEN_HEIGHT) {
    		running = false;
    	}
    	//stop timer
    	if(!running) {
    		timer.stop();
    	}
    	
    }
    
    
    public void gameOver(Graphics g) {
    	
         //game over text
    	 g.setColor(Color.red);
    	 g.setFont(new Font("Ink Free",Font.BOLD,75));
    	 FontMetrics metrics = getFontMetrics(g.getFont());
    	 g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2 );
    	 
    	 //score text
    	 g.setColor(Color.green);
    	 g.setFont(new Font("Ink Free",Font.BOLD,40));
    	 g.drawString("Score:"+appleEaten, (SCREEN_WIDTH - metrics.stringWidth("Score:"+appleEaten))/2, g.getFont().getSize()+(SCREEN_HEIGHT/2));

    	 //for display High Score
    	 g.drawString("High Score:"+HighScore, (SCREEN_WIDTH - metrics.stringWidth("High Score:"+HighScore))/2, g.getFont().getSize());
    	 if(appleEaten > HighScore) {
    		try {
				BufferedWriter writer = new BufferedWriter(new FileWriter("HighScore.txt"));
				writer.write(String.valueOf(appleEaten));
				writer.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
    		
    	 }
    	
    }
    
    
	@Override
	public void actionPerformed(ActionEvent e) {
		//call move function
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
		
	}
	
	//Inner Class ("KeyAdapter" for receiving keyboard events)
	public class MyKeyAdapter extends KeyAdapter{
		
		 @Override
	   //method is where you define the behavior you want to occur when a key is pressed. 
	   public void keyPressed(KeyEvent e) {
			 
		   // appears to be part of a game or application where the user can control a character's movement using the keyboard.
		   switch(e.getKeyCode()) {
		      case KeyEvent.VK_LEFT:
				   if(direction != 'R') {
					   direction = 'L';
				   }
				   break;
			   case KeyEvent.VK_RIGHT:
				   if(direction != 'L') {
					   direction = 'R';
				   }
				   break;
			   case KeyEvent.VK_UP:
				   if(direction != 'D') {
					   direction = 'U';
				   }
				   break;
			   case KeyEvent.VK_DOWN:
				   if(direction != 'U') {
					   direction = 'D';
				   }
				   break;
			   
		   }
		   
	   }
		
	}
	

}
