package main;

import gamestate.GameStateManager;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	//fields
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 960;
	
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000/FPS;
	
	private BufferedImage image;
	private Graphics2D g;
	
	private GameStateManager gsm;
	
	public GamePanel(){
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			addKeyListener(this);
			addMouseListener(this);
			addMouseMotionListener(this);
			thread.start();
		}
	}
	
	private void init(){
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		running = true;
		
		gsm = new GameStateManager();
	}
	
	@Override
	public void run() {
		
		init();
		
		long start;
		long elapsed;
		long wait;
		
		//game loop
		while(running){
			
			start = System.nanoTime();
			
			update();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			
			wait = targetTime - elapsed / 1000000;
			if(wait < 0){
				wait = 1;
			}
			
			try{
				Thread.sleep(wait);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	private void update() {
		gsm.update();
	}
	
	private void draw() {
		gsm.draw(g);
	}
	
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	
	@Override
	public void mouseClicked(MouseEvent evt) {
		Point p = evt.getPoint();
		int button = evt.getButton();
		
		System.out.println("Point " + p);
		
		gsm.mouseClicked(button, p);
		//Mouse.button = button;
		//Mouse.p = p;
		//Mouse.clicked = true;
	}

	@Override
	public void mousePressed(MouseEvent evt) {
		
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		Point p = evt.getPoint();
		int button = evt.getButton();
		
		gsm.mouseReleased(button, p);
	}

	@Override
	public void mouseEntered(MouseEvent evt) {
		
	}

	@Override
	public void mouseExited(MouseEvent evt) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent key) {
		
		gsm.keyPressed(key.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent key) {
		
		gsm.keyReleased(key.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent key) {
		
		
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		
	}

	@Override
	public void mouseMoved(MouseEvent evt) {
		Point p = evt.getPoint();
		
		if(p != null && gsm != null){
			gsm.mouseMotion(p);
		}
	}
	
	
}
