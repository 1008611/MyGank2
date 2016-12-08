package com.wildwolf.mygank2.adapter;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;
import com.squareup.picasso.Picasso;
import com.wildwolf.mygank2.R;
import com.wildwolf.mygank2.bean.GanHuo;
import com.wildwolf.mygank2.utils.ThemeUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.xiaopan.android.content.res.DimenUtils;

/**
 * Created by ${wild00wolf} on 2016/12/8.
 */
public class FuliAdapter extends BaseAdapter {
    private Context context;
    private List<GanHuo> ganHuos;

    public FuliAdapter(Context context, List<GanHuo> ganHuos) {
        this.context = context;
        this.ganHuos = ganHuos;
    }

    @Override
    public int getCount() {
        return ganHuos.size();
    }

    @Override
    public Object getItem(int i) {
        return ganHuos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        GanHuo ganHuo = ganHuos.get(position);
        View view1;

        ImageViewHolder viewHolder = null;
        if (convertView != null && convertView.getTag() instanceof ImageViewHolder) {
            viewHolder = (ImageViewHolder) convertView.getTag();
        } else {
            view1 = LayoutInflater.from(context).inflate(R.layout.item_fuli, null);
            viewHolder = new ImageViewHolder(view1);
            view1.setTag(viewHolder);
            convertView = view1;
        }
        Picasso.with(context).load(ganHuo.getUrl()).placeholder(R.mipmap.ic_launcher).into(viewHolder.mImage);

        return convertView;

    }

    static class ImageViewHolder {
        @Bind(R.id.image)
        ImageView mImage;

        ImageViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
