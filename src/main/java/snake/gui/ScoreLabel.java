package snake.gui;

import javax.swing.*;

import java.awt.*;

public class ScoreLabel extends JPanel 
{
	private static final int BOLD = 0;
	private JLabel no;
	private JLabel name;
	private JLabel score;
	
	private String rank;
	private String Name;
	private String Score;
	private int x;
	
	public ScoreLabel(String no, String name, String score)
	{			
		this.rank = no;
		this.Name = name;
		this.Score = score;
		
		this.setSize(600, 25);
		this.setLayout(null);
		this.setBackground(new Color(0, 0, 0, 32));
		
		/*
		this.no = new JLabel(rank);
		this.name = new JLabel(Name);
		this.score = new JLabel(Score);
		
		add(this.no);
		add(this.name);
		add(this.score);
		
		this.no.setFont(new Font("Dialog", BOLD, 20));
		this.name.setFont(new Font("Dialog", BOLD, 20));
		this.score.setFont(new Font("Dialog", BOLD, 20));
		
		this.no.setHorizontalAlignment(SwingConstants.CENTER);
		this.name.setHorizontalAlignment(SwingConstants.CENTER);
		this.score.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.no.setBounds(5, 0, 75, 26);
		this.name.setBounds(300 - 75 , 0, 150, 26);
		this.score.setBounds(445, 0, 150, 26);
		*/
		
		initComponents();
	}
	
	public void initComponents()
	{
		this.no = new JLabel(rank);
		this.name = new JLabel(Name);
		this.score = new JLabel(Score);
		
		add(this.no);
		add(this.name);
		add(this.score);
		
		this.no.setFont(new Font("Dialog", BOLD, 20));
		this.name.setFont(new Font("Dialog", BOLD, 20));
		this.score.setFont(new Font("Dialog", BOLD, 20));
		
		this.no.setHorizontalAlignment(SwingConstants.CENTER);
		this.name.setHorizontalAlignment(SwingConstants.CENTER);
		this.score.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.no.setBounds(5, 0, 75, 26);
		this.name.setBounds(300 - 75 , 0, 150, 26);
		this.score.setBounds(445, 0, 150, 26);
	}
}
