package com.gcstudios.world;

import java.awt.Graphics;

import com.gcstudios.entities.Entity;
import com.gcstudios.entities.Player;
import com.gcstudios.graficos.Spritesheet;
import com.gcstudios.main.Game;

public class World {

	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static final int TILE_SIZE = 16;
	
	public static int morning   = 0;
	public static int afternoon = 1;
	public static int night     = 2;
	public static int Time = Entity.rand.nextInt(3);
	
	
	public World(){
		String[] tilesTypes = {"grama","terra","areia","neve"};
		WIDTH = 1000;
		HEIGHT = 200;
		//Divisor do mapa
		int divisao = WIDTH/tilesTypes.length;
		tiles = new Tile[WIDTH*HEIGHT];
		for(int xx = 0; xx < WIDTH; xx++) {
			int initialHeight = Entity.rand.nextInt(12 - 8) + 8;
			for(int yy = 0; yy < HEIGHT; yy++) {
				if(yy == HEIGHT - 1 || xx == WIDTH -1 || xx == 0 || yy == 0) {
					tiles[xx+yy*WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_Rock);
					tiles[xx+yy*WIDTH].solid = true;
				}else {
					if(yy >= initialHeight) {
						int indexBioma = xx / divisao;
						if(tilesTypes[indexBioma] == "grama") {
							tiles[xx+yy*WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_Grama);
						}else if(tilesTypes[indexBioma] == "terra"){
							tiles[xx+yy*WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_Terra);
						}else if(tilesTypes[indexBioma] == "areia"){
							tiles[xx+yy*WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_Areia);
						}else if(tilesTypes[indexBioma] == "neve"){
							tiles[xx+yy*WIDTH] = new WallTile(xx*16,yy*16,Tile.TILE_Neve);
						}
					}else {
						tiles[xx+yy*WIDTH] = new FloorTile(xx*16,yy*16,Tile.TILE_Ar);
					}
				}
			}
		}
	}
	
	public static boolean isFree(int xnext,int ynext){
		
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));
	}
	

	
	public static void restartGame(String level) {
		
		Game.entities.clear();
		Game.spritesheet =  new Spritesheet("/spritesheet.png");
		Game.player  = new Player(16,16,16,16,0.95,Entity.player_Right[0]);
		Game.entities.add(Game.player);
		Game.world = new World();
		
		return;
	}
	
	public void render(Graphics g){
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
	
}
