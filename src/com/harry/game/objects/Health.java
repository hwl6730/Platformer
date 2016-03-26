package com.harry.game.objects;

import java.awt.Graphics;
import java.awt.Point;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.harry.game.gamestate.GameState;
import com.harry.game.physics.Collision;
import com.harry.game.resources.Images;

public class Health {
	
	private int width = 25;
	private int height = 25;
	private double xOffset = 0;
	private double moveSpeed = 1.25;
	
	private int life = 1;
	
	private double maxFallSpeed = 5;
	private double currentFallSpeed = 0.2;

	public double x, y, checkX;
	
	private boolean right = true, left = false, fall = false;
	private boolean topCollision = false;
	
	public Health(double x, double y){
		this.x = x;
		this.y = y;
		checkX = x;
	}
	
	public void tick(Block[][] b){
		int iX = (int)checkX;
		int iY = (int)y;
		
		System.out.println("Health x is: " + x);
		System.out.println("Health checkX is: " + checkX);
		
		for( int i = 0; i < b.length; i++){
			for(int j = 0; j < b[0].length; j++){
				
				if(b[i][j].getID() != 0){
			
					//right
					if(Collision.healthBlock(new Point(iX + width + (int)GameState.xOffset, iY + (int)GameState.yOffset + 2), b[i][j]) 
							|| Collision.healthBlock(new Point(iX + width + (int)GameState.xOffset, iY + height + (int)GameState.yOffset - 1), b[i][j])){
						right = false;
						left = true;
						moveSpeed *= -1;
					}
			
					//left
					if(Collision.healthBlock(new Point(iX + (int)GameState.xOffset - 1, iY + (int)GameState.yOffset + 2), b[i][j])
							|| Collision.healthBlock(new Point(iX + (int)GameState.xOffset - 1, iY + height + (int)GameState.yOffset - 1), b[i][j])){
						left = false;
						right = true;
						moveSpeed *= -1;
					}
			
					//top
					if(Collision.healthBlock((new Point(iX + (int)GameState.xOffset + 1, iY + (int)GameState.yOffset)), b[i][j])
							|| Collision.healthBlock((new Point(iX + width + (int)GameState.xOffset - 2, iY + (int)GameState.yOffset)), b[i][j])){
						fall = true;
					}
			
					//bottom
					if(Collision.healthBlock(new Point(iX + (int)GameState.xOffset + 2, iY + height + (int)GameState.yOffset + 1), b[i][j])
							|| Collision.healthBlock(new Point(iX + width + (int)GameState.xOffset - 2, iY + height + (int)GameState.yOffset + 1), b[i][j])){
						y = b[i][j].getY() - height - GameState.yOffset;
						fall = false;
						topCollision = true;
					}
					else{
						if(!topCollision){
							fall = true;
						}
					}
				}
			}
		}
		
		//movement
		x += moveSpeed;
		checkX += moveSpeed;
		
		
		topCollision = false;
		
		if(fall){
			y += currentFallSpeed;
			
			if(currentFallSpeed < maxFallSpeed){
				currentFallSpeed += 0.2;
			}
		}
		if(!fall){
			currentFallSpeed = 0.1;
		}
	}
	
	public void draw(Graphics g) throws IOException{
		if(life == 1){
			g.drawImage(Images.items[0], (int)x - (int)xOffset, (int)y, width, height, null);
		}
		else{
			
		}
	}
	
	public double getXOffset(){
		return this.xOffset;
	}
	
	public void setXOffset(double offset){
		this.xOffset = offset;
	}
	
	public int getLife(){
		return life;
	}
	
	public void reduceLife(){
		life -= 1;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
}
