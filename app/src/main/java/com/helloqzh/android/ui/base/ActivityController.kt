package com.helloqzh.android.ui.base

import android.app.Activity
import com.helloqzh.android.MainActivity

object ActivityController {
    private val activities = ArrayList<Activity>()
    fun addActivity(activity: Activity) = activities.add(activity)

    fun removeActivity(activity: Activity) = activities.remove(activity)

    fun finishAll() {
        for (activity in activities){
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        activities.clear()
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    fun restartApp() {
        lateinit var mainActivity: MainActivity
        for (activity in activities){
            if (activity is MainActivity) {
                mainActivity = activity
                continue
            }
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        activities.clear()
        activities.add(mainActivity)
        mainActivity.recreate()
    }
}