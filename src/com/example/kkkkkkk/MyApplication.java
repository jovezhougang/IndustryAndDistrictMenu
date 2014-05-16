package com.example.kkkkkkk;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import android.app.Application;
import android.widget.Toast;

public class MyApplication extends Application {
    private static TreeMap<String, String> industryMap = null;
    private static TreeMap<String, String> areaMap = null;

    @Override
    public void onCreate() {
        super.onCreate();
        loadIndustryCodeTable();
        loadAreaCodeTable();
    }

    void loadAreaCodeTable() {
        if (null == areaMap) {
            Properties mProperties = new Properties();
            InputStreamReader is = null;
            try {
                is = new InputStreamReader(getAssets().open(
                        "AreaCode.properties"), "UTF-8");
                mProperties.load(is);
                areaMap = new TreeMap<String, String>(new Comparator<String>() {

                    @Override
                    public int compare(String lhs, String rhs) {
                        return lhs.compareTo(rhs);
                    }
                });
                for (Entry<Object, Object> entry : mProperties.entrySet()) {
                    String value = (String) entry.getValue();
                    String key = (String) entry.getKey();
                    String[] keys = key.split("\\.");
                    areaMap.put(keys[1], value);
                }
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "IOException",
                        Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } finally {
                try {
                    if (null != is) {
                        is.close();
                        is = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void loadIndustryCodeTable() {
        if (null == industryMap) {
            InputStreamReader is = null;
            try {
                Properties mProperties = new Properties();
                is = new InputStreamReader(getAssets().open(
                        "IndustryType.properties"), "UTF-8");
                mProperties.load(is);
                industryMap = new TreeMap<String, String>(
                        new Comparator<String>() {
                            @Override
                            public int compare(String lhs, String rhs) {
                                return lhs.compareTo(rhs);
                            }
                        });
                for (Entry<Object, Object> entry : mProperties.entrySet()) {
                    String value = (String) entry.getValue();
                    String key = (String) entry.getKey();
                    String[] keys = key.split("\\.");
                    industryMap.put(keys[1], value);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != is) {
                        is.close();
                        is = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取行业投向四级联动菜单数据
     * 
     * @param one 
     * @param two
     * @param three
     * @param four
     */
    public static void generateFourMenu(List<Map.Entry<String, String>> one,
            HashMap<String, List<Map.Entry<String, String>>> two,
            HashMap<String, List<Map.Entry<String, String>>> three,
            HashMap<String, List<Map.Entry<String, String>>> four) {
        if (industryMap == null) {
            return;
        }
        Set<Map.Entry<String, String>> entries = industryMap.entrySet();
        for (Entry<String, String> entry : entries) {
            if (entry.getKey().length() == 1) {
                one.add(entry);
            } else if (entry.getKey().length() == 3) {
                String key = entry.getKey().substring(0, 1);
                List<Map.Entry<String, String>> list = null;
                if ((list = two.get(key)) == null) {
                    list = new ArrayList<Map.Entry<String, String>>();
                    list.add(entry);
                    two.put(key, list);
                } else {
                    list.add(entry);
                }
            } else if (entry.getKey().length() == 4) {
                String key = entry.getKey().substring(0, 3);
                List<Map.Entry<String, String>> list = null;
                if ((list = three.get(key)) == null) {
                    list = new ArrayList<Map.Entry<String, String>>();
                    list.add(entry);
                    three.put(key, list);
                } else {
                    list.add(entry);
                }
            } else if (entry.getKey().length() == 5) {
                String key = entry.getKey().substring(0, 4);
                List<Map.Entry<String, String>> list = null;
                if ((list = four.get(key)) == null) {
                    list = new ArrayList<Map.Entry<String, String>>();
                    list.add(entry);
                    four.put(key, list);
                } else {
                    list.add(entry);
                }
            }
        }
    }

    /**
     * 获取省市区三级联动菜单的数据
     * 
     * @param one
     * @param two
     * @param three
     */
    public static void generateThreeMenu(List<Map.Entry<String, String>> one,
            HashMap<String, List<Map.Entry<String, String>>> two,
            HashMap<String, List<Map.Entry<String, String>>> three) {
        if (areaMap == null) {
            return;
        }
        Set<Map.Entry<String, String>> entries = areaMap.entrySet();
        for (Entry<String, String> entry : entries) {
            if (entry.getKey().endsWith("0000")) {
                one.add(entry);
            } else if (entry.getKey().endsWith("00")) {
                String key = entry.getKey().substring(0, 2) + "0000";
                List<Map.Entry<String, String>> list = null;
                if ((list = two.get(key)) == null) {
                    list = new ArrayList<Map.Entry<String, String>>();
                    list.add(entry);
                    two.put(key, list);
                } else {
                    list.add(entry);
                }
            } else {
                String key = entry.getKey().substring(0, 4) + "00";
                List<Map.Entry<String, String>> list = null;
                if ((list = three.get(key)) == null) {
                    list = new ArrayList<Map.Entry<String, String>>();
                    list.add(entry);
                    three.put(key, list);
                } else {
                    list.add(entry);
                }
            }
        }
    }
}
