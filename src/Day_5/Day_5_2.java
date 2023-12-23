package Day_5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day_5_2 {
    public static ArrayList<String> lines = new ArrayList<>();
    static long minLocation = -1;

    public static long completed = 0;
    public static long started = 0;

    public static void main(String[] args) {
        String currentDirectory = System.getProperty("user.dir");
        String fileName = "src/Day_5/input.txt";
        String filePath = currentDirectory + "/" + fileName;

        long startTime = System.nanoTime();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Long> tempSeeds = Arrays.stream(lines.get(0).split(":")[1].trim().split(" ")).map(Long::parseLong).toList();

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

        String[] listOrder = {
                "seed-to-soil", "soil-to-fertilizer", "fertilizer-to-water", "water-to-light", "light-to-temperature", "temperature-to-humidity", "humidity-to-location"
        };

        int j;
        for (int i = 0; i < tempSeeds.size(); i++) {
            j = i + 1;
            long start = tempSeeds.get(i);
            long end = start + tempSeeds.get(j);
            started++;
            System.out.println("Created " + started);
            Thread thread = new Thread(() -> {
                for (long seed = start; seed < end; seed++) {
                    final long[] target = {seed};
                    for (String key : listOrder) {
                        List<Long> correspondingRanges = map.get(key).stream()
                                .filter(list -> target[0] >= list.get(1) && target[0] < list.get(1) + list.get(2))
                                .findFirst().orElse(null);

                        long destination = target[0];
                        if (correspondingRanges != null) {
                            destination = target[0] - correspondingRanges.get(1) + correspondingRanges.get(0);
                        }
                        target[0] = destination;

                        if (key.equals("humidity-to-location")
                                && (minLocation == -1 || destination < minLocation)) {
                            minLocation = destination;
                        }
                    }
                }
                completed++;
                System.out.println("Thread Completed");
            });
            thread.start();
            i = j;
        }

        while (true) {
            try {
                Thread.sleep(500);
                if (completed == started) {
                    long executionTime = System.nanoTime() - startTime;
                    System.out.println("Execution Time: " + executionTime / 1_000_000 + " ms");
                    System.out.println("Result " + minLocation);
                    break;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

