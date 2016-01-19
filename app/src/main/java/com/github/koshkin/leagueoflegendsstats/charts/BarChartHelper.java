package com.github.koshkin.leagueoflegendsstats.charts;

import com.github.koshkin.leagueoflegendsstats.models.match.BaseTimeLineDelta;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

/**
 * Created by tehras on 1/18/16.
 */
public class BarChartHelper {

    public static BarChart getBarChart(BaseTimeLineDelta baseTimeLineDelta, BarChart barChart, String label) {
        barChart.setData(getDataChart(baseTimeLineDelta, label));


        return barChart;
    }

    private static final String ZERO_TO_TEN = "0-10 min";
    private static final String TEN_TO_TWENTY = "10-20 min";
    private static final String TWENTY_TO_THIRTY = "20-30 min";
    private static final String THIRTY_PLUS = "30+ min";

    private static BarData getDataChart(BaseTimeLineDelta timeline, String label) {
        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<BarDataSet> barDataSets = new ArrayList<>();


        float zeroToTen = timeline.getZeroToTen();
        float tenToTwenty = timeline.getTenToTwenty();
        float twentyToThirty = timeline.getThirtyToEnd();
        float thirtyPlus = timeline.getThirtyToEnd();


        addBarSetsAndString(barDataSets, xVals, zeroToTen, ZERO_TO_TEN, label);
        addBarSetsAndString(barDataSets, xVals, tenToTwenty, TEN_TO_TWENTY, label);
        addBarSetsAndString(barDataSets, xVals, twentyToThirty, TWENTY_TO_THIRTY, label);
        addBarSetsAndString(barDataSets, xVals, thirtyPlus, THIRTY_PLUS, label);

        return new BarData(xVals, barDataSets);
    }

    private static void addBarSetsAndString(ArrayList<BarDataSet> barDataSets, ArrayList<String> xLabels, float data, String xLab, String label) {
        if (data == BaseTimeLineDelta.DEFAULT_VALUE)
            return;

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(data, 0));
        BarDataSet barDataSet = new BarDataSet(barEntries, label);

        barDataSets.add(barDataSet);
        xLabels.add(xLab);
    }
}
