# Report

Submitted report to be manually graded. We encourage you to review the report as you read through the provided
code as it is meant to help you understand some of the concepts. 

## Technical Questions

1. What is the difference between == and .equals in java? Provide a code example of each, where they would return different results for an object. Include the code snippet using the hash marks (```) to create a code block.  
   `==` checks if two references point to the same memory location  
   `.equals()` is a method that checks if two objects are logically equivalent. By default, unless overridden, it behaves like ==, but many classes (such as String) override it to compare actual content.
   ```java
   // your code here
   public class EqualsVsDoubleEquals {
    public static void main(String[] args) {
        String str1 = new String("Hello");
        String str2 = new String("Hello");

        // Using ==
        System.out.println(str1 == str2);  // false (Different objects in memory)

        // Using .equals()
        System.out.println(str1.equals(str2));  // true (Same content)
    }
   }
   ```




2. Logical sorting can be difficult when talking about case. For example, should "apple" come before "Banana" or after? How would you sort a list of strings in a case-insensitive manner? 

   In Java, you can sort a list of strings in a case-insensitive manner using Comparator.comparing(String::toLowerCase). This ensures that the sorting is done based on the lowercase version of each string while maintaining the original case in the output.




3. In our version of the solution, we had the following code (snippet)
    ```java
    public static Operations getOperatorFromStr(String str) {
        if (str.contains(">=")) {
            return Operations.GREATER_THAN_EQUALS;
        } else if (str.contains("<=")) {
            return Operations.LESS_THAN_EQUALS;
        } else if (str.contains(">")) {
            return Operations.GREATER_THAN;
        } else if (str.contains("<")) {
            return Operations.LESS_THAN;
        } else if (str.contains("=="))...
    ```
    Why would the order in which we checked matter (if it does matter)? Provide examples either way proving your point.  
   The order of checks in this method does matter because some operators (like >= and <=) contain characters that are also used in other operators (> and <). If the checks for > or < are placed before >= or <=, then str.contains(">") will return true even for ">=", causing an incorrect match before ">=" is checked.
   ```java
   public static Operations getOperatorFromStr(String str) {
    if (str.contains(">")) {
        return Operations.GREATER_THAN;  // Incorrectly matches ">=" as ">"
    } else if (str.contains(">=")) {
        return Operations.GREATER_THAN_EQUALS;
    }
    return null;
   }

   public static void main(String[] args) {
   System.out.println(getOperatorFromStr(">="));  // Returns GREATER_THAN (wrong!)
   }
   ```


4. What is the difference between a List and a Set in Java? When would you use one over the other? 
   1.	Duplicates:
         •	List allows duplicate elements.
         •	Set does not allow duplicate elements.
   2.	Order:
         •	List maintains the insertion order.
         •	Set does not guarantee order (except for ordered implementations like LinkedHashSet or sorted implementations like TreeSet).
   3.	Performance:
         •	List is faster for indexed access (ArrayList provides O(1) access time).
         •	Set (especially HashSet) is faster for lookups and ensuring uniqueness (O(1) for HashSet, O(log n) for TreeSet).

   •Use a List when:  
         •	You need to maintain the order of elements.  
         •	Duplicates are allowed.  
         •	You frequently need to access elements by index.  
   •Use a Set when:  
         •	You need to ensure all elements are unique.  
         •	Fast lookups are required.  
         •	Order is not important (or you use a LinkedHashSet/TreeSet for ordering).  




5. In [GamesLoader.java](src/main/java/student/GamesLoader.java), we use a Map to help figure out the columns. What is a map? Why would we use a Map here?  
   A Map in Java is a collection that stores key-value pairs, allowing efficient lookups, insertions, and deletions. Unlike a List or Set, a Map does not store elements in a linear order but instead associates each key with a corresponding value. Common implementations include:  
   •	HashMap (fast lookups, unordered)  
   •	LinkedHashMap (maintains insertion order)  
   •	TreeMap (sorted order)  
   When
   1.	Fast Lookups – Instead of iterating through an array or list to find a column, we can retrieve it in O(1) time using the key.
   2.	Flexible Column Mapping – If the order of columns in the dataset changes, we can still correctly map names to indices dynamically.
   3.	Improved Code Readability – Instead of manually handling index positions, we can use meaningful names like columnMap.get("rating") to retrieve the rating column index.



6. [GameData.java](src/main/java/student/GameData.java) is actually an `enum` with special properties we added to help with column name mappings. What is an `enum` in Java? Why would we use it for this application?  
   An enum in Java is a special data type that represents a fixed set of constants. Unlike regular classes, enums provide type safety, meaning only predefined values can be used. Enums can also have additional properties and methods, making them more powerful than simple constants.

   In this application, GameData is used to represent different column names in the dataset, helping with mapping and retrieval. Using an enum provides several benefits:
   1.	Type Safety – Prevents invalid column names by restricting values to predefined constants.
   2.	Readable and Maintainable Code – Instead of using plain strings like "rating" or "minPlayers", we use GameData.RATING and GameData.MIN_PLAYERS, making the code easier to understand.
   3.	Built-in Mapping Logic – Enums can store additional properties, such as column names or indices, allowing dynamic column lookups.
   4.	Prevents Hardcoding Strings – Instead of manually checking "rating".equals(column), we can directly use GameData.RATING, avoiding potential typos.





7. Rewrite the following as an if else statement inside the empty code block.
    ```java
    switch (ct) {
                case CMD_QUESTION: // same as help
                case CMD_HELP:
                    processHelp();
                    break;
                case INVALID:
                default:
                    CONSOLE.printf("%s%n", ConsoleText.INVALID);
            }
    ``` 

    ```java
    // your code here, don't forget the class name that is dropped in the switch block..
    if (ct == CMD_QUESTION || ct == CMD_HELP) {
        processHelp();
    } else if (ct == INVALID) {
    CONSOLE.printf("%s%n", ConsoleText.INVALID);
    } else {
    CONSOLE.printf("%s%n", ConsoleText.INVALID);
    }
    ```

## Deeper Thinking

ConsoleApp.java uses a .properties file that contains all the strings
that are displayed to the client. This is a common pattern in software development
as it can help localize the application for different languages. You can see this
talked about here on [Java Localization – Formatting Messages](https://www.baeldung.com/java-localization-messages-formatting).

Take time to look through the console.properties file, and change some of the messages to
another language (probably the welcome message is easier). It could even be a made up language and for this - and only this - alright to use a translator. See how the main program changes, but there are still limitations in 
the current layout. 

Post a copy of the run with the updated languages below this. Use three back ticks (```) to create a code block. 

```text
Bienvenue dans BGArena Planner! Tapez 'help' pour voir la liste des commandes disponibles.
Entrez votre commande:
```

Now, thinking about localization - we have the question of why does it matter? The obvious
one is more about market share, but there may be other reasons.  I encourage
you to take time researching localization and the importance of having programs
flexible enough to be localized to different languages and cultures. Maybe pull up data on the
various spoken languages around the world? What about areas with internet access - do they match? Just some ideas to get you started. Another question you are welcome to talk about - what are the dangers of trying to localize your program and doing it wrong? Can you find any examples of that? Business marketing classes love to point out an example of a car name in Mexico that meant something very different in Spanish than it did in English - however [Snopes has shown that is a false tale](https://www.snopes.com/fact-check/chevrolet-nova-name-spanish/).  As a developer, what are some things you can do to reduce 'hick ups' when expanding your program to other languages?


As a reminder, deeper thinking questions are meant to require some research and to be answered in a paragraph for with references. The goal is to open up some of the discussion topics in CS, so you are better informed going into industry. 