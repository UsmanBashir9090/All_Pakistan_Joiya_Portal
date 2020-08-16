package com.example.membership;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        Button plcord = (Button) findViewById(R.id.orders);
        plcord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startplcord = new Intent(getApplicationContext(), pending_list.class);
                startActivity(startplcord);
            }
        });

        Button cord = (Button) findViewById(R.id.dispatched_orders);
        cord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startplcord = new Intent(getApplicationContext(), approved_list.class);
                startActivity(startplcord);
            }
        });






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.admin, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected( MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuLogout:

                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, Login.class) );

                break;
        }

        return true;
    }

}