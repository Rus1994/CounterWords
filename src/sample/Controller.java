package sample;


import java.net.URL;
import java.util.*;
import java.util.Observer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller implements Observer {
    Parser parser;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button start_button;

    @FXML
    private TextField url_textField;

    @FXML
    private TextArea out_textArea;

    @FXML
    void initialize() {
        parser = new Parser();
        parser.addObserver(this);

        start_button.setOnAction(event -> {
            Map<String, Integer> wordList = parser.getWordsFromUrl(url_textField.getText());
            if(wordList != null){
                StringBuilder result = new StringBuilder();
                Set<String> keys = wordList.keySet();
                for (String str : keys) {
                    result.append(str + " --> " + wordList.get(str) + "\n");
                }
                out_textArea.setText(result.toString());
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        out_textArea.appendText(arg + "\n");
    }
}

