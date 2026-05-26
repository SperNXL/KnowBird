package com.knowbird.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.knowbird.R;

import java.util.List;

public class RecognizeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_SEARCH = 0;
    private static final int TYPE_RECOMMEND = 1;
    private static final int TYPE_FILTER = 2;
    private static final int TYPE_BIRD = 3;
    private static final int TYPE_EMPTY = 4;

    private static Context mContext;
    private List<Object> mDataList;
    private OnFilterClickListener mFilterClickListener;
    private int mCurrentFilter = 0;

    public interface OnFilterClickListener {
        void onFilterClick(int filterType);
    }

    public RecognizeAdapter(Context context, List<Object> dataList, OnFilterClickListener listener) {
        this.mContext = context;
        this.mDataList = dataList;
        this.mFilterClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = mDataList.get(position);
        if (item instanceof String) {
            String type = (String) item;
            if ("search".equals(type)) {
                return TYPE_SEARCH;
            } else if ("recommend".equals(type)) {
                return TYPE_RECOMMEND;
            } else if ("filter".equals(type)) {
                return TYPE_FILTER;
            } else if ("empty".equals(type)) {
                return TYPE_EMPTY;
            }
        } else if (item instanceof BirdAdapter.BirdItem) {
            return TYPE_BIRD;
        }
        return TYPE_BIRD;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        switch (viewType) {
            case TYPE_SEARCH:
                return new SearchHolder(inflater.inflate(R.layout.item_recognize_search, parent, false));
            case TYPE_RECOMMEND:
                return new RecommendHolder(inflater.inflate(R.layout.item_recognize_recommend, parent, false));
            case TYPE_FILTER:
                return new FilterHolder(inflater.inflate(R.layout.item_recognize_filter, parent, false), mFilterClickListener, mCurrentFilter);
            case TYPE_EMPTY:
                return new EmptyHolder(inflater.inflate(R.layout.item_recognize_empty, parent, false));
            case TYPE_BIRD:
            default:
                return new BirdHolder(inflater.inflate(R.layout.item_bird, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_SEARCH:
                break;
            case TYPE_RECOMMEND:
                break;
            case TYPE_FILTER:
                ((FilterHolder) holder).updateSelection(mCurrentFilter);
                break;
            case TYPE_EMPTY:
                break;
            case TYPE_BIRD:
                BirdAdapter.BirdItem birdItem = (BirdAdapter.BirdItem) mDataList.get(position);
                ((BirdHolder) holder).bind(birdItem);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void setDataList(List<Object> dataList) {
        this.mDataList = dataList;
        notifyDataSetChanged();
    }

    public void setCurrentFilter(int filter) {
        this.mCurrentFilter = filter;
    }

    static class SearchHolder extends RecyclerView.ViewHolder {
        EditText etSearch;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            etSearch = itemView.findViewById(R.id.et_search);
        }
    }

    static class RecommendHolder extends RecyclerView.ViewHolder {
        public RecommendHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class FilterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView btnAll, btnImage, btnAudio, btnUnknown;
        OnFilterClickListener listener;
        int currentFilter = 0;

        public FilterHolder(@NonNull View itemView, OnFilterClickListener listener, int currentFilter) {
            super(itemView);
            this.listener = listener;
            this.currentFilter = currentFilter;
            btnAll = itemView.findViewById(R.id.btn_all);
            btnImage = itemView.findViewById(R.id.btn_image);
            btnAudio = itemView.findViewById(R.id.btn_audio);
            btnUnknown = itemView.findViewById(R.id.btn_unknown);

            btnAll.setOnClickListener(this);
            btnImage.setOnClickListener(this);
            btnAudio.setOnClickListener(this);
            btnUnknown.setOnClickListener(this);

            updateSelection(currentFilter);
        }

        public void updateSelection(int filter) {
            currentFilter = filter;
            resetAllButtons();
            switch (filter) {
                case 0:
                    btnAll.setBackgroundResource(R.drawable.bg_filter_selected);
                    btnAll.setTextColor(mContext.getResources().getColor(R.color.selected_color));
                    break;
                case 1:
                    btnImage.setBackgroundResource(R.drawable.bg_filter_selected);
                    btnImage.setTextColor(mContext.getResources().getColor(R.color.selected_color));
                    break;
                case 2:
                    btnAudio.setBackgroundResource(R.drawable.bg_filter_selected);
                    btnAudio.setTextColor(mContext.getResources().getColor(R.color.selected_color));
                    break;
                case 3:
                    btnUnknown.setBackgroundResource(R.drawable.bg_filter_selected);
                    btnUnknown.setTextColor(mContext.getResources().getColor(R.color.selected_color));
                    break;
            }
        }

        private void resetAllButtons() {
            btnAll.setBackgroundResource(R.drawable.bg_filter_unselected);
            btnAll.setTextColor(mContext.getResources().getColor(R.color.unselected_color));
            btnImage.setBackgroundResource(R.drawable.bg_filter_unselected);
            btnImage.setTextColor(mContext.getResources().getColor(R.color.unselected_color));
            btnAudio.setBackgroundResource(R.drawable.bg_filter_unselected);
            btnAudio.setTextColor(mContext.getResources().getColor(R.color.unselected_color));
            btnUnknown.setBackgroundResource(R.drawable.bg_filter_unselected);
            btnUnknown.setTextColor(mContext.getResources().getColor(R.color.unselected_color));
        }

        @Override
        public void onClick(View v) {
            if (listener == null) return;
            int filterType;
            if (v.getId() == R.id.btn_all) {
                filterType = 0;
            } else if (v.getId() == R.id.btn_image) {
                filterType = 1;
            } else if (v.getId() == R.id.btn_audio) {
                filterType = 2;
            } else if (v.getId() == R.id.btn_unknown) {
                filterType = 3;
            } else {
                return;
            }
            listener.onFilterClick(filterType);
            updateSelection(filterType);
        }
    }

    static class EmptyHolder extends RecyclerView.ViewHolder {
        public EmptyHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class BirdHolder extends RecyclerView.ViewHolder {
        ImageView ivBirdIcon;
        TextView tvBirdName, tvBirdLatin, tvBirdTime;

        public BirdHolder(@NonNull View itemView) {
            super(itemView);
            ivBirdIcon = itemView.findViewById(R.id.iv_bird_icon);
            tvBirdName = itemView.findViewById(R.id.tv_bird_name);
            tvBirdLatin = itemView.findViewById(R.id.tv_bird_latin);
            tvBirdTime = itemView.findViewById(R.id.tv_bird_time);
        }

        public void bind(BirdAdapter.BirdItem item) {
            Glide.with(ivBirdIcon.getContext())
                    .load(item.getIconRes())
                    .into(ivBirdIcon);
            tvBirdName.setText(item.getTitle());
            tvBirdLatin.setText(item.getSubtitle());
            tvBirdTime.setText(item.getTime());
        }
    }
}
