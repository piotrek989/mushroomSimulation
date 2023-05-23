package org.example;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;

//do zrobienia jeszcze:
//-score każdego z grzybiarzy, którzy przeżyli
//-naprawienie, żeby działało dla kilku grzybiarzy o różnych poziomach
//-wizualizacja symulacji

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
    public static void forestPrint() {//printowanie lasu
        for (int i = 0; i < Variables.forestHeight; i++) {
            for (int j = 0; j < Variables.forestWidth; j++) {
                if (Variables.board.get(i).get(j).equals("X")) { //puste pole
                    System.out.print(WHITE + Variables.board.get(i).get(j) + RESET);
                } else if(Variables.board.get(i).get(j).equals("P")){ //grzyb trujący
                    System.out.print(RED+Variables.board.get(i).get(j)+RESET);
                }
                else if(Variables.board.get(i).get(j).equals("H")){ //grzyb jadalny
                    System.out.print(GREEN+Variables.board.get(i).get(j)+RESET);
                }
                else if(Variables.board.get(i).get(j).equals("@")){ //grzyb halucynek
                    System.out.print(CYAN+Variables.board.get(i).get(j)+RESET);
                }
                else if(Variables.board.get(i).get(j).equals("B")){ //beginner
                    System.out.print(YELLOW+Variables.board.get(i).get(j)+RESET);
                }
                else if(Variables.board.get(i).get(j).equals("A")){ //advanced
                    System.out.print(PURPLE+Variables.board.get(i).get(j)+RESET);
                }
                else if(Variables.board.get(i).get(j).equals("I")){ //intermediate
                    System.out.print(BLUE+Variables.board.get(i).get(j)+RESET);
                }
            }
            System.out.println();
        }
    }

    public static void createForest(){//wypelnia X-ami planszę
        for (int i = 0; i < Variables.forestHeight; i++) {
            ArrayList<String> row = new ArrayList<String>();
            for (int j = 0; j < Variables.forestWidth; j++) {
                row.add("X");//cała plansza wypelniona X-ami
            }
            Variables.board.add(row);
        }
    }





    public static void main(String[] args) {
        //if sprawdza poprawność zadanych wartości (tak żeby wszystko zmieściło się na planszy)
        if (Variables.forestWidth > 0 && Variables.forestWidth <= 100
                && Variables.forestHeight > 0 && Variables.forestHeight <=100
                && Variables.percentOfToxic <= 100 && Variables.percentOfToxic >= 0
                && Variables.percentOfHallucination <= 100 && Variables.percentOfHallucination >= 0
                && Variables.mushrooms > 0
                && Variables.mushrooms <= Variables.forestWidth * Variables.forestHeight - 1
                && Variables.beginnerPickers >= 0
                && Variables.beginnerPickers <= Variables.forestWidth * Variables.forestHeight - Variables.mushrooms
                && Variables.advancedPickers >= 0
                && Variables.advancedPickers <= Variables.forestWidth * Variables.forestHeight - Variables.mushrooms - Variables.beginnerPickers
                && Variables.intermediatePickers >= 0
                && Variables.intermediatePickers <= Variables.forestWidth * Variables.forestHeight - Variables.mushrooms - Variables.beginnerPickers - Variables.advancedPickers) {
            Main main = new Main();
            main.createForest();

            for (int i = 0; i < Variables.nontoxicMush; i++) {//dopisywanie nowych (healthy) grzybów do planszy
                int[] coordinatesOfNontoxic = NontoxicMushroom.fulfillmentWithMushrooms("H");//nontoxic
                int coordinateX = coordinatesOfNontoxic[0];//x
                int coordinateY = coordinatesOfNontoxic[1];//y
                NontoxicMushroom nontoxic = new NontoxicMushroom(coordinateX, coordinateY);//firstelement to x, secondelement to y
                Variables.nontoxicMushroomList.add(nontoxic);
            }
            for (int i = 0; i < Variables.toxicMush; i++) {//dopisywanie nowych (toxic) grzybów do planszy
                int[] coordinatesOfToxic = ToxicMushroom.fulfillmentWithMushrooms("P");//toxic
                int coordinateX = coordinatesOfToxic[0];//x
                int coordinateY = coordinatesOfToxic[1];//y
                ToxicMushroom toxic = new ToxicMushroom(coordinateX, coordinateY);//firstelement to x, secondelement to y
                Variables.toxicMushroomList.add(toxic);
            }
            for (int i = 0; i < Variables.hallucinationMush; i++) {//dopisywanie nowych (hallucination) grzybów do planszy
                int[] coordinatesOfHallucination = HallucinationMushroom.fulfillmentWithMushrooms("@");//hallucination
                int coordinateX = coordinatesOfHallucination[0];//x
                int coordinateY = coordinatesOfHallucination[1];//y
                HallucinationMushroom hallucination = new HallucinationMushroom(coordinateX, coordinateY);//firstelement to x, secondelement to y
                Variables.hallucinationMushroomList.add(hallucination);
            }

            for (int i = 0; i < Variables.beginnerPickers; i++) {//dopisywanie begginerów do planszy
                int[] coordinatesOfBegginer = BeginnerMushroomPicker.checkAndGiveFirstPosition("B");
                int coordinateX = coordinatesOfBegginer[0];//x
                int coordinateY = coordinatesOfBegginer[1];//y
                BeginnerMushroomPicker begginer = new BeginnerMushroomPicker(1, coordinateX, coordinateY);//firelement to x, secelement to y
                Variables.beginnersList.add(begginer);
            }
            for (int i = 0; i < Variables.advancedPickers; i++) {//dopisywanie advanced do planszy
                int[] coordinatesOfAdvanced = AdvancedMushroomPicker.checkAndGiveFirstPosition("A");
                int coordinateX = coordinatesOfAdvanced[0];//x
                int coordinateY = coordinatesOfAdvanced[1];//y
                AdvancedMushroomPicker advanced = new AdvancedMushroomPicker(1, coordinateX, coordinateY);//firelement to x, secelement to y
                Variables.advancedList.add(advanced);
            }
            for (int i = 0; i < Variables.intermediatePickers; i++) {//dopisywanie intermediate do planszy
                int[] coordinatesOfIntermediate = AdvancedMushroomPicker.checkAndGiveFirstPosition("I");
                int coordinateX = coordinatesOfIntermediate[0];//x
                int coordinateY = coordinatesOfIntermediate[1];//y
                IntermediateMushroomPicker intermediate = new IntermediateMushroomPicker(1, coordinateX, coordinateY);//firelement to x, secelement to y
                Variables.intermediateList.add(intermediate);
            }
            forestPrint();


            int iteraction = 0;
            outerLoop:
            while (true) {
                for (int i = 0, k = 0, m = 0; m < Variables.advancedList.size() || i < Variables.beginnersList.size() || k < Variables.intermediateList.size(); i++, k++, m++) {//sprawdza co jest wokół grzybiarzy od 1 do ostatniego!
                    if (i < Variables.beginnersList.size()) {//musimy sprawdzać ten warunek
                        BeginnerMushroomPicker.checkTheKind(Variables.beginnersList.get(i).positionX, Variables.beginnersList.get(i).positionY, i);
                    }
                    if (k < Variables.intermediateList.size()) {//musimy sprawdzać ten warunek
                        IntermediateMushroomPicker.checkTheKind(Variables.intermediateList.get(k).positionX, Variables.intermediateList.get(k).positionY, k);
                    }
                    if (m < Variables.advancedList.size()) {//musimy sprawdzać ten warunek
                        AdvancedMushroomPicker.checkTheKind(Variables.advancedList.get(m).positionX, Variables.advancedList.get(m).positionY, m);
                    }
                    //sytuacja, w której nie ma już grzybów
                    if (Variables.toxicMush + Variables.nontoxicMush + Variables.hallucinationMush == 0) {
                        System.out.println("Symulacja zakonczona - wszystkie grzyby zostaly zebrane");
                        System.out.println("liczba iteracji: " + iteraction);
                        break outerLoop;
                        //sytuacja, w której nie ma już grzybiarzy
                    } else if (Variables.beginnersList.size() + Variables.intermediateList.size() + Variables.advancedList.size() == 0) {
                        System.out.println("Symulacja zakonczona - wszyscy zgineli");
                        System.out.println("liczba iteracji: " + iteraction);
                        forestPrint();
                        System.out.println(Variables.intermediateList.size());
                        break outerLoop;
                        //sytuacja, w której zostali sami advanced i grzyby toxic
                    } else if (Variables.beginnersList.size() + Variables.intermediateList.size() == 0 && Variables.nontoxicMush + Variables.hallucinationMush == 0) {
                        System.out.println("Symulacja zakonczona - zostali tylko grzybiarze zaawansowani i trujace grzyby");
                        System.out.println("liczba iteracji: " + iteraction);
                        forestPrint();
                        break outerLoop;
                        //sytuacja, w której zostali sami beginnerzy i halucynki
                    } else if (Variables.intermediateList.size() + Variables.advancedList.size() == 0 && Variables.nontoxicMush + Variables.toxicMush == 0) {
                        System.out.println("Symulacja zakonczona - zostali tylko grzybiarze poczatkujacy i grzyby halucynki");
                        System.out.println("liczba iteracji: " + iteraction);
                        forestPrint();
                        break outerLoop;
                    }
                }
                System.out.println();
                forestPrint();

                for (int n = 0; n < Variables.beginnersList.size(); n++) {//random walk
                    BeginnerMushroomPicker.randomWalk(Variables.beginnersList.get(n).positionX, Variables.beginnersList.get(n).positionY, "B");//sprawdzamy czy wokól postaci begginer nie ma czasem grzyba jadalnego którego może zebrac
                }
                for (int u = 0; u < Variables.intermediateList.size(); u++) {//random walk
                    IntermediateMushroomPicker.randomWalk(Variables.intermediateList.get(u).positionX, Variables.intermediateList.get(u).positionY, "I");
                }
                for (int l = 0; l < Variables.advancedList.size(); l++) {//random walk
                    AdvancedMushroomPicker.randomWalk(Variables.advancedList.get(l).positionX, Variables.advancedList.get(l).positionY, "A");
                }
                System.out.println();
                forestPrint();
                System.out.println("Po random walk");
                iteraction++;

            }
            //wypisanie wyników po skończonej symulacji
            System.out.println("---------- Podsumowanie symulacji ----------");
            if(Variables.dead == 1) {
                System.out.println("Zginal " + Variables.dead + " grzybiarz");
            } else System.out.println("Zginelo " + Variables.dead + " grzybiarzy");
        }else System.out.println("podano zle parametry");
    }
}