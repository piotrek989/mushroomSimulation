package org.example;

public class Advanced_mushroom_picker extends Mushroom_picker {
    public Advanced_mushroom_picker(int score, int position_x, int position_y) {

        super(score, position_x, position_y);
    }


    public static void change_position_after_random_walk(int x, int y, int random_x, int random_y) {
        for (int i = 0; i < Variables.advancedList.size(); i++) {//sprawdzamy który z tych advancedow ma taka pozycje i zmieniamy mu ją
            if (Variables.advancedList.get(i).position_x == x && Variables.advancedList.get(i).position_y == y) {
                Variables.advancedList.get(i).position_x = random_x;
                Variables.advancedList.get(i).position_y = random_y;
                break;//wychodzimy bo juz znaleźliśmy
            }
        }
    }
    public static void interaction_with_hallucination(int x, int y, int around_x, int around_y, int index_of_advanced)
    {
        for(int k = 0 ; k < Variables.hallucinationMushroomList.size() ; k++){//skanujemy po całej liście grzybow halucynkow i czekamy aż pętla natrafi na takowego
            if(Variables.hallucinationMushroomList.get(k).position_x == around_x && Variables.hallucinationMushroomList.get(k).position_y == around_y){
                Variables.BOARD.get(around_x).set(around_y, "X");//ustawienie na planszy że grzyb hallucination jest zjedzony
                Variables.BOARD.get(x).set(y, "X");//ustawienie na planszy że advanced zginal
                Variables.hallucinationMushroomList.remove(k);//usunięcie grzyba halucynka z arraylisty bo został zjedzony
                Variables.HALLUCIN_MUSH--;
                Variables.advancedList.remove(index_of_advanced);//usuniecie advanced z listy bo zginal
                break;
            }
        }
    }
        public static void check_the_kind(int x,int y, int index_of_advanced){
            int around_x = 0;//tutaj mamy pozycje x wokolo postaci
            int around_y = 0;
            outerLoop:
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    around_x = x + i;//tutaj mamy pozycje x wokolo postaci
                    around_y = y + j;//tutaj mamy pozycje y wokolo postaci
                    if (around_x >= 0 && around_y >= 0 && around_x < Variables.FOREST_HEIGHT && around_y < Variables.FOREST_WIDTH) {
                        if (Variables.BOARD.get(around_x).get(around_y).equals("H")) {
                            interaction_with_nontoxic(around_x, around_y);//check if nontoxic jest dziedziczone po mushroompickers dziedziczenie jest!
                            break outerLoop;
                        } else if (Variables.BOARD.get(around_x).get(around_y).equals("@")) {//@ to sa grzyby halucynki
                            interaction_with_hallucination(x, y, around_x, around_y, index_of_advanced);
                            break outerLoop;
                        }
                    }
                }
            }
        }
}
