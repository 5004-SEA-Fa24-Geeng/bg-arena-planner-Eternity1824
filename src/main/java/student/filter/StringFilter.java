package student.filter;

import student.BoardGame;
import student.GameData;
import student.Operations;

import java.util.function.Predicate;

/**
 * Filter implementation for string-based columns like game names.
 * Supports operations such as equals, not equals, and contains.
 */
public class StringFilter extends Filter {
    /**
     * Constructor for StringFilter.
     * 
     * @param column The column to filter on
     * @param operation The operation to apply
     * @param value The value to compare against
     */
    public StringFilter(GameData column, Operations operation, String value) {
        super(column, operation, value);
    }

    /**
     * Creates a predicate that filters BoardGame objects based on string comparison.
     * All string comparisons are case-insensitive.
     * 
     * @return A predicate for filtering BoardGame objects
     */
    @Override
    public Predicate<BoardGame> createPredicate() {
        return game -> {
            String gameValue = "";
            if (column == GameData.NAME) {
                gameValue = game.getName();
            }

            gameValue = gameValue.toLowerCase();
            String compareValue = value.toLowerCase();

            switch (operation) {
                case EQUALS:
                    return gameValue.equals(compareValue);
                case NOT_EQUALS:
                    return !gameValue.equals(compareValue);
                case CONTAINS:
                    return gameValue.contains(compareValue);
                default:
                    return true;
            }
        };
    }
}
