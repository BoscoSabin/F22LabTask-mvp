package com.f22lab.task.interfaces

import android.view.View

interface AdapterItemClickListener {
    fun onItemClick(v: View, position: Int, data: Any, requestType: String)
}