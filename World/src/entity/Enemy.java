package entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Enemy {

	//fields
	private int x;
	private int y;
	private int width;
	private int height;
	
	private int standX;
	private int standY;
	
	private BufferedImage neutral;
	private BufferedImage ready;
	private BufferedImage throwing;
	private BufferedImage stand;
	
	private int status;
	public static final int NEUTRAL_STATE = 0;
	public static final int READY_STATE = 1;
	public static final int THROW_STATE = 2;
	
	public Enemy(int x, int y){
		this.x = x;
		this.y = y;
		init();
	}
	
	public void init(){
		
		status = NEUTRAL_STATE;
		
		standX = x - 200;
		standY = y - 50;
		
		try{
			
			BufferedImage neutralBefore = ImageIO.read(getClass().getResourceAsStream("/dio_neutral_trans.png"));
			BufferedImage standBefore = ImageIO.read(getClass().getResourceAsStream("/the_world_neutral_trans.png"));
			BufferedImage readyBefore = ImageIO.read(getClass().getResourceAsStream("/dio_ready_trans.png"));
			BufferedImage throwingBefore = ImageIO.read(getClass().getResourceAsStream("/dio_throw_trans.png"));
			
			int w = neutralBefore.getWidth();
			int h = neutralBefore.getHeight();
			double scale = 0.65;
			
			neutral = new BufferedImage((int)(w*scale), (int)(h*scale), BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(scale, scale);
			AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			neutral = scaleOp.filter(neutralBefore, neutral);
			
			stand = new BufferedImage((int)(w*scale), (int)(h*scale), BufferedImage.TYPE_INT_ARGB);
			stand = scaleOp.filter(standBefore, stand);
			
			ready = new BufferedImage((int)(w*scale), (int)(h*scale), BufferedImage.TYPE_INT_ARGB);
			ready = scaleOp.filter(readyBefore, ready);
			
			throwing = new BufferedImage((int)(w*scale), (int)(h*scale), BufferedImage.TYPE_INT_ARGB);
			throwing = scaleOp.filter(throwingBefore, throwing);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void reset(){
		standX = x - 200;
		status = NEUTRAL_STATE;
	}
	
	public void update(int status){
		this.status = status;
	}
	
	public int getStatus(){
		return status;
	}
	
	public void updateStand(){
		
		standX -= 1;
		
	}
	
	public void draw(Graphics2D g){
		
		if(status == NEUTRAL_STATE){
			g.drawImage(neutral, x, y, null);
		}
		else if(status == READY_STATE){
			g.drawImage(ready, x, y, null);
		}
		else if(status == THROW_STATE){
			g.drawImage(throwing, x, y, null);
		}
		
	}
	
	public void drawStand(Graphics2D g){
		g.drawImage(stand, standX, standY, null);
	}
	
}
