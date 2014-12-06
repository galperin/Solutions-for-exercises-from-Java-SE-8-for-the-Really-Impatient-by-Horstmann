#!/usr/bin/jjs -scripting
function pipe(){
    var result;
    for (var i = 0; i < arguments.length;  i++) {
         result = $EXEC(arguments[i], result);
    }
    return result;
}

print(pipe('find .', 'xargs grep function', 'sort -r'));