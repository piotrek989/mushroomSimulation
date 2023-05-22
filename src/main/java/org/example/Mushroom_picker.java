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
        //szukanie pozycji dla grzybiarza
        int[] position = new int[2];
        Random liczba = new Random();
        int random_x = liczba.nextInt(Variables.FOREST_HEIGHT);
        int random_y = liczba.nextInt(Variables.FOREST_WIDTH);
        if ( !Variables.BOARD.get(random_x).get(random_y).equals("X")) {
            for (int j = 0; !Variables.BOARD.get(random_x).get(random_y).equals("X") ; j++) {//petla losuje aż nie znajdzie takiej pozycji na której można dać postac
                random_x = liczba.nextInt(Variables.FOREST_HEIGHT);
                random_y = liczba.nextInt(Variables.FOREST_WIDTH);
                if(Variables.BOARD.get(random_x).get(random_y).equals("X")) {
                    Variables.BOARD.get(random_x).set(random_y, signOfPicker);//to postac
                    position[0] = random_x;
                    position[1] = random_y;
                    break;
                }
            }
        }
        Variables.BOARD.get(random_x).set(random_y, signOfPicker);
        position[0] = random_x;
        position[1] = random_y;
        return position;
    }

    public static void interaction_with_nontoxic(int around_x, int around_y) {
        for(int k = 0 ; k < Variables.nontoxicMushroomList.size() ; k++){
            if(Variables.nontoxicMushroomList.get(k).position_x == around_x && Variables.nontoxicMushroomList.get(k).position_y == around_y){
                Variables.BOARD.get(around_x).set(around_y, "X");//ustawienie na planszy że grzyb healthy jest zjedzony
                Variables.nontoxicMushroomList.remove(k);//usunięcie grzyba healthy z arraylisty bo został zjedzony
                Variables.NONTOXIC_MUSH--;
                break;
            }
        }
    }

    public static void random_walk(int x, int y, String signOfPicker) {//to metoda dziedziczona przez innych zbieraczy

        Random liczba = new Random();

        int iteracja = 0;
        do {
            int random_x = x - 1 + liczba.nextInt(3);//ta linijka losuje od -1 do 1 i dodaje x
            int random_y = y - 1 + liczba.nextInt(3);//ta linijka losuje od -1 do 1 i dodaj y
            if ((random_x >= 0) && (random_y >= 0) && (random_y < Variables.FOREST_WIDTH) && (random_x < Variables.FOREST_HEIGHT) && Variables.BOARD.get(random_x).get(random_y).equals("X")) {
                Variables.BOARD.get(random_x).set(random_y, signOfPicker);//nowa pozycja beginnera
                Variables.BOARD.get(x).set(y, "X");//stare pole staje się polem X
                if (signOfPicker.equals("B")) {//beginner
                    Beginner_mushroom_picker.change_position_after_random_walk(x, y, random_x, random_y);
                } else if (signOfPicker.equals("I")) {//intermediate
                    Intermediate_mushroom_picker.change_position_after_random_walk(x, y, random_x, random_y);
                } else if (signOfPicker.equals("A")) {//advanced
                    Advanced_mushroom_picker.change_position_after_random_walk(x, y, random_x, random_y);
                }
            break;
            }
            iteracja++;
        }while(true && iteracja <= 10);//zakladamy że po 10 losowaniach nie ma pola takiego aby postawić tam postac i ona nie porusza sie
    }
}

