package student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of the IGameList interface that manages a list of board games
 * that a user wants to play.
 */
public class GameList implements IGameList {
    /** recording the games*/
    private final Set<BoardGame> games;
    
    /**
     * Constructor for the GameList.
     * Initializes an empty set of games.
     */
    public GameList() {
        this.games = new HashSet<>();
    }

    /**
     * Gets the contents of the list as a list of game names in ascending order ignoring case.
     * 
     * @return The list of game names in ascending order ignoring case
     */
    @Override
    public List<String> getGameNames() {
        return games.stream()
                .map(BoardGame::getName)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(Collectors.toList());
    }

    /**
     * Removes all games in the list.
     */
    @Override
    public void clear() {
        games.clear();
    }

    /**
     * Returns the number of games in the list.
     * 
     * @return The number of games in the list
     */
    @Override
    public int count() {
        return games.size();
    }

    /**
     * Saves the list of games to a file.
     * Each game name is written on a new line.
     * 
     * @param filename The name of the file to save the list to
     */
    @Override
    public void saveGame(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            List<String> gameNames = getGameNames();
            for (String name : gameNames) {
                writer.write(name);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving game List" + e.getMessage());
        }
    }

    /**
     * Adds a game or games to the list based on the provided string.
     * 
     * @param str The string to parse and add games to the list
     * @param filtered The filtered list to use as a basis for adding
     * @throws IllegalArgumentException if the string is not valid
     */
    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {
        if (str == null || str.trim().isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be empty!");
        }

        str = str.trim().toLowerCase();
        if (str.equals(ADD_ALL)) {
            filtered.forEach(games::add);
            return;
        }

        // Collect the filtered results into a list and sort them by game name to ensure consistent order
        List<BoardGame> filteredList = filtered.collect(Collectors.toList());
        filteredList.sort(Comparator.comparing(BoardGame::getName, String.CASE_INSENSITIVE_ORDER));

        // if input is a digit
        if (str.matches("\\d+")) {
            int index = Integer.parseInt(str) - 1;
            if (index < 0 || index >= filteredList.size()) {
                throw new IllegalArgumentException("Index out of bounds! " + str);
            }
            games.add(filteredList.get(index));
        } else if (str.matches("\\d+-\\d+")) { // 如果输入的是范围
            String[] parts = str.split("-");
            int start = Integer.parseInt(parts[0]) - 1;
            int end = Integer.parseInt(parts[1]) - 1;

            if (start < 0 || start > end || end >= filteredList.size()) {
                throw new IllegalArgumentException("Index range! " + str);
            }

            for (int i = start; i <= end; i++) {
                games.add(filteredList.get(i));
            }
        } else { // if input is a game name
            boolean found = false;
            for (BoardGame game : filteredList) {
                if (game.getName().toLowerCase().equals(str)) {
                    games.add(game);
                    found = true;
                    break;
                }
            }

            if (!found) {
                throw new IllegalArgumentException("Game not found! " + str);
            }
        }
    }

    /**
     * Removes a game or games from the list based on the provided string.
     * 
     * @param str The string to parse and remove games from the list
     * @throws IllegalArgumentException if the string is not valid
     */
    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        if (str == null || str.trim().isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be empty!");
        }

        str = str.trim().toLowerCase();
        if (str.equals(ADD_ALL)) {
            games.clear();
            return;
        }

        List<BoardGame> gameList = new ArrayList<>(games);
        gameList.sort(Comparator.comparing(BoardGame::getName, String.CASE_INSENSITIVE_ORDER));

        if (str.matches("\\d+")) {
            int index = Integer.parseInt(str) - 1;
            if (index < 0 || index >= gameList.size()) {
                throw new IllegalArgumentException("Index out of bounds!" + str);
            }
            games.remove(gameList.get(index));
        } else if (str.matches("\\d+-\\d+")) {
            String[] parts = str.split("-");
            int start = Integer.parseInt(parts[0]) - 1;
            int end = Integer.parseInt(parts[1]) - 1;

            if (start < 0 || start > end || end >= gameList.size()) {
                throw new IllegalArgumentException("Index range!" + str);
            }

            for (int i = start; i <= end; i++) {
                gameList.remove(gameList.get(i));
            }
        } else {
            boolean found = false;
            for (BoardGame game : new ArrayList<>(gameList)) {
                if (game.getName().toLowerCase().equals(str)) {
                    games.remove(game);
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new IllegalArgumentException("Game not found!" + str);
            }
        }
    }
}
