package com.example.news.main


fun main() {
    println(checkMe(intArrayOf(3, 2, 1, 0, 5)))
    println(checkMe(intArrayOf(3, 2, 2, 1, 2)))
    println(checkMe(intArrayOf(1, 2, 0, 2, 0, 5)))
}

fun checkMe(arr: IntArray): Boolean {
    var result = false
    var i = 0
    while (i < arr.size - 1) {
        i += arr[i]
        if ((arr[i] == 0) && (i < arr.size - 1)) {
            result = false
            break
        }
        if (i == arr.size - 1) {
            result = true
        }
    }
    return result
}

