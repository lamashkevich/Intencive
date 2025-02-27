package ru.aston.lamashkevich_ev.task5;

import ru.aston.lamashkevich_ev.task5.model.Category;
import ru.aston.lamashkevich_ev.task5.model.Product;
import ru.aston.lamashkevich_ev.task5.model.User;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamAPI {

    //1. Собрать числа в Stream в список сумм цифр каждого числа.
    public List<Integer> mapNumbersToDigitSumList(Stream<Integer> numbers) {
        return numbers
                .map(num -> String.valueOf(Math.abs(num))
                        .chars()
                        .map(Character::getNumericValue)
                        .sum())
                .collect(Collectors.toList());
    }

    //2. Собрать пользователей в Stream в список объектов, где каждый объект содержит информацию
    // о городе проживания пользователя и количестве пользователей из этого города.
    public Map<String, Long> countUsersGroupedByCity(Stream<User> users) {
        return users.collect(Collectors.groupingBy(User::getCity, Collectors.counting()));
    }

    //3. Создать три стрима из массивов чисел от 1 до 10, от 10 до 20 и от 20 до 30 соответственно.
    // Объединить их в один стрим и вывести числа, которые кратны 5.
    public List<Integer> mergeAndFilterMultiplesOfFive() {
        return Stream.of(
                    IntStream.rangeClosed(1,10).boxed(),
                    IntStream.rangeClosed(11,20).boxed(),
                    IntStream.rangeClosed(21,30).boxed())
                .flatMap(stream -> stream)
                .filter(num -> num % 5 == 0)
                .collect(Collectors.toList());
    }

    //4. Разделить продукты в Stream на несколько групп по категориям (например, молочные продукты,
    // мясные продукты, овощи, фрукты), посчитать стоимость продуктов в каждой группе.
    public Map<Category, BigDecimal> sumProductPricesByCategory(Stream<Product> products) {
        return products
                .collect(Collectors.groupingBy(Product::getCategory,
                        Collectors.reducing(BigDecimal.ZERO ,Product::getPrice, BigDecimal::add)));
    }

    //5. Сгруппировать файлы в Stream по расширению, посчитать количество файлов с каждым расширением и вывести
    // результаты в виде списка, где ключ — расширение файла, а значение — количество файлов с таким расширением.
    public Map<String, Long> countFilesByExtension(Stream<File> files) {
        return files
                .map(file -> file.getName()
                        .substring(file.getName().lastIndexOf('.') + 1))
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
    }

    //6. Создать стрим из массива чисел и вывести на экран только числа, которые больше 10 и меньше 20.
    public List<Integer> filterNumbersBetween10And20(Stream<Integer> numbers) {
        return numbers
                .filter(num -> num > 10 && num < 20)
                .collect(Collectors.toList());
    }

    //7. Разделить элементы Stream на две группы: четные и нечетные, вывести результаты.
    public Map<Boolean, List<Integer>> divideIntoEvenAndOdd(Stream<Integer> numbers) {
        return numbers.collect(Collectors.partitioningBy(num -> num % 2 == 0));
    }

    //8. Сгруппировать слова в Stream по первой букве, посчитать количество слов в каждой группе
    // и вывести результаты в виде словаря, где ключ — первая буква слова, а значение — количество слов,
    // начинающихся на эту букву.
    public Map<Character, Long> countWordsGroupedByFirstChar(Stream<String> words) {
        return words.collect(Collectors.groupingBy(
                word -> word.toUpperCase().charAt(0), Collectors.counting()));
    }

    //9. Соберите числа в Stream в одно число, перемножив их между собой и выведите результат.
    public Integer multiplyNumbers(Stream<Integer> numbers) {
        return numbers.reduce(1, (a, b) -> a * b);
    }

    //10. Сгруппировать даты в Stream по месяцу, посчитать количество дат в каждом месяце
    // и вывести результаты в виде календаря с количеством событий в каждом месяце.
    public Map<Month, Long> countDatesGroupedByMonth(Stream<LocalDate> dates) {
        Map<Month, Long> groupedByMonth = dates
                .collect(Collectors.groupingBy(
                        LocalDate::getMonth,
                        () -> new EnumMap<>(Month.class),
                        Collectors.counting())
                );

        return Stream
                .of(Month.values())
                .collect(Collectors.toMap(
                        month -> month,
                        count -> groupedByMonth.getOrDefault(count, 0L),
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

}
