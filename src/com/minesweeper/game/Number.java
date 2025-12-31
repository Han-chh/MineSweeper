package com.minesweeper.game;

import java.awt.Color;

public enum Number{
	ONE(1, Color.black), TWO(2, Color.pink), THERE(3, Color.red), 
	FOUR(4, Color.blue), FIVE(5 ,Color.darkGray), SIX(6, Color.magenta), SEVEN(7,Color.darkGray), EIGHT(8, Color.black);
	
	int number;
	Color color;
	
	Number(int number, Color c) {
		this.number = number;
		this.color = c;
	}
}
