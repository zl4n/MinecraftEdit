package com.gcstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;
import com.gcstudios.world.FloorTile;
import com.gcstudios.world.Tile;
import com.gcstudios.world.WallTile;
import com.gcstudios.world.World;

public class Enemy extends Entity{
	
	public boolean right = true,left = false;
	
	public double vida    = Entity.rand.nextInt(200-60)+60;
	public double maxVida = vida;
	
	public int dir = 1;
	
	public BufferedImage [] sprite1,sprite2;
	
	private int framesAnimation = 0;
	private int maxFrame        = 15;
	private int maxSprites      = 2;
	private int curSprites      = 0;
	
	public int right_dir = 1, left_dir= -1;
	

	public Enemy(double x, double y, int width, int height, double speed, BufferedImage [] sprite1, BufferedImage[] sprite2) {
		super(x, y, width, height, speed, null);
		
		this.sprite1 = sprite1;
		this.sprite2 = sprite2;
	}
	
	public void tick() {
		if(World.isFree((int)x,(int)(y+1))) {
			y+=1;
		}
		
			if(dir == 1) {
				if(World.isFree((int)(x+speed),(int)y)) {
					x+=speed;
				}else {
					
					int Xnext = (int)((x+speed)/16) +1;
					int Ynext = (int)(y/16);
						if(World.tiles[Xnext+Ynext*World.WIDTH] instanceof WallTile && World.tiles[Xnext+Ynext*World.WIDTH].solid == false) {
							World.tiles[Xnext+Ynext*World.WIDTH] = new FloorTile((Xnext)*16,Ynext*16,Tile.TILE_Ar);
						}
					dir   = -1;
					left  = true;
					right = false;
				}
			}else if (dir == -1) {
				if(World.isFree((int)(x-speed),(int)y)) {
					x-=speed;
				}else {
					
					int Xnext = (int)((x-speed)/16);
					int Ynext = (int)(y/16);
						if(World.tiles[Xnext+Ynext*World.WIDTH] instanceof WallTile && World.tiles[Xnext+Ynext*World.WIDTH].solid == false) {
							World.tiles[Xnext+Ynext*World.WIDTH] = new FloorTile((Xnext)*16,Ynext*16,Tile.TILE_Ar);
						}
					dir   = 1;
					right = true;
					left  = false;
				}
			}
			
			if(vida == 0) {
				Game.entities.remove(this);
				return;
			}
	}
	
		public void render(Graphics g){
			framesAnimation++;
			 if(framesAnimation == maxFrame) {
				 curSprites++;
					framesAnimation = 0;
						if(curSprites == maxSprites) {
							curSprites = 0;
						}
				}
					if(dir == right_dir) {
						
						sprite = Entity.ENEMY_Right[curSprites];
						
					}else if(dir == left_dir) {
					
						sprite = Entity.ENEMY_Left[curSprites];
					}
		
					super.render(g);
					
					int curlife = (int)((vida/maxVida) * 30);
					
					g.setColor(Color.red);
					g.fillRect(this.getX() - 4 - Camera.x, this.getY() - 8 - Camera.y, 30, 7);
					
					g.setColor(Color.green);
					g.fillRect(this.getX() - 4 - Camera.x, this.getY() - 8 - Camera.y, curlife, 7);
	}
	
}
