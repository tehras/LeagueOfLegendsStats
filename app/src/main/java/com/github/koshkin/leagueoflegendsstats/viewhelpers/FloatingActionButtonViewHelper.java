package com.github.koshkin.leagueoflegendsstats.viewhelpers;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.fragments.stats.StatsTabbedFragment;
import com.github.koshkin.leagueoflegendsstats.models.AutoCompleteAdapterHolder;
import com.github.koshkin.leagueoflegendsstats.models.RecentSummoner;
import com.github.koshkin.leagueoflegendsstats.models.SimpleSummoner;
import com.github.koshkin.leagueoflegendsstats.sqlite.FavoritesSqLiteHelper;
import com.github.koshkin.leagueoflegendsstats.sqlite.RecentSearchSqlLiteHelper;
import com.github.koshkin.leagueoflegendsstats.views.ToolbarSearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tehras on 1/9/16.
 * <p/>
 * This helper handles the Fab button
 */
public class FloatingActionButtonViewHelper {

    private MainActivity mMainActivity;

    private static FloatingActionButtonViewHelper sHelper;
    private ToolbarSearchView mTsv;

    private FloatingActionButtonViewHelper() {
    }

    private void setActivity(MainActivity activity) {
        mMainActivity = activity;
    }

    public static FloatingActionButtonViewHelper getInstance(MainActivity activity) {
        if (sHelper == null)
            sHelper = new FloatingActionButtonViewHelper();

        sHelper.setActivity(activity);
        return sHelper;
    }

    public void initialize() {
        if (mMainActivity == null)
            return;

        FloatingActionButton fab = (FloatingActionButton) mMainActivity.findViewById(R.id.fab);
        ToolbarSearchView tsv = (ToolbarSearchView) mMainActivity.findViewById(R.id.toolbar_search_layout);

        initToolbarSearchView(tsv, fab);

        fab.setOnClickListener(fabOnClickListener(tsv, fab));
    }

    private void initToolbarSearchView(final ToolbarSearchView tsv, final FloatingActionButton fab) {
        this.mTsv = tsv;
        mTsv.setOnButtonListener(new ToolbarSearchView.ToolbarSearchCallback() {
            @Override
            public void backButtonClicked() {
                isSearchIcon = true;
                closeSearch(fab, tsv);
            }

            @Override
            public void clearButtonClicked() {
                tsv.clearText();
            }
        });
        mTsv.setOnSearchButtonListener(new ToolbarSearchView.ToolbarSearchActionCallback() {
            @Override
            public void performSearch(String searchName) {
                isSearchIcon = true;
                closeSearch(fab, tsv);

                mMainActivity.startFragment(StatsTabbedFragment.getInstance(searchName, null));
            }
        });
        updateSearchSuggestions();
    }

    private void updateSearchSuggestions() {
        ArrayList<AutoCompleteAdapterHolder> array = getSuggestionArray();
        mTsv.setUpAdapter(array);
    }

    private Animation mSlideInAnim;
    private Animation mSlideOutAnim;

    private Animation mFadeOutAnimation;
    private Animation mFadeInAnimation;

    public Animation getFadeOutAnimation() {
        if (mFadeOutAnimation == null)
            mFadeOutAnimation = AnimationUtils.loadAnimation(mMainActivity, android.R.anim.fade_out);

        return mFadeOutAnimation;
    }

    public Animation getFadeInAnimation() {
        if (mFadeInAnimation == null)
            mFadeInAnimation = AnimationUtils.loadAnimation(mMainActivity, android.R.anim.fade_in);

        return mFadeInAnimation;
    }

    private Animation getSlideInAnim() {
        if (mSlideInAnim == null)
            mSlideInAnim = AnimationUtils.loadAnimation(mMainActivity, R.anim.slide_in_right);

        return mSlideInAnim;
    }

    private Animation getSlideOutAnim() {
        if (mSlideOutAnim == null)
            mSlideOutAnim = AnimationUtils.loadAnimation(mMainActivity, R.anim.slide_out_right);

        return mSlideOutAnim;
    }

    private boolean isSearchIcon = true;

    private View.OnClickListener fabOnClickListener(final ToolbarSearchView toolbarSearchView, final FloatingActionButton fab) {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean isSearchIcon = isCurrentlySearchIcon();
                if (isSearchIcon) {
                    openSearch(fab, toolbarSearchView);
                } else {
                    closeSearch(fab, toolbarSearchView);
                }
            }
        };
    }

    private void openSearch(FloatingActionButton fab, ToolbarSearchView toolbarSearchView) {
        updateSearchSuggestions();

        toolbarSearchView.resetText();
        //animate fab
        animateSearchIcon(true, fab);
        //animate card view show
        animateSearchBar(true, toolbarSearchView);

        mMainActivity.openKeyboard(toolbarSearchView.getEditText());
    }

    private void closeSearch(FloatingActionButton fab, ToolbarSearchView toolbarSearchView) {
        mMainActivity.hideKeyboard(toolbarSearchView.getEditText());

        //animate fab
        animateSearchIcon(false, fab);
        //animate card view hide
        animateSearchBar(false, toolbarSearchView);
    }

    private void animateSearchBar(boolean isSearch, ToolbarSearchView toolbarSearchView) {
        Animation animation;
        final Toolbar toolbar = mMainActivity.getToolbar();

        if (isSearch) {
            animation = getSlideInAnim();
            if (toolbar != null)
                toolbar.setVisibility(View.GONE);
            toolbarSearchView.setVisibility(View.VISIBLE);
            toolbarSearchView.requestFocus();
        } else {
            animation = getSlideOutAnim();
            if (toolbar != null)
                toolbar.setVisibility(View.VISIBLE);
            toolbarSearchView.setVisibility(View.GONE);
            toolbarSearchView.clearFocus();
        }

        toolbarSearchView.startAnimation(animation);
    }

    private boolean isCurrentlySearchIcon() {
        isSearchIcon = !isSearchIcon;
        return !isSearchIcon;
    }

    private void animateSearchIcon(boolean isSearch, FloatingActionButton floatingActionButton) {
        if (isSearch) {
            floatingActionButton.startAnimation(getFadeOutAnimation());
            floatingActionButton.setVisibility(View.GONE);
        } else {
            floatingActionButton.startAnimation(getFadeInAnimation());
            floatingActionButton.setVisibility(View.VISIBLE);
        }

    }

    public ArrayList<AutoCompleteAdapterHolder> getSuggestionArray() {
        List<SimpleSummoner> favorites = FavoritesSqLiteHelper.getAllFavorites();
        List<RecentSummoner> recent = RecentSearchSqlLiteHelper.getAllRecent();

        ArrayList<AutoCompleteAdapterHolder> strings = new ArrayList<>();
        if (favorites != null)
            for (SimpleSummoner favore : favorites) {
                strings.add(new AutoCompleteAdapterHolder(favore.getName(), AutoCompleteAdapterHolder.AutoCompleteType.FAVORITES));
            }
        if (recent != null)
            for (RecentSummoner thisRecent : recent) {
                if (!contains(strings, thisRecent.getName()))
                    strings.add(new AutoCompleteAdapterHolder(thisRecent.getName(), AutoCompleteAdapterHolder.AutoCompleteType.RECENT));
            }

        if (strings.size() > 0)
            return strings;

        return new ArrayList<>();
    }

    private boolean contains(ArrayList<AutoCompleteAdapterHolder> strings, String name) {
        if (strings != null) {
            for (AutoCompleteAdapterHolder string : strings) {
                if (name.equalsIgnoreCase(string.getName()))
                    return true;
            }
        }

        return false;
    }
}
