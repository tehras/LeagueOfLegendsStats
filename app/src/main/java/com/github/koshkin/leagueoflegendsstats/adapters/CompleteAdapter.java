package com.github.koshkin.leagueoflegendsstats.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.koshkin.leagueoflegendsstats.R;
import com.github.koshkin.leagueoflegendsstats.models.AutoCompleteAdapterHolder;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Created by tehras on 1/20/16.
 * <p/>
 * Adapter
 */
public class CompleteAdapter extends ArrayAdapter<AutoCompleteAdapterHolder> {
    private final Context mContext;
    private final ArrayList<AutoCompleteAdapterHolder> mHolder;
    private final int mResource;
    private final LayoutInflater mInflater;

    public CompleteAdapter(Context context, int resource, ArrayList<AutoCompleteAdapterHolder> holder) {
        super(context, resource);
        mContext = context;
        mResource = resource;
        mHolder = holder;
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private Filter mFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((AutoCompleteAdapterHolder) resultValue).getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {
                ArrayList<AutoCompleteAdapterHolder> suggestions = new ArrayList<>();
                for (AutoCompleteAdapterHolder customer : mHolder) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (customer.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(customer);
                    }
                }

                results.values = suggestions;
                results.count = suggestions.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                // we have filtered results
                addAll((ArrayList<AutoCompleteAdapterHolder>) results.values);
            } else {
                // no filter, add entire original list back in
                addAll(new ArrayDeque<AutoCompleteAdapterHolder>());
            }
            notifyDataSetChanged();
        }
    };

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = mInflater.inflate(R.layout.array_adapter_list_dropdown, null);
        }

        AutoCompleteAdapterHolder holder = getItem(position);

        TextView name = (TextView) view.findViewById(R.id.text);
        name.setText(holder.getName());

        ImageView imageView = (ImageView) view.findViewById(R.id.star);
        switch (holder.getAutoCompleteType()) {
            case FAVORITES:
                imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_gray_star));
                break;
            case RECENT:
                imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_action_history));
                break;
        }


        return view;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }
}
