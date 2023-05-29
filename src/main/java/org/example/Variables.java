package org.example;
import java.util.ArrayList;

public class Variables {
    private static final int forestWidth = 20; //szerokość lasu
    public static final int forestHeight = 8; //wysokość lasu
    public static final int mushrooms = 10;//liczba wszystkich grzybów
    public static final double percentOfToxic = 100;//procent grzybów toksycznych
    public static final double percentOfHallucination = 0;//procent grzybów halucynków
    public static final int beginnerPickers = 5;//liczba beginnerów
    public static final int intermediatePickers = 0;//liczba intermediate
    public static final int advancedPickers = 0;//liczba advanced
    public static int nontoxicMush = mushrooms *(100-(int) percentOfHallucination -(int) percentOfToxic)/100;//liczba zdrowych grzybów
    public static int toxicMush = Variables.mushrooms *(int)Variables.percentOfToxic /100;//liczba grzybów toxic
    public static int hallucinationMush = Variables.mushrooms *(int)Variables.percentOfHallucination /100;//liczba grzybów hallucynogennych
    public static ArrayList<ArrayList<String>> board = new ArrayList<ArrayList<String>>();//tutaj przechowyway jest las z wszystkimi polami
    public static ArrayList<BeginnerMushroomPicker> beginnersList = new ArrayList<>();//przechowuje beginnerów
    public static ArrayList<IntermediateMushroomPicker> intermediateList = new ArrayList<>();//przechowuje intermediate
    public static ArrayList<AdvancedMushroomPicker> advancedList = new ArrayList<>();//przechowuje advanced
    public static ArrayList<NontoxicMushroom> nontoxicMushroomList = new ArrayList<>();//przechowuje grzyby nontoxic
    public static ArrayList<ToxicMushroom>  toxicMushroomList= new ArrayList<>();//przechowuje grzyby toxic
    public static ArrayList<HallucinationMushroom> hallucinationMushroomList = new ArrayList<>();//przechowuje grzyby hallucination
    public static int dead = 0;//będzie zliczać ilość umarłych grzybiarzy


    public static int getForestWidth() {
        return forestWidth;
    }
}
