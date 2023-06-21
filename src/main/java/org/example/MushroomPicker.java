package org.example;

import java.util.Random;

public class MushroomPicker {

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

    public static int interactionWithToxic(int x, int y, int aroundX, int aroundY, int indexOfPicker,String signOfPicker){
        for(int k = 0; k < Forest.toxicMushroomList.size() ; k++){//skanujemy po całej liście grzybow toxic i czekamy, aż pętla natrafi na takowego
            if(Forest.toxicMushroomList.get(k).getPositionX() == aroundX && Forest.toxicMushroomList.get(k).getPositionY() == aroundY) {
                if (signOfPicker.equals("B")) {
                    Forest.board.get(aroundX).set(aroundY, Forest.X);//ustawienie na planszy, że grzyb toxic jest zjedzony
                    Forest.board.get(x).set(y, Forest.X);//ustawienie na planszy, że beginner zginął
                    Forest.toxicMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty, bo został zjedzony
                    Forest.toxicMush--;
                    Forest.beginnersList.remove(indexOfPicker);//usunięcie beginnera z listy, bo zginął
                    Forest.deadBegg++;//zliczanie umarłych
                    return 0;
                }
                else if(signOfPicker.equals("I")){
                    if (losowanie()) {//jeśli losowanie zwróci prawdę to wchodzimy do tego if-a(intermediate zbiera grzyba i ginie)
                        Forest.board.get(aroundX).set(aroundY, Forest.X);//ustawienie na planszy, że grzyb toxic jest zjedzony
                        Forest.board.get(x).set(y, Forest.X);//ustawienie na planszy, że intermediate zginął
                        Forest.toxicMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty, bo został zjedzony
                        Forest.toxicMush--;
                        Forest.intermediateList.remove(indexOfPicker);//usunięcie intermedaite z listy, bo zginął
                        Forest.deadInter++;//zliczanie umarłych
                        return -1;//zwraca -1, bo intermediate ginie
                    }
                    else{//intermediate zjada, ale nie ginie
                        Forest.board.get(aroundX).set(aroundY, Forest.X);//ustawienie na planszy, że grzyb toxic jest zjedzony
                        Forest.toxicMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty, bo został zjedzony
                        Forest.toxicMush--;
                        return 0;//zwraca 0 bo intermediate nie ginie
                    }
                }
            }
        }
        return 0;
    }
    public static int checkTheKind(int x, int y, int indexOfPicker, String signOfPicker){
        int aroundX = 0;//tutaj mamy pozycje x wokół postaci
        int aroundY = 0;
        for(int i = -1 ;i<=1;i++){
            for (int j = -1; j<=1 ; j++){
                aroundX = x + i;//tutaj mamy pozycję x wokół postaci
                aroundY = y + j;//tutaj mamy pozycję y wokół postaci
                if (aroundX >= 0 && aroundY >= 0 && aroundX < Forest.forestHeight && aroundY < Forest.getForestWidth()){
                    if(signOfPicker.equals("B")) {
                        if (Forest.board.get(aroundX).get(aroundY).equals(Forest.H)) {
                            //DZIEDZICZENIE
                            int temp = Forest.nontoxicMush;
                            interactionWithNontoxic(aroundX, aroundY);//check if nontoxic jest dziedziczone po mushroompickers
                            if (temp - Forest.nontoxicMush == 1) {//okazało się, że beginner wziął grzyba nontoxic
                                Forest.beginnersList.get(indexOfPicker).setScore(Forest.beginnersList.get(indexOfPicker).getScore() + 1);
                            }
                            return 0;//zwracamy 0, gdy jest interakcja z nontoxic - nie zmniejsza się ilość beginnerów
                        } else if (Forest.board.get(aroundX).get(aroundY).equals(Forest.P)) {//P to są toxic grzyby
                            interactionWithToxic(x, y, aroundX, aroundY, indexOfPicker, signOfPicker);
                            return -1;//zwracamy -1, gdy jest interakcja z toxic - ginie beginner
                        }
                    }
                    else if(signOfPicker.equals("I")){
                        if (Forest.board.get(aroundX).get(aroundY).equals(Forest.H)) {
                            //DZIEDZICZENIE
                            int temp = Forest.nontoxicMush;
                            interactionWithNontoxic(aroundX, aroundY);//check if nontoxic jest dziedziczone po mushroompickers
                            if(temp - Forest.nontoxicMush == 1){//okazało się, że beginner wziął grzyba nontoxic
                                Forest.intermediateList.get(indexOfPicker).setScore(Forest.intermediateList.get(indexOfPicker).getScore() + 1);
                            }
                            return 0;//zwracamy 0, gdy jest interakcja z nontoxic - nie zmniejsza się ilość beginnerów
                        }
                        else if (Forest.board.get(aroundX).get(aroundY).equals(Forest.P)) {//P to są toxic grzyby
                            int k;
                            k = interactionWithToxic(x, y, aroundX, aroundY, indexOfPicker, signOfPicker);
                            return k;

                        }
                        else if (Forest.board.get(aroundX).get(aroundY).equals(Forest.L)) {//L to są halucynogenne grzyby
                            int k;
                            k = interactionWithHallucination(x, y, aroundX, aroundY, indexOfPicker, signOfPicker);
                            return k;
                        }
                    }
                    else if(signOfPicker.equals("A")){
                        if (Forest.board.get(aroundX).get(aroundY).equals(Forest.H)) {
                            //DZIEDZICZENIE
                            int temp = Forest.nontoxicMush;
                            interactionWithNontoxic(aroundX, aroundY);//check if nontoxic jest dziedziczone po mushroompickers
                            if(temp - Forest.nontoxicMush == 1){//okazało się, że advanced wziął grzyba nontoxic
                                Forest.advancedList.get(indexOfPicker).setScore(Forest.advancedList.get(indexOfPicker).getScore() + 1);
                            }
                            return 0;//zwracamy 0, gdy jest interakcja z nontoxic - nie zmniejsza się ilość beginnerów
                        } else if (Forest.board.get(aroundX).get(aroundY).equals(Forest.L)) {//L to są grzyby halucynki
                            interactionWithHallucination(x, y, aroundX, aroundY, indexOfPicker, signOfPicker);
                            return -1;//zwraca -1, bo ginie
                        }
                    }
                }
            }
        }
        return 0;
    }

