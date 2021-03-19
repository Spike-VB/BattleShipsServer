package com.spike.BattleShipsServer;

import javax.swing.*;

public class Gui {
	
	private JFrame frame;
	
	public void buildGui() {
    	frame = new JFrame("Battle ships server");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setBounds(600, 200, 400, 300);
    	
    	frame.setVisible(true);
	}
}
