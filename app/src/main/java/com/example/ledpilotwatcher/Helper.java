package com.example.ledpilotwatcher;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.USAGE_STATS_SERVICE;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.util.Log;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Helper {
    public static boolean isNamedProcessRunning(final Context context,String processName){
        if (processName == null)
            return false;
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processes = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo process : processes) {
            if (processName.equals(process.processName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAppRunning(final Context context, final String packageName) {
        UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService(USAGE_STATS_SERVICE);
        long time = System.currentTimeMillis();
        // We get usage stats for the last 10 seconds
        List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000*5, time);
        // Sort the stats by the last time used
        Log.d("STATS : ", String.valueOf(stats.size()));
        if(stats != null) {

            SortedMap<Long,UsageStats> mySortedMap = new TreeMap<Long,UsageStats>();
            for (UsageStats usageStats : stats) {
                mySortedMap.put(usageStats.getLastTimeUsed(),usageStats);
                Log.d("RunningAppProcessInfo","Package name : "+usageStats.getPackageName());

                if(usageStats.getPackageName().equals("package name")) {
                    Log.d("Package ","Package name : Success");
                    return true;
                }

            }

        }else{
            return false;
        }
        return false;
    }
}
