package snake.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PauseMenuPanel extends JPanel implements ActionListener
{
	private MainForm host;	//for communication with form
	private static final int BOLD = 0;
	private JLabel title;
	private JButton con;	//button to continue game
	private JButton end;	//button to end game
	
	public PauseMenuPanel ()
	{
		initComponents();
	}
	
	//overlay panel for paused game
	public PauseMenuPanel(MainForm host)
	{
		this.host = host;
		this.setSize(host.getSize());
		this.setLayout(null);
		initComponents();
	}
	
	public void initComponents()
	{
		title = new JLabel("PAUSED");
		con = new JButton("CONTINUE");
		end = new JButton("BACK TO MENU");
		
		title.setFont(new Font("Dialog", BOLD, 30));
		title.setForeground(Color.black);
		
		this.setBackground(new Color(0, 0, 0, 64));
		
		add(title);
		add(con);
		add(end);
		
		con.addActionListener(this);
		end.addActionListener(this);
		
		Insets insets = this.getInsets();
		Dimension size = title.getPreferredSize(); 
		title.setBounds(400 - (size.width / 2) + insets.left, 50 + insets.top, size.width, size.height);
		size = con.getPreferredSize();
		con.setBounds(400 - size.width - 50 + insets.left, 300 + insets.top, size.width, size.height);
		size = end.getPreferredSize();
		end.setBounds(400 + 50 + insets.left, 300 + insets.top, size.width, size.height);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == end)
		{
			//host.endgame
			host.showMain();
		}
		if(e.getSource() == con)
		{
			//timer.continue
			//this.hide
		}
	}

}
