package snake.game;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import processing.core.PApplet;
import processing.core.PImage;
import snake.game.objects.Kopf;

public class Spielfenster extends PApplet {
	private PImage background, apple, gem, rock, sheep;
	private HashMap<Integer, PImage> snake_head, snake_straight, snake_tail,
			snake_corner;

	public PImage getBackground_Image() {
		return background;
	}

	public PImage getSnake_head(int direction) {
		return snake_head.get(direction);
	}

	public PImage getSnake_straight(int direction) {
		return snake_straight.get(direction);
	}

	public PImage getSnake_tail(int direction) {
		return snake_tail.get(direction);
	}

	public PImage getSnake_corner(int direction) {
		return snake_corner.get(direction);
	}

	public PImage getApple() {
		return apple;
	}
	
	public PImage getGem() {
		return gem;
	}
	
	public PImage getRock() {
		return rock;
	}
	
	public PImage getSheep() {
		return sheep;
	}

	public static final int standard_tile = 30;
	private Spiel spiel;
	// Höhe in Pixel
	private int height, width;
	private float scale;

	public Spielfenster(Spiel spiel, int height, int width) {
		super();
		this.spiel = spiel;
		this.height = height;
		this.width = width;

		System.out.println("Skalierung: " + scale);
		// spiel.setHost(findWindow(Spielfenster.this));

	}

