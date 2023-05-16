package org.example;

public class Intermediate_mushroom_picker extends Mushroom_picker{
    public Intermediate_mushroom_picker(int score, int position_x, int position_y) {
        super(score, position_x, position_y);
    }

    public static void change_position_after_random_walk(int x, int y, int random_x, int random_y){
        for (int i = 0; i < Variables.intermediateList.size(); i++) {//sprawdzamy który z tych intermediatow ma taka pozycje i zmieniamy mu ją
            if (Variables.intermediateList.get(i).position_x == x && Variables.intermediateList.get(i).position_y == y) {
                Variables.intermediateList.get(i).position_x = random_x;
                Variables.intermediateList.get(i).position_y = random_y;
                break;//wychodzimy bo juz znaleźliśmy
            }
        }
    }
}
