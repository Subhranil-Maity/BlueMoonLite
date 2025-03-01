package com.subhranil.bluemoon.lite.utils

import com.subhranil.bluemoon.lite.models.Drive

class PathStack {
    private val stack = mutableListOf<String>()
    fun getAbsolutePath(): String {
        return stack.joinToString("/")
    }
    fun fromAbsolutePath(path: String) {
        stack.clear()
        stack.addAll(path.split("/"))
    }
    fun push(path: String) {
        stack.add(path)
    }
//    fun pop(): String {
//        return stack.removeAt(stack.size - 1)
//    }
    fun goBack() {
        if (stack.size > 0) {
            stack.removeAt(stack.size - 1)
        }
    }
    fun isNoWhere(): Boolean {
        return stack.isEmpty()
    }
}