package com.match.app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.match.app.customer.MonthView;
import com.matches.fitness.R;

import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.MonthHolder> {

    private Context mContext;
    private List<String> dates;

    public DateAdapter(Context context, List<String> dates) {
        mContext = context;
        this.dates = dates;
    }


    @Override
    public MonthHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.itemview_month, parent, false);
        return new MonthHolder(view);
    }

    @Override
    public void onBindViewHolder(MonthHolder holder, int position) {
        holder.monthView.setDate(dates.get(position));
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    @Override
    public int getItemViewType(int position) {
        return -1;
    }


    class MonthHolder extends RecyclerView.ViewHolder {
        private MonthView monthView;

        public MonthHolder(View itemView) {
            super(itemView);
            monthView = itemView.findViewById(R.id.view_month);
        }
    }
}
