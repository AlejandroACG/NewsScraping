package com.svalero.newsscraping;
import com.svalero.newsscraping.utils.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.svalero.newsscraping.utils.Constants.URLS_PATH;
import static com.svalero.newsscraping.utils.Constants.WORDS_PATH;

public class AppController implements Initializable {
    private final Logger logger = LoggerFactory.getLogger(AppController.class);
    @FXML
    private Button btnLaunch;
    @FXML
    private TabPane tabPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    @FXML
    public void launchScrape(ActionEvent event) {
        Data data = new Data();
        ArrayList<String> urls = data.read(URLS_PATH);
        ArrayList<String> words = data.read(WORDS_PATH);

        for(String url : urls) {
            try {
                Document doc = Jsoup.connect(url).userAgent("Chrome/58.0.3029.110").get();
                Tab newTab = new Tab(doc.title());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("tab.fxml"));
                loader.setController(new ScrapeController(doc, words));
                newTab.setContent(loader.load());
                tabPane.getTabs().add(newTab);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
