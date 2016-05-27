package snake.gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainMenuPanel extends JPanel implements ActionListener, KeyListener
{
	private MainForm host;	//for communication with form
	private JButton start;	//button to start the game
	private JButton score;	//button for highscore
	private JButton opt;	//button for settings	
	private JButton exit;   //quit button
	
	public MainMenuPanel()
	{
		initComponents();
	}
	
	public MainMenuPanel(MainForm host)
	{
		this.host = host;
		this.setSize(host.getSize());
		setLayout(null);
		initComponents();
	}
	
	public void initComponents()
	{		
		start = new JButton("Start");
		score = new JButton("Highscore");
		opt = new JButton("Settings");
		exit = new JButton("QUIT");
		
		start.addActionListener(this);
		score.addActionListener(this);
		opt.addActionListener(this);
		exit.addActionListener(this);
		
		start.addKeyListener(this);
		score.addKeyListener(this);
		opt.addKeyListener(this);
		exit.addKeyListener(this);
		
		add(start);
		add(score);
		add(opt);
		add(exit);
		
		//position the components manually without layout-manager
		Insets insets = this.getInsets();
		Dimension size = start.getPreferredSize();
		start.setBounds(400 + insets.left - (size.width / 2), 175 + insets.top, size.width, size.height);
		size = score.getPreferredSize();
		score.setBounds(400 + insets.left - (size.width / 2), 225 + insets.top, size.width, size.height);
		size = opt.getPreferredSize();
		opt.setBounds(400 + insets.left - (size.width / 2), 275 + insets.top, size.width, size.height);	
		size = exit.getPreferredSize();
		exit.setBounds(400 + insets.left - (size.width / 2), 325 + insets.top, size.width, size.height);	
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		BufferedImage bg = null;
		try {
		  System.out.println("read bg image");
			File file = new File("src/main/resources/snake/gui/bg.png");
			System.out.println(file.getPath());
			System.out.println("read bg image 2");
			bg = ImageIO.read(file);
			System.out.println("read bg image 3");
		}
		catch (IOException e) { 
		    System.out.println("error!");
		    System.out.println("exception " + e.getMessage()); 
		    e.printStackTrace();
		}
		catch(Exception ex) {
		  System.out.println("error");
		  ex.printStackTrace();
		}
	    super.paintComponent(g);
	    g.drawImage(bg, 0, 0, this);    	
	    g.finalize();
	}
		
	public void setvisible(boolean visible)
	{
		this.setvisible(visible);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.start)
		{
			host.showInpt();
			//MainForm.getCon().showInpt();
		}
		if(e.getSource() == this.score)
		{
			host.showHighScore();
		}
		if(e.getSource() == this.opt)
		{
			host.showOpts();
		}
		if(e.getSource() == this.exit)
		{
			host.cleanup();
		}
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_ENTER && start.isFocusOwner() == true)
		{
			host.showInpt();
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER && score.isFocusOwner() == true)
		{
			host.showHighScore();
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER && opt.isFocusOwner() == true)
		{
			host.showOpts();
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER && exit.isFocusOwner() == true)
		{
			host.cleanup();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN && start.isFocusOwner() == true)
			score.requestFocus();
		if(e.getKeyCode() == KeyEvent.VK_DOWN && score.isFocusOwner() == true)
			opt.requestFocus();
		if(e.getKeyCode() == KeyEvent.VK_DOWN && opt.isFocusOwner() == true)
			exit.requestFocus();
		if(e.getKeyCode() == KeyEvent.VK_DOWN && exit.isFocusOwner() == true)
			start.requestFocus();
		
		if(e.getKeyCode() == KeyEvent.VK_UP && start.isFocusOwner() == true)
			exit.requestFocus();
		if(e.getKeyCode() == KeyEvent.VK_UP && score.isFocusOwner() == true)
			start.requestFocus();
		if(e.getKeyCode() == KeyEvent.VK_UP && opt.isFocusOwner() == true)
			score.requestFocus();
		if(e.getKeyCode() == KeyEvent.VK_UP && exit.isFocusOwner() == true)
			opt.requestFocus();
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void setFocus()
	{
		start.requestFocus();
	}
	
}
