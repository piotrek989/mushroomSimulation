package org.example;

import java.util.Random;

public class Mushroom {
    public int positionX;
    public int positionY;
    public Mushroom(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
    public static int[] fulfillmentWithMushrooms(String signOfMushroom) {//to wypełnia, ale tylko grzybami
        int[] returnedArrayOfMushroom = new int[2];
        Random number = new Random();
        int randomX = number.nextInt(Variables.forestHeight);
        int randomY = number.nextInt(Variables.forestWidth);
        while (!Variables.board.get(randomX).get(randomY).equals("X")) {
            randomX = number.nextInt(Variables.forestHeight);//losuje do skutku, czyli trafienia na wylosowane pole X, na które można postawić grzyba
            randomY = number.nextInt(Variables.forestWidth);
        }
        Variables.board.get(randomX).set(randomY, signOfMushroom);//ustawienie grzyba toxic
        returnedArrayOfMushroom[0] = randomX;
        returnedArrayOfMushroom[1] = randomY;
        return returnedArrayOfMushroom;
    }

}
