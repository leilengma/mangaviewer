var fs = require('fs')


var spawn = require('child_process').spawn,
    list  = spawn('cmd');

list.stdout.on('data', function (data) {
  console.log('stdout: ' + data);
});

list.stderr.on('data', function (data) {
  console.log('stderr: ' + data);
});

list.on('exit', function (code) {
  console.log('child process exited with code ' + code);
});

list.stdin.write('wmic logicaldisk get name\n');
list.stdin.end();


// var path = '/360Downloads/'
// console.log(require('os').homedir().split('\\'));
// fs.readdir(path, (err, files) => {
//     let paths ='360Downloads';
//     let path = '/' 
//     if (paths) {
//         path = '/' + paths.split(',').join('/') + '/';
//     } 
//     console.log(path);
//     files = files.filter(item => !(/(^|\/)\.[^\/\.]/g).test(item));
//     for (let file of files) {
//         try {
//             console.log(file, fs.lstatSync(path + file).isDirectory());
//         }catch (e) {      
//         }      
//     }

// })