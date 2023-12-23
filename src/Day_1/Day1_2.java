package Day_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Day1_2 {
    static HashMap<String, Integer> values = new HashMap<>();

    public static void main(String[] args) {
        String currentDirectory = System.getProperty("user.dir");
        String fileName = "src/Day_1/input.txt";
        String filePath = currentDirectory + "/" + fileName;

        ArrayList<String> lines = new ArrayList<>();
        values.put("one", 1);
        values.put("two", 2);
        values.put("three", 3);
        values.put("four", 4);
        values.put("five", 5);
        values.put("six", 6);
        values.put("seven", 7);
        values.put("eight", 8);
        values.put("nine", 9);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int total = lines.stream()
                .mapToInt(line -> {
                    String tempLine = line
                            .replace("one", "o1e")
                            .replace("two", "t2o")
                            .replace("three", "thr3e").replace("four", "fo4r")
                            .replace("five", "fi5e").replace("six", "s6x")
                            .replace("seven", "se7en").replace("eight", "eig8t")
                            .replace("nine", "ni9e");

                    String number = tempLine
                            .chars()
                            .filter(Character::isDigit)
                            .mapToObj(Character::toString)
                            .collect(Collectors.joining());

                    System.out.println(line);
                    if (number.length() > 1) {
                        number = number.charAt(0) + String.valueOf(number.charAt(number.length() - 1));
                    } else if (number.length() == 1) {
                        number = number.charAt(0) + String.valueOf(number.charAt(0));
                    }
                    System.out.println(number);
                    return Integer.parseInt(number);
                }).sum();
        System.out.println(total);
        System.out.println(lines.size());
    }
}