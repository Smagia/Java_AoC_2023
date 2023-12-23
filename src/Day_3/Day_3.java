package Day_3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Character.isDigit;

public class Day_3 {
    public static ArrayList<String> lines = new ArrayList<>();
    public static String allowedChars = "0123456789.";
    public static long total = 0L;

    public static void main(String[] args) {
        ArrayList<String> lines = new ArrayList<>();
        String currentDirectory = System.getProperty("user.dir");
        String fileName = "src/Day_3/input.txt";
        String filePath = currentDirectory + "/" + fileName;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < lines.size(); i++) {
            String currentLine = lines.get(i);
            for (int j = 0; j < currentLine.length(); j++) {
                if (allowedChars.indexOf(currentLine.charAt(j)) == -1) {
                    findNearValues(i, j);
                }
            }
        }

        System.out.println("----------------------");
        for (String line : lines) {
            System.out.println(line);
        }

        System.out.println("Total: " + total);
    }

    public static void findNearValues(int linePosition, int charPosition) {
        ArrayList<String> linesToCheck = new ArrayList<>();
        linesToCheck.add(linePosition > 0 ? lines.get(linePosition - 1) : "");
        linesToCheck.add(lines.get(linePosition));
        linesToCheck.add(linePosition < lines.size() - 1 ? lines.get(linePosition + 1) : "");

        for (int i = 0; i < linesToCheck.size(); i++) {
            String line = linesToCheck.get(i);
            if (!Objects.equals(line, "")) {
                line = checkLine(line, charPosition);
                int index = switch (i) {
                    case 0 -> linePosition - 1;
                    case 1 -> linePosition;
                    case 2 -> linePosition + 1;
                    default -> -1;
                };
                lines.set(index, line);
                linesToCheck.set(i, line);
            }
        }
    }

    public static String checkLine(String line, int charPosition) {
        int from = charPosition > 0 ? charPosition - 1 : charPosition;
        int to = charPosition < line.length() - 1 ? charPosition + 1 : charPosition;

        ArrayList<Integer> foundValues = new ArrayList<>();

        for (int i = from; i <= to; i++) {
            char c = line.charAt(i);
            if (c != '.' && allowedChars.indexOf(c) != -1) {
                // digit found
                int found = findNumber(line, i);
                if (found != -1) {
                    foundValues.add(found);
                    total += found;
                    line = fixString(foundValues.get(foundValues.size() - 1), charPosition, line);
                }
            }
        }

        System.out.println("Values identifed for line " + foundValues
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", ")));

        return line;
    }

    public static String fixString(int value, int originalCharPosition, String line) {
        StringBuilder result = new StringBuilder();

        Pattern regex = Pattern.compile("\\.?" + value + "\\.?");
        Matcher matcher = regex.matcher(line);


        while (matcher.find()) {
            int matchStart = matcher.start();
            int matchEnd = matcher.end();

            if((originalCharPosition >= matchStart && originalCharPosition <= matchEnd)
                || matchStart == originalCharPosition + 1) {
                String match = matcher.group();
                String replacement = ".".repeat(match.length());
                matcher.appendReplacement(result, replacement);
                System.out.println("Found: "+match);
            }
        }
        matcher.appendTail(result);

        return result.toString();
    }

    public static int findNumber(String line, int position) {
        StringBuilder number = new StringBuilder();
        int i;
        for (i = position - 1; i >= 0; i--) {
            if (isDigit(line.charAt(i))) {
                number.append(line.charAt(i));
            } else {
                break;
            }
        }

        number.reverse();
        number.append(line.charAt(position));
        for (i = position + 1; i < line.length(); i++) {
            if (isDigit(line.charAt(i))) {
                number.append(line.charAt(i));
            } else {
                break;
            }
        }
        if (number.toString().isEmpty()) {
            return -1;
        }

        return Integer.parseInt(number.toString());
    }
}

