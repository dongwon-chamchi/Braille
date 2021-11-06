package com.dongkun.braille

import android.os.Handler

class Hangul {
    val cho = arrayOf(
        "ㄱ",
        "ㄲ",
        "ㄴ",
        "ㄷ",
        "ㄸ",
        "ㄹ",
        "ㅁ",
        "ㅂ",
        "ㅃ",
        "ㅅ",
        "ㅆ",
        "ㅇ",
        "ㅈ",
        "ㅉ",
        "ㅊ",
        "ㅋ",
        "ㅌ",
        "ㅍ",
        "ㅎ"
    )

    val joong = arrayOf(
        "ㅏ",
        "ㅐ",
        "ㅑ",
        "ㅒ",
        "ㅓ",
        "ㅔ",
        "ㅕ",
        "ㅖ",
        "ㅗ",
        "ㅘ",
        "ㅙ",
        "ㅚ",
        "ㅛ",
        "ㅜ",
        "ㅝ",
        "ㅞ",
        "ㅟ",
        "ㅠ",
        "ㅡ",
        "ㅢ",
        "ㅣ"
    )

    val jong = arrayOf(
        "",
        "ㄱ",
        "ㄲ",
        "ㄳ",
        "ㄴ",
        "ㄵ",
        "ㄶ",
        "ㄷ",
        "ㄹ",
        "ㄺ",
        "ㄻ",
        "ㄼ",
        "ㄽ",
        "ㄾ",
        "ㄿ",
        "ㅀ",
        "ㅁ",
        "ㅂ",
        "ㅄ",
        "ㅅ",
        "ㅆ",
        "ㅇ",
        "ㅈ",
        "ㅊ",
        "ㅋ",
        "ㅌ",
        "ㅍ",
        "ㅎ"
    )

    fun checkJOONG(joong: Int) {
        val joong2 = Hangul().joong[joong]
        when (joong2) {
            "ㅏ" -> {
                Repository().toArduino(6, 1)

            }
            "ㅐ" -> {
                Repository().toArduino(7, 2)

            }
            "ㅑ" -> {
                Repository().toArduino(1, 6)

            }
            "ㅒ" -> {
                Repository().toArduino(1, 6)
                Handler().postDelayed({
                    Repository().toArduino(7, 2)
                }, 1000L)

            }
            "ㅓ" -> {
                Repository().toArduino(3, 4)

            }
            "ㅔ" -> {
                Repository().toArduino(5, 6)

            }
            "ㅕ" -> {
                Repository().toArduino(4, 3)

            }
            "ㅖ" -> {
                Repository().toArduino(1, 4)

            }
            "ㅗ" -> {
                Repository().toArduino(5, 1)

            }
            "ㅘ" -> {
                Repository().toArduino(7, 1)

            }
            "ㅙ" -> {
                Repository().toArduino(7, 1)
                Handler().postDelayed({
                    Repository().toArduino(7, 2)
                }, 1000L)

            }
            "ㅚ" -> {
                Repository().toArduino(5, 7)

            }
            "ㅛ" -> {
                Repository().toArduino(1, 5)

            }
            "ㅜ" -> {
                Repository().toArduino(5, 4)

            }
            "ㅝ" -> {
                Repository().toArduino(7, 4)

            }
            "ㅞ" -> {
                Repository().toArduino(7, 4)
                Handler().postDelayed({
                    Repository().toArduino(7, 2)
                }, 1000L)

            }
            "ㅟ" -> {
                Repository().toArduino(5, 4)
                Handler().postDelayed({
                    Repository().toArduino(7, 2)
                }, 1000L)

            }
            "ㅠ" -> {
                Repository().toArduino(4, 5)

            }
            "ㅡ" -> {
                Repository().toArduino(2, 5)

            }
            "ㅢ" -> {
                Repository().toArduino(2, 7)

            }
            "ㅣ" -> {
                Repository().toArduino(5, 2)

            }
            else -> {
                //                    toArduino(-1, -1)
            }
        }

    }

    fun checkJONG(jong: Int) {
        val jong2 = Hangul().jong[jong]
        when (jong2) {
            "" -> {
                // 종성이 없으면 아무것도 안보냄..?
//                Repository().toArduino(0, 0, 3)

            }
            "ㄱ" -> {
                Repository().toArduino(4, 0)

            }
            "ㄲ" -> {
                Repository().toArduino(4, 0)
                Handler().postDelayed({
                    Repository().toArduino(4, 0)
                }, 1000L)

            }
            "ㄳ" -> {
                Repository().toArduino(4, 0)
                Handler().postDelayed({
                    Repository().toArduino(1, 0)
                }, 1000L)

            }
            "ㄴ" -> {
                Repository().toArduino(2, 2)

            }
            "ㄵ" -> {
                Repository().toArduino(2, 2)
                Handler().postDelayed({
                    Repository().toArduino(5, 0)
                }, 1000L)

            }
            "ㄶ" -> {
                Repository().toArduino(2, 2)
                Handler().postDelayed({
                    Repository().toArduino(1, 3)
                }, 1000L)

            }
            "ㄷ" -> {
                Repository().toArduino(1, 2)

            }
            "ㄹ" -> {
                Repository().toArduino(2, 0)

            }
            "ㄺ" -> {
                Repository().toArduino(2, 0)
                Handler().postDelayed({
                    Repository().toArduino(4, 0)
                }, 1000L)

            }
            "ㄻ" -> {
                Repository().toArduino(2, 0)
                Handler().postDelayed({
                    Repository().toArduino(2, 1)
                }, 1000L)

            }
            "ㄼ" -> {
                Repository().toArduino(2, 0)
                Handler().postDelayed({
                    Repository().toArduino(6, 0)
                }, 1000L)

            }
            "ㄽ" -> {
                Repository().toArduino(2, 0)
                Handler().postDelayed({
                    Repository().toArduino(1, 0)
                }, 1000L)

            }
            "ㄾ" -> {
                Repository().toArduino(2, 0)
                Handler().postDelayed({
                    Repository().toArduino(3, 1)
                }, 1000L)

            }
            "ㄿ" -> {
                Repository().toArduino(2, 0)
                Handler().postDelayed({
                    Repository().toArduino(2, 3)
                }, 1000L)

            }
            "ㅀ" -> {
                Repository().toArduino(2, 0)
                Handler().postDelayed({
                    Repository().toArduino(1, 3)
                }, 1000L)

            }
            "ㅁ" -> {
                Repository().toArduino(2, 1)

            }
            "ㅂ" -> {
                Repository().toArduino(6, 0)

            }
            "ㅄ" -> {
                Repository().toArduino(6, 0)
                Handler().postDelayed({
                    Repository().toArduino(1, 0)
                }, 1000L)

            }
            "ㅅ" -> {
                Repository().toArduino(1, 0)

            }
            "ㅆ" -> {
                Repository().toArduino(
                    Integer.parseInt("001", 2),
                    Integer.parseInt("100", 2)
                )

            }
            "ㅇ" -> {
                Repository().toArduino(3, 3)

            }
            "ㅈ" -> {
                Repository().toArduino(5, 0)

            }
            "ㅊ" -> {
                Repository().toArduino(3, 0)

            }
            "ㅋ" -> {
                Repository().toArduino(3, 2)

            }
            "ㅌ" -> {
                Repository().toArduino(3, 1)

            }
            "ㅍ" -> {
                Repository().toArduino(2, 3)

            }
            "ㅎ" -> {
                Repository().toArduino(1, 3)

            }
            else -> {
                //                    toArduino(-1, -1)
            }
        }

    }

    fun eok() {
        Repository().toArduino(
            Integer.parseInt("100", 2),
            Integer.parseInt("111", 2)
        )

    }

    fun eon() {
        Repository().toArduino(
            Integer.parseInt("011", 2),
            Integer.parseInt("111", 2)
        )

    }

    fun eol() {
        Repository().toArduino(
            Integer.parseInt("011", 2),
            Integer.parseInt("110", 2)
        )

    }

    fun yeon() {
        Repository().toArduino(
            Integer.parseInt("100", 2),
            Integer.parseInt("001", 2)
        )

    }

    fun yeol() {
        Repository().toArduino(
            Integer.parseInt("110", 2),
            Integer.parseInt("011", 2)
        )

    }

    fun yeong() {
        Repository().toArduino(
            Integer.parseInt("110", 2),
            Integer.parseInt("111", 2)
        )

    }

    fun ok() {
        Repository().toArduino(
            Integer.parseInt("101", 2),
            Integer.parseInt("101", 2)
        )

    }

    fun on() {
        Repository().toArduino(
            Integer.parseInt("111", 2),
            Integer.parseInt("011", 2)
        )

    }

    fun ong() {
        Repository().toArduino(
            Integer.parseInt("111", 2),
            Integer.parseInt("111", 2)
        )

    }

    fun un() {
        Repository().toArduino(
            Integer.parseInt("110", 2),
            Integer.parseInt("110", 2)
        )

    }

    fun ul() {
        Repository().toArduino(
            Integer.parseInt("111", 2),
            Integer.parseInt("101", 2)
        )

    }

    fun eun() {
        Repository().toArduino(
            Integer.parseInt("101", 2),
            Integer.parseInt("011", 2)
        )

    }

    fun eul() {
        Repository().toArduino(
            Integer.parseInt("011", 2),
            Integer.parseInt("101", 2)
        )

    }

    fun in_() {
        Repository().toArduino(
            Integer.parseInt("111", 2),
            Integer.parseInt("110", 2)
        )

    }

    fun yageo(joong: Int, jong: Int) {
        val joong2 = Hangul().joong[joong]
        val jong2 = Hangul().jong[jong]
        if (joong2 == "ㅓ" && jong2 == "ㄱ") {
            Hangul().eok()

        } else if (joong2 == "ㅓ" && jong2 == "ㄴ") {
            Hangul().eon()

        } else if (joong2 == "ㅓ" && jong2 == "ㄹ") {
            Hangul().eol()

        } else if (joong2 == "ㅕ" && jong2 == "ㄴ") {
            Hangul().yeon()

        } else if (joong2 == "ㅕ" && jong2 == "ㄹ") {
            Hangul().yeol()

        } else if (joong2 == "ㅕ" && jong2 == "ㅇ") {
            Hangul().yeong()

        } else if (joong2 == "ㅗ" && jong2 == "ㄱ") {
            Hangul().ok()

        } else if (joong2 == "ㅗ" && jong2 == "ㄴ") {
            Hangul().on()

        } else if (joong2 == "ㅗ" && jong2 == "ㅇ") {
            Hangul().ong()

        } else if (joong2 == "ㅜ" && jong2 == "ㄴ") {
            Hangul().un()

        } else if (joong2 == "ㅜ" && jong2 == "ㄹ") {
            Hangul().ul()

        } else if (joong2 == "ㅡ" && jong2 == "ㄴ") {
            Hangul().eun()

        } else if (joong2 == "ㅡ" && jong2 == "ㄹ") {
            Hangul().eul()

        } else if (joong2 == "ㅣ" && jong2 == "ㄴ") {
            Hangul().in_()

        } else {
            Hangul().checkJOONG(joong)
            Hangul().checkJONG(jong)
        }

    }


}