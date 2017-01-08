package net.ukyo.codepathassignment1;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.shapes.RoundRectShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * Created by ukyo on 2017/1/3.
 * ListView adapter
 */

public class ListAdapter extends BaseAdapter {
    private Context mContext;
    private MovieGson mData;
    private int mOrientation;
    private int mScreemWidth;
    private final int ITEM_TYPE_NOT_POPULAR = 0;
    private final int ITEM_TYPE_POPULAR = 1;

    public ListAdapter(Context context, MovieGson data, int screemWidth) {
        this.mContext = context;
        this.mData = data;
        this.mScreemWidth = screemWidth;
        mOrientation = mContext.getResources().getConfiguration().orientation;
    }


    @Override
    public int getCount() {

        if (mData != null) {
            return mData.results.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (mData.results.get(position).vote_average >= 5.0f) {
            return ITEM_TYPE_POPULAR;
        } else {
            return ITEM_TYPE_NOT_POPULAR;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Holder holder;
        int type = getItemViewType(position);

        if (view == null) {
            view = getInflatedLayoutForType(type);
            holder = new Holder();
            holder.imagePoster = (ImageView) view.findViewById(R.id.image_poster);
            holder.textTitle = (TextView) view.findViewById(R.id.text_title);
            holder.textOverview = (TextView) view.findViewById(R.id.text_overview);

            view.setTag(holder);

        } else {
            holder = (Holder) view.getTag();
        }

        if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {

            if (type == ITEM_TYPE_NOT_POPULAR) {

                holder.textTitle.setText(mData.results.get(position).title);
                holder.textOverview.setText(mData.results.get(position).overview);

                Picasso.with(mContext).load(getImageUrl(mData.results.get(position).poster_path))
                        .placeholder(R.mipmap.img_placeholder)
                        .into(holder.imagePoster);
            } else {
                Picasso.with(mContext).load(getImageUrl(mData.results.get(position).backdrop_path))
                        .placeholder(R.mipmap.img_placeholder)
                        .resize(mScreemWidth, 0)
                        .into(holder.imagePoster);
            }

        } else if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {

            holder.textTitle.setText(mData.results.get(position).title);
            holder.textOverview.setText(mData.results.get(position).overview);

            if (type == ITEM_TYPE_NOT_POPULAR) {

                Picasso.with(mContext).load(getImageUrl(mData.results.get(position).poster_path))
                        .placeholder(R.mipmap.img_placeholder)
                        .into(holder.imagePoster);
            } else {
                Picasso.with(mContext).load(getImageUrl(mData.results.get(position).backdrop_path))
                        .placeholder(R.mipmap.img_placeholder)
                        .into(holder.imagePoster);
            }
        }

        return view;
    }

    private class Holder {
        ImageView imagePoster;
        TextView textTitle;
        TextView textOverview;
    }

    private String getImageUrl(String urlFromApi) {
        String prefix = "https://image.tmdb.org/t/p/w780";
        String urlArray = urlFromApi.substring(0, urlFromApi.length());
        return prefix + urlArray;
    }

    private View getInflatedLayoutForType(int type) {
        if (type == ITEM_TYPE_NOT_POPULAR) {
            return LayoutInflater.from(mContext).inflate(R.layout.list_item_type1, null);
        } else if (type == ITEM_TYPE_POPULAR) {
            return LayoutInflater.from(mContext).inflate(R.layout.list_item_type2, null);
        } else {
            return null;
        }
    }

}
