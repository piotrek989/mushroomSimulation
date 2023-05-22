package org.example;
import java.util.ArrayList;

public class Main {
    //kolory
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static void forest_print() {//printowanie lasu
        for (int i = 0; i < Variables.FOREST_HEIGHT; i++) {
            for (int j = 0; j < Variables.FOREST_WIDTH; j++) {
                if (Variables.BOARD.get(i).get(j).equals("X")) { //puste pole
                    System.out.print(WHITE + Variables.BOARD.get(i).get(j) + RESET);
                } else if(Variables.BOARD.get(i).get(j).equals("P")){ //grzyb trujacy
                    System.out.print(RED+Variables.BOARD.get(i).get(j)+RESET);
                }
                else if(Variables.BOARD.get(i).get(j).equals("H")){ //grzyb jadalny
                    System.out.print(GREEN+Variables.BOARD.get(i).get(j)+RESET);
                }
                else if(Variables.BOARD.get(i).get(j).equals("@")){ //grzyb halucynek
                    System.out.print(CYAN+Variables.BOARD.get(i).get(j)+RESET);
                }
                else if(Variables.BOARD.get(i).get(j).equals("B")){ //beginner
                    System.out.print(YELLOW+Variables.BOARD.get(i).get(j)+RESET);
                }
                else if(Variables.BOARD.get(i).get(j).equals("A")){ //advanced
                    System.out.print(PURPLE+Variables.BOARD.get(i).get(j)+RESET);
                }
                else if(Variables.BOARD.get(i).get(j).equals("I")){ //intermediate
                    System.out.print(BLUE+Variables.BOARD.get(i).get(j)+RESET);
                }
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
            Variables.BOARD.add(wiersz);
        }
    }





    public static void main(String[] args) {
        Main main = new Main();
        main.create_a_forest();

        for(int i = 0;i<Variables.NONTOXIC_MUSH; i++){//dopisywanie nowych (healthy) grzybow do planszy
            int[] coordinatesOfNontoxic = Nontoxic_mushroom.fulfillment_with_mushrooms("H");//nontoxic
            int coordinate_x = coordinatesOfNontoxic[0];//x
            int coordinate_y = coordinatesOfNontoxic[1];//y
            Nontoxic_mushroom nontoxic = new Nontoxic_mushroom(coordinate_x, coordinate_y);//firstelement to x, secondelement to y
            Variables.nontoxicMushroomList.add(nontoxic);
        }
        for(int i = 0;i<Variables.TOXIC_MUSH; i++){//dopisywanie nowych (toxic) grzybow do planszy
            int[] coordinatesOfToxic = Toxic_mushroom.fulfillment_with_mushrooms("P");//toxic
            int coordinate_x = coordinatesOfToxic[0];//x
            int coordinate_y = coordinatesOfToxic[1];//y
            Toxic_mushroom toxic = new Toxic_mushroom(coordinate_x, coordinate_y);//firstelement to x, secondelement to y
            Variables.toxicMushroomList.add(toxic);
        }
        for(int i = 0;i<Variables.HALLUCIN_MUSH; i++){//dopisywanie nowych (hallucin) grzybow do planszy
            int[] coordinatesOfHallucination = Hallucination_mushroom.fulfillment_with_mushrooms("@");//hallucination
            int coordinate_x = coordinatesOfHallucination[0];//x
            int coordinate_y = coordinatesOfHallucination[1];//y
            Hallucination_mushroom hallucination = new Hallucination_mushroom(coordinate_x, coordinate_y);//firstelement to x, secondelement to y
            Variables.hallucinationMushroomList.add(hallucination);
        }

        for (int i = 0; i < Variables.beginnerPickers; i++) {//dopisywanie begginerow do planszy
            int[] coordinatesOfBegginer = Beginner_mushroom_picker.check_and_give_first_position("B");
            int coordinate_x = coordinatesOfBegginer[0];//x
            int coordinate_y = coordinatesOfBegginer[1];//y
            Beginner_mushroom_picker begginer = new Beginner_mushroom_picker(1, coordinate_x, coordinate_y);//firelement to x, secelement to y
            Variables.beginnersList.add(begginer);
        }
        for (int i = 0; i < Variables.advancedPickers; i++) {//dopisywanie advancedow do planszy
            int[] coordinatesOfAdvanced = Advanced_mushroom_picker.check_and_give_first_position("A");
            int coordinate_x = coordinatesOfAdvanced[0];//x
            int coordinate_y = coordinatesOfAdvanced[1];//y
            Advanced_mushroom_picker advanced = new Advanced_mushroom_picker(1, coordinate_x, coordinate_y);//firelement to x, secelement to y
            Variables.advancedList.add(advanced);
        }
        for (int i = 0; i < Variables.intermediatePickers; i++) {//dopisywanie inntermediatow do planszy
            int[] coordinatesOfIntermediate = Advanced_mushroom_picker.check_and_give_first_position("I");
            int coordinate_x = coordinatesOfIntermediate[0];//x
            int coordinate_y = coordinatesOfIntermediate[1];//y
            Intermediate_mushroom_picker intermediate = new Intermediate_mushroom_picker(1, coordinate_x, coordinate_y);//firelement to x, secelement to y
            Variables.intermediateList.add(intermediate);
        }
        forest_print();


        int iteracja = 0;
        outerLoop:
        while(true) {
            for (int i = 0,k = 0; i < Variables.beginnersList.size() || k < Variables.intermediateList.size(); i++ ,k++) {//sprawdza co jest wokół grzybiarzy od 1 do ostatniego!
                if(i<Variables.beginnersList.size()) {
                    Beginner_mushroom_picker.check_the_kind(Variables.beginnersList.get(i).position_x, Variables.beginnersList.get(i).position_y, i);
                }
                else if(k<Variables.intermediateList.size()) {
                    Intermediate_mushroom_picker.check_the_kind(Variables.intermediateList.get(k).position_x, Variables.intermediateList.get(k).position_y, k);
                }
                else if(k<Variables.advancedList.size()){
                    Advanced_mushroom_picker.check_the_kind(Variables.advancedList.get(k).position_x, Variables.advancedList.get(k).position_y, k);
                }
                if(Variables.TOXIC_MUSH + Variables.NONTOXIC_MUSH + Variables.HALLUCIN_MUSH == 0){
                    System.out.println("Symulacja zakonczona - wszystkie grzyby zostaly zebrane");
                    System.out.println("liczba iteracji: "+iteracja);
                    break outerLoop;
                }
                else if(Variables.beginnersList.size() + Variables.intermediateList.size() + Variables.advancedList.size() == 0){
                    System.out.println("Symulacja zakonczona - wszyscy zgineli");
                    System.out.println("liczba iteracji: "+iteracja);
                    forest_print();
                    System.out.println(Variables.intermediateList.size());
                    break outerLoop;
                }
                else if(Variables.beginnersList.size() + Variables.intermediateList.size() == 0 && Variables.NONTOXIC_MUSH + Variables.HALLUCIN_MUSH == 0) {
                    System.out.println("Symulacja zakonczona - zostali tylko grzybiarze zaawansowani i trujace grzyby");
                    System.out.println("liczba iteracji: "+iteracja);
                    forest_print();
                    break outerLoop;
                }
                else if(Variables.intermediateList.size() + Variables.advancedList.size() == 0 && Variables.NONTOXIC_MUSH + Variables.TOXIC_MUSH == 0) {
                    System.out.println("Symulacja zakonczona - zostali tylko grzybiarze poczatkujacy i grzyby halucynki");
                    System.out.println("liczba iteracji: "+iteracja);
                    forest_print();
                    break outerLoop;
                }
            }//nalezy rozpatrzec przypadek gdy ostatni beginner zbierze ostatniego grzyba wtedy jest koniec symulacji z jednocześnie 2 powodów
            System.out.println();
            forest_print();

            for (int i = 0; i < Variables.beginnersList.size(); i++) {//random walk
                Beginner_mushroom_picker.random_walk(Variables.beginnersList.get(i).position_x, Variables.beginnersList.get(i).position_y, "B");//sprawdzamy czy wokól postaci begginer nie ma czasem grzyba jadalnego którego może zebrac
            }
            for (int i = 0; i < Variables.intermediateList.size(); i++) {//random walk
                Intermediate_mushroom_picker.random_walk(Variables.intermediateList.get(i).position_x, Variables.intermediateList.get(i).position_y, "I");
            }
            for (int i = 0; i < Variables.advancedList.size(); i++) {//random walk
                Advanced_mushroom_picker.random_walk(Variables.advancedList.get(i).position_x, Variables.advancedList.get(i).position_y, "A");
            }
            System.out.println();
            forest_print();
            iteracja++;
        }
    }
}