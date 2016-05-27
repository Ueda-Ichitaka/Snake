package snake.gui;

import javax.swing.*;

import snake.game.GameFrame;
import snake.game.Spielfenster;
import snake.game.GameGenerator;

import java.awt.*;
import java.util.Properties;

import snake.database.*;

public class MainForm extends JFrame
{
	public MainMenuPanel mm;
	public ScorePanel sp;
	public OptPanel op;
	public InputPanel ip;
	CardLayout cl;
	
	private Properties properties;
	
	public Properties getProperties() {
		return properties;
	}

	private static MainForm mainform = new MainForm();
	
	private String p_name;
	private int p_score;
	
	public MainForm()
	{
		setTitle("Snake Launcher");
		setResizable(false);
		
		cl = new CardLayout();
		getContentPane().setLayout(cl);

		mm = new MainMenuPanel(this);		
		op = new OptPanel(this);		
		sp = new ScorePanel(this);
		ip = new InputPanel(this);
		
		getContentPane().add(mm);
		getContentPane().add(sp);
		getContentPane().add(op);								
		getContentPane().add(ip);
		
		cl.addLayoutComponent(mm, "mm");
		cl.addLayoutComponent(op, "op");
		cl.addLayoutComponent(sp, "sp");
		cl.addLayoutComponent(ip, "ip");
		properties = new Properties();
		//entry = new Highscoreeintrag();
		//new GameOverForm(0);
		

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);	
	}
	
	public static void main(String[] args)
	{		
		MainForm mf = new MainForm();
		mf.pack();
		mf.setSize(800, 600);
		mf.setPreferredSize(new Dimension(800, 600));
		mf.setVisible(true);
		mf.setLocationRelativeTo(null);
	}
	
	public void showHighScore()
	{
		cl.show(this.getContentPane(), "sp");
		sp.setFocus();
	}
	
	public void showOpts()
	{
		cl.show(this.getContentPane(), "op");
		op.setFocus();
	}
	
	public void showInpt()
	{
		cl.show(this.getContentPane(), "ip");
		ip.setFocus();
	}
	
	public void startGame()
	{
		this.setVisible(false);
		//new Game(ref this);
		
		Spielfenster fenster = new Spielfenster(GameGenerator.generate_game(),500,500);
		new GameFrame(fenster).setVisible(true);
		
		System.out.println("Name: " + ip.getName());
		p_name = ip.getName();		
	}
	
	public void showMain()
	{
		this.setVisible(true);
		cl.show(this.getContentPane(), "mm");
		mm.setFocus();
	}
	
	public void endGame(int score)
	{
		p_score = score;

		try
		{
			Highscoreeintrag.neuerSpieler(p_name, p_score);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		
		MainForm mf = new MainForm();
		mf.pack();
		mf.setSize(800, 600);
		mf.setPreferredSize(new Dimension(800, 600));
		mf.setVisible(true);
		mf.setLocationRelativeTo(null);
	}
	
	public void setName(String name)
	{
		p_name = name;
		//p_name = ip.getName();
	}
	
	public static MainForm getCon()
	{
		return mainform;
	}
	
	public void cleanup()
	{
		this.dispose();
	}
	
	
}


	

	
	



