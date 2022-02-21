package ie.wordsearchproject.wordsearchgrid;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")


public class WordSearchController {

    @Autowired
    WordGridService wordGridService;

    
    @GetMapping("/wordgrid")
    @CrossOrigin(origins = "http://localhost:1234" )
    public String createWordGrid(@RequestParam int gridSize,@RequestParam String wordList){
       List<String> words = Arrays.asList(wordList.split(","));
       char[][]grid = wordGridService.populateGrid(gridSize, words);
       String gridTostring ="";
       
       for (int i = 0; i < gridSize; i++) {
           for (int j = 0; j < gridSize; j++) {
               gridTostring += grid[i][j] + " ";
           }
           gridTostring += "\r\n";
       }
       

        return gridTostring;
    }
    
}
