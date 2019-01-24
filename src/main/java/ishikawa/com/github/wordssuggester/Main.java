package ishikawa.com.github.wordssuggester;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
            primaryStage.setTitle("WordsSuggesterFromTwitter");
            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*参考文献
http://twitter4j.org/ja/index.html
http://satoyashiki.hatenablog.com/entry/2015/12/24/180416
http://www.techscore.com/blog/2012/11/29/%E6%96%87%E5%AD%97%E5%88%97%E7%B5%90%E5%90%88-java%E7%B7%A8/
http://papiroidsensei.com/memo/java_map_sort.html
http://pineplanter.moo.jp/non-it-salaryman/2018/04/01/javafx-tableview/
*/
