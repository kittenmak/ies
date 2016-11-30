package ies;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Kitten on 30/11/2016.
 */
public class Algorithm {

    public ArrayList<Integer> updateUpQueue(ArrayList<Integer> currentUpQueue, int newDestination) {
        ArrayList<Integer> upQueue = new ArrayList<Integer>();
        upQueue = currentUpQueue;
        upQueue.add(newDestination);
        return upQueue;
    }

    public ArrayList<Integer> updateDownQueue(ArrayList<Integer> currentDownQueue, int newDestination) {
        ArrayList<Integer> downQueue = new ArrayList<Integer>();
        downQueue = currentDownQueue;
        downQueue.add(newDestination);
        return downQueue;
    }

    public ArrayList<Integer> sortQueue(ArrayList<Integer> queue) {
        Collections.sort(queue);
        return queue;
    }

    public boolean findDirection(int[] input) {
        if (input[0] > input[1]) {
            return false;
        } else {
            return true;
        }
    }


    public int findBestElevator(ArrayList<Integer> currentFloorOfElevator, ArrayList<Integer> directionOfElevator, int requestFloor, int requestDirection, int topFloor) {
        ArrayList<Integer> diff = new ArrayList<Integer>();

        int choice = 0;
        for (int i = 0; i < directionOfElevator.size(); i++) {

            if (directionOfElevator.get(i) == 1) { //Lift is stop
                if (requestFloor > currentFloorOfElevator.get(i)) {
                    diff.add(requestFloor - currentFloorOfElevator.get(i));
                } else {
                    diff.add(currentFloorOfElevator.get(i) - requestFloor);
                }
            } else { //lift is moving

                if (directionOfElevator.get(i) == requestDirection) { //if the lift has same direction as request
                    if (directionOfElevator.get(i) == 0) { //Lift is going up
                        if (requestFloor > currentFloorOfElevator.get(i)) { //lift hasn't passed through the requested floor
                            diff.add((requestFloor - currentFloorOfElevator.get(i)));
                        } else { //lift has passed through the requested floor
                            diff.add((topFloor - currentFloorOfElevator.get(i)) + (topFloor - requestFloor));
                        }
                    } else { //lift is going down
                        if (requestFloor > currentFloorOfElevator.get(i)) {//lift has passed through the request floor [Lift is going down]
                            diff.add(currentFloorOfElevator.get(i) - requestFloor);
                        } else {
                            diff.add(currentFloorOfElevator.get(i) + requestFloor);
                        }
                    }
                } else {//the lift does not have the same direction as request

                    if (directionOfElevator.get(i) == 0) {//lift is going up
                        diff.add(topFloor - currentFloorOfElevator.get(i) + requestFloor);
                    } else {
                        diff.add(currentFloorOfElevator.get(i) + requestFloor);
                    }
                }
            }
        }

        System.out.println("Option in total: " + diff.size());
        for (int i = 0; i < diff.size(); i++) {
            int minValue = diff.get(i);
            if (minValue < diff.get(i)) {
                minValue = diff.get(i);
                choice = i;
            }
        }
        return choice;
    }


    public static void main(String[] args) {
        int index = -1;
        ArrayList<Integer> test = new ArrayList<Integer>();
        test.add(8);
        test.add(3);
        test.add(9);
        test.add(1);
        test.add(0);
        test.add(0);
        int minValue = test.get(0);
        for (int i = 0; i < test.size(); i++) {
            if (test.get(i) < minValue) {
                minValue = test.get(i);
                index = i;
            }
        }

        System.out.println(index);


    }

}
