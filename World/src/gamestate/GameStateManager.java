package gamestate;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class GameStateManager {

	private ArrayList<GameState> gameStates;
	private int currentState;
	
	public static final int MENUSTATE = 0;
	public static final int PLAYSTATE = 1;
	public static final int PAUSESTATE = 2;
	public static final int GAMEOVERSTATE = 3;
	public static final int TUTORIALSTATE = 4;
	
	public GameStateManager(){
		
		gameStates = new ArrayList<GameState>();
		
		gameStates.add(new MenuState(this));
		gameStates.add(new PlayState(this));
		gameStates.add(new PauseState(this));
		gameStates.add(new GameOverState(this));
		gameStates.add(new TutorialState(this));
		
		setState(MENUSTATE);
	}
	
	public void setState(int state){
		currentState = state;
		//gameStates.get(currentState).init();
	}
	
	public GameState getState(int state){
		return gameStates.get(state);
	}
	
	public void update(){
		gameStates.get(currentState).update();
	}
	
	public void draw(Graphics2D g){
		gameStates.get(currentState).draw(g);
	}
	
	public void keyPressed(int k){
		gameStates.get(currentState).keyPressed(k);
	}
	
	public void keyReleased(int k){
		gameStates.get(currentState).keyReleased(k);
	}
	
	public void mouseClicked(int button, Point loc){
		gameStates.get(currentState).mouseClicked(button, loc);
	}
	
	public void mouseMotion(Point loc){
		gameStates.get(currentState).mouseMotion(loc);
	}
	
	public void mouseReleased(int button, Point loc){
		gameStates.get(currentState).mouseReleased(button, loc);
	}
	
}
