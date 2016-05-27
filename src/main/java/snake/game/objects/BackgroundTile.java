package snake.game.objects;

import java.awt.Point;

import processing.core.PApplet;
import snake.game.OnDraw;
import snake.game.Spielfenster;

public class BackgroundTile implements OnDraw {
	public BackgroundTile(Point position) {
		super();
		this.position = position;
	}

	Point position;

	public void onDraw(Spielfenster window, float scale,int actual_tile_dimension ) {
		window.fill(119, 100, 54);
		window.noStroke();
		Point draw_to = Spielfenster.kachel_nach_pixel(position, scale);
		window.image(window.getBackground_Image(), draw_to.x, draw_to.y);
//		window.rect(draw_to.x,
//				draw_to.y,
//				Spielfenster.standard_tile * scale, Spielfenster.standard_tile
//						* scale);

	}

}
