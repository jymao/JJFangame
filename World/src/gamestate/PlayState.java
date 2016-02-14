package gamestate;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import entity.*;

public class PlayState extends GameState{

	//fields
	private BufferedImage bg;
	private BufferedImage bg2;
	
	private boolean timeStop;
	private boolean firstCycle;
	private long timer1;
	private long timer2;
	private long timer3;
	private long stopPeriod;
	private long downTime;
	private int timeCount;
	private boolean enemyDone; //done with one cycle of enemy animation
	private final long ENEMY_DELAY = 450;
	private final long PLAYER_DELAY = 100;
	
	private static final int STOP = 6;
	private static final int BREAK = 5;
	private static final int MAXHP = 100;
	private static final int MAXBARLENGTH = 500;
	private static final int DAMAGE = 5;
	
	private Player player;
	private Enemy enemy;
	
	private Color fontColor;
	private Font font;
	private Font font2;
	
	private int wave;
	private int numKnives;
	private int leftBorder;
	private int rightBorder;
	private int topBorder;
	private int bottomBorder;
	
	private ArrayList<Knife> knives;
	
	public static int score;
	
	public PlayState(GameStateManager gsm) {
		this.gsm = gsm;
		player = new Player(-80,300);
		enemy = new Enemy(800, 300);
		
		fontColor = Color.WHITE;
		font = new Font("Cambria", Font.PLAIN, 48);
		font2 = new Font("Cambria", Font.PLAIN, 36);
		init();
		readImages();
	}

	@Override
	public void init() {
		
		player.reset();
		enemy.reset();
		
		
		timeStop = false;
		firstCycle = true;
		timer1 = System.nanoTime();
		timer2 = System.nanoTime();
		timer3 = System.nanoTime();
		
		timeCount = 0;
		stopPeriod = STOP;
		downTime = BREAK;
		
		wave = 1;
		numKnives = 6;
		
		//area knives can spawn in
		leftBorder = 400;
		rightBorder = 800;
		topBorder = 400;
		bottomBorder = 700;
		
		knives = new ArrayList<Knife>();
		
		score = 0;
		
		System.out.println("INIT");
		
	}
	
	public void readImages(){
		try{
			
			BufferedImage bgBefore = ImageIO.read(getClass().getResourceAsStream("/egypt_bridge.png"));
			BufferedImage bg2Before = ImageIO.read(getClass().getResourceAsStream("/egypt_bridge_invert.png"));
			
			int w = bgBefore.getWidth();
			int h = bgBefore.getHeight();
			double scaleW = (double)GamePanel.WIDTH/(double)w;
			double scaleH = (double)GamePanel.HEIGHT/(double)h;
			bg = new BufferedImage((int)(w * scaleW), (int)(h * scaleH), BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(scaleW, scaleH);
			AffineTransformOp scaleOp = 
			   new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			bg = scaleOp.filter(bgBefore, bg);
			
			bg2 = new BufferedImage((int)(w * scaleW), (int)(h * scaleH), BufferedImage.TYPE_INT_ARGB);
			bg2 = scaleOp.filter(bg2Before, bg2);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		
		long elapsed_Short = (System.nanoTime() - timer1) / 1000000;
		long elapsed_1Sec = (System.nanoTime() - timer2) / 1000000;
		long elapsed_player = (System.nanoTime() - timer3) / 1000000;
		
		//update player stand
		if(elapsed_player >= PLAYER_DELAY){
			if(player.getStatus() == Player.NEUTRAL_STATE){
				player.update(Player.PUNCH1_STATE);
			}
			else if(player.getStatus() == Player.PUNCH1_STATE){
				player.update(Player.PUNCH2_STATE);
			}
			else if(player.getStatus() == Player.PUNCH2_STATE){
				player.update(Player.PUNCH1_STATE);
			}
			
			timer3 = System.nanoTime();
		}
		
		//update enemy
		if(elapsed_Short >= ENEMY_DELAY){
			if(enemy.getStatus() == Enemy.THROW_STATE && enemyDone == false){
				enemy.update(Enemy.NEUTRAL_STATE);
				enemyDone = true;
			}
			else if(enemy.getStatus() == Enemy.READY_STATE && enemyDone == false){
				enemy.update(Enemy.THROW_STATE);
			}
			else if(timeStop && enemy.getStatus() == Enemy.NEUTRAL_STATE && enemyDone == false){
				enemy.update(Enemy.READY_STATE);
			}
			
			timer1 = System.nanoTime();
		}
		
		if(elapsed_1Sec >= 1000){
			timeCount++;
			System.out.println("Time is: " + timeCount);
			
			if(downTime == 0 && stopPeriod == 0){
				
				downTime = BREAK;
				stopPeriod = STOP;
				timeStop = false;
				firstCycle = false;
				enemyDone = false;
				player.resetStand();
				enemy.reset();
				wave++;
				
				if(wave % 2 == 0){
					numKnives++;
				}
				
				System.out.println("Next wave: " + wave);
				System.out.println("Number of knives: " + numKnives);
			}
			
			if(downTime > 0){
				downTime--;
			}
			else{
				timeStop = true;
				stopPeriod--;
			}
			
			if(timeStop && stopPeriod == 4){
				Random rand = new Random();
				
				for(int i = 0; i < numKnives; i++){
					int x = rand.nextInt(401) + 400; //random x coord btwn 400 and 800
					int y = rand.nextInt(301) + 400; //random y coord btwn 400 and 700
					
					Knife knife = new Knife(x,y);
					knives.add(knife);
				}
				
				System.out.println("Current knives: " + knives.size());
			}
			
			timer2 = System.nanoTime();
		}
		
		//move stand upon appearance
		if(stopPeriod > 4 && timeStop){
			enemy.updateStand();
		}
		if(downTime >= 3 && !timeStop){
			player.updateStand();
		}
		
		if(!timeStop){
			for(int i = 0; i < knives.size(); i++){
				
				if(knives.get(i).getHit()){
					knives.remove(i);
					i--;
					continue;
				}
				
				knives.get(i).move();
				if(knives.get(i).getX() <= player.getRightCollision()){
					if(!knives.get(i).getSlashed()){
						player.damaged(DAMAGE);
						player.setHit(true);
						knives.get(i).setSlashed(true);
					}
				}
				if(knives.get(i).outOfBounds()){
					knives.remove(i);
					i--;
				}
			}
		}
		
		if(knives.size() == 0){
			player.setHit(false);
		}
	
	}

	@Override
	public void draw(Graphics2D g) {
			
		if(!timeStop){
			g.drawImage(bg, 0, 0, null);
			
			if(downTime > 3 && !firstCycle){
				player.drawStand(g);
			}
		}
		else{
			g.drawImage(bg2, 0, 0, null);
			
			enemy.drawStand(g);
			
			//draw timer
			g.setFont(font);
			g.drawString("Time Stop: 0:0" + stopPeriod, 780, 930);
		}
		
		//score
		g.setFont(font);
		g.drawString("Score: " + score, 40, 930);
		
		//draw hp bar
		double scale = (double) player.getHP() / (double) MAXHP;
		int length = (int)(MAXBARLENGTH * scale);
		
		g.setColor(Color.BLACK);
		g.fillRect(30, 30, MAXBARLENGTH, 50);
		g.setColor(Color.RED);
		g.fillRect(30, 30, length, 50);
		
		g.setColor(fontColor);
		g.setFont(font2);
		g.drawString("HP", 40, 65);
		
		player.draw(g);
		enemy.draw(g);
		
		//draw knives
		for(int i = 0; i < knives.size(); i++){
			knives.get(i).draw(g);
		}
		
		//game over when no hp left
		if(player.getHP() <= 0){
			gsm.setState(gsm.GAMEOVERSTATE);
		}
		
	}

	@Override
	public void keyPressed(int k) {
		
		//testing hp bar
		if(k == KeyEvent.VK_X){
			player.damaged(5);
		}
		if(k == KeyEvent.VK_ESCAPE){
			gsm.setState(gsm.PAUSESTATE);
		}
		
	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(int button, Point loc) {
		
	}
	
	@Override
	public void mouseMotion(Point loc){
		
	}
	
	@Override
	public void mouseReleased(int button, Point loc){
		
		if(button == MouseEvent.BUTTON1){
			if(timeStop){
				for(int i = 0; i < knives.size(); i++){
					if(knives.get(i).contains(loc.x, loc.y)){
						if(!knives.get(i).getHit()){
							score += 10;
							knives.get(i).setHit(true);
						}
					}
				}
			}
		}
		
	}
	
}
