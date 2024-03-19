import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;

public class App extends Application {
    private Cell[][] grid = new Cell[5][5];
    private Label turnLabel = new Label("X's turn");
    private char turn = 'X';

    @Override
    public void start(Stage window) throws Exception{
        GridPane layout = new GridPane(); 

        //Creating the board
        for(int i = 0; i < 5; i++) 
        {
            for(int j = 0; j < 5; j++)
            {
                layout.add(grid[i][j] = new Cell(), j, i);
            }
        }
    
        BorderPane border = new BorderPane();
        border.setCenter(layout);
        border.setBottom(turnLabel);

        Scene scene = new Scene(border, 500, 500);
        window.setTitle("TicTacToe");
        window.setScene(scene);
        window.show();
      }

  public class Cell extends Pane { //Class for each cell inside the grid
    private char token = ' '; //Fills each cell with a space initially
    public Cell() {
      setStyle("-fx-border-color: black"); 
      this.setPrefSize(800, 800);
      this.setOnMouseClicked(e -> handleMouseClick());
    }

  public char getToken() { //Returns token
      return token;
  }

public boolean isFull()
{
  for(int i = 0; i < 5; i++)
  {
    for(int j = 0; j < 5; j++)
    {
        if(grid[i][j].getToken() == ' ')
        {
          return false; //Cell is not full
        }
    }
  }
  return true; //If the cell doesn't have a space, then it is full
}

public boolean isWon(char token){
  for(int i = 0; i < 5; i++)
  {
    if(grid[i][0].getToken() == token && grid[i][1].getToken() == token && grid[i][2].getToken() == token && grid[i][3].getToken() == token && grid[i][4].getToken() == token) //5 in a row vertically in any column
    {
      return true;
    }
  }

  for(int j = 0; j < 5; j++)
  if(grid[0][j].getToken() == token && grid[1][j].getToken() == token && grid[2][j].getToken() == token && grid[3][j].getToken() == token && grid[4][j].getToken() == token) //5 in a row horizontally in any row
  {
    return true;
  }

  if(grid[0][0].getToken() == token && grid[1][1].getToken() == token && grid[2][2].getToken() == token && grid[3][3].getToken() == token && grid[4][4].getToken() == token) //5 in a row diagonally from top left to bottom right
  {
    return true;
  }

  if(grid[4][0].getToken() == token &&grid[3][1].getToken() == token && grid[2][2].getToken() == token && grid[1][3].getToken() == token && grid[0][4].getToken() == token) //5 in a row diagonally from top right to bottom left
  {
    return true;
  }
  return false; //If none of the if statements are true, nobody has won 
}

public void setToken(char c) {
  token = c;
  double cellWidth = this.getWidth();
  double cellHeight = this.getHeight(); 

  if (token == 'X') { //Creating the X with two lines crisscrossing
    Line line1 = new Line(10, 10, cellWidth - 10, cellHeight - 10);
    line1.setEndX(cellWidth - 10);
    line1.setEndY(cellHeight - 10);

    Line line2 = new Line(10, cellHeight - 10, cellWidth - 10, 10);
    line2.setStartY(cellHeight - 10);
    line2.setEndX(cellWidth - 10);

    this.getChildren().addAll(line1, line2);
  }

  else if (token == 'O') { //Creating the O
    Circle circle = new Circle();
    circle.setCenterX(cellWidth / 2);
    circle.setCenterY(cellHeight / 2);
    circle.setRadius(cellWidth / 4);
    circle.setFill(Color.PINK);

    getChildren().add(circle);
    }
}

private void handleMouseClick() //When user clicks on a cell
{
  if(token == ' ' && turn != ' ') //If the cell is empty and the game isn't over
  {
    setToken(turn); //Play the game
  }

  if(isWon(turn)) //If somebody wins
  {
    turnLabel.setText(turn + " won! Game over.");
    turn = ' '; //Signifies game is over
  }

  else if(isFull()) //If board is full and nobody has won
  {
    turnLabel.setText("Cat's eye! Draw.");
    turn = ' ';
  }

  else{
    turn = (turn == 'X') ? 'O' : 'X'; //If it's X's turn, then change it to O; if it's not X's turn then change it to X
    turnLabel.setText(turn + "'s turn");
  }
}

  public static void main(String[] args) {
    launch(args);
  }

}
}