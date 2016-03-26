package com.harry.game.physics;

import java.awt.Point;

import com.harry.game.objects.Block;
import com.harry.game.objects.MovingBlock;

public class Collision {

	public static boolean playerBlock(Point p, Block b){
		return b.contains(p);
	}
	
	public static boolean playerMovingBlock(Point p, MovingBlock b){
		return b.contains(p);
	}
	
	public static boolean enemyBlock(Point p, Block b){
		return b.contains(p);
	}
	
	public static boolean healthBlock(Point p, Block b){
		return b.contains(p);
	}
}
