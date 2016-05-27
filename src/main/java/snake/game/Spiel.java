package snake.game;

import java.awt.Point;
import java.util.Random;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import processing.core.PApplet;
import snake.game.objects.Apfel;
import snake.game.objects.Gem;
import snake.game.objects.Rock;
import snake.game.objects.Sheep;
import snake.gui.GameOverForm;
import snake.gui.MainForm;

public class Spiel implements OnDraw, KeyListener {
	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	private List<OnMove> moving_objects;
	private List<OnCollide> colliding_objects;
	private List<OnDraw> drawable;
	private List<OnAction> action_objects;
	private Semaphore drawwait;
	private Timer onMoveTimer;
	private int punkte = 0;
	private int t = 0;
	boolean gameover = false;
	// Breite Höhe in Kachelanzahl
	private int height, width;
	private Window host;

	public void setHost(Window host) {
		this.host = host;

	}

	public Spiel(List<Object> Objekte, int height, int width) {
		this.height = height;
		this.width = width;

		drawwait = new Semaphore(1);
		moving_objects = new ArrayList<OnMove>();
		colliding_objects = new ArrayList<OnCollide>();
		drawable = new ArrayList<OnDraw>();
		action_objects = new ArrayList<OnAction>();
		for (Object o : Objekte) {
			if (o instanceof OnMove) {
				moving_objects.add((OnMove) o);
			}
			if (o instanceof OnCollide) {

				colliding_objects.add((OnCollide) o);
			}
			if (o instanceof OnDraw) {
				drawable.add((OnDraw) o);
			}
			if (o instanceof OnAction) {
				action_objects.add((OnAction) o);
			}
		}

		onMoveTimer = new Timer();

	}

