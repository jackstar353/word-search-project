package ie.wordsearchproject.wordsearchgrid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

@Service
public class WordGridService {

    private enum Direction {
        HORIZONTAL,
        VERTICAL,
        DIAGONAL,
        HORIZONTAL_INVERSE,
        VERTICAL_INVERSE,
        DIAGONAL_INVERSE
    }

    private class Coordinates {
        int x;
        int y;

        Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    

    public char[][]populateGrid(int gridSize,List<String> words) {
        List<Coordinates> coordinates = new ArrayList<>();
        char [][]gridContents = new char[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                coordinates.add(new Coordinates(i, j));
                gridContents[i][j] = '_';
            }

        }
        Collections.shuffle(coordinates);
        for (String word : words) {
            

            for (Coordinates coordinate : coordinates) {
                int x = coordinate.x;
                int y = coordinate.y;
                Direction selectedDirection = getDirectionForFit(gridContents,word, coordinate);
                if (selectedDirection !=null) {
                    switch(selectedDirection){
                        case HORIZONTAL:
                    for (char c : word.toCharArray()) {
                        gridContents[x][y++] = c;
                    }
                    break;
                    case VERTICAL:
                    for (char c : word.toCharArray()) {
                        gridContents[x++][y] = c;
                    }
                    break;
                    case DIAGONAL:
                    for (char c : word.toCharArray()) {
                        gridContents[x++][y++] = c;
                    }
                    break;
                    case HORIZONTAL_INVERSE:
                    for (char c : word.toCharArray()) {
                        gridContents[x][y--] = c;
                    }
                    break;
                    case VERTICAL_INVERSE:
                    for (char c : word.toCharArray()) {
                        gridContents[x--][y] = c;
                    }
                    break;
                    case DIAGONAL_INVERSE:
                    for (char c : word.toCharArray()) {
                        gridContents[x--][y--] = c;
                    }
                    break;
                }
                break;
            }
        }
    }
randomFillGrid(gridContents);
return gridContents;
        
    }

    public void displayGrid(char [][]gridContents) {
        int gridSize = gridContents[0].length;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                System.out.print(gridContents[i][j] + " ");
            }
            System.out.println("");
        }
    }

    private void randomFillGrid(char [][]gridContents){
        int gridSize =gridContents[0].length;
        String allCapitalLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
               if(gridContents[i][j] == '_'){
                   int randomIndex = ThreadLocalRandom.current().nextInt(0, allCapitalLetters.length());
                   gridContents[i][j] = allCapitalLetters.charAt(randomIndex);
               }
            }
    }
    }

    private Direction getDirectionForFit(char [][]gridContents, String word, Coordinates coordinate) {
        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions);
        for (Direction direction : directions) {
            if (doesFit(gridContents,word, coordinate, direction)) {
                return direction;
            }
        }

        return null;
    }

    private boolean doesFit(char [][]gridContents, String word, Coordinates coordinate, Direction direction) {
        int gridSize =gridContents[0].length;
        int wordLength = word.length();
        switch (direction) {
            case HORIZONTAL:

                if (coordinate.y + wordLength > gridSize)
                    return false;
                for (int i = 0; i < wordLength; i++) {
                    if (gridContents[coordinate.x][coordinate.y + i] != '_')
                        return false;
                }
                break;
            case VERTICAL:
                if (coordinate.x + wordLength > gridSize)
                    return false;
                for (int i = 0; i < wordLength; i++) {
                    if (gridContents[coordinate.x + i][coordinate.y] != '_')
                        return false;
                }
                break;

            case DIAGONAL:
                if (coordinate.x + wordLength > gridSize || coordinate.y + word.length() > gridSize)
                    return false;
                for (int i = 0; i < wordLength; i++) {
                    if (gridContents[coordinate.x + i][coordinate.y + i] != '_')
                        return false;
                }
                break;
                case HORIZONTAL_INVERSE:

                if (coordinate.y < wordLength )
                    return false;
                for (int i = 0; i < wordLength; i++) {
                    if (gridContents[coordinate.x][coordinate.y - i] != '_')
                        return false;
                }
                break;
            case VERTICAL_INVERSE:
                if (coordinate.x < wordLength )
                    return false;
                for (int i = 0; i < wordLength; i++) {
                    if (gridContents[coordinate.x - i][coordinate.y] != '_')
                        return false;
                }
                break;

            case DIAGONAL_INVERSE:
                if (coordinate.x < wordLength  || coordinate.y < wordLength)
                    return false;
                for (int i = 0; i < wordLength; i++) {
                    if (gridContents[coordinate.x - i][coordinate.y - i] != '_')
                        return false;
                }
                break;
        }
        return true;
    }

}
