package com.udacity.example.droidtermsprovider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class DroidtermExampleContract implements BaseColumns {
    // inner class
    // authority
    public static final String CONTENT_AUTHORITY = "com.example.udacity.droidtermsexample";
    // ------------------------------- CONTENTS------------------------------------
    // if we have to access content from content provider :we have define uri
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //paths from the uri
    private static final String PATH_TERMS = "terms";
    // content uri:full list of definition & items
    private static final Uri CONTENT_URI  = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TERMS).build();
    // content type:list of definition
    private static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TERMS;
    // content item type:single item definition
    public static final String CONTENT_ITEM_TYPE =
                   ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TERMS;

    //===============================DATABASE ===================================================
        // sql type content provider
    private static final int DATABASE_VERSION  = 1;
    private static final String DATABASE_NAME  = "terms";

    // table name
    private static final String TERMS_TABLE  = "term_entries";

    private static final String COLUMN_WORD  = "word";
    private static final String COLUMN_DEFINATION  = "definition";
    private static final String [] COLUMNS  = {_ID,COLUMN_WORD,COLUMN_DEFINATION};

    // indexes
    private static final int COLUMN_INDEX_ID = 0;
    private static final int COLUMN_INDEX_WORD = 1;
    private static final int COLUMN_INDEX_DEFINITION = 2;

    /**
     * This Method Creates A Uri Link Between Our App And Droid App And Get Word Definition by id
     * @param id
     * @return
     */
    public static Uri builtTermUriWithId(long id){
        return ContentUris.withAppendedId(CONTENT_URI,id);
    }
}
