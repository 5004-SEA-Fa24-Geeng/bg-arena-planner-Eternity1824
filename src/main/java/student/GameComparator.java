package student;

import java.util.Comparator;

/**
 * Factory class for creating comparators to sort BoardGame objects.
 * Provides comparators for different columns and sorting directions.
 */
public final class GameComparator {

    private GameComparator() {
        throw new AssertionError("Cannot instantiate GameComparator");
    }
    /**
     * Creates a comparator for BoardGame objects based on the specified column and sort direction.
     * 
     * @param column The column to sort on
     * @param ascending Whether to sort in ascending order (true) or descending order (false)
     * @return A comparator for BoardGame objects
     */
    public static Comparator<BoardGame> createComparator(GameData column, boolean ascending) {
        Comparator<BoardGame> comparator;
        
        switch (column) {
            case NAME:
                comparator = Comparator.comparing(BoardGame::getName, String.CASE_INSENSITIVE_ORDER);
                break;
            case RATING:
                comparator = Comparator.comparing(BoardGame::getRating);
                break;
            case DIFFICULTY:
                comparator = Comparator.comparing(BoardGame::getDifficulty);
                break;
            case RANK:
                comparator = Comparator.comparing(BoardGame::getRank);
                break;
            case MIN_PLAYERS:
                comparator = Comparator.comparing(BoardGame::getMinPlayers);
                break;
            case MAX_PLAYERS:
                comparator = Comparator.comparing(BoardGame::getMaxPlayers);
                break;
            case MIN_TIME:
                comparator = Comparator.comparing(BoardGame::getMinPlayTime);
                break;
            case MAX_TIME:
                comparator = Comparator.comparing(BoardGame::getMaxPlayTime);
                break;
            case YEAR:
                comparator = Comparator.comparing(BoardGame::getYearPublished);
                break;
            default:
                // Default to sorting by name
                comparator = Comparator.comparing(BoardGame::getName, String.CASE_INSENSITIVE_ORDER);
        }
        
        // Reverse the comparator if descending order is requested
        return ascending ? comparator : comparator.reversed();
    }
}
