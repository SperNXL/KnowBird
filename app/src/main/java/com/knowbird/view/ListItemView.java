package com.knowbird.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.knowbird.R;

/**
 * MainActivity 识别List
 *
 */
public class ListItemView extends FrameLayout {

    private ImageView ivIcon;
    private TextView tvTitle;
    private TextView tvSubtitle;
    private TextView tvTime;

    public ListItemView(Context context) {
        this(context, null);
    }

    public ListItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.recon_item_list, this, true);
        ivIcon = findViewById(R.id.iv_icon);
        tvTitle = findViewById(R.id.tv_title);
        tvSubtitle = findViewById(R.id.tv_subtitle);
        tvTime = findViewById(R.id.tv_time);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ListItemView);
            Drawable icon = a.getDrawable(R.styleable.ListItemView_liv_icon);
            String title = a.getString(R.styleable.ListItemView_liv_title);
            String subtitle = a.getString(R.styleable.ListItemView_liv_subtitle);
            String time = a.getString(R.styleable.ListItemView_liv_time);
            a.recycle();

            if (icon != null) ivIcon.setImageDrawable(icon);
            if (title != null) tvTitle.setText(title);
            if (subtitle != null) tvSubtitle.setText(subtitle);
            if (time != null) tvTime.setText(time);
        }
    }

    public void setIcon(Drawable icon) {
        ivIcon.setImageDrawable(icon);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setSubtitle(String subtitle) {
        tvSubtitle.setText(subtitle);
    }

    public void setTime(String time) {
        tvTime.setText(time);
    }
}