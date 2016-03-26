package com.harry.game.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

import com.harry.game.main.GamePanel;

public class GameOver_01 extends GameState{
	
	private String gameOver = "GAME OVER";
	private String[] options = {"Retry", "Menu"};

	private int currentSelection = 0;
	
	public GameOver_01(GameStateManager gsm){
		super(gsm);
	}

	public void init() {}

	public void tick() {}

	public void draw(Graphics g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, 128));
		g.drawString(gameOver, GamePanel.WIDTH / 2 - 375, GamePanel.HEIGHT / 2 - 100);
		
		//display options
		for( int i = 0; i < options.length; i++ ){

			if( i == currentSelection ){

				g.setColor(Color.YELLOW);

			}
			else{

				g.setColor(Color.WHITE);

			}

			g.setFont(new Font("Arial", Font.PLAIN, 72));
			g.drawString(options[i], GamePanel.WIDTH / 2 - 75, GamePanel.HEIGHT / 2 + i * 100);

		}
		
		
		//gsm.states.push(new Level1_State(gsm));
	}

	public void keyPressed(int k) {
		
		if( k == KeyEvent.VK_DOWN){
			currentSelection++;
			if(currentSelection >= options.length){
				currentSelection = 0;
			}
		}
		else if( k == KeyEvent.VK_UP){
			currentSelection--;
			if(currentSelection < 0){
				currentSelection = options.length - 1;
			}
		}

		if( k == KeyEvent.VK_ENTER ){
			if( currentSelection == 0 ){
				gsm.states.push( new Level1_State(gsm));
			}
			else if( currentSelection == 1 ){
				gsm.states.push(new MenuState(gsm));
			}
		}
	}

	public void keyReleased(int k) {}
}
