package org.example;

public class AdvancedMushroomPicker extends MushroomPicker {
    public AdvancedMushroomPicker(int score, int positionX, int positionY) {
        super(score, positionX, positionY);//za pomocą słowa kluczowego super inicjalizujemy pola z klasy bazowej
    }
    public static int checkTheKind(int x, int y, int indexOfAdvanced, String signOfPicker){
        int aroundX = 0;//tutaj mamy pozycje x wokół postaci
        int aroundY = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                aroundX = x + i;//tutaj mamy pozycję x wokół postaci
                aroundY = y + j;//tutaj mamy pozycję y wokół postaci
                if (aroundX >= 0 && aroundY >= 0 && aroundX < Forest.forestHeight && aroundY < Forest.getForestWidth()) {
                    if (Forest.board.get(aroundX).get(aroundY).equals(Forest.H)) {
                        //DZIEDZICZENIE
                        int temp = Forest.nontoxicMush;
                        interactionWithNontoxic(aroundX, aroundY);//check if nontoxic jest dziedziczone po mushroompickers
                        if(temp - Forest.nontoxicMush == 1){//okazało się, że advanced wziął grzyba nontoxic
                            Forest.advancedList.get(indexOfAdvanced).setScore(Forest.advancedList.get(indexOfAdvanced).getScore() + 1);
                        }
                        return 0;//zwracamy 0, gdy jest interakcja z nontoxic - nie zmniejsza się ilość beginnerów
                    } else if (Forest.board.get(aroundX).get(aroundY).equals(Forest.L)) {//L to są grzyby halucynki
                        MushroomPicker.interactionWithHallucination(x, y, aroundX, aroundY, indexOfAdvanced, signOfPicker);
                        return -1;//zwraca -1, bo ginie
                    }
                }
            }
        }
        return 0;//zwraca 0, bo nic sie nie dzieje
    }
}