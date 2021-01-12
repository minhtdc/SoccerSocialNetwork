package com.example.soccersocialnetwork.TranDuyHuynh.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.soccersocialnetwork.LoginActivity;
import com.example.soccersocialnetwork.R;
import com.example.soccersocialnetwork.TranDuyHuynh.adapter.CategoryAdapter_KhuVuc;
import com.example.soccersocialnetwork.TranDuyHuynh.adapter.CategoryAdapter_LoaiHinhSan;
import com.example.soccersocialnetwork.TranDuyHuynh.adapter.CategoryAdapter_LoaiSan;
import com.example.soccersocialnetwork.TranDuyHuynh.adapter.information_findTeams_Adapter;
import com.example.soccersocialnetwork.TranDuyHuynh.edit_profile_user;
import com.example.soccersocialnetwork.TranDuyHuynh.infomation_dangtintimtran;
import com.example.soccersocialnetwork.TranDuyHuynh.models.Category_KhuVuc;
import com.example.soccersocialnetwork.TranDuyHuynh.models.Category_LoaiHinhSan;
import com.example.soccersocialnetwork.TranDuyHuynh.models.Category_LoaiSan;
import com.example.soccersocialnetwork.TranDuyHuynh.models.thongTinTranDau;
import com.example.soccersocialnetwork.support_func;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.soccersocialnetwork.TranDuyHuynh.models.information_findTeams;
import com.example.soccersocialnetwork.TranDuyHuynh.edit_profile_user;
import com.example.soccersocialnetwork.football_field_owner.database.DataBaseHelper;
import com.example.soccersocialnetwork.football_field_owner.model.City;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home_flagment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home_flagment extends Fragment {
    TextView txtDangTin;
    ImageView imgUser;
    private DatabaseReference firebaseDatabase;

    // TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner spnThanhPho, spnQuan;
    ArrayList<City> data_tp = new ArrayList<>();
    ArrayList<String> data_quan = new ArrayList<>();
    ArrayAdapter adapter_tp, adapter_quan;

    // khai báo đối tượng cho list view
    private ListView listView;
    private TextView txtDiaDiem,txtNgay,txtThoiGian;
    private  ArrayList<thongTinTranDau> thongTinTranDaus = new ArrayList<>();
    public static String idThongTinTranDau;

    public home_flagment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home_flagment.
     */
// TODO: Rename and change types and number of parameters
    public static home_flagment newInstance(String param1, String param2) {
        home_flagment fragment = new home_flagment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_flagment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtDangTin = (TextView) getView().findViewById(R.id.txtDangTinTimTran);
        spnThanhPho = getView().findViewById(R.id.spnThanhPho);
        spnQuan = getView().findViewById(R.id.spnTinh);


        //duw lieu spn
        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        dataBaseHelper.createDataBase();
        dataBaseHelper.openDataBase();
        City city = new City();
        city.setName("City");
        data_tp = dataBaseHelper.getAllCity();
        data_tp.add(city);
        setAdapterSpinner(data_tp, adapter_tp, spnThanhPho);
        spnThanhPho.setSelection(data_tp.size());
        spnThanhPho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                locDanhSachSanTheoTP();
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                    City items = data_tp.get(i);
                    data_quan = dataBaseHelper.getAllDistrict(items.getId());
                    data_quan.add("District");
                    setAdapterSpinner(data_quan, adapter_quan, spnQuan);
                    spnQuan.setSelection(data_quan.size());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnQuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locDanhSachSanTheoTPVaQuan();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // gọi hàm tạo dữ liệu giả cho list view
        DanhSachTranDau();
//        createDataForListView();

        // setAdapter cho list view
//        information_findTeams_Adapter adapter = new information_findTeams_Adapter(getContext(), R.layout.listview_doi_dang_tim_tran, information_findTeams);
//        listView = (ListView) getView().findViewById(R.id.listview_tim_tran);
//        listView.setAdapter(adapter);


        // hiện thị màn hình thông tin đăng tin tìm trận khi click vào edt đăng tin tìm trận
        txtDangTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), infomation_dangtintimtran.class);
                startActivity(intent);
            }
        });

        imgUser = (ImageView) getView().findViewById(R.id.imgUser);
        Picasso.get().load(LoginActivity.USER_IMG_CURRENT).into(imgUser);
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), edit_profile_user.class);
                startActivity(intent);
            }
        });


    }


    // thêm dữ liệu giả cho spinner khu vực
    private List<Category_KhuVuc> getListCategoryKhuVuc() {
        List<Category_KhuVuc> list = new ArrayList<>();
        list.add(new Category_KhuVuc("Khu vực"));
        list.add(new Category_KhuVuc("TP.HCM"));
        list.add(new Category_KhuVuc("Đà Nẵng"));
        list.add(new Category_KhuVuc("Hà Nội"));
        list.add(new Category_KhuVuc("Huế"));

        return list;
    }

    // thêm dữ liệu giả cho spinner loại hình sân
    private List<Category_LoaiHinhSan> getListCategoryLoaiHinhSan() {
        List<Category_LoaiHinhSan> list = new ArrayList<>();
        list.add(new Category_LoaiHinhSan("Loại hình sân"));
        list.add(new Category_LoaiHinhSan("Sân 5"));
        list.add(new Category_LoaiHinhSan("Sân 7"));
        list.add(new Category_LoaiHinhSan("Sân 11"));

        return list;
    }

    // thêm dữ liệu giả cho spinner loại sân
    private List<Category_LoaiSan> getListCategoryLoaiSan() {
        List<Category_LoaiSan> list = new ArrayList<>();
        list.add(new Category_LoaiSan("Loại sân"));
        list.add(new Category_LoaiSan("Sân cỏ thật"));
        list.add(new Category_LoaiSan("Sân cỏ nhân tạo"));
        list.add(new Category_LoaiSan("Sân trong nhà"));
        return list;
    }

    // thêm dữ liệu giả cho list view đội tìm trận
    private void createDataForListView() {


//
//        Intent intent = new Intent();
//         thongTinTranDau = (thongTinTranDau) intent.getSerializableExtra("thongTinTranDau");
//
//        thongTinTranDaus.add(thongTinTranDau);

    }
    private void locDanhSachSanTheoTPVaQuan(){
        if(spnQuan.getSelectedItem().toString().equals("District"))
        {
            locDanhSachSanTheoTP();
        }
        else {
            firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("ThongTinTranDau");
            firebaseDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    thongTinTranDaus.clear();
                    for (DataSnapshot dt :
                            snapshot.getChildren()) {
                        thongTinTranDau thongTinTranDau = dt.getValue(thongTinTranDau.class);
                        if (support_func.isStrLike(thongTinTranDau.getDiaDiem(), spnThanhPho.getSelectedItem().toString() + ", " + spnQuan.getSelectedItem().toString())) {
                            thongTinTranDaus.add(thongTinTranDau);
                        }
                    }
                    // Toast.makeText(getContext(),listTeams.get(0).getIdDoi()+ "", Toast.LENGTH_SHORT).show();

                    information_findTeams_Adapter adapter = new information_findTeams_Adapter(getContext(), R.layout.listview_doi_dang_tim_tran, thongTinTranDaus);
                    listView = (ListView) getView().findViewById(R.id.listview_tim_tran);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void locDanhSachSanTheoTP(){
        if(spnThanhPho.getSelectedItem().toString().equals("City")){
            DanhSachTranDau();
        }
        else {
            firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("ThongTinTranDau");
            firebaseDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    thongTinTranDaus.clear();
                    for (DataSnapshot dt :
                            snapshot.getChildren()) {
                        thongTinTranDau thongTinTranDau = dt.getValue(thongTinTranDau.class);
                        if (support_func.isStrLike(thongTinTranDau.getDiaDiem(), spnThanhPho.getSelectedItem().toString())) {
                            thongTinTranDaus.add(thongTinTranDau);
                        }
                    }
                    // Toast.makeText(getContext(),listTeams.get(0).getIdDoi()+ "", Toast.LENGTH_SHORT).show();

                    information_findTeams_Adapter adapter = new information_findTeams_Adapter(getContext(), R.layout.listview_doi_dang_tim_tran, thongTinTranDaus);
                    listView = (ListView) getView().findViewById(R.id.listview_tim_tran);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void DanhSachTranDau() {
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("ThongTinTranDau");
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                thongTinTranDaus.clear();
                for (DataSnapshot dt :
                        snapshot.getChildren()) {
                    thongTinTranDau thongTinTranDau = dt.getValue(thongTinTranDau.class);
                    thongTinTranDaus.add(thongTinTranDau);
                }
                // Toast.makeText(getContext(),listTeams.get(0).getIdDoi()+ "", Toast.LENGTH_SHORT).show();

                information_findTeams_Adapter adapter = new information_findTeams_Adapter(getContext(),R.layout.listview_doi_dang_tim_tran,thongTinTranDaus);
                listView = (ListView)getView().findViewById(R.id.listview_tim_tran);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void setAdapterSpinner(ArrayList data, ArrayAdapter adapter, Spinner spinner) {
        if (adapter == null) {
            adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, data);
            spinner.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            adapter.notifyDataSetChanged();
            spinner.setSelection(adapter.getCount() - 1);
        }
    }
}