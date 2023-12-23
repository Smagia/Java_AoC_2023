package Day_15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Day_15 {

    public static void main(String[] args) {
        ArrayList<String> lines = new ArrayList<>();
        String currentDirectory = System.getProperty("user.dir");
        String fileName = "src/Day_15/input.txt";
        String filePath = currentDirectory + "/" + fileName;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))  {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] wordsToHash = lines.get(0).split(",");

        final long[] total = {0L};
        Arrays.stream(wordsToHash).forEach(word -> {
            final long[] wordTotal = {0L};
            System.out.println(word);
            word.chars().forEach(character -> {
                wordTotal[0] = (wordTotal[0] + character) * 17 % 256;
            });
            System.out.println(word + " " + wordTotal[0]);
            total[0] += wordTotal[0];
        });

        System.out.println(total[0]);
    }
}