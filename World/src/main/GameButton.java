package main;

public class GameButton {

	//fields
	private int x;
	private int y;
	private int width;
	private int height;
	private String text;
	private boolean selected;
	
	public GameButton(int x, int y, int width, int height, String text){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		
		selected = false;
	}
	
	public int getX(){ return x;}
	public int getY(){ return y;}
	public int getWidth(){ return width;}
	public int getHeight(){ return height;}
	public String getText(){ return text;}
	public boolean getSelect(){ return selected;}
	public void setSelect(boolean b){ selected = b;}
	
	public boolean contains(int px, int py){
		
		if(px >= x && px <= x + width && py >= y && py <= y + height){
			return true;
		}
		
		return false;
	}
	
}
