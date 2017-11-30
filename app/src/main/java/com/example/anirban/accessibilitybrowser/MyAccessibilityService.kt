package com.example.anirban.accessibilitybrowser

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo


/**
 * Created by: anirban on 29/11/17.
 */

class MyAccessibilityService : AccessibilityService() {
    override fun onInterrupt() {

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        Log.d("tag", " DemoAccessibilityService change= " + event.action)
        if (AccessibilityEvent.eventTypeToString(event.eventType).contains("WINDOW")) {
            val nodeInfo = event.source
            dfs(nodeInfo)
        }

    }

    fun dfs(info: AccessibilityNodeInfo?) {

        if (info?.text != null && info.text.isNotEmpty()) {
            Log.d("tag", info.text.toString() + " class: " + info.className)

            if(info.text.toString().contains("quiph") && info.className.contains("EditText"))
                sendUpdateAppNotification(this,"hello",packageManager)
        }
        for (i in 0 until (info?.childCount?:0)) {
            val child = info?.getChild(i)
            dfs(child)
            child?.recycle()
        }
    }





}
