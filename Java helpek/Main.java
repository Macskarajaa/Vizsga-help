import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<DiaFilmek> diafilmlist = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File("diafilm.csv"), "UTF-8")) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                diafilmlist.add(new DiaFilmek(scanner.nextLine()));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        List<DiaFilmek> szintelen = diafilmlist.stream().filter(diaFilmek -> !diaFilmek.getSzines()).toList();

        //0) 705 diafilm adata beolvasva
        //Közülük 141 még fekete-fehér

        System.out.printf("0) %d diafilm adata beolvasva\n", diafilmlist.size());
        System.out.printf("\tKözülük %d még fekete-fehér\n", szintelen.size());

        //1) A legrégebbi diafilm: A cár és a madár (1950)
        //De ugyanebben az évben készült még:

        DiaFilmek legregebbi = diafilmlist.stream().min(Comparator.comparing(DiaFilmek::getEv)).get();
        System.out.printf("1) A legrégebbi diafilm: %s (%d)", legregebbi.getCim(), legregebbi.getEv());
        System.out.printf("\nDe ugyanebben az évben készült még:");

        //2) A 2000 előtt készült diafilmek átlagos kockaszáma: 38,7
        //A később készült diafilmeknél az áltag: 29,4

        List<DiaFilmek> legregebbifilmek = diafilmlist.stream()
                .filter(diaFilmek ->{
                    return diaFilmek.getEv().equals(legregebbi.getEv()) && !diaFilmek.equals(legregebbi);

                }).toList();
        legregebbifilmek.stream().forEach(hzs-> System.out.printf("\n -  %s (%d)",hzs.getCim(), hzs.getEv()));

        double ketezerelottiatlag = diafilmlist.stream().filter(diaFilmek -> diaFilmek.getEv() < 2000).map(DiaFilmek::getKocka).collect(Collectors.averagingDouble(Integer::intValue));

        double ketezerutaniatlag = diafilmlist.stream().filter(diaFilmek -> diaFilmek.getEv() > 2000).map(DiaFilmek::getKocka).collect(Collectors.averagingDouble(Integer::intValue));

        System.out.printf("2) A 2000 előtt készült diafilmek átlagos kockaszáma: %.1f\n",ketezerelottiatlag);
        System.out.printf("\tA később készült diafilmeknél az áltag: %.1f",ketezerutaniatlag);

        // 3) Az egyes évtizedekben készült diafilmek száma:
        //lefott a kave
        Map<String, Long> feladom = diafilmlist.stream()
                .collect(Collectors.groupingBy(
                        evszerint -> evszerint.getEv() / 10 * 10 + "-" + evszerint.getEv() / 10 + "9",
                        Collectors.counting()));

        System.out.println("3) Az egyes évtizedekben készült diafilmek száma:");
        Map<String, Long> countByEvSortedDesc = feladom.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new));
        countByEvSortedDesc.forEach((key, value)-> System.out.printf("\n %s : %d darab", key, value));


        //4) A legtöbb kocka (3053 db) készítésének éve: 1957
        //    A második legtöbb kocka (2016 db) éve: 1958
        Map<Integer, Integer> evekEsKockak = diafilmlist.stream()
                        .collect(Collectors.groupingBy(
                                DiaFilmek::getEv,
                                Collectors.summingInt(DiaFilmek::getKocka)
                        ));

        evekEsKockak.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .ifPresent(result -> System.out.printf("\n4) A legtöbb kocka (%d db) készítésének éve: %d\n", result.getValue(), result.getKey()));


        List<DiaFilmek> ketezernelnagyobb= diafilmlist.stream().filter(value->value.getEv() >= 2000).toList();
        List<String> ketezernelnagyobbStr = ketezernelnagyobb.stream().map(obj->obj.toString()).toList();

        System.out.println("\n5) A 200x évben megjelent diák adatai elmentve (200x.txt)");
        try{
            Files.write(Paths.get("200x.txt"),ketezernelnagyobbStr, StandardCharsets.UTF_8);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}