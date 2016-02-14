package gamestate;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GameButton;
import main.GamePanel;

public class MenuState extends GameState{

	private BufferedImage image;
	private Font font;
	private Color fontColor;
	private Color selectColor;
	
	//button
	private ArrayList<GameButton> buttons;
	
	public MenuState(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	@Override
	public void init() {
		
		buttons = new ArrayList<GameButton>();
		
		try{
			BufferedImage imageBefore = ImageIO.read(getClass().getResourceAsStream("/JOJO THE WORLD.png"));
			
			int w = imageBefore.getWidth();
			int h = imageBefore.getHeight();
			double scaleW = (double)GamePanel.WIDTH/(double)w;
			double scaleH = (double)GamePanel.HEIGHT/(double)h;
			image = new BufferedImage((int)(w * scaleW), (int)(h * scaleH), BufferedImage.TYPE_INT_ARGB);
			AffineTransform at = new AffineTransform();
			at.scale(scaleW, scaleH);
			AffineTransformOp scaleOp = 
			   new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			image = scaleOp.filter(imageBefore, image);
			
			font = new Font("Georgia", Font.PLAIN, 72);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		fontColor = Color.WHITE;
		selectColor = Color.RED;
		
		placeButtons();
		
	}

	private void placeButtons(){
		
		String text = "PLAY";
		//string width and height
		Canvas c = new Canvas();
		FontMetrics fm = c.getFontMetrics(font);
		int width = fm.stringWidth(text);
		int height = fm.getHeight();
		
		buttons.add(new GameButton(120, 350, width, height, text));
		
		text = "TUTORIAL";
		width = fm.stringWidth(text);
		
		buttons.add(new GameButton(120, 525, width, height, text));
		
		text = "EXIT";
		width = fm.stringWidth(text);
		
		buttons.add(new GameButton(120, 700, width, height, text));
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, 0, 0, null);
		
		g.setFont(font);
		
		for(int i = 0; i < buttons.size(); i++){
			
			GameButton b = buttons.get(i);
			String text = b.getText();
			int x = b.getX();
			int y = b.getY();
			int width = b.getWidth();
			int height = b.getHeight();
			
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
					
					if(text.equals("PLAY")){
						gsm.getState(gsm.PLAYSTATE).init();
						gsm.setState(gsm.PLAYSTATE);
					}
					else if(text.equals("TUTORIAL")){
						gsm.setState(gsm.TUTORIALSTATE);
					}
					else if(text.equals("EXIT")){
						System.exit(0);
					}
				}
			}
			
		}
		
	}
	
	@Override
	public void mouseMotion(Point loc){
		
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
