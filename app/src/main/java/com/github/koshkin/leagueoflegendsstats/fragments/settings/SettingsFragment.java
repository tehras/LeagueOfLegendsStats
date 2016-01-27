package com.github.koshkin.leagueoflegendsstats.fragments.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.koshkin.leagueoflegendsstats.BaseFragment;
import com.github.koshkin.leagueoflegendsstats.MainActivity;
import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.models.StaticDataHolder;
import com.github.koshkin.leagueoflegendsstats.models.Summoner;
import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.github.koshkin.leagueoflegendsstats.networking.Response;
import com.github.koshkin.leagueoflegendsstats.networking.URIHelper;
import com.github.koshkin.leagueoflegendsstats.views.CustomSpinner;
import com.github.koshkin.leagueoflegendsstats.views.MaterialButton;

/**
 * Created by tehras on 1/23/16.
 * You will be able to change things like regions, and setting my summoner
 */
public class SettingsFragment extends BaseFragment implements Request.RequestCallback {

    private ContentLoadingProgressBar progressBar;
    private MaterialButton materialButton;
    private View editTextContainer;
    private TextView tv;
    private EditText editText;

    @Override
    protected String getToolbarTitle() {
        return getActivity().getResources().getString(R.string.fragment_title_settings);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        initializeAndPopulateGeneralSettings(view);
        initializeAndPopulateSummonerView(view);

        return view;
    }

    private void initializeAndPopulateSummonerView(View view) {
        Summoner summoner = StaticDataHolder.getInstance(getActivity()).getMySummoner();

        tv = (TextView) view.findViewById(R.id.settings_summoner_name);
        editText = (EditText) view.findViewById(R.id.settings_summoner_name_edit);
        editTextContainer = view.findViewById(R.id.settings_summoner_name_edit_layout);
        materialButton = (MaterialButton) view.findViewById(R.id.settings_edit_button);
        progressBar = (ContentLoadingProgressBar) view.findViewById(R.id.settings_edit_button_progress_bar);

        if (summoner == null || summoner.getSummonerInfo() == null) {
            tv.setText(NO_SUMMONER);
        } else {
            editText.setText(summoner.getSummonerInfo().getName());
            tv.setText(String.format(SUMMONER, summoner.getSummonerInfo().getName()));
        }

        materialButton.setText(BUTTON_TEXT_EDIT);
        editTextContainer.setVisibility(View.INVISIBLE);
        tv.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        materialButton.setVisibility(View.VISIBLE);
        materialButton.setOnButtonClickListener(new View.OnClickListener() {
            public boolean isEditTextViable(EditText editText1) {
                return editText1.getText().toString().length() > 1;
            }

            @Override
            public void onClick(View v) {
                hideKeyboard();
                if (materialButton.getText().toString().equalsIgnoreCase(BUTTON_TEXT_EDIT)) {
                    materialButton.setText(BUTTON_TEXT_UPDATE);
                    tv.setVisibility(View.INVISIBLE);
                    editTextContainer.setVisibility(View.VISIBLE);
                    editTextContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_from_top));
                } else {
                    if (isEditTextViable(editText)) {
                        materialButton.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.show();
                        executeGetSummoner(SettingsFragment.this, editText.getText().toString());
                    } else {
                        switchToEditSummonerText();
                    }
                }
            }
        });

    }

    private void switchToEditSummonerText() {
        materialButton.setText(BUTTON_TEXT_EDIT);
        materialButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        progressBar.hide();
        tv.setVisibility(View.VISIBLE);
        tv.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_top));
        editTextContainer.setVisibility(View.GONE);
    }

    private static final String BUTTON_TEXT_EDIT = "Edit";
    private static final String BUTTON_TEXT_UPDATE = "Update";
    private static final String NO_SUMMONER = "No Summoner Set";
    private static final String SUMMONER = "Summoner: %s";

    private void initializeAndPopulateGeneralSettings(View view) {
        CustomSpinner spinner = (CustomSpinner) view.findViewById(R.id.settings_select_region);

        spinner.setChoices(getRegions());
        spinner.setSelectedOption(URIHelper.getCurrentRegion().getRegionDisplayName());
        spinner.setSpinnerListener(new CustomSpinner.CustomSpinnerListener() {
            @Override
            public void spinnerClicked(String text) {
                URIHelper.setCurrentRegion(URIHelper.Region.fromDisplayName(text));
                StaticDataHolder.getInstance(getActivity()).needsRefresh(true);
            }
        });
    }

    @Override
    public void finished(Response response, Request request) {
        switch (request.getURIHelper()) {
            case GET_SUMMONER:
                if (response.getStatus() == Response.Status.SUCCESS) {
                    Summoner summoner = (Summoner) response.getReturnedObject();
                    StaticDataHolder.getInstance(getActivity()).setMySummoner(summoner, getActivity());
                    ((MainActivity) getActivity()).updateNavigationView();
                    tv.setText(summoner.getSummonerInfo().getName());
                    switchToEditSummonerText();
                } else {
                    Toast.makeText(SettingsFragment.this.getActivity(), "No summoner id of that name is found", Toast.LENGTH_SHORT).show();
                    materialButton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
        }
    }

    public String[] getRegions() {
        String[] regions = new String[URIHelper.Region.values().length];

        int i = 0;
        for (URIHelper.Region region : URIHelper.Region.values()) {
            regions[i] = region.getRegionDisplayName();
            i++;
        }

        return regions;
    }
}
