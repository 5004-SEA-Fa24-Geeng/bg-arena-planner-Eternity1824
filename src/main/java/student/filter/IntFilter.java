package student.filter;

import student.BoardGame;
import student.GameData;
import student.Operations;

import java.util.function.Predicate;

/**
 * Filter implementation for integer-based columns such as minimum players,
 * maximum players, play time, rank, and year published.
 */
public class IntFilter extends Filter {
    /**
     * Constructor for IntFilter.
     * 
     * @param column The column to filter on
     * @param operation The operation to apply
     * @param value The value to compare against
     */
    public IntFilter(GameData column, Operations operation, String value) {
        super(column, operation, value);
    }

    /**
     * Creates a predicate that filters BoardGame objects based on integer comparison.
     * If the value cannot be parsed as an integer, the predicate will return true for all games.
     * 
     * @return A predicate for filtering BoardGame objects
     */
    @Override
    public Predicate<BoardGame> createPredicate() {
        try {
            int intValue = Integer.parseInt(value);
            return boardGame -> {
                int gameValue = 0;

                switch (column) {
                    case MIN_PLAYERS:
                        gameValue = boardGame.getMinPlayers();
                        break;
                    case MAX_PLAYERS:
                        gameValue = boardGame.getMaxPlayers();
                        break;
                    case MIN_TIME:
                        gameValue = boardGame.getMinPlayTime();
                        break;
                    case MAX_TIME:
                        gameValue = boardGame.getMaxPlayTime();
                        break;
                    case RANK:
                        gameValue = boardGame.getRank();
                        break;
                    case YEAR:
                        gameValue = boardGame.getYearPublished();
                        break;
                    default:
                        return true;
                }

                switch (operation) {
                    case EQUALS:
                        return gameValue == intValue;
                    case NOT_EQUALS:
                        return gameValue != intValue;
                    case GREATER_THAN:
                        return gameValue > intValue;
                    case LESS_THAN:
                        return gameValue < intValue;
                    case GREATER_THAN_EQUALS:
                        return gameValue >= intValue;
                    case LESS_THAN_EQUALS:
                        return gameValue <= intValue;
                    default:
                        return true;
                }
            };
        } catch (NumberFormatException e) {
            return game -> true;
        }
    }
}
