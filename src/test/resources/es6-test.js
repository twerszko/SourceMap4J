function testEs6(){
    var set = new Set();
    set.add('a');
    set.add('b');
    set.add('c');
    return Array.from(set);
}