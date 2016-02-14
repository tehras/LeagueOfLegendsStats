package com.github.koshkin.leagueoflegendsstats.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.utils.MatchUtils;
import com.github.koshkin.leagueoflegendsstats.utils.NullChecker;
import com.github.koshkin.leagueoflegendsstats.utils.NumberUtils;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tehras on 2/14/16.
 */
public class HorizontalBarStatView extends LinearLayout {
    private Context mContext;
    private TextView mTitle;
    private HorizontalBarChart mChart;

    public HorizontalBarStatView(Context context) {
        super(context);
        init(context, null);
    }

    public HorizontalBarStatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HorizontalBarStatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;

        String title = "";
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.HorizontalBarStatView, 0, 0);
            title = a.getString(R.styleable.HorizontalBarStatView_barChartTitle);
            a.recycle();
        }

        //To inflate the view
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_horizontal_bar_chart_layout, this);

        mTitle = (TextView) view.findViewById(R.id.horizontal_bar_chart_title);
        mChart = (HorizontalBarChart) view.findViewById(R.id.horizontal_bar_chart);

        if (!NullChecker.isNullOrEmpty(title))
            mTitle.setText(title);

        initChart(mChart);
    }

    public void setData(ArrayList<Float> allValues, float thisValue) {
        float average;
        float total = 0f;
        float highest = 0f;

        int rank = 1;

        for (Float f : allValues) {
            if (f > highest)
                highest = f;

            if (f > thisValue)
                rank++;

            total = total + f;
        }

        average = total / allValues.size();


        ArrayList<String> labels = new ArrayList<>();

        List<BarEntry> barEntry1 = new ArrayList<>();
        barEntry1.add(new BarEntry(average, 0));
        barEntry1.add(new BarEntry(highest, 1));
        barEntry1.add(new BarEntry(thisValue, 2));
        BarDataSet barDataSet1 = new BarDataSet(barEntry1, "Highest");
        barDataSet1.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                String output;
                if (value >= 1000) {
                    output = NumberUtils.oneDecimalSafely(value / 1000) + "k";
                } else {
                    output = String.valueOf(NumberUtils.oneDecimalSafely(value));
                }
                return output;
            }
        });
        barDataSet1.setColor(getResources().getColor(R.color.great));
        barDataSet1.setValueTextSize(10f);
        barDataSet1.setValueTextColor(getResources().getColor(R.color.elevation_background));
        barDataSet1.setAxisDependency(YAxis.AxisDependency.LEFT);

        //Labels
        labels.add("Average");
        labels.add("Highest");
        labels.add("Player(" + getRankText(rank) + "/" + allValues.size() + ")");

        barDataSet1.setColors(new int[]{R.color.average, R.color.great, MatchUtils.getColor(rank, allValues.size()).getColor()}, getContext());

        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);

        mChart.setData(new BarData(labels, dataSets));
        mChart.invalidate();
    }

    private String getRankText(int rank) {
        if (rank == 1)
            return rank + "st";
        else if (rank == 2)
            return rank + "nd";
        else if (rank == 3)
            return rank + "rd";
        else return rank + "th";
    }

    private void initChart(HorizontalBarChart horizontalBarChart) {
        horizontalBarChart.setDrawBarShadow(false);
        horizontalBarChart.setDrawGridBackground(false);

        horizontalBarChart.getXAxis().setEnabled(true);
        horizontalBarChart.getXAxis().setDrawAxisLine(false);
        horizontalBarChart.getXAxis().setDrawGridLines(false);
        horizontalBarChart.getXAxis().setGridColor(getResources().getColor(R.color.elevation_background));
        horizontalBarChart.setDrawValueAboveBar(false);
        horizontalBarChart.setBackgroundColor(getResources().getColor(R.color.elevation_background));
        horizontalBarChart.getAxisRight().setEnabled(false);
        horizontalBarChart.getAxisLeft().setEnabled(false);

        horizontalBarChart.setDescription("");
        horizontalBarChart.getLegend().setEnabled(false);
    }
}
