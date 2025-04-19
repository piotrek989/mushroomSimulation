#!/bin/bash

width=(5 15 25 35 45 50)
height=(5 15 25 35 45 50)


numberOfSets="${#width[@]}"
for (( i = 0; i < numberOfSets; i++ ))
do
  echo "szerokosc:${width[$i]} wysokosc:${height[$i]}"
  echo "begginerzyDead interDead advDead"
  for((j = 0; j<100 ;j++))
  do
    #echo "Calling Main with parameters ${height[$i]} ${width[$i]} ${mushrooms[$i]}"
    #echo "${i}/${numberOfSets}"
   java -jar ./build/libs/GrzybobranieV3-1.0-SNAPSHOT.jar ${width[$i]} ${height[$i]}
  done
  echo
done