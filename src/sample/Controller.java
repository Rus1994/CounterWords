package sample;

import java.net.URL;
import java.util.*;
import java.util.Observer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/*
Класс, управляющий графическим пользовательским интерфейсом
urlTextField - поле ввода url-адреса
outTextArea - область вывода результатов
startButton - кнопка запуска подсчёта и вывода статистики
 */

public class Controller implements Observer {
    private Parser parser;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button startButton;

    @FXML
    private TextField urlTextField;

    @FXML
    private TextArea outTextArea;

    /*
    Вывод сообщений от наблюдаемого объекта (Observable) Parser parser
     */
    @Override
    public void update(Observable o, Object arg) {
        outTextArea.appendText(arg + "\n");
    }

    @FXML
    void initialize() {
        parser = new Parser();
        parser.addObserver(this);

        startButton.setOnAction(event -> {
            Map<String, Integer> wordList = parser.getWordsFromUrl(urlTextField.getText());
            if (wordList != null) {
                StringBuilder result = new StringBuilder();
                Set<String> keys = wordList.keySet();
                for (String str : keys) {
                    result.append(str + " --> " + wordList.get(str) + "\n");
                }
                outTextArea.setText(result.toString());
            }
        });
    }
}
