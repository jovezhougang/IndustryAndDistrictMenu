package com.example.kkkkkkk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MenuExpandableListAdapter extends BaseExpandableListAdapter {
    private static boolean isLoadGangedMenu = false;
    private static List<Map.Entry<String, String>> one = new ArrayList<Map.Entry<String, String>>();
    private static HashMap<String, List<Map.Entry<String, String>>> two = new HashMap<String, List<Entry<String, String>>>();
    private static HashMap<String, List<Map.Entry<String, String>>> three = new HashMap<String, List<Entry<String, String>>>();
    private static HashMap<String, List<Map.Entry<String, String>>> four = new HashMap<String, List<Entry<String, String>>>();

    private static List<Map.Entry<String, String>> area_one = new ArrayList<Map.Entry<String, String>>();
    private static HashMap<String, List<Map.Entry<String, String>>> area_two = new HashMap<String, List<Entry<String, String>>>();
    private static HashMap<String, List<Map.Entry<String, String>>> area_three = new HashMap<String, List<Entry<String, String>>>();

    private LayoutInflater mLayoutInflater;
    /**
     * 0 代表左边自动显示菜单 1代表右边被动显示菜单的Adapter 2代表省市区 左边菜单Adapter
     */
    private int type = 0;
    /**
     * 左边选中的entry
     */
    private Map.Entry<String, String> entry;
    private int[] selectPostion = new int[] { -1, -1 };

    public MenuExpandableListAdapter(int type, Context context) {
        if (!isLoadGangedMenu) {
            isLoadGangedMenu = true;
            MyApplication.generateFourMenu(one, two, three, four);
            MyApplication
                    .generateThreeMenu(area_one, area_two, getArea_three());
        }
        this.type = type;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        if (0 == type) {
            return one.size();
        } else if (1 == type) {
            if (null == entry) {
                return 0;
            }
            return three.get(entry.getKey()).size();
        } else {
            return area_one.size();
        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (0 == type) {
            return two.get(one.get(groupPosition).getKey()).size();
        } else if (1 == type) {
            if (null == entry) {
                return 0;
            }
            return four.get(
                    three.get(entry.getKey()).get(groupPosition).getKey())
                    .size();
        } else {
            return area_two.get(area_one.get(groupPosition).getKey()).size();
        }

    }

    @Override
    public Object getGroup(int groupPosition) {
        if (0 == type) {
            return one.get(groupPosition);
        } else if (1 == type) {
            return three.get(entry.getKey()).get(groupPosition);
        } else {
            return area_one.get(groupPosition);
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (0 == type) {
            return two.get(one.get(groupPosition).getKey()).get(childPosition);
        } else if (1 == type) {
            return four.get(
                    three.get(entry.getKey()).get(groupPosition).getKey()).get(
                    childPosition);
        } else {
            return area_two.get(area_one.get(groupPosition).getKey()).get(
                    childPosition);
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    class Holder {
        TextView textView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        Holder holder = null;
        if (null == convertView) {
            holder = new Holder();
            convertView = mLayoutInflater.inflate(
                    R.layout.list_one_stair_menu_item, null, false);
            holder.textView = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        if (type == 0) {
            holder.textView.setText(one.get(groupPosition).getValue());
        } else if (1 == type) {
            holder.textView.setText(three.get(entry.getKey())
                    .get(groupPosition).getValue());
        } else {
            holder.textView.setText(area_one.get(groupPosition).getValue());
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (null == convertView) {
            holder = new Holder();
            convertView = mLayoutInflater.inflate(
                    R.layout.list_two_stair_menu_item, null, false);
            holder.textView = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        if (type == 0) {
            holder.textView.setText(two.get(one.get(groupPosition).getKey())
                    .get(childPosition).getValue());
        } else if (1 == type) {
            holder.textView.setText(four
                    .get(three.get(entry.getKey()).get(groupPosition).getKey())
                    .get(childPosition).getValue());
        } else {
            holder.textView.setText(area_two
                    .get(area_one.get(groupPosition).getKey())
                    .get(childPosition).getValue());
        }
        if (getSelectPostion()[0] == groupPosition
                && getSelectPostion()[1] == childPosition) {
            convertView.setBackgroundColor(Color.argb(100, 127, 204, 232));
        } else {
            convertView.setBackgroundColor(Color.argb(0, 0, 0, 0));
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @SuppressWarnings("unchecked")
    public void notifyDataSetChanged(Object object) {
        this.entry = (Entry<String, String>) object;
        super.notifyDataSetChanged();
    }

    public int[] getSelectPostion() {
        return selectPostion;
    }

    public void setSelectPostion(int groupPosition, int childPosition) {
        this.selectPostion[0] = groupPosition;
        this.selectPostion[1] = childPosition;
        notifyDataSetChanged();
    }

    public static HashMap<String, List<Map.Entry<String, String>>> getArea_three() {
        return area_three;
    }
}
