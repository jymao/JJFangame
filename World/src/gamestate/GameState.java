package gamestate;

import java.awt.*;

public abstract class GameState {

	protected GameStateManager gsm;
	
	public abstract void init();
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	public abstract void mouseClicked(int button, Point loc);
	public abstract void mouseMotion(Point loc);
	public abstract void mouseReleased(int button, Point loc);
}
