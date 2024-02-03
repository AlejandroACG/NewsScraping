package com.svalero.newsscraping;
import com.svalero.newsscraping.tasks.ScrapeTask;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import org.jsoup.nodes.Document;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ScrapeController implements Initializable {
    private final Document doc;
    private final ArrayList<String> words;
    @FXML
    private Label lblCurrentWord;
    @FXML
    private ProgressBar pbProgress;
    @FXML
    private Label lblWordCount;

    public ScrapeController(Document doc, ArrayList<String> words) {
        this.doc = doc;
        this.words = words;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ScrapeTask scrapeTask = new ScrapeTask(doc, words, lblWordCount);
        pbProgress.setProgress(0);
        pbProgress.progressProperty().bind(scrapeTask.progressProperty());
        pbProgress.progressProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.doubleValue() >= 1) {
                pbProgress.getStyleClass().remove("progress-bar");
                pbProgress.getStyleClass().add("progress-bar-complete");
            } else {
                pbProgress.getStyleClass().remove("progress-bar-complete");
                pbProgress.getStyleClass().add("progress-bar");
            }
        });
        scrapeTask.messageProperty().addListener((observable, oldValue, newValue) -> lblCurrentWord.setText(newValue));
        scrapeTask.setOnSucceeded(workerStateEvent -> {
            lblCurrentWord.setText("Done");
        });
        new Thread(scrapeTask).start();
    }
}
