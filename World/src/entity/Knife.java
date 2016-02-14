package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Knife {

	private int x;
	private int y;
	private int width;
	private int height;
	private boolean hit;
	private boolean slashed;
	
	private BufferedImage image;
	private BufferedImage selected;
	
	public Knife(int x, int y){
		this.x = x;
		this.y = y;
		hit = false;
		slashed = false;
		init();
	}
	
	public void init() {
		
		try{
			BufferedImage imageBefore = ImageIO.read(getClass().getResourceAsStream("/knife.png"));
			BufferedImage selectBefore = ImageIO.read(getClass().getResourceAsStream("/knifehit.png"));
			
			int w = imageBefore.getWidth();
			int h = imageBefore.getHeight();
			double scale = 0.65;
			
			image = new BufferedImage((int)(w*scale), (int)(h*scale), BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(scale, scale);
			AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			image = scaleOp.filter(imageBefore, image);
			
			selected = new BufferedImage((int)(w*scale), (int)(h*scale), BufferedImage.TYPE_INT_ARGB);
			selected = scaleOp.filter(selectBefore, selected);
			
			width = image.getWidth();
			height = image.getHeight();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public int getX() {return x;}
	public int getY() {return y;}
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	public boolean getHit() {return hit;}
	public boolean getSlashed() {return slashed;}
	
	public void setHit(boolean hit){
		this.hit = hit;
	}
	
	public void setSlashed(boolean slash){
		slashed = slash;
	}
	
	public void draw(Graphics2D g){
		
		if(!hit){
			g.drawImage(image, x, y, null);
		}
		else{
			g.drawImage(selected, x, y, null);
		}
		//g.setColor(Color.WHITE);
		//g.drawRect(x, y, width, height);
	}
	
	public void move(){
		x -= 20;
	}
	
	public void updatePosition(int x) {
		this.x = x;
	}
	
	public boolean outOfBounds(){
		if(x < 0){
			return true;
		}
		return false;
	}
	
	public boolean contains(int x, int y){
		if(x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height){
			return true;
		}
		return false;
	}
}
