# empty3
[![Download empty3](https://a.fsdn.com/con/app/sf-download-button)](https://sourceforge.net/projects/empty3/files/latest/download)
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
ie. tubulaire3.soulCurve.coefficients[0] from current
position to vec3 moveVector in 3 sec.
