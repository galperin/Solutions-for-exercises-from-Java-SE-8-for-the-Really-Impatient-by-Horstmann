function newArrayList(){
    var arr = new (Java.extend(java.util.ArrayList, {
        add: function(x) {
            print('Adding ' + x);
            return Java.super(arr).add(x)
        }
    }))();
    return arr;
}

var a1 = newArrayList();
a1.add(1);
var a2 = newArrayList();
a2.add(2);
print(a1);
print(a2);