package com.dongkun.braille.viewmodel

import android.os.Handler
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.dongkun.braille.Hangul
import com.dongkun.braille.Repository
import com.dongkun.braille.util.*

class MainViewModel(private val repository: Repository) : ViewModel() {

    val connected: LiveData<Boolean?>
        get() = repository.connected
    val progressState: LiveData<String>
        get() = repository.progressState
    var btnConnected = ObservableBoolean(false)
    var inProgressView = ObservableBoolean(false)

    var isDebug = ObservableBoolean(false)
    var txtProgress: ObservableField<String> = ObservableField("")

    private val _requestBleOn = MutableLiveData<Event<Boolean>>()
    val requestBleOn: LiveData<Event<Boolean>>
        get() = _requestBleOn

    val inProgress: LiveData<Event<Boolean>>
        get() = repository.inProgress

    val connectError: LiveData<Event<Boolean>>
        get() = repository.connectError

    val txtRead: ObservableField<String> = ObservableField("")
    val putTxt: LiveData<String>
        get() = repository.putTxt

    fun setInProgress(en: Boolean) {
        repository.inProgress.value = Event(en)
    }

    fun onClickConnect() {
        if (connected.value == false || connected.value == null) {
            if (repository.isBluetoothSupport()) {   // 블루투스 지원 체크
                if (repository.isBluetoothEnabled()) { // 블루투스 활성화 체크
                    //Progress Bar
                    setInProgress(true)
                    //디바이스 스캔 시작
                    repository.scanDevice()
                } else {
                    // 블루투스를 지원하지만 비활성 상태인 경우
                    // 블루투스를 활성 상태로 바꾸기 위해 사용자 동의 요청
                    _requestBleOn.value = Event(true)
                }
            } else { //블루투스 지원 불가
                Util.showNotification("Bluetooth is not supported.")
            }
        } else {
            repository.disconnect()
        }
    }

    fun unregisterReceiver() {
        repository.unregisterReceiver()
    }

