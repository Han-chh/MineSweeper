package com.minesweeper.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameUI extends JDialog {
	
	private int sum_bombs;
	private static int remain_bombs;
	private Timer timer = new Timer();
	private int level;
	private final int sidelength = 25;
	private int WIDTH = 300;
	private int HEIGHT = 300;
	private final int DEFALT_X = 400;
	private final int DEFALT_Y = 180;
	private int rows;
	private int columns;
	public static float time = 0;
	private JPanel info_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel map_panel = new JPanel(null);
	private static JLabel remain_label = new JLabel("Mines left: " + remain_bombs);
	private JLabel timer_label = new JLabel("TIME: " + time + " s");
	private static boolean pause = false;
	private final int SIMPLE_MODE_BOMBS = 10;
	private final int MEDIUM_MODE_BOMBS = 40;
	private final int HARD_MODE_BOMBS = 99;
	public static boolean isDead = false;
	public static boolean isVictory = false;
	private static boolean customSuccess = false;
	private boolean cancel = false;
	
	private GameUI(JFrame parent, int level){
		super(parent, true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.level = level;
		customSuccess = false;
		setTitle("Mine Sweeper");
		setResizable(false);
		if(this.level == BeginUI.CUSTOM) {
			setCustomGameConfigs();
		}else {
			configGameInfos();
		}
		if(customSuccess) {
			setPreferredSize(calculateSuitableSize());
			setLocation(DEFALT_X, DEFALT_Y);
			setLayout(new BorderLayout());
			setPanels();
			addInfos();
			generateMap();
			pack();
			
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					if((!isDead && !isVictory)) {
						pause = true;
						MessageDialogs.showExitConfirm(GameUI.this);
						if(getDefaultCloseOperation() == DISPOSE_ON_CLOSE) {
							cancel = true;
						}else {
							pause = false;
						}
					}else {
						cancel = true;
					}
					
				}
			});
			
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					time = 0;
					
					while(!cancel) {
						if(isVictory) {
							pause = true;
							MessageDialogs.showSuccessDialog(GameUI.this, level);
						}
						while(pause) {
							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								// TODO 自动生成的 catch 块
								e.printStackTrace();
							}
						}
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
						time =  time + 0.1f;
						String s = String.format("%.1f", time);
						timer_label.setText("TIME: " + s + " s");
						
					}
					this.cancel();
				}
			}, 0);
			
			setVisible(true);
		}
		
	}
	
	public static void setPause(boolean isPause) {
		pause = isPause;
	}
	
	public static boolean isPause() {
		return pause;
	}
	private void setMapSize(int row, int line) {
		rows = row;
		columns = line;
	}
	private void configGameInfos() {
		customSuccess = true;
		switch (level) {
			case BeginUI.SIMPLE_MODE: {
				this.sum_bombs = SIMPLE_MODE_BOMBS;
				setMapSize(9, 9);
				break;
			}
			case BeginUI.MEDIUM_MODE:{
				this.sum_bombs = MEDIUM_MODE_BOMBS;
				setMapSize(16, 16);
				break;
			}
			case BeginUI.HARD_MODE:{
				this.sum_bombs = HARD_MODE_BOMBS;
				setMapSize(16, 30);
				break;
			}
		}
	}
	
	private void setCustomGameConfigs() {
		var dialog = new JDialog(this, true);
		dialog.setBounds(400, 250, 300, 230);
		dialog.setTitle("Custom Mode");
		var title_panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		var input_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		var button_panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		title_panel.setPreferredSize(new Dimension(300,25));
		input_panel.setPreferredSize(new Dimension(300,80));
		button_panel.setPreferredSize(new Dimension(300,53));
		dialog.add(title_panel, BorderLayout.NORTH);
		dialog.add(input_panel, BorderLayout.CENTER);
		dialog.add(button_panel, BorderLayout.SOUTH);
		
		var title = new JLabel("Custom your game configs.");
		title.setFont(new Font("Sancerif",Font.BOLD, 15));
		var row_label = new JLabel("Row: ");
		var column_label = new JLabel("Column: ");
		var bomb_sum_label = new JLabel("Bombs: ");
		
		var row_text = new JTextField(5);
		var column_text = new JTextField(5);
		var bomb_text = new JTextField(5);
		var start = new JButton("Start");
		
		row_text.requestFocus();
		row_text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) column_text.requestFocus();
			}
		});
		column_text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) bomb_text.requestFocus();
			}
		});
		bomb_text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == KeyEvent.VK_ENTER) start(dialog,row_text, column_text, bomb_text);;
			}
		});
		
		start.addActionListener((e) -> {
			start(dialog,row_text, column_text, bomb_text);
		});
		
		dialog.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
		
		title_panel.add(title);
		
		input_panel.add(row_label);
		input_panel.add(row_text);
		input_panel.add(new JLabel("                                                 "));
		
		input_panel.add(column_label);
		input_panel.add(column_text);
		input_panel.add(new JLabel("                                               "));
		
		input_panel.add(bomb_sum_label);
		input_panel.add(bomb_text);
		input_panel.add(new JLabel("                                               "));
		
		button_panel.add(start);
		
		dialog.setVisible(true);
		
	}
	
	private void start(JDialog customconfig,JTextField row, JTextField column, JTextField bombs) {
		int rows;
		int columns;
		int bombs_sum;
		try {
			rows = Integer.valueOf(row.getText());
			columns = Integer.valueOf(column.getText());
			bombs_sum = Integer.valueOf(bombs.getText());
		}catch (NullPointerException e1) {
			MessageDialogs.showWaringMessage(this, "Invalid Input!", "Invalid");
			row.setText("");
			column.setText("");
			bombs.setText("");
			return;
		}catch (NumberFormatException e1) {
			MessageDialogs.showWaringMessage(this, "Must input numbers!", "Invalid");
			row.setText("");
			column.setText("");
			bombs.setText("");
			return;
		}
		
		if(columns > 50) {
			MessageDialogs.showWaringMessage(this, "Columns must be smaller than 50!", "Invalid");
			column.setText("");
			return ;
		}else if(rows > 30) {
			MessageDialogs.showWaringMessage(this, "Rows must be smaller than 30!", "Invalid");
			row.setText("");
			return ;
		}else if(rows < 5 || columns<5){
			MessageDialogs.showWaringMessage(this, "Rows or columns must be larger than 5!", "Invalid");
			row.setText("");
			column.setText("");
			return ;
		}else if(bombs_sum >= rows * columns || bombs_sum <= 0) {
			MessageDialogs.showWaringMessage(this, "Invalid bombs amount!", "Invalid");
			bombs.setText("");
			return ;
		}
		
		this.sum_bombs = bombs_sum;
		setMapSize(rows, columns);
		customSuccess = true;
		customconfig.setVisible(false);
	}
	
	public static void setRemainBombs(int remain) {
		remain_bombs = remain;
		remain_label.setText("Mines left: " + remain_bombs);
	}
	private void setPanels() {
		info_panel.setBorder(BorderFactory.createLineBorder(Color.black));
		map_panel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		info_panel.setPreferredSize(new Dimension(WIDTH, 35));
		map_panel.setPreferredSize(new Dimension(WIDTH, HEIGHT - 72));
		add(info_panel, BorderLayout.NORTH);
		add(map_panel, BorderLayout.SOUTH);
	}
	
	private void addInfos() {
		
		info_panel.add(remain_label);
		info_panel.add(timer_label);
		
	}
	
	private void generateMap() {
		SingleGrid.generate(map_panel,sidelength, rows, columns, sum_bombs);
	}
	
	public static void createGameUI(JFrame parent, int level) {
		new GameUI(parent, level);
	}
	
	private Dimension calculateSuitableSize() {
		
		HEIGHT = sidelength * rows + 72;
		WIDTH = sidelength * columns + 15;
		return(new Dimension(WIDTH, HEIGHT));
	}
	
}
