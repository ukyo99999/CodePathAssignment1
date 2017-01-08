package net.ukyo.codepathassignment1;

import java.util.ArrayList;

/**
 * Created by ukyo on 2017/1/3.
 * Data model for movies
 */

public class MovieGson {
    public int page;
    public ArrayList<Results> results = new ArrayList<>();

    public class Results {
        public String poster_path;
        public String overview;
        public String title;
        public String backdrop_path;
        public float vote_average;
    }
}
