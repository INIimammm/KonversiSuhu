package org.d3if3045.convertsuhu.model

import org.d3if3045.convertsuhu.db.SuhuEntity

fun SuhuEntity.hitungSuhu(): HasilKonversi {
    val inputSuhu = value
    val fromSuhu = from
    val toSuhu = to
    var hasil = 0f

    when (fromSuhu) {
        "Kelvin" -> {
            when (toSuhu) {
                "Celcius" -> hasil = (value - 273.15).toFloat()
                "Fahrenheit" -> hasil = ((value - 273.15) * 9 / 5 + 32).toFloat()
            }
        }
        "Celcius" -> {
            when (toSuhu) {
                "Fahrenheit" -> hasil = (inputSuhu * 9 / 5) + 32
                "Kelvin" -> hasil = (inputSuhu + 273.15).toFloat()
            }
        }
        "Fahrenheit" -> {
            when (toSuhu) {
                "Celcius" -> hasil = (value - 32) * 5 / 9
                "Kelvin" -> hasil = ((value - 32) * 5 / 9 + 273.15).toFloat()
            }
        }
    }
    return HasilKonversi(hasil)

}