package student;

import student.filter.Filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Implementation of the IPlanner interface that provides filtering and sorting
 * functionality for BoardGame objects.
 */
public class Planner implements IPlanner {
    /** set for all games */
    private final Set<BoardGame> allGames;
    /** set for games after filter */
    private Set<BoardGame> filteredGames;

    /**
     * Constructor for the Planner class.
     * 
     * @param games The set of all available board games
     */
    public Planner(Set<BoardGame> games) {
        this.allGames = new HashSet<BoardGame>(games);
        this.filteredGames = new HashSet<BoardGame>(games);
    }

    /**
     * Filters the board games by the passed in text filter.
     * Results are sorted by name in ascending order.
     * 
     * @param filter The filter to apply to the board games
     * @return A stream of board games that match the filter
     */
    @Override
    public Stream<BoardGame> filter(String filter) {
        return filter(filter, GameData.NAME, true);
    }

    /**
     * Filters the board games by the passed in text filter.
     * Results are sorted by the specified column in ascending order.
     * 
     * @param filter The filter to apply to the board games
     * @param sortOn The column to sort the results on
     * @return A stream of board games that match the filter
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        return filter(filter, sortOn, true);
    }

    /**
     * Filters the board games by the passed in text filter.
     * Results are sorted by the specified column in the specified direction.
     * 
     * @param filter The filter to apply to the board games
     * @param sortOn The column to sort the results on
     * @param ascending Whether to sort in ascending order
     * @return A stream of board games that match the filter
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        if (filter == null || filter.trim().isEmpty()) {
            return filteredGames.stream()
                    .sorted(GameComparator.createComparator(sortOn, ascending));
        }

        // don't remove space inside string
        filter = filter.trim().toLowerCase();

        List<Predicate<BoardGame>> predicates = parseFilters(filter);

        Set<BoardGame> workingSet = new HashSet<>(filteredGames);
        for (Predicate<BoardGame> predicate : predicates) {
            workingSet = workingSet.stream()
                    .filter(predicate)
                    .collect(HashSet::new, HashSet::add, HashSet::addAll);
        }
        filteredGames = workingSet;

        return workingSet.stream()
                .sorted(GameComparator.createComparator(sortOn, ascending));
    }

    /**
     * Resets the filtered games to include all games.
     */
    @Override
    public void reset() {
        filteredGames = new HashSet<>(allGames);
    }

    /**
     * Parses a filter string into a list of predicates.
     * 
     * @param filter The filter string to parse
     * @return A list of predicates for filtering BoardGame objects
     */
    private List<Predicate<BoardGame>> parseFilters(String filter) {
        List<Predicate<BoardGame>> predicates = new ArrayList<>();

        // Split multiple filters by comma
        String[] filters = filter.split(",");

        for (String singleFilter : filters) {
            if (!singleFilter.isEmpty()) {
                Predicate<BoardGame> predicate = parseSingleFilter(singleFilter);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            }
        }

        return predicates;
    }

    /**
     * Parses a single filter string into a predicate.
     * 
     * @param filter The single filter string to parse
     * @return A predicate for filtering BoardGame objects, or null if the filter is invalid
     */
    private Predicate<BoardGame> parseSingleFilter(String filter) {
        Operations operator = Operations.getOperatorFromStr(filter);
        if (operator == null) {
            return null;
        }

        // use trim() for each part，ensure no other extra space
        String[] parts = filter.split(operator.getOperator());
        if (parts.length != 2) {
            return null;
        }
        String columnStr = parts[0].trim();
        String valueStr = parts[1].trim();

        GameData column;
        try {
            column = GameData.fromString(columnStr);
        } catch (IllegalArgumentException e) {
            return null;
        }

        return Filter.createFilter(column, operator, valueStr).createPredicate();
    }
}
