package sample;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Parser extends Observable{
    private Document getPage(String urlString) {
        Document page = null;
        try {
            page = Jsoup.parse(new URL(urlString), 5000);
        } catch (IOException e) {
            showMessage("Не удаётся получить веб-страницу. Проверьте соединение и корректность введённого url-адреса.");
        }

        return page;
    }

    private void showMessage(String msg){
        setChanged();
        notifyObservers(msg);
    }

    public Map<String, Integer> getWordsFromUrl(String url) {
        Document page = getPage(url);
        if (page == null){
            return null;
        }
        String textBegin = page.body().text();
        String text = textBegin.replaceAll("\\d-[а-я]+", "");
        String textPure = text.replaceAll("[^a-zA-Zа-яёА-ЯЁ ]", " ");

        String[] words = textPure.split("\\s+");
        Map<String, Integer> wordList = new TreeMap<>();
        for(String word : words){
            String str = word.toUpperCase();
            if(str.length() <= 1){
                continue;
            }
            if(wordList.containsKey(str)){
                Integer count = wordList.get(str);
                wordList.replace(str, count+1);
            } else {
                wordList.put(str, 1);
            }
        }
        return wordList;
    }
}
