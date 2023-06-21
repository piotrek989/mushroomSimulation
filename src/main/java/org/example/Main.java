package org.example;

public class Main {
    public static void main(String[] args) {
        /*
       if (args.length < 2) {
            System.out.println("Nie podano wystarczającej liczby argumentów.");
            System.out.println("Użycie: java Main <interacje> <szerokosc> ");
            return;
        }
        int param1 = Integer.parseInt(args[0]);
        int param2 = Integer.parseInt(args[1]);
        */
        //if sprawdza poprawność zadanych wartości (tak żeby wszystko zmieściło się na planszy)

        int iteraction = 0;
        if (Forest.getForestWidth() < 0 && Forest.getForestWidth() > 100
                || Forest.forestHeight < 0 && Forest.forestHeight > 100) {
            System.out.println("wpisano zle parametry planszy");
        } else if (Forest.percentOfToxic > 100 && Forest.percentOfToxic < 0
                || Forest.percentOfHallucination > 100 && Forest.percentOfHallucination < 0
                || Forest.mushrooms <= 0//grzybów musi byc minimum 1
                || Forest.mushrooms > Forest.getForestWidth() * Forest.forestHeight - 1
                || Forest.percentOfHallucination + Forest.percentOfToxic > 100) {//nie może byc wiecej niż 100% grzybów
            System.out.println("wpisano zle parametry grzybow");
        } else if (Forest.beginnerPickers < 0
                || Forest.beginnerPickers > Forest.getForestWidth() * Forest.forestHeight - Forest.mushrooms
                || Forest.advancedPickers < 0
                || Forest.advancedPickers > Forest.getForestWidth() * Forest.forestHeight - Forest.mushrooms - Forest.beginnerPickers
                || Forest.intermediatePickers < 0
                || Forest.intermediatePickers > Forest.getForestWidth() * Forest.forestHeight - Forest.mushrooms - Forest.beginnerPickers - Forest.advancedPickers
                || Forest.intermediatePickers + Forest.advancedPickers + Forest.beginnerPickers <= 0) {//nie może nie byc grzybiarzy - symulacje bez sensu
            System.out.println("wpisano zle parametry grzybiarzy");
        } else {

            Forest.createForest();

            for (int i = 0; i < Forest.nontoxicMush; i++) {//dopisywanie nowych (healthy) grzybów do planszy
                int[] coordinatesOfNontoxic = NontoxicMushroom.fulfillmentWithMushrooms(Forest.H);//nontoxic
                int coordinateX = coordinatesOfNontoxic[0];//x
                int coordinateY = coordinatesOfNontoxic[1];//y
                NontoxicMushroom nontoxic = new NontoxicMushroom(coordinateX, coordinateY);//firstelement to x, secondelement to y
                Forest.nontoxicMushroomList.add(nontoxic);
            }
            for (int i = 0; i < Forest.toxicMush; i++) {//dopisywanie nowych (toxic) grzybów do planszy
                int[] coordinatesOfToxic = ToxicMushroom.fulfillmentWithMushrooms(Forest.P);//toxic
                int coordinateX = coordinatesOfToxic[0];//x
                int coordinateY = coordinatesOfToxic[1];//y
                ToxicMushroom toxic = new ToxicMushroom(coordinateX, coordinateY);//firstelement to x, secondelement to y
                Forest.toxicMushroomList.add(toxic);
            }
            for (int i = 0; i < Forest.hallucinationMush; i++) {//dopisywanie nowych (hallucination) grzybów do planszy
                int[] coordinatesOfHallucination = HallucinationMushroom.fulfillmentWithMushrooms(Forest.L);//hallucination
                int coordinateX = coordinatesOfHallucination[0];//x
                int coordinateY = coordinatesOfHallucination[1];//y
                HallucinationMushroom hallucination = new HallucinationMushroom(coordinateX, coordinateY);//firstelement to x, secondelement to y
                Forest.hallucinationMushroomList.add(hallucination);
            }

            for (int i = 0; i < Forest.beginnerPickers; i++) {//dopisywanie beginnerów do planszy
                int[] coordinatesOfBegginer = MushroomPicker.checkAndGiveFirstPosition(Forest.B);
                int coordinateX = coordinatesOfBegginer[0];//x
                int coordinateY = coordinatesOfBegginer[1];//y
                BeginnerMushroomPicker begginer = new BeginnerMushroomPicker(0, coordinateX, coordinateY);//firelement to x, secelement to y
                Forest.beginnersList.add(begginer);
            }
            for (int i = 0; i < Forest.advancedPickers; i++) {//dopisywanie advanced do planszy
                int[] coordinatesOfAdvanced = AdvancedMushroomPicker.checkAndGiveFirstPosition(Forest.A);
                int coordinateX = coordinatesOfAdvanced[0];//x
                int coordinateY = coordinatesOfAdvanced[1];//y
                AdvancedMushroomPicker advanced = new AdvancedMushroomPicker(0, coordinateX, coordinateY);//firelement to x, secelement to y
                Forest.advancedList.add(advanced);
            }
            for (int i = 0; i < Forest.intermediatePickers; i++) {//dopisywanie intermediate do planszy
                int[] coordinatesOfIntermediate = AdvancedMushroomPicker.checkAndGiveFirstPosition(Forest.I);
                int coordinateX = coordinatesOfIntermediate[0];//x
                int coordinateY = coordinatesOfIntermediate[1];//y
                IntermediateMushroomPicker intermediate = new IntermediateMushroomPicker(0, coordinateX, coordinateY);//firelement to x, secelement to y
                Forest.intermediateList.add(intermediate);
            }
            Forest.forestPrint();


            outerLoop:
            while (true) {
                for (int i = 0, k = 0, m = 0; m < Forest.advancedList.size() || i < Forest.beginnersList.size() || k < Forest.intermediateList.size(); i++, k++, m++) {//sprawdza co jest wokół grzybiarzy od 1 do ostatniego!
                    if (i < Forest.beginnersList.size()) {//musimy sprawdzać ten warunek
                        int n = MushroomPicker.checkTheKind(Forest.beginnersList.get(i).getPositionX(), Forest.beginnersList.get(i).getPositionY(), i, "B");//metoda zwraca 0 gdy begginer nie ginie i -1 gdy ginie
                        i = i + n;
                    }
                    if (k < Forest.intermediateList.size()) {//musimy sprawdzać ten warunek
                        int n = MushroomPicker.checkTheKind(Forest.intermediateList.get(k).getPositionX(), Forest.intermediateList.get(k).getPositionY(), k,"I");//metoda zwraca 0 gdy intermediate nie ginie i -1 gdy ginie
                        k = k + n;
                    }
                    if (m < Forest.advancedList.size()) {//musimy sprawdzać ten warunek
                        int n = MushroomPicker.checkTheKind(Forest.advancedList.get(m).getPositionX(), Forest.advancedList.get(m).getPositionY(), m,"A");//metoda zwraca 0 gdy advanced nie ginie i -1 gdy ginie
                        m = m + n;
                    }
                    //sytuacja, w której nie ma już grzybów
                    if (Forest.toxicMush + Forest.nontoxicMush + Forest.hallucinationMush == 0) {
                        System.out.println("Symulacja zakonczona - wszystkie grzyby zostaly zebrane");
                        break outerLoop;
                        //sytuacja, w której nie ma już grzybiarzy
                    } else if (Forest.beginnersList.size() + Forest.intermediateList.size() + Forest.advancedList.size() == 0) {
                        System.out.println("Symulacja zakonczona - wszyscy zgineli");
                        Forest.forestPrint();
                        System.out.println(Forest.intermediateList.size());
                        break outerLoop;
                        //sytuacja, w której zostali sami advanced i grzyby toxic
                    } else if (Forest.beginnersList.size() + Forest.intermediateList.size() == 0 && Forest.nontoxicMush + Forest.hallucinationMush == 0) {
                        System.out.println("Symulacja zakonczona - zostali tylko grzybiarze zaawansowani i trujace grzyby");
                        Forest.forestPrint();
                        break outerLoop;
                        //sytuacja, w której zostali sami beginnerzy i halucynki
                    } else if (Forest.intermediateList.size() + Forest.advancedList.size() == 0 && Forest.nontoxicMush + Forest.toxicMush == 0) {
                        System.out.println("Symulacja zakonczona - zostali tylko grzybiarze poczatkujacy i grzyby halucynki");
                        Forest.forestPrint();
                        break outerLoop;
                    }
                }
                System.out.println();
                Forest.forestPrint();

                for (int n = 0; n < Forest.beginnersList.size(); n++) {//random walk
                    MushroomPicker.randomWalk(Forest.beginnersList.get(n).getPositionX(), Forest.beginnersList.get(n).getPositionY(), Forest.B);//sprawdzamy czy wokól postaci begginer nie ma czasem grzyba jadalnego którego może zebrac
                }
                for (int u = 0; u < Forest.intermediateList.size(); u++) {//random walk
                    MushroomPicker.randomWalk(Forest.intermediateList.get(u).getPositionX(), Forest.intermediateList.get(u).getPositionY(), Forest.I);
                }
                for (int l = 0; l < Forest.advancedList.size(); l++) {//random walk
                    MushroomPicker.randomWalk(Forest.advancedList.get(l).getPositionX(), Forest.advancedList.get(l).getPositionY(), Forest.A);
                }
                System.out.println();
                Forest.forestPrint();
                iteraction++;

            }
            //wypisanie wyników po skończonej symulacji
            System.out.println("---------- Podsumowanie symulacji ----------");

            //wypisanie grzybiarzy, którzy przeżyli i ilości grzybów, jakie zebrali
            System.out.println("Ilosci zebranych grzybow:");
            System.out.println("grzybiarze zaawansowani:");
            for (int i = 0; i < Forest.advancedList.size(); i++)
            {
                System.out.println("grzybiarz" + (i+1) +" ilosc: " + Forest.advancedList.get(i).getScore());
            }
            System.out.println();
            System.out.println("grzybiarze sredniozaawansowani:");
            for (int i = 0; i < Forest.intermediateList.size(); i++)
            {
                System.out.println("grzybiarz" + (i+1) + " ilosc: " + Forest.intermediateList.get(i).getScore());
            }
            System.out.println();
            System.out.println("grzybiarze poczatkujacy:");
            for (int i = 0; i < Forest.beginnersList.size(); i++)
            {
                System.out.println("grzybiarz" + (i+1) + " ilosc " + Forest.beginnersList.get(i).getScore());
            }
        }
        /*
        System.out.println("Przykładowe parametry wejściowe:");
        System.out.println("iteracje: " + param1);
        System.out.println("szerokosc: " + param2);
        */
    }
}