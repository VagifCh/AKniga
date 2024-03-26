package com.my.akniga;

import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText searchText;
    private TextView tv;
    private ImageButton searchButton;
    private int countBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchText = findViewById(R.id.searchText);
        searchButton = findViewById(R.id.searchButton);
        tv = findViewById(R.id.resSearch);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countBook = 0;
                if (v.getId() == R.id.searchText) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(v.getContext().INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                Document document = null;
                if (searchText.getText().toString().trim().equals(""))
                    Toast.makeText(MainActivity.this, R.string.hintSearch, Toast.LENGTH_LONG).show();
                else {
//                    String url = getString(R.string.mainHost) + "/search?q=" + searchText.getText().toString().trim();
                    String url = getString(R.string.mainHost);
                    new GetURLData().execute(url);
                }
            }
        });
        String url = getString(R.string.mainHost);
        new GetURLData().execute(url);

    }

    private class GetURLData extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            tv.setText("ожидайте...");
        }

        ArrayList<Book> books = new ArrayList<>();
        BookAdapter bookAdapter = new BookAdapter(MainActivity.this, books);

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", getString(R.string.userAgent));
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null)
                    buffer.append(line);
                return buffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            ArrayList<String> pages = new ArrayList<String>();
            String style = "";
            String author = "";
            String authorLink = "";
            String seria = "";
            String bookCover = "";
            String name = "";
            String reader = "";
            String readerLink = "";
            String duration = "";
            String link = "";
            String desc = "";
            super.onPostExecute(result);
            Document document = Jsoup.parse(result);
            System.out.println("-> Получена страница");
            Elements allBooks = document.getElementsByClass("content__main__articles--item");
            System.out.println("-> Всего книг: " + allBooks.size());
            for (Element oneBook : allBooks) {
                Elements e = oneBook.getElementsByTag("div");

                bookCover = getString(R.string.mainHostPic) +  e.get(1).getElementsByAttribute("src").get(0).attr("src"); //обложка
                name = e.get(1).getElementsByAttribute("src").get(0).attr("alt"); //название
                link = e.get(2).getElementsByAttribute("href").get(1).attr("href");  // ссылка на книгу
                //name = e.get(2).getElementsByAttribute("href").get(1).getElementsByTag("h2").get(0).text();   // автор + название
                authorLink = e.get(2).getElementsByAttribute("href").get(2).attr("href");      //   (ссылка на автора)
                author = e.get(2).getElementsByAttribute("href").get(2).text();  // автор
                readerLink = e.get(2).getElementsByAttribute("href").get(3).attr("href");  // ссылка на чтеца
                reader = e.get(2).getElementsByAttribute("href").get(3).text();  //чтец
                desc = e.get(2).getElementsByAttribute("class").get(9).text(); //описание
                duration = e.get(5).getElementsByAttribute("class").get(7).text();  //часы
                duration = duration + " " +e.get(5).getElementsByAttribute("class").get(8).text();  //минуты
//                for (Element ee : e) {
                System.out.println("-> ");
                System.out.println("Обложка: " + bookCover + "\n" + "Название: " + name + "\n" + "Автор: " + author + "\n" + "Серия: " + seria + "\n" + "\n" +
                        "Описание: " + desc + "Чтец: " + reader + "\n" + "Стиль: " + style + "\n" + "Время: " + duration + "\n" + "Ссылка: " + link + "\n==================\n");
                books.add(new Book(bookCover, name, author, seria, reader, style, duration, link, desc));
                countBook++;
//                }
            }
            if (!books.isEmpty()) {
//                System.out.println("Итого: " + countBook);
                tv.setText("Найдено: " + String.valueOf(countBook));

                ListView resultSearch = findViewById(R.id.resultSearch);
                resultSearch.setAdapter(bookAdapter);
/*
                resultSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Toast.makeText(getApplicationContext(), books.get(position).getName(), Toast.LENGTH_SHORT).show();
                    }


                });
*/
            }
        }
    }
}
