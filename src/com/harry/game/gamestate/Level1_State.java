package com.harry.game.gamestate;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import com.harry.game.characters.Player;
import com.harry.game.main.GamePanel;
import com.harry.game.mapping.Map;
import com.harry.game.objects.Block;

public class Level1_State extends GameState{
	
	private Player player;
	private Map map;

	public Level1_State(GameStateManager gsm){
		super(gsm);
	}

	public void init() {
		map = new Map("/Maps/map1.map");
		player = new Player(25, 25, map);
		
		xOffset = 0;
		yOffset = 0;
	}

	public void tick() {
		player.tick(map.getBlocks(), map.getMovingBlocks(), map.getEnemies());
		map.tick();
	}

	public void draw(Graphics g) {
		g.setColor(new Color(30, 144, 255));
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		player.draw(g);
		try {
			map.draw(g);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(player.y >= GamePanel.HEIGHT || player.life == 0){
			gsm.states.push(new GameOver_01(gsm));
		}
	}

	public void keyPressed(int k) {
		player.keyPressed(k);
	}

	public void keyReleased(int k) {
		player.keyReleased(k);
	}
}
