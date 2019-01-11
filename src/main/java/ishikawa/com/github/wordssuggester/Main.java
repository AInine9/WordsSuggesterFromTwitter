package ishikawa.com.github.wordssuggester;

import net.moraleboost.mecab.Lattice;
import net.moraleboost.mecab.Node;
import net.moraleboost.mecab.impl.StandardTagger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("キーワード？");

        Scanner scanner = new Scanner(System.in);
        String keyword = scanner.next();

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

        for (Entry<String,Integer> s : entries) {
            System.out.println(s.getKey() + ": " + s.getValue());
        }
    }
}

/*参考文献
http://twitter4j.org/ja/index.html
http://satoyashiki.hatenablog.com/entry/2015/12/24/180416
http://www.techscore.com/blog/2012/11/29/%E6%96%87%E5%AD%97%E5%88%97%E7%B5%90%E5%90%88-java%E7%B7%A8/
http://papiroidsensei.com/memo/java_map_sort.html
*/
