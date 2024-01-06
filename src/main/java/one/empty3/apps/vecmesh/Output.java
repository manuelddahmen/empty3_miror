package one.empty3.apps.vecmesh;

import javax.swing.*;
import javax.swing.text.JTextComponent;

public class Output {
    private static JLabel getText;
    public static void println(String string) {
        System.out.println(string);
        if(getText!=null)
            getText.setText(string == null ?"":(string.length()<=20?string:string.substring(0, 20))+
                    string.length());
    }

    public static JLabel getGetText() {
        return getText;
    }

    public static void setGetText(JLabel getText) {
        Output.getText = getText;
    }
}
