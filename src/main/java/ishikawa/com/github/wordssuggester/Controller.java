package ishikawa.com.github.wordssuggester;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import net.moraleboost.mecab.Lattice;
import net.moraleboost.mecab.Node;
import net.moraleboost.mecab.impl.StandardTagger;

import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    @FXML private Button searchButton;
    @FXML private TextField keywordTextField;
    @FXML private TableView<Table> resultTableView;
    @FXML private TableColumn<Table, String> wordsColumn;
    @FXML private TableColumn<Table, String> amountColumn;
    private ObservableList<Table> data;

    @FXML
    public void onSearchButtonClicked(ActionEvent event) {
        String keyword = keywordTextField.getText();

        List<String> tweets;
        StringBuilder sumTweets = new StringBuilder();

        Twitter4JRequester requester = new Twitter4JRequester();
        tweets = requester.SearchTweets(keyword);

        if (tweets != null) {
            for (String tweet : tweets) {
                sumTweets.append("\r\n").append(tweet);
            }
        } else {
            System.out.println("検索結果がありません！");
        }

        StandardTagger tagger = new StandardTagger("");

        //Lattice（形態素解析に必要な実行時情報が格納されるオブジェクト）を構築
        Lattice lattice = tagger.createLattice();

        //解析対象文字列をセット
        String text = sumTweets.toString();
        lattice.setSentence(text);

        //tagger.parse()を呼び出して、文字列を形態素解析する。
        tagger.parse(lattice);

        //名詞をカウントする
        Node node = lattice.bosNode();
        Map<String, Integer> words = new HashMap<>();
        while (node != null) {
            if (node.feature().split(",")[0].equals("名詞")) {
                if (words.keySet().contains(node.surface())) {
                    words.put(node.surface(), words.get(node.surface())+1);
                } else {
                    words.put(node.surface(), 1);
                }
            }
            node = node.next();
        }

        // lattice, taggerを破壊
        lattice.destroy();
        tagger.destroy();

        //出現頻度の高い順にソートする
        List<Map.Entry<String,Integer>> entries = new ArrayList<>(words.entrySet());
        entries.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        /*for (Map.Entry<String,Integer> s : entries) {
            System.out.println(s.getKey() + ": " + s.getValue());
        }*/

        displayResults(entries);
    }

    public void displayResults(List<Map.Entry<String,Integer>> result) {
        data.clear();
        for (Map.Entry<String,Integer> s : result) {
            data.addAll(new Table(s.getKey(), String.valueOf(s.getValue())));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = FXCollections.observableArrayList();
        resultTableView.itemsProperty().setValue(data);
        resultTableView.setItems(data);

        wordsColumn.setCellValueFactory(new PropertyValueFactory<>("words"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }
}
