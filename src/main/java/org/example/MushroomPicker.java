package org.example;

import java.util.Random;

public abstract class MushroomPicker {

    private int score;//nie static bo każdy obiekt ma inny score
    private int positionX;
    private int positionY;
    public MushroomPicker(int score, int positionX, int positionY) {
        this.score = score;
        this.positionX = positionX;//this.positionX daje nam parametry obiektu, którego u nas utworzylismy w mainie
        this.positionY = positionY;
    }
    public int getPositionX() {//getter
        return positionX;
    }

    public void setPositionX(int X) {//setter
        positionX = X;
    }

    public int getPositionY() {
        return positionY;
    }
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public static int [] checkAndGiveFirstPosition(String signOfPicker){
        //szukanie pozycji dla grzybiarza
        int[] position = new int[2];
        Random liczba = new Random();
        int randomX = liczba.nextInt(Variables.forestHeight);
        int randomY = liczba.nextInt(Variables.getForestWidth());
        if ( !Variables.board.get(randomX).get(randomY).equals("X")) {
            for (int j = 0; !Variables.board.get(randomX).get(randomY).equals("X") ; j++) {//pętla losuje, aż nie znajdzie takiej pozycji, na której można dać postać
                randomX = liczba.nextInt(Variables.forestHeight);
                randomY = liczba.nextInt(Variables.getForestWidth());
                if(Variables.board.get(randomX).get(randomY).equals("X")) {
                    Variables.board.get(randomX).set(randomY, signOfPicker);//to postać
                    position[0] = randomX;
                    position[1] = randomY;
                    break;
                }
            }
        }
        Variables.board.get(randomX).set(randomY, signOfPicker);
        position[0] = randomX;
        position[1] = randomY;
        return position;
    }

    public static void interactionWithNontoxic(int aroundX, int aroundY) {
        for(int k = 0 ; k < Variables.nontoxicMushroomList.size() ; k++){
            if(Variables.nontoxicMushroomList.get(k).getPositionX() == aroundX && Variables.nontoxicMushroomList.get(k).getPositionY() == aroundY){
                Variables.board.get(aroundX).set(aroundY, "X");//ustawienie na planszy, że grzyb healthy jest zjedzony
                Variables.nontoxicMushroomList.remove(k);//usunięcie grzyba healthy z arraylisty bo został zjedzony
                Variables.nontoxicMush--;
                break;
            }
        }
    }

    public static void randomWalk(int x, int y, String signOfPicker) {//to metoda dziedziczona przez innych zbieraczy

        Random liczba = new Random();

        int iteracja = 0;
        do {
            int randomX = x - 1 + liczba.nextInt(3);//ta linijka losuje od -1 do 1 i dodaje x
            int randomY = y - 1 + liczba.nextInt(3);//ta linijka losuje od -1 do 1 i dodaj y
            if ((randomX >= 0) && (randomY >= 0) && (randomY < Variables.getForestWidth()) && (randomX < Variables.forestHeight) && Variables.board.get(randomX).get(randomY).equals("X")) {
                Variables.board.get(randomX).set(randomY, signOfPicker);//nowa pozycja beginnera
                Variables.board.get(x).set(y, "X");//stare pole staje się polem X
                if (signOfPicker.equals("B")) {//beginner
                    BeginnerMushroomPicker.change_position_after_random_walk(x, y, randomX, randomY);
                } else if (signOfPicker.equals("I")) {//intermediate
                    IntermediateMushroomPicker.changePositionAfterRandomWalk(x, y, randomX, randomY);
                } else if (signOfPicker.equals("A")) {//advanced
                    AdvancedMushroomPicker.changePositionAfterRandomWalk(x, y, randomX, randomY);
                }
            break;
            }
            iteracja++;
        }while(true && iteracja <= 10);//zakładamy, że po 10 losowaniach nie ma pola takiego, aby postawić tam postać i ona nie porusza się
    }
}

