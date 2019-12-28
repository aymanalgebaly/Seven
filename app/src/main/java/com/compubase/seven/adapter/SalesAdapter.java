package com.compubase.seven.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.compubase.seven.R;
import com.compubase.seven.helper.TinyDB;
import com.compubase.seven.model.SalesItems;
import com.compubase.seven.ui.activity.HarageDetailsActivity;

import java.util.ArrayList;


public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.SalesViewHolder> {

    TinyDB tinyDB ;

    private ArrayList<SalesItems> oursalesitems;

    Context context ;
    public SalesAdapter(ArrayList<SalesItems> salesItems)
    {
        oursalesitems = salesItems;
    }


    @NonNull
    @Override
    public SalesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_items,parent,false);
        context = parent.getContext();
        tinyDB = new TinyDB(parent.getContext());
        return new SalesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final SalesItems salesItems = oursalesitems.get(position);

        holder.location.setText(salesItems.getLocation());
        holder.salesname.setText(salesItems.getSalesname());
        holder.saller_name.setText(salesItems.getSallername());
        holder.salesdate.setText(salesItems.getSalesdate());

        if(salesItems.getSellseimage().equals("images/imgposting.png") || salesItems.getSellseimage().equals(""))
        {
            Glide.with(holder.salesimage.getContext()).load(R.drawable.imgposting).into(holder.salesimage);
        }else
            {
                Glide.with(holder.salesimage.getContext()).load(salesItems.getSellseimage()).into(holder.salesimage);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, HarageDetailsActivity.class);

                    intent.putExtra("item_id",oursalesitems.get(position).getID());
                    intent.putExtra("item_owner_id",oursalesitems.get(position).getIdMember());
                    intent.putExtra("item_title",oursalesitems.get(position).getSalesname());
                    intent.putExtra("item_owner",oursalesitems.get(position).getSallername());
                    intent.putExtra("item_city",oursalesitems.get(position).getLocation());
                    intent.putExtra("item_description",oursalesitems.get(position).getDescription());
                    intent.putExtra("item_department",oursalesitems.get(position).getDepartment());
                    intent.putExtra("item_phone",oursalesitems.get(position).getPhone());
                    intent.putExtra("item_url",oursalesitems.get(position).getUrl());
                    intent.putExtra("item_date",oursalesitems.get(position).getSalesdate());
                    intent.putExtra("item_img1",oursalesitems.get(position).getSellseimage());
                    intent.putExtra("item_img2",oursalesitems.get(position).getImage2());
                    intent.putExtra("item_img3",oursalesitems.get(position).getImage3());
                    intent.putExtra("item_img4",oursalesitems.get(position).getImage4());
                    intent.putExtra("item_img5",oursalesitems.get(position).getImage5());
                    intent.putExtra("item_img6",oursalesitems.get(position).getImage6());
                    intent.putExtra("item_img7",oursalesitems.get(position).getImage7());
                    intent.putExtra("item_img8",oursalesitems.get(position).getImage8());
                    context.startActivity(intent);

                }
            });

        if(salesItems.getImage2().contains("videos"))
        {
            holder.videoicon.setVisibility(View.VISIBLE);
        }else
            {
                holder.videoicon.setVisibility(View.INVISIBLE);
            }


    }

    @Override
    public int getItemCount() {
        return oursalesitems.size();
    }


    class SalesViewHolder extends RecyclerView.ViewHolder {

        TextView location , salesname, salesdate , saller_name;
        ImageView salesimage,videoicon;


        SalesViewHolder(View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.location_name);
            salesname = itemView.findViewById(R.id.sales_name);
            salesdate = itemView.findViewById(R.id.sales_date);
            saller_name = itemView.findViewById(R.id.saller_name);
            salesimage = itemView.findViewById(R.id.sales_image);
            videoicon = itemView.findViewById(R.id.videoicon);

        }
    }


    private void showMessage(String _s) {
        Toast.makeText(context.getApplicationContext(), _s, Toast.LENGTH_LONG).show();
    }

}
