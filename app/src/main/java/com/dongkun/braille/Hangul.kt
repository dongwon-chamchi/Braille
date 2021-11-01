package com.dongkun.braille

import android.os.Handler

class Hangul(private val repository: Repository) {
    val CHO = arrayOf(
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

    val JOONG = arrayOf(
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

    val JONG = arrayOf(
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
        var joong2 = Hangul(repository).JOONG[joong]
        when (joong2) {
            "ㅏ" -> {
                repository.toArduino(6, 1, 2)

            }
            "ㅐ" -> {
                repository.toArduino(7, 2, 2)

            }
            "ㅑ" -> {
                repository.toArduino(1, 6, 2)

            }
            "ㅒ" -> {
                repository.toArduino(1, 6, 2)
                Handler().postDelayed({
                    repository.toArduino(7, 2, 2)
                }, 1000L)

            }
            "ㅓ" -> {
                repository.toArduino(3, 4, 2)

            }
            "ㅔ" -> {
                repository.toArduino(5, 6, 2)

            }
            "ㅕ" -> {
                repository.toArduino(4, 3, 2)

            }
            "ㅖ" -> {
                repository.toArduino(1, 4, 2)

            }
            "ㅗ" -> {
                repository.toArduino(5, 1, 2)

            }
            "ㅘ" -> {
                repository.toArduino(7, 1, 2)

            }
            "ㅙ" -> {
                repository.toArduino(7, 1, 2)
                Handler().postDelayed({
                    repository.toArduino(7, 2, 2)
                }, 1000L)

            }
            "ㅚ" -> {
                repository.toArduino(5, 7, 2)

            }
            "ㅛ" -> {
                repository.toArduino(1, 5, 2)

            }
            "ㅜ" -> {
                repository.toArduino(5, 4, 2)

            }
            "ㅝ" -> {
                repository.toArduino(7, 4, 2)

            }
            "ㅞ" -> {
                repository.toArduino(7, 4, 2)
                Handler().postDelayed({
                    repository.toArduino(7, 2, 2)
                }, 1000L)

            }
            "ㅟ" -> {
                repository.toArduino(5, 4, 2)
                Handler().postDelayed({
                    repository.toArduino(7, 2, 2)
                }, 1000L)

            }
            "ㅠ" -> {
                repository.toArduino(4, 5, 2)

            }
            "ㅡ" -> {
                repository.toArduino(2, 5, 2)

            }
            "ㅢ" -> {
                repository.toArduino(2, 7, 2)

            }
            "ㅣ" -> {
                repository.toArduino(5, 2, 2)

            }
            else -> {
                //                    toArduino(-1, -1)
            }
        }

    }

    fun checkJONG(jong: Int) {
        var jong2 = Hangul(repository).JONG[jong]
        when (jong2) {
            "" -> {
                // 종성이 없으면 아무것도 안보냄..?
//                repository.toArduino(0, 0, 3)

            }
            "ㄱ" -> {
                repository.toArduino(4, 0, 3)

            }
            "ㄲ" -> {
                repository.toArduino(4, 0, 3)
                Handler().postDelayed({
                    repository.toArduino(4, 0, 3)
                }, 1000L)

            }
            "ㄳ" -> {
                repository.toArduino(4, 0, 3)
                Handler().postDelayed({
                    repository.toArduino(1, 0, 3)
                }, 1000L)

            }
            "ㄴ" -> {
                repository.toArduino(2, 2, 3)

            }
            "ㄵ" -> {
                repository.toArduino(2, 2, 3)
                Handler().postDelayed({
                    repository.toArduino(5, 0, 3)
                }, 1000L)

            }
            "ㄶ" -> {
                repository.toArduino(2, 2, 3)
                Handler().postDelayed({
                    repository.toArduino(1, 3, 3)
                }, 1000L)

            }
            "ㄷ" -> {
                repository.toArduino(1, 2, 3)

            }
            "ㄹ" -> {
                repository.toArduino(2, 0, 3)

            }
            "ㄺ" -> {
                repository.toArduino(2, 0, 3)
                Handler().postDelayed({
                    repository.toArduino(4, 0, 3)
                }, 1000L)

            }
            "ㄻ" -> {
                repository.toArduino(2, 0, 3)
                Handler().postDelayed({
                    repository.toArduino(2, 1, 3)
                }, 1000L)

            }
            "ㄼ" -> {
                repository.toArduino(2, 0, 3)
                Handler().postDelayed({
                    repository.toArduino(6, 0, 3)
                }, 1000L)

            }
            "ㄽ" -> {
                repository.toArduino(2, 0, 3)
                Handler().postDelayed({
                    repository.toArduino(1, 0, 3)
                }, 1000L)

            }
            "ㄾ" -> {
                repository.toArduino(2, 0, 3)
                Handler().postDelayed({
                    repository.toArduino(3, 1, 3)
                }, 1000L)

            }
            "ㄿ" -> {
                repository.toArduino(2, 0, 3)
                Handler().postDelayed({
                    repository.toArduino(2, 3, 3)
                }, 1000L)

            }
            "ㅀ" -> {
                repository.toArduino(2, 0, 3)
                Handler().postDelayed({
                    repository.toArduino(1, 3, 3)
                }, 1000L)

            }
            "ㅁ" -> {
                repository.toArduino(2, 1, 3)

            }
            "ㅂ" -> {
                repository.toArduino(6, 0, 3)

            }
            "ㅄ" -> {
                repository.toArduino(6, 0, 3)
                Handler().postDelayed({
                    repository.toArduino(1, 0, 3)
                }, 1000L)

            }
            "ㅅ" -> {
                repository.toArduino(1, 0, 3)

            }
            "ㅆ" -> {
                repository.toArduino(
                    Integer.parseInt("001", 2),
                    Integer.parseInt("100", 2),
                    3
                )

            }
            "ㅇ" -> {
                repository.toArduino(3, 3, 3)

            }
            "ㅈ" -> {
                repository.toArduino(5, 0, 3)

            }
            "ㅊ" -> {
                repository.toArduino(3, 0, 3)

            }
            "ㅋ" -> {
                repository.toArduino(3, 2, 3)

            }
            "ㅌ" -> {
                repository.toArduino(3, 1, 3)

            }
            "ㅍ" -> {
                repository.toArduino(2, 3, 3)

            }
            "ㅎ" -> {
                repository.toArduino(1, 3, 3)

            }
            else -> {
                //                    toArduino(-1, -1)
            }
        }

    }

    fun eok() {
        repository.toArduino(
            Integer.parseInt("100", 2),
            Integer.parseInt("111", 2),
            1
        )

    }

    fun eon() {
        repository.toArduino(
            Integer.parseInt("011", 2),
            Integer.parseInt("111", 2),
            1
        )

    }

    fun eol() {
        repository.toArduino(
            Integer.parseInt("011", 2),
            Integer.parseInt("110", 2),
            1
        )

    }

    fun yeon() {
        repository.toArduino(
            Integer.parseInt("100", 2),
            Integer.parseInt("001", 2),
            1
        )

    }

    fun yeol() {
        repository.toArduino(
            Integer.parseInt("110", 2),
            Integer.parseInt("011", 2),
            1
        )

    }

    fun yeong() {
        repository.toArduino(
            Integer.parseInt("110", 2),
            Integer.parseInt("111", 2),
            1
        )

    }

    fun ok() {
        repository.toArduino(
            Integer.parseInt("101", 2),
            Integer.parseInt("101", 2),
            1
        )

    }

    fun on() {
        repository.toArduino(
            Integer.parseInt("111", 2),
            Integer.parseInt("011", 2),
            1
        )

    }

    fun ong() {
        repository.toArduino(
            Integer.parseInt("111", 2),
            Integer.parseInt("111", 2),
            1
        )

    }

    fun un() {
        repository.toArduino(
            Integer.parseInt("110", 2),
            Integer.parseInt("110", 2),
            1
        )

    }

    fun ul() {
        repository.toArduino(
            Integer.parseInt("111", 2),
            Integer.parseInt("101", 2),
            1
        )

    }

    fun eun() {
        repository.toArduino(
            Integer.parseInt("101", 2),
            Integer.parseInt("011", 2),
            1
        )

    }

    fun eul() {
        repository.toArduino(
            Integer.parseInt("011", 2),
            Integer.parseInt("101", 2),
            1
        )

    }

    fun _in() {
        repository.toArduino(
            Integer.parseInt("111", 2),
            Integer.parseInt("110", 2),
            1
        )

    }

    fun yageo(joong2: String, jong2: String, joong: Int, jong: Int) {
        if (joong2 == "ㅓ" && jong2 == "ㄱ") {
            Hangul(repository).eok()

        } else if (joong2 == "ㅓ" && jong2 == "ㄴ") {
            Hangul(repository).eon()

        } else if (joong2 == "ㅓ" && jong2 == "ㄹ") {
            Hangul(repository).eol()

        } else if (joong2 == "ㅕ" && jong2 == "ㄴ") {
            Hangul(repository).yeon()

        } else if (joong2 == "ㅕ" && jong2 == "ㄹ") {
            Hangul(repository).yeol()

        } else if (joong2 == "ㅕ" && jong2 == "ㅇ") {
            Hangul(repository).yeong()

        } else if (joong2 == "ㅗ" && jong2 == "ㄱ") {
            Hangul(repository).ok()

        } else if (joong2 == "ㅗ" && jong2 == "ㄴ") {
            Hangul(repository).on()

        } else if (joong2 == "ㅗ" && jong2 == "ㅇ") {
            Hangul(repository).ong()

        } else if (joong2 == "ㅜ" && jong2 == "ㄴ") {
            Hangul(repository).un()

        } else if (joong2 == "ㅜ" && jong2 == "ㄹ") {
            Hangul(repository).ul()

        } else if (joong2 == "ㅡ" && jong2 == "ㄴ") {
            Hangul(repository).eun()

        } else if (joong2 == "ㅡ" && jong2 == "ㄹ") {
            Hangul(repository).eul()

        } else if (joong2 == "ㅣ" && jong2 == "ㄴ") {
            Hangul(repository)._in()

        } else {
            Hangul(repository).checkJOONG(joong)
            Hangul(repository).checkJONG(jong)
        }

    }


}