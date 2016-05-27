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

public class OptPanel extends JPanel implements ActionListener, KeyListener 
{
	private static final int BOLD = 0;
	private MainForm host;	//for communication with form
	private JButton back;
	private JLabel title;
	
	public OptPanel()
	{
		initComponents();
	}
	
	public OptPanel(MainForm host)
	{
		this.host = host;
		this.setSize(host.getSize());
		this.setLayout(null);
		this.setBackground(Color.blue);
		initComponents();
	}
	
	public void initComponents()
	{
		back = new JButton("<");
		title = new JLabel("SETTINGS");
		
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
		
		//back.requestFocus();
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