	public void start() {
		createApple();
		createGem(); // gedacht als collectable, nur für mehr punkte, kein
						// verlängern der schlange
		createRock(); // gedacht als collidable
		createSheep(); // ebenso gedacht wie Rock als collidable

		Properties prop = MainForm.getCon().getProperties();
		Object Properties;
		int period = Integer.parseInt(prop.getProperty("move_period", "500"));

		onMoveTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				synchronized (drawwait) {
					onMove();
					OnCollide();

				}

			}
		}, 100L, period);

	}

	public void onDraw(Spielfenster window, float scale,
			int actual_tile_dimension) {
		synchronized (drawwait) {

			window.fill(50);
			window.rect(10, 10, 10, 10);
			for (OnDraw draw : drawable) {
				draw.onDraw(window, scale, actual_tile_dimension);
			}
			if (gameover) {
				window.fill(0);
				window.text("Game Over", 20, 20);

			}
			window.textSize(actual_tile_dimension*0.8f);
			//window.imageMode(PApplet.CORNER);
			window.textAlign(Spielfenster.LEFT, Spielfenster.TOP);
			window.text(punkte, 6,6);
			//window.imageMode(PApplet.CENTER);

		}

	}

	public void onMove() {
		for (OnMove om : moving_objects) {
			if (!om.onMove(height, height)) {
				endGame();
			}
		}
		//punkte++;
		t++;

	}

	public void OnCollide() {
		boolean success = false;
		try {
			while (!success) {
				OnCollide[][] objects = new OnCollide[width][height];

				top: for (OnCollide oc : colliding_objects) {
					for (OnCollide cur_obj : oc.getobjects()) {
						Point p = cur_obj.getposition();
						OnCollide o = objects[p.x][p.y];
						if (o != null) {
							
							// Falls false zurückgegeben wird, endet das Spiel
							if (!oc.onCollide(o) || !o.onCollide(oc)
									|| (oc != cur_obj && !cur_obj.onCollide(o))) {
								endGame();
								break top;
							}

						}
						objects[p.x][p.y] = oc;
					}
				}
				success = true;
			}
		} catch (ConcurrentModificationException e) {

		}
	}

	public void unregisterObject(Object o) {
		synchronized (drawwait) {
			if (o instanceof OnMove) {
				moving_objects.remove(o);
			}
			if (o instanceof OnCollide) {

				colliding_objects.remove(o);
			}
			if (o instanceof OnDraw) {
				drawable.remove(o);
			}
			if (o instanceof OnAction) {
				action_objects.remove(o);
			}

		}
	}

	public void registerObject(Object o) {
		synchronized (drawwait) {
			if (o instanceof OnMove) {
				moving_objects.add((OnMove) o);
			}
			if (o instanceof OnCollide) {

				colliding_objects.add((OnCollide) o);
			}
			if (o instanceof OnDraw) {
				drawable.add((OnDraw) o);
			}
			if (o instanceof OnAction) {
				action_objects.add((OnAction) o);
			}
		}
	}

	private void endGame() {
		GameOverForm gf = new GameOverForm(punkte);
		System.out.println("End");
		gameover = true;
		onMoveTimer.cancel();
		
		// host.setVisible(false);
		// host.dispose();
		host.dispatchEvent(new WindowEvent(host, WindowEvent.WINDOW_CLOSING));
		// gf.requestFocus();
	}

	public void keyPressed(KeyEvent arg0) {
		ActionEnum action = null;
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			action = ActionEnum.LINKS;
			break;
		case KeyEvent.VK_UP:
			action = ActionEnum.OBEN;
			break;
		case KeyEvent.VK_DOWN:
			action = ActionEnum.UNTEN;
			break;
		case KeyEvent.VK_RIGHT:
			action = ActionEnum.RECHTS;
			break;
		default:
			return;
		}

		for (OnAction o : action_objects) {
			o.onAction(action);
		}
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void createApple() {
		OnCollide[][] objects = new OnCollide[width][height];

		for (OnCollide oc : colliding_objects) {
			for (OnCollide cur_obj : oc.getobjects()) {
				Point p = cur_obj.getposition();
				objects[p.x][p.y] = oc;

			}
		}
		List<Point> positions = new LinkedList<Point>();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (objects[x][y] == null) {
					positions.add(new Point(x, y));
				}
			}
		}

		Random genx = new Random();
		Point pos = positions.get(genx.nextInt(positions.size()));
		System.out.println(pos);
		Apfel a = new Apfel(pos, this);
		this.registerObject(a);
		// int x = genx.nextInt(width);
		// int y = genx.nextInt(height);
		// while (objects[x][y] != null) {
		// x = genx.nextInt(width);
		// y = genx.nextInt(height);
		// }
	}

	public void createGem() {
		OnCollide[][] objects = new OnCollide[width][height];

		for (OnCollide oc : colliding_objects) {
			for (OnCollide cur_obj : oc.getobjects()) {
				Point p = cur_obj.getposition();
				objects[p.x][p.y] = oc;

			}
		}
		List<Point> positions = new LinkedList<Point>();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (objects[x][y] == null) {
					positions.add(new Point(x, y));
				}
			}
		}

		Random genx = new Random();
		Gem g = new Gem(positions.get(genx.nextInt(positions.size())), this);
		this.registerObject(g);
	}

	public void createRock() {
		OnCollide[][] objects = new OnCollide[width][height];

		for (OnCollide oc : colliding_objects) {
			for (OnCollide cur_obj : oc.getobjects()) {
				Point p = cur_obj.getposition();
				objects[p.x][p.y] = oc;

			}
		}
		List<Point> positions = new LinkedList<Point>();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (objects[x][y] == null) {
					positions.add(new Point(x, y));
				}
			}
		}

		Random genx = new Random();
		Rock r = new Rock(positions.get(genx.nextInt(positions.size())), this);
		this.registerObject(r);
	}

	public void createSheep() {
		OnCollide[][] objects = new OnCollide[width][height];

		for (OnCollide oc : colliding_objects) {
			for (OnCollide cur_obj : oc.getobjects()) {
				Point p = cur_obj.getposition();
				objects[p.x][p.y] = oc;

			}
		}
		List<Point> positions = new LinkedList<Point>();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (objects[x][y] == null) {
					positions.add(new Point(x, y));
				}
			}
		}

		Random genx = new Random();
		Sheep s = new Sheep(positions.get(genx.nextInt(positions.size())), this);
		this.registerObject(s);
	}

	public void punktezahlerhoehen() {
		punkte = punkte + 50;
	}

	
	public void relocateCollidables()
	{
		
	}
	
	public void PunktezahlGemErhoehen()
	{
		punkte = punkte + 100;
	}
	
}
