package one.empty3.apps.vecmesh;

import javax.swing.*;
import javax.swing.text.JTextComponent;

public class Output {
    private static JButton getText;
    public static void println(String string) {
        System.out.println(string);
        if(getText!=null)
            getText.setText(string);
    }

    public static JButton getGetText() {
        return getText;
    }

    public static void setGetText(JButton getText) {
        Output.getText = getText;
    }
}
