package com.harry.game.characters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.harry.game.gamestate.GameState;
import com.harry.game.main.GamePanel;
import com.harry.game.mapping.Map;
import com.harry.game.objects.Block;
import com.harry.game.physics.Collision;

public class Enemy1{
	
	public double x, y, checkX;
	
	private int width = 25;
	private int height = 25;
	
	Map map;
	
	private int life = 1;
	
	public double move = -1.25;
	
	//fall
	private double maxFallSpeed = 5;
	private double currentFallSpeed = 0.2;
		
	public boolean right = false, left = true, fall = false, jump = false;
	private boolean topCollision = false;

	public Enemy1(int x){
		this.x = x;
		this.y = GamePanel.HEIGHT - (Block.blockSize * 3);
		checkX = x;
	}
	
	public void tick(Block[][] b){
		
		int iX = (int)checkX;
		int iY = (int)y;
		
		
		for( int i = 0; i < b.length; i++){
			for(int j = 0; j < b[0].length; j++){
				
				if(b[i][j].getID() != 0){
			
					//right
					if(Collision.enemyBlock(new Point(iX + width + (int)GameState.xOffset, iY + (int)GameState.yOffset + 2), b[i][j]) 
							|| Collision.enemyBlock(new Point(iX + width + (int)GameState.xOffset, iY + height + (int)GameState.yOffset - 1), b[i][j])){
						right = false;
						left = true;
						move *= -1;
					}
			
					//left
					if(Collision.enemyBlock(new Point(iX + (int)GameState.xOffset - 1, iY + (int)GameState.yOffset + 2), b[i][j])
							|| Collision.enemyBlock(new Point(iX + (int)GameState.xOffset - 1, iY + height + (int)GameState.yOffset - 1), b[i][j])){
						left = false;
						right = true;
						move *= -1;
					}
			
					//top
					if(Collision.enemyBlock((new Point(iX + (int)GameState.xOffset + 1, iY + (int)GameState.yOffset)), b[i][j])
							|| Collision.enemyBlock((new Point(iX + width + (int)GameState.xOffset - 2, iY + (int)GameState.yOffset)), b[i][j])){
						jump = false;
						fall = true;
					}
			
					//bottom
					if(Collision.enemyBlock(new Point(iX + (int)GameState.xOffset + 2, iY + height + (int)GameState.yOffset + 1), b[i][j])
							|| Collision.enemyBlock(new Point(iX + width + (int)GameState.xOffset - 2, iY + height + (int)GameState.yOffset + 1), b[i][j])){
						y = b[i][j].getY() - height - GameState.yOffset;
						fall = false;
						topCollision = true;
					}
					else{
						if(!topCollision && !jump){
							fall = true;
						}
					}
				}
			}
		}
		
		//movement
		x += move;
		checkX += move;
		
		
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
	
	public void draw(Graphics g){
		g.setColor(Color.RED);
		if(life == 1){
			g.fillRect((int)x - (int)GameState.xOffset, (int)y, width, height);
		}
		else{
			
		}
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getLife(){
		return life;
	}
	
	public void reduceLife(){
		life -= 1;
	}
}
