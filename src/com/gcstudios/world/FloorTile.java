package com.gcstudios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class FloorTile extends Tile{

	public FloorTile(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
	}
	
	public void render(Graphics g) {
		if(World.Time == World.morning) {
			g.drawImage(Tile.TILE_Morning, x - Camera.x, y - Camera.y, null);
		}else if(World.Time == World.afternoon) {
			g.drawImage(Tile.TILE_Afternoon, x - Camera.x, y - Camera.y, null);
		}
		else if(World.Time == World.night) {
			g.drawImage(Tile.TILE_Night, x - Camera.x, y - Camera.y, null);
		}
	}

}
