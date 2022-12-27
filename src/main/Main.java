package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		System.out.println("Start");
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Mush Rooms");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		window.pack(); // Forces the windows to set its dimensions regarding its GamePanel component.
		window.setLocationRelativeTo(null); // Centers the window.
		window.setVisible(true);
		
		gamePanel.startGameThread();
		
	}

}
