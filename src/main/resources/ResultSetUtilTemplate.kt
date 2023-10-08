package BASE_PACKAGE.util

import java.sql.ResultSet

fun ResultSet.getBooleanOrNull(columnName: String): Boolean? {
    val value = this.getBoolean(columnName)
    return if (this.wasNull()) {
        null
    } else {
        value
    }
}

fun ResultSet.getIntOrNull(columnName: String): Int? {
    val value = this.getInt(columnName)
    return if (this.wasNull()) {
        null
    } else {
        value
    }
}

fun ResultSet.getLongOrNull(columnName: String): Long? {
    val value = this.getLong(columnName)
    return if (this.wasNull()) {
        null
    } else {
        value
    }
}
