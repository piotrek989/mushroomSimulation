package org.example;

import java.util.Random;

public class Mushroom {
    private int positionX;
    private int positionY;
    public Mushroom(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
    public int getPositionX() {//getter
        return positionX;
    }

    public void setPositionX(int X) {//setter
        positionX = X;
    }

    public int getPositionY() {
        return positionY;
    }
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
    public static int[] fulfillmentWithMushrooms(String signOfMushroom) {//to wypełnia, ale tylko grzybami
        int[] returnedArrayOfMushroom = new int[2];
        Random number = new Random();
        int randomX = number.nextInt(Forest.forestHeight);
        int randomY = number.nextInt(Forest.getForestWidth());
        while (!Forest.board.get(randomX).get(randomY).equals("X")) {
            randomX = number.nextInt(Forest.forestHeight);//losuje do skutku, czyli trafienia na wylosowane pole X, na które można postawić grzyba
            randomY = number.nextInt(Forest.getForestWidth());
        }
        Forest.board.get(randomX).set(randomY, signOfMushroom);//ustawienie grzyba toxic
        returnedArrayOfMushroom[0] = randomX;
        returnedArrayOfMushroom[1] = randomY;
        return returnedArrayOfMushroom;
    }

}
