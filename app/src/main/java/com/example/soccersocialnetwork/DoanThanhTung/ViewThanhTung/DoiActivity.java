package com.example.soccersocialnetwork.DoanThanhTung.ViewThanhTung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.soccersocialnetwork.DoanThanhTung.Adapter.ViewPagerAdapter;
import com.example.soccersocialnetwork.DoanThanhTung.DataBase.DBTeam;
import com.example.soccersocialnetwork.DoanThanhTung.FireBaseTeam;
import com.example.soccersocialnetwork.DoanThanhTung.Models.Team;
import com.example.soccersocialnetwork.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DoiActivity extends AppCompatActivity {

    //tao thanh nang, luot qua luot lai
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FireBaseTeam fireBaseTeam = new FireBaseTeam();
    private DatabaseReference mDatabase;
    private FirebaseStorage storage;

    private StorageReference storegaRef;

    ArrayList<Team> listTeam = new ArrayList<>();
    List<String> strings = new ArrayList<>();
    String uriIMG;
    ImageView imgDoi;
    TextView tvTenDoi;


    ///test
    DatabaseReference db;
    //FireBaseHelp helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Nhóm");
        actionBar.setDisplayHomeAsUpEnabled(true);

        setControl();
        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);

        Picasso.get().load(TaoDonActivity.uri).fit().into(imgDoi);
        tvTenDoi.setText(TaoDonActivity.tenDoi);
        setIcon();
        //Toast.makeText(this, fireBaseTeam.getListTeam().size() +"", Toast.LENGTH_SHORT).show();
        setEvent();

    }

//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//
//    }

    private void setEvent() {
        DBTeam dbTeam = new DBTeam(this);


        // listTeam = fireBaseTeam.readTeam_ONE();
//        uriIMG = listTeam.get(0).getHinhAnh();




        //vào firebase rồi add danh sách firebase vào sqlite
        mDatabase = FirebaseDatabase.getInstance().getReference("Team");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dt:
                        snapshot.getChildren()) {
                    Team team = dt.getValue(Team.class);
                    listTeam.add(team);
                  //  dbTeam.addTeam(team);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
//    private ArrayList<Team> getData(){
//        ArrayList<Team> listTeam = new ArrayList<>();
//        ArrayList<Team> a = new ArrayList<>();
//        fireBaseTeam.readTeam( a );
//        listTeam = fireBaseTeam.getListTeam();
//        return listTeam;
//    }


    public void readTeam() {
        mDatabase = FirebaseDatabase.getInstance().getReference("Team");
        final List<String> keys = new ArrayList<>();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dt :
                        snapshot.getChildren()) {
                    keys.add(dt.getKey());
                    Team team = dt.getValue(Team.class);
//                    team.setTenDoi(dt.child("tenDoi").getValue().toString());
//                    team.setEmail(dt.child("email").getValue().toString());
//                    team.setGioiThieu(dt.child("gioiThieu").getValue().toString());
//                    team.setIdDoi(Integer.parseInt(dt.child("idDoi").getValue().toString()));
//                    team.setsLogan(dt.child("sLogan").getValue().toString());
//                    team.setSdt(dt.child("sdt").getValue().toString());
//                    team.setTieuChi(dt.child("tieuChi").getValue().toString());
                    listTeam.add(team);

                    Toast.makeText(DoiActivity.this, listTeam.size() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(null, "loadPost:onCancelled", error.toException());
            }
        });
        imgDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(DoiActivity.this, keys.size() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment_Doi(), "");
        adapter.addFragment(new Fragment_Doi_2(), "");
        adapter.addFragment(new Fragment_Doi_Menu(), "");

        viewPager.setAdapter(adapter);

    }

    private void setIcon() {
        tabLayout.getTabAt(0).setIcon(R.drawable.icon_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon_delicious);
        tabLayout.getTabAt(2).setIcon(R.drawable.icon_bars);

    }

    private void setControl() {
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);

        imgDoi = findViewById(R.id.imgDoi);
        tvTenDoi = findViewById(R.id.tvTenDoi);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}