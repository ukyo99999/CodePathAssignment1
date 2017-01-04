package net.ukyo.codepathassignment1;

import android.content.Context;
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

    public ListAdapter(Context context, MovieGson data) {
        this.mContext = context;
        this.mData = data;
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
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        Holder holder;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item_movies, parent, false);
            holder = new Holder();
            holder.imagePoster = (ImageView) view.findViewById(R.id.image_poster);
            holder.textTitle = (TextView) view.findViewById(R.id.text_title);
            holder.textOverview = (TextView) view.findViewById(R.id.text_overview);

            view.setTag(holder);

        } else {
            holder = (Holder) view.getTag();
        }

        Picasso.with(mContext).load(getImageUrl(mData.results.get(position).poster_path)).into(holder.imagePoster);
        holder.textTitle.setText(mData.results.get(position).title);
        holder.textOverview.setText(mData.results.get(position).overview);

        return view;
    }

    private class Holder {
        ImageView imagePoster;
        TextView textTitle;
        TextView textOverview;
    }

    private String getImageUrl(String urlFromApi) {
        String prefix = "https://image.tmdb.org/t/p/w342";
        String urlArray = urlFromApi.substring(0, urlFromApi.length());
        return prefix + urlArray;
    }


}