	public static BufferedImage rotateImageX(Image img, double angle) {
		double sin = Math.abs(Math.sin(Math.toRadians(angle))), cos = Math
				.abs(Math.cos(Math.toRadians(angle)));
		int w = img.getWidth(null), h = img.getHeight(null);
		int neww = (int) Math.floor(w * cos + h * sin), newh = (int) Math
				.floor(h * cos + w * sin);
		BufferedImage bimg = new BufferedImage(neww, newh,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bimg.createGraphics();
		g.translate((neww - w) / 2, (newh - h) / 2);
		g.rotate(Math.toRadians(angle), w / 2, h / 2);
		g.drawRenderedImage(toBufferedImage(img), null);
		g.dispose();
		return bimg;
	}

	/**
	 * Converts a given Image into a BufferedImage
	 * 
	 * @param img
	 *            The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null),
				img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}

	public PImage loadPImageFromRessource(String path) throws IOException {
		BufferedImage bi;
		Image i = Toolkit.getDefaultToolkit().createImage(
				getClass().getClassLoader().getResource(path));
		// bi = ImageIO
		// .read(getClass().getClassLoader().getResourceAsStream(path));
		PImage pi = this.loadImageMT(i);
		if (path.endsWith(".png") || path.endsWith(".gif")) {
			pi.format = PImage.ARGB;
		}

		return pi;

	}

	public PImage imagetoPimage(Image i) {
		PImage pi = this.loadImageMT(i);
		pi.format = PImage.ARGB;
		return pi;

	}

	public Image loadImageFromRessource(String path) throws IOException {

		Image i = Toolkit.getDefaultToolkit().createImage(
				getClass().getClassLoader().getResource(path));

		return i;

	}

	private void insertRotated_Images(HashMap<Integer, PImage> images,
			Image start_image, int start_direction) {
		images.put(start_direction, imagetoPimage(start_image));
		BufferedImage bi = toBufferedImage(start_image);
		images.put(-start_direction, imagetoPimage(rotateImageX(bi, 180)));
		int second_dir;
		// if(start_direction %2 == 0){
		// dir = start_direction>>1;
		// }else{
		// dir = start_direction<<1;
		// }
		switch (start_direction) {
		case Kopf.OBEN:
			second_dir = Kopf.RECHTS;
			break;
		case Kopf.RECHTS:
			second_dir = Kopf.UNTEN;
			break;
		case Kopf.UNTEN:
			second_dir = Kopf.LINKS;
			break;
		case Kopf.LINKS:
			second_dir = Kopf.OBEN;
			break;
		default:
			throw new IllegalArgumentException();
		}
		images.put(second_dir, imagetoPimage(rotateImageX(bi, 90)));
		images.put(-second_dir, imagetoPimage(rotateImageX(bi, -90)));

	}

	private void loadRessources() {
		try {

			background = loadPImageFromRessource("src/main/resources/snake/game/bgTile2.jpg");
			Image snake_head_image =  loadImageFromRessource("src/main/resources/snake/game/snakeHead.png");
			snake_head = new HashMap<Integer,PImage>();
			insertRotated_Images(snake_head,snake_head_image,Kopf.RECHTS);
			
			Image snake_tail_image =loadImageFromRessource("src/main/resources/snake/game/snakeTail.png");
			snake_tail = new HashMap<Integer,PImage>();
			insertRotated_Images(snake_tail,snake_tail_image,Kopf.LINKS);
			
			Image snake_straight_image =loadImageFromRessource("src/main/resources/snake/game/snakeStraight.png");
			snake_straight = new HashMap<Integer,PImage>();
			insertRotated_Images(snake_straight,snake_straight_image,Kopf.LINKS);
			
			Image snake_corner_image =loadImageFromRessource("src/main/resources/snake/game/snakeEdge.png");
			snake_corner = new HashMap<Integer,PImage>();
			//Hier ist die Richtung definitionssache, Ich hab das jetzt einfach mal so auf OBEN gelegt (immer am dicken Fleck orientiert)
			insertRotated_Images(snake_corner,snake_corner_image,Kopf.OBEN);
			
			apple = loadPImageFromRessource("src/main/resources/snake/game/apple.png");
			gem = loadPImageFromRessource("src/main/resources/snake/game/gem.png");
			rock = loadPImageFromRessource("src/main/resources/snake/game/rock.png");
			sheep = loadPImageFromRessource("src/main/resources/snake/game/sheep.png");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resizeImages();
	}

	@Override
	public void draw() {

		// super.draw();
		spiel.onDraw(this, scale, (int) (Spielfenster.standard_tile * scale));

	}

	@Override
	public void setup() {

		// Berechne die Skalierung
		float ratio = width / height;
		float gameratio = spiel.getWidth() / spiel.getHeight();
		if (ratio > gameratio) {
			scale = (height / spiel.getHeight()) / (float) standard_tile;
		} else {
			scale = (width / spiel.getWidth()) / (float) standard_tile;
		}
		loadRessources();
		super.setup();
		// this.imageMode(CENTER);
		this.loop();
		this.frameRate(30);
		this.colorMode(HSB);
		this.size(width, height);
		this.addKeyListener(spiel);
		setPreferredSize(new Dimension(500, 500));
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				// Dies muss in invokeLater geschehen, da sonst eventuell noch
				// nicht alle Events abgearbeitet sind
				Spielfenster.this.spiel.setHost(findWindow(Spielfenster.this));
				findWindow(Spielfenster.this).pack();

				spiel.start();
			}
		});
		

	}

	private void resizeImages() {
		int size = (int) (Spielfenster.standard_tile * scale);
		background.resize(size, 0);
		for (PImage pi:snake_head.values()){
			pi.resize(size, 0);
		}
		for (PImage pi:snake_tail.values()){
			pi.resize(size, 0);
		}
		for (PImage pi:snake_straight.values()){
			pi.resize(size, 0);
		}
		for (PImage pi:snake_corner.values()){
			pi.resize(size, 0);
		}
		apple.resize(size, 0);
		gem.resize(size,  0);
		rock.resize(size, 0);
		sheep.resize(size,  0);
		
	}

	public static Point kachel_nach_pixel(Point tile_point, float scale) {
		Point r = new Point();
		r.setLocation((tile_point.x) * Spielfenster.standard_tile * scale + 1,
				(tile_point.y) * Spielfenster.standard_tile * scale + 1);
		return r;
	}

	public static Window findWindow(Component c) {
		if (c == null) {
			return JOptionPane.getRootFrame();
		} else if (c instanceof Window) {
			return (Window) c;
		} else {
			return findWindow(c.getParent());
		}
	}
}
