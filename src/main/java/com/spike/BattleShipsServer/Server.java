package com.spike.BattleShipsServer;

import java.io.*;
import java.net.*;
import com.spike.BattleShipsLib.*;

public class Server {
	
	private ServerSocket serSock;
	private int numOfClients = 2;
	private Socket[] clientSock = new Socket[numOfClients];
	private Ship[][] ships = new Ship[numOfClients][];

	public void startServer() {
		//bss.startEmptyServer();
		connect();
		acceptClients();
		readShips();
		gameStart();
	}
	
	private void startEmptyServer() {
		try {
			ServerSocket serSock = new ServerSocket(5050);
			while(true) {
				Socket sock = serSock.accept();
			}
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private void connect() {
		try {
			serSock = new ServerSocket(5050);
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private void acceptClients() {
		int c = 0;
		
		while(c < numOfClients) {
			try {
				Socket sock = serSock.accept();
				clientSock[c] = sock;
				c++;
				System.out.println("Client is accepted");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void readShips() {
		for(int i = 0; i < clientSock.length; i++) {
			Socket sock = clientSock[i];
			try {
				ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
				ships[i] = (Ship[]) ois.readObject();
				
				for(int j = 0; j < ships[i].length; j++) {
					ships[i][j].printShip();
					System.out.println("");
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch(ClassNotFoundException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private void gameStart() {
		for(int i = 0; i < clientSock.length; i++) {
			Socket sock = clientSock[i];
			try {
				ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
				int[] position = (int[]) ois.readObject();
				
				System.out.println(position[0] + "-" + position[1]);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch(ClassNotFoundException ex) {
				ex.printStackTrace();
			}
		}
	}

}