package gamestate;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import main.GameButton;
import main.GamePanel;

public class GameOverState extends GameState{

	//fields
	private Color color;
	private Color fontColor;
	private Color selectColor;
	private Font font;
	private Font font2;
	
	private ArrayList<GameButton> buttons;
	private final int BUTTONSPACE = 60;
	
	public GameOverState(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	@Override
	public void init() {
		
		buttons = new ArrayList<GameButton>();
		
		color = new Color(100, 30, 30, 5);
		fontColor = Color.WHITE;
		selectColor = Color.RED;
		font = new Font("Cambria", Font.BOLD, 72);
		font2 = new Font("Cambria", Font.PLAIN, 36);
		
		placeButtons();
	}

	public void placeButtons(){
		
		String text = "RETRY";
		Canvas c = new Canvas();
		FontMetrics fm = c.getFontMetrics(font2);
		int width = fm.stringWidth(text);
		int height = fm.getHeight();
		buttons.add(new GameButton(
				(GamePanel.WIDTH / 2) - (width / 2), 
				(GamePanel.HEIGHT / 3) + (height + 20) + BUTTONSPACE, width, height, text));
		
		text = "MENU";
		width = fm.stringWidth(text);
		buttons.add(new GameButton(
				(GamePanel.WIDTH / 2) - (width / 2), 
				(GamePanel.HEIGHT / 3) + (height + 20) + BUTTONSPACE * 2, width, height, text));
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics2D g) {
		
		g.setColor(color);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		g.setColor(fontColor);
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics(font);
		int width = fm.stringWidth("GAME OVER");
		int height = fm.getHeight();
		g.drawString("GAME OVER", (GamePanel.WIDTH / 2) - (width / 2), (GamePanel.HEIGHT / 3) + (height / 2));
		
		width = fm.stringWidth("Score: " + PlayState.score);
		g.drawString("Score: " + PlayState.score, (GamePanel.WIDTH / 2) - (width / 2), (GamePanel.HEIGHT / 3) + height + 30);
		
		g.setFont(font2);
		
		for(int i = 0; i < buttons.size(); i++){
			
			GameButton b = buttons.get(i);
			String text = b.getText();
			int x = b.getX();
			int y = b.getY();
			width = b.getWidth();
			height = b.getHeight();
			
			if(b.getSelect()){
				g.setColor(selectColor);
			}
			else{
				g.setColor(fontColor);
			}
			
			//g.drawRect(x, y, width, height);
			
			//y+height to make sure font placement matches button coords
			g.drawString(text, x, y+height);
		}
	}

	@Override
	public void keyPressed(int k) {
		
	}

	@Override
	public void keyReleased(int k) {
		
	}

	@Override
	public void mouseClicked(int button, Point loc) {
		
		if(button == MouseEvent.BUTTON1){
			
			for(int i = 0; i < buttons.size(); i++){
				
				GameButton b = buttons.get(i);
				boolean click = b.contains(loc.x, loc.y);
				
				if(click){
					
					String text = b.getText();
					
					if(text.equals("RETRY")){
						gsm.getState(gsm.PLAYSTATE).init();
						gsm.setState(gsm.PLAYSTATE);
					}
					else if(text.equals("MENU")){
						gsm.setState(gsm.MENUSTATE);
					}
				}
			}
			
		}
	}

	@Override
	public void mouseMotion(Point loc) {
		
		for(int i = 0; i < buttons.size(); i++){
			
			GameButton b = buttons.get(i);
			boolean on = b.contains(loc.x, loc.y);
			
			if(on){
				b.setSelect(true);
			}
			else{
				b.setSelect(false);
			}
		}
	}
	
	@Override
	public void mouseReleased(int button, Point loc){
		
	}

}
