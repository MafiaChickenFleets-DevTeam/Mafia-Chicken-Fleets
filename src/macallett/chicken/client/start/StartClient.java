package macallett.chicken.client.start;

import javax.swing.JFrame;

import macallett.chicken.client.GameClient;
import macallett.chicken.common.reference.Strings;

public class StartClient {

	private static final int WIDTH = 533;
	private static final int HEIGHT = 300; // 16:9
	private static final int SCALE = 2;
	
	public static void main(String[] args) {
		GameClient game = new GameClient(WIDTH, HEIGHT, SCALE);
		
		JFrame frame = new JFrame();
		frame.setResizable(false);
		frame.add(game.getCanvas());
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		String title = Strings.NAME + " " + Strings.VERSION;
		frame.setTitle(title);
		
		frame.setVisible(true);
		game.start();
	}

}
