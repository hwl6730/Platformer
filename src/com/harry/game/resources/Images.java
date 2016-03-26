package com.harry.game.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Images {

	public static BufferedImage[] blocks;
	public static BufferedImage[] items;
	
	public Images(){
		blocks = new BufferedImage[3];
		try {
			blocks[0] = ImageIO.read(getClass().getResourceAsStream("/Blocks/block_brick.png"));
			blocks[1] = ImageIO.read(getClass().getResourceAsStream("/Blocks/block_item.png"));
			blocks[2] = ImageIO.read(getClass().getResourceAsStream("/Blocks/block_item_empty.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		items = new BufferedImage[2];
		try{
			items[0] = ImageIO.read(getClass().getResourceAsStream("/Items/Health.png"));
			items[1] = ImageIO.read(getClass().getResourceAsStream("/Items/Star.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
}
