#!/usr/bin/jjs -scripting
var age = $ENV.AGE;
if($ARG.length > 0){
    age = $ARG[0];
}
if(!age || age != parseInt(age, 10)){
    do {
        age = readLine('Your age: ');
    } while(age != parseInt(age, 10))
}
print("Next year you will be ${++age}");