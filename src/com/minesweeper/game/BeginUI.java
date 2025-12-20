package com.minesweeper.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class BeginUI extends JFrame{
	private final int DEFALT_WIDTH = 300;
	private final int DEFALT_HEIGHT = 400;
	private final int DEFALT_X = 400;
	private final int DEFALT_Y = 200;
	private JPanel panel = new JPanel();
	private JRadioButton simple = new JRadioButton("SIMPLE");
	private JRadioButton medium = new JRadioButton("MEDIUM");
	private JRadioButton hard = new JRadioButton("HARD");
	private JRadioButton custom = new JRadioButton("CUSTOM");
	public static final int SIMPLE_MODE = 0;
	public static final int MEDIUM_MODE = 1;
	public static final int HARD_MODE = 2;
	public static final int CUSTOM = 3;
	
	public BeginUI() {
		super();
		setTitle("MineSweeper");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(DEFALT_WIDTH, DEFALT_HEIGHT));
		setLocation(DEFALT_X, DEFALT_Y);
		
		BeginUIInit();
		pack();
		setVisible(true);
	}
	
	private void BeginUIInit() {
		
		panel.setLayout(new GridBagLayout());
		panel.setBorder(BorderFactory.createLineBorder(Color.gray));
		add(panel);
		
		var title = new JLabel("Mine Sweeper");
		title.setFont(new Font("Sancerif",Font.ITALIC,18));
		var history_record = new JButton("RECORD");
		var clear_record = new JButton("Clear Record");
		var start = new JButton("START");
		ButtonGroup levels = new ButtonGroup();
		levels.add(simple);
		levels.add(medium);
		levels.add(hard);
		levels.add(custom);
		simple.setSelected(true);
		
		panel.add(title,new GBC().setDownInsects(25).setIpadX(5));
		panel.add(simple,new GBC().setDownInsects(20).setIpadX(5));
		panel.add(medium,new GBC().setDownInsects(20).setIpadX(0));
		panel.add(hard,new GBC().setDownInsects(20).setIpadX(15));
		panel.add(custom,new GBC().setDownInsects(25).setIpadX(0));
		panel.add(start,new GBC().setDownInsects(20).setIpadX(5));
		panel.add(history_record,new GBC().setDownInsects(10).setIpadX(5));
		panel.add(clear_record,new GBC().setDownInsects(15).setIpadX(5));
		
		history_record.setFocusPainted(false);
		start.setFocusPainted(false);
		
		history_record.addActionListener((e)-> {
			String simple = MainGame.History_Record.get(MainGame.SIMPLE);
			String medium = MainGame.History_Record.get(MainGame.MEDIUM);
			String hard = MainGame.History_Record.get(MainGame.HARD);
			MessageDialogs.showMessageDialog(this, "SIMPLE MODE: "+ simple+" s"+ 
			"    MEDIUM MODE: "+ medium+" s" + "    HARD MODE: "+ hard+" s");
		});
		
		clear_record.addActionListener((e)->{
			if(JOptionPane.showConfirmDialog(this,"Confirm to clear all the historical records?",
					"Confirm to Clear",JOptionPane.OK_CANCEL_OPTION) == MessageDialogs.OK_OPTION) {
				File f = new File("HISTORY");
				f.deleteOnExit();
				MainGame.History_Record.put(MainGame.SIMPLE, "none");
				MainGame.History_Record.put(MainGame.MEDIUM, "none");
				MainGame.History_Record.put(MainGame.HARD, "none");
			}
		});
		start.addActionListener((e)-> {
			int mode = 0;
			if (simple.isSelected()) mode = SIMPLE_MODE; 
			if (medium.isSelected()) mode = MEDIUM_MODE; 
			if (hard.isSelected()) mode = HARD_MODE; 
			if (custom.isSelected()) mode = CUSTOM; 
			MainGame.callGameUI(this, mode);
		});
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				MessageDialogs.showExitConfirm(BeginUI.this);
				if(getDefaultCloseOperation() == EXIT_ON_CLOSE) {
					try(ObjectOutputStream oos = new ObjectOutputStream
							(new FileOutputStream("HISTORY"))){
						oos.writeObject(MainGame.History_Record);
						oos.close();
					} catch (FileNotFoundException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}
			};
		});
		
	}
	
	class GBC extends GridBagConstraints{
		public GBC() {
			this.gridx = REMAINDER;
		}
		
		public GBC setDownInsects(int down_insect) {
			this.insets = new Insets(0, 0, down_insect, 0);
			return this;
		}
		
		public GBC setIpadX(int ipadx) {
			this.ipadx = ipadx;
			return this;
		}
	}
	
}
