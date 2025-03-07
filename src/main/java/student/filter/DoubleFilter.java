package student.filter;

import student.BoardGame;
import student.GameData;
import student.Operations;

import java.util.function.Predicate;

/**
 * Filter implementation for double-based columns such as rating and difficulty.
 * Handles floating-point comparison with appropriate precision.
 */
public class DoubleFilter extends Filter{
    /**
     * Constructor for DoubleFilter.
     * 
     * @param column The column to filter on
     * @param operation The operation to apply
     * @param value The value to compare against
     */
    public DoubleFilter(GameData column, Operations operation, String value) {
        super(column, operation, value);
    }

    /**
     * Creates a predicate that filters BoardGame objects based on double comparison.
     * Uses a small epsilon value (0.0001) for floating-point equality comparison.
     * If the value cannot be parsed as a double, the predicate will return true for all games.
     * 
     * @return A predicate for filtering BoardGame objects
     */
    @Override
    public Predicate<BoardGame> createPredicate() {
        try {
            double doubleValue = Double.parseDouble(value);

            return game -> {
                double gameValue = 0.0;

                switch(column) {
                    case RATING:
                        gameValue = game.getRating();
                        break;
                    case DIFFICULTY:
                        gameValue = game.getDifficulty();
                        break;
                    default:
                        return true;
                }

                switch (operation) {
                    case EQUALS:
                        return Math.abs(gameValue - doubleValue) < 0.0001;
                    case NOT_EQUALS:
                        return Math.abs(gameValue - doubleValue) >= 0.0001;
                    case GREATER_THAN:
                        return gameValue > doubleValue;
                    case LESS_THAN:
                        return gameValue < doubleValue;
                    case GREATER_THAN_EQUALS:
                        return gameValue >= doubleValue;
                    case LESS_THAN_EQUALS:
                        return gameValue <= doubleValue;
                    default:
                        return true;
                }
            };

        } catch (NumberFormatException e) {
            return game -> true;
        }
    }
}
