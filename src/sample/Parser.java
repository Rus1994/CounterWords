package sample;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/*
Класс, подсчитывающий количество уникальных слов на веб-странице
MIN_LENGTH_WORD - минимально необходимая длина слов, для добавления в список
TIMEOUT_ANSWER - время ожидания ответа в миллисекундах
 */
public class Parser extends Observable {
    private final int MIN_LENGTH_WORD = 2;
    private final int TIMEOUT_ANSWER = 5000;
    private final String MESSAGE_ERROR = "Не удаётся получить веб-страницу. Проверьте соединение и корректность введённого url-адреса.";

    /*
    Получить Map(key - уникальное слово, value - количество повторений) по url-адресу
     */
    public Map<String, Integer> getWordsFromUrl(String url) {
        Document page = getPage(url);
        if (page == null) {
            return null;
        }
        saveHTML(page);

        String textBegin = page.body().text();
        String text = textBegin.replaceAll("\\d-[а-я]+", "");
        String textPure = text.replaceAll("[^a-zA-Zа-яёА-ЯЁ ]", " ");

        String[] words = textPure.split("\\s+");
        Map<String, Integer> wordList = new TreeMap<>();
        for (String word : words) {
            String str = word.toUpperCase();
            if (str.length() < MIN_LENGTH_WORD) {
                continue;
            }
            if (wordList.containsKey(str)) {
                wordList.replace(str, 1 + wordList.get(str));
            } else {
                wordList.put(str, 1);
            }
        }
        return wordList;
    }

    /*
    Получить Document по url-адресу
    return Document
     */
    private Document getPage(String urlString) {
        Document page = null;
        try {
            page = Jsoup.parse(new URL(urlString), TIMEOUT_ANSWER);
        } catch (IOException e) {
            showMessage(MESSAGE_ERROR);
        }
        return page;
    }

    /*
    Отобразить сообщение в объекте Наблюдателя (Observer)
     */
    private void showMessage(String msg) {
        setChanged();
        notifyObservers(msg);
    }

    /*
    Сохранение файла Document в формате HTML
     */
    private void saveHTML(Document doc) {
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MM_dd__hh_mm_ss");
            String nameFile = "Page_" + sdf.format(date) + ".html";
            PrintWriter printWriter = new PrintWriter(nameFile);
            printWriter.println(doc.html());
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
