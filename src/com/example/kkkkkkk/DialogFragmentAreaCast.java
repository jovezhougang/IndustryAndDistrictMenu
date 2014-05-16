package com.example.kkkkkkk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

public class DialogFragmentAreaCast extends DialogFragment implements
        OnClickListener {
    private ExpandableListView mExpandableListViewOne;
    private ListView mListViewTwo;
    private MenuExpandableListAdapter mJSNXEpandableListAdapterOne;
    private Entry<String, String> selectEntry;
    private View view;
    private Button okButton;
    private static HashMap<String, List<Map.Entry<String, String>>> area_three;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(0, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
    }

    private void clearSelectEntry() {
        selectEntry = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadView();
    }

    private void loadView() {
        View view = getView();
        mExpandableListViewOne = (ExpandableListView) view
                .findViewById(R.id.expandale_list_one);
        mExpandableListViewOne.setIndicatorBounds(10, 20);
        mExpandableListViewOne
                .setOnChildClickListener(new OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent,
                            View v, int groupPosition, int childPosition,
                            long id) {
                        int[] selectPostion = mJSNXEpandableListAdapterOne
                                .getSelectPostion();
                        if (selectPostion[0] != groupPosition
                                || selectPostion[1] != childPosition) {
                            mAreaThreeMenuAdapter
                                    .notifyDataSetChanged((Entry<String, String>) mJSNXEpandableListAdapterOne
                                            .getChild(groupPosition,
                                                    childPosition));
                            mJSNXEpandableListAdapterOne.setSelectPostion(
                                    groupPosition, childPosition);
                            clearSelectEntry();
                        }
                        return false;
                    }
                });
        mJSNXEpandableListAdapterOne = new MenuExpandableListAdapter(3,
                getActivity().getApplicationContext());
        area_three = mJSNXEpandableListAdapterOne.getArea_three();
        mExpandableListViewOne.setAdapter(mJSNXEpandableListAdapterOne);
        mListViewTwo = (ListView) view.findViewById(R.id.list_two);
        mListViewTwo.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                selectEntry = (Entry<String, String>) parent.getAdapter().getItem(position);
                mAreaThreeMenuAdapter.notifyDataSetChanged();
            }
        });
        mAreaThreeMenuAdapter = new AreaThreeMenuAdapter();
        mListViewTwo.setAdapter(mAreaThreeMenuAdapter);
        okButton = (Button) view.findViewById(R.id.ok);
        okButton.setOnClickListener(this);
    }

    private AreaThreeMenuAdapter mAreaThreeMenuAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater
                .inflate(R.layout.dialog_fragment_area_cast, null, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        Display mDisplay = ((WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        window.setLayout((mDisplay.getWidth() * 4)/5, (mDisplay.getHeight() * 4)/5);
        window.setGravity(Gravity.CENTER);
    }

    public int show(FragmentTransaction transaction, String tag, View view) {
        this.view = view;
        return super.show(transaction, tag);
    }

    public void show(FragmentManager manager, String tag, View view) {
        this.view = view;
        super.show(manager, tag);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.ok:
            if (null != view && selectEntry != null) {
                if (view instanceof TextView) {
                    ((TextView) view).setText(selectEntry.getValue());
                }
                view.setTag(R.string.select_entry, selectEntry);
            }
            dismiss();
            break;
        default:
            break;
        }
    }

    class AreaThreeMenuAdapter extends BaseAdapter {
        private Entry<String, String> leftSelectEntry;

        @Override
        public int getCount() {
            if (leftSelectEntry == null || area_three == null)
                return 0;
            else
                return area_three.get(leftSelectEntry.getKey()).size();
        }

        class Holder {
            TextView textView;
        }

        @Override
        public Object getItem(int position) {
            return area_three.get(leftSelectEntry.getKey()).get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (null == convertView) {
                holder = new Holder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.list_one_stair_menu_item, null, false);
                holder.textView = (TextView) convertView
                        .findViewById(R.id.text);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            if(((ListView)parent).isItemChecked(position)){
                convertView.setBackgroundColor(Color.argb(100, 127, 204, 232));
            }else{
                convertView.setBackgroundColor(Color.argb(0, 0, 0, 0));
            }
            holder.textView.setText(area_three.get(leftSelectEntry.getKey())
                    .get(position).getValue());
            return convertView;
        }

        public void notifyDataSetChanged(Entry<String, String> leftSelectEntry) {
            this.leftSelectEntry = leftSelectEntry;
            super.notifyDataSetChanged();
        }
    }
}
