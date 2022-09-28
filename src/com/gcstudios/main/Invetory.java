package com.gcstudios.main;

import java.awt.Color;
import java.awt.Graphics;

import com.gcstudios.world.Camera;
import com.gcstudios.world.FloorTile;
import com.gcstudios.world.Tile;
import com.gcstudios.world.WallTile;
import com.gcstudios.world.World;

public class Invetory {
	
	public int selected = 0;
	
	public boolean isPressed = false;
	
	public boolean isPlaceItem = false;
	
	public int mX,mY;
	
	public int invetoryBoxSize = 50;
	
	public String[] items = {"grama","terra","neve","areia","ar",""};
	
	public int initialPosition = ((Game.WIDTH * Game.SCALE) / 2) - ((items.length*invetoryBoxSize)/2);
	
	
	public void tick() {
		if(isPressed) {
			isPressed = false;
				if(mX >= initialPosition && mX < initialPosition + (invetoryBoxSize*items.length)) {
						if(mY >= Game.HEIGHT* Game.SCALE-invetoryBoxSize -1 && mY < Game.HEIGHT* Game.SCALE-invetoryBoxSize - 1 + invetoryBoxSize) {
							selected = (int) (mX - initialPosition)/invetoryBoxSize;
						}
				}
		   }
		        
		        if(isPlaceItem) {
		        	isPlaceItem = false;
		        	
		        	mX = (int)mX/3 + Camera.x;
		        	mY = (int)mY/3 + Camera.y;
		        	
		        	int tileX = mX/16;
		        	int tileY = mY/16;
		        	
		        	if(World.tiles[tileX+tileY*World.WIDTH].solid == false) {
		        		if(items[selected] == "grama") {
		        			World.tiles[tileX+tileY*World.WIDTH] = new WallTile(tileX*16,tileY*16,Tile.TILE_Grama);
		        		}else if(items[selected] == "terra") {
		        			World.tiles[tileX+tileY*World.WIDTH] = new WallTile(tileX*16,tileY*16,Tile.TILE_Terra);
		        		}else if(items[selected] == "neve") {
		        			World.tiles[tileX+tileY*World.WIDTH] = new WallTile(tileX*16,tileY*16,Tile.TILE_Neve);
		        		}else if(items[selected] == "areia") {
		        			World.tiles[tileX+tileY*World.WIDTH] = new WallTile(tileX*16,tileY*16,Tile.TILE_Areia);
		        		}else if(items[selected] == "ar") {
		        			World.tiles[tileX+tileY*World.WIDTH] = new FloorTile(tileX*16,tileY*16,Tile.TILE_Ar);
		        		}
		        		
		        		if(World.isFree(Game.player.getX(), Game.player.getY()) == false) {
		        			World.tiles[tileX+tileY*World.WIDTH] = new FloorTile(tileX*16,tileY*16,Tile.TILE_Ar);
		        		}
		         }   	
		   }
	}
	
	public void render (Graphics g) {
		for(int i = 0; i < items.length; i++) {
			g.setColor(Color.gray);
			g.fillRect(initialPosition + (i*invetoryBoxSize) +1,Game.HEIGHT*Game.SCALE-invetoryBoxSize -1, invetoryBoxSize,invetoryBoxSize);
			g.setColor(Color.black);
			g.drawRect(initialPosition + (i*invetoryBoxSize) +1,Game.HEIGHT*Game.SCALE-invetoryBoxSize -1, invetoryBoxSize,invetoryBoxSize);
			
			if(items [i] == "grama") {
				g.drawImage(Tile.TILE_Grama, initialPosition + (i*invetoryBoxSize) + 10,Game.HEIGHT*Game.SCALE-invetoryBoxSize + 10, 32 , 32, null);
				
			}else if(items [i] == "terra") {
				g.drawImage(Tile.TILE_Terra, initialPosition + (i*invetoryBoxSize) + 10,Game.HEIGHT*Game.SCALE-invetoryBoxSize + 10, 32 , 32, null);
				
			}else if(items [i] == "neve") {
				g.drawImage(Tile.TILE_Neve, initialPosition + (i*invetoryBoxSize) + 10,Game.HEIGHT*Game.SCALE-invetoryBoxSize + 10, 32 , 32, null);
				
			}else if(items [i] == "areia") {
				g.drawImage(Tile.TILE_Areia, initialPosition + (i*invetoryBoxSize) + 10,Game.HEIGHT*Game.SCALE-invetoryBoxSize + 10, 32 , 32, null);
				
			}else if(items [i] == "ar") {
				g.drawImage(Tile.TILE_Ar, initialPosition + (i*invetoryBoxSize) + 10,Game.HEIGHT*Game.SCALE-invetoryBoxSize + 10, 32 , 32, null);
			}
			
			if(selected == i) {
				g.setColor(Color.red);
				g.drawRect(initialPosition + (i*invetoryBoxSize),Game.HEIGHT*Game.SCALE-invetoryBoxSize -1, invetoryBoxSize,invetoryBoxSize);
			}
			
		}
	}

}
