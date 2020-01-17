package com.nek0mancer.finalexamproject

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Books(
    var bookId: String = "",
    var author: String? = "",
    var title: String? = "",
    var year: Int = 0
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "author" to author,
            "title" to title,
            "year" to year
        )
    }
}