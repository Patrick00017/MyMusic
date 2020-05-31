package com.example.mymusic.adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.R;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.ViewHolder> {

    private List<Track> m_tracks = new ArrayList<>();
    private OnAlbumClickedListener m_listener = null;

    public TrackListAdapter(List<Track> tracks) {
        m_tracks = tracks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_track_detail, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.numText.setText((position + 1) + "");
        holder.trackTitle.setText(m_tracks.get(position).getTrackTitle());
        holder.trackTitle.setOnClickListener((v) -> {
            if (m_listener != null) {
                m_listener.onClick(position);
            }
        });
        Picasso.get().load(R.drawable.like_clicked).into(holder.likeButton);
    }

    @Override
    public int getItemCount() {
        return m_tracks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView numText;
        TextView trackTitle;
        ImageView likeButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            numText = itemView.findViewById(R.id.track_id);
            trackTitle = itemView.findViewById(R.id.track_title);
            likeButton = itemView.findViewById(R.id.like_track);
        }
    }

    public interface OnAlbumClickedListener{
        void onClick(int index);
    }

    public void setOnAlbumClickedListener(OnAlbumClickedListener listener){
        m_listener = listener;
    }
}
