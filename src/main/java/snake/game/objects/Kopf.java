package snake.game.objects;

import java.awt.Point;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;
import snake.game.OnCollide;
import snake.game.OnDraw;
import snake.game.Spielfenster;

public class Kopf implements OnDraw,OnCollide{
	public static final int RECHTS = 1, LINKS = -1, OBEN = 2, UNTEN = -2;
	private Point position;
	private int dir;

	public Kopf(int xneu, int yneu) {
		position = new Point(xneu, yneu);

	}
	
	public int xGeben() {
		return position.x;
	}

	public int yGeben() {
		return position.y;
	}

	public void move(int dir) {
		switch (dir) {
		case RECHTS:
			position.x++;			
			this.dir = Kopf.LINKS;
			break;
		case LINKS:
			position.x--;
			this.dir = Kopf.RECHTS;
			break;
		case OBEN:
			position.y--;
			this.dir = Kopf.UNTEN;
			break;
		case UNTEN:
			position.y++;
			this.dir = Kopf.OBEN;
			break;

		}

	}

	public boolean canMove(int dir, int width, int height) {
		switch (dir) {
		case RECHTS:
			return (position.x < width-1);

		case LINKS:
			return (position.x > 0);

		case OBEN:
			return (position.y > 0);

		case UNTEN:
			return (position.y < height-1);

		default:
			return false;

		}
	}
	public void onDraw(Spielfenster window, float scale,int actual_tile_dimension) {
		window.fill(50, 100, 50);
		window.noStroke();

		Point draw_to = Spielfenster.kachel_nach_pixel(position, scale);

		//window.rect(draw_to.x, draw_to.y, Spielfenster.standard_tile * scale,
			//	Spielfenster.standard_tile * scale);
		
		switch(this.dir)
		{
			case RECHTS:
				window.image(window.getSnake_head(RECHTS), draw_to.x, draw_to.y);
				break;
			case LINKS:
				window.image(window.getSnake_head(LINKS), draw_to.x, draw_to.y);
				break;
			case OBEN:
				window.image(window.getSnake_head(OBEN), draw_to.x, draw_to.y);
				break;
			case UNTEN:
				window.image(window.getSnake_head(UNTEN), draw_to.x, draw_to.y);
				break;
			default:
				window.image(window.getSnake_head(LINKS), draw_to.x, draw_to.y);
				break;
		}
		
		//window.image(window.getSnake_head(LINKS), draw_to.x, draw_to.y);

		
	}

	public boolean onCollide(OnCollide colliding) {
		if (colliding instanceof Apfel){
			return true;
		}
		return false;
	}

	public List<OnCollide> getobjects() {
		
		return null;
	}

	public Point getposition() {
		
		return position;
	}
}
