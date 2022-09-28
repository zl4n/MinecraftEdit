package com.gcstudios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JFrame;


import com.gcstudios.entities.Entity;
import com.gcstudios.entities.Player;
import com.gcstudios.graficos.Spritesheet;
import com.gcstudios.graficos.UI;
import com.gcstudios.world.World;

public class Game extends Canvas implements Runnable,KeyListener,MouseListener,MouseMotionListener{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;
	
	private BufferedImage image;
	
	public static World world;
	public static List<Entity> entities;
	public static Spritesheet spritesheet;
	public static Player player;
	
	public static Invetory invetory;
	
	public static EnemySpawn enemySpawn;

	public UI ui;
	
	public static String gameState ="MENU";
	
	private int CUR_LEVEL =1, MAX_LEVEL = 3;
	
	private boolean showMessageGameOver = true;
	
	private boolean restartGame = false;
	
	private int framesGameOver = 0;
	
	public Menu menu;
	
	public Game(){
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
		//Inicializando objetos.
		spritesheet = new Spritesheet("/spritesheet.png");
		entities = new ArrayList<Entity>();
		player = new Player(16,16,16,16,0.95,Entity.player_Right[0]);
		world = new World();
		ui = new UI();
		invetory = new Invetory();
		enemySpawn = new EnemySpawn();
		entities.add(player);
		
		menu = new Menu();
	}
	
	public void initFrame(){
		frame = new JFrame("Minecraft");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop(){
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		Game game = new Game();
		game.start();
	}
	
	public void tick(){
		
		if(gameState == "NORMAL") {
			
			this.restartGame = false;
			
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
			
			
			}else if(gameState == "GAME_OVER") {
				this.framesGameOver++;
				if(this.framesGameOver == 30) {
					this.framesGameOver = 0;
					if(this.showMessageGameOver)
						this.showMessageGameOver = false;
						else
							this.showMessageGameOver = true;
				}
				
				if(restartGame) {
					this.restartGame = false;
					this.gameState = "NORMAL";
					CUR_LEVEL = 1;
					String newWorld = "level"+CUR_LEVEL+".png";
					//System.out.println(newWorld);
					World.restartGame(newWorld);
				}
			}else if(gameState == "MENU") {
				player.updateCamera();
				menu.tick();
			}
		

		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}
		ui.tick();
		invetory.tick();
		enemySpawn.tick();
	}
	


	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(122,102,255));
		g.fillRect(0, 0,WIDTH,HEIGHT);
		
		/*Renderiza��o do jogo*/
		//Graphics2D g2 = (Graphics2D) g;
		world.render(g);
		Collections.sort(entities,Entity.nodeSorter);
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
		/***/
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,WIDTH*SCALE,HEIGHT*SCALE,null);
		ui.render(g);
		invetory.render(g);
		
		if(gameState == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,100));
			g2.fillRect(0	,0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setFont(new Font("arial",Font.BOLD,36));
			g.setColor(Color.white);
			g.drawString("Game Over", (WIDTH*SCALE) /2 - 50, (HEIGHT*SCALE) /2 - 20 );
			g.setFont(new Font("arial",Font.BOLD,32));
			if(showMessageGameOver)
			g.drawString(">Pressione Enter para reiniciar<", (WIDTH*SCALE) /2 - 200, (HEIGHT*SCALE) /2 + 40 );
			}else if(gameState =="MENU") {
			menu.render(g);
	}
		
		
		
		bs.show();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning){
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000){
				System.out.println("FPS: "+ frames);
				frames = 0;
				timer+=1000;
			}
			
		}
		
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) { 
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.jump = true;
					 if(gameState =="MENU") {
							menu.up =true;
					}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_DOWN ) {
						if(gameState =="MENU") {
							menu.down =true;
					}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_Z) {
			player.attack = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.restartGame = true;
			if(gameState == "MENU") {
					menu.enter = true;
			}
		}	
		
		if(e.getKeyCode() == KeyEvent.VK_P) {
				gameState = "MENU";
				menu.pause = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {	
		
		if(e.getButton() == MouseEvent.BUTTON1) {
			
			invetory.isPressed = true;
			
		}else if(e.getButton() == MouseEvent.BUTTON3) {
			
			invetory.isPlaceItem = true;
		}

		
		
				invetory.mX = e.getX();
				invetory.mY = e.getY();
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	
	}

	
}
