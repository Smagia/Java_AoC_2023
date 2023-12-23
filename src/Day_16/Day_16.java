package Day_16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Day_16 {

    public static void main(String[] args) {
        ArrayList<String> lines = new ArrayList<>();
        String currentDirectory = System.getProperty("user.dir");
        String fileName = "src/Day_16/input.txt";
        String filePath = currentDirectory + "/" + fileName;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))  {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        Floor floor = new Floor(lines, new Beam(0,0, BeamDirection.RIGHT));
        floor.printVisited();
    }
}

class Floor {
    private int maxRows;
    private int maxColumns;

    private ArrayList<String> lines;

    private boolean[][] visited;

    private int totalVisited = 0;

    public Floor(ArrayList<String> lines, Beam startingBeam) {
        this.lines = lines;
        this.maxRows = lines.size();
        this.maxColumns = lines.get(0).length();
        this.visited = new boolean[this.maxRows][this.maxColumns];
        for (boolean[] booleans : visited) {
            Arrays.fill(booleans, false);
        }

        beamProceed(startingBeam);
    }

    private void moveBeam(Beam beam) {
        printVisited();
        int tempRow = beam.getCurrentRow();
        int tempColumn = beam.getCurrentColumn();
        System.out.println(tempRow);
        System.out.println(tempColumn);

        int currentVisited = 0;
        int limit = 3;
        while(currentVisited <= limit) {
            if(!canGoThere(tempRow, tempColumn)) {
                break;
            }

            if(visited[tempRow][tempColumn]) {
                currentVisited ++;
            }

            switch (beam.getDirection()) {
                case UP -> tempRow++;
                case DOWN -> tempRow--;
                case LEFT -> tempColumn--;
                case RIGHT -> tempColumn++;
            }

            if (canGoThere(tempRow, tempColumn) && !visited[tempRow][tempColumn]) {
                beam.moveToNextPosition(tempRow, tempColumn);
                beamProceed(beam);
            }
        }
    }

    private void beamProceed(Beam beam) {
        if(!canGoThere(beam.getCurrentRow(), beam.getCurrentColumn())) {
            return;
        }
        visitPlace(beam.getCurrentRow(), beam.getCurrentColumn());
        checkFloor(beam.getCurrentRow(), beam.getCurrentColumn(), beam);
    }

    private boolean canGoThere(int newRow, int newColumn) {
        return newRow >= 0 && newColumn >= 0 && newRow < maxRows && newColumn < maxColumns;
    }

    private void visitPlace(int row, int column) {
        if (canGoThere(row, column) && !visited[row][column]) {
            visited[row][column] = true;
            this.totalVisited++;
        }
    }

    private void checkFloor(int row, int column, Beam beam) {
        char floorItem = this.lines.get(row).charAt(column);

        switch (floorItem) {
            case '.' -> moveBeam(beam);
            case '/' -> {
                if (beam.getDirection().equals(BeamDirection.LEFT)) {
                    beamProceed(new Beam(row-1, column, BeamDirection.UP));
                } else if (beam.getDirection().equals(BeamDirection.UP)) {
                    beamProceed(new Beam(row, column-1, BeamDirection.LEFT));
                } else if (beam.getDirection().equals(BeamDirection.RIGHT)) {
                    beamProceed(new Beam(row+1, column, BeamDirection.DOWN));
                } else if (beam.getDirection().equals(BeamDirection.DOWN)){
                    beamProceed(new Beam(row, column+1, BeamDirection.RIGHT));
                }
            }
            case '\\' -> {
                if (beam.getDirection().equals(BeamDirection.LEFT)) {
                    beamProceed(new Beam(row-1, column, BeamDirection.DOWN));
                } else if (beam.getDirection().equals(BeamDirection.DOWN)){
                    beamProceed(new Beam(row, column-1, BeamDirection.LEFT));
                } else if (beam.getDirection().equals(BeamDirection.RIGHT)){
                    beamProceed(new Beam(row+1, column, BeamDirection.UP));
                } else if (beam.getDirection().equals(BeamDirection.UP)){
                    beamProceed(new Beam(row, column+1, BeamDirection.RIGHT));
                }
            }
            case '|' -> {
                if (beam.getDirection().equals(BeamDirection.LEFT) || beam.getDirection().equals(BeamDirection.RIGHT)) {
                    beamProceed(new Beam(row-1, column, BeamDirection.UP));
                    beamProceed(new Beam(row+1, column, BeamDirection.DOWN));
                } else {
                    moveBeam(beam);
                }
            }
            case '-' -> {
                if (beam.getDirection().equals(BeamDirection.UP) || beam.getDirection().equals(BeamDirection.DOWN)) {
                    beamProceed(new Beam(row, column-1, BeamDirection.LEFT));
                    beamProceed(new Beam(row, column+1, BeamDirection.RIGHT));
                } else {
                    moveBeam(beam);
                }
            }
        }
    }

    public void printVisited() {
        for (boolean[] booleans : visited) {
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < booleans.length; i++) {
                line.append(booleans[i] ? "#" : ".");
            }
            System.out.println(line);
        }
        System.out.println("Total Visited: "+totalVisited);
    }
}

class Beam {
    private int currentRow;
    private int currentColumn;

    private BeamDirection direction;

    public Beam(int currentX, int currentY, BeamDirection direction) {
        this.currentRow = currentX;
        this.currentColumn = currentY;
        this.direction = direction;
    }

    public void moveToNextPosition(int newX, int newY) {
        this.currentRow = newX;
        this.currentColumn = newY;
    }

    public BeamDirection getDirection() {
        return direction;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentColumn() {
        return currentColumn;
    }
}

enum BeamDirection {
    UP, DOWN, LEFT, RIGHT
}