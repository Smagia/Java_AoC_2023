package Day_4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Character.isDigit;

public class Day_4 {
    public static ArrayList<String> lines = new ArrayList<>();

    public static void main(String[] args) {
        String fileName = "C:\\Users\\aries\\IdeaProjects\\AoC_2023\\src\\Day_4\\input.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        double result = 0;
        for (String line : lines) {
            String tempLine = line.split(":")[1];
            String[] numberParts = tempLine.split("\\|");

            ArrayList<Integer> winningNumbers = getNumbersList(numberParts[0]);
            ArrayList<Integer> scratchedNumbers = getNumbersList(numberParts[1]);
            long lineResult = countWinningNumbers(winningNumbers, scratchedNumbers);
            if (lineResult > 0) {
                result += Math.pow(2, (lineResult - 1));
            }
        }

        System.out.println("Total: " + result);
    }

    public static ArrayList<Integer> getNumbersList(String list) {
        String[] tempNumbers = list.trim().split(" ");
        ArrayList<Integer> result = new ArrayList<>();
        for (String numb : tempNumbers) {
            if (!numb.equals("")) result.add(Integer.parseInt(numb));
        }
        return result;
    }

    public static long countWinningNumbers(ArrayList<Integer> winningNumbers, ArrayList<Integer> scratchedNumbers) {
        return scratchedNumbers
                .stream()
                .filter(winningNumbers::contains)
                .count();
    }
}

