package com.minesweeper.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import javax.swing.JFrame;

public class MainGame {
	
	public static final String SIMPLE = "SIMPLE";
	public static final String MEDIUM = "MEDIUM";
	public static final String HARD = "HARD";
	public static HashMap<String, String> History_Record = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		File f = new File("HISTORY");
		if(!f.exists()) {
			f.createNewFile();
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			HashMap<String, String> map = new HashMap<>();
			map.put(SIMPLE, "none");
			map.put(MEDIUM, "none");
			map.put(HARD, "none");
			oos.writeObject(map);
			oos.close();
		}
		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
		History_Record = (HashMap<String, String>) ois.readObject();
		ois.close();
		
		new BeginUI();
	}
	
	public static void callGameUI(JFrame parent,int level) {
		GameUI.createGameUI(parent, level);
	}
}
