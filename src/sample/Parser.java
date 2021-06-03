package sample;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.rmi.UnknownHostException;
import java.util.*;

public class Parser {
    private static Document getPage(String urlString) {
        Document page = null;
        try {
            page = Jsoup.parse(new URL(urlString), 5000);
        } catch (UnknownHostException e){

        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

    private static String deleteSpliters(String text, String[] spliters){
        StringBuilder sb = new StringBuilder(text);
        for(String spliter : spliters){
            int i;
            while((i = sb.indexOf(spliter)) != -1){
                sb.setCharAt(i, ' ');
            }
        }
        return sb.toString();
    }

    public static Map<String, Integer> getWordsFromUrl(String url) {
//        String[] spliter = {",", ".", "\"", "'", ";", ":", "!", "#", "(", ")", "[", "]", "{", "}", "?", "/", "-", "+", "=", "*", "«", "»", "—", "%", "©"};
        Document page = getPage(url);
        if (page == null){
            return null;
        }
        String textBegin = page.body().text();
        String text = textBegin.replaceAll("\\d-[а-я]+", " ");
        String textWithoutNum = text.replaceAll("[0-9]", " ");
        String textPure = textWithoutNum.replaceAll("[^\\da-zA-Zа-яёА-ЯЁ ]", " ");

        String[] words = textPure.split("\\s+");
        Map<String, Integer> wordList = new TreeMap<>();
        for(String word : words){
            String str = word.toUpperCase();
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
