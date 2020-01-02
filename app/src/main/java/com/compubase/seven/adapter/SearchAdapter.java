package com.compubase.seven.adapter;

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
import com.compubase.seven.model.AdsResponse;
import com.compubase.seven.model.SearchResponse;
import com.compubase.seven.ui.activity.HarageDetailsActivity;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolderSearch> {

    private Context context;
    private List<SearchResponse>oursalesitems;

    public SearchAdapter(List<SearchResponse> searchResponseList) {
        this.oursalesitems = searchResponseList;
    }

    @NonNull
    @Override
    public ViewHolderSearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.sales_items, parent, false);

        return new ViewHolderSearch(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSearch holder, int position) {

        final SearchResponse salesItems = oursalesitems.get(position);

        holder.salesname.setText(salesItems.getTitle());
        holder.saller_name.setText(salesItems.getNameMember());
        holder.salesdate.setText(salesItems.getDateeC());


        if (salesItems.getCountry().equals("السعودية"))
            Glide.with(holder.salesimage.getContext()).load(R.drawable.saudi).into(holder.ivLocation);
        else if (salesItems.getCountry().equals("تركيا"))
            Glide.with(holder.salesimage.getContext()).load(R.drawable.turkesh).into(holder.ivLocation);
        else if (salesItems.getCountry().equals("الأمارات"))
            Glide.with(holder.salesimage.getContext()).load(R.drawable.emerates).into(holder.ivLocation);
        else if (salesItems.getCountry().equals("مصر"))
            Glide.with(holder.salesimage.getContext()).load(R.drawable.egy).into(holder.ivLocation);
        else if (salesItems.getCountry().equals("السودان"))
            Glide.with(holder.salesimage.getContext()).load(R.drawable.suden).into(holder.ivLocation);
        else if (salesItems.getCountry().equals("الأردن"))
            Glide.with(holder.salesimage.getContext()).load(R.drawable.jordon).into(holder.ivLocation);
        else if (salesItems.getCountry().equals("الجزائر"))
            Glide.with(holder.salesimage.getContext()).load(R.drawable.gzaer).into(holder.ivLocation);
        else
            Glide.with(holder.salesimage.getContext()).load(R.drawable.saudi).into(holder.ivLocation);




        Glide.with(holder.salesimage.getContext()).load(salesItems.getImage()).into(holder.salesimage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, HarageDetailsActivity.class);

                intent.putExtra("item_id", oursalesitems.get(position).getId());
                intent.putExtra("item_owner_id", oursalesitems.get(position).getIdMember());
                intent.putExtra("item_title", oursalesitems.get(position).getTitle());
                intent.putExtra("item_owner", oursalesitems.get(position).getNameMember());
                intent.putExtra("item_city", oursalesitems.get(position).getCity());
                intent.putExtra("item_description", oursalesitems.get(position).getDes());
                intent.putExtra("item_department", oursalesitems.get(position).getDepartment());
                intent.putExtra("item_phone", oursalesitems.get(position).getPhone());
                intent.putExtra("item_url", oursalesitems.get(position).getURL());
                intent.putExtra("item_date", oursalesitems.get(position).getDatee());
                intent.putExtra("item_img1", oursalesitems.get(position).getImage());
                intent.putExtra("item_img2", oursalesitems.get(position).getImage2());
                intent.putExtra("item_img3", oursalesitems.get(position).getImage3());
                intent.putExtra("item_img4", oursalesitems.get(position).getImage4());
                intent.putExtra("item_img5", oursalesitems.get(position).getImage5());
                intent.putExtra("item_img6", oursalesitems.get(position).getImage6());
                intent.putExtra("item_img7", oursalesitems.get(position).getImage7());
                intent.putExtra("item_img8", oursalesitems.get(position).getImage8());
                context.startActivity(intent);

            }
        });

        if (salesItems.getImage2().contains("videos")) {
            holder.videoicon.setVisibility(View.VISIBLE);
        } else {
            holder.videoicon.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return oursalesitems != null ? oursalesitems.size() : 0;
    }

    public class ViewHolderSearch extends RecyclerView.ViewHolder {

        TextView salesname, salesdate, saller_name;
        ImageView salesimage, videoicon, ivLocation;

        public ViewHolderSearch(@NonNull View itemView) {
            super(itemView);
            salesname = itemView.findViewById(R.id.sales_name);
            salesdate = itemView.findViewById(R.id.sales_date);
            saller_name = itemView.findViewById(R.id.saller_name);
            salesimage = itemView.findViewById(R.id.sales_image);
            videoicon = itemView.findViewById(R.id.videoicon);
            ivLocation = itemView.findViewById(R.id.iv_location);
        }
    }
    private void showMessage(String _s) {
        Toast.makeText(context.getApplicationContext(), _s, Toast.LENGTH_LONG).show();
    }

}
