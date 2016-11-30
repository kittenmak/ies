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

    public int oldfindBestElevator(ArrayList<Integer> speedOfElevator, ArrayList<Integer> numberOfDestinationInRelatedQueue, ArrayList<Integer> ElevatorFloor, ArrayList<Integer> directionOfElevator, int directionRequest, int requestFloor, int destination) {
        int bestElevator = -1;
        ArrayList<Integer> ElevatorOption = new ArrayList<Integer>();

        //loop for finding available lifts that can pick up the passenger
        for (int i = 0; i < directionOfElevator.size(); i++) {
            if (directionOfElevator.get(i) == 1) {
                if (ElevatorFloor.get(i) == requestFloor) {
                    bestElevator = i;
                    return bestElevator;
                } else {
                    ElevatorOption.add(i);
                }
            } else if (directionOfElevator.get(i) == directionRequest) {
                if (directionRequest == 0 && ElevatorFloor.get(i) < requestFloor) { //UP
                    //If the lift hasn't passed through the current floor from Request
                    ElevatorOption.add(i);

                } else if (directionRequest == 2 && ElevatorFloor.get(i) > requestFloor) {//DOWN
                    //If the lift hasn't passed through the current floor from Request
                    ElevatorOption.add(i);
                }
            }
        }

        if (ElevatorOption.size() == 0) {

        } else if (ElevatorOption.size() == 1) {
            bestElevator = ElevatorOption.get(0);
        } else {
            for (int i = 0; i < ElevatorOption.size(); i++) {
                int tmp = ElevatorOption.get(0);
                int diff = -1;
                if (directionRequest == 0) {
                }
            }

        }


        if (bestElevator != -1) {
            return bestElevator;
        } else {
            System.out.println("Cant Find the Best Lift");
            return 0;

        }
    }



    public int findBestElevator(ArrayList<Integer> currentFloorOfElevator, ArrayList<Integer> directionOfElevator, ArrayList<Integer> )


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
