package ishikawa.com.github.wordssuggester;

public class Table {

    private String words;
    private String amount;

    public Table(String words, String amount) {
        this.words = words;
        this.amount = amount;
    }


    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}