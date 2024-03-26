package com.my.akniga;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {

    private Context context;
    private ArrayList<Book> books;

    public BookAdapter(Context context, ArrayList<Book> books) {
        super(context, R.layout.itembook, books);
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.itembook, parent, false);

        TextView style = view.findViewById(R.id.style);
        style.setText(this.books.get(position).getStyle());

        TextView reader = view.findViewById(R.id.reader);
        reader.setText(this.books.get(position).getReader());

        TextView duration = view.findViewById(R.id.duration);
        duration.setText(this.books.get(position).getDuration());

        TextView bookName = view.findViewById(R.id.bookName);
        bookName.setText(this.books.get(position).getName());

        TextView bookAuthor = view.findViewById(R.id.bookAuthor);
        bookAuthor.setText(this.books.get(position).getAuthor());

        ImageView imageView = view.findViewById(R.id.coverBook);
        Picasso.get().load(this.books.get(position).getBookCover()).into(imageView);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oneBook =new Intent(context, OneBook.class);
                oneBook.putExtra(Book.class.getSimpleName(), books.get(position));
                context.startActivity(oneBook);
                Toast.makeText(context, books.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
/*
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, books.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
*/
        return view;
    }

}
