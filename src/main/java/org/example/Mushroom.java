package org.example;

import java.util.Random;

public class Mushroom {
    public int position_x;
    public int position_y;
    public Mushroom(int position_x, int position_y) {
        this.position_x = position_x;
        this.position_y = position_y;
    }
    public static int[] fulfillment_with_mushrooms(String signOfMushroom) {//to wypelnia ale tylko grzybami
        int[] returnedArrayOfMushroom = new int[2];
        Random number = new Random();
        int random_x = number.nextInt(Variables.FOREST_HEIGHT);
        int random_y = number.nextInt(Variables.FOREST_WIDTH);
        while (!Variables.BOARD.get(random_x).get(random_y).equals("X")) {
            random_x = number.nextInt(Variables.FOREST_HEIGHT);//losuje do skutku czyli trafienia na wylosowane pole X na które można postawic grzyba
            random_y = number.nextInt(Variables.FOREST_WIDTH);
        }
        Variables.BOARD.get(random_x).set(random_y, signOfMushroom);//ustawienie grzyba toxic
        returnedArrayOfMushroom[0] = random_x;
        returnedArrayOfMushroom[1] = random_y;
        return returnedArrayOfMushroom;
    }

}
