package uta.ubs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by shantan on 2/14/2018.
 */

public class MarketPlace extends AppCompatActivity {

    ImageButton list_item;
    ImageButton buy_item;
    ImageButton lend_item;
    ImageButton exchange_item;
    Button myitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketplace);
        list_item = (ImageButton) findViewById(R.id.list_item);
        buy_item = (ImageButton) findViewById(R.id.sell_item);
        lend_item = (ImageButton) findViewById(R.id.lend_item);
        exchange_item = (ImageButton) findViewById(R.id.exchange_item);
        myitems = (Button) findViewById(R.id.myitems);

        list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(getApplicationContext(), ListItemPage.class);
                startActivity(next);
            }
        });

        buy_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(getApplicationContext(), SellActivity.class);
                startActivity(next);
            }
        });

        lend_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(getApplicationContext(), LendActivity.class);
                startActivity(next);
            }
        });

        exchange_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(getApplicationContext(), ExchangeActivity.class);
                startActivity(next);
            }
        });

        myitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(getApplicationContext(), MyItemsActivity.class);
                startActivity(next);
            }
        });
    }
}
