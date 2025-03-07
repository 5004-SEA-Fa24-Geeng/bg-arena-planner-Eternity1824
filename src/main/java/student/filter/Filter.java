package student.filter;

import student.BoardGame;
import student.GameData;
import student.Operations;

import java.util.function.Predicate;

/**
 * Abstract filter class used to create different types of filters for BoardGame objects.
 * This class serves as a base for specific filter implementations.
 */
public abstract class Filter {
    // The column to filter on
    protected final GameData column;
    // The operation to apply
    protected final Operations operation;
    // The value to compare against
    protected final String value;

    /**
     * Constructor for the Filter class.
     *
     * @param column    The column to filter on
     * @param operation The operation to apply
     * @param value     The value to compare against
     */
    public Filter(GameData column, Operations operation, String value) {
        this.column = column;
        this.operation = operation;
        this.value = value;
    }

    /**
     * Factory method to create the appropriate filter based on column type.
     *
     * @param column    The column to filter on
     * @param operation The operation to apply
     * @param value     The value to compare against
     * @return A specific filter implementation based on the column type
     * @throws IllegalArgumentException if the column is not supported
     */
    public static Filter createFilter(GameData column, Operations operation, String value) {
        switch (column) {
            case NAME:
                return new StringFilter(column, operation, value);
            case RATING:
            case DIFFICULTY:
                return new DoubleFilter(column, operation, value);
            case RANK:
            case MIN_PLAYERS:
            case MAX_PLAYERS:
            case MIN_TIME:
            case MAX_TIME:
            case YEAR:
                return new IntFilter(column, operation, value);
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Creates a predicate that can be used to filter BoardGame objects.
     *
     * @return A predicate for filtering BoardGame objects
     */
    public abstract Predicate<BoardGame> createPredicate();
}
