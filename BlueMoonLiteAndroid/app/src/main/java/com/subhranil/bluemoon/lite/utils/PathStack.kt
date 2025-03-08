package com.subhranil.bluemoon.lite.utils

const val SEPARATOR = "\\"

class PathStack() {
    private var path = ""
    companion object {
        fun fromAbsolutePath(path: String): PathStack {
            val newPath = PathStack()
            newPath.path = path
            return newPath
        }
    }

    override fun toString(): String {
        return path
    }

    fun getAbsolutePath(): String {
        return this.withoutTrailingSeparator().toString()
    }

    fun push(path: String) {
        this.path = this.path + SEPARATOR + path
    }

    fun goBack() {
        if (path.isNotEmpty()) {
            val lastSlashIndex = path.lastIndexOf(SEPARATOR)
            path = if (lastSlashIndex == 0) {
                ""
            } else {
                path.substring(0, lastSlashIndex)
            }
        }
    }

    fun getExceptLast(): PathStack {
        val newPath = PathStack()
        if (path.isNotEmpty()) {
            val lastSlashIndex = path.lastIndexOf(SEPARATOR)
            newPath.path = if (lastSlashIndex == 0) {
                ""
            } else {
                path.substring(0, lastSlashIndex).trim()
            }
        }
        return newPath.withTrailingSeparator()
    }

    fun isNoWhere(): Boolean {
        return if (path.endsWith(SEPARATOR))
            path.split(SEPARATOR).size == 2
        else
            path.split(SEPARATOR).size == 1
    }
    private fun withTrailingSeparator(): PathStack {
        if (path.isNotEmpty() && !path.endsWith(SEPARATOR)) {
            path += SEPARATOR
        }
        return this
    }
    private fun withoutTrailingSeparator(): PathStack {
        if (this.path.length <= 3) return this
        if (path.isNotEmpty() && path.endsWith(SEPARATOR)) {
            path = path.substring(0, path.length - 1)
        }
        return this
    }
}


//class PathStack {
//    private val stack = mutableListOf<String>()
//
//    constructor(path: String) {
//        stack.addAll(path.split("/"))
//    }
//
//    constructor()
//
//    override fun toString(): String {
//        return stack.toString()
//    }
//
//    fun getAbsolutePath(): String {
//        return stack.joinToString("/")
//    }
//
//    fun fromAbsolutePath(path: String) {
//        stack.clear()
//        stack.addAll(path.split("/"))
//    }
//
//    fun push(path: String) {
//        stack.add(path)
//    }
//
//    //    fun pop(): String {
////        return stack.removeAt(stack.size - 1)
////    }
//    fun goBack() {
//        if (stack.size > 1) { // Prevent removing the last element if it’s the only one left
//            stack.removeAt(stack.size - 1)
//        }
//    }
//
//    fun getExceptLast(): PathStack {
//        val newPath = PathStack()
//
//        Log.d("PathStack", "Before: $stack")
//        if (stack.isNotEmpty()) { // Prevent errors if stack is empty
//            newPath.stack.addAll(stack.subList(0, stack.size - 1)) // Remove last element
//        }
//
//        Log.d("PathStack", "After: ${newPath.stack}")
//
//        return newPath
//    }
//
//
//    fun isNoWhere(): Boolean {
//        return stack.isEmpty()
//    }
//}