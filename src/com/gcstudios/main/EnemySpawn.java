package com.gcstudios.main;

import com.gcstudios.entities.Enemy;
import com.gcstudios.entities.Entity;
import com.gcstudios.world.World;

public class EnemySpawn {

	public int interval = 60*10;
	public int curTime = 0;
	
	public void tick () {
		curTime++;
			if(curTime == interval) {
				curTime = 0;
				int xInitial = Entity.rand.nextInt((World.WIDTH/2) * 16 - 16 - 16) +16;
				Enemy enemy = new Enemy(xInitial,16,16,16,0.95,Entity.ENEMY_Right,Entity.ENEMY_Left);
				Game.entities.add(enemy);
			}
			
		
	}
}
