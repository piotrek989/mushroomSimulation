package org.example;

public class BeginnerMushroomPicker extends MushroomPicker {


    public BeginnerMushroomPicker(int score, int positionX, int positionY) {
        super(score, positionX, positionY);//za pomocą słowa kluczowego super inicjalizujemy pola z klasy bazowej
    }


    public static void change_position_after_random_walk(int x, int y, int randomX, int randomY){
        for (int i = 0; i < Forest.beginnersList.size(); i++) {//sprawdzamy, który z tych begginerów ma taką pozycję i zmieniamy mu ją
            if (Forest.beginnersList.get(i).getPositionX() == x && Forest.beginnersList.get(i).getPositionY() == y) {
                Forest.beginnersList.get(i).setPositionX(randomX);
                Forest.beginnersList.get(i).setPositionY(randomY);
                break;//wychodzimy bo już znaleźliśmy
            }
        }
    }

    private static void interactionWithToxic(int x, int y, int aroundX, int aroundY, int indexOfBeginner){
        for(int k = 0; k < Forest.toxicMushroomList.size() ; k++){//skanujemy po całej liście grzybow toxic i czekamy aż pętla natrafi na takowego
            if(Forest.toxicMushroomList.get(k).getPositionX() == aroundX && Forest.toxicMushroomList.get(k).getPositionY() == aroundY){
                Forest.board.get(aroundX).set(aroundY, "X");//ustawienie na planszy, że grzyb toxic jest zjedzony
                Forest.board.get(x).set(y, "X");//ustawienie na planszy, że beginner zginął
                Forest.toxicMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty bo został zjedzony
                Forest.toxicMush--;
                Forest.beginnersList.remove(indexOfBeginner);//usunięcie beginnera z listy bo zginął
                Forest.dead++;//zliczanie umarłych
                break;
            }
        }
    }

    public static int checkTheKind(int x, int y, int indexOfBeginner){
        int aroundX = 0;//tutaj mamy pozycje x wokół postaci
        int aroundY = 0;
        for(int i = -1 ;i<=1;i++){
            for (int j = -1; j<=1 ; j++){
                aroundX = x + i;//tutaj mamy pozycję x wokół postaci
                aroundY = y + j;//tutaj mamy pozycję y wokół postaci
                if (aroundX >= 0 && aroundY >= 0 && aroundX < Forest.forestHeight && aroundY < Forest.getForestWidth()){
                    if(Forest.board.get(aroundX).get(aroundY).equals("H")){
                        //DZIEDZICZENIE
                        int temp = Forest.nontoxicMush;
                        interactionWithNontoxic(aroundX, aroundY);//check if nontoxic jest dziedziczone po mushroompickers
                        if(temp - Forest.nontoxicMush == 1){//okazalo sie ze begginer wziol grzyba nontoxic
                            Forest.beginnersList.get(indexOfBeginner).setScore(Forest.beginnersList.get(indexOfBeginner).getScore() + 1);
                        }
                        return 0;//zwracamy 0 gdy jest interakcja z nontoxic - nie zmniejsza się ilosc begginerów
                    }
                    else if(Forest.board.get(aroundX).get(aroundY).equals("P")) {//P to są toxic grzyby
                        interactionWithToxic(x, y, aroundX, aroundY, indexOfBeginner);
                        return -1;//zwracamy -1 gdy jest innterakcja z toxic - ginie begginer
                    }

                }
            }
        }
        return 0;
    }

}

