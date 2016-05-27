package snake.gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import snake.database.*;

public class ScorePanel extends JPanel implements ActionListener, KeyListener
{
	private static final int BOLD = 0;
	private MainForm host;	//for communication with form
	private JButton back;
	private JLabel title;	
	private ScoreLabel top;
	
	private int x = 50;
	//private Highscoreeintrag entries = new Highscoreeintrag();
	private int l = 12;
	
	public ScorePanel()
	{
		initComponents();
	}
	
	public ScorePanel(MainForm host)
	{
		this.host = host;
		this.setSize(new Dimension(800, 600));
		this.setLayout(null);
		initComponents();
		initScore();
	}
	
	public void initComponents()
	{
		back = new JButton("<");
		title = new JLabel("HIGHSCORE");	
		top = new ScoreLabel("RANK", "NAME", "SCORE");
		
		add(top);		
		add(back);
		add(title);
		
		title.setFont(new Font("Dialog", BOLD, 24));
		title.setForeground(Color.black);
		
		back.addActionListener(this);
		back.addKeyListener(this);
				
		Insets insets = this.getInsets();
		Dimension size = back.getPreferredSize();
		back.setBounds(10 + insets.left, 5 + insets.top, size.width, size.height);
		size = title.getPreferredSize();
		title.setBounds(400 - (size.width / 2) + insets.left, 10 + insets.top, size.width, size.height);		
		size = top.getPreferredSize();
		top.setBounds(100 + insets.left, 50 + insets.top, size.width, size.height);
		
		//back.requestFocus();
	}
	
	public void initScore()
	{
		 
		/*Hier wird testweise die optimierte Methode verwendet
		  for(int i = 1; i < l + 1; i++)
		{
			if(entries.spielerNameAusDB(i) != null || entries.spielerNameAusDB(i) != "")
			{
				ScoreLabel score = new ScoreLabel("" + i, entries.spielerNameAusDB(i), "" + entries.getScore(i));
				add(score);
				this.setPosition(score);
			}
		}*/
		java.util.List<Highscoreeintrag> eintraege = Datenbankzugriff.VerbindungGeben().Highscoreeintraegeholen(0, l);
		for (int i=0;i<eintraege.size();i++){
			Highscoreeintrag eintrag = eintraege.get(i);
			ScoreLabel score = new ScoreLabel("" + (i+1), eintrag.name, "" + eintrag.punktezahl);
			add(score);
			this.setPosition(score);
		}
	}
	
	public void setPosition(ScoreLabel score)
	{
		Insets insets = this.getInsets();
		Dimension size = score.getPreferredSize();
		score.setBounds(100 + insets.left, x + 40 + insets.top, size.width, size.height);
		x = x + 40;
	}

	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == back)
		{
			host.showMain();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		BufferedImage bg = null;
		try {
			URL url = getClass().getResource("bg.png");
			bg = ImageIO.read(url);
		}
		catch (IOException e) { System.out.println("exception " + e.getMessage()); }
	    super.paintComponent(g);
	    g.drawImage(bg, 0, 0, this);    	
	    g.finalize();
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_ENTER && back.isFocusOwner() == true)
		{
			host.showMain();
		}
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void setFocus()
	{
		back.requestFocus();
	}
}
