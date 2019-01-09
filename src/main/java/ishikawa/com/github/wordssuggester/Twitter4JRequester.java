package ishikawa.com.github.wordssuggester;

import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

public class Twitter4JRequester {

    private static final int count = 1000;

    public List<String> SearchTweets(String keyword) {
        Twitter twitter = new TwitterFactory().getInstance();
        Query query = new Query();

        //検索キーワードを設定
        query.setQuery(keyword);

        //検索ツイート数を設定
        query.setCount(count);

        QueryResult result = null;

        try {
            result = twitter.search(query);
        } catch (TwitterException e) {
            //検索結果が見つからないときの処理
            System.out.println("error");
            return null;
        }

        List<String> tweets = new ArrayList<>();

        for (Status status : result.getTweets()) {
            tweets.add(status.getText());
        }

        System.out.println("検索件数 " + result.getCount());

        return tweets;
    }
}
