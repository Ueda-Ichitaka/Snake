package snake.game.objects;

import java.awt.Point;
import java.util.List;

import processing.core.PApplet;
import snake.game.OnCollide;
import snake.game.OnDraw;
import snake.game.OnMove;
import snake.game.Spielfenster;

public class Schwanz implements OnDraw, OnCollide {

	public Schwanz(int direction, Point position) {
		super();
		this.direction = direction;
		this.position = position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	// Die Richtung, in die der Schwanz zeigt (also der Körper weitergeht)
	private int direction;

	public void setDirection(int direction) {
		this.direction = direction;
	}

	private Point position;

	public boolean onCollide(OnCollide colliding) {

		return true;
	}

	public List<OnCollide> getobjects() {
		// TODO Auto-generated method stub
		return null;
	}

	public Point getposition() {

		return position;
		}
	
	public int xGeben() {
		return position.x;
	}

	public int yGeben() {
		return position.y;
	}
	
	public int getDirection()
	{
		return direction;
	}


	public void move(int dir) {
		direction = dir;
		switch (dir) {
		case Kopf.LINKS:
			position.x--;
			break;
		case Kopf.RECHTS:
			position.x++;
			break;
		case Kopf.OBEN:
			position.y--;
			break;
		case Kopf.UNTEN:
			position.y++;
			break;

		}
	}

	public void onDraw(Spielfenster window, float scale,int actual_tile_dimension) {
//		window.noStroke();
//		window.fill(50, 100, 98);
//		Point p1,p2,p3;
//		Point top_left = Spielfenster.kachel_nach_pixel(position, scale);
//		
//		switch(direction){
//		case Kopf.OBEN:
//			p1 = top_left;
//			p2 = new Point((int)(p1.x+actual_tile_dimension -1),p1.y);
//			p3 = new Point((int) (p1.x+0.5*actual_tile_dimension),(int) (p1.y+actual_tile_dimension));
//			break;
//		case Kopf.UNTEN:
//			p1 = (Point) top_left.clone();
//			p1.translate(0, actual_tile_dimension);
//			p2 = (Point) p1.clone();
//			p2.translate(actual_tile_dimension, 0);
//			p3 = new Point((int) (p1.x+0.5*actual_tile_dimension),top_left.y);
//			break;
//		case Kopf.RECHTS:
//			p1 = (Point) top_left.clone();
//			p1.translate(actual_tile_dimension,0);
//			p2 = (Point) p1.clone();
//			p2.translate(0, actual_tile_dimension);
//			p3 = (Point) top_left.clone();
//			p3.translate(0, (int) (actual_tile_dimension*0.5));
//			break;
//		case Kopf.LINKS:
//			p1 = (Point) top_left.clone();
//			p2 = (Point) p1.clone();
//			p2.translate(0, actual_tile_dimension);
//			p3 = new Point(p1.x+actual_tile_dimension,(int) (p1.y+actual_tile_dimension*0.5));
//			break;
//		default:
//			p1=null;
//			p2=null;
//			p3=null;
//		}
//		
//
//		window.triangle(p1.x,p1.y,p2.x,p2.y,p3.x,p3.y);

//		window.rect(draw_to.x, draw_to.y, Spielfenster.standard_tile * scale,
//				Spielfenster.standard_tile * scale);
		/*window.pushMatrix();
		Point draw_to = Spielfenster.kachel_nach_pixel(position, scale);
		window.imageMode(PApplet.CENTER);
		window.translate(draw_to.x+actual_tile_dimension/2f, +actual_tile_dimension/2f);
		
	
		
		
		//window.translate(window.getWidth()/2, window.getHeight()/2);
		
		 
		
		switch(direction){
		case Kopf.OBEN:
			window.rotate(PApplet.HALF_PI);
			
			break;
		case Kopf.UNTEN:
			window.rotate(-PApplet.HALF_PI);
			
			break;
		case Kopf.RECHTS:
			window.rotate(PApplet.PI);
			
			break;
			
		}
		window.image(window.getSnake_tail(), 0, 0);
		window.popMatrix();
		window.imageMode(PApplet.CORNER);*/
		//window.image(window.getSnake_tail(), top_left.x, top_left.y);
		//window.resetMatrix();
		Point draw_to = Spielfenster.kachel_nach_pixel(position, scale);
		window.image(window.getSnake_tail(direction), draw_to.x, draw_to.y);
	}

}
