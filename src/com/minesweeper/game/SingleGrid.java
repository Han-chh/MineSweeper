package com.minesweeper.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SingleGrid extends JButton{
	
	private static final String NONE = "NONE";
	private static final String BOMB = "BOMB";
	private static final String NUM = "NUM";
	
	private String condition  = new String();
	private Number number = null;
	private static SingleGrid map[][];
	private static int sidelength;
	private static Dimension size = new Dimension(sidelength,sidelength);
	private boolean isSigned = false;
	private ImageIcon flag = new ImageIcon(
			getClass().getResource("/Images/flag.jpg"));

	private ImageIcon bomb = new ImageIcon(
			getClass().getResource("/Images/bomb.jpg"));
	private static int remain_bombs;
	private boolean isOpened = false;
	private static int sum_rows;
	private static int sum_columns;
	private static int sum_bombs;
	
	private static JPanel map_panel = new JPanel();
	
	
	private SingleGrid() {
		super();
		setVisible(false);
		this.condition = NONE; //initiation is none
		this.setBorder(BorderFactory.createLineBorder(Color.gray));
		this.setOpaque(true);
		setPreferredSize(size);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.isMetaDown()) {
					metaClick();
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				setFocusable(true);
				setBorder(BorderFactory.createLineBorder(Color.yellow));
				requestFocus();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				setBorder(BorderFactory.createLineBorder(Color.gray));
				setFocusable(false);
			}
		});
		
		addActionListener((e)-> click(SingleGrid.this));
	}
	
	private JLabel open(){
		this.setBackground(Color.WHITE);
		this.isOpened = true;
		this.setFocusable(false);
		this.setBorder(null);
		return replaceButtonAsLabel(this);
	}

	//map generating method
		public static void generate(JPanel map_panel, int sidelength, int rows,int columns, int bombs) {
			sum_rows = rows;
			sum_columns = columns;
			sum_bombs = bombs;
			SingleGrid.map_panel = map_panel;
			GameUI.isDead = false;
			GameUI.isVictory = false;
			GameUI.setPause(false);
			remain_bombs = bombs;
			GameUI.setRemainBombs(remain_bombs);
			SingleGrid.sidelength = sidelength;
			map_panel.removeAll();
			map_panel.repaint();
			map = new SingleGrid[rows][columns]; 
			for (int i = 0;i<rows;i++) {
				for (int j = 0;j<columns;j++) {
					map[i][j] = new SingleGrid();
					map[i][j].setBounds(j * sidelength, i * sidelength, sidelength, sidelength);
					map_panel.add(map[i][j]);
					map[i][j].setVisible(true);
				}
			}
			
			randomAddBombsAndNumbers(bombs);
		}
		
		private static void randomAddBombsAndNumbers(int bombs) {
			HashSet<int[]> bomb_coor = Algorithms.randomGenerateBombs(map, bombs);
			for(int[] coor :bomb_coor) {
				map[coor[0]][coor[1]].condition = BOMB;
			}
			Algorithms.generateNumbers(map);
		}
		
		private void click(SingleGrid grid) {
			if(!GameUI.isPause()) {
				if(grid.isSigned) {
					grid.setIcon(null);
					if(grid.getIcon() == null){
						grid.isSigned = false;
						remain_bombs++;
						GameUI.setRemainBombs(remain_bombs);
					}
					
				}else {
					
					if(grid.condition.equals(NUM)) {
						var label = grid.open();
						map_panel.add(label);
						map_panel.remove(grid);
						map_panel.repaint();
					} else if(grid.condition.equals(BOMB)){
						for(SingleGrid[] row: map) {
							for(SingleGrid s: row) {
								if(s.condition.equals(BOMB)) s.setIcon(bomb);
							}
						}
						GameUI.isDead = true;
						GameUI.setPause(true);
					}else{
						Algorithms.recursionOpenEmptyGrid(grid);
					}
					if(!GameUI.isDead) {
						GameUI.isVictory = detectWin();
						if(GameUI.isVictory) GameUI.setPause(true);
					}
					
				}
			}
		}
		
		private static JLabel replaceButtonAsLabel(SingleGrid grid) {
			var label = new JLabel();
			label.setBounds(grid.getBounds());
			label.setBackground(Color.white);
			label.setBorder(BorderFactory.createLineBorder(Color.gray));
			label.setHorizontalAlignment(JLabel.CENTER);
			if(grid.condition.equals(NUM)) {
				label.setForeground(grid.number.color);
				label.setText(Integer.toString(grid.number.number));
			}
			label.setFocusable(false);
			return label;
		}
		
		private boolean detectWin() {
			int num = 0;
			for(SingleGrid[] row: map) {
				for(SingleGrid s: row) {
					if(s.condition.equals(BOMB)) {
						continue;
					}else {
						if(s.isOpened) {
							num++;
						}
					}
				}
			}
			if(remain_bombs == 0) {
				return (num == sum_rows*sum_columns-sum_bombs);

			}else {
				return false;
			}
			
		}

		private void metaClick() {
			if(!GameUI.isPause()) {
				if(this.isSigned) {
					this.setIcon(null);
					if(this.getIcon() == null) {
						remain_bombs++;
						GameUI.setRemainBombs(remain_bombs);
						this.isSigned = false;
					}
				}else {
					if(remain_bombs > 0) {
						this.setIcon(flag);
						if(this.getIcon().equals(flag)) {
							this.isSigned = true;
							remain_bombs--;
							GameUI.setRemainBombs(remain_bombs);
							GameUI.isVictory = detectWin();
							if(GameUI.isVictory) GameUI.setPause(true);
						}
					}
					
					
				}
			}
			
		}
		
		 
		
		
		class Algorithms{
			
			static Queue<SingleGrid> recursionQueue = new LinkedList<>();
			
			static HashSet<int[]> randomGenerateBombs(SingleGrid map[][], int sum_bombs) {
				HashSet<int[]> bombs_coor = new HashSet<>();
				int row = map.length;
				int column = map[0].length;
				Random ran = new Random();
				int[] coor;
				int bomb_row;
				int bomb_column;
				boolean same = false;
				for(int i = 0; i<sum_bombs;i++) {
					do {
						same = false;
						coor = new int[2];
						bomb_row = ran.nextInt(row);
						bomb_column = ran.nextInt(column);
						coor[0] = bomb_row;
						coor[1] = bomb_column;
						for(int[]arr: bombs_coor) {
							if(Arrays.equals(arr, coor)) {
								same = true;
							}
						}
					}while(same);
					bombs_coor.add(coor);
				}
				return bombs_coor;
				
			}
			
			static void generateNumbers(SingleGrid[][]map) {
				int row = map.length;
				int column = map[0].length;
				for(int i = 0;i<row;i++) {
					for(int j = 0;j<column;j++) {
						if(map[i][j].condition.equals(BOMB)) {
							continue;
						}else {
							int num = 0;
							 ArrayList<SingleGrid> list = detectSurround(i, j);
							 for(SingleGrid grid:list) {
								 if(grid.condition.equals(BOMB)) num++;
							 }
						    if(num != 0) {
						    	map[i][j].number = Number.values()[num - 1];
							   
							    map[i][j].condition = NUM;
						    }
						    
						}
					}
				}
				
				
			}
			
			static ArrayList<SingleGrid> detectSurround(int i, int j) { //i represents rows; j represents columns
				ArrayList<SingleGrid> list = new ArrayList<>();
				
				if(detectBoundary(i-1, j-1, map.length, map[0].length))list.add(map[i-1][j-1]);
				if(detectBoundary(i-1, j+1, map.length, map[0].length))list.add(map[i-1][j+1]);
				if(detectBoundary(i-1, j, map.length, map[0].length))list.add(map[i-1][j]);
				if(detectBoundary(i, j+1, map.length, map[0].length))list.add(map[i][j+1]);
				if(detectBoundary(i, j-1, map.length, map[0].length))list.add(map[i][j-1]);
				if(detectBoundary(i+1, j-1, map.length, map[0].length))list.add(map[i+1][j-1]);
				if(detectBoundary(i+1, j, map.length, map[0].length))list.add(map[i+1][j]);
				if(detectBoundary(i+1, j+1, map.length, map[0].length))list.add(map[i+1][j+1]);
			    
			    return list;
			}
			
			static boolean detectBoundary(int i,int j,int row,int column) {
				if(i>row-1 || i<0) {
					return false;
				}else if(j>column-1||j<0) {
					return false;
				}
				return true;
			}
			static void recursionOpenEmptyGrid(SingleGrid grid) {
				recursionQueue.clear();
				recursionQueue.offer(grid);
				int row = 0;
				int column = 0;
				for(int i = 0;i<map.length;i++) {
					for(int j = 0;j<map[0].length;j++) {
						if(map[i][j] == recursionQueue.peek()) {
							row = i;
							column = j;
						}
					}
				}
				open(row, column);
				openLeftTopRightDown();
				
			}
			
			static void openLeftTopRightDown() {
				if(recursionQueue.size() > 0) {
					int row = 0;
					int column = 0;
					for(int i = 0;i<map.length;i++) {
						for(int j = 0;j<map[0].length;j++) {
							if(map[i][j] == recursionQueue.peek()) {
								row = i;
								column = j;
							}
						}
					}
					if(detectBoundary(row-1, column, map.length, map[0].length)) {
						open(row-1,column);
					}
					if(detectBoundary(row+1, column, map.length, map[0].length)) {
						open(row+1,column);
					}
					if(detectBoundary(row, column-1, map.length, map[0].length)) {
						open(row,column-1);
					}
					if(detectBoundary(row, column+1, map.length, map[0].length)) {
						open(row,column+1);
					}
				    
					recursionQueue.remove();
					openLeftTopRightDown();
				}
				
			}
			
			static void open(int row,int column) {
				if(!map[row][column].isOpened) {
					map[row][column].open();
					var label = map[row][column].open();
					map_panel.remove(map[row][column]);
					label.setBorder(null);
					map_panel.add(label);
					map_panel.repaint();
					if(map[row][column].condition.equals(NONE)){
						recursionQueue.offer(map[row][column]);
					}
		    	}
			}
		}
}
