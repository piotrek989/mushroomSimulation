package org.example;

import java.util.Random;

public class Mushroom {
    public int position_x;
    public int position_y;
    public Mushroom(int position_x, int position_y) {
        this.position_x = position_x;
        this.position_y = position_y;
    }
    public static int[] fullfillment_with_mushrooms(String signOfMushroom) {//to wypelnia ale tylko grzybami
        int[] returnedArrayOfHallucination = new int[2];
        Random liczba = new Random();
        int losowax = liczba.nextInt(Variables.FOREST_HEIGHT);
        int losoway = liczba.nextInt(Variables.FOREST_WIDTH);
        while (!Variables.PLANSZA.get(losowax).get(losoway).equals("X")) {
            losowax = liczba.nextInt(Variables.FOREST_HEIGHT);//losuje do skutku czyli trafienia na wylosowane pole X na które można postawic grzyba
            losoway = liczba.nextInt(Variables.FOREST_WIDTH);
        }
        Variables.PLANSZA.get(losowax).set(losoway, signOfMushroom);//ustawienie grzyba toxic
        returnedArrayOfHallucination[0] = losowax;
        returnedArrayOfHallucination[1] = losoway;
        return returnedArrayOfHallucination;
    }

}
