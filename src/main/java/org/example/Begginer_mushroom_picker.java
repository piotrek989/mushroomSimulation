package org.example;
import java.util.Random;

public class Begginer_mushroom_picker extends Mushroom_picker {


    public Begginer_mushroom_picker(int score, int position_x, int position_y) {
        super(score, position_x, position_y);// za pomocą słowa kluczowego super,inicjalizujemy pola z klasy bazowej:
    }


    public static void random_walk(int x, int y) {//to bedzie rozszerzane ze względu na inne grzyby

        Random liczba = new Random();
        int random_x = x -1 + liczba.nextInt(3);//ta linijka losuje od -1 do 1 i dodaje x
        int random_y = y -1 + liczba.nextInt(3);//ta linijka losuje od -1 do 1 i dodaj y
        if ((random_x >= 0) && (random_y >= 0) && (random_y < Variables.FOREST_WIDTH) && (random_x < Variables.FOREST_HEIGHT) && Variables.PLANSZA.get(random_x).get(random_y).equals("X")) {
            Variables.PLANSZA.get(random_x).set(random_y, "A");//nowa pozycja beginnera
            Variables.PLANSZA.get(x).set(y, "X");//stare pole staje się polem X
                for (int i = 0; i < Variables.beginnersList.size(); i++) {//sprawdzamy który z tych begginerów ma taka pozycje i zmieniamy mu ją
                    if (Variables.beginnersList.get(i).position_x == x && Variables.beginnersList.get(i).position_y == y) {
                        Variables.beginnersList.get(i).position_x = random_x;
                        Variables.beginnersList.get(i).position_y = random_y;
                        break;//wychodzimy bo juz znaleźliśmy
                    }
                }


        }
        else{
            outerLoop:
            for (int k = -1; k <= 1; k++) {
                for (int p = -1; p <= 1; p++) {
                    random_x = k + x;
                    random_y = p + y;
                    if ((random_x >= 0) && (random_y >= 0) && (random_y < Variables.FOREST_WIDTH) && (random_x < Variables.FOREST_HEIGHT) && (Variables.PLANSZA.get(random_x).get(random_y).equals("X"))) {
                        Variables.PLANSZA.get(random_x).set(random_y, "A");
                        Variables.PLANSZA.get(x).set(y, "X");//stare pole staje się polem X
                        for(int i = 0;i<Variables.beginnersList.size();i++){//sprawdzamy który z tych begginerów ma taka pozycje i zmieniamy mu ją
                            if(Variables.beginnersList.get(i).position_x == x && Variables.beginnersList.get(i).position_y == y){
                                Variables.beginnersList.get(i).position_x = random_x;
                                Variables.beginnersList.get(i).position_y = random_y;
                                break;//wychodzimy bo juz znaleźliśmy
                            }
                        }
                        break outerLoop;//wychodzi z dwóch pętli for gdy to mozliwe
                    }
                }
            }
        }
    }

    public static void interaction_with_toxic(int x, int y, int around_x, int around_y, int index_of_begginer){
        for(int k = 0 ; k < Variables.toxicMushroomList.size() ; k++){//skanujemy po całej liście grzybow toxic i czekamy aż pętla natrafi na takowego
            if(Variables.toxicMushroomList.get(k).position_x == around_x && Variables.toxicMushroomList.get(k).position_y == around_y){
                Variables.PLANSZA.get(around_x).set(around_y, "X");//ustawienie na planszy że grzyb toxic jest zjedzony
                Variables.PLANSZA.get(x).set(y, "X");//ustawienie na planszy że begginer zginal
                Variables.toxicMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty bo został zjedzony
                Variables.TOXIC_MUSH--;
                Variables.beginnersList.remove(index_of_begginer);//usuniecie begginera z listy bo zginal
                break;
            }
        }
    }

    public static void check_the_kind(int x, int y, int index_of_begginer){
        int around_x = 0;//tutaj mamy pozycje x wokolo postaci
        int around_y = 0;
        outerLoop:
        for(int i = -1 ;i<=1;i++){
            for (int j = -1; j<=1 ; j++){
                around_x = x + i;//tutaj mamy pozycje x wokolo postaci
                around_y = y + j;//tutaj mamy pozycje y wokolo postaci
                if (around_x >= 0 && around_y >= 0 && around_x < Variables.FOREST_HEIGHT && around_y < Variables.FOREST_WIDTH){
                    if(Variables.PLANSZA.get(around_x).get(around_y).equals("H")){
                        interaction_with_nontoxic(around_x, around_y);//check if nontoxic jest dziedziczone po mushroompickers dziedziczenie jest!
                        break outerLoop;
                    }
                    else if(Variables.PLANSZA.get(around_x).get(around_y).equals("P")) {//P to sa toxic grzyby
                        interaction_with_toxic(x, y, around_x, around_y, index_of_begginer);
                        break outerLoop;
                    }
                    else if(Variables.PLANSZA.get(around_x).get(around_y).equals("@")) {//@ to sa halucynogenne grzyby//szerze to to można wywalic
                        interaction_with_hallucination(around_x, around_y);
                        //break outerLoop;pomimo że to jest to nie może tego byc bo begginer nic nie robi z tym grzybem
                    }

                }
            }
        }
    }
    public static void interaction_with_hallucination(int x, int y){//funkcja bezuzyteczna dla beginner bo on i tak nic nie robi z tym grzybem
        System.out.println("coś");
    }

}

