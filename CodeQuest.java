
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CodeQuest {

    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    static String playerName;
    static int score = 0;
    static int lives = 3;
    static int roomsCompleted = 0;
    static int gamesPlayed = 0;
    static int gamesWon = 0;

    static ArrayList<String> history = new ArrayList<>();

    static final String SAVE_FILE = "scores.txt";

    public static void main(String[] args) {

        showTitle();

        boolean running = true;

        while (running) {

            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. New Game");
            System.out.println("2. How to Play");
            System.out.println("3. View Statistics");
            System.out.println("4. View Game History");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = readInt();

            switch (choice) {

                case 1:
                    startGame();
                    break;

                case 2:
                    showInstructions();
                    break;

                case 3:
                    showStatistics();
                    break;

                case 4:
                    showHistory();
                    break;

                case 5:
                    System.out.println("\nThanks for playing CodeQuest!");
                    System.out.println("Keep coding, keep exploring!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option! Choose 1-5.");
            }
        }

        scanner.close();
    }

    // GAME TITLE

    static void showTitle() {

        System.out.println("========================================");
        System.out.println("              CODEQUEST");
        System.out.println("         THE LOST ALGORITHM");
        System.out.println("========================================");
        System.out.println("A mysterious digital world awaits...");
        System.out.println("Your mission: recover the lost algorithm!");
    }

    // SAFE INTEGER INPUT

    static int readInt() {

        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a number: ");
            scanner.nextLine();
        }

        int number = scanner.nextInt();
        scanner.nextLine();

        return number;
    }

    // HOW TO PLAY

    static void showInstructions() {

        System.out.println("\n========== HOW TO PLAY ==========");
        System.out.println("- Explore three mysterious rooms.");
        System.out.println("- Solve puzzles to earn points.");
        System.out.println("- Correct answers earn 10 points.");
        System.out.println("- You have 3 lives.");
        System.out.println("- A wrong answer costs one life.");
        System.out.println("- Complete all rooms to win!");
        System.out.println("- Your results are saved to scores.txt.");
    }

    // START GAME

    static void startGame() {

        System.out.print("\nEnter your player name: ");
        playerName = scanner.nextLine().trim();

        if (playerName.isEmpty()) {
            playerName = "Player";
        }

        score = 0;
        lives = 3;
        roomsCompleted = 0;
        history.clear();

        gamesPlayed++;

        System.out.println("\nWelcome, " + playerName + "!");
        System.out.println("Your adventure begins now...");

        boolean won = true;

        for (int room = 1; room <= 3; room++) {

            if (lives <= 0) {
                won = false;
                break;
            }

            boolean completed = enterRoom(room);

            if (!completed) {
                won = false;
                break;
            }

            roomsCompleted++;
        }

        if (won && roomsCompleted == 3 && lives > 0) {

            System.out.println("\n================================");
            System.out.println("          YOU WIN!");
            System.out.println("================================");
            System.out.println("You recovered the lost algorithm!");
            System.out.println("Final score: " + score);

            gamesWon++;

        } else {

            System.out.println("\n========== GAME OVER ==========");
            System.out.println("Your adventure has ended.");
            System.out.println("Rooms completed: " + roomsCompleted);
            System.out.println("Final score: " + score);
        }

        saveResult();
    }

    // ROOM SYSTEM

    static boolean enterRoom(int roomNumber) {

        System.out.println("\n================================");
        System.out.println("          ROOM " + roomNumber);
        System.out.println("================================");

        switch (roomNumber) {

            case 1:
                System.out.println("The Gate of Numbers");
                break;

            case 2:
                System.out.println("The Logic Chamber");
                break;

            case 3:
                System.out.println("The Final Algorithm");
                break;
        }

        System.out.println("Solve the puzzle to continue!");

        boolean correct = askPuzzle(roomNumber);

        if (correct) {

            System.out.println("Room unlocked!");
            System.out.println("Current score: " + score);

            history.add("Room " + roomNumber + ": Completed");

            return true;

        } else {

            System.out.println("You could not unlock this room.");

            history.add("Room " + roomNumber + ": Failed");

            return false;
        }
    }

    // PUZZLE SYSTEM

    static boolean askPuzzle(int roomNumber) {

        int answer;
        int correctAnswer;

        switch (roomNumber) {

            case 1:
                System.out.println("\nPuzzle: What comes next?");
                System.out.println("2, 4, 6, 8, ?");
                System.out.print("Your answer: ");

                correctAnswer = 10;
                break;

            case 2:
                System.out.println("\nPuzzle: Solve the equation.");
                System.out.println("If x + 7 = 15, what is x?");
                System.out.print("Your answer: ");

                correctAnswer = 8;
                break;

            default:
                System.out.println("\nFINAL CHALLENGE!");
                System.out.println("What is 2 to the power of 5?");
                System.out.print("Your answer: ");

                correctAnswer = 32;
                break;
        }

        answer = readInt();

        if (answer == correctAnswer) {

            int points = 10;

            if (roomNumber == 3) {
                points = 20;
            }

            score += points;

            System.out.println("Correct! +" + points + " points!");

            return true;

        } else {

            lives--;

            System.out.println("Incorrect answer!");
            System.out.println("Correct answer: " + correctAnswer);
            System.out.println("Lives remaining: " + lives);

            return false;
        }
    }

    // STATISTICS

    static void showStatistics() {

        System.out.println("\n========== STATISTICS ==========");
        System.out.println("Games played: " + gamesPlayed);
        System.out.println("Games won: " + gamesWon);
        System.out.println("Current score: " + score);
        System.out.println("Rooms completed: " + roomsCompleted);
        System.out.println("Lives remaining: " + lives);
    }

    // GAME HISTORY

    static void showHistory() {

        System.out.println("\n========== GAME HISTORY ==========");

        if (history.isEmpty()) {
            System.out.println("No game history yet.");
            return;
        }

        for (String event : history) {
            System.out.println("- " + event);
        }
    }

    // SAVE RESULTS TO FILE

    static void saveResult() {

        try (FileWriter writer = new FileWriter(SAVE_FILE, true)) {

            writer.write(
                "Player: " + playerName
                + " | Score: " + score
                + " | Rooms: " + roomsCompleted
                + " | Lives: " + lives
                + System.lineSeparator()
            );

            System.out.println("Game result saved successfully!");

        } catch (IOException e) {

            System.out.println("Could not save the result.");
            System.out.println("Reason: " + e.getMessage());
        }
    }
}
