#!/usr/bin/jjs -scripting
for(var v in $ENV){
    print("${v} -> ${$ENV[v]}");
}