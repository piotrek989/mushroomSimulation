package org.example;
import java.util.Random;
public class IntermediateMushroomPicker extends MushroomPicker {
    public IntermediateMushroomPicker(int score, int position_x, int position_y) {
        super(score, position_x, position_y);//za pomocą słowa kluczowego super inicjalizujemy pola z klasy bazowej
    }

    public static void changePositionAfterRandomWalk(int x, int y, int randomX, int randomY){
        for (int i = 0; i < Variables.intermediateList.size(); i++) {//sprawdzamy, który z tych intermediate ma taką pozycję i zmieniamy mu ją
            if (Variables.intermediateList.get(i).getPositionX() == x && Variables.intermediateList.get(i).getPositionY() == y) {
                Variables.intermediateList.get(i).setPositionX(randomX);
                Variables.intermediateList.get(i).setPositionY(randomY);
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
                if (aroundX >= 0 && aroundY >= 0 && aroundX < Variables.forestHeight && aroundY < Variables.getForestWidth()) {
                    if (Variables.board.get(aroundX).get(aroundY).equals("H")) {
                        MushroomPicker.interactionWithNontoxic(aroundX, aroundY);
                    }
                    else if (Variables.board.get(aroundX).get(aroundY).equals("P")) {//P to są toxic grzyby
                        int k;
                        k = interactionWithToxic(x, y, aroundX, aroundY, indexOfIntermediate);
                        return k;

                    }
                    else if (Variables.board.get(aroundX).get(aroundY).equals("@")) {//@ to są halucynogenne grzyby
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
        for(int k = 0 ; k < Variables.toxicMushroomList.size() ; k++){//skanujemy po całej liście grzybów toxic i czekamy, aż pętla natrafi na takowego
            if(Variables.toxicMushroomList.get(k).getPositionX() == aroundX && Variables.toxicMushroomList.get(k).getPositionY() == aroundY) {
                if (losowanie()) {//jeśli losowanie zwróci prawdę to wchodzimy do tego if-a(intermediate zbiera grzyba i ginie)
                    Variables.board.get(aroundX).set(aroundY, "X");//ustawienie na planszy, że grzyb toxic jest zjedzony
                    Variables.board.get(x).set(y, "X");//ustawienie na planszy, że intermediate zginął
                    Variables.toxicMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty, bo został zjedzony
                    Variables.toxicMush--;
                    Variables.intermediateList.remove(indexOfIntermediate);//usunięcie intermedaite z listy bo zginął
                    Variables.dead++;//zliczanie umarłych
                    return -1;//zwraca -1 bo intermediate ginie
                }
                else{//intermediate zjada, ale nie ginie
                    Variables.board.get(aroundX).set(aroundY, "X");//ustawienie na planszy, że grzyb toxic jest zjedzony
                    Variables.toxicMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty bo został zjedzony
                    Variables.toxicMush--;
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
        for(int k = 0 ; k < Variables.hallucinationMushroomList.size() ; k++){//skanujemy po całej liście grzybOw halucynków i czekamy, aż pętla natrafi na takowego
            if(Variables.hallucinationMushroomList.get(k).getPositionX() == aroundX && Variables.hallucinationMushroomList.get(k).getPositionY() == aroundY) {
                if (losowanie()) {//jeśli losowanie zwróci prawdę to wchodzimy do tego if-a(intermediate zbiera grzyba i ginie)
                    Variables.board.get(aroundX).set(aroundY, "X");//ustawienie na planszy, że grzyb halucynek jest zjedzony
                    Variables.board.get(x).set(y, "X");//ustawienie na planszy że intermediate zginął
                    Variables.hallucinationMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty, bo został zjedzony
                    Variables.hallucinationMush--;
                    Variables.intermediateList.remove(indexOfIntermediate);//usunięcie intermediate z listy bo zginął
                    Variables.dead++;//zliczanie umarłych
                    return -1;//zwraca -1 bo intermediate ginie
                }
                else{//intermediate zjada ale nie ginie
                    Variables.board.get(aroundX).set(aroundY, "X");//ustawienie na planszy, że grzyb halucynek jest zjedzony
                    Variables.hallucinationMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty bo został zjedzony
                    Variables.hallucinationMush--;
                    return 0;//zwraca 0 bo intermediate nie ginie
                }
            }
        }
        return 0;
    }
}
