package com.harry.game.mapping;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.harry.game.characters.Enemy1;
import com.harry.game.objects.Block;
import com.harry.game.objects.Health;
import com.harry.game.objects.MovingBlock;

public class Map {

	private String path;
	private String line;
	public int width, height;
	
	private Block[][] blocks;
	private ArrayList<MovingBlock> movingBlocks;
	public ArrayList<Enemy1> enemies;
	public ArrayList<Health> health = new ArrayList<Health>();
	
	public Map(String loadPath){
		path = loadPath;
		
		blocks = new Block[height][width];
		
		loadMap();
	}
	
	public void tick(){
		for(int i = 0; i < movingBlocks.size(); i++){
			movingBlocks.get(i).tick();
		}
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).tick(blocks);
		}
		for(int i = 0; i < health.size(); i++){
			health.get(i).tick(blocks);
		}
	}
	
	public void draw(Graphics g) throws IOException{
		for(int i = 0; i < blocks.length; i++){
			for(int j = 0; j < blocks[0].length; j++){
				blocks[i][j].draw(g);
			}
		}
		for(int i = 0; i < movingBlocks.size(); i++){
			movingBlocks.get(i).draw(g);
		}
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).draw(g);
		}
		if(health.size() != 0){
			for(int i = 0; i < health.size(); i++){
				health.get(i).draw(g);
			}
		}
	}
	
	public Block[][] getBlocks(){
		return blocks;
	}
	
	public ArrayList<MovingBlock> getMovingBlocks(){
		return movingBlocks;
	}
	
	public ArrayList<Enemy1> getEnemies(){
		return enemies;
	}
	
	public void addHealth(Health health){
		this.health.add(health);
	}
	
	public void loadMap(){
		InputStream is = this.getClass().getResourceAsStream(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		try {
			width = Integer.parseInt(br.readLine());
			height = Integer.parseInt(br.readLine());
			
			blocks = new Block[height][width];
			
			for(int y = 0; y < height; y++){
				
				line = br.readLine();

				String[] tokens = line.split("\\s+");

				for(int x = 0; x < width; x++){

					blocks[y][x] = new Block( x * Block.blockSize, y * Block.blockSize, Integer.parseInt(tokens[x]));
				}
			}
			
			line = br.readLine();
			line = br.readLine();
			int length = Integer.parseInt(line);
			movingBlocks = new ArrayList<MovingBlock>();
			
			for(int i = 0; i < length; i++){
				line = br.readLine();
				String[] tokens = line.split("\\s+");
				
				movingBlocks.add(new MovingBlock(Integer.parseInt(tokens[0]) * Block.blockSize,
						Integer.parseInt(tokens[1]) * Block.blockSize, Integer.parseInt(tokens[2]),
						Integer.parseInt(tokens[3]) * Block.blockSize, Integer.parseInt(tokens[4]) * Block.blockSize)) ;
			}
			
			line = br.readLine();
			line = br.readLine();
			int enemyNum = Integer.parseInt(line);
			enemies = new ArrayList<Enemy1>();
			
			for(int i = 0; i < enemyNum; i++){
				line = br.readLine();
				String[] tokens = line.split("\\s+");
				
				enemies.add(new Enemy1(Integer.parseInt(tokens[0]) * Block.blockSize));
			}
			
		} catch(NumberFormatException | IOException r){
			r.printStackTrace();
		}
	}
}