    public static int interactionWithHallucination(int x, int y, int aroundX, int aroundY, int indexOfPicker, String signOfPicker){
        for(int k = 0; k < Forest.hallucinationMushroomList.size() ; k++){//skanujemy po całej liście grzybOw halucynków i czekamy, aż pętla natrafi na takowego
            if(Forest.hallucinationMushroomList.get(k).getPositionX() == aroundX && Forest.hallucinationMushroomList.get(k).getPositionY() == aroundY) {
                if(signOfPicker.equals("I")) {
                    if (losowanie()) {//jeśli losowanie zwróci prawdę to wchodzimy do tego if-a(intermediate zbiera grzyba i ginie)
                        Forest.board.get(aroundX).set(aroundY, Forest.X);//ustawienie na planszy, że grzyb halucynek jest zjedzony
                        Forest.board.get(x).set(y, Forest.X);//ustawienie na planszy że intermediate zginął
                        Forest.hallucinationMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty, bo został zjedzony
                        Forest.hallucinationMush--;
                        Forest.intermediateList.remove(indexOfPicker);//usunięcie intermediate z listy bo zginął
                        Forest.deadInter++;//zliczanie umarłych
                        return -1;//zwraca -1, bo intermediate ginie
                    } else {//intermediate zjada ale nie ginie
                        Forest.board.get(aroundX).set(aroundY, Forest.X);//ustawienie na planszy, że grzyb halucynek jest zjedzony
                        Forest.hallucinationMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty bo został zjedzony
                        Forest.hallucinationMush--;
                        return 0;//zwraca 0, bo intermediate nie ginie
                    }
                }
                else if(signOfPicker.equals("A")){
                    Forest.board.get(aroundX).set(aroundY, Forest.X);//ustawienie na planszy, że grzyb hallucination jest zjedzony
                    Forest.board.get(x).set(y, Forest.X);//ustawienie na planszy, że advanced zginął
                    Forest.hallucinationMushroomList.remove(k);//usunięcie grzyba halucynka z arraylisty, bo został zjedzony
                    Forest.hallucinationMush--;
                    Forest.advancedList.remove(indexOfPicker);//usunięcie advanced z listy, bo zginął
                    Forest.deadAdv++;//zliczanie umarłych
                    return 0;
                }
            }
        }
        return 0;
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


    public static void changePositionAfterRandomWalk(int x, int y, int randomX, int randomY, String signOfPicker) {
        if(signOfPicker.equals(Forest.B)) {
            for (int i = 0; i < Forest.beginnersList.size(); i++) {//sprawdzamy, który z tych begginerów ma taką pozycję i zmieniamy mu ją
                if (Forest.beginnersList.get(i).getPositionX() == x && Forest.beginnersList.get(i).getPositionY() == y) {
                    Forest.beginnersList.get(i).setPositionX(randomX);
                    Forest.beginnersList.get(i).setPositionY(randomY);
                    break;//wychodzimy, bo już znaleźliśmy
                }
            }
        }
        else if (signOfPicker.equals(Forest.I)){
            for (int i = 0; i < Forest.intermediateList.size(); i++) {//sprawdzamy, który z tych intermediate ma taką pozycję i zmieniamy mu ją
                if (Forest.intermediateList.get(i).getPositionX() == x && Forest.intermediateList.get(i).getPositionY() == y) {
                    Forest.intermediateList.get(i).setPositionX(randomX);
                    Forest.intermediateList.get(i).setPositionY(randomY);
                    break;//wychodzimy, bo już znaleźliśmy
                }
            }
        }
        else if(signOfPicker.equals(Forest.A)){
            for (int i = 0; i < Forest.advancedList.size(); i++) {//sprawdzamy, który z tych intermediate ma taką pozycję i zmieniamy mu ją
                if (Forest.advancedList.get(i).getPositionX() == x && Forest.advancedList.get(i).getPositionY() == y) {
                    Forest.advancedList.get(i).setPositionX(randomX);
                    Forest.advancedList.get(i).setPositionY(randomY);
                    break;//wychodzimy, bo już znaleźliśmy
                }
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
                    MushroomPicker.changePositionAfterRandomWalk(x, y, randomX, randomY, signOfPicker);
                } else if (signOfPicker.equals(Forest.I)) {//intermediate
                    IntermediateMushroomPicker.changePositionAfterRandomWalk(x, y, randomX, randomY, signOfPicker);
                } else if (signOfPicker.equals(Forest.A)) {//advanced
                    AdvancedMushroomPicker.changePositionAfterRandomWalk(x, y, randomX, randomY, signOfPicker);
                }
                break;
            }
            iteracja++;
        }while(true && iteracja <= 10);//zakładamy, że po 10 losowaniach nie ma pola takiego, aby postawić tam postać i ona nie porusza się
    }
}
