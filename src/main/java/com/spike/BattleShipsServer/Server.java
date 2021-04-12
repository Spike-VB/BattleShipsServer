package com.spike.BattleShipsServer;

import java.io.*;
import java.net.*;
import com.spike.BattleShipsLib.*;

public class Server {
	
	private ServerSocket serSock;
	private int numOfClients = 2;
	private Socket[] clientSock = new Socket[numOfClients];
	private ObjectInputStream[] ois = new ObjectInputStream[numOfClients];
	private ObjectOutputStream[] oos = new ObjectOutputStream[numOfClients];
	private Ship[][] ships = new Ship[numOfClients][];

	public void startServer() {
		connect();
		acceptClients();
		readShips();
		gameStart();
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
		int i = 0;
		
		while(i < numOfClients) {
			try {
				clientSock[i] = serSock.accept();
				oos[i] = new ObjectOutputStream(clientSock[i].getOutputStream());
				ois[i] = new ObjectInputStream(clientSock[i].getInputStream());
				i++;
				System.out.println("Client is accepted");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void readShips() {
		for(int i = 0; i < numOfClients; i++) {
			try {
				ships[i] = (Ship[]) ois[i].readObject();
			}
			catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void printPositions(int[][] position) {
		for(int[] p : position) {
			System.out.println(p[0] + "-" + p[1]);
		}
	}
	
	public void gameStart() {
		int[] position = new int[2];
		
		int client = firstClientNum();
		
		while(true) {
			
			try {
				position = (int[]) ois[client].readObject();
			}
			catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}

			for(int i = 0; i < numOfClients; i++) {
				if(i != client) {
					
					boolean hit = false;
					boolean killed = false;
					
					for(Ship s : ships[i]) {
						hit = s.isHit(position[0], position[1]);
						killed = s.isKilled();
						if(hit) {
							break;
						}
					}
					
					try {
						oos[client].writeObject(new HitResponse(hit, killed));
						oos[i].writeObject(new WaitingResponse(hit, position));
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					if(!hit) {
						client = i;
					}
				}
			}
		}
	}
	
	private int firstClientNum() {
		//int c;
		//while((c = (int) (Math.ceil(Math.random() * 10) - 1) % numOfClients) == -1) {}
		int c = Math.random() < 0.5d ? 0 : 1;
		return c;
	}

}