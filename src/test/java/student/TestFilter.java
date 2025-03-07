package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import student.filter.DoubleFilter;
import student.filter.Filter;
import student.filter.IntFilter;
import student.filter.StringFilter;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for the Filter classes.
 * Tests the functionality of different filter implementations.
 */
public class TestFilter {
    private BoardGame chess;
    private BoardGame monopoly;
    private BoardGame pandemic;

    @BeforeEach
    public void setup() {
        chess = new BoardGame("Chess", 1, 2, 2, 30, 60, 4.0, 100, 8.5, 1500);
        monopoly = new BoardGame("Monopoly", 2, 2, 6, 60, 180, 2.0, 200, 5.5, 1935);
        pandemic = new BoardGame("Pandemic", 3, 2, 4, 45, 60, 3.0, 300, 7.5, 2008);
    }

    // 测试字符串等于过滤器
    @Test
    public void testStringEqualsFilter() {
        Filter filter = Filter.createFilter(GameData.NAME, Operations.EQUALS, "Chess");
        Predicate<BoardGame> predicate = filter.createPredicate();
        
        assertTrue(predicate.test(chess));
        assertFalse(predicate.test(monopoly));
        assertFalse(predicate.test(pandemic));
    }

    // 测试字符串包含过滤器
    @Test
    public void testStringContainsFilter() {
        Filter filter = Filter.createFilter(GameData.NAME, Operations.CONTAINS, "on");
        Predicate<BoardGame> predicate = filter.createPredicate();
        
        assertFalse(predicate.test(chess));
        assertTrue(predicate.test(monopoly));  // Monopoly contains "on"
        assertTrue(predicate.test(pandemic));  // Pandemic contains "on"
    }

    // 测试字符串不等于过滤器
    @Test
    public void testStringNotEqualsFilter() {
        Filter filter = Filter.createFilter(GameData.NAME, Operations.NOT_EQUALS, "Chess");
        Predicate<BoardGame> predicate = filter.createPredicate();
        
        assertFalse(predicate.test(chess));
        assertTrue(predicate.test(monopoly));
        assertTrue(predicate.test(pandemic));
    }

    // 测试整数大于过滤器
    @Test
    public void testIntGreaterThanFilter() {
        Filter filter = Filter.createFilter(GameData.MAX_PLAYERS, Operations.GREATER_THAN, "4");
        Predicate<BoardGame> predicate = filter.createPredicate();
        
        assertFalse(predicate.test(chess));    // max players = 2
        assertTrue(predicate.test(monopoly));  // max players = 6
        assertFalse(predicate.test(pandemic)); // max players = 4
    }

    // 测试整数小于等于过滤器
    @Test
    public void testIntLessThanEqualsFilter() {
        Filter filter = Filter.createFilter(GameData.MIN_TIME, Operations.LESS_THAN_EQUALS, "45");
        Predicate<BoardGame> predicate = filter.createPredicate();
        
        assertTrue(predicate.test(chess));     // min time = 30
        assertFalse(predicate.test(monopoly)); // min time = 60
        assertTrue(predicate.test(pandemic));  // min time = 45
    }

    // 测试整数等于过滤器
    @Test
    public void testIntEqualsFilter() {
        Filter filter = Filter.createFilter(GameData.YEAR, Operations.EQUALS, "2008");
        Predicate<BoardGame> predicate = filter.createPredicate();
        
        assertFalse(predicate.test(chess));    // year = 1500
        assertFalse(predicate.test(monopoly)); // year = 1935
        assertTrue(predicate.test(pandemic));  // year = 2008
    }

    // 测试浮点数大于等于过滤器
    @Test
    public void testDoubleGreaterThanEqualsFilter() {
        Filter filter = Filter.createFilter(GameData.RATING, Operations.GREATER_THAN_EQUALS, "7.5");
        Predicate<BoardGame> predicate = filter.createPredicate();
        
        assertTrue(predicate.test(chess));     // rating = 8.5
        assertFalse(predicate.test(monopoly)); // rating = 5.5
        assertTrue(predicate.test(pandemic));  // rating = 7.5
    }

    // 测试浮点数小于过滤器
    @Test
    public void testDoubleLessThanFilter() {
        Filter filter = Filter.createFilter(GameData.DIFFICULTY, Operations.LESS_THAN, "3.5");
        Predicate<BoardGame> predicate = filter.createPredicate();
        
        assertFalse(predicate.test(chess));    // difficulty = 4.0
        assertTrue(predicate.test(monopoly));  // difficulty = 2.0
        assertTrue(predicate.test(pandemic));  // difficulty = 3.0
    }

    // 测试无效数值处理
    @Test
    public void testInvalidNumericValue() {
        Filter filter = Filter.createFilter(GameData.MIN_PLAYERS, Operations.EQUALS, "not-a-number");
        Predicate<BoardGame> predicate = filter.createPredicate();
        
        // 无效数值应该返回true（不过滤）
        assertTrue(predicate.test(chess));
        assertTrue(predicate.test(monopoly));
        assertTrue(predicate.test(pandemic));
    }

    // 测试过滤器工厂方法
    @Test
    public void testFilterFactory() {
        Filter stringFilter = Filter.createFilter(GameData.NAME, Operations.EQUALS, "value");
        Filter intFilter = Filter.createFilter(GameData.MIN_PLAYERS, Operations.EQUALS, "2");
        Filter doubleFilter = Filter.createFilter(GameData.RATING, Operations.EQUALS, "8.5");
        
        assertTrue(stringFilter instanceof StringFilter);
        assertTrue(intFilter instanceof IntFilter);
        assertTrue(doubleFilter instanceof DoubleFilter);
    }
} 