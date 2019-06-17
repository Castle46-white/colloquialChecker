package jp.ac.chitose.colloquial_checker.data;

import java.util.ArrayList;
import java.util.List;

public class ColloquialAnswer {

    private Colloquy colloquy;  //話し言葉
    private List<ExampleSentence> exampleSentence = new ArrayList<ExampleSentence>(); //話しことばの例文

    public ColloquialAnswer(Colloquy colloquy, List<ExampleSentence> exampleSentence) {
        this.colloquy = colloquy;
        this.exampleSentence = exampleSentence;
    }

    public ColloquialAnswer() {
        this.colloquy = new Colloquy();
        this.exampleSentence = new ArrayList<>();
    }

    public Colloquy getColloquy() {
        return colloquy;
    }

    public void setColloquy(Colloquy colloquy) {
        this.colloquy = colloquy;
    }

    public List<ExampleSentence> getExampleSentence() {
        return exampleSentence;
    }

    public void addExmapleSentence(String sentence, String correctSentence) {
        exampleSentence.add(new ExampleSentence(sentence, correctSentence));
    }
}
