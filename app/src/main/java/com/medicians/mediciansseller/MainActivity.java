package com.medicians.mediciansseller;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.medicians.mediciansseller.NavigationTabs.DuePayment;
import com.medicians.mediciansseller.NavigationTabs.Statement;
import com.medicians.mediciansseller.NavigationTabs.SwipeTabs;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String[] list={"Tabs","Order History","Due Payments"};
    ArrayAdapter<String> listadapter;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView=(ListView)findViewById(R.id.listNav);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);

        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);


        listadapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(listadapter);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        preferences=this.getSharedPreferences(Login.PREF,MODE_PRIVATE);
        editor=preferences.edit();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayFragment(position);
            }
        });


    }

    private void displayFragment(int pos){
        Fragment fragment=null;
        switch (pos){
            case 0: fragment=new SwipeTabs();
                    break;
            case 1: fragment=new Statement();
                    break;
            case 2: fragment=new DuePayment();
        }
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container_body,fragment)
        .commit();

        drawerLayout.closeDrawer(listView);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id==R.id.logout){

            editor.putString("username",null);
            editor.putString("sellerid",null);
            editor.commit();

            startActivity(new Intent(MainActivity.this, Login.class));

            finish();

        }

        return super.onOptionsItemSelected(item);
    }
}
