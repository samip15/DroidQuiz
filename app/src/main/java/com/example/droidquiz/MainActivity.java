package com.example.droidquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.udacity.example.droidtermsprovider.DroidtermExampleContract;

public class MainActivity extends AppCompatActivity {
    private Button mButton;
    private TextView mDefinitiontv,mWordTv;
    // cursor data
    private Cursor mData;
    private int mDefCol,mWordCol;
    // current state
    private int mCurrentState;
    // hidden or shown
    private final int STATE_HIDDEN = 0;
    private final int STATE_SHOWN = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = findViewById(R.id.button_next);
        mDefinitiontv = findViewById(R.id.text_view_definition);
        mWordTv = findViewById(R.id.text_view_word);
        // database operation to get cursor or data from the content provider
        new WordFetchTask().execute();
    }

    /**
     * when button is clicked
     * @param view
     */
    public void onButtonClick(View view) {
        // change state
        switch (mCurrentState){
            case STATE_HIDDEN:
                showDefinition();
                break;
            case STATE_SHOWN:
                nextWord();
                break;
        }
    }

    /**
     * helps to go to the next word
     */
    private void nextWord() {
        if (mData!=null){
            if (!mData.moveToNext()){
                mData.moveToFirst();
            }
            mDefinitiontv.setVisibility(View.INVISIBLE);
            mButton.setText(getString(R.string.show_defination));
            // get the data
            mWordTv.setText(mData.getString(mWordCol));
            mDefinitiontv.setText(mData.getString(mDefCol));
            mCurrentState = STATE_HIDDEN;
        }

    }

    /**
     * helps to show the definition of the word
     */
    private void showDefinition() {
        if (mData!=null){
            mDefinitiontv.setVisibility(View.VISIBLE);
            mButton.setText(getString(R.string.next_word));
            mCurrentState = STATE_SHOWN;
        }

    }
    //-----------------------------CONTENT PROVIDER CONNECTION---------------

    /**
     * Async Task For Getting The Data From Content Provider
     */
    public class WordFetchTask extends AsyncTask<Void,Void, Cursor>{

        @Override
        protected Cursor doInBackground(Void... voids) {
            // content resolver : gets the cursor from content provider we need query Uri toit
            ContentResolver resolver = getContentResolver();
            // uri pass
            Cursor cursor = resolver.query(DroidtermExampleContract.CONTENT_URI,null,null,null,null);
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            mData = cursor;
            // word and definition
            mDefCol = mData.getColumnIndex(DroidtermExampleContract.COLUMN_DEFINATION);
            mWordCol = mData.getColumnIndex(DroidtermExampleContract.COLUMN_WORD);
            // get the next word
            nextWord();
        }
    }
}