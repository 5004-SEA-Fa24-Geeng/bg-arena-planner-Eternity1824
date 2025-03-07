package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * JUnit test for the GameList class.
 * Tests the functionality of managing a list of board games.
 */
public class TestGameList {
    private GameList gameList;
    private Set<BoardGame> testGames;
    private BoardGame catan;
    private BoardGame pandemic;
    private BoardGame ticket;

    @BeforeEach
    public void setUp() {
        gameList = new GameList();
        testGames = new LinkedHashSet<>();
        
        // 创建一些测试游戏
        catan = new BoardGame("Catan", 1, 3, 4, 60, 120, 2.5, 1, 4.5, 1995);
        pandemic = new BoardGame("Pandemic", 2, 2, 4, 45, 60, 2.0, 2, 4.0, 2008);
        ticket = new BoardGame("Ticket to Ride", 3, 2, 5, 30, 60, 1.8, 3, 4.2, 2004);
        
        testGames.add(catan);
        testGames.add(pandemic);
        testGames.add(ticket);
    }

    // 1.1 添加游戏到空列表
    @Test
    public void testAddGameToEmptyList() {
        Stream<BoardGame> filtered = testGames.stream().filter(g -> g.equals(catan));
        gameList.addToList("catan", filtered);
        
        assertEquals(1, gameList.count());
        assertTrue(gameList.getGameNames().contains("Catan"));
    }
    
    // 1.2 通过索引添加游戏
    @Test
    public void testAddGameByIndex() {
        Stream<BoardGame> filtered = testGames.stream();
        gameList.addToList("1", filtered);
        System.out.println(gameList.getGameNames());
        
        assertEquals(1, gameList.count());
        assertTrue(gameList.getGameNames().contains("Catan"));
    }

    // 1.3 通过范围添加游戏
    @Test
    public void testAddGameByRange() {
        Stream<BoardGame> filtered = testGames.stream();
        gameList.addToList("1-2", filtered);

        assertEquals(2, gameList.count());
        List<String> names = gameList.getGameNames();
        assertTrue(names.contains("Catan"));
        assertTrue(names.contains("Pandemic"));
    }
    
    // 1.4 添加所有游戏
    @Test
    public void testAddAllGames() {
        Stream<BoardGame> filtered = testGames.stream();
        gameList.addToList("all", filtered);
        
        assertEquals(3, gameList.count());
        List<String> names = gameList.getGameNames();
        assertTrue(names.contains("Catan"));
        assertTrue(names.contains("Pandemic"));
        assertTrue(names.contains("Ticket to Ride"));
    }
    
    // 1.5 添加重复游戏
    @Test
    public void testAddDuplicateGame() {
        gameList.addToList("catan", testGames.stream());
        gameList.addToList("catan", testGames.stream());
        
        assertEquals(1, gameList.count());
    }
    
    // 1.6 删除存在的游戏
    @Test
    public void testRemoveExistingGame() {
        // 先添加游戏
        Stream<BoardGame> filtered = testGames.stream();
        gameList.addToList("all", filtered);
        
        // 然后删除一个
        gameList.removeFromList("catan");
        
        assertEquals(2, gameList.count());
        List<String> names = gameList.getGameNames();
        assertFalse(names.contains("Catan"));
        assertTrue(names.contains("Pandemic"));
        assertTrue(names.contains("Ticket to Ride"));
    }
    
    // 1.7 通过索引删除游戏
    @Test
    public void testRemoveGameByIndex() {
        // 先添加游戏
        Stream<BoardGame> filtered = testGames.stream();
        gameList.addToList("all", filtered);
        
        // 然后删除索引1的游戏（应该是按名称排序后的第一个）
        gameList.removeFromList("1");
        
        assertEquals(2, gameList.count());
    }

    
    // 1.9 删除所有游戏
    @Test
    public void testRemoveAllGames() {
        // 先添加游戏
        Stream<BoardGame> filtered = testGames.stream();
        gameList.addToList("all", filtered);
        
        // 然后删除所有
        gameList.removeFromList("all");
        
        assertEquals(0, gameList.count());
        assertTrue(gameList.getGameNames().isEmpty());
    }
    
    // 1.10 测试保存游戏列表
    @Test
    public void testSaveGameList() throws Exception {
        // 先添加游戏
        Stream<BoardGame> filtered = testGames.stream();
        gameList.addToList("all", filtered);
        
        // 保存到临时文件
        String tempFile = "test_gamelist.txt";
        gameList.saveGame(tempFile);
        
        // 验证文件内容
        List<String> lines = Files.readAllLines(Paths.get(tempFile));
        assertEquals(3, lines.size());
        assertTrue(lines.contains("Catan"));
        assertTrue(lines.contains("Pandemic"));
        assertTrue(lines.contains("Ticket to Ride"));
        
        // 清理
        new File(tempFile).delete();
    }
    
    // 1.11 测试边缘情况：添加不存在的游戏
    @Test
    public void testAddNonExistentGame() {
        Stream<BoardGame> filtered = testGames.stream();
        assertThrows(IllegalArgumentException.class, () -> {
            gameList.addToList("nonexistent", filtered);
        });
    }
    
    // 1.12 测试边缘情况：删除不存在的游戏
    @Test
    public void testRemoveNonExistentGame() {
        assertThrows(IllegalArgumentException.class, () -> {
            gameList.removeFromList("nonexistent");
        });
    }
    
    // 1.13 测试边缘情况：索引越界
    @Test
    public void testAddOutOfBoundsIndex() {
        Stream<BoardGame> filtered = testGames.stream();
        assertThrows(IllegalArgumentException.class, () -> {
            gameList.addToList("10", filtered);
        });
    }
    
    // 1.14 测试清空列表
    @Test
    public void testClearList() {
        // 先添加游戏
        Stream<BoardGame> filtered = testGames.stream();
        gameList.addToList("all", filtered);
        
        // 然后清空列表
        gameList.clear();
        
        assertEquals(0, gameList.count());
        assertTrue(gameList.getGameNames().isEmpty());
    }
    
    // 1.15 测试空输入字符串
    @Test
    public void testEmptyInputString() {
        Stream<BoardGame> filtered = testGames.stream();
        assertThrows(IllegalArgumentException.class, () -> {
            gameList.addToList("", filtered);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            gameList.removeFromList("");
        });
    }
} 