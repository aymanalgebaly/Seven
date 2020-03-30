package com.compubase.seven.adapter;

import android.annotation.SuppressLint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.compubase.seven.R;
import com.compubase.seven.helper.AddButtonClick;
import com.compubase.seven.model.ItemList;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;


public class IteamListAdapter extends RecyclerView.Adapter<IteamListAdapter.ItemViewHolder> {

    private ArrayList<ItemList> ourItemList ;

    public IteamListAdapter(ArrayList<ItemList> ourItemList) {
        this.ourItemList = ourItemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list,parent,false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ItemList myitemList = ourItemList.get(position);
        holder.itemimage.setImageResource(myitemList.getImguri());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new AddButtonClick(String.valueOf(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return ourItemList.size() ;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{

        ImageView itemimage;

        ItemViewHolder(View itemView) {
            super(itemView);
            itemimage = itemView.findViewById(R.id.item_img);
        }

    }
}
