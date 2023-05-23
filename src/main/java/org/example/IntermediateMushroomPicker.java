package org.example;
import java.util.Random;
public class IntermediateMushroomPicker extends MushroomPicker {
    public IntermediateMushroomPicker(int score, int position_x, int position_y) {
        super(score, position_x, position_y);//za pomocą słowa kluczowego super inicjalizujemy pola z klasy bazowej
    }

    public static void changePositionAfterRandomWalk(int x, int y, int randomX, int randomY){
        for (int i = 0; i < Variables.intermediateList.size(); i++) {//sprawdzamy, który z tych intermediate ma taką pozycję i zmieniamy mu ją
            if (Variables.intermediateList.get(i).positionX == x && Variables.intermediateList.get(i).positionY == y) {
                Variables.intermediateList.get(i).positionX = randomX;
                Variables.intermediateList.get(i).positionY = randomY;
                break;//wychodzimy, bo już znaleźliśmy
            }
        }
    }
    public static void checkTheKind(int x, int y, int indexOfIntermediate) {
        int aroundX = 0;//tutaj mamy pozycje x wokół postaci
        int aroundY = 0;
        outerLoop:
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                aroundX = x + i;//tutaj mamy pozycję x wokół postaci
                aroundY = y + j;//tutaj mamy pozycję y wokół postaci
                if (aroundX >= 0 && aroundY >= 0 && aroundX < Variables.forestHeight && aroundY < Variables.forestWidth) {
                    if (Variables.board.get(aroundX).get(aroundY).equals("H")) {
                        MushroomPicker.interactionWithNontoxic(aroundX, aroundY);
                        break outerLoop;
                    } else if (Variables.board.get(aroundX).get(aroundY).equals("P")) {//P to są toxic grzyby
                        interactionWithToxic(x, y, aroundX, aroundY, indexOfIntermediate);
                        break outerLoop;
                    } else if (Variables.board.get(aroundX).get(aroundY).equals("@")) {//@ to są halucynogenne grzyby
                        interactionWithHallucination(x, y, aroundX, aroundY, indexOfIntermediate);
                        break outerLoop;
                    }

                }
            }
        }
    }


    public static void interactionWithToxic(int x, int y, int aroundX, int aroundY, int indexOfIntermediate){
        for(int k = 0 ; k < Variables.toxicMushroomList.size() ; k++){//skanujemy po całej liście grzybów toxic i czekamy, aż pętla natrafi na takowego
            if(Variables.toxicMushroomList.get(k).positionX == aroundX && Variables.toxicMushroomList.get(k).positionY == aroundY) {
                if (losowanie()) {//jeśli losowanie zwróci prawdę to wchodzimy do tego if-a(intermediate zbiera grzyba i ginie)
                    Variables.board.get(aroundX).set(aroundY, "X");//ustawienie na planszy, że grzyb toxic jest zjedzony
                    Variables.board.get(x).set(y, "X");//ustawienie na planszy, że intermediate zginął
                    Variables.toxicMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty, bo został zjedzony
                    Variables.toxicMush--;
                    Variables.intermediateList.remove(indexOfIntermediate);//usunięcie intermedaite z listy bo zginął
                    Variables.dead++;//zliczanie umarłych
                    break;
                }
                else{//intermediate zjada, ale nie ginie
                    Variables.board.get(aroundX).set(aroundY, "X");//ustawienie na planszy, że grzyb toxic jest zjedzony
                    Variables.toxicMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty bo został zjedzony
                    Variables.toxicMush--;
                    Variables.dead++;//zliczanie umarłych
                    break;
                }

            }
        }
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
    public static void interactionWithHallucination(int x, int y, int aroundX, int aroundY, int indexOfIntermediate){
        for(int k = 0 ; k < Variables.hallucinationMushroomList.size() ; k++){//skanujemy po całej liście grzybOw halucynków i czekamy, aż pętla natrafi na takowego
            if(Variables.hallucinationMushroomList.get(k).positionX == aroundX && Variables.hallucinationMushroomList.get(k).positionY == aroundY) {
                if (losowanie()) {//jeśli losowanie zwróci prawdę to wchodzimy do tego if-a(intermediate zbiera grzyba i ginie)
                    Variables.board.get(aroundX).set(aroundY, "X");//ustawienie na planszy, że grzyb halucynek jest zjedzony
                    Variables.board.get(x).set(y, "X");//ustawienie na planszy że intermediate zginął
                    Variables.hallucinationMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty, bo został zjedzony
                    Variables.hallucinationMush--;
                    Variables.intermediateList.remove(indexOfIntermediate);//usunięcie intermediate z listy bo zginął
                    break;
                }
                else{//intermediate zjada ale nie ginie
                    Variables.board.get(aroundX).set(aroundY, "X");//ustawienie na planszy, że grzyb halucynek jest zjedzony
                    Variables.hallucinationMushroomList.remove(k);//usunięcie grzyba toxic z arraylisty bo został zjedzony
                    Variables.hallucinationMush--;
                    break;
                }
            }
        }
    }
}
