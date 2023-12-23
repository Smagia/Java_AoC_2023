package Day_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Day_1 {

    public static void main(String[] args) {
        ArrayList<String> lines = new ArrayList<>();
        String currentDirectory = System.getProperty("user.dir");
        String fileName = "src/Day_1/input.txt";
        String filePath = currentDirectory + "/" + fileName;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))  {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int total = lines.stream()
                .mapToInt(line -> {
                    String number = "";
                    number = line
                            .chars()
                            .filter(Character::isDigit)
                            .mapToObj(Character::toString)
                            .collect(Collectors.joining());
                    if (number.length() > 1) {
                        number = number.charAt(0) + String.valueOf(number.charAt(number.length() - 1));
                    } else if (number.length() == 1) {
                        number = number.charAt(0) + String.valueOf(number.charAt(0));
                    }
                    return Integer.parseInt(number);
                }).sum();
        System.out.println(total);
    }
}