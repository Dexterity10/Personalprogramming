package Whodunnit;

import java.util.ArrayList;
import java.util.Random;

import Utility.Color;

public class Suspect {

    // hair, eye, skin
    String[] prefix = { "Mr.", "Dr.", "Ms.", "Mrs.", "Sir.", "Lord", "King" };
    String[] surname = { "Red", "Orange", "Yellow", "Green", "Lime", "Cyan", "B. Blue", "Blue", "Violet", "Purple",
            "Indigo", "Grey", "Black", "White", "Maroon", "Crimson", "Merengue" };
    String name;

    Color hair;
    Color eyes;
    Color skin;
    Random rand = new Random();
    static final Color END = Color.END;
    static final Color[] HAIR = new Color[] { Color.BLACK, Color.YELLOW, Color.WHITE, Color.RED };
    static final Color[] EYES = new Color[] { Color.BROWN, Color.BLACK, Color.GREEN, Color.LIGHT_GREEN, Color.BLUE,
            Color.LIGHT_BLUE };
    static final Color[] SKIN = new Color[] { Color.BLACK, Color.BROWN, Color.WHITE };
    static final ArrayList<Color[]> NPR = new ArrayList<>();

    static {
        for (Color hairColor : HAIR) {
            for (Color eyeColor : EYES) {
                for (Color skinColor : SKIN) {
                    NPR.add(new Color[] { hairColor, eyeColor, skinColor });
                }
            }
        }
    }

    Suspect() {
        this.name = prefix[rand.nextInt(prefix.length)] + " " + surname[rand.nextInt(surname.length)];
        int i = rand.nextInt(0, NPR.size());
        hair = NPR.get(i)[0];
        eyes = NPR.get(i)[1];
        skin = NPR.get(i)[2];
        NPR.remove(i);

    }

    @Override
    public String toString() {
        return name + ": "
                + hair + "H" + END
                + eyes + "E" + END
                + skin + "S" + END;
    }

}
