package snake.game.objects;

import processing.core.PApplet;
import snake.game.OnCollide;
import snake.game.OnDraw;
import snake.game.Spiel;
import snake.game.Spielfenster;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

public class Sheep implements OnDraw, OnCollide
{	
	private Point position;
	private List<OnCollide> objects;
	private Spiel game;
	public Sheep(Point position, Spiel game){
		this.position = position;
		objects= Arrays.asList(new OnCollide[]{this});
		this.game = game;
	}
	
	/**
	 * @deprecated Dieser Konstruktor ist veraltet und wird nur noch zu Kompatibilitätszwecekn behalten
	 */
	@Deprecated
	public Sheep(int x, int y, Spiel game) {
		this(new Point(x,y),game);

	
	}
	
	public void onDraw(Spielfenster window, float scale, int actual_tile_dimension)
	{
		window.fill(120, 100, 100);
		window.noStroke();

		Point draw_to = Spielfenster.kachel_nach_pixel(position, scale);

		//window.rect(draw_to.x, draw_to.y, Spielfenster.standard_tile * scale,
			//	Spielfenster.standard_tile * scale);
		
		window.image(window.getSheep(), draw_to.x, draw_to.y);
	}
	
	public boolean onCollide(OnCollide colliding) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<OnCollide> getobjects() {
		// TODO Auto-generated method stub
		return objects;
	}

	public Point getposition() {
		// TODO Auto-generated method stub
		return position;
	}
	
	public void relocate()
	{
		game.unregisterObject(this);
		game.createSheep();
	}
	
}

