package com.harry.game.characters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.harry.game.gamestate.GameOver_01;
import com.harry.game.gamestate.GameState;
import com.harry.game.gamestate.GameStateManager;
import com.harry.game.main.GamePanel;
import com.harry.game.mapping.Map;
import com.harry.game.objects.Block;
import com.harry.game.objects.Health;
import com.harry.game.objects.MovingBlock;
import com.harry.game.physics.Collision;

public class Player{
	
	public double x, y, checkX;
	private int width, height;
	
	//life
	public int life = 2;
	private int isStunned = 0;
	private boolean stunned = false;
	
	private Map map;
	
	//jump
	private double jumpSpeed = 7;
	private double currentJumpSpeed = jumpSpeed;
	
	//fall
	private double maxFallSpeed = 5;
	private double currentFallSpeed = 0.2;
	
	//movement
	private double moveSpeed = 2.5;
	
	private boolean right = false, left = false, jump = false, fall = false;
	private boolean topCollision = false;
	
	public Player(int width, int height, Map map){
		x = GamePanel.WIDTH / 2 - 256;
		y = GamePanel.HEIGHT - Block.blockSize * 3;
		checkX = x;
		this.map = map;
		this.width = width;
		this.height = height;
	}
	
	public void tick(Block[][] b, ArrayList<MovingBlock> movingBlocks, ArrayList<Enemy1> enemies1){
		
		int iX = (int)x;
		int iY = (int)y;
		
		
		if(stunned){
			isStunned += 1;
			if(isStunned == 120){
				stunned = false;
				isStunned = 0;
			}
		}
		
		for( int i = 0; i < b.length; i++){
			for(int j = 0; j < b[0].length; j++){
				
				if(b[i][j].getID() != 0){
			
					//right
					if(Collision.playerBlock(new Point(iX + width + (int)GameState.xOffset, iY + (int)GameState.yOffset + 2), b[i][j]) 
							|| Collision.playerBlock(new Point(iX + width + (int)GameState.xOffset, iY + height + (int)GameState.yOffset - 1), b[i][j])){
						right = false;
					}
			
					//left
					if(Collision.playerBlock(new Point(iX + (int)GameState.xOffset - 1, iY + (int)GameState.yOffset + 2), b[i][j])
							|| Collision.playerBlock(new Point(iX + (int)GameState.xOffset - 1, iY + height + (int)GameState.yOffset - 1), b[i][j])){
						left = false;
					}
			
					//top
					if(Collision.playerBlock((new Point(iX + (int)GameState.xOffset + 1, iY + (int)GameState.yOffset)), b[i][j])
							|| Collision.playerBlock((new Point(iX + width + (int)GameState.xOffset - 2, iY + (int)GameState.yOffset)), b[i][j])){
						jump = false;
						currentJumpSpeed = 0;
						fall = true;
						if(b[i][j].getID() == 2){
							b[i][j].setId(3);
							System.out.println("Item Block x is: " + b[i][j].x);
							Health health = new Health(b[i][j].x  - (int)GameState.xOffset + Block.blockSize/2,
									b[i][j].y - (int)GameState.yOffset - Block.blockSize/2);
							map.addHealth(health);
						}
					}
			
					//bottom
					if(Collision.playerBlock(new Point(iX + (int)GameState.xOffset + 2, iY + height + (int)GameState.yOffset + 1), b[i][j])
							|| Collision.playerBlock(new Point(iX + width + (int)GameState.xOffset - 2, iY + height + (int)GameState.yOffset + 1), b[i][j])){
						y = b[i][j].getY() - height - GameState.yOffset;
						fall = false;
						topCollision = true;
						System.out.println("Block y is: " + b[i][j].y);
					}
					else{
						if(!topCollision && !jump){
							fall = true;
						}
					}
				}
			}
		}
		
		for(int i = 0; i < movingBlocks.size(); i++){
			if(movingBlocks.get(i).getID() != 0){
				if(movingBlocks.get(i).getID() != 0){
					
					//right
					if(Collision.playerMovingBlock(new Point(iX + width + (int)GameState.xOffset, iY + (int)GameState.yOffset + 2), movingBlocks.get(i)) 
							|| Collision.playerMovingBlock(new Point(iX + width + (int)GameState.xOffset, iY + height + (int)GameState.yOffset - 1), movingBlocks.get(i))){
						right = false;
					}
			
					//left
					if(Collision.playerMovingBlock(new Point(iX + (int)GameState.xOffset - 1, iY + (int)GameState.yOffset + 2), movingBlocks.get(i))
							|| Collision.playerMovingBlock(new Point(iX + (int)GameState.xOffset - 1, iY + height + (int)GameState.yOffset - 1), movingBlocks.get(i))){
						left = false;
					}
			
					//top
					if(Collision.playerMovingBlock((new Point(iX + (int)GameState.xOffset + 1, iY + (int)GameState.yOffset)), movingBlocks.get(i))
							|| Collision.playerMovingBlock((new Point(iX + width + (int)GameState.xOffset - 2, iY + (int)GameState.yOffset)), movingBlocks.get(i))){
						jump = false;
						fall = true;
					}
			
					//bottom
					if(Collision.playerMovingBlock(new Point(iX + (int)GameState.xOffset + 2, iY + height + (int)GameState.yOffset + 1), movingBlocks.get(i))
							|| Collision.playerMovingBlock(new Point(iX + width + (int)GameState.xOffset - 2, iY + height + (int)GameState.yOffset + 1), movingBlocks.get(i))){
						y = movingBlocks.get(i).getY() - height - GameState.yOffset;
						fall = false;
						topCollision = true;
						
						GameState.xOffset += movingBlocks.get(i).getMove();
						checkX += movingBlocks.get(i).getMove();
					}
					else{
						if(!topCollision && !jump){
							fall = true;
						}
					}
				}
			}
		}
		
		topCollision = false;
		
		//Enemy1 collision
		for(int i = 0; i < enemies1.size(); i++){
			//right
			if(!stunned && enemies1.get(i).getLife() != 0){
				if(checkX + (height/2) >= enemies1.get(i).x && iY== enemies1.get(i).y){
					if(checkX <= enemies1.get(i).x + 25){
						life -= 1;
						stunned = true;
					}
				}
			}
			//left
			if(!stunned && enemies1.get(i).getLife() != 0){
				if(checkX - (height/2) <= enemies1.get(i).x && iY == enemies1.get(i).y){
					if(checkX >= enemies1.get(i).x - 25){
						life -= 1;
						stunned = true;
					}
				}
			}
			//above
			if(fall == true && enemies1.get(i).getLife() != 0){
				if(iY + currentFallSpeed >= enemies1.get(i).y - enemies1.get(i).getHeight()/2){
					if(checkX >= enemies1.get(i).x - enemies1.get(i).getWidth()/2 && checkX <= enemies1.get(i).x + enemies1.get(i).getWidth()/2){
						
						enemies1.get(i).reduceLife();
					}
				}
			}
		}
		
		//Health collision
				for(int i = 0; i < map.health.size(); i++){
					//right
					if(life < 2 && map.health.get(i).getLife() == 1){
						if(checkX + (height/2) >= map.health.get(i).checkX + GameState.xOffset && iY== map.health.get(i).y){
							if(checkX <= map.health.get(i).checkX + 5){
								life += 1;
								map.health.get(i).reduceLife();
							}
						}
					}
					//left
					if( life < 2 && map.health.get(i).getLife() == 1){
						if(checkX - (height/2) <= map.health.get(i).checkX + GameState.xOffset && iY == map.health.get(i).y){
							if(checkX >= map.health.get(i).checkX - 5){
								life += 1;
								map.health.get(i).reduceLife();
							}
						}
					}
					//above
					if(fall == true && map.health.get(i).getLife() != 0 && life < 2){
						if(iY + currentFallSpeed >= map.health.get(i).y - map.health.get(i).getHeight()/2){
							if(checkX >= map.health.get(i).checkX + GameState.xOffset - map.health.get(i).getWidth()/2 && checkX <= map.health.get(i).checkX + GameState.xOffset + map.health.get(i).getWidth()/2){
								life += 1;
								map.health.get(i).reduceLife();
							}
						}
					}
				}
					
		
		if(right){		
			if((checkX + moveSpeed) <= (map.width * Block.blockSize) - 32){
				if(checkX <= ((map.width * Block.blockSize) - (Block.blockSize * 14)) && checkX >= Block.blockSize * 16){
					checkX += moveSpeed;
					GameState.xOffset += moveSpeed;
					for(int i = 0; i < map.enemies.size(); i++){
						map.enemies.get(i).checkX -= moveSpeed;
					}
					for(int i = 0; i < map.health.size(); i++){
						map.health.get(i).checkX -= moveSpeed;
						
						double offset = map.health.get(i).getXOffset();
						offset += moveSpeed;
						map.health.get(i).setXOffset(offset);
					}
				}
				else{
					checkX += moveSpeed;
					x += moveSpeed;
				}
			}
		}
		
		if(left){
			if((checkX - moveSpeed) >= 0){
				if(checkX >= Block.blockSize * 16 && checkX <= ((map.width * Block.blockSize) - (Block.blockSize * 14))){
					checkX -= moveSpeed;
					GameState.xOffset -= moveSpeed;
					for(int i = 0; i < map.enemies.size(); i++){
						map.enemies.get(i).checkX += moveSpeed;
					}
					for(int i = 0; i < map.health.size(); i++){
						map.health.get(i).checkX += moveSpeed;
						double offset = map.health.get(i).getXOffset();
						offset -= moveSpeed;
						map.health.get(i).setXOffset(offset);
					}
				}
				else{
					//checkX = x;
					checkX -= moveSpeed;
					x -= moveSpeed;
				}
			}
		}
		if(jump){
			y -= currentJumpSpeed;
			
			currentJumpSpeed -= .2;
			
			if(currentJumpSpeed <= 0){
				currentJumpSpeed = jumpSpeed;
				jump = false;
			}
		}
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
		if(stunned){
			g.setColor(Color.WHITE);
			g.fillRect((int)x, (int)y, width, height);
		}
		else{
			if(life == 2){
				g.setColor(Color.BLACK);
				g.fillRect((int)x, (int)y, width, height);
			}
			else if(life == 1){
				g.setColor(Color.DARK_GRAY);
				g.fillRect((int)x, (int)y, width, height);
			}
		}
	}
	
	public void keyPressed(int k){
		if(k == KeyEvent.VK_D){
			right = true;
		}
		if(k == KeyEvent.VK_A){
			left = true;
		}
		if(k == KeyEvent.VK_SPACE && !jump && !fall){
			jump = true;
		}
	}
	
	public void keyReleased(int k){
		if(k == KeyEvent.VK_D){
			right = false;
		}
		if(k == KeyEvent.VK_A){
			left = false;
		}
		if(k == KeyEvent.VK_SPACE){
			fall = true;
		}
	}
}
