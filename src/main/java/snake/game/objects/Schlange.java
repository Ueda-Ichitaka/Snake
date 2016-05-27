package snake.game.objects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import processing.core.PApplet;
import snake.game.ActionEnum;
import snake.game.OnAction;
import snake.game.OnCollide;
import snake.game.OnDraw;
import snake.game.OnMove;
import snake.game.Spielfenster;

public class Schlange implements OnMove, OnDraw, OnAction, OnCollide {

	private List<Schlangenteil> schlange;
	private int laenge;
	private int direction;
	private Kopf kopf;
	private Schwanz schwanz;

	private int z = 0;
	
	public Schlange() {
		laenge = 10;
		schlange = new LinkedList<Schlangenteil>();
		direction = 1;
		kopf = new Kopf(11, 3);

	}

	public void start() {
		int i;
		for (i = laenge - 1; i > 0; i--) {
			schlange.add(new Schlangenteil((i + 1), 3, Kopf.RECHTS, Kopf.LINKS));
		}
		schwanz = new Schwanz(Kopf.RECHTS, new Point(i, 3));
	}

	public void onDraw(Spielfenster window, float scale, int actual_tile_dimension) {
		kopf.onDraw(window, scale, actual_tile_dimension);
		for (Schlangenteil teil : schlange) {
			teil.onDraw(window, scale, actual_tile_dimension);

		}
		schwanz.onDraw(window, scale, actual_tile_dimension);
	}

	public boolean onMove(int width, int height) {
		// System.out.println("First coord 1: x:" + kopf.xGeben() + " y:"+
		// kopf.yGeben());
		// System.out.println(width + " "+ height);
		if (kopf.canMove(direction, width, height)) {
			Schlangenteil t = schlange.get(0);
			schlange.add(0, new Schlangenteil(kopf.xGeben(), kopf.yGeben(), direction, -t.getStart_direction()));
			kopf.move(direction);
			Schlangenteil last = schlange.get(schlange.size() - 1);
			schwanz.setPosition(last.getposition());
			schwanz.setDirection(last.getStart_direction());
			schlange.remove(schlange.size() - 1);
			// System.out.println("First coord 2: x:" + kopf.xGeben() + " y:"+
			// kopf.yGeben() + " true");
			
			z++;
			return true;
		} else {
			return false;
		}

	}

	public int getDirection_relativetopoint(int this_x, int this_y,
			int other_x, int other_y) {
		if (this_x > other_x) {
			return Kopf.LINKS;
		} else if (this_x < other_x) {
			return Kopf.RECHTS;
		} else if (this_y > other_y) {
			return Kopf.OBEN;
		} else if (this_y < other_y) {
			return Kopf.UNTEN;
		}
		return 0;
	}

	public void onAction(ActionEnum action) {

		switch (action) {
		case LINKS:
			if (direction == Kopf.RECHTS) {
			} else {
				direction = Kopf.LINKS;
			}
			break;
		case RECHTS:
			if (direction == Kopf.LINKS) {
			} else {
				direction = Kopf.RECHTS;
			}
			break;
		case OBEN:
			if (direction == Kopf.UNTEN) {
			} else {
				direction = Kopf.OBEN;
			}
			break;
		case UNTEN:
			if (direction == Kopf.OBEN) {
			} else {
				direction = Kopf.UNTEN;
			}
			break;
		}
	}

	public boolean onCollide(OnCollide colliding){

		if (colliding instanceof Apfel) {
			laenge++;
			Schlangenteil s = new Schlangenteil(schwanz.xGeben(), schwanz.yGeben(),
					schwanz.getDirection(), schwanz.getDirection());
			schlange.add(s);
			Point p = schwanz.getposition();
			switch (schwanz.getDirection()) {
			case Kopf.LINKS:
				p.x++;
				break;
			case Kopf.RECHTS:
				p.x--;
				break;
			case Kopf.OBEN:
				p.y++;
				break;
			case Kopf.UNTEN:
				p.y--;
				break;
			}
			schwanz.setPosition(p);
			return true;
		} 
		else if(colliding instanceof Gem)
		{
			return true;
		}
		else {
			return false;
		}

	}

	public List<OnCollide> getobjects() {
		List<OnCollide> coll_list = new ArrayList<OnCollide>(
				2 + schlange.size());
		coll_list.add(kopf);
		coll_list.addAll(schlange);
		coll_list.add(schwanz);
		return coll_list;
	}

	public Point getposition() {

		return null;
	}

}
