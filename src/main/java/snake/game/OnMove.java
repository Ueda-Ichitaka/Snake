package snake.game;

public interface OnMove {

/**
 * @param passedtime Die Zeit die seit dem letzten Takt verstrichen ist
 */
public boolean onMove(int width,int height);
}
