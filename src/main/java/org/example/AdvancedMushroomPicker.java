package org.example;

public class AdvancedMushroomPicker extends MushroomPicker {
    public AdvancedMushroomPicker(int score, int positionX, int positionY) {
        super(score, positionX, positionY);//za pomocą słowa kluczowego super inicjalizujemy pola z klasy bazowej
    }

    public static void changePositionAfterRandomWalk(int x, int y, int randomX, int randomY) {
        for (int i = 0; i < Variables.advancedList.size(); i++) {//sprawdzamy, który z advanced ma taką pozycję i zmieniamy mu ją
            if (Variables.advancedList.get(i).getPositionX() == x && Variables.advancedList.get(i).getPositionY() == y) {
                Variables.advancedList.get(i).setPositionX(randomX);
                Variables.advancedList.get(i).setPositionY(randomY);
                break;//wychodzimy bo już znaleźliśmy
            }
        }
    }
    private static void interactionWithHallucination(int x, int y, int aroundX, int aroundY, int indexOfAdvanced)
    {
        for(int k = 0 ; k < Variables.hallucinationMushroomList.size() ; k++){//skanujemy po całej liście grzybów halucynków i czekamy aż pętla natrafi na takowego
            if(Variables.hallucinationMushroomList.get(k).getPositionX() == aroundX && Variables.hallucinationMushroomList.get(k).getPositionY() == aroundY){
                Variables.board.get(aroundX).set(aroundY, "X");//ustawienie na planszy, że grzyb hallucination jest zjedzony
                Variables.board.get(x).set(y, "X");//ustawienie na planszy, że advanced zginął
                Variables.hallucinationMushroomList.remove(k);//usunięcie grzyba halucynka z arraylisty bo został zjedzony
                Variables.hallucinationMush--;
                Variables.advancedList.remove(indexOfAdvanced);//usunięcie advanced z listy bo zginął
                Variables.dead++;//zliczanie umarłych
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
                    if (aroundX >= 0 && aroundY >= 0 && aroundX < Variables.forestHeight && aroundY < Variables.getForestWidth()) {
                        if (Variables.board.get(aroundX).get(aroundY).equals("H")) {
                            //DZIEDZICZENIE
                            interactionWithNontoxic(aroundX, aroundY);//check if nontoxic jest dziedziczone po mushroompickers
                            return 0;//zwraca 0 bo nic sie nie dzieje
                        } else if (Variables.board.get(aroundX).get(aroundY).equals("@")) {//@ to są grzyby halucynki
                            interactionWithHallucination(x, y, aroundX, aroundY, indexOfAdvanced);
                            return -1;//zwraca -1 bo ginie
                        }
                    }
                }
            }
            return 0;//zwraca 0 bo nic sie nie dzieje
        }
}
