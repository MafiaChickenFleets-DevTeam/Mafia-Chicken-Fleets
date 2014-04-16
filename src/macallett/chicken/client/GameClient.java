package macallett.chicken.client;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import macallett.chicken.client.gfx.Renderer;
import macallett.chicken.common.Game;

public class GameClient extends Game {

	private int width, height;
	
	private Canvas canvas;
	private Renderer renderer;
	
	private BufferedImage image;
	private int[] pixels;
	
	public GameClient(int width, int height, int scale) {
		super(GameType.CLIENT);
		
		this.width = width;
		this.height = height;
		
		canvas = new Canvas();
		int scWidth = width * scale;
		int scHeight = height * scale;
		canvas.setPreferredSize(new Dimension(scWidth, scHeight));
		
		renderer = new Renderer(width, height);
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Canvas getCanvas() {
		return canvas;
	}

	@Override
	protected void update() {
		
	}

	@Override
	protected void render() {
		BufferStrategy bs = canvas.getBufferStrategy();
		if(bs == null) {
			canvas.createBufferStrategy(3);
			return;
		}
		
		renderer.clear();
		loadPixels();
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		
		g.dispose();
		bs.show();
	}
	
	private void loadPixels() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = renderer.pixels[i];
		}
	}

	@Override
	protected void focus() {
		canvas.requestFocus();
	}

}
