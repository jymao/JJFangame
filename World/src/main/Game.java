package main;

import javax.swing.JFrame;

public class Game {

	public static void main(String[] args){
		
		JFrame window = new JFrame("ZA WARUDO WRYYYY");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocation(300,20);
		window.pack();
		window.setVisible(true);
	}
	
	
}
