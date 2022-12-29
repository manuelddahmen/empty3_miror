/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package one.empty3.neuralnetwork;

public class Loss {
    /***
     * Fonction de perte Cross Entropy
     * Permet de calculer l'erreur (la différence entre la sortie du réseau et la sortie que serait
     * celle d'un item identifié (train je pense).
     * @param sumX Taille en X de la donnée
     * @param sumY Taille en Y de la donnée (0= vecteur)
     * @param actual Sortie du reseau constatée
     * @param expected Sortie prédite
     * @return Mesure 'CrossEntropy' de l'erreur
     */
    public static double[] crossentropy(int sumX, int sumY, double [] actual, double [] expected) {
        double[] result = new double[expected.length];
        for (int i = 0; i < expected.length; i++) {
                result[i] =-expected[i]*Math.log(actual[i]);

        }
        double[] res2 = new double[result.length / sumX];
        for(int j=0; j<sumY; j++) {
            for (int i = 0; i < sumX; i++) {
                res2[j]+= result[res2.length/sumY+i];
            }
        }
        return res2;
    }

}
