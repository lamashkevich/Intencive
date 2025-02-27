package ru.aston.lamashkevich_ev.task5;

import org.junit.jupiter.api.Test;
import ru.aston.lamashkevich_ev.task5.model.Category;
import ru.aston.lamashkevich_ev.task5.model.Product;
import ru.aston.lamashkevich_ev.task5.model.User;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StreamAPITest {

    private final StreamAPI streamAPI = new StreamAPI();

    @Test
    void testMapNumbersToDigitSumList() {
        var numbers = List.of(1, 12, -123);
        var result = streamAPI.mapNumbersToDigitSumList(numbers.stream());
        assertEquals(List.of(1, 3, 6), result);
    }

    @Test
    void testCountUsersGroupedByCity() {
        var users = List.of(new User("Minsk"), new User("Grodno"), new User("Minsk"));
        var result = streamAPI.countUsersGroupedByCity(users.stream());
        assertEquals(1, result.get("Grodno"));
        assertEquals(2, result.get("Minsk"));
    }

    @Test
    void testMergeAndFilterMultiplesOfFive() {
        var result = streamAPI.mergeAndFilterMultiplesOfFive();
        assertEquals(List.of(5, 10, 15, 20, 25, 30), result);
    }

    @Test
    void testSumProductPricesByCategory() {
        var categories = List.of(
                new Category("мясные продукты"),
                new Category("овощи"),
                new Category("фрукты")
        );
        var products = Stream.of(
                new Product(BigDecimal.valueOf(1.10), categories.get(0)),
                new Product(BigDecimal.valueOf(2.20), categories.get(1)),
                new Product(BigDecimal.valueOf(3.30), categories.get(2)),
                new Product(BigDecimal.valueOf(4.40), categories.get(0)),
                new Product(BigDecimal.valueOf(5.50), categories.get(0)),
                new Product(BigDecimal.valueOf(6.60), categories.get(1))
        );

        var result = streamAPI.sumProductPricesByCategory(products);

        var expected = Map.of(
                categories.get(0), BigDecimal.valueOf(11.00),
                categories.get(1), BigDecimal.valueOf(8.80),
                categories.get(2), BigDecimal.valueOf(3.30)
        );
        assertEquals(expected, result);
    }

    @Test
    void testCountFilesByExtension() {
        var files = Stream.of(
                new File("file1.txt"),
                new File("file2.doc"),
                new File("file3.jpeg"),
                new File("file4.doc"),
                new File("file5.txt")
        );

        var result = streamAPI.countFilesByExtension(files);

        var expected = Map.of("txt", 2L, "doc", 2L, "jpeg", 1L);
        assertEquals(expected, result);
    }

    @Test
    void testFilterNumbersBetween10And20() {
        var numbers = Stream.of(1, 17, 8, 13, 25, 10);
        var result = streamAPI.filterNumbersBetween10And20(numbers);
        assertEquals(List.of(17, 13), result);
    }

    @Test
    void testDivideIntoEvenAndOdd() {
        var numbers = Stream.of(1, 2, 3, 4, 10, 11, 12, 13);

        var result = streamAPI.divideIntoEvenAndOdd(numbers);

        var expected = Map.of(
                true, List.of(2, 4, 10, 12),
                false, List.of(1, 3, 11, 13)
        );
        assertEquals(expected, result);
    }

    @Test
    void testCountWordsGroupedByFirstChar() {
        var words = Stream.of(
                "Animal", "auto", "Boat",
                "background ","account", "cake"
        );

        var result = streamAPI.countWordsGroupedByFirstChar(words);

        var expected = Map.of('A', 3L, 'B', 2L, 'C', 1L);
        assertEquals(expected, result);
    }

    @Test
    void testMultiplyNumbers() {
        var numbers = Stream.of(1, 2, 3, 4, 5);
        var result = streamAPI.multiplyNumbers(numbers);
        assertEquals(120, result);
    }

    @Test
    void testCountDatesGroupedByMonth() {
        var dates = Stream.of(
                LocalDate.of(2025, 2, 1),
                LocalDate.of(2026, 2, 2),
                LocalDate.of(2026, 2, 3)

        );

        var result = streamAPI.countDatesGroupedByMonth(dates);

        assertEquals(Month.values().length , result.size());
        assertEquals(3L , result.get(Month.FEBRUARY));
        for (Month month: Month.values()) {
            if (!month.equals(Month.FEBRUARY)) {
                assertEquals(0L, result.get(month));
            }
        }
    }
}