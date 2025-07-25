package Wordle;

import java.io.IOException;
import java.nio.file.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.*;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;

public class Wordle {
    private static final String WORDLE_FILE = "Data/wordles.txt";
    private static final Set<String> ALLOWED_GUESSES = loadAllowedGuesses("Data/all_allowed_guesses.txt");
    private static final LocalDate START_DATE = LocalDate.of(2025, 4, 2);
    private static final Scanner SCANNER = new Scanner(System.in);

    private enum Color {
        BLACK("\033[0;30m"), YELLOW("\033[1;33m"), GREEN("\033[0;32m"), BOLD("\033[1m"), END("\033[0;0;0m");

        private String ANSI;

        Color(String ANSI) {
            this.ANSI = ANSI;
        }

        @Override
        public String toString() {
            return ANSI;
        }
    }

    private static class Tile {
        char letter;
        Color color = Color.END;

        Tile(char letter, int row, int col) {
            this.letter = letter;
        }
    }

    private int gameType; // 0=daily, 1=random, -1=debug
    private String currentWord;
    private Map<Character, Color> letters;
    private List<List<Tile>> board = new ArrayList<>();
    private boolean endGame = false;
    private Random random;

    public Wordle(boolean isRandom) {
        this.gameType = isRandom ? 1 : 0;
        this.letters = IntStream.range('a', 'z' + 1)
                .mapToObj(c -> (char) c)
                .collect(Collectors.toMap(c -> c, c -> Color.BOLD));
        this.random = new Random();
    }

    public void startGame() {
        setRandomSeed();
        this.currentWord = chooseWord();

        if (gameType == 0) {
            System.out.println(getGameHeader());
            String timeHash = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            random.setSeed(Long.parseLong(timeHash));
        } else {
            System.out.println("Random Pydle");
        }

        wordleCLI();
    }

    private void setRandomSeed() {
        if (gameType == 0) {
            String seed = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            random.setSeed(Long.parseLong(seed));
        } else {
            random.setSeed(new Random().nextLong());
        }
    }

    private String chooseWord() {
        try {
            List<String> words = Files.readAllLines(Paths.get(WORDLE_FILE));
            return words.get(random.nextInt(words.size()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getGameHeader() {
        if (gameType == 0) {
            long dayNum = ChronoUnit.DAYS.between(START_DATE, LocalDate.now());
            return "Daily Pydle " + dayNum;
        }
        return "Random Pydle";
    }

    private void wordleCLI() {
        while (!endGame) {
            printBoard();
            System.out.print(": ");
            String userInput = SCANNER.nextLine().trim().toLowerCase();

            if (userInput.startsWith("/")) {
                endGame = handleCommand(userInput.substring(1));
                continue;
            }

            if (userInput.length() != 5) {
                System.out.println("incorrect word length");
            } else if (!ALLOWED_GUESSES.contains(userInput)) {
                System.out.println("word not on list\n\n");
            } else {
                addGuess(userInput);
                endGame = checkEndGame();
            }
        }
    }

    private void addGuess(String guess) {
        List<Tile> row = new ArrayList<>();
        int r = board.size();
        for (int i = 0; i < guess.length(); i++) {
            row.add(new Tile(guess.charAt(i), r, i));
        }
        board.add(row);
    }

    private boolean handleCommand(String cmdLine) {
        String[] parts = cmdLine.split("\\s+");
        String cmd = parts[0];

        Map<String, Runnable> commands = new HashMap<>();

        commands.put("help", () -> System.out.println(
                "/quit | close game\n/random | pick a random word for Wordle\n/daily | pick the daily word for Wordle"));

        commands.put("quit", () -> endGame = true);

        commands.put("random", () -> {
            if (gameType != 1)
                new Wordle(true).startGame();
            else
                System.out.println("Already random wordle!");
            endGame = true;
        });

        commands.put("daily", () -> {
            if (gameType != 0)
                new Wordle(false).startGame();
            else
                System.out.println("Already daily wordle!");
            endGame = true;
        });

        commands.put("philmode", () -> {
            currentWord = "burnt";
            gameType = -1;
        });

        commands.put("set", () -> {
            if (parts.length > 1 && parts[1].length() == 5) {
                currentWord = parts[1];
                gameType = -1;
                System.out.println("Set word to " + currentWord);
            } else {
                System.out.println("Usage: /set <word> (5 letters)");
            }
        });

        Runnable action = commands.get(cmd);
        if (action != null) {
            action.run();
        } else {
            System.out.println("Unknown command!");
        }

        return endGame;
    }

    private boolean checkEndGame() {
        if (board.isEmpty())
            return false;
        String guess = board.get(board.size() - 1).stream()
                .map(t -> String.valueOf(t.letter))
                .collect(Collectors.joining());
        boolean correct = guess.equals(currentWord);

        if (board.size() == 6 || correct) {
            printBoard();
            System.out.printf("You %s! The word was %s.%n", correct ? "won" : "lost", currentWord);
            System.out.println("\nShare your results!\n" + getShareable());
            System.out.print("Copy to Clipboard? [Y/N] ");
            String resp = SCANNER.nextLine().trim().toLowerCase();
            if (resp.startsWith("y")) {
                copyToClipboard("```ansi\n" + getShareable() + "```");
                System.out.println("Copied!");
            }
            return true;
        }
        return false;
    }

    private void printBoard() {
        for (List<Tile> row : board) {
            setRowColor(row);
            for (Tile t : row) {
                System.out.printf("[%s%c%s]", t.color, Character.toUpperCase(t.letter), Color.END);
            }

            System.out.println();
        }
        for (int i = board.size(); i < 6; i++) {
            System.out.println("[ ][ ][ ][ ][ ]");
        }
        printKeyboard();
    }

    private void setRowColor(List<Tile> row) {
        Map<Character, Long> freq = currentWord.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        // Greens
        for (int i = 0; i < row.size(); i++) {
            Tile t = row.get(i);
            if (t.letter == currentWord.charAt(i)) {
                freq.put(t.letter, freq.get(t.letter) - 1);
                t.color = Color.GREEN;
                letters.put(t.letter, Color.GREEN);
            }
        }

        // Yellows & blacks
        for (Tile t : row) {
            if (t.color == Color.GREEN)
                continue;
            if (currentWord.indexOf(t.letter) >= 0 && freq.getOrDefault(t.letter, 0L) > 0) {
                freq.put(t.letter, freq.get(t.letter) - 1);
                t.color = Color.YELLOW;
                letters.put(t.letter, Color.YELLOW);
            } else {
                t.color = Color.BLACK;
                if (letters.get(t.letter) == Color.BOLD) {
                    letters.put(t.letter, Color.BLACK);
                }
            }
        }
    }

    private void printKeyboard() {
        StringBuilder row = new StringBuilder();
        List<Character> kbd = "qwertyuiopadsfghkjlzxcvbnm".chars().mapToObj(c -> (char) c).toList();
        for (Character c : kbd) {
            row.append(letters.get(c).toString() + c + ' ' + Color.END);
            if ("plm".indexOf(c) >= 0)
                row.append("\n ");
            if ("z".indexOf(c) >= 0)
                row.append(" ");
        }
        System.out.println(row);
    }

    private String getShareable() {
        StringBuilder out = new StringBuilder();
        String type = gameType == 0 ? "Daily" : (gameType == 1 ? "Random" : "Easter Egg'd");
        long dayNum = ChronoUnit.DAYS.between(START_DATE, LocalDate.now());
        int outOf = board.get(board.size() - 1).stream()
                .map(t -> t.letter).map(String::valueOf)
                .collect(Collectors.joining())
                .equals(currentWord) ? board.size() : 0;
        out.append(type + " Javadle ");
        if (gameType == 0)
            out.append(dayNum + " ");
        out.append(outOf).append("/6\n");
        for (List<Tile> row : board) {
            String color;
            for (Tile t : row) {
                char symbol;
                switch (t.color) {
                    case GREEN -> {
                        symbol = '+';
                        color = Color.GREEN.toString();
                    }
                    case YELLOW -> {
                        symbol = '-';
                        color = Color.YELLOW.toString();
                    }
                    default -> {
                        symbol = 'X';
                        color = Color.BLACK.toString();
                    }
                }
                ;
                out.append(color + "[" + symbol + "]");
            }
            out.append("\n" + Color.END);
        }
        return out.toString();
    }

    private static void copyToClipboard(String text) {
        // Simple clipboard copy on macOS/Linux/Windows (requires external
        // command/tools)
        try {
            StringSelection sel = new StringSelection(text);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel, sel);
        } catch (Exception e) {
            System.err.println("Clipboard copy failed: " + e.getMessage());
        }
    }

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
        Wordle game = new Wordle(false);
        game.startGame();

        while (true) {
            System.out.print("Play again? [Y/N] ");
            String resp = SCANNER.nextLine().trim().toLowerCase();
            if (resp.startsWith("y")) {
                game = new Wordle(true);
                game.gameType = 1;
                game.startGame();
            } else if (resp.startsWith("n")) {
                break;
            } else {
                System.out.println("Unknown input.");
            }
        }
    }
}
