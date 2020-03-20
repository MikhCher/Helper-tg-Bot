import com.vdurmont.emoji.EmojiParser;

public enum Smile {
    WINK(":wink:"),
    RELIEVED(":relieved:"),
    X(":x:"),
    SELECT(":white_check_mark:");

    private String title;

    Smile(String title) {
        this.title = title;
    }

    public String get() {
        return EmojiParser.parseToUnicode(title);
    }
}
