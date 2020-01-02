package com.compubase.seven.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.compubase.seven.R;
import com.compubase.seven.helper.AddButtonClick;
import com.compubase.seven.helper.RequestHandler;
import com.compubase.seven.helper.TinyDB;
import com.compubase.seven.model.CommentsResponse;
import com.compubase.seven.model.SallesCommentItems;
import com.compubase.seven.ui.fragment.EditCommentFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProfileCommentsAdapter extends RecyclerView.Adapter<ProfileCommentsAdapter.ProfileCommentsViewHolder> {

    List<CommentsResponse> commentItems;

    Context context;

    TinyDB tinyDB;

    @NonNull
    @Override
    public ProfileCommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();

        tinyDB = new TinyDB(context);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_comments_item, parent, false);
        return new ProfileCommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileCommentsViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.username.setText(commentItems.get(position).getName());
        holder.comment.setText(commentItems.get(position).getComment());
        holder.date.setText(commentItems.get(position).getDatee());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteComment(String.valueOf(commentItems.get(position).getId()), position);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tinyDB.putString("commentID", String.valueOf(commentItems.get(position).getId()));
                tinyDB.putString("commentContent", commentItems.get(position).getComment());

                final FragmentManager fm = ((Activity) context).getFragmentManager();
                EditCommentFragment editCommentFragment = new EditCommentFragment();

                editCommentFragment.show(fm, "TV_tag");
            }
        });

    }

    @Override
    public int getItemCount() {
        return commentItems != null ? commentItems.size() : 0;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onButtonClick(AddButtonClick addButtonClick) {
        notifyDataSetChanged();
    }


    private void deleteComment(final String ID, final int position) {
        String GET_JSON_DATA_HTTP_URL = "http://alosboiya.com.sa/wsnew.asmx/delete_comment?";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_JSON_DATA_HTTP_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("True")) {
                            showMessage("تم حذف التعليق");

                            commentItems.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, commentItems.size());

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                showMessage(error.toString());

            }

        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_comment", ID);
                return params;
            }


        };

        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void showMessage(String _s) {
        Toast.makeText(context, _s, Toast.LENGTH_LONG).show();
    }

    public void setdataList(List<CommentsResponse> commentsList) {
        this.commentItems = commentsList;
        notifyDataSetChanged();
    }


    class ProfileCommentsViewHolder extends RecyclerView.ViewHolder {

        TextView username, comment, date;

        Button edit, delete;

        ProfileCommentsViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.comment_user);
            comment = itemView.findViewById(R.id.comment_content);
            date = itemView.findViewById(R.id.comment_date);

            edit = itemView.findViewById(R.id.editbutton);
            delete = itemView.findViewById(R.id.deletebutton);
        }
    }
}
