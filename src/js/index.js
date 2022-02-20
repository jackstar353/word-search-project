import { Grid } from "./grid";

const GRID_SIZE =10;
const submitWordBtn = document.querySelector(".submit-word");
const grid = new Grid();


submitWordBtn.addEventListener("click", async() =>{
    let result = await fetchGridInfo(["ONE","TWO","THREE"]);
    grid.renderGrid(GRID_SIZE, result);
});

async function fetchGridInfo(wordList){
    const commaSeperatedWords = wordList.join(",");
    let response = await fetch(`http://localhost:8080/wordgrid?gridSize=${GRID_SIZE}&wordList=${commaSeperatedWords}`);
    let result = await response.text();
    return result.split(" ");
}