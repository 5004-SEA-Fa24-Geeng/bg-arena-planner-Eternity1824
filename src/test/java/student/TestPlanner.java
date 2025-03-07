package student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * JUnit test for the Planner class.
 * 
 * Tests various filtering and sorting capabilities of the Planner.
 */
public class TestPlanner {
    static Set<BoardGame> games;

    @BeforeAll
    public static void setup() {
        games = new HashSet<>();
        games.add(new BoardGame("17 days", 6, 1, 8, 70, 70, 9.0, 600, 9.0, 2005));
        games.add(new BoardGame("Chess", 7, 2, 2, 10, 20, 10.0, 700, 10.0, 2006));
        games.add(new BoardGame("Go", 1, 2, 5, 30, 30, 8.0, 100, 7.5, 2000));
        games.add(new BoardGame("Go Fish", 2, 2, 10, 20, 120, 3.0, 200, 6.5, 2001));
        games.add(new BoardGame("golang", 4, 2, 7, 50, 55, 7.0, 400, 9.5, 2003));
        games.add(new BoardGame("GoRami", 3, 6, 6, 40, 42, 5.0, 300, 8.5, 2002));
        games.add(new BoardGame("Monopoly", 8, 6, 10, 20, 1000, 1.0, 800, 5.0, 2007));
        games.add(new BoardGame("Tucano", 5, 10, 20, 60, 90, 6.0, 500, 8.0, 2004));
    }

