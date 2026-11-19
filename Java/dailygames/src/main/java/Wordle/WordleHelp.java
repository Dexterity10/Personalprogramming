package Wordle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.jgrapht.*;

public class WordleHelp {
    private static final Set<String> ALLOWED_GUESSES = loadAllowedGuesses("Data/all_allowed_guesses.txt");

    private static Set<String> loadAllowedGuesses(String path) {
        try {
            return new HashSet<>(Files.readAllLines(Paths.get(path))
                    .stream()
                    .map(String::trim)
                    .collect(Collectors.toSet()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Set<String> likeWeary = new HashSet<>();
        for (String i : ALLOWED_GUESSES) {
            for (char j : "Weary".toCharArray()) {
                if (i.toCharArray()[0] == j) {
                    // System.out.println("yes");
                    likeWeary.add(i);
                }
            }
        }
        System.out.println(likeWeary);
    }
}