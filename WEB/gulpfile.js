var gulp = require('gulp');
var wiredep = require('wiredep').stream;
var inject = require('gulp-inject');
var clean = require('gulp-clean');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var stripDebug = require('gulp-strip-debug');
var minifyCSS = require('gulp-minify-css');
var beautify = require('gulp-beautify');


var base = {
    src : 'src/',
    dest: 'dest/',
    tmp: 'tmp/',
    bower: 'ext/'
};
var paths = {
    html: ['index.html'],
    css: ['css/**/*.css'],
    js: ['js/**/*.js'],
    app: ['app/**/*.js'],
};

gulp.task('cleanDest', function() {
    return gulp.src(base.dest, {read: false})
        .pipe(clean());
});
gulp.task('cleanTMP', function() {
    return gulp.src(base.tmp, {read: false})
        .pipe(clean());
});
gulp.task('clean',['cleanDest','cleanTMP']);

gulp.task('idx',['copyApp'], function () {
    var target = gulp.src(paths.html,{cwd: base.src});
    var sources = gulp.src(paths.js.concat(paths.css), {cwd: base.dest, read: false});

    return target.pipe(inject(sources,{ addRootSlash: false}))
            .pipe(wiredep({
            ignorePath:'../'
            }))
        .pipe(gulp.dest('./dest'));
});

gulp.task('index',['clean','concatJS','concatCSS','copyBower','copyTmpl'], function () {
    var target = gulp.src(paths.html,{cwd: base.src});
    var sources = gulp.src(paths.js.concat(paths.css), {cwd: base.dest, read: false});

    return target.pipe(inject(sources,{ addRootSlash: false}))
            .pipe(wiredep({
            ignorePath:'../'
            }))
        .pipe(gulp.dest('./dest'));
});
gulp.task('indexDev',['clean','concatJSDev','concatCSS','copyBower','copyTmpl'], function () {
    var target = gulp.src(paths.html,{cwd: base.src});
    var sources = gulp.src(paths.js.concat(paths.css), {cwd: base.dest, read: false});

    return target.pipe(inject(sources,{ addRootSlash: false}))
            .pipe(wiredep({
            ignorePath:'../'
            }))
        .pipe(gulp.dest('./dest'));
});
gulp.task('minifyJS',['clean'],function(){
   return gulp.src(paths.app.concat(paths.js), {cwd: base.src})
       .pipe(stripDebug())
       .pipe(uglify())
       .pipe(gulp.dest(base.tmp+"/js/"));
});
gulp.task('concatJS',['minifyJS'],function(){
    return gulp.src(paths.app.concat(paths.js), {cwd: base.tmp})
        .pipe(concat("scripts.min.js"))
        .pipe(gulp.dest(base.dest+"/js/"));

});
gulp.task('concatJSDev',function(){ //bez minifikacji do dev
    return gulp.src(paths.app.concat(paths.js), {cwd: base.src})
        .pipe(concat("scripts.min.js"))
        .pipe(gulp.dest(base.dest+"/js/"));

});
gulp.task('minifyCSS',['clean'],function(){
    return gulp.src(paths.css, {cwd: base.src})
        .pipe(minifyCSS())
        .pipe(gulp.dest(base.tmp+"/css/"));
});
gulp.task('concatCSS',['minifyCSS'],function(){
    return gulp.src(paths.css, {cwd: base.tmp})
        .pipe(concat("style.min.css"))
        .pipe(gulp.dest(base.dest+"/css/"));

});

gulp.task('copyBower',['clean'], function() {
    gulp.src("**/*", {cwd: base.bower})
        .pipe(gulp.dest(base.dest+'/ext/'));
});
gulp.task('copyApp',['clean'],function(){
    gulp.src("**/*", {cwd: base.src+"/app/"})
    .pipe(gulp.dest(base.dest+'/app'));
});

gulp.task('copyTmpl',['clean'],function(){
    gulp.src("**/*.html", {cwd: base.src+"/app/"})
    .pipe(gulp.dest(base.dest+'/app'));
});

gulp.task('default', ['clean'],function(){

});


gulp.task('bf',function(){
gulp.src(paths.app,{cwd: base.src})
    .pipe(beautify({indentSize: 2}))
    .pipe(gulp.dest(base.src+"app/"));
});

gulp.task('watch',['index'], function() {
    gulp.watch(paths.app.concat(paths.css).concat(paths.js).concat(['src/**/*.html']), ['index']);
});

gulp.task('watchDev',['indexDev'], function() {
    gulp.watch(["src/**/*.css"].concat(["src/**/*.js"]).concat(['src/**/*.html']), ['indexDev']);
});
