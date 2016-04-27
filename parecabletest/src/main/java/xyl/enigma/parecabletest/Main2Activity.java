package xyl.enigma.parecabletest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView tv = (TextView) findViewById(R.id.textView);
        TextView tv2 = (TextView) findViewById(R.id.textView2);
        TextView tv3 = (TextView) findViewById(R.id.textView3);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bun = intent.getExtras();
            ArrayList<Book> bookList = bun.getParcelableArrayList("list");
            if (bookList != null && bookList.size() > 0) {
                Book book1 = bookList.get(0);
                Book book2 = bookList.get(1);
                Book book3 = bookList.get(2);

                tv.setText(book1.getBookName()+book1.getAuthor()+book1.getPublishDate());
                tv2.setText(book2.getBookName()+book2.getAuthor()+book2.getPublishDate());
                tv3.setText(book3.getBookName()+book3.getAuthor()+book3.getPublishDate());
            }
        }

    }

}
