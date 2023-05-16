package org.example;

public class Advanced_mushroom_picker extends Mushroom_picker {
    public Advanced_mushroom_picker(int score, int position_x, int position_y) {
        super(score, position_x, position_y);
    }


    public static void change_position_after_random_walk(int x, int y, int random_x, int random_y){
        for (int i = 0; i < Variables.advancedList.size(); i++) {//sprawdzamy który z tych advancedow ma taka pozycje i zmieniamy mu ją
            if (Variables.advancedList.get(i).position_x == x && Variables.advancedList.get(i).position_y == y) {
                Variables.advancedList.get(i).position_x = random_x;
                Variables.advancedList.get(i).position_y = random_y;
                break;//wychodzimy bo juz znaleźliśmy
            }
        }
    }
}
