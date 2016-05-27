package snake.gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameOverForm extends JFrame implements ActionListener, KeyListener
{
	private JLabel score;
	private JButton end;
	private int p_score;
	
	public GameOverForm(int score)
	{
		getContentPane().setLayout(null);
		setSize(300, 200);
		setPreferredSize(new Dimension(300, 200));
		setLocationRelativeTo(null);
		setVisible(true);
		p_score = score;
		initComponents();
	}
	
	public GameOverForm()
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.out.print("ff");
				MainForm.getCon().endGame(p_score);
				close();
			}
			@Override
			public void windowClosed(WindowEvent e) {
				System.out.print("tt");
			}
		});
		getContentPane().setLayout(null);
		setSize(300, 200);
		setVisible(true);
		initComponents();
	}
	
	public void initComponents()
	{
		score = new JLabel("YOUR SCORE: " + p_score);
		end = new JButton("BACK TO MENU");	
		end.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MainForm.getCon().endGame(p_score);
				//MainForm.getCon().showMain();
				close();
			}
		});
		end.addKeyListener(this);
		
		getContentPane().add(score);
		getContentPane().add(end);
		
		Dimension size = score.getPreferredSize();
		score.setBounds(75, 50, size.width, size.height);
		size = end.getPreferredSize();
		end.setBounds(80, 100, size.width, size.height);
		
		end.requestFocus();
	}
	
	public void close()
	{
		this.dispose();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == this.end)
		{
			System.out.println("end1");
			//p_score = 2;
			MainForm.getCon().endGame(p_score);
			System.out.println("end2");
			this.dispose();
		}
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_ENTER && end.isFocusOwner() == true)
		{
			MainForm.getCon().endGame(p_score);
			this.dispose();
		}
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void setFocus()
	{
		end.requestFocus();
	}
}
