package org.example;
import java.util.Random;
public class IntermediateMushroomPicker extends MushroomPicker {
    public IntermediateMushroomPicker(int score, int position_x, int position_y) {
        super(score, position_x, position_y);//za pomocą słowa kluczowego super inicjalizujemy pola z klasy bazowej
    }

    public static void changePositionAfterRandomWalk(int x, int y, int randomX, int randomY){
        for (int i = 0; i < Forest.intermediateList.size(); i++) {//sprawdzamy, który z tych intermediate ma taką pozycję i zmieniamy mu ją
            if (Forest.intermediateList.get(i).getPositionX() == x && Forest.intermediateList.get(i).getPositionY() == y) {
                Forest.intermediateList.get(i).setPositionX(randomX);
                Forest.intermediateList.get(i).setPositionY(randomY);
                break;//wychodzimy, bo już znaleźliśmy
            }
        }
    }
    public static int checkTheKind(int x, int y, int indexOfIntermediate) {
        int aroundX = 0;//tutaj mamy pozycje x wokół postaci
        int aroundY = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                aroundX = x + i;//tutaj mamy pozycję x wokół postaci
                aroundY = y + j;//tutaj mamy pozycję y wokół postaci
                if (aroundX >= 0 && aroundY >= 0 && aroundX < Forest.forestHeight && aroundY < Forest.getForestWidth()) {
                    if (Forest.board.get(aroundX).get(aroundY).equals(Forest.H)) {
                        MushroomPicker.interactionWithNontoxic(aroundX, aroundY);
                    }
                    else if (Forest.board.get(aroundX).get(aroundY).equals(Forest.P)) {//P to są toxic grzyby
                        int k;
                        k = interactionWithToxic(x, y, aroundX, aroundY, indexOfIntermediate);
                        return k;

                    }
                    else if (Forest.board.get(aroundX).get(aroundY).equals(Forest.L)) {//L to są halucynogenne grzyby
                        int k;
                        k = interactionWithHallucination(x, y, aroundX, aroundY, indexOfIntermediate);
                        return k;
                    }

                }
            }
        }
        return 0;
    }


   private static int interactionWithToxic(int x, int y, int aroundX, int aroundY, int indexOfIntermediate){
        for(int k = 0; k < Forest.toxicMushroomList.size() ; k++){//skanujemy po całej liście grzybów toxic i czekamy, aż pętla natrafi na takowego
            if(Forest.toxicMushroomList.get(k).getPositionX() == aroundX && Forest.toxicMushroomList.get(k).getPositionY() == aroundY) {
                if (losowanie()) {//jeśli losowanie zwróci prawdę to wchodzimy do tego if-a(intermediate zbiera grzyba i ginie)
                    Forest.board.get(aroundX).set(aroundY, Forest.X);//ustawienie na planszy, że grzyb toxic jest zjedzony
                    Forest.board.get(x).set(y, Forest.X);//ustawienie na planszy, że intermediate zginął
                    Forest.toxicMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty, bo został zjedzony
                    Forest.toxicMush--;
                    Forest.intermediateList.remove(indexOfIntermediate);//usunięcie intermedaite z listy bo zginął
                    Forest.dead++;//zliczanie umarłych
                    return -1;//zwraca -1 bo intermediate ginie
                }
                else{//intermediate zjada, ale nie ginie
                    Forest.board.get(aroundX).set(aroundY, Forest.X);//ustawienie na planszy, że grzyb toxic jest zjedzony
                    Forest.toxicMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty bo został zjedzony
                    Forest.toxicMush--;
                    return 0;//zwraca 0 bo intermediate nie ginie
                }
            }
        }
        return 0;
    }
    private static boolean losowanie() {//losuje i zwraca true albo false szansa 50/50
        Random random = new Random();
        double losowaWartosc = random.nextDouble();

        if (losowaWartosc < 0.5) {
            return true;
        } else {
            return false;
        }
    }
    private static int interactionWithHallucination(int x, int y, int aroundX, int aroundY, int indexOfIntermediate){
        for(int k = 0; k < Forest.hallucinationMushroomList.size() ; k++){//skanujemy po całej liście grzybOw halucynków i czekamy, aż pętla natrafi na takowego
            if(Forest.hallucinationMushroomList.get(k).getPositionX() == aroundX && Forest.hallucinationMushroomList.get(k).getPositionY() == aroundY) {
                if (losowanie()) {//jeśli losowanie zwróci prawdę to wchodzimy do tego if-a(intermediate zbiera grzyba i ginie)
                    Forest.board.get(aroundX).set(aroundY, Forest.X);//ustawienie na planszy, że grzyb halucynek jest zjedzony
                    Forest.board.get(x).set(y, Forest.X);//ustawienie na planszy że intermediate zginął
                    Forest.hallucinationMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty, bo został zjedzony
                    Forest.hallucinationMush--;
                    Forest.intermediateList.remove(indexOfIntermediate);//usunięcie intermediate z listy bo zginął
                    Forest.dead++;//zliczanie umarłych
                    return -1;//zwraca -1 bo intermediate ginie
                }
                else{//intermediate zjada ale nie ginie
                    Forest.board.get(aroundX).set(aroundY, Forest.X);//ustawienie na planszy, że grzyb halucynek jest zjedzony
                    Forest.hallucinationMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty bo został zjedzony
                    Forest.hallucinationMush--;
                    return 0;//zwraca 0 bo intermediate nie ginie
                }
            }
        }
        return 0;
    }
}
