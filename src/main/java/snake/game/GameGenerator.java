package snake.game;

import java.awt.Point;
import java.util.LinkedList;

import snake.game.*;
import snake.game.objects.Apfel;
import snake.game.objects.BackgroundTile;
import snake.game.objects.Schlange;

public class GameGenerator {
	public static Spiel generate_game(){
		LinkedList<Object> game = new LinkedList<Object>();
		int width = 20;
		int height = 20;
		for(int i=0;i<width;i++){
			for (int e=0;e<height;e++){
				game.add(new BackgroundTile(new Point(i,e)));
			}
		}
		Schlange s = new Schlange();

		s.start();
		game.add(s);
	
		
		
		
		return new Spiel(game,width,height);
	}
}
