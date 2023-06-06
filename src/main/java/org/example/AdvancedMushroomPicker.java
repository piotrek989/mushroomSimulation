package org.example;

public class AdvancedMushroomPicker extends MushroomPicker {
    public AdvancedMushroomPicker(int score, int positionX, int positionY) {
        super(score, positionX, positionY);//za pomocą słowa kluczowego super inicjalizujemy pola z klasy bazowej
    }

    public static void changePositionAfterRandomWalk(int x, int y, int randomX, int randomY) {
        for (int i = 0; i < Forest.advancedList.size(); i++) {//sprawdzamy, który z advanced ma taką pozycję i zmieniamy mu ją
            if (Forest.advancedList.get(i).getPositionX() == x && Forest.advancedList.get(i).getPositionY() == y) {
                Forest.advancedList.get(i).setPositionX(randomX);
                Forest.advancedList.get(i).setPositionY(randomY);
                break;//wychodzimy, bo już znaleźliśmy
            }
        }
    }
    private static void interactionWithHallucination(int x, int y, int aroundX, int aroundY, int indexOfAdvanced)
    {
        for(int k = 0; k < Forest.hallucinationMushroomList.size() ; k++){//skanujemy po całej liście grzybów halucynków i czekamy, aż pętla natrafi na takowego
            if(Forest.hallucinationMushroomList.get(k).getPositionX() == aroundX && Forest.hallucinationMushroomList.get(k).getPositionY() == aroundY){
                Forest.board.get(aroundX).set(aroundY, "X");//ustawienie na planszy, że grzyb hallucination jest zjedzony
                Forest.board.get(x).set(y, "X");//ustawienie na planszy, że advanced zginął
                Forest.hallucinationMushroomList.remove(k);//usunięcie grzyba halucynka z arraylisty, bo został zjedzony
                Forest.hallucinationMush--;
                Forest.advancedList.remove(indexOfAdvanced);//usunięcie advanced z listy, bo zginął
                Forest.dead++;//zliczanie umarłych
                break;
            }
        }
    }
        public static int checkTheKind(int x, int y, int indexOfAdvanced){
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
                            interactionWithHallucination(x, y, aroundX, aroundY, indexOfAdvanced);
                            return -1;//zwraca -1, bo ginie
                        }
                    }
                }
            }
            return 0;//zwraca 0, bo nic sie nie dzieje
        }
}
