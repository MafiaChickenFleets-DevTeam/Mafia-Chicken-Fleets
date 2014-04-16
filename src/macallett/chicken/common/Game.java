package macallett.chicken.common;

public abstract class Game implements Runnable {

	public enum GameType {
		CLIENT, SERVER;
	}
	
	public static final int UPS = 35;
	public static final double NANO_SECONDS_PER_UPDATE = 1_000_000_000 / UPS;
	
	protected GameType type;
	
	private Thread t;
	private boolean running = false;
	
	private long totUpdates = 0L;
	
	public Game(GameType type) {
		this.type = type;
	}
	
	public boolean isClient() {
		return type.equals(GameType.CLIENT);
	}
	
	@Override
	public void run() {
		long prevTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		double delta = 0;
		int updates = 0;
		int frames = 0;
		
		if(isClient())
			focus();
		
		while(running) {
			long nowTime = System.nanoTime();
			delta += (nowTime - prevTime) / NANO_SECONDS_PER_UPDATE;
			prevTime = nowTime;
			
			while(delta >= 1) {
				update();
				updates++;
				totUpdates++;
				delta--;
			}
			
			if(isClient()) {
				render();
				frames++;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				
				String text = updates + " u";
				if(isClient())
					text += ", " + frames + " f";
				System.out.println(text);
				
				updates = 0;
				if(isClient())
					frames = 0;
			}
		}
		
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void start() {
		running = true;
		
		t = new Thread(this);
		t.start();
	}
	
	public synchronized void stop() {
		running = false;
	}
	
	public long getUpdates() {
		return totUpdates;
	}
	
	protected abstract void update();
	protected abstract void render();
	protected abstract void focus();
	
}
