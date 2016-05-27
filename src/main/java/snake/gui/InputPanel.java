package snake.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.io.IOException;

import javax.imageio.ImageIO;


public class InputPanel extends JPanel implements ActionListener, KeyListener
{
	private static final int BOLD = 0;
	private MainForm host;
	private JButton back;
	private JLabel txt;
	private JTextField name;
	private JButton accept;
	
	private String p_name = "";
	
	public InputPanel()
	{
		initComponents();
	}
	
	public InputPanel(MainForm host)
	{
		this.host = host;
		this.setSize(new Dimension(800, 600));
		this.setLayout(null);
		initComponents();
		//name.requestFocus();
	}
	
	public void initComponents()
	{
		back = new JButton("<");
		txt = new JLabel("NAME: ");
		name = new JTextField();
		accept = new JButton("START NEW GAME");
			
		add(back);
		add(txt);
		add(name);
		add(accept);
		
		txt.setFont(new Font("Dialog", BOLD, 20));
		name.setFont(new Font("Dialog", BOLD, 20));
		
		back.addActionListener(this);
		name.addActionListener(this);
		accept.addActionListener(this);
		
		name.addKeyListener(this);
		accept.addKeyListener(this);
		back.addKeyListener(this);
		
		Insets insets = this.getInsets();
		Dimension size = back.getPreferredSize();
		back.setBounds(10 + insets.left, 5 + insets.top, size.width, size.height);
		size = txt.getPreferredSize();
		txt.setBounds(400 - 80 + insets.left - (size.width / 2), 200, size.width, size.height);
		size = name.getPreferredSize();
		name.setBounds(400 - (txt.getWidth() / 2) + insets.left - (size.width / 2), 200 - insets.top, 150, 25);
		size = accept.getPreferredSize();
		accept.setBounds(400 + insets.left - (size.width / 2), 300, size.width, size.height);
		
		//name.requestFocusInWindow();
		//name.setCaretPosition(0);
		
		//p_name = " ";
		
		name.setText("Anonymous");
		//accept.requestFocus();
	}
	
	public String getName()
	{
		if(p_name != "")
		{
			return p_name;			
		}
		else
			return null;	
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == back)
		{
			host.showMain();
		}
		if(e.getSource() == accept)
		{
			p_name = name.getText();  //if no name entered, write anonymous		
			MainForm.getCon().setName(p_name);
			host.startGame();
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
			host.showMain();
		if(e.getKeyCode() == KeyEvent.VK_ENTER && accept.isFocusOwner() == true)
		{
			p_name = name.getText();  //if no name entered, write anonymous		
			MainForm.getCon().setName(p_name);
			host.startGame();
		}
		
		
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void setFocus()
	{
		accept.requestFocus();
	}
}
