package com.example.kkkkkkk;

import java.util.Map.Entry;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;

public class DialogFragmentIndustryCast extends DialogFragment implements
        OnClickListener {
    private ExpandableListView mExpandableListViewOne;
    private ExpandableListView mExpandableListViewTwo;
    private MenuExpandableListAdapter mJSNXEpandableListAdapterOne;
    private MenuExpandableListAdapter mJSNXEpandableListAdapterTwo;
    private Entry<String, String> selectEntry;
    private View view;
    private Button okButton;

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
                            mJSNXEpandableListAdapterTwo
                                    .notifyDataSetChanged(mJSNXEpandableListAdapterOne
                                            .getChild(groupPosition,
                                                    childPosition));
                            mJSNXEpandableListAdapterOne.setSelectPostion(
                                    groupPosition, childPosition);
                            mJSNXEpandableListAdapterTwo.setSelectPostion(-1,
                                    -1);
                            clearSelectEntry();
                        }

                        return false;
                    }
                });
        mJSNXEpandableListAdapterOne = new MenuExpandableListAdapter(0,
                getActivity().getApplicationContext());
        mExpandableListViewOne.setAdapter(mJSNXEpandableListAdapterOne);
        mExpandableListViewTwo = (ExpandableListView) view
                .findViewById(R.id.expandale_list_two);
        mJSNXEpandableListAdapterTwo = new MenuExpandableListAdapter(1,
                getActivity().getApplicationContext());
        mExpandableListViewTwo.setAdapter(mJSNXEpandableListAdapterTwo);
        mExpandableListViewTwo.setIndicatorBounds(10, 20);
        mExpandableListViewTwo
                .setOnChildClickListener(new OnChildClickListener() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public boolean onChildClick(ExpandableListView parent,
                            View v, int groupPosition, int childPosition,
                            long id) {
                        selectEntry = (Entry<String, String>) mJSNXEpandableListAdapterTwo
                                .getChild(groupPosition, childPosition);
                        mJSNXEpandableListAdapterTwo.setSelectPostion(
                                groupPosition, childPosition);
                        return false;
                    }
                });
        okButton = (Button) view.findViewById(R.id.ok);
        okButton.setOnClickListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_industry_cast, null,
                false);
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
}
