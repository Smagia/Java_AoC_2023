package Day_15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day_15_2 {

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

        HashMap<Integer, List<BoxStep>> boxes = new HashMap<>();

        Arrays.stream(wordsToHash).forEach(word -> {
            final int[] relatedBox = {0};
            System.out.println(word);

            boolean insert = word.contains("=");
            String regex = insert ? "=" : "-";
            String[] data = word.split(regex);
            String label = data[0];
            int value = insert ? Integer.parseInt(data[1]) : 0;

            // Identifies Box
            label.chars().forEach(character -> {
                relatedBox[0] = (relatedBox[0] + character) * 17 % 256;
            });
            System.out.println(word + " " + relatedBox[0]);

            int boxNumber = relatedBox[0];

            if (boxes.containsKey(boxNumber)) {
                List<BoxStep> steps = boxes.get(boxNumber);

                int i;
                boolean found = false;
                for (i = 0; i < steps.size(); i++) {
                    if (steps.get(i).getLabel().equals(label)) {
                        found = true;
                        break;
                    }
                }

                if (found && insert) {
                    steps.set(i, new BoxStep(label, value));
                } else if (found) {
                    steps.remove(i);
                } else {
                    if (insert) {
                        steps.add(new BoxStep(label, value));
                    }
                }
                boxes.put(boxNumber, steps);
            } else {
                if (insert) {
                    ArrayList<BoxStep> steps = new ArrayList<>();
                    steps.add(new BoxStep(label, value));
                    boxes.put(boxNumber, steps);
                }
            }
        });

        AtomicInteger total = new AtomicInteger();

        boxes.keySet().forEach(key -> {
            List<BoxStep> steps = boxes.get(key);

            for (int i = 0; i < steps.size(); i++) {
                int focusingPower = (key + 1) * (i + 1) * steps.get(i).getValue();
                System.out.println("Box " + key + " box " + (key + 1) + " first slot " + (i + 1) + " focal length " + steps.get(i).getValue() + " focusingPower " + focusingPower);
                total.set(total.get() + focusingPower);
            }
        });

        System.out.println(total.get());
    }
}

class BoxStep {
    private String label;
    private int value;

    public BoxStep(String label, int value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public int getValue() {
        return value;

    }

    public void setValue(int value) {
        this.value = value;
    }
}