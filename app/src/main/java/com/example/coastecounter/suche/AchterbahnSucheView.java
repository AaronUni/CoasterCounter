package com.example.coastecounter.suche;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.coastecounter.R;
import com.example.coastecounter.achterbahn.AchterbahnDB;
import com.example.coastecounter.achterbahn.AchterbahnView;
import com.example.coastecounter.dashboard.DashboardView;
import com.example.coastecounter.park.ParkSuchenView;

import java.util.ArrayList;

public class AchterbahnSucheView extends AppCompatActivity {

    ArrayList<String> myList;   //Gesamtliste der Achterbahnen (des Parks)
    ArrayAdapter adapter;   //erzeugt für jeden Eintrag eine eigene View Ansicht
    SearchView searchView;  //Suchleiste


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achterbahnsuchen); //wechselt zum Achterbahnsuchen Layout
        setUpToolbar(); //setzt Toolbar
        initializeSearch(); //setzt Suche
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Öffnet das Menü und befüllt dieses mit Items, wenn welche vorhanden sind
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    private void setUpToolbar() {
        //erstellt die Toolbar mit passender Überschrift und steuert den Zurück-Pfeil
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar); //setzt Toolbar
        setTitle(R.string.string_achterbahnsuchen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //setzt Zurück-Pfeil
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //Aktion bei Drücken des Zurückpfeils
                Intent intent = new Intent(AchterbahnSucheView.this, DashboardView.class); //setzt nächste View
                Bundle b = getIntent().getExtras(); //lädt das Bundle
                intent.putExtras(b);    //übergibt das Bundle
                startActivity(intent);  //Viewwechsel
            }
        });
    }

    private void initializeSearch() {
        //erstellt die Suche
        AchterbahnDB achterbahnDB = new AchterbahnDB(); //lädt Achterbahndatenbank
        //erzeugt Liste mit den Achterbahnen (des ausgewählten Parks)
        myList = achterbahnDB.getListByName((String) getIntent().getExtras().get("Parkname"));
        //erzeugt ArrayAdapter nach Vorlage für die Liste der Achterbahnen
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, myList);
        final ListView listView = findViewById(R.id.listview_achterbahnsuche); //erzeugt ListView
        listView.setAdapter(adapter);   //übergibt den ArrayAdapter der ListView
        searchView = findViewById(R.id.searchview_achterbahnsuchen);    //setzt Suchleiste
        ParkSuchenView.listenerErstellen(searchView, myList, adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  //Aktion bei Auswahl einer Achterbahn
                Intent intent = new Intent(AchterbahnSucheView.this, AchterbahnView.class); //setzt nächste View
                Bundle b = getIntent().getExtras(); //lädt übergebenes Bundle
                b.putString("id", (String) parent.getItemAtPosition(position)); //fügt dem Bundle die Info id hinzu
                intent.putExtras(b);    //übergibt das Bundle
                startActivity(intent);  //Viewwechsel
            }
        });
    }
}
