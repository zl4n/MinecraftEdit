package com.gcstudios.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu {
	
	public String[] options = {"Novo jogo","Sair"};
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	
	public boolean up,down,enter;
	
	public boolean pause = false;
	
	
	public void tick() {
		if(up) {
			up = false;
			currentOption--;
			if(currentOption < 0)
				currentOption = maxOption;
		}
		if(down) {
			down = false;
			currentOption++;
			if(currentOption > maxOption)
				currentOption = 0;
		}
		if(enter) {
			enter = false;
			if(options[currentOption] == "Novo jogo" || options[currentOption] == "Continuar") {
				Game.gameState = "NORMAL";
				pause = false;
			}else if(options[currentOption] == "Sair") {
				System.exit(1);
			}
		}
	}
	
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial",Font.BOLD,40));
		
		g.drawString("Minecraft Edit", (Game.WIDTH*Game.SCALE) / 2 - 123, (Game.HEIGHT*Game.SCALE) / 2 - 150);
		
		g.setColor(Color.white);
		g.setFont(new Font("arial",Font.BOLD,24));
		if(pause == false)
			g.drawString("Novo jogo", (Game.WIDTH*Game.SCALE) / 2 - 50, 160);
		else
		g.drawString("Continuar", (Game.WIDTH*Game.SCALE) / 2 - 40, 160);
		g.drawString("Sair", (Game.WIDTH*Game.SCALE) / 2 - 10, 200);
		
		if(options[currentOption] == "Novo jogo") {
			g.drawString(">", (Game.WIDTH*Game.SCALE) / 2 - 90, 160);
		}else if(options[currentOption] == "Sair") {
			g.drawString(">", (Game.WIDTH*Game.SCALE) / 2 - 90, 200);
		}
	}
	
}
