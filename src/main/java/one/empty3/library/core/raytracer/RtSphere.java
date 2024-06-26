/*
 *
 *  * Copyright (c) 2024. Manuel Daniel Dahmen
 *  *
 *  *
 *  *    Copyright 2024 Manuel Daniel Dahmen
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 *
 */

package one.empty3.library.core.raytracer;

import one.empty3.library.*;

public class RtSphere extends RtObject {
    private Point3D mCenter; // Le centre de la sphere
    private double mRadius; // Le rayon de la sphere


    public RtSphere(Point3D center, double radius) {
        super();
        mCenter = center;
        mRadius = radius;
    }

    public boolean intersectsNode(RtRay ray, RtIntersectInfo intersectInfo) {

        double a, b, c;
        double delta;
        double t = -1;
        double t1, t2;
        Point3D intersect;
        Point3D tmpNormal;

        Point3D rayOrg = ray.mVStart.moins(mCenter);    // ray in space of the sphere

//CNAGES


        a = 1;
        b = rayOrg.prodScalaire(ray.mVDir);
        c = rayOrg.NormeCarree() - mRadius * mRadius;

/*
        b = rayOrg.prodScalaire(ray.mVDir);
        a = (ray.mVDir.prodScalaire(ray.mVDir));
        c = (rayOrg.prodScalaire(rayOrg) - mRadius * mRadius);
        delta = b * b - 4 * a * c;


/*
        a = ray.mVDir.norme1().moins(ray.mVStart).NormeCarree() - mRadius * mRadius;
        b = -2 * mCenter.prodScalaire(ray.mVDir.norme1());
        c = mCenter.NormeCarree();
        delta = b * b - 4 * a * c;
*/
        /*
         c = rayOrg.prodScalaire(rayOrg)-mRadius*mRadius;
        b = 2*ray.mVDir.prodScalaire(rayOrg);
        a = 1;
*/
        delta = b * b - 4 * a * c;

        if (delta < 0.0f)
            return false; // pas d'intersection


        if (intersectInfo != null) {
            if (delta != 0) {
                delta = Math.sqrt(delta);
                t1 = (-b + delta) / 2 / a;
                t2 = (-b - delta) / 2 / a;

                double max = Math.max(t1, t2);
                double min = Math.min(t1, t2);
                t = min;
                if (t2 < 0) {
                    t = t1;
                }
                if (t1 < 0) {
                    t = t2;

                    if (t2 < 0) {
                        return false;
                    }
                }

            }
        }
                /*
                if (max < 0)
                    return false; // Intersection derrière la camera
                else {
                    if (t1 >= 0 && t2 >= 0)
                        t = Math.min(t1, t2);
                    else if (t1 >= 0)
                        t = t1;
                    else if (t2 >= 0) {
                        t = t2;
                    }
                    else
                        return false; // Ne devrait pas se produire. Pour le compilateur
                        */
        else {
            t = (-b) / 2 / a;
        }
        intersect = ray.mVStart.plus(ray.mVDir.norme1().mult(t));

        tmpNormal = (intersect.moins(mCenter)).norme1();

        intersectInfo.mIntersection = intersect;
        intersectInfo.mNormal = tmpNormal;
        intersectInfo.mNode = getNode();
        intersectInfo.mMaterial = getMaterial();
        return true;

    }

}

