package jp.ac.chitose.colloquial_checker.data;


public class History {
    private String colloquialName;
    private int colloquialCount;
    private String colloquialDate;


    public History() {
        this.colloquialName = "";
        this.colloquialCount = 0;
        this.colloquialDate = "";
    }

    public History(String colloquialName, int colloquialCount, String colloquialDate) {
        this.colloquialName = colloquialName;
        this.colloquialCount = colloquialCount;
        this.colloquialDate = colloquialDate;
    }


    public String getColloquialName() {
        return colloquialName;
    }

    public void setColloquialName(String colloquialName) {
        this.colloquialName = colloquialName;
    }

    public int getColloquialCount() {
        return colloquialCount;
    }

    public void setColloquialCount(int colloquialCount) {
        this.colloquialCount = colloquialCount;
    }

    public String getColloquialDate() {
        return colloquialDate;
    }

    public void setColloquialDate(String colloquialDate) {
        this.colloquialDate = colloquialDate;
    }
}



