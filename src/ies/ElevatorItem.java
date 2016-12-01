package ies;

import java.util.ArrayList;

public class ElevatorItem {

	int EID = 0;
	int currentFloor = 0;
	//	double maxWeight = 0;
//	double currentWeight = 0;
	double idleTimer = 0;
	//	double doorTimer = 0;
//	double ETA = 0;
	int status = 1;
	//	int port = 0;
//	String host = "";
	ArrayList<Integer> upQueue = new ArrayList<Integer>();
	ArrayList<Integer> downQueue = new ArrayList<Integer>();
	int direction = 1;

	public ElevatorItem(int EID, int currentFloor, double idleTimer, int status, ArrayList<Integer> upQueue, ArrayList<Integer> downQueue, int direction){
		this.EID = EID;
		this.currentFloor = currentFloor;
		this.idleTimer = idleTimer;
		this.status = status;
		this.upQueue = upQueue;
		this.downQueue = downQueue;
		this.direction = direction;
	}

	public int getEID() {
		return EID;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public double getIdleTimer() {
		return idleTimer;
	}

	public int getStatus() {
		return status;
	}

	public ArrayList<Integer> getUpQueue() {
		return upQueue;
	}

	public ArrayList<Integer> getDownQueue() {
		return downQueue;
	}

	public int getDirection() {
		return direction;
	}

	public void setEID(int EID) {
		this.EID = EID;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

	public void setIdleTimer(double idleTimer) {
		this.idleTimer = idleTimer;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setUpQueue(ArrayList<Integer> upQueue) {
		this.upQueue = upQueue;
	}

	public void setDownQueue(ArrayList<Integer> downQueue) {
		this.downQueue = downQueue;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
}
