package com.minesweeper.game;

import java.awt.Window;
import java.util.function.Function;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class MessageDialogs extends JOptionPane{
	
	
	public static void showWaringMessage( Window frame,String text,String title) {
		showMessageDialog(frame, text, title, WARNING_MESSAGE);
	}
	
	public static void showExitConfirm(JFrame frame) {
		
		int option = showConfirmDialog(frame, "Confirm leaving?","CONFIRMLEAVING",OK_CANCEL_OPTION);
		if (option == OK_OPTION) {
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		}else {
			frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		}
	}
	
	public static void showExitConfirm(JDialog dialog) {
		
		int option = showConfirmDialog(dialog, "Confirm leaving the game?","CONFIRMLEAVING",OK_CANCEL_OPTION);
		if (option == OK_OPTION) {
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		}else {
			dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		}
	}
	
	public static void showSuccessDialog(JDialog dialog, int mode) {
		String message = new String("You win! Finished in "+ String.format("%.1f", GameUI.time) +" s");
		switch (mode){
			case BeginUI.SIMPLE_MODE:
				if(MainGame.History_Record.get(MainGame.SIMPLE).equals("none")){
					MainGame.History_Record.put(MainGame.SIMPLE, String.format("%.1f", GameUI.time));
					message = new String(message + " New Record!");
				}else if(Float.valueOf(MainGame.History_Record.get(MainGame.SIMPLE)) > GameUI.time){
					message = new String(message + " New Record!");
					MainGame.History_Record.put(MainGame.SIMPLE, String.format("%.1f", GameUI.time));
				}
				break;
			case BeginUI.MEDIUM_MODE:
				if(MainGame.History_Record.get(MainGame.MEDIUM).equals("none")){
					MainGame.History_Record.put(MainGame.MEDIUM, String.format("%.1f", GameUI.time));
					message = new String(message + " New Record!");
				}else if(Float.valueOf(MainGame.History_Record.get(MainGame.MEDIUM)) > GameUI.time){
					message = new String(message + " New Record!");
					MainGame.History_Record.put(MainGame.SIMPLE, String.format("%.1f", GameUI.time));
				}
				break;
			case BeginUI.HARD_MODE:
				if(MainGame.History_Record.get(MainGame.HARD).equals("none")){
					MainGame.History_Record.put(MainGame.HARD, String.format("%.1f", GameUI.time));
					message = new String(message + " New Record!");
				}else if(Float.valueOf(MainGame.History_Record.get(MainGame.HARD)) > GameUI.time){
					message = new String(message + " New Record!");
					MainGame.History_Record.put(MainGame.SIMPLE, String.format("%.1f", GameUI.time));
				}
				break;
		}
		showMessageDialog(dialog, message);
	}
}
