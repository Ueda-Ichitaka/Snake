package snake.game.objects;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;
import snake.game.OnCollide;
import snake.game.OnDraw;
import snake.game.Spielfenster;

public class Schlangenteil implements OnDraw, OnCollide {
	private List<OnCollide> this_list;

	public int getStart_direction() {
		return start_direction;
	}

	public void setStart_direction(int start_direction) {
		this.start_direction = start_direction;
	}

	public int getEnd_direction() {
		return end_direction;
	}

	public void setEnd_direction(int end_direction) {
		this.end_direction = end_direction;
	}

	private Point position;
	private int start_direction, end_direction;

	// public Schlangenteil(int xneu, int yneu) {
	// x = xneu;
	// y = yneu;
	// }
	public Schlangenteil(int x, int y, int start_direction, int end_direction) {
		this(new Point(x, y), start_direction, end_direction);
	}

	public Schlangenteil(Point position, int start_direction, int end_direction) {
		super();
		this.position = position;
		this.start_direction = start_direction;
		this.end_direction = end_direction;
		this_list = Arrays.asList(new OnCollide[] { this });
	}

	public int xGeben() {
		return position.x;
	}

	public int yGeben() {
		return position.y;
	}

	public void onDraw(Spielfenster window, float scale,
			int actual_tile_dimension) {
		window.fill(0, 100, 100);
		window.noStroke();

		Point draw_to = Spielfenster.kachel_nach_pixel(position, scale);

		// window.rect(draw_to.x, draw_to.y, Spielfenster.standard_tile*scale,
		// Spielfenster.standard_tile*scale);
		PImage pi = null;
		if (start_direction == -end_direction) {
			pi = window.getSnake_straight(start_direction);
		} else {
			int selected_dir = 0;
			switch (start_direction) {
			case Kopf.UNTEN:
				if (end_direction == Kopf.RECHTS) {
					selected_dir = Kopf.OBEN;
				} else {
					selected_dir = Kopf.RECHTS;
				}
				break;
			case Kopf.RECHTS:
				if (end_direction == Kopf.OBEN) {
					selected_dir = Kopf.LINKS;
				} else {
					selected_dir = Kopf.OBEN;
				}
				break;

			case Kopf.OBEN:
				if (end_direction == Kopf.LINKS) {
					selected_dir = Kopf.UNTEN;
				} else {
					selected_dir = Kopf.LINKS;
				}
				break;
			case Kopf.LINKS:
				if (end_direction == Kopf.UNTEN) {
					selected_dir = Kopf.RECHTS;
				} else {
					selected_dir = Kopf.UNTEN;
				}
				break;

			}
			pi = window.getSnake_corner(selected_dir);

		}
		window.image(pi, draw_to.x, draw_to.y);
	}

	public boolean onCollide(OnCollide colliding) {

		return true;
	}

	public List<OnCollide> getobjects() {

		return this_list;
	}

	public Point getposition() {

		return position;
	}

}
