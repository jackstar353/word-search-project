export class Grid {
  constructor() {
    this.wordSelectMode = false;
    this.selectedItems = [];
  }
  renderGrid(gridSize, wordGrid) {
    var gridArea = document.getElementsByClassName("grid-area")[0];
    if (gridArea.lastChild) {
      gridArea.removeChild(gridArea.lastChild);
    }
    // console.log(gridArea);
    var tbl = document.createElement("table");
    var tblBody = document.createElement("tbody");
    let index = 0;
    for (var i = 0; i < gridSize; i++) {
      var row = document.createElement("tr");

      for (var j = 0; j < gridSize; j++) {
        var cell = document.createElement("td");
        let letter = wordGrid[index++];
        var cellText = document.createTextNode(letter);
        cell.appendChild(cellText);
        cell.setAttribute("data-x", i);
        cell.setAttribute("data-y", j);
        cell.setAttribute("data-letter", letter);

        row.appendChild(cell);
      }

      tblBody.appendChild(row);
    }

    tbl.appendChild(tblBody);
    gridArea.appendChild(tbl);

    //onclick handlers
    gridArea.addEventListener("mousedown", (event) => {
      this.wordSelectMode = true;
    });
    gridArea.addEventListener("mousemove", (event) => {
      if (this.wordSelectMode) {
        const cell = event.target;
        event.target.classList.add("selected");
        let x = cell.getAttribute("data-x");
        let y = cell.getAttribute("data-y");
        let letter = cell.getAttribute("data-letter");
        if (this.selectedItems.length) {
          const lastSelectedItem =
            this.selectedItems[this.selectedItems.length - 1];
          if (lastSelectedItem.x === x && lastSelectedItem.y === y) return;
        }

        this.selectedItems.push({
          x,
          y,
          letter,
          cell,
        });
        console.log(this.selectedItems);
      }
    });
    gridArea.addEventListener("mouseup", (event) => {
      this.wordSelectMode = false;
      this.selectedItems.forEach((item) =>
        item.cell.classList.remove("selected")
      );
    });
  }
}
