'use strict'

var gulp       = require('gulp')
var sass       = require('gulp-ruby-sass')
var prefix     = require('gulp-autoprefixer')
var sourcemaps = require('gulp-sourcemaps')

gulp.task('build', function() {
  return sass('resources/sass/app.sass', { sourcemap: true })
    .pipe(prefix("> 0%"))
    .pipe(sourcemaps.write('.'))
    .pipe(gulp.dest('resources/public/css'))
})

gulp.task('watch', function() {
  return gulp.watch('resources/sass/**/*.sass', ['build'])
})
