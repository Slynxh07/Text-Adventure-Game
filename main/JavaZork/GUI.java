import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GUI extends Application /* implements Runnable */ {

    final private static ZorkULGame game = new ZorkULGame();

    @Override
    public void start(Stage stage) {
        Button north = new Button("North");
        Button south = new Button("South");
        Button east = new Button("East");
        Button west = new Button("West");

        HBox topBox = new HBox(north);
        topBox.setAlignment(Pos.CENTER);

        HBox bottomBox = new HBox(south);
        bottomBox.setAlignment(Pos.CENTER);

        VBox leftBox = new VBox(west);
        leftBox.setAlignment(Pos.CENTER);

        VBox rightBox = new VBox(east);
        rightBox.setAlignment(Pos.CENTER);

        north.setOnAction(e -> System.out.println("north"));
        south.setOnAction(e -> game.goRoom(new Command("go", "south")));
        east.setOnAction(e -> System.out.println("east"));
        west.setOnAction(e -> System.out.println("west"));

        BorderPane bp = new BorderPane();
        bp.setTop(topBox);
        bp.setBottom(bottomBox);
        bp.setRight(rightBox);
        bp.setLeft(leftBox);

        Scene mainScene = new Scene(bp, 300, 200);
        stage.setTitle("Button Test");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        Thread gameThread = new Thread(game);
        gameThread.start();
        launch();
    }
}