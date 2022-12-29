/*
 * Copyright (c) 2022. Manuel Daniel Dahmen
 */

package one.empty3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Run {
    public static void main(String [] args) {
        try {
        List<String> args2 = new ArrayList<>();
        Properties properties = new Properties();
        properties.load(new FileInputStream("runtestobjetsub.txt"));
        Scanner scanIn = new Scanner(System.in);      
        boolean delete = false;            
        properties.forEach((key, value ) -> {
            String [] line = new String []{(String)key, (String)value};
            String s;
            Logger.getAnonymousLogger().log(Level.INFO, "var "+key+"\n\tdefault:\t"+value+"\n\tchange ? \t");

            s = scanIn.nextLine(); 

             

            if(s.length()>0 && !s.equals("\n")) {
              args2.add(line[0]+"="+value);
               properties.replace(key, s);

            } else {
               args2.add(line[0]+"="+value);
               properties.replace(key, value);
            }
        });
        properties.store(new FileOutputStream("runtestobjetsub.txt"),
            null);
        args = new String [args2.size()];
        for(int i=0; i<args2.size(); i++)
            args[i] = args2.get(i);
        TestRun.main(args);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
