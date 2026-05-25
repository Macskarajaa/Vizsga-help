package main;

import java.util.ArrayList;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.Random;
import java.util.stream.Collectors;

public class MainTemplate {
        
        public static void printPrintfExamples() {
                String name = "Alice";
                int count = 5;
                double value = 12.34567;
                long big = 123456789L;
                boolean flag = true;
                char letter = 'A';
                LocalDateTime dateTime = LocalDateTime.of(2026, 5, 20, 14, 30, 0);

                // String=%s, int=%d, long=%d, double=%f, boolean=%b, char=%c
                System.out.printf("String=%s Int=%d Long=%d Double=%f Bool=%b Char=%c%n",
                                name, count, big, value, flag, letter);

                // Width and alignment: %10s right-align, %-10s left-align
                System.out.printf("|%10s| |%-10s|%n", name, name);

                // New lines and precision: %.3f -> 3 decimals
                System.out.printf("%s%n%d%n%.3f%n", name, count, value);

                // Rounding: %.0f (no decimals), %.2f (two decimals)
                System.out.printf("Rounded=%.0f TwoDecimals=%.2f%n", value, value);

                System.out.printf("Hex=%x Oct=%o%n", count, count);
                System.out.printf("Sci=%e Fixed=%.4f%n", value, value);
                System.out.printf("Percent=%% Value=%f%n", value);

                // Date/time: %tF (YYYY-MM-DD), %tT (HH:MM:SS), %tR (HH:MM)
                System.out.printf("Date=%tF Time=%tT ShortTime=%tR%n", dateTime, dateTime, dateTime);
        }

        // ===== GENERAL GLOBALS =====
        public static List<Data> dataList = new ArrayList<>();
        public static String groupFilter = "A";
        public static String cityFilter = "City";
        public static String nameFilter = "Name";
        public static int topN = 3;

        // ===== STREAM TEMPLATES (GLOBAL VALUES) =====
        // Max item by numeric value.
        public static Optional<Data> maxByValue = dataList.stream()
                        .max(Comparator.comparingDouble(Data::getValue));

        // Min item by numeric value.
        public static Optional<Data> minByValue = dataList.stream()
                        .min(Comparator.comparingDouble(Data::getValue));

        // First item by group filter (if any).
        public static Optional<Data> firstByGroup = dataList.stream()
                        .filter(data -> Objects.equals(data.getGroup(), groupFilter))
                        .findFirst();

        // Any item in a specific city.
        public static boolean anyInCity = dataList.stream()
                        .anyMatch(data -> Objects.equals(data.getCity(), cityFilter));

        // Count items by name filter.
        public static long countByName = dataList.stream()
                        .filter(data -> Objects.equals(data.getName(), nameFilter))
                        .count();

        // Sort all items by value descending.
        public static List<Data> sortedByValueDesc = dataList.stream()
                        .sorted(Comparator.comparingDouble(Data::getValue).reversed())
                        .collect(Collectors.toList());

        // Take top N by value.
        public static List<Data> topByValue = dataList.stream()
                        .sorted(Comparator.comparingDouble(Data::getValue).reversed())
                        .limit(topN)
                        .collect(Collectors.toList());

        // Distinct non-null names.
        public static List<String> distinctNames = dataList.stream()
                        .map(Data::getName)
                        .filter(Objects::nonNull)
                        .distinct()
                        .collect(Collectors.toList());

        // Random selection from the list.
        public static Data randomData() {
                if (dataList.isEmpty()) {
                        return null;
                }
                Random random = new Random();
                return dataList.get(random.nextInt(dataList.size()));
        }

        // Average of numeric values.
        public static double averageValue = dataList.stream()
                        .collect(Collectors.averagingDouble(Data::getValue));

        // HashMap templates (what each pattern does)
        // 1) groupByGroup: key = group, value = all rows for that group.
        // 2) countByCityMap: key = city, value = number of rows in that city.
        // 3) sumValueByGroupMap: key = group, value = sum of value in that group.
        public static HashMap<String, List<Data>> groupByGroup = dataList.stream()
                        .collect(Collectors.groupingBy(
                                        Data::getGroup,
                                        HashMap::new,
                                        Collectors.toList()));

        public static HashMap<String, Long> countByCityMap = dataList.stream()
                        .collect(Collectors.groupingBy(
                                        Data::getCity,
                                        HashMap::new,
                                        Collectors.counting()));

        // TreeMap example: same as countByCityMap but keys are sorted.
        public static TreeMap<String, Long> countByCityTreeMap = dataList.stream()
                        .collect(Collectors.groupingBy(
                                        Data::getCity,
                                        TreeMap::new,
                                        Collectors.counting()));

        public static HashMap<String, Double> sumValueByGroupMap = dataList.stream()
                        .collect(Collectors.groupingBy(
                                        Data::getGroup,
                                        HashMap::new,
                                        Collectors.summingDouble(Data::getValue)));