    // 2.1 Filter by name equals
    @Test
    public void testFilterName() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("name == Go").toList();
        assertEquals(1, filtered.size());
        assertEquals("Go", filtered.get(0).getName());
    }
    
    // 2.2 Filter by name contains
    @Test
    public void testFilterNameContains() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("name ~= Go").toList();
        assertEquals(4, filtered.size());
        // Should include Go, Go Fish, golang, GoRami
        List<String> names = filtered.stream().map(BoardGame::getName).collect(Collectors.toList());
        assertTrue(names.contains("Go"));
        assertTrue(names.contains("Go Fish"));
        assertTrue(names.contains("golang"));
        assertTrue(names.contains("GoRami"));
    }
    
    // 2.3 Filter by minimum players with >= operator
    @Test
    public void testFilterMinPlayersGreaterThanEquals() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("minPlayers >= 6").toList();
        assertEquals(3, filtered.size());
        // Should include 17 days, GoRami, Monopoly
        List<String> names = filtered.stream().map(BoardGame::getName).collect(Collectors.toList());
        assertTrue(names.contains("17 days"));
        assertTrue(names.contains("GoRami"));
        assertTrue(names.contains("Monopoly"));
    }
    
    // 2.4 Filter by maximum time with <= operator
    @Test
    public void testFilterMaxTimeLessThanEquals() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("maxTime <= 60").toList();
        assertEquals(5, filtered.size());
        // Should include Chess, Go, Go Fish, golang, GoRami
        List<String> names = filtered.stream().map(BoardGame::getName).collect(Collectors.toList());
        assertTrue(names.contains("Chess"));
        assertTrue(names.contains("Go"));
        assertTrue(names.contains("Go Fish"));
        assertTrue(names.contains("golang"));
        assertTrue(names.contains("GoRami"));
    }
    
    // 2.5 Chain multiple filters
    @Test
    public void testMultipleFilters() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("minPlayers >= 2, maxTime <= 60").toList();
        assertEquals(4, filtered.size());
        // Should include Chess, Go, golang, GoRami
        List<String> names = filtered.stream().map(BoardGame::getName).collect(Collectors.toList());
        assertTrue(names.contains("Chess"));
        assertTrue(names.contains("Go"));
        assertTrue(names.contains("golang"));
        assertTrue(names.contains("GoRami"));
    }
    
    // 3.1 Sort by name ascending (default)
    @Test
    public void testSortByNameAscending() {
        IPlanner planner = new Planner(games);
        List<BoardGame> sorted = planner.filter("").toList();
        assertEquals(8, sorted.size());
        assertEquals("17 days", sorted.get(0).getName());
        assertEquals("Chess", sorted.get(1).getName());
        assertEquals("Go", sorted.get(2).getName());
        assertEquals("Go Fish", sorted.get(3).getName());
        assertEquals("golang", sorted.get(4).getName());
        assertEquals("GoRami", sorted.get(5).getName());
        assertEquals("Monopoly", sorted.get(6).getName());
        assertEquals("Tucano", sorted.get(7).getName());
    }
    
    // 3.2 Sort by difficulty descending
    @Test
    public void testSortByDifficultyDescending() {
        IPlanner planner = new Planner(games);
        List<BoardGame> sorted = planner.filter("", GameData.DIFFICULTY, false).toList();
        assertEquals(8, sorted.size());
        assertEquals(10.0, sorted.get(0).getDifficulty()); // Chess
        assertEquals(9.0, sorted.get(1).getDifficulty());  // 17 days
        assertEquals(8.0, sorted.get(2).getDifficulty());  // GoRami
        assertEquals(7.0, sorted.get(3).getDifficulty());  // Go and Tucano (tie)
        assertEquals(6.0, sorted.get(5).getDifficulty());  // golang
        assertEquals(5.0, sorted.get(6).getDifficulty());  // Tucano
        assertEquals(3.0, sorted.get(7).getDifficulty());  // Go Fish
        assertEquals(1.0, sorted.get(8).getDifficulty());  // Monopoly
    }
    
    // 3.3 Sort by rating ascending
    @Test
    public void testSortByRatingAscending() {
        IPlanner planner = new Planner(games);
        List<BoardGame> sorted = planner.filter("", GameData.RATING, true).toList();
        assertEquals(8, sorted.size());
        assertEquals(5.0, sorted.get(0).getRating());  // Monopoly
        assertEquals(6.5, sorted.get(1).getRating());  // Go Fish
        assertEquals(7.5, sorted.get(2).getRating());  // Go
        assertEquals(8.0, sorted.get(3).getRating());  // Tucano
        assertEquals(8.5, sorted.get(4).getRating());  // GoRami
        assertEquals(9.0, sorted.get(5).getRating());  // 17 days
        assertEquals(9.5, sorted.get(6).getRating());  // golang
        assertEquals(10.0, sorted.get(7).getRating()); // Chess
    }
    
    // 4.1 Filter with invalid operator
    @Test
    public void testInvalidOperator() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("minPlayers @ 5").toList();
        // Should ignore invalid filter and return all games
        assertEquals(8, filtered.size());
    }
    
    // 4.2 Filter with non-numeric value for numeric field
    @Test
    public void testNonNumericValueForNumericField() {
        IPlanner planner = new Planner(games);
        List<BoardGame> filtered = planner.filter("minPlayers > abc").toList();
        // Should ignore invalid filter and return all games
        assertEquals(8, filtered.size());
    }
    
    // 4.3 Reset after filtering
    @Test
    public void testResetAfterFiltering() {
        IPlanner planner = new Planner(games);
        planner.filter("minPlayers >= 6");
        planner.reset();
        List<BoardGame> filtered = planner.filter("").toList();
        // Should return all games after reset
        assertEquals(8, filtered.size());
    }
    
    // 4.4 Progressive filtering
    @Test
    public void testProgressiveFiltering() {
        IPlanner planner = new Planner(games);
        planner.filter("minPlayers >= 2");
        List<BoardGame> filtered = planner.filter("maxTime <= 60").toList();
        assertEquals(4, filtered.size());
        // Should include Chess, Go, golang, GoRami
        List<String> names = filtered.stream().map(BoardGame::getName).collect(Collectors.toList());
        assertTrue(names.contains("Chess"));
        assertTrue(names.contains("Go"));
        assertTrue(names.contains("golang"));
        assertTrue(names.contains("GoRami"));
    }
}