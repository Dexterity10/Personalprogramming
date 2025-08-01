package Whodunnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import Utility.Color;

public class Main {

    List<Suspect> susList = new ArrayList<>();
    HashMap<String, Color> clues = new HashMap<>();
    int diff = 5;
    Suspect culprit;
    Random rand = new Random();

    private void getList() {
        for (int i = 0; i < diff + 1; i++) {
            Suspect person = new Suspect();
            susList.add(person);
        }
    }

    private void pickCulprit() {
        culprit = susList.get(rand.nextInt(susList.size()));
    }

    private void printList() {
        for (int i = 0; i < susList.size(); i++) {
            System.out.println(i + 1 + ") " + susList.get(i));
        }
    }

    private boolean guess(int index) {
        return culprit == susList.get(index - 1);
    }

    private void startGame() {
        // TODO: add difficulty options. 3,6,9 suspects

        getList();
        pickCulprit();
        System.out.println(culprit);
        System.out.println(
                Color.BOLD + susList.remove(0).name
                        + " has died." + Color.END
                        + " You are the detective on the case. Find the murderer before it's too late!");
        printList();
    }

    private boolean commandMan(String[] parts) {
        String command = parts[0].toLowerCase();
        switch (command) {
            case "quit" -> {
                System.out.println("Bye bye!");
                return false;
            }
            case "guess" -> {
                if (parts.length < 2) {
                    System.out.println("Please provide the index of your suspect to guess them.");
                } else {
                    try {
                        int number = Integer.parseInt(parts[1]);
                        if (guess(number)) {
                            System.out.println("The culprit was " + Color.BOLD + culprit.name + Color.END
                                    + ". You got it correct!");
                            return false;
                        } else {
                            System.out.println("Incorrect!");
                            return true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format.");
                    }
                }
            }
            case "help" -> {
                System.out
                        .print("""
                                Commands:
                                    Quit | Exits the program
                                    Guess | Make an accusation. If you are wrong, someone dies and you gain a new clue. If you are right, you win.

                                """);
            }
        }
        return true;
    }

    private void loop() {
        try (Scanner scan = new Scanner(System.in)) {
            boolean running = true;
            int days = 0;
            while (running) {
                days++;
                System.out.println("\nDay " + days + ": ");
                switch (rand.nextInt(3)) {
                    case 0 -> {
                        clues.put("Hair", culprit.hair);
                    }
                    case 1 -> {
                        clues.put("Eyes", culprit.eyes);
                    }
                    case 2 -> {
                        clues.put("Skin", culprit.skin);
                    }
                    default -> {
                        System.out.println("error! Got a case outside 0,2");
                    }
                }

                System.out.print("The culprit has ");
                String toPrint = clues.entrySet().stream()
                        .map(entry -> entry.getValue() + entry.getValue().name().substring(0, 1)
                                + entry.getValue().name().substring(1).toLowerCase() + " " + entry.getKey() + Color.END)
                        .collect(Collectors.joining(", "));
                System.out.println(toPrint);
                String line = scan.nextLine();
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 0)
                    continue;
                running = commandMan(parts);

            }
        }

    }

    public static void main(String[] args) {
        Main game = new Main();
        game.startGame();

        // game loop
        game.loop();
    }
}