package com.harry.game.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.harry.game.gamestate.GameState;
import com.harry.game.resources.Images;

public class Block extends Rectangle{

	private static final long serialVersionUID = 1L;
	public static final int blockSize = 32;
	
	private int id;
	
	public Block( int x, int y , int id){
		setBounds(x, y, blockSize, blockSize);
		this.id = id;
	}
	
	public void tick(){
		
	}
	
	public void draw(Graphics g){
		g.setColor(Color.DARK_GRAY);
		if(id != 0){
			g.drawImage(Images.blocks[id-1], x - (int)GameState.xOffset, y - (int)GameState.yOffset, width, height, null);
		}
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getID(){
		return id;
	}
}
