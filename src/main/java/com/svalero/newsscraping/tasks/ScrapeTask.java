package com.svalero.newsscraping.tasks;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ScrapeTask extends Task<Void> {
    private final Document doc;
    private final ArrayList<String> words;
    private final Label lblWordCount;

    public ScrapeTask(Document doc, ArrayList<String> words, Label lblWordCount) {
        this.doc = doc;
        this.words = words;
        this.lblWordCount = lblWordCount;
    }

    @Override
    protected Void call() {
        int wordCount = 0;
        System.out.println(doc.title());
        Elements paragraphs = doc.select("p");
        System.out.println(paragraphs.size());
        HashMap<String, Integer> wordCounts = new HashMap<>();
        for (String word : words) {
            updateMessage(word);
            updateProgress(0, 1);
            int paragraphCount = 0;
            for (Element paragraph : paragraphs) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                String[] paragraphWords = paragraph.text().toLowerCase().split("\\s+|[,.!?;:]");
                for (String paragraphWord : paragraphWords) {
                    if (paragraphWord.contains(word.toLowerCase())) {
                        wordCount++;
                    }
                }
                updateProgress(++paragraphCount, paragraphs.size());
                wordCounts.put(word, wordCount);

                String labelText = wordCounts.entrySet()
                        .stream()
                        .map(entry -> entry.getKey() + ": " + entry.getValue())
                        .collect(Collectors.joining("\n"));
                Platform.runLater(() -> {
                    lblWordCount.setText(labelText);
                });
            }
            System.out.println(word + ": " + wordCount);
        }

        return null;
    }
}
