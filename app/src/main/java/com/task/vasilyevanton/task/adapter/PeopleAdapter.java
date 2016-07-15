package com.task.vasilyevanton.task.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.task.vasilyevanton.task.R;
import com.task.vasilyevanton.task.pojos.PeopleData;

import java.util.List;

public class PeopleAdapter extends ArrayAdapter<PeopleData> {

    protected List<PeopleData> mDataList;
    private Activity activity;
    private Integer mLayout;


    public PeopleAdapter(Activity context, Integer mLayout, List<PeopleData> dataList) {
        super(context, mLayout, dataList);
        this.activity = context;
        this.mLayout = mLayout;
        this.mDataList = dataList;
    }

    @Override
    public View getView(final int position, View mConvertView, ViewGroup parent) {

        final ViewHolder mHolder;
        if (mConvertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            mConvertView = inflater.inflate(this.mLayout, null, false);
            mHolder = new ViewHolder();
            //
            mHolder.mFullName = (TextView) mConvertView.findViewById(R.id.full_name);
            mHolder.mPosition = (TextView) mConvertView.findViewById(R.id.position);
            mHolder.mDepartment = (TextView) mConvertView.findViewById(R.id.department);
            //
            mConvertView.setTag(mHolder);

        } else {
            mHolder = (ViewHolder) mConvertView.getTag();
        }
        mHolder.mFullName.setText(mDataList.get(position).getFullName());
        mHolder.mPosition.setText(mDataList.get(position).getPosition());
        mHolder.mDepartment.setText(mDataList.get(position).getDepartment());
        if(mDataList.get(position).getDepartment().equals("Web")){
            mConvertView.setBackgroundResource(R.color.colorAccent);
        }
        if(mDataList.get(position).getDepartment().equals("Designing")){
            mConvertView.setBackgroundResource(R.color.colorPrimary);
        }
        if(mDataList.get(position).getDepartment().equals("Support")){
            mConvertView.setBackgroundResource(R.color.colorPrimaryDark);
        }
        Log.w("Data",mDataList.get(position).getId()+ " "+mDataList.get(position).getPosition()+ " "+mDataList.get(position).getDepartment() );
        return mConvertView;
    }

    static class ViewHolder {
        public TextView mFullName, mPosition, mDepartment;
    }

}
