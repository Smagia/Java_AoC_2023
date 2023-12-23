package Day_5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day_5 {
    public static ArrayList<String> lines = new ArrayList<>();

    public static void main(String[] args) {
        String currentDirectory = System.getProperty("user.dir");
        String fileName = "src/Day_5/input.txt";
        String filePath = currentDirectory + "/" + fileName;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Long> seeds = Arrays.stream(lines.get(0).split(":")[1].trim().split(" ")).map(Long::parseLong).toList();


        HashMap<String, List<List<Long>>> map = new HashMap<>();

        String currentKey = "";
        for (String s : lines.subList(1, lines.size())) {
            if (!s.equals("")) {
                if (s.contains("map:")) {
                    currentKey = s.trim().split(" ")[0];
                } else {
                    List<Long> tempValues = Arrays.stream(s.trim().split(" ")).map(Long::parseLong).toList();
                    map.computeIfAbsent(currentKey, k -> new ArrayList<>()).add(tempValues);
                }
            }
        }

        for (String key : map.keySet()) {
            System.out.println("---- " + key);
            map.get(key).forEach(System.out::println);
        }


        HashMap<Long, HashMap<String, Long>> seedList = new HashMap<>();
        System.out.println("---- Seeds: ");
        String[] listOrder = {
                "seed-to-soil", "soil-to-fertilizer", "fertilizer-to-water", "water-to-light", "light-to-temperature", "temperature-to-humidity", "humidity-to-location"
        };
        long minLocation = -1;
        for (long seed : seeds) {
            final long[] target = {seed};
            for (String key : listOrder) {
                List<Long> correspondingRanges = map.get(key).stream()
                        .filter(list -> target[0] >= list.get(1) && target[0] < list.get(1) + list.get(2))
                        .findFirst().orElse(null);

                long destination = target[0];
                if (correspondingRanges != null) {
                    destination = target[0] - correspondingRanges.get(1) + correspondingRanges.get(0);
                    System.out.println(key + " -> " + target[0] + " " + correspondingRanges.get(1) + " " + correspondingRanges.get(0));
                    System.out.println(key + " -> " + destination);
                }
                target[0] = destination;
                seedList.computeIfAbsent(seed, k -> new HashMap<>())
                        .put(key, destination);

                if (key.equals("humidity-to-location")
                        && (minLocation == -1 || destination < minLocation)) {
                    minLocation = destination;
                }
            }
        }

        System.out.println(seedList);
        System.out.println("Result " + minLocation);

    }
}
