package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class View extends Application {
    private class catcher implements ThrowListener{

        @Override
        public void Catch() {
            Board gameBoard = new Board();
            gameBoard.addThrowListener(myListener);
            GridPane layout = new GridPane();
            layout = gameBoard.setupGridPane();
            Scene scene = new Scene(layout, 300,300);
            gameStage.setScene(scene);
        }
    }
    Stage gameStage;
    catcher myListener = new catcher();


    @Override
    public void start(Stage primaryStage) throws Exception{
        gameStage = primaryStage;
        primaryStage.setTitle("Tic Tac Toe");
        Board gameBoard = new Board();
        gameBoard.addThrowListener(myListener);
        GridPane layout = gameBoard.setupGridPane();
        Scene scene = new Scene(layout, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
