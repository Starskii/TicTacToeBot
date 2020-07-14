package view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

interface ThrowListener {
    public void Catch();
}

public class Board {
    private int currentPlayer = 1;
    private int gameStatus = 0;
    View theView = new View();
    List<ThrowListener> listeners = new ArrayList<ThrowListener>();
    public void addThrowListener(ThrowListener toAdd){ listeners.add(toAdd); }
    //Set of functions that Throw Events.

    public void Throw(){ for (ThrowListener hl : listeners) hl.Catch();
        System.out.println("Something thrown");
    }

    private class MyButton extends Button implements ActionListener {
        private int status = 0;
        private int player = 0;
        private int row;
        private int col;

        MyButton(int row, int col){
            this.row = row;
            this.col = col;
        }
        public int getRow(){
            return this.row;
        }
        public int getCol(){
            return this.col;
        }
        public int getStatus(){
            return status;
        }
        public void setStatus(int newStatus){
            status = newStatus;
        }
        public int getPlayer(){
            return player;
        }
        public void setPlayer(int newPlayer){
            player = newPlayer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
    public void updatePlayer(){
        currentPlayer = (-currentPlayer);
    }

    public int[][] getGameBoard(GridPane layout){
        int[][] gameBoard = new int[3][3];
        for(Node b : layout.getChildren()){
            if(b instanceof MyButton){
                MyButton currentButton = ((MyButton)b);
                gameBoard[currentButton.getRow()][currentButton.getCol()] = currentButton.getPlayer();
            }
        }
        return gameBoard;
    }
    public int checkWin(GridPane layout){
        int[][] gameBoard = getGameBoard(layout);
        boolean currentPlayerWon = false;
        //Checks vertical
        for(int i = 0; i < 3; i++){
            if(gameBoard[i][0] == currentPlayer && gameBoard[i][1] == currentPlayer && gameBoard[i][2] == currentPlayer){
                currentPlayerWon = true;
            }
        }
        //Checks horizontal
        for(int i = 0; i < 3; i++){
            if(gameBoard[0][i] == currentPlayer && gameBoard[1][i] == currentPlayer && gameBoard[2][i] == currentPlayer){
                currentPlayerWon = true;
            }
        }
        //Checks diagonal
        if(gameBoard[0][0] == currentPlayer && gameBoard[1][1] == currentPlayer && gameBoard[2][2] == currentPlayer ||
                gameBoard[2][0] == currentPlayer && gameBoard[1][1] == currentPlayer && gameBoard[0][2] == currentPlayer){
            currentPlayerWon = true;
        }
        if(currentPlayerWon){
            gameStatus = currentPlayer;
        }
        return gameStatus;
    }
    GridPane setupGridPane(){
        GridPane layout = new GridPane();
        layout.setHgap(0);
        layout.setVgap(0);
        int col = -1;
        int row = 0;
        for(int i = 0; i < 9; i++){
            if(i%3 == 0) {
                col++;
                row = 0;
            }
            layout.add(new MyButton(row,col),row,col,1,1);
            row++;
        }
        for(Node b: layout.getChildren()){
            if(b instanceof MyButton){
                MyButton currentButton = ((MyButton)b);
                currentButton.setPrefSize(100,100);
                currentButton.setOnAction(actionEvent -> {
                    MyButton pressedButton = (MyButton)b;
                    if(pressedButton.getStatus() == 0){
                        pressedButton.setStatus(1);
                        pressedButton.setPlayer(currentPlayer);
                        if(currentPlayer == 1){
                            pressedButton.setText("X");
                        }else{
                            pressedButton.setText("O");
                        }
                        if(checkWin(layout) != 0){
                            Throw();
                        }
                        updatePlayer();
                    }
                });
            }
        }
        return layout;
    }
}
