# empty3-library-3d

empty3.app
Moteur de rendu et éléments de conception.
Formes:

## surfaces:

Surfaces paramétrées
Cube, sphère, rectangle, extrusion, cylindre courbe

## courbes

Courbe paramétrées
Ligne, cercle, rectangle

##

Textures: couleur, image, vidéo, algorithmique

Build avec Gradle, différentes interfaces utilisateurs sont proposées

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
    <artifactId>empty3-library-3d</artifactId>
    <version>2024-</version>
</dependency>
```

## En développement. Ambitions.

## La conception de features 2d 3d.

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

Voici un exemple de run:
Run class: one.empty3.Run
Args : "originalHarris_PasteBlank"
settings.properties

![image](https://user-images.githubusercontent.com/38113629/158326067-4e881cae-26f1-42cf-b529-20a6f99d7ada.png)
![image](https://user-images.githubusercontent.com/38113629/158326114-f577abd5-0af4-4dc6-9061-aaaa7eb0d61d.png)

Avec une application Android.
![image](https://github.com/manuelddahmen/empty3_miror/assets/38113629/6a27f5db-e0c4-44ed-9731-23799b72fe10)

![Screenshot_20230701_095233](https://github.com/manuelddahmen/empty3_miror/assets/38113629/827310f3-3823-4577-a3e9-784a50934e78)

