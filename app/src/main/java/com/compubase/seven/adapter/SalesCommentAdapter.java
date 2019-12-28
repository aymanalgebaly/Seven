package com.compubase.seven.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.compubase.seven.R;
import com.compubase.seven.model.SallesCommentItems;
import java.util.ArrayList;

public class SalesCommentAdapter extends RecyclerView.Adapter<SalesCommentAdapter.salesCommentViewHolder> {


    private ArrayList<SallesCommentItems> oursallesCommentItems;

    public SalesCommentAdapter(ArrayList<SallesCommentItems> sallesCommentItems)
    {
        oursallesCommentItems =sallesCommentItems;
    }


    @NonNull
    @Override
    public salesCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.salescommentitem,parent,false);
        return new salesCommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull salesCommentViewHolder holder, int position) {
        SallesCommentItems sallesCommentItems = oursallesCommentItems.get(position);

        holder.scommdate.setText(sallesCommentItems.getScommentdate());
        holder.scommuser.setText(sallesCommentItems.getScommentuser());
        holder.scommtext.setText(sallesCommentItems.getScommenttext());

    }

    @Override
    public int getItemCount() {
        return oursallesCommentItems.size();
    }


    class salesCommentViewHolder extends RecyclerView.ViewHolder {

        TextView scommdate , scommuser , scommtext;

        salesCommentViewHolder(View itemView) {
            super(itemView);

            scommdate = itemView.findViewById(R.id.sales_comment_date);
            scommuser = itemView.findViewById(R.id.sales_comment_user);
            scommtext = itemView.findViewById(R.id.sales_comment_text);
        }
    }


}
