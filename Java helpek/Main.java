import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<Repulok> repulok = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("repulok.csv"), "UTF-8")){
                scanner.nextLine();
                while (scanner.hasNextLine()){
                    repulok.add(new Repulok(scanner.nextLine()));
                }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        Random random = new Random();
        Repulok randomRepulo = repulok.get(random.nextInt(repulok.size()));

        System.out.println();
        System.out.printf("0) Összesen %d repülő adata beolvasva.\n", repulok.size());
        System.out.printf("\tKözülük egy véletlen kiválasztott: %s \n", randomRepulo.getTipus());

        List<Repulok> legtobb = repulok.stream().sorted(Comparator.comparingInt(Repulok::getFerohelyek).reversed()).distinct().limit(2).toList();

        System.out.printf("1) Legtöbb férőhellyel rendelkezik: %s (%d hely)\n", legtobb.get(0).getTipus(), legtobb.get(0).getFerohelyek());
        System.out.printf("\t A második legtöbb férőhely: %s (%d hely)",  legtobb.get(1).getTipus(), legtobb.get(1).getFerohelyek());

        List<Repulok> szazezernelkisebb = repulok.stream().filter(repulok1 -> repulok1.getSuly()< 100000).toList();

        double osszsuly = 0;
        for(Repulok e: szazezernelkisebb){
            osszsuly += e.getSuly();
        }

        double atlagsuly = (double) osszsuly/szazezernelkisebb.size();
        System.out.printf("2) A 100000kg súlynál kisebb gépek (%d darab) átlagsúlya: %.2f kg\n", szazezernelkisebb.size(), atlagsuly);

        String szamnelkulitipus = repulok.stream().filter(repulok1 -> !repulok1.getTipus().chars().anyMatch(Character::isDigit)).map(Repulok::getTipus).collect(Collectors.joining(", "));
        System.out.printf("3) Típusok, amelyikben nincs szám: %s \n", szamnelkulitipus);

        List<String> gyartok = repulok.stream().map(repulok1 -> repulok1
        .getTipus()
        .split(" ")[0]).distinct().sorted().toList();

        System.out.printf("4) Gyártók: %s \n",String.join(", ", gyartok) );

        Random gyartorandom = new Random();
        String veletlengyarto = gyartok.get(random.nextInt(gyartok.size()));

        System.out.printf("Közülük egy véletlen kiválasztott: %s \n", veletlengyarto);
        System.out.printf("Termékeik: \n");
        repulok.stream()
                .filter(repulok1 -> repulok1.getTipus().startsWith(veletlengyarto))
                .forEach(repulok1 -> System.out.printf(" - %s\n", repulok1.getTipus()));

       List<Repulok> haromszaznaltobb = repulok.stream()
                       .filter(repulok1 -> repulok1.getFerohelyek()>300).toList();
        List<String> haromszaznaltobbStr = haromszaznaltobb.stream().map(obj->obj.toString()).toList();

        System.out.println("5) A 300 főnél több férőhelyű gépek adatai a sokutas.txt fájlba mentve.");
        try{
            Files.write(Paths.get("sokutas.txt"),haromszaznaltobbStr, StandardCharsets.UTF_8);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


}