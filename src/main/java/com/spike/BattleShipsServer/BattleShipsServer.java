package com.spike.BattleShipsServer;

public class BattleShipsServer {

	public static void main(String[] args) {
		Gui gui = new Gui();
		gui.buildGui();
		Server server = new Server();
		server.startServer();
	}

}
