package org.example;
import java.util.ArrayList;

public class Main {
    public static void forest_print() {//printowanie lasu
        for (int i = 0; i < Variables.FOREST_HEIGHT; i++) {
            for (int j = 0; j < Variables.FOREST_WIDTH; j++) {
                System.out.print(Variables.PLANSZA.get(i).get(j));
            }
            System.out.println();
        }
    }

    public static void create_a_forest(){//wypelnia X-ami - PLANSZE
        for (int i = 0; i < Variables.FOREST_HEIGHT; i++) {
            ArrayList<String> wiersz = new ArrayList<String>();
            for (int j = 0; j < Variables.FOREST_WIDTH; j++) {
                wiersz.add("X");//cała plansza wypelniona X-ami
            }
            Variables.PLANSZA.add(wiersz);
        }
    }





    public static void main(String[] args) {//dsadasd
        Main main = new Main();
        main.create_a_forest();

        for(int i = 0;i<Variables.NONTOXIC_MUSH; i++){//dopisywanie nowych (healtyhy) grzybow do planszy
            int[] coordinatesOfNontoxic = Nontoxic_mushroom.fullfillment_with_mushrooms("H");
            int coordinate_x = coordinatesOfNontoxic[0];//x
            int coordinate_y = coordinatesOfNontoxic[1];//y
            Nontoxic_mushroom nontoxic = new Nontoxic_mushroom(coordinate_x, coordinate_y);//firstelement to x, secondelement to y
            Variables.nontoxicMushroomList.add(nontoxic);
        }
        for(int i = 0;i<Variables.TOXIC_MUSH; i++){//dopisywanie nowych (toxic) grzybow do planszy
            int[] coordinatesOfToxic = Toxic_mushroom.fullfillment_with_mushrooms("P");
            int coordinate_x = coordinatesOfToxic[0];//x
            int coordinate_y = coordinatesOfToxic[1];//y
            Toxic_mushroom toxic = new Toxic_mushroom(coordinate_x, coordinate_y);//firstelement to x, secondelement to y
            Variables.toxicMushroomList.add(toxic);
        }
        for(int i = 0;i<Variables.HALLUCIN_MUSH; i++){//dopisywanie nowych (hallucin) grzybow do planszy
            int[] coordinatesOfHallucination = Hallucination_mushroom.fullfillment_with_mushrooms("@");
            int coordinate_x = coordinatesOfHallucination[0];//x
            int coordinate_y = coordinatesOfHallucination[1];//y
            Hallucination_mushroom hallucination = new Hallucination_mushroom(coordinate_x, coordinate_y);//firstelement to x, secondelement to y
            Variables.hallucinationMushroomList.add(hallucination);
        }

        for (int i = 0; i < Variables.BEGGINERS_CHARACTERS; i++) {//dopisywanie begginerow do planszy
            int[] coordinatesOfBegginer = Begginer_mushroom_picker.check_and_give_first_position("A");
            int coordinate_x = coordinatesOfBegginer[0];//x
            int coordinate_y = coordinatesOfBegginer[1];//y
            Begginer_mushroom_picker begginer = new Begginer_mushroom_picker(1, coordinate_x, coordinate_y);//firelement to x, secelement to y
            Variables.beginnersList.add(begginer);
        }
        forest_print();


        int iteracja = 0;
        outerLoop:
        while(true) {
            for (int i = 0; i < Variables.beginnersList.size(); i++) {//sprawdza co jest wokół grzybiarzy od 1 do ostatniego!
                Begginer_mushroom_picker.check_the_kind(Variables.beginnersList.get(i).position_x, Variables.beginnersList.get(i).position_y, i);
                if(Variables.TOXIC_MUSH+Variables.NONTOXIC_MUSH == 0){
                    System.out.println("Symulacja zakonczona z powodu zebrania wszystkich grzybow toxic i nontoxic");
                    System.out.println("liczba iteracji: "+iteracja);
                    break outerLoop;
                }
                else if(Variables.beginnersList.size() == 0){
                    System.out.println("Symulacja zakończona - wszyscy begginerzy zginęli");
                    System.out.println("liczba iteracji: "+iteracja);
                    break outerLoop;
                }
            }//nalezy rozpatrzec przypadek gdy ostatni begginer zbierze ostatniego grzyba wtedy jest koniec symulacji z jednocześnie 2 powodów
            System.out.println();
            forest_print();

            for (int i = 0; i < Variables.beginnersList.size(); i++) {//random walk
                Begginer_mushroom_picker.random_walk(Variables.beginnersList.get(i).position_x, Variables.beginnersList.get(i).position_y);//sprawdzamy czy wokól postaci begginer nie ma czasem grzyba jadalnego którego może zebrac
            }
            System.out.println();
            forest_print();
            iteracja++;

        }
    }
}