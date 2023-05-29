#!/bin/bash

iterations=(10 10 10)
width=(5 10 20)

numberOfSets="${#iterations[@]}"

for (( i = 0; i < $numberOfSets; i++ ))
do
  echo "Calling Main with parameters ${iterations[$i]} ${width[$i]}"
  echo "${i}/${numberOfSets}"

  java -cp C:\Users\piotr\IdeaProjects\GrzybobranieV3\src\main\java\org\example  org.example.Main.java ${iterations[$i]} ${width[$i]} > log_script

done