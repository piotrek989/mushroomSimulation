package org.example;

import java.util.Random;

public abstract class Mushroom_picker {

    public int score;
    public int position_x;
    public int position_y;
    public Mushroom_picker(int score, int position_x, int position_y) {
        this.score = score;
        this.position_x = position_x;//this.position_x daje nam parametry obiektu którego u nas utworzylismy w mainie
        this.position_y = position_y;
    }

    public static int [] check_and_give_first_position(String signOfPicker){

        int[] position = new int[2];
        Random liczba = new Random();
        int losowax = liczba.nextInt(Variables.FOREST_HEIGHT);
        int losoway = liczba.nextInt(Variables.FOREST_WIDTH);
        if ( !Variables.PLANSZA.get(losowax).get(losoway).equals("X")) {
            for (int j = 0; !Variables.PLANSZA.get(losowax).get(losoway).equals("X") ; j++) {//petla losuje aż nie znajdzie takiej pozycji na której można dać postac
                losowax = liczba.nextInt(Variables.FOREST_HEIGHT);
                losoway = liczba.nextInt(Variables.FOREST_WIDTH);
                if(Variables.PLANSZA.get(losowax).get(losoway).equals("X")) {
                    Variables.PLANSZA.get(losowax).set(losoway, signOfPicker);//to postac
                    position[0] = losowax;
                    position[1] = losoway;
                    break;
                }
            }
        }
        Variables.PLANSZA.get(losowax).set(losoway, signOfPicker);
        position[0] = losowax;
        position[1] = losoway;
        return position;
    }

    public static void interaction_with_nontoxic(int around_x, int around_y) {
        for(int k = 0 ; k < Variables.nontoxicMushroomList.size() ; k++){
            if(Variables.nontoxicMushroomList.get(k).position_x == around_x && Variables.nontoxicMushroomList.get(k).position_y == around_y){
                Variables.PLANSZA.get(around_x).set(around_y, "X");//ustawienie na planszy że grzyb healthy jest zjedzony
                Variables.nontoxicMushroomList.remove(k);//usunięcie grzyba healthy z arraylisty bo został zjedzony
                Variables.NONTOXIC_MUSH--;
                break;
            }
        }
    }
}

