package org.example;
import java.util.ArrayList;

public class Forest {
    public static int forestWidth = 20; //szerokość lasu
    public static int forestHeight = 10; //wysokość lasu
    public static final int mushrooms = 25;//liczba wszystkich grzybów
    public static final double percentOfToxic = 0;//procent grzybów toksycznych
    public static final double percentOfHallucination = 0;//procent grzybów halucynków
    public static final int beginnerPickers = 5;//liczba beginnerów
    public static final int intermediatePickers = 5;//liczba intermediate
    public static final int advancedPickers = 5;//liczba advanced
    public static int nontoxicMush = mushrooms *(100-(int) percentOfHallucination -(int) percentOfToxic)/100;//liczba zdrowych grzybów
    public static int toxicMush = Forest.mushrooms *(int) Forest.percentOfToxic /100;//liczba grzybów toxic
    public static int hallucinationMush = Forest.mushrooms *(int) Forest.percentOfHallucination /100;//liczba grzybów hallucynogennych
    public static ArrayList<ArrayList<String>> board = new ArrayList<ArrayList<String>>();//tutaj przechowyway jest las z wszystkimi polami
    public static ArrayList<MushroomPicker> beginnersList = new ArrayList<>();//przechowuje beginnerów
    public static ArrayList<MushroomPicker> intermediateList = new ArrayList<>();//przechowuje intermediate
    public static ArrayList<MushroomPicker> advancedList = new ArrayList<>();//przechowuje advanced
    public static ArrayList<NontoxicMushroom> nontoxicMushroomList = new ArrayList<>();//przechowuje grzyby nontoxic
    public static ArrayList<ToxicMushroom>  toxicMushroomList= new ArrayList<>();//przechowuje grzyby toxic
    public static ArrayList<HallucinationMushroom> hallucinationMushroomList = new ArrayList<>();//przechowuje grzyby hallucination
    public static int deadBegg = 0;//będzie zliczać ilość umarłych grzybiarzy
    public static int deadInter = 0;//będzie zliczać ilość umarłych grzybiarzy
    public static int deadAdv = 0;//będzie zliczać ilość umarłych grzybiarzy


    public static int getForestWidth() {
        return forestWidth;
    }
    //kolory
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static final String X = WHITE + "X" + RESET;
    public static final String P = RED + "P" + RESET;
    public static final String H = GREEN + "H" + RESET;
    public static final String L = CYAN + "L" + RESET;
    public static final String B = YELLOW + "B" + RESET;
    public static final String A = PURPLE + "A" + RESET;
    public static final String I = BLUE + "I" + RESET;
    public static void forestPrint() {//printowanie lasu
        for (int i = 0; i < Forest.forestHeight; i++) {
            for (int j = 0; j < Forest.getForestWidth(); j++) {
                System.out.print(board.get(i).get(j));
            }
            System.out.println();
        }
    }

    public static void createForest(){//wypelnia X-ami planszę
        for (int i = 0; i < Forest.forestHeight; i++) {
            ArrayList<String> row = new ArrayList<String>();
            for (int j = 0; j < Forest.getForestWidth(); j++) {
                row.add(X);//cała plansza wypelniona X-ami
            }
            Forest.board.add(row);
        }
    }


}
