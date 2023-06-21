package org.example;

public class BeginnerMushroomPicker extends MushroomPicker {


    public BeginnerMushroomPicker(int score, int positionX, int positionY) {
        super(score, positionX, positionY);//za pomocą słowa kluczowego super inicjalizujemy pola z klasy bazowej
    }
    public static int checkTheKind(int x, int y, int indexOfBeginner, String signOfPicker){
        int aroundX = 0;//tutaj mamy pozycje x wokół postaci
        int aroundY = 0;
        for(int i = -1 ;i<=1;i++){
            for (int j = -1; j<=1 ; j++){
                aroundX = x + i;//tutaj mamy pozycję x wokół postaci
                aroundY = y + j;//tutaj mamy pozycję y wokół postaci
                if (aroundX >= 0 && aroundY >= 0 && aroundX < Forest.forestHeight && aroundY < Forest.getForestWidth()){
                    if(Forest.board.get(aroundX).get(aroundY).equals(Forest.H)){
                        //DZIEDZICZENIE
                        int temp = Forest.nontoxicMush;
                        interactionWithNontoxic(aroundX, aroundY);//check if nontoxic jest dziedziczone po mushroompickers
                        if(temp - Forest.nontoxicMush == 1){//okazało się, że beginner wziął grzyba nontoxic
                            Forest.beginnersList.get(indexOfBeginner).setScore(Forest.beginnersList.get(indexOfBeginner).getScore() + 1);
                        }
                        return 0;//zwracamy 0, gdy jest interakcja z nontoxic - nie zmniejsza się ilość beginnerów
                    }
                    else if(Forest.board.get(aroundX).get(aroundY).equals(Forest.P)) {//P to są toxic grzyby
                        MushroomPicker.interactionWithToxic(x, y, aroundX, aroundY, indexOfBeginner, signOfPicker);
                        return -1;//zwracamy -1, gdy jest interakcja z toxic - ginie beginner
                    }

                }
            }
        }
        return 0;
    }
}


