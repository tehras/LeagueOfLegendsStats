package com.github.koshkin.leagueoflegendsstats.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.adapters.CompleteAdapter;
import com.github.koshkin.leagueoflegendsstats.models.AutoCompleteAdapterHolder;
import com.github.koshkin.leagueoflegendsstats.utils.NullChecker;

import java.util.ArrayList;

/**
 * Created by tehras on 1/9/16.
 */
public class ToolbarSearchView extends LinearLayout {

    private int mThreshold = 2;
    private Context mContext;

    public ToolbarSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ToolbarSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public ToolbarSearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private AutoCompleteTextView mEditText;
    private ImageView mBackImage;
    private ImageView mClearImage;

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.ToolbarSearchView, 0, 0);
        String hint = a.getString(R.styleable.ToolbarSearchView_textHint);
        a.recycle();

        //To inflate the view
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_toolbar_search_view, this);

        mEditText = (AutoCompleteTextView) view.findViewById(R.id.search_edit_field);
        if (!NullChecker.isNullOrEmpty(hint))
            mEditText.setHint(hint);

        mContext = context;

        mBackImage = (ImageView) view.findViewById(R.id.toolbar_search_view_back_image);
        mClearImage = (ImageView) view.findViewById(R.id.toolbar_search_view_clear_image);
    }

    private void setHint(String hint) {
        mEditText.setHint(hint);
    }

    private ToolbarSearchCallback mCallback;
    private ToolbarSearchActionCallback mActionCallback;

    public void setOnSearchButtonListener(ToolbarSearchActionCallback callback) {
        mActionCallback = callback;

        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mActionCallback.performSearch(mEditText.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    public void setOnButtonListener(ToolbarSearchCallback callback) {
        mCallback = callback;
        mBackImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.backButtonClicked();
            }
        });
        mClearImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.clearButtonClicked();
            }
        });
    }

    public void backPressed() {
        if (mCallback != null)
            mCallback.backButtonClicked();
        else
            this.setVisibility(GONE);
    }

    public void clearText() {
        if (mEditText != null) {
            mEditText.setText("");
        }
    }

    public EditText getEditText() {
        return mEditText;
    }

    public void resetText() {
        mEditText.setText("");
    }

    public void setUpAdapter(ArrayList<AutoCompleteAdapterHolder> fullArray) {
        if (fullArray != null) {
            ArrayAdapter<AutoCompleteAdapterHolder> adapter = new CompleteAdapter(mContext, R.layout.array_adapter_list_dropdown, fullArray);

            mEditText.setAdapter(adapter);
            mEditText.setThreshold(mThreshold);
            mEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String str = ((AutoCompleteAdapterHolder) parent.getItemAtPosition(position)).getName();
                    mActionCallback.performSearch(str);
                }
            });
        }
    }

    public interface ToolbarSearchActionCallback {
        void performSearch(String search);
    }

    public interface ToolbarSearchCallback {
        void backButtonClicked();

        void clearButtonClicked();
    }
}
