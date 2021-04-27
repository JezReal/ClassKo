package app.netlify.accessdeniedgc.classko.util

fun formatTime(hour: Int, minute: Int): String {
    val formatHour: Int

    //TODO: refactor this atrocity
    when {
        hour > 12 -> {
            formatHour = (hour - 12)

            if (formatHour < 10) {
                //pad with zeros at the beginning if hour and/or min is less than 10
                return if (minute < 10) {
                    "0$formatHour : 0$minute PM"
                } else {
                    "0$formatHour : $minute PM"
                }
            }
            return if (minute < 10) {
                "$formatHour : 0$minute PM"
            } else {
                "$formatHour : $minute PM"
            }
        }
        hour in 1..11 -> {
            if (hour < 10) {
                return if (minute < 10) {
                    "0$hour : 0$minute AM"
                } else {
                    "0$hour : $minute AM"
                }
            }

            return if (minute < 10) {
                "$hour : 0$minute AM"
            } else {
                "$hour : $minute AM"
            }
        }
        hour == 12 -> {
            return if (minute < 10) {
                "$hour : 0$minute PM"
            } else {
                "$hour : $minute PM"
            }
        }

        // return 12 AM
        else -> return if (minute < 10) {
            "12 : 0$minute AM"
        } else {
            "12 : $minute AM"
        }
    }
}