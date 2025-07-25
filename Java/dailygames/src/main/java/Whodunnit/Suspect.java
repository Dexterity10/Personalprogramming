package Whodunnit;

import java.util.Random;
import Utility.Color;

public class Suspect {

    // hair, eye, skin
    String[] prefix = { "Mr.", "Dr.", "Ms.", "Mrs.", "Sir.", "Lord", "King" };
    String[] lastname = { "Red", "Orange", "Yellow", "Green", "Lime", "Cyan", "B. Blue", "Blue", "Violet", "Purple",
            "Indigo", "Grey", "Black", "White", "Maroon", "Crimson", "Merengue" };
    String name;

    Color hair;
    Color eyes;
    Color skin;
    Random rand = new Random();
    static final Color END = Color.END;
    static final Color[][] COLORS = new Color[][] {
            { Color.BROWN, Color.BLACK, Color.YELLOW },
            { Color.BROWN, Color.BLACK, Color.GREEN, Color.LIGHT_GREEN, Color.BLUE, Color.LIGHT_BLUE },
            { Color.BLACK, Color.BROWN, Color.LIGHT_WHITE } };

    Suspect() {
        this.name = prefix[rand.nextInt(prefix.length)] + " " + lastname[rand.nextInt(lastname.length)];
        hair = COLORS[0][rand.nextInt(3)];
        eyes = COLORS[1][rand.nextInt(6)];
        skin = COLORS[2][rand.nextInt(3)];
    }

    @Override
    public String toString() {
        return name + ": "
                + hair + "H" + END
                + eyes + "E" + END
                + skin + "S" + END;
    }

}
