package gamestate;

import java.awt.*;
import java.awt.event.MouseEvent;

import main.GameButton;
import main.GamePanel;

public class TutorialState extends GameState {
	
	private Font font;
	private Font font2;
	private Color fontColor;
	private Color selectColor;
	
	private GameButton button;
	
	public TutorialState(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	@Override
	public void init() {
		
		font = new Font("Georgia", Font.PLAIN, 72);
		font2 = new Font("Georgia", Font.PLAIN, 36);
		
		fontColor = Color.WHITE;
		selectColor = Color.RED;
		
		String text = "BACK";
		Canvas c = new Canvas();
		FontMetrics fm = c.getFontMetrics(font);
		int width = fm.stringWidth(text);
		int height = fm.getHeight();
		button = new GameButton(30, GamePanel.HEIGHT - height - 30, width, height, text);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D g) {
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		g.setFont(font);
		
		String text = button.getText();
		int x = button.getX();
		int y = button.getY();
		int width = button.getWidth();
		int height = button.getHeight();
		
		if(button.getSelect()){
			g.setColor(selectColor);
		}
		else{
			g.setColor(fontColor);
		}
		
		//g.drawRect(x, y, width, height);
		
		//y+height to make sure font placement matches button coords
		g.drawString(text, x, y+height);
		
		
		//instructions
		g.setColor(fontColor);
		g.setFont(font2);
		
		String line1 = "Dio will be throwing knives within his WORLD of frozen time.";
		String line2 = "Click on the knives to target them so STAR PLATINUM can protect Jotaro!";
		g.drawString(line1, 140, 400);
		g.drawString(line2, 50, 500);
	}

	@Override
	public void keyPressed(int k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(int button, Point loc) {
		
		if(button == MouseEvent.BUTTON1){
			
			boolean click = this.button.contains(loc.x, loc.y);
			
			if(click){
				gsm.setState(gsm.MENUSTATE);
			}
		}

	}

	@Override
	public void mouseMotion(Point loc) {
	
		boolean on = button.contains(loc.x, loc.y);
		
		if(on){
			button.setSelect(true);
		}
		else{
			button.setSelect(false);
		}
	

	}

	@Override
	public void mouseReleased(int button, Point loc) {
		// TODO Auto-generated method stub

	}

}
