package Whodunnit;

import java.util.Random;
import Utility.Color;

public class Suspect {

    // hair, eye, skin
    String name = "Template Name";
    Color hair;
    Color eyes;
    Color skin;
    Random rand = new Random();
    static final Color END = Color.END;
    static final Color[][] COLORS = new Color[][] {
            { Color.BROWN, Color.BLACK, Color.YELLOW },
            { Color.BROWN, Color.BLACK, Color.GREEN, Color.LIGHT_GREEN, Color.BLUE, Color.LIGHT_BLUE },
            { Color.BLACK, Color.BROWN, Color.LIGHT_WHITE } };

    Suspect(String name) {
        this.name = name;
        hair = COLORS[1][rand.nextInt(4)];
        eyes = COLORS[2][rand.nextInt(7)];
        skin = COLORS[3][rand.nextInt(4)];
    }

    @Override
    public String toString() {
        return name + ": " + hair + "H" + END + eyes + "E" + END + skin + "S" + END;
    }

}
