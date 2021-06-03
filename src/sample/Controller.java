package sample;

import java.net.URL;
import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

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
        start_button.setOnAction(event -> {
            Map<String, Integer> wordList = Parser.getWordsFromUrl(url_textField.getText());
            if(wordList == null){
                out_textArea.setText("Не удаётся получить веб-страницу. Проверьте корректность введённого url-адреса.");
            } else {
                StringBuilder result = new StringBuilder();
                Set<String> keys = wordList.keySet();
                for (String str : keys) {
                    result.append(str + " --> " + wordList.get(str) + "\n");
                }
                out_textArea.setText(result.toString());
            }
        });
    }
}

