## **Commentaire sur la bibliothèque empty3-library-3d**

**Points forts:**

* **Fonctionnalités riches:** La bibliothèque offre un large éventail de fonctionnalités pour le rendu 3D, y compris la modélisation, le texturage et l'éclairage.
* **Facile à utiliser:** La bibliothèque est conçue pour être facile à utiliser, avec une API simple et bien documentée.
* **Performances élevées:** La bibliothèque est optimisée pour les performances et peut être utilisée pour créer des applications 3D fluides et réactives.
* **Open source:** La bibliothèque est open source et gratuite à utiliser.

**Points faibles:**

* **Documentation limitée:** La documentation de la bibliothèque est un peu limitée, ce qui peut rendre difficile l'apprentissage de l'utilisation de toutes ses fonctionnalités.
* **Manque de fonctionnalités avancées:** La bibliothèque ne dispose pas de certaines fonctionnalités avancées que l'on peut trouver dans d'autres bibliothèques 3D, telles que le rendu par lancer de rayons et la simulation physique.

**Exemples d'utilisation:**

* **Création de jeux 3D:** La bibliothèque peut être utilisée pour créer des jeux 3D simples et complexes.
* **Modélisation 3D:** La bibliothèque peut être utilisée pour créer des modèles 3D d'objets et de scènes.
* **Visualisation de données:** La bibliothèque peut être utilisée pour visualiser des données 3D, telles que des nuages de points et des maillages.

**Conclusion:**

**empty3-library-3d** est une bibliothèque 3D puissante et flexible qui peut être utilisée pour créer une variété d'applications. La bibliothèque est facile à utiliser et offre de bonnes performances. Cependant, la documentation est un peu limitée et la bibliothèque manque de certaines fonctionnalités avancées.

**En plus des points forts et des points faibles mentionnés ci-dessus, voici quelques commentaires supplémentaires sur la bibliothèque :**

* **Filtres d'images:** La bibliothèque propose une variété de filtres d'images, ce qui peut être utile pour le traitement d'images et la vision par ordinateur.
* **Formats et classes d'images:** La bibliothèque prend en charge plusieurs formats d'images courants, ainsi que des classes pour représenter et manipuler des images.
* **Détecteur de contours:** La bibliothèque inclut un détecteur de contours, qui peut être utile pour la segmentation d'images et la reconnaissance d'objets.

**Dans l'ensemble, empty3-library-3d est une bibliothèque 3D prometteuse avec un large éventail de fonctionnalités. La bibliothèque est encore en développement, mais elle a le potentiel de devenir une ressource précieuse pour les développeurs 3D.**

**N'hésitez pas à me poser des questions sur la bibliothèque empty3-library-3d.**

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

