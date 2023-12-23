package Day_4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Character.isDigit;

public class Day_4_2 {
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

        int totalScratchcards = 0;
        HashMap<Integer, Integer> temp = new HashMap<>();
        for (int i = 0; i < lines.size(); i++) {
            String tempLine = lines.get(i).split(":")[1];
            String[] numberParts = tempLine.split("\\|");

            ArrayList<Integer> winningNumbers = getNumbersList(numberParts[0]);
            ArrayList<Integer> scratchedNumbers = getNumbersList(numberParts[1]);
            long lineResult = countWinningNumbers(winningNumbers, scratchedNumbers);

            if (lineResult > 0) {
                System.out.println("Card "+(i+1)+" won copies "+lineResult);
                for (int j = 0; j < lineResult; j++) {
                    int nextCard = i + 1 + j;
                    int multiplier = 1 + temp.getOrDefault(i, 0);
//                    System.out.println("For card "+(i)+" there are "+multiplier+" copies of "+nextCard);
                    if(temp.containsKey(nextCard)) {
                        temp.put(nextCard, temp.get(nextCard)+multiplier);
                    } else {
                        temp.put(nextCard, multiplier);
                    }
                }
            }

            System.out.println("Card "+(i+1)+" having copies "+temp.getOrDefault(i, 0));
            totalScratchcards += 1 + temp.getOrDefault(i, 0);
        }

        for(int key : temp.keySet()) {
            System.out.println(key+" "+temp.get(key));
        }
        System.out.println("Total: " + totalScratchcards);
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

