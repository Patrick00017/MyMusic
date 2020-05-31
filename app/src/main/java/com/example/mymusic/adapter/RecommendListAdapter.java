package com.example.mymusic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.R;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

public class RecommendListAdapter extends RecyclerView.Adapter<RecommendListAdapter.ViewHolder> {

    private List<Album> m_albums = new ArrayList<>();
    private onAlbumClickedListener m_listener = null;

    public RecommendListAdapter(List<Album> albums) {
        m_albums = albums;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recommend_album, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.num_view.setText((position + 1) + "");
        holder.name_view.setText(m_albums.get(position).getAlbumTitle());
        holder.name_view.setOnClickListener((v) -> {
            if (m_listener != null) {
                m_listener.onClick(m_albums.get(position));
            }
        });
        Picasso.get().load(R.drawable.like_clicked).into(holder.like_button);
    }

    @Override
    public int getItemCount() {
        return m_albums.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView num_view;
        public TextView name_view;
        public ImageView like_button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            num_view = itemView.findViewById(R.id.album_id);
            name_view = itemView.findViewById(R.id.album_title);
            like_button = itemView.findViewById(R.id.like_button);
        }
    }

    public interface onAlbumClickedListener{
        void onClick(Album album);
    }

    public void setonAlbumClickedListener(onAlbumClickedListener listener){
        m_listener = listener;
    }
}
