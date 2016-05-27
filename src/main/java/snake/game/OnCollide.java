package snake.game;

import java.awt.Point;
import java.util.List;

public interface OnCollide {
	/**
	 * @param colliding Hier wird immer das Objekt mitgegeben,mit dem kollidiert wird
	 * @return true, wenn das Spiel weiterlaufen kann, false wenn es beendet werden muss
	 */
	public boolean onCollide (OnCollide colliding);
	public List<OnCollide> getobjects();
	public Point getposition();
}
