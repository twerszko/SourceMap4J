var global = this;
var window = this;
var process = {env: {}};

var console = {};
console.debug = print;
console.warn = print;
console.log = print;

load("classpath:lib/es6-shim.min.js");
load("classpath:lib/npm-jvm.js");
