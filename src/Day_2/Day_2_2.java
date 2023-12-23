package Day_2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Day_2_2 {

    public static void main(String[] args) {
        ArrayList<String> lines = new ArrayList<>();
        String currentDirectory = System.getProperty("user.dir");
        String fileName = "src/Day_2/input.txt";
        String filePath = currentDirectory + "/" + fileName;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        HashMap<String, Integer> rules = new HashMap<>();
//        rules.put("red", 12);
//        rules.put("green", 13);
//        rules.put("blue", 14);

        long total = 0;
        for (int i = 0; i < lines.size(); i++) {
            // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
            System.out.println(lines.get(i));
            String[] games = lines.get(i).split(":")[1].split(";");
            // ['3 blue, 4 red', '1 red, 2 green, 6 blue', '2 green']

            HashMap<String, Integer> requiredCubes = new HashMap<>();
            requiredCubes.put("red", 0);
            requiredCubes.put("green", 0);
            requiredCubes.put("blue", 0);

            for (int j = 0; j < games.length; j++) {
                String[] dices = games[j].trim().split(",");
                for (int k = 0; k < dices.length; k++) {
                    String[] dice = dices[k].trim().split(" ");
                    if (Integer.parseInt(dice[0]) > requiredCubes.get(dice[1])) {
                        requiredCubes.put(dice[1], Integer.parseInt(dice[0]));
                    }
                }
            }
            total = total + requiredCubes.get("red") * requiredCubes.get("green") * requiredCubes.get("blue");
        }

        System.out.println(total);
    }
}