package application.helper;

public class CategorySearchModel {
    private String searchText;
    private long id;

    private String inputString = "";

    public void reset() {
        this.inputString = null;
        this.id = 0;
    }

    public String[] getKeywords() {
        String[] keywords = this.inputString.split("[, ]");

        for (int i = 0; i < keywords.length; i++) {
            if (keywords[i].length() > 0) {
                keywords[i] = "%" + keywords[i] + "%";
            }
        }

        return keywords;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
