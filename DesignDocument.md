# Board Game Arena Planner Design Document


This document is meant to provide a tool for you to demonstrate the design process. You need to work on this before you code, and after have a finished product. That way you can compare the changes, and changes in design are normal as you work through a project. It is contrary to popular belief, but we are not perfect our first attempt. We need to iterate on our designs to make them better. This document is a tool to help you do that.


## (INITIAL DESIGN): Class Diagram 

Place your class diagrams below. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. If it is not, you will need to fix it. As a reminder, here is a link to tools that can help you create a class diagram: [Class Resources: Class Design Tools](https://github.com/CS5004-khoury-lionelle/Resources?tab=readme-ov-file#uml-design-tools)

### Provided Code

Provide a class diagram for the provided code as you read through it.  For the classes you are adding, you will create them as a separate diagram, so for now, you can just point towards the interfaces for the provided code diagram.


```mermaid
classDiagram
    class BGArenaPlanner {
        <<application>>
        + main(String[] args)
    %% Additional methods, fields, etc.
    }

    class BoardGame {
        - String name
        - int minPlayers
        - int maxPlayers
        - int playingTime
        - double difficulty
        - double rating
    %% etc.
        + getName() : String
        + getMinPlayers() : int
        + getMaxPlayers() : int
        + getPlayingTime() : int
        + getDifficulty() : double
        + getRating() : double
        + toString() : String
    }

    class ConsoleApp {
        + start()
        - handleUserInput()
    %% more methods if you want
    }

    class GameData {
        - ...
    }

    class GameList {
        + GameList()
        + addGame(BoardGame game) : void
        + removeGame(String gameName) : boolean
        + getGames() : List~BoardGame~
        + size() : int
    }

    class GamesLoader {
        + GamesLoader()
        + loadGames(String filePath) : List~BoardGame~
    }

    class Operations {
        <<enumeration>>
    %% e.g. EQUALS, GREATER_THAN, LESS_THAN, etc.
    }

    class Planner {
        + Planner()
        + filter(String) : List~BoardGame~
        + sort(String) : List~BoardGame~
    }

    class IGameList {
        <<interface>>
        + addGame(BoardGame game) : void
        + removeGame(String gameName) : boolean
        + getGames() : List~BoardGame~
    }

    class IPlanner {
        <<interface>>
        + filter(String criteria) : List~BoardGame~
        + sort(String sortSpec) : List~BoardGame~
    }

%% RELATIONSHIPS
    GameList ..|> IGameList
    Planner ..|> IPlanner
    BGArenaPlanner --> Planner : "uses"
    BGArenaPlanner --> GameList : "uses"
    BGArenaPlanner --> ConsoleApp : "uses"
    ConsoleApp --> Planner : "uses"
    ConsoleApp --> GameList : "uses"
    Planner --> BoardGame : "filters/sorts list of"
    GameList --> BoardGame : "stores list of"
    GamesLoader --> BoardGame : "creates"
    GamesLoader --> GameList : "populates"
    Planner --> Operations : "uses"
```

### Your Plans/Design

Create a class diagram for the classes you plan to create. This is your initial design, and it is okay if it changes. Your starting points are the interfaces. 
```mermaid
classDiagram
    class Planner {
      + filter(String) : List~BoardGame~
      + sort(String) : List~BoardGame~
      - Filter filterHelper
      - SortHelper sortHelper
      ...
    }

    class Filter {
      + applyFilters(List~BoardGame~, String filterSpec) : List~BoardGame~
      - parseFilterSpec(String filterSpec) : List~FilterCriteria~
      ...
    }

    class SortHelper {
      + sortGames(List~BoardGame~, String sortSpec) : List~BoardGame~
      - parseSortSpec(String sortSpec) : List~SortCriteria~
      ...
    }

    Planner --> Filter : "has-a"
    Planner --> SortHelper : "has-a"
```




## (INITIAL DESIGN): Tests to Write - Brainstorm

Write a test (in english) that you can picture for the class diagram you have created. This is the brainstorming stage in the TDD process. 

> [!TIP]
> As a reminder, this is the TDD process we are following:
> 1. Figure out a number of tests by brainstorming (this step)
> 2. Write **one** test
> 3. Write **just enough** code to make that test pass
> 4. Refactor/update  as you go along
> 5. Repeat steps 2-4 until you have all the tests passing/fully built program

You should feel free to number your brainstorm. 

1.	GameList Tests  
1.1. Add a Game to an empty GameList and confirm it appears in getGames().  
1.2. Remove a Game that exists; confirm true is returned and the list is updated.  
1.3. Remove a Game that does not exist; confirm false is returned and the list is unchanged.  
1.4. Adding duplicate games (if duplicates are allowed or disallowed) to see how it behaves.  
2.	Planner Filter Tests  
2.1. Filter by minimum players with >= operator. Check only games that meet or exceed that min.  
2.2. Filter by maximum time with <= operator.  
2.3. Filter by name containing some substring.  
2.4. Chain multiple filters at once (e.g., max time <= 60 and difficulty >= 3.0).  
3.	Planner Sort Tests  
3.1. Sort by name ascending.  
3.2. Sort by difficulty descending.  
3.3. Sort by rating, confirm stable ordering for ties (if relevant).  
3.4. Sort with multiple criteria (e.g., name ascending, then rating descending).  
4.	Edge Cases  
4.1. Attempting to filter or sort an empty list.  
4.2. Very large or invalid input strings for filtering/sorting specs.  
4.3. Non‐numeric input where numeric filter is expected.  





## (FINAL DESIGN): Class Diagram

Go through your completed code, and update your class diagram to reflect the final design. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. It is normal that the two diagrams don't match! Rarely (though possible) is your initial design perfect. 

For the final design, you just need to do a single diagram that includes both the original classes and the classes you added. 
```mermaid
classDiagram
    class BGArenaPlanner {
        <<application>>
        + main(String[] args)
    }

    class BoardGame {
        - String name
        - int minPlayers
        - int maxPlayers
        - int minTime
        - int maxTime
        - double difficulty
        - double rating
        - int yearPublished
        + getName() : String
        + getMinPlayers() : int
        + getMaxPlayers() : int
        + getMinTime() : int
        + getMaxTime() : int
        + getDifficulty() : double
        + getRating() : double
        + getYearPublished() : int
        + toString() : String
    }

    class ConsoleApp {
        + start()
        - handleUserInput()
    }

    class GameData {
        - ...
    }

    class GameList {
        + GameList()
        + addToList(String str, Stream~BoardGame~ filtered) : void
        + removeFromList(String str) : void
        + clear() : void
        + getGameNames() : List~String~
        + count() : int
        + saveGame(String filename) : void
    }

    class GamesLoader {
        + GamesLoader()
        + loadGames(String filePath) : List~BoardGame~
    }

    class Operations {
        <<enumeration>>
        // EQUALS, GREATER_THAN, LESS_THAN, etc.
    }

    class Planner {
        + Planner(Set~BoardGame~ games)
        + filter(String) : Stream~BoardGame~
        + filter(String, GameData) : Stream~BoardGame~
        + filter(String, GameData, boolean) : Stream~BoardGame~
        + reset() : void
    }

    class GameComparator {
        <<utility>>
        + createComparator(GameData column, boolean ascending) : Comparator~BoardGame~
    }

    class IGameList {
        <<interface>>
        + addToList(String str, Stream~BoardGame~ filtered) : void
        + removeFromList(String str) : void
        + clear() : void
        + getGameNames() : List~String~
        + count() : int
        + saveGame(String filename) : void
    }

    class IPlanner {
        <<interface>>
        + filter(String criteria) : Stream~BoardGame~
        + filter(String criteria, GameData sortOn) : Stream~BoardGame~
        + filter(String criteria, GameData sortOn, boolean ascending) : Stream~BoardGame~
        + reset() : void
    }

    class Filter {
        <<abstract>>
        - GameData column
        - Operations operation
        - String value
        + Filter(GameData column, Operations operation, String value)
        + createPredicate() : Predicate~BoardGame~
        + getColumn() : GameData
        + getOperation() : Operations
        + getValue() : String
        + createFilter(GameData column, Operations operation, String value) : Filter
    }

    class IntFilter {
        + IntFilter(GameData column, Operations operation, String value)
        + createPredicate() : Predicate~BoardGame~
    }

    class DoubleFilter {
        + DoubleFilter(GameData column, Operations operation, String value)
        + createPredicate() : Predicate~BoardGame~
    }

    class StringFilter {
        + StringFilter(GameData column, Operations operation, String value)
        + createPredicate() : Predicate~BoardGame~
    }

%% RELATIONSHIPS
    GameList ..|> IGameList
    Planner ..|> IPlanner
    Filter <|-- IntFilter
    Filter <|-- DoubleFilter
    Filter <|-- StringFilter
    BGArenaPlanner --> Planner : "uses"
    BGArenaPlanner --> GameList : "uses"
    BGArenaPlanner --> ConsoleApp : "uses"
    ConsoleApp --> Planner : "uses"
    ConsoleApp --> GameList : "uses"
    Planner --> BoardGame : "filters/sorts list of"
    GameList --> BoardGame : "stores list of"
    GamesLoader --> BoardGame : "creates"
    GamesLoader --> GameList : "populates"
    Planner --> Operations : "uses"
    Planner --> GameComparator : "uses"
```
> [!WARNING]
> If you resubmit your assignment for manual grading, this is a section that often needs updating. You should double check with every resubmit to make sure it is up to date.





## (FINAL DESIGN): Reflection/Retrospective

> [!IMPORTANT]
> The value of reflective writing has been highly researched and documented within computer science, from learning to information to showing higher salaries in the workplace. For this next part, we encourage you to take time, and truly focus on your retrospective.

Take time to reflect on how your design has changed. Write in *prose* (i.e. do not bullet point your answers - it matters in how our brain processes the information). Make sure to include what were some major changes, and why you made them. What did you learn from this process? What would you do differently next time? What was the most challenging part of this process? For most students, it will be a paragraph or two.  
  
During the design process, I improved encapsulation by making fields private and using getter methods, ensuring better structure. I also refined class relationships and interface implementations, which became clearer when generating the UML diagram. The biggest challenge was debugging filtering logic and handling test failures. This process taught me the importance of refining designs iteratively and catching issues early through proper modeling.
