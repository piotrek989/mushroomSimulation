package org.example;

import java.util.Random;

public abstract class MushroomPicker {

    private int score;//nie static bo każdy obiekt ma inny score
    private int positionX;
    private int positionY;
    public MushroomPicker(int score, int positionX, int positionY) {
        this.score = score;
        this.positionX = positionX;//this.positionX daje nam parametry obiektu, którego u nas utworzyliśmy w mainie
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
    public int getScore() {//getter
        return score;
    }
    public void setScore(int score) {//getter
        this.score = score;
    }

    public static int [] checkAndGiveFirstPosition(String signOfPicker){
        //szukanie pozycji dla grzybiarza
        int[] position = new int[2];
        Random liczba = new Random();
        int randomX = liczba.nextInt(Forest.forestHeight);
        int randomY = liczba.nextInt(Forest.getForestWidth());
        if ( !Forest.board.get(randomX).get(randomY).equals(Forest.X)) {
            for (int j = 0; !Forest.board.get(randomX).get(randomY).equals(Forest.X) ; j++) {//pętla losuje, aż nie znajdzie takiej pozycji, na której można dać postać
                randomX = liczba.nextInt(Forest.forestHeight);
                randomY = liczba.nextInt(Forest.getForestWidth());
                if(Forest.board.get(randomX).get(randomY).equals(Forest.X)) {
                    Forest.board.get(randomX).set(randomY, signOfPicker);//to postać
                    position[0] = randomX;
                    position[1] = randomY;
                    break;
                }
            }
        }
        Forest.board.get(randomX).set(randomY, signOfPicker);
        position[0] = randomX;
        position[1] = randomY;
        return position;
    }

    public static void interactionWithNontoxic(int aroundX, int aroundY) {
        for(int k = 0; k < Forest.nontoxicMushroomList.size() ; k++){
            if(Forest.nontoxicMushroomList.get(k).getPositionX() == aroundX && Forest.nontoxicMushroomList.get(k).getPositionY() == aroundY){
                Forest.board.get(aroundX).set(aroundY, Forest.X);//ustawienie na planszy, że grzyb healthy jest zjedzony
                Forest.nontoxicMushroomList.remove(k);//usunięcie grzyba healthy z arraylisty, bo został zjedzony
                Forest.nontoxicMush--;
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
            if ((randomX >= 0) && (randomY >= 0) && (randomY < Forest.getForestWidth()) && (randomX < Forest.forestHeight) && Forest.board.get(randomX).get(randomY).equals(Forest.X)) {
                Forest.board.get(randomX).set(randomY, signOfPicker);//nowa pozycja beginnera
                Forest.board.get(x).set(y, Forest.X);//stare pole staje się polem X
                if (signOfPicker.equals(Forest.B)) {//beginner
                    BeginnerMushroomPicker.change_position_after_random_walk(x, y, randomX, randomY);
                } else if (signOfPicker.equals(Forest.I)) {//intermediate
                    IntermediateMushroomPicker.changePositionAfterRandomWalk(x, y, randomX, randomY);
                } else if (signOfPicker.equals(Forest.A)) {//advanced
                    AdvancedMushroomPicker.changePositionAfterRandomWalk(x, y, randomX, randomY);
                }
            break;
            }
            iteracja++;
        }while(true && iteracja <= 10);//zakładamy, że po 10 losowaniach nie ma pola takiego, aby postawić tam postać i ona nie porusza się
    }
}