    fun onClickSendData(sendTxt: String) {
        // 권동원 211101
        for (i in sendTxt.indices) {
            when {
                sendTxt[i].toString() == "것" -> {
                    repository.toArduino(Integer.parseInt("000", 2), Integer.parseInt("111", 2))
                    repository.toArduino(Integer.parseInt("011", 2), Integer.parseInt("100", 2))

                }
                else -> {

                    if (sendTxt[i].toString() >= "가" && sendTxt[i].toString() <= "힣") {
                        val cho = ((sendTxt[i] - '\uAC00') / 28) / 21   // 초성
                        val joong = (sendTxt[i] - '\uAC00') / 28 % 21   // 중성
                        val jong = (sendTxt[i] - '\uAC00') % 28         // 종성
                        val cho2 = Hangul().cho[cho]
                        val joong2 = Hangul().joong[joong]
                        val jong2 = Hangul().jong[jong]
                        when (cho2) {
                            "ㄱ" -> {
                                if (joong2 == "ㅏ") {
                                    repository.toArduino(
                                        Integer.parseInt("110", 2),
                                        Integer.parseInt("101", 2)
                                    )
                                    Hangul().checkJONG(jong)

                                } else {
                                    repository.toArduino(0, 4)
                                    Hangul().yageo(joong, jong)

                                }

                            }
                            "ㄲ" -> {
                                repository.toArduino(0, 1)
                                if (joong2 == "ㅏ") {
                                    repository.toArduino(
                                        Integer.parseInt("110", 2),
                                        Integer.parseInt("101", 2)
                                    )
                                    Hangul().checkJONG(jong)

                                } else {
                                    repository.toArduino(0, 4)
                                    Hangul().yageo(joong, jong)

                                }

                            }
                            "ㄴ" -> {
                                if (joong2 == "ㅏ") {
                                    repository.toArduino(
                                        Integer.parseInt("100", 2),
                                        Integer.parseInt("100", 2)
                                    )
                                    Hangul().checkJONG(jong)

                                } else {
                                    repository.toArduino(4, 4)
                                    Hangul().yageo(joong, jong)

                                }

                            }
                            "ㄷ" -> {
                                if (joong2 == "ㅏ") {
                                    repository.toArduino(
                                        Integer.parseInt("010", 2),
                                        Integer.parseInt("100", 2)
                                    )
                                    Hangul().checkJONG(jong)

                                } else {
                                    repository.toArduino(2, 4)
                                    Hangul().yageo(joong, jong)

                                }

                            }
                            "ㄸ" -> {
                                repository.toArduino(0, 1)
                                if (joong2 == "ㅏ") {
                                    repository.toArduino(
                                        Integer.parseInt("010", 2),
                                        Integer.parseInt("100", 2)
                                    )
                                    Hangul().checkJONG(jong)

                                } else {
                                    repository.toArduino(2, 4)
                                    Hangul().yageo(joong, jong)

                                }

                            }
                            "ㄹ" -> {
                                // '라'는 약자가 없음
//                        if (joong2 == "ㅏ") {
//                            repository.toArduino(
//                                Integer.parseInt("010", 2),
//                                Integer.parseInt("100", 2)
//                            )
//                            Hangul().checkJONG(jong)
//
//                        } else {
                                repository.toArduino(0, 2)
                                Hangul().yageo(joong, jong)

//                        }

                            }
                            "ㅁ" -> {
                                if (joong2 == "ㅏ") {
                                    repository.toArduino(
                                        Integer.parseInt("100", 2),
                                        Integer.parseInt("010", 2)
                                    )
                                    Hangul().checkJONG(jong)

                                } else {
                                    repository.toArduino(4, 2)
                                    Hangul().yageo(joong, jong)

                                }

                            }
                            "ㅂ" -> {
                                if (joong2 == "ㅏ") {
                                    repository.toArduino(
                                        Integer.parseInt("000", 2),
                                        Integer.parseInt("110", 2)
                                    )
                                    Hangul().checkJONG(jong)

                                } else {
                                    repository.toArduino(0, 6)
                                    Hangul().yageo(joong, jong)

                                }

                            }
                            "ㅃ" -> {
                                repository.toArduino(0, 1)
                                if (joong2 == "ㅏ") {
                                    repository.toArduino(
                                        Integer.parseInt("000", 2),
                                        Integer.parseInt("110", 2)
                                    )
                                    Hangul().checkJONG(jong)

                                } else {
                                    repository.toArduino(0, 6)
                                    Hangul().yageo(joong, jong)

                                }
                            }
                            "ㅅ" -> {
                                if (joong2 == "ㅏ") {
                                    repository.toArduino(
                                        Integer.parseInt("111", 2),
                                        Integer.parseInt("000", 2)
                                    )
                                    Hangul().checkJONG(jong)

                                } else {
                                    repository.toArduino(0, 1)
                                    Hangul().yageo(joong, jong)

                                }

                            }
                            "ㅆ" -> {
                                repository.toArduino(0, 1)
                                if (joong2 == "ㅏ") {
                                    repository.toArduino(
                                        Integer.parseInt("111", 2),
                                        Integer.parseInt("000", 2)
                                    )
                                    Hangul().checkJONG(jong)

                                } else {
                                    repository.toArduino(0, 1)
                                    Hangul().yageo(joong, jong)

                                }
                            }
                            "ㅇ" -> {
                                // '아'는 약자가 없음
//                            if (joong2 == "ㅏ") {
//                                repository.toArduino(
//                                    Integer.parseInt("111", 2),
//                                    Integer.parseInt("000", 2)
//                                )
//                                Hangul().checkJONG(jong)
//
//                            } else {
                                repository.toArduino(0, 0)
                                Hangul().yageo(joong, jong)

//                            }

                            }
                            "ㅈ" -> {
                                if (joong2 == "ㅏ") {
                                    repository.toArduino(
                                        Integer.parseInt("000", 2),
                                        Integer.parseInt("101", 2)
                                    )
                                    Hangul().checkJONG(jong)

                                } else {
                                    repository.toArduino(0, 5)
                                    Hangul().yageo(joong, jong)

                                }

                            }
                            "ㅉ" -> {
                                repository.toArduino(0, 1)
                                if (joong2 == "ㅏ") {
                                    repository.toArduino(
                                        Integer.parseInt("000", 2),
                                        Integer.parseInt("101", 2)
                                    )
                                    Hangul().checkJONG(jong)

                                } else {
                                    repository.toArduino(0, 5)
                                    Hangul().yageo(joong, jong)

                                }

                            }
                            "ㅊ" -> {
                                // '차'는 약자가 없음
//                            if (joong2 == "ㅏ") {
//                                repository.toArduino(
//                                    Integer.parseInt("011", 2),
//                                    Integer.parseInt("000", 2)
//                                )
//                                Hangul().checkJONG(jong)
//
//                            } else {
                                repository.toArduino(0, 3)
                                Hangul().yageo(joong, jong)

//                            }

                            }
                            "ㅋ" -> {
                                if (joong2 == "ㅏ") {
                                    repository.toArduino(
                                        Integer.parseInt("110", 2),
                                        Integer.parseInt("100", 2)
                                    )
                                    Hangul().checkJONG(jong)

                                } else {
                                    repository.toArduino(6, 4)
                                    Hangul().yageo(joong, jong)

                                }

                            }
                            "ㅌ" -> {
                                if (joong2 == "ㅏ") {
                                    repository.toArduino(
                                        Integer.parseInt("110", 2),
                                        Integer.parseInt("010", 2)
                                    )
                                    Hangul().checkJONG(jong)

                                } else {
                                    repository.toArduino(6, 2)
                                    Hangul().yageo(joong, jong)

                                }

                            }
                            "ㅍ" -> {
                                if (joong2 == "ㅏ") {
                                    repository.toArduino(
                                        Integer.parseInt("100", 2),
                                        Integer.parseInt("110", 2)
                                    )
                                    Hangul().checkJONG(jong)

                                } else {
                                    repository.toArduino(4, 6)
                                    Hangul().yageo(joong, jong)

                                }

                            }
                            "ㅎ" -> {
                                if (joong2 == "ㅏ") {
                                    repository.toArduino(
                                        Integer.parseInt("010", 2),
                                        Integer.parseInt("110", 2)
                                    )
                                    Hangul().checkJONG(jong)

                                } else {
                                    repository.toArduino(2, 6)
                                    Hangul().yageo(joong, jong)

                                }

                            }
                            else -> {
                                //                    toArduino(-1, -1)
                            }
                        }
                    }

                }
            }

        }

//        val byteArr = sendTxt.toByteArray(Charset.defaultCharset())
//        repository.sendByteData(byteArr)
        Util.showNotification("send data!")
    }

}
