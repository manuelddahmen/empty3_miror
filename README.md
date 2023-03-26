# empty3
empty3.one
Rendering engine. Shapes: surfaces, math-related
Drawings, curves, textures with pictures, movies
or colors.

#In progress or not:
- Lighting model. 
- Texture with geometric structure.
- Burbs? Nurbs.
- real modelling interface. Graphical or
  touch.
- Glsl clone compiler, interpreter and 
  rendering.

When you build with maven tool, assuming you have
correctly set jdk home and exe path, and maven
too, internet connection, space disk and memory
size enough...

If you miss a property check this
https://maven.apache.org/guides/introduction/introduction-to-profiles.html

Or make a new issue.

#In progress 
##October 2021 : move
Movement : make persona walk. Idea: program object for moving
cylinder and sphere and beziers. Make it move from/to (Vec3 
linear displacement).
MoveCollection: actions (to walk, to run, to sit, to smoke)
Move: individual member move. 

2022 Automn

``` _._
```

# Feature

https://drive.google.com/folderview?id=10CXWU2JPVsNMbHrAssxN23KzizzNkvHg
Documentation utilisée.

## Filtres d'images

Filtres : flou Gauss, Gradient X, Y, outter dot product. SobelX, SobelY. localextrema, histogramme des densites, harris
matrix c(x,y)

## Formats et classes d'images.

- BufferedImage Int Rgb (sans rgb)
- format jpg en sortie
- PixM components matrix of pictures. color component based.
- M3 components
  + matrix (columnsIn, linesIn) internal matrix at x, y
- conversion to BufferedImage, minMax normalize linear
- gradient. dx, dy, phase atan dy/dy atan dx/dy
- detecteur de contours

## maven use with pom.xml dependency

```
<dependency>
    <groupId>one.empty3</groupId>
    <artifactId>feature</artifactId>
    <version>2020.4.4</version>
</dependency>
```

## En développement. Ambitions.

https://github.com/manuelddahmen/feature

## La conception de features 2d 3d.

Les collections d'images contenant des
_feature_ (caractéristiques) ou d'un ensemble de _features_.

Features matching

Base de données à mettre au point. sum(Circle.i) dist/ c.r2

#    

```
file : ./settings.properties

username=user98
password=ghhyvj
host=ftp.example.net
port=21
directory=/myimages/selection/input
classname=one.empty3.feature.Transform1,one.empty3.feature.Histogram2,one.empty3.feature.GradProcess
```

## Feature extraction, classification

Faire les histogrammes de toutes les parties des images. Rechercher les histogrammes de différences minimales
(-autour des features.)

## Exemple
Voici un exemple de run:
Run class: one.empty3.Run
Args : "originalHarris_PasteBlank"
settings.properties
```
# Effect blue eyes white hairs
username=empty3.one
password=yfgvu75rtr44fsfii8yr
host=empty3.one
port=21
in.device=local
in.directory=../images_faces/
out.device=local
out.directory=../o/originalHarris_PasteBlank/
directory=/empty3/feature/processes
classname=one.empty3.feature.TrueHarrisProcess,one.empty3.feature.TrueHarrisProcess
#  ,one.empty3.feature.selection.PasteBlank
#classname=one.empty3.feature.DiffE
effect.0.min=double:0.3
maxRes=400
```
![image](https://user-images.githubusercontent.com/38113629/158326067-4e881cae-26f1-42cf-b529-20a6f99d7ada.png)
![image](https://user-images.githubusercontent.com/38113629/158326114-f577abd5-0af4-4dc6-9061-aaaa7eb0d61d.png)



Manuel DAHMEN.
