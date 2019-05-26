var express = require('express')
var app = express();
var fs = require('fs')
var path = require('path')

if (!Array.prototype.last) {
    Array.prototype.last = function () {
        return this[this.length - 1];
    };
};


var currentPath;
var imageFormats = ['jpg', 'jpeg', 'png', 'jpe']

app.use(function (req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});

app.use(express.static("dist"));

app.get('/api/ls', function (req, res) {
    let paths = req.query.path;
    if (paths) {
        currentPath = '/' + paths.split(',').join('/') + '/';
    } else {
        currentPath = '/'
    }
    let results = [];
    fs.readdir(currentPath, (err, files) => {
        for (let file of files) {
            try {
                if (fs.lstatSync(currentPath + file).isDirectory()) {
                    results.push(file);
                }
            } catch (e) { }
        }
        res.send(results);
    })
})

app.get('/api/home', function (req, res) {
    res.send(require('os').homedir().split('/'));
})

app.get('/api/imagesName', function (req, res) {
    let paths = req.query.path;
    currentPath = '/' + paths.split(',').join('/') + '/';
    fs.readdir(currentPath, (err, files) => {
        names = [];
        for (let file of files) {
            try {
                let extension = file.split('.').pop();
                if (imageFormats.indexOf(extension) != -1) {
                    names.push(file);
                }
            } catch (e) { }
        }
        res.send(names);
    })

})

app.get('/api/image', function (req, res) {
    console.log(req.query.path.split(',').join('/'))
    let imageName = req.query.path.split(',').last();
    let extension = imageName.split('.').pop();
    console.log(extension)
    if(extension == 'png') {
        res.setHeader("content-type", "image/png");
    } else {
        res.setHeader("content-type", "image/jpeg");
    }
    
    fs.createReadStream('/' + req.query.path.split(',').join('/')).pipe(res);
})

app.listen(3001, function () {
    console.log('Example app listening on port 3001!');
});
