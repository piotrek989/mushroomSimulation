package org.example;
import java.util.Random;
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
    public static void check_the_kind(int x, int y, int index_of_intermediate) {
        int around_x = 0;//tutaj mamy pozycje x wokolo postaci
        int around_y = 0;
        outerLoop:
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                around_x = x + i;//tutaj mamy pozycje x wokolo postaci
                around_y = y + j;//tutaj mamy pozycje y wokolo postaci
                if (around_x >= 0 && around_y >= 0 && around_x < Variables.FOREST_HEIGHT && around_y < Variables.FOREST_WIDTH) {
                    if (Variables.PLANSZA.get(around_x).get(around_y).equals("H")) {
                        interaction_with_nontoxic(around_x, around_y);
                        break outerLoop;
                    } else if (Variables.PLANSZA.get(around_x).get(around_y).equals("P")) {//P to sa toxic grzyby
                        interaction_with_toxic(x, y, around_x, around_y, index_of_intermediate);
                        break outerLoop;
                    } else if (Variables.PLANSZA.get(around_x).get(around_y).equals("@")) {//@ to sa halucynogenne grzyby
                        interaction_with_hallucination(x, y, around_x, around_y, index_of_intermediate);
                        break outerLoop;
                    }

                }
            }
        }
    }


    public static void interaction_with_toxic(int x, int y, int around_x, int around_y, int index_of_intermediate){
        for(int k = 0 ; k < Variables.toxicMushroomList.size() ; k++){//skanujemy po całej liście grzybow toxic i czekamy aż pętla natrafi na takowego
            if(Variables.toxicMushroomList.get(k).position_x == around_x && Variables.toxicMushroomList.get(k).position_y == around_y) {
                if (losowanie()) {//jesli losowanie zwroci prawde to wchodzimy do tego if-a(intermediate zbiera grzyba i ginie)
                    Variables.PLANSZA.get(around_x).set(around_y, "X");//ustawienie na planszy że grzyb toxic jest zjedzony
                    Variables.PLANSZA.get(x).set(y, "X");//ustawienie na planszy że intermediate zginal
                    Variables.toxicMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty bo został zjedzony
                    Variables.TOXIC_MUSH--;
                    Variables.intermediateList.remove(index_of_intermediate);//usuniecie begginera z listy bo zginal
                    break;
                }
                else{//intermediate zjada ale nie ginie
                    Variables.PLANSZA.get(around_x).set(around_y, "X");//ustawienie na planszy że grzyb toxic jest zjedzony
                    Variables.toxicMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty bo został zjedzony
                    Variables.TOXIC_MUSH--;
                    break;
                }

            }
        }
    }
    public static boolean losowanie() {//losuje i zwraca true albo false szansa 50/50
        Random random = new Random();
        double losowaWartosc = random.nextDouble();

        if (losowaWartosc < 0.5) {
            return true;
        } else {
            return false;
        }
    }
    public static void interaction_with_hallucination(int x, int y, int around_x, int around_y, int index_of_intermediate){
        for(int k = 0 ; k < Variables.hallucinationMushroomList.size() ; k++){//skanujemy po całej liście grzybow hallucin i czekamy aż pętla natrafi na takowego
            if(Variables.hallucinationMushroomList.get(k).position_x == around_x && Variables.hallucinationMushroomList.get(k).position_y == around_y) {
                if (losowanie()) {//jesli losowanie zwroci prawde to wchodzimy do tego if-a(intermediate zbiera grzyba i ginie)
                    Variables.PLANSZA.get(around_x).set(around_y, "X");//ustawienie na planszy że grzyb hallucin jest zjedzony
                    Variables.PLANSZA.get(x).set(y, "X");//ustawienie na planszy że intermediate zginal
                    Variables.hallucinationMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty bo został zjedzony
                    Variables.HALLUCIN_MUSH--;
                    Variables.intermediateList.remove(index_of_intermediate);//usuniecie intermediate z listy bo zginal
                    break;
                }
                else{//intermediate zjada ale nie ginie
                    Variables.PLANSZA.get(around_x).set(around_y, "X");//ustawienie na planszy że grzyb hallucin jest zjedzony
                    Variables.hallucinationMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty bo został zjedzony
                    Variables.HALLUCIN_MUSH--;
                    break;
                }
            }
        }
    }
}
