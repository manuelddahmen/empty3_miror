package one.empty3.apps.vecmesh;

import javax.swing.*;
import javax.swing.text.JTextComponent;

public class Output {
    private static JLabel getText;
    public static void println(String string) {
        System.out.println(string);
        if(getText!=null)
            getText.setText(string);
    }

    public static JLabel getGetText() {
        return getText;
    }

    public static void setGetText(JLabel getText) {
        Output.getText = getText;
    }
}
