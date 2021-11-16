package com.dongkun.braille.ui

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.dongkun.braille.R
import com.dongkun.braille.util.ACTIVITY_REQUEST_CODE
import com.dongkun.braille.databinding.ActivityMainBinding
import com.dongkun.braille.util.*
import com.dongkun.braille.viewmodel.MainViewModel
import com.googlecode.tesseract.android.TessBaseAPI
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.*


open class MainActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false
    private var mBtnCameraView: Button? = null
    private var mEditOcrResult: EditText? = null

    private val viewModel by viewModel<MainViewModel>()

    private var datapath = ""
    private var lang = ""
    var mBluetoothAdapter: BluetoothAdapter? = null
    var recv: String = ""

    var sTess: TessBaseAPI? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel


        // 뷰 선언
        mBtnCameraView = findViewById<Button>(R.id.btn_camera)
        mBtnCameraView!!.setOnClickListener(mClickLister);
        mEditOcrResult = findViewById<EditText>(R.id.txt_send)
        sTess = TessBaseAPI()


        // Tesseract 인식 언어를 한국어로 설정 및 초기화
        lang = "kor"
        datapath = "$filesDir/tesseract"

        if (checkFile(File(datapath + "/tessdata"))) {
            sTess!!.init(datapath, lang)
        }


        // Tesseract 인식 언어를 한국어로 설정 및 초기화


        // Tesseract 인식 언어를 한국어로 설정 및 초기화
        lang = "kor"
        datapath = "$filesDir/tesseract"

        if (!hasPermissions(this, PERMISSIONS)) {
            requestPermissions(PERMISSIONS, REQUEST_ALL_PERMISSION)
        }

        initObserving()

    }

    private fun checkFile(dir: File): Boolean {
        //디렉토리가 없으면 디렉토리를 만들고 그 후에 파일을 카피
        if (!dir.exists() && dir.mkdirs()) {
            copyFiles()
        }
        //디렉토리가 있지만 파일이 없으면 파일카피 진행
        if (dir.exists()) {
            val datafilepath = "$datapath/tessdata/$lang.traineddata"
            val datafile = File(datafilepath)
            if (!datafile.exists()) {
                copyFiles()
            }
        }
        return true
    }

    private fun copyFiles() {
        val assetMgr = this.assets
        var `is`: InputStream? = null
        var os: OutputStream? = null
        try {
            `is` = assetMgr.open("tessdata/$lang.traineddata")
            val destFile = "$datapath/tessdata/$lang.traineddata"
            os = FileOutputStream(destFile)
            val buffer = ByteArray(1024)
            var read: Int
            while (`is`.read(buffer).also { read = it } != -1) {
                os.write(buffer, 0, read)
            }
            `is`.close()
            os.flush()
            os.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == ACTIVITY_REQUEST_CODE) {
                // 받아온 OCR 결과 출력
                mEditOcrResult!!.setText(data!!.getStringExtra("STRING_OCR_RESULT"))
            }
        }
    }

    private val mClickLister: View.OnClickListener = object : View.OnClickListener {
        override fun onClick(v: View?) {

            // 버튼 클릭 시

            // Camera 화면 띄우기
            val mIttCamera = Intent(this@MainActivity, CameraView::class.java)
            startActivityForResult(mIttCamera, ACTIVITY_REQUEST_CODE)


        }
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (doubleBackToExitPressedOnce) {
                return super.onKeyDown(keyCode, event)
                viewModel.setInProgress(false)
            }

            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()

            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
            return true
        }
        return false
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                viewModel.onClickConnect()
            }
        }


    private fun initObserving() {

        //Progress
        viewModel.inProgress.observe(this, {
            if (it.getContentIfNotHandled() == true) {
                viewModel.inProgressView.set(true)
            } else {
                viewModel.inProgressView.set(false)
            }
        })
        //Progress text
        viewModel.progressState.observe(this, {
            viewModel.txtProgress.set(it)
        })

        //Bluetooth On 요청
        viewModel.requestBleOn.observe(this, {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startForResult.launch(enableBtIntent)

        })

        //Bluetooth Connect/Disconnect Event
        viewModel.connected.observe(this, {
            if (it != null) {
                if (it) {
                    viewModel.setInProgress(false)
                    viewModel.btnConnected.set(true)
                    Util.showNotification("디바이스와 연결되었습니다.")
                } else {
                    viewModel.setInProgress(false)
                    viewModel.btnConnected.set(false)
                    Util.showNotification("디바이스와 연결이 해제되었습니다.")
                }
            }
        })

        //Bluetooth Connect Error
        viewModel.connectError.observe(this, {
            Util.showNotification("Connect Error. Please check the device")
            viewModel.setInProgress(false)
        })

        //Data Receive
        viewModel.putTxt.observe(this, {
            if (it != null) {
                recv += it
                sv_read_data.fullScroll(View.FOCUS_DOWN)
                viewModel.txtRead.set(recv)
            }
        })
    }

    private fun hasPermissions(context: Context?, permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (context?.let { ActivityCompat.checkSelfPermission(it, permission) }
                != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    // Permission check
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_ALL_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permissions granted!", Toast.LENGTH_SHORT).show()
                } else {
                    requestPermissions(permissions, REQUEST_ALL_PERMISSION)
                    Toast.makeText(this, "Permissions must be granted", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.txtRead.set("here you can see the message come")
    }

    override fun onPause() {
        super.onPause()
        viewModel.unregisterReceiver()
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        viewModel.setInProgress(false)
//
//    }

}