        // HashMap sorting by value (ascending).
        public static Map<String, Long> countByCitySortedAsc = countByCityMap.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (a, b) -> a,
                                        LinkedHashMap::new));

        // HashMap sorting by value (descending).
        public static Map<String, Long> countByCitySortedDesc = countByCityMap.entrySet().stream()
                        .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                        .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (a, b) -> a,
                                        LinkedHashMap::new));

        // ===== ADVANCED STREAM TEMPLATES =====
        // Partition items by a predicate (true/false groups).
        public static Map<Boolean, List<Data>> partitionByPositiveValue = dataList.stream()
                        .collect(Collectors.partitioningBy(data -> data.getValue() > 0));

        // Group by group, then by city (nested grouping).
        public static Map<String, Map<String, List<Data>>> groupByGroupThenCity = dataList.stream()
                        .collect(Collectors.groupingBy(
                                        Data::getGroup,
                                        Collectors.groupingBy(Data::getCity)));

        // Count by group.
        public static Map<String, Long> countByGroupMap = dataList.stream()
                        .collect(Collectors.groupingBy(Data::getGroup, Collectors.counting()));

        // Summary stats by group (count, min, max, average, sum).
        public static Map<String, DoubleSummaryStatistics> statsByGroup = dataList.stream()
                        .collect(Collectors.groupingBy(
                                        Data::getGroup,
                                        Collectors.summarizingDouble(Data::getValue)));

        // Multi-field sorting (group ASC, value DESC).
        public static List<Data> sortByGroupThenValueDesc = dataList.stream()
                        .sorted(Comparator.comparing(
                                        Data::getGroup,
                                        Comparator.nullsLast(Comparator.naturalOrder()))
                                        .thenComparing(Comparator.comparingDouble(Data::getValue).reversed()))
                        .collect(Collectors.toList());

        // Build a map by ID; merge keeps the larger value.
        public static Map<String, Data> mapByIdKeepMaxValue = dataList.stream()
                        .collect(Collectors.toMap(
                                        Data::getId,
                                        data -> data,
                                        (left, right) -> left.getValue() >= right.getValue() ? left : right,
                                        HashMap::new));

        // Join names into a single string.
        public static String joinedNames = dataList.stream()
                        .map(Data::getName)
                        .filter(Objects::nonNull)
                        .collect(Collectors.joining(", "));

        // Sum with mapToDouble (alternative to reduce).
        public static double totalValue = dataList.stream()
                        .mapToDouble(Data::getValue)
                        .sum();

        // ===== DATE/TIME TEMPLATES =====
        public static String dateSample = "2026-05-20";
        public static String timeSample = "14:30";
        public static String dateTimeSample = "2026-05-20 14:30:00";

        public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Parsing
        public static LocalDate parsedDate = LocalDate.parse(dateSample);
        public static LocalTime parsedTime = LocalTime.parse(timeSample);
        public static LocalDateTime parsedDateTime = LocalDateTime.parse(dateTimeSample, dateTimeFormatter);

        // Formatting
        public static String formattedDateTime = parsedDateTime.format(dateTimeFormatter);

        // Comparison
        public static boolean dateIsAfter = parsedDate.isAfter(parsedDate.minusDays(1));

        // Differences
        public static long daysBetween = ChronoUnit.DAYS.between(parsedDate.minusDays(7), parsedDate);
        public static Period datePeriod = Period.between(parsedDate.minusDays(10), parsedDate);
        public static Duration timeDuration = Duration.between(parsedTime.minusMinutes(30), parsedTime);

        // Plus/minus
        public static LocalDate nextWeek = parsedDate.plusDays(7);
        public static LocalDateTime plusTwoHours = parsedDateTime.plusHours(2);
        public static LocalTime minusTenMinutes = parsedTime.minusMinutes(10);

        // ===== VALIDATION HELPERS =====
        public static String nameToken = "a";
        public static String numericSample = "12.5";

        // Any name contains a token (null-safe).
        public static boolean anyNameContainsToken = dataList.stream()
                        .anyMatch(data -> safeContains(data.getName(), nameToken));

        // Safe parse example.
        public static Double parsedNumber = parseDoubleSafe(numericSample);

        public static class Data {
                private final String id;
                private final String name;
                private final double value;
                private final String group;
                private final String city;
                private final String date;

                public Data(String id, String name, double value, String group, String city, String date) {
                        this.id = id;
                        this.name = name;
                        this.value = value;
                        this.group = group;
                        this.city = city;
                        this.date = date;
                }

                public String getId() {
                        return id;
                }

                public String getName() {
                        return name;
                }

                public double getValue() {
                        return value;
                }

                public String getGroup() {
                        return group;
                }

                public String getCity() {
                        return city;
                }

                public String getDate() {
                        return date;
                }
        }

        public static boolean isBlank(String value) {
                return value == null || value.trim().isEmpty();
        }

        public static boolean safeEquals(String left, String right) {
                return Objects.equals(left, right);
        }

        public static boolean safeContains(String text, String token) {
                return text != null && token != null && text.contains(token);
        }

        public static Double parseDoubleSafe(String raw) {
                if (isBlank(raw)) {
                        return null;
                }
                try {
                        return Double.parseDouble(raw.trim());
                } catch (Exception ignored) {
                        return null;
                }
        }
}
