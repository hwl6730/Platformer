package com.harry.game.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import com.harry.game.main.GamePanel;

public class MenuState extends GameState{

	private String[] options = {"Start", "Help", "Quit"};
	private int currentSelection = 0;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {}

	public void tick() {}

	public void draw(Graphics g) {

		g.setColor(new Color(50, 10, 100));;
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		for( int i = 0; i < options.length; i++ ){

			if( i == currentSelection ){

				g.setColor(Color.WHITE);

			}
			else{

				g.setColor(Color.BLACK);

			}

			g.setFont(new Font("Arial", Font.PLAIN, 72));
			g.drawString(options[i], GamePanel.WIDTH / 2 - 75, 100 + i * 150);

		}
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
				
			}
			else if( currentSelection == 2 ){
				System.exit(0);
			}
		}
	}

	public void keyReleased(int k) {
	
	}

}
