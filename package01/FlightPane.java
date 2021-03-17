package package01;

import java.util.LinkedList;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class FlightPane extends Pane {
	private static final int WIDTH = 600;
	private static final int HEIGHT = 600;
	public Bird bird;
	public boolean gameStart = false;
	
	private LinkedList<Rectangle> topWalls = new LinkedList<Rectangle>();
	private LinkedList<Rectangle> bottomWalls = new LinkedList<Rectangle>();
	
	public FlightPane() {
		this.setPrefWidth(WIDTH);
		this.setPrefHeight(HEIGHT);
		this.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
		
		bird = new Bird();
		this.getChildren().add(bird);
		bird.setLayoutX(10);
		bird.setLayoutY(300);
		
		generateWall(250);
		generateWall(500);
		generateWall(750);
		
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(50), e -> {
		  if(gameStart == true) {	
			bird.setLayoutY(bird.getLayoutY() + 5);
			BabyBirdGame.updateScore();
			for(Rectangle topWall : topWalls) {
			  topWall.setLayoutX(topWall.getLayoutX() - 5);
			  
			  if(bird.getBoundsInParent().intersects(topWall.getBoundsInParent())) {
				  gameStart = false;
				  }
				}
			for(Rectangle bottomWall : bottomWalls) {
			  bottomWall.setLayoutX(bottomWall.getLayoutX() - 5);
			  if(bird.getBoundsInParent().intersects(bottomWall.getBoundsInParent())) {
				  gameStart = false;
				  }
			  
				}
			
			// Check if bird goes beyond pane height
			if(bird.getLayoutY() > HEIGHT - bird.getHeight() || bird.getLayoutY() < 0) {
				gameStart = false;
			}
			
			// This will check if the left most walls goes beyond the left of the pane
			if(topWalls.getFirst().getBoundsInParent().getMaxX() < 0) {
				Rectangle topWall = topWalls.removeFirst();
				Rectangle bottomWall = bottomWalls.removeFirst();
				
				this.getChildren().removeAll(topWall, bottomWall);
				generateWall(topWalls.getLast().getBoundsInParent().getCenterX()
						+ 250);
		  }
			  if(gameStart == false) {
				  BabyBirdGame.initGUI();
			  }
		  }
		}));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();
		
	

	}
	
	public void moveBirdUp() {
		bird.setLayoutY(bird.getLayoutY() - 50);
	}
	
	private Random random = new Random();
	
	public void generateWall(double x) {
		int topHeight = random.nextInt(250) + 100;
		int bottomHeight = HEIGHT - topHeight - 150;
		Rectangle topWall = new Rectangle(x, 0, 50, topHeight);
		Rectangle bottomWall = new Rectangle(x, HEIGHT - bottomHeight, 50,
		bottomHeight);
		this.getChildren().addAll(topWall, bottomWall);
		topWalls.add(topWall); // this will add to the end of the LinkedList
		bottomWalls.add(bottomWall); // this will add to the end of the LinkedList
		
	}
	
	
	
	
}
	


