package com.my.akniga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OneBook extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_book);
        Document document;
        Bundle arguments = getIntent().getExtras();
        Book book;
        if (arguments != null) {
            book = (Book) arguments.getSerializable(Book.class.getSimpleName());

            String url = book.getLink();
            new GetURLData().execute(url);
            ImageView cover =  findViewById(R.id.bookCover);
            Picasso.get().load(book.getBookCover()).into(cover);
            ((TextView) findViewById(R.id.bookName)).setText(book.getName());
            ((TextView) findViewById(R.id.authorBookSingle)).setText(book.getAuthor());
            ((TextView) findViewById(R.id.readerBookSingle)).setText(book.getReader());
            ((TextView) findViewById(R.id.durationBookSingle)).setText(book.getDuration());
            ((TextView) findViewById(R.id.descriptBookSingle)).setText(book.getDesc());
        }
    }

    private class GetURLData extends AsyncTask<String, String, String> {

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

            super.onPostExecute(result);
            Document document = Jsoup.parse(result);
            Elements elements = document.getElementsByClass("book--table");
            //for(Element element : elements){
                //String bookCover = getString(R.string.mainHost) + element.getElementsByTag("img").get(0).attr("src").toString();
                //ImageView iv = findViewById(R.id.bookCover);
                //Picasso.get().load(bookCover).into(iv);
                //System.out.println(bookCover);
            //}

        }
    }
}