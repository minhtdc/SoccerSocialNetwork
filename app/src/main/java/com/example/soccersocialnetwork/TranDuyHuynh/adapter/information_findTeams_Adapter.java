package com.example.soccersocialnetwork.TranDuyHuynh.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.soccersocialnetwork.R;
import com.example.soccersocialnetwork.TranDuyHuynh.models.thongTinTranDau;
import com.squareup.picasso.Picasso;

import java.util.List;

public class information_findTeams_Adapter extends ArrayAdapter<thongTinTranDau> {
    Context context;
    int resource;
    List<thongTinTranDau> object;

    public information_findTeams_Adapter(@NonNull Context context, int resource, @NonNull List<thongTinTranDau> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.object = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        information_findTeams_Holder information_findTeams_holder = null;
        if( row == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(resource,parent,false);
            information_findTeams_holder = new information_findTeams_Holder();
            information_findTeams_holder.imageView = (ImageView)row.findViewById(R.id.imgLogo_find_teams);
            information_findTeams_holder.txtTenDoi = (TextView)row.findViewById(R.id.txtTenDoi);
            information_findTeams_holder.txtDiaDiem = (TextView)row.findViewById(R.id.txtThongTinDiaDiem);
            information_findTeams_holder.txtThoigian = (TextView)row.findViewById(R.id.txtThongTinGio);
            information_findTeams_holder.txtNgay= (TextView)row.findViewById(R.id.txtThongTinNgay);
            row.setTag(information_findTeams_holder);
        }
        else{
            information_findTeams_holder = (information_findTeams_Holder)row.getTag();
        }

        thongTinTranDau thongTinTranDau = object.get(position);


        information_findTeams_holder.txtTenDoi.setText(thongTinTranDau.getTenDoi());
        information_findTeams_holder.txtDiaDiem.setText(thongTinTranDau.getDiaDiem());
        information_findTeams_holder.txtNgay.setText(thongTinTranDau.getNgay());
        information_findTeams_holder.txtThoigian.setText(thongTinTranDau.getThoiGian());
        Picasso.get().load(thongTinTranDau.getAnhDoi()).into( information_findTeams_holder.imageView);

        return  row;
    }
    class information_findTeams_Holder{
        ImageView imageView;
        TextView txtTenDoi, txtDiaDiem, txtThoigian, txtNgay;
    }
}
