package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Player {

	//fields
	private int x;
	private int y;
	private int width;
	private int height;
	private int cx; //collision box location
	private int cy;
	private int standX;
	private int standY;
	
	private BufferedImage neutral;
	private BufferedImage hurt;
	private BufferedImage standNeutral;
	private BufferedImage punch1;
	private BufferedImage punch2;
	
	private int hp;
	private boolean hit;
	
	private int status;
	public static final int NEUTRAL_STATE = 0;
	public static final int PUNCH1_STATE = 1;
	public static final int PUNCH2_STATE = 2;
	
	
	public Player(int x, int y){
		this.x = x;
		this.y = y;
		init();
	}
	
	public void init(){
		
		hp = 100;
		hit = false;
		
		standX = x + 100;
		standY = y - 30;
		
		try{
			
			BufferedImage neutralBefore = ImageIO.read(getClass().getResourceAsStream("/jotaro_neutral_trans.png"));
			BufferedImage hurtBefore = ImageIO.read(getClass().getResourceAsStream("/jotaro_hurt_trans.png"));
			BufferedImage standNBefore = ImageIO.read(getClass().getResourceAsStream("/star_platinum_neutral_trans.png"));
			BufferedImage punch1Before = ImageIO.read(getClass().getResourceAsStream("/star_platinum_punch1.png"));
			BufferedImage punch2Before = ImageIO.read(getClass().getResourceAsStream("/star_platinum_punch2.png"));
			
			int w = neutralBefore.getWidth();
			int h = neutralBefore.getHeight();
			double scale = .65;
			neutral = new BufferedImage((int)(w*scale), (int)(h*scale), BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(scale, scale);
			AffineTransformOp scaleOp = 
			   new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			neutral = scaleOp.filter(neutralBefore, neutral);
			
			hurt = new BufferedImage((int)(w*scale), (int)(h*scale), BufferedImage.TYPE_INT_ARGB);
			hurt = scaleOp.filter(hurtBefore, hurt);
			
			standNeutral = new BufferedImage((int)(w*scale), (int)(h*scale), BufferedImage.TYPE_INT_ARGB);
			standNeutral = scaleOp.filter(standNBefore, standNeutral);
			
			punch1 = new BufferedImage((int)(w*scale), (int)(h*scale), BufferedImage.TYPE_INT_ARGB);
			punch1 = scaleOp.filter(punch1Before, punch1);
			
			punch2 = new BufferedImage((int)(w*scale), (int)(h*scale), BufferedImage.TYPE_INT_ARGB);
			punch2 = scaleOp.filter(punch2Before, punch2);
			
			w = neutral.getWidth();
			h = neutral.getHeight();
			
			width = w - 380;
			height = h - 50;
			cx = 110;
			cy = 350;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void reset(){
		hp = 100;
		hit = false;
		resetStand();
	}
	
	public void resetStand(){
		standX = x + 100;
		status = NEUTRAL_STATE;
	}
	
	public int getHP(){
		return hp;
	}
	
	public int getRightCollision(){
		return cx + width;
	}
	
	public void damaged(int amount){
		hp -= amount;
		if(hp < 0){
			hp = 0;
		}
	}
	
	public void setHit(boolean hit){
		this.hit = hit;
	}
	
	public void update(int status){
		this.status = status;
	}
	
	public int getStatus(){
		return status;
	}
	
	public void updateStand(){
		standX += 3;
	}
	
	public void draw(Graphics2D g){
		
		if(!hit){
			g.drawImage(neutral, x, y, null);
			//g.setColor(Color.WHITE);
			//g.drawRect(cx, cy, width, height);
		}
		else{
			g.drawImage(hurt, x, y, null);
		}
		
	}
	
	public void drawStand(Graphics2D g){
		if(status == NEUTRAL_STATE){
			g.drawImage(standNeutral, standX, standY, null);
		}
		else if(status == PUNCH1_STATE){
			g.drawImage(punch1, standX, standY, null);
		}
		else if(status == PUNCH2_STATE){
			g.drawImage(punch2, standX, standY, null);
		}
		
	}
	
}
