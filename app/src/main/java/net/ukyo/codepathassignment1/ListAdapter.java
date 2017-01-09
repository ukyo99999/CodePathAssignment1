package net.ukyo.codepathassignment1;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
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
    private int mScreenWidth;
    private final int ITEM_TYPE_NOT_POPULAR = 0;
    private final int ITEM_TYPE_POPULAR = 1;

    public ListAdapter(Context context, MovieGson data, int screemWidth) {
        this.mContext = context;
        this.mData = data;
        this.mScreenWidth = screemWidth;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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

                //set view display
                holder.textTitle.setText(mData.results.get(position).title);
                holder.textOverview.setText(mData.results.get(position).overview);

                Picasso.with(mContext).load(getImageUrl(mData.results.get(position).poster_path))
                        .placeholder(R.mipmap.img_placeholder)
                        .into(holder.imagePoster);

                //set this item click listener
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoDetailActivity(position);
                    }
                });

            } else {

                //set view display
                Picasso.with(mContext).load(getImageUrl(mData.results.get(position).backdrop_path))
                        .placeholder(R.mipmap.img_placeholder)
                        .resize(mScreenWidth, 0)
                        .into(holder.imagePoster);

                //set this item click listener
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoYoutubeActivity(position);
                    }
                });
            }

        } else if (mOrientation == Configuration.ORIENTATION_LANDSCAPE) {

            holder.textTitle.setText(mData.results.get(position).title);
            holder.textOverview.setText(mData.results.get(position).overview);

            if (type == ITEM_TYPE_NOT_POPULAR) {

                //set view display
                Picasso.with(mContext).load(getImageUrl(mData.results.get(position).poster_path))
                        .placeholder(R.mipmap.img_placeholder)
                        .into(holder.imagePoster);

                //set this item click listener
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoDetailActivity(position);
                    }
                });
            } else {

                //set view display
                Picasso.with(mContext).load(getImageUrl(mData.results.get(position).backdrop_path))
                        .placeholder(R.mipmap.img_placeholder)
                        .into(holder.imagePoster);

                //set poster image click listener
                holder.imagePoster.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoYoutubeActivity(position);
                    }
                });
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

    private void gotoDetailActivity(int position) {
        if (mData != null) {
            Intent intent = new Intent();
            intent.setClass(mContext, DetailActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("image", mData.results.get(position).backdrop_path);
            bundle.putString("title", mData.results.get(position).title);
            bundle.putString("release_date", mData.results.get(position).release_date);
            bundle.putFloat("rating", mData.results.get(position).vote_average);
            bundle.putString("overview", mData.results.get(position).overview);
            bundle.putString("videoId", mData.results.get(position).id);

            intent.putExtras(bundle);
            mContext.startActivity(intent);
        }
    }

    private void gotoYoutubeActivity(int position) {
        if (mData != null) {
            Intent intent = new Intent();
            intent.setClass(mContext, PlayYoutubeActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("videoId", mData.results.get(position).id);

            intent.putExtras(bundle);
            mContext.startActivity(intent);
        }
    }

}
