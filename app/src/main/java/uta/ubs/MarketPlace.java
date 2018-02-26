package uta.ubs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by shantan on 2/14/2018.
 */

public class MarketPlace extends AppCompatActivity {

    Button list_item;
    Button buy_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketplace);
        list_item = (Button) findViewById(R.id.list_item);
        buy_item = (Button) findViewById(R.id.sell_item);

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
    }
}
