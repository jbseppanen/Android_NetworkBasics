package com.example.jacob.android_networkbasics;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

public class XkcdDao {
    private final static String BASE_URL = "https://xkcd.com/";
    private final static String END_URL = "info.0.json";
    private final static String RECENT_COMIC = BASE_URL + END_URL;
    private final static String SPECIFIC_COMIC = BASE_URL + "%d/" + END_URL;


    private static XkcdComic getComic(String urlString) {
        String url = NetworkAdapter.httpRequest(urlString);
        XkcdComic comic = null;
        try {
            JSONObject comicAsJson = new JSONObject(url);
            comic = new XkcdComic(comicAsJson);


            String imageUrl = null;
            imageUrl = comic.getImg();
            if (imageUrl != null) {
                Bitmap bitmap;
                bitmap = NetworkAdapter.httpImageRequest(imageUrl);
                if (bitmap != null) {
                    comic.setBitmap(bitmap);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return comic;
        }
        return comic;
    }

    public static XkcdComic getRecentComic() {
        XkcdComic comic = null;
        comic = getComic(RECENT_COMIC);
        return comic;
    }

    public static XkcdComic getNextComic(XkcdComic currentComic) {
        XkcdComic comic = null;
        int comicNum = -1;
        comicNum = Integer.parseInt(currentComic.getNum());
        if (comicNum != -1) {
            comicNum += 1;
            String url = SPECIFIC_COMIC.replace("%d/", Integer.toString(comicNum));
            comic = getComic(url);
        }
        return comic;
    }

    public static XkcdComic gePreviousComic(XkcdComic currentComic) {
        XkcdComic comic = null;
        int comicNum = -1;
        comicNum = Integer.parseInt(currentComic.getNum());
        if (comicNum != -1) {
            comicNum -= 1;
            String url = SPECIFIC_COMIC.replace("%d/", Integer.toString(comicNum));
            comic = getComic(url);
        }
        return comic;
    }


}
