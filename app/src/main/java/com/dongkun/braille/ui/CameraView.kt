package com.dongkun.braille.ui

import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.hardware.SensorManager
import android.os.*
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dongkun.braille.R
import com.dongkun.braille.ui.MainActivity.Companion.sTess
import com.dongkun.braille.util.PERMISSIONS
import com.dongkun.braille.util.PERMISSION_REQUEST_CODE
import com.dongkun.braille.util.REQUEST_ALL_PERMISSION
import org.opencv.android.*
import org.opencv.core.Mat
import org.opencv.core.Rect
import org.opencv.imgproc.Imgproc

class CameraView : Activity(), CameraBridgeViewBase.CvCameraViewListener2 {
    private var imgInput: Mat? = null
    private val TAG = "opencv"
    private var mOpenCvCameraView: CameraBridgeViewBase? = null
    private var mStrOcrResult = ""

    private var mBtnOcrStart: Button? = null
    private var mBtnFinish: Button? = null
    private var mTextOcrResult: TextView? = null

    private var bmpResult: Bitmap? = null

    private var mOrientEventListener: OrientationEventListener? = null

    private var mRectRoi: Rect? = null

    private var mSurfaceRoi: SurfaceView? = null
    private var mSurfaceRoiBorder: SurfaceView? = null

    private var mRoiWidthT = 0.0
    private var mRoiHeightT = 0.0
    private var mRoiX = 0
    private var mRoiY = 0
    private var mdWscale = 0.0
    private var mdHscale = 0.0

    private var m_viewDeco: View? = null
    private var m_nUIOption = 0
    private var mRelativeParams: RelativeLayout.LayoutParams? = null

    private var mImageCapture: ImageView? = null
    private var mMatRoi: Mat? = null

    private var mStartFlag = false

    // 현재 회전 상태 (하단 Home 버튼의 위치)
    private enum class mOrientHomeButton {
        Right, Bottom, Left, Top
    }

    private var mCurrOrientHomeButton = mOrientHomeButton.Right

    private fun hasPermissions(permissions: Array<String>): Boolean {
        // 퍼미션 확인
        var result = -1
        for (i in permissions.indices) {
            result = ContextCompat.checkSelfPermission(applicationContext, PERMISSIONS[i])
        }
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestNecessaryPermissions(permissions: Array<String>) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_ALL_PERMISSION -> {
                //퍼미션을 거절했을 때 메시지 출력 후 종료
                if (!hasPermissions(PERMISSIONS)) {
                    Toast.makeText(applicationContext, "CAMERA PERMISSION FAIL", Toast.LENGTH_LONG)
                        .show()
                    finish()
                }
                return
            }
        }
    }

    private val mLoaderCallback: BaseLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            when (status) {
                SUCCESS -> {

                    // 퍼미션 확인 후 카메라 활성화
                    if (hasPermissions(PERMISSIONS)) mOpenCvCameraView?.enableView()
                }
                else -> {
                    super.onManagerConnected(status)
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_view)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        if (!hasPermissions(PERMISSIONS)) { //퍼미션 허가를 했었는지 여부를 확인
            requestNecessaryPermissions(PERMISSIONS) //퍼미션 허가안되어 있다면 사용자에게 요청
        } else {
            //이미 사용자에게 퍼미션 허가를 받음.
        }

        // 카메라 설정
        mOpenCvCameraView = findViewById<View>(R.id.activity_surface_view) as CameraBridgeViewBase
        mOpenCvCameraView!!.visibility = SurfaceView.VISIBLE
        mOpenCvCameraView!!.setCvCameraViewListener(this)
        mOpenCvCameraView!!.setCameraIndex(0) // front-camera(1),  back-camera(0)

        mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)


        //뷰 선언
        mBtnOcrStart = findViewById<View>(R.id.btn_ocrstart) as Button
        mBtnFinish = findViewById<View>(R.id.btn_finish) as Button

        mTextOcrResult = findViewById<View>(R.id.text_ocrresult) as TextView

        mSurfaceRoi = findViewById<View>(R.id.surface_roi) as SurfaceView
        mSurfaceRoiBorder = findViewById<View>(R.id.surface_roi_border) as SurfaceView

        mImageCapture = findViewById<View>(R.id.image_capture) as ImageView

        //풀스크린 상태 만들기 (상태바, 네비게이션바 없애고 고정시키기)
        m_viewDeco = window.decorView
        m_nUIOption = window.decorView.systemUiVisibility
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) m_nUIOption =
            m_nUIOption or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) m_nUIOption =
            m_nUIOption or View.SYSTEM_UI_FLAG_FULLSCREEN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) m_nUIOption =
            m_nUIOption or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        m_viewDeco!!.setSystemUiVisibility(m_nUIOption)


        mOrientEventListener = object : OrientationEventListener(
            this,
            SensorManager.SENSOR_DELAY_NORMAL
        ) {
            override fun onOrientationChanged(arg0: Int) {

                //방향센서값에 따라 화면 요소들 회전

                // 0˚ (portrait)
                if (arg0 >= 315 || arg0 < 45) {
                    rotateViews(270)
                    mCurrOrientHomeButton = CameraView.mOrientHomeButton.Bottom
                    // 90˚
                } else if (arg0 in 45..134) {
                    rotateViews(180)
                    mCurrOrientHomeButton = CameraView.mOrientHomeButton.Left
                    // 180˚
                } else if (arg0 in 135..224) {
                    rotateViews(90)
                    mCurrOrientHomeButton = CameraView.mOrientHomeButton.Top
                    // 270˚ (landscape)
                } else {
                    rotateViews(0)
                    mCurrOrientHomeButton = CameraView.mOrientHomeButton.Right
                }


                //ROI 선 조정
                mRelativeParams =
                    RelativeLayout.LayoutParams((mRoiWidthT + 5).toInt(), (mRoiHeightT + 5).toInt())
                mRelativeParams!!.setMargins(mRoiX, mRoiY, 0, 0)
                mSurfaceRoiBorder!!.layoutParams = mRelativeParams

                //ROI 영역 조정
                mRelativeParams =
                    RelativeLayout.LayoutParams((mRoiWidthT - 5).toInt(), (mRoiHeightT - 5).toInt())
                mRelativeParams!!.setMargins(mRoiX + 5, mRoiY + 5, 0, 0)
                mSurfaceRoi!!.layoutParams = mRelativeParams
            }
        }

        //방향센서 핸들러 활성화
        mOrientEventListener!!.enable()

        //방향센서 인식 오류 시, Toast 메시지 출력 후 종료
        if (!mOrientEventListener!!.canDetectOrientation()) {
            Toast.makeText(
                this, "Can't Detect Orientation",
                Toast.LENGTH_LONG
            ).show()
            finish()
        }


    }

    fun onClickButton(v: View) {
        when (v.id) {
            R.id.btn_ocrstart -> if (!mStartFlag) {
                // 인식을 새로 시작하는 경우

                // 버튼 속성 변경
                mBtnOcrStart!!.isEnabled = false
                mBtnOcrStart!!.text = "Working..."
                mBtnOcrStart!!.setTextColor(Color.LTGRAY)
                bmpResult =
                    Bitmap.createBitmap(mMatRoi!!.cols(), mMatRoi!!.rows(), Bitmap.Config.ARGB_8888)
                Utils.matToBitmap(mMatRoi, bmpResult)

                // 캡쳐한 이미지를 ROI 영역 안에 표시
                mImageCapture!!.visibility = View.VISIBLE
                mImageCapture!!.setImageBitmap(bmpResult)


                //Orientation에 따라 Bitmap 회전 (landscape일 때는 미수행)
                if (mCurrOrientHomeButton != mOrientHomeButton.Right) {
                    when (mCurrOrientHomeButton) {
                        mOrientHomeButton.Bottom -> bmpResult =
                            GetRotatedBitmap(bmpResult, 90)
                        mOrientHomeButton.Left -> bmpResult =
                            GetRotatedBitmap(bmpResult, 180)
                        mOrientHomeButton.Top -> bmpResult =
                            GetRotatedBitmap(bmpResult, 270)
                    }
                }
                AsyncTess().execute(bmpResult)
            } else {
                //Retry를 눌렀을 경우

                // ImageView에서 사용한 캡쳐이미지 제거
                mImageCapture!!.setImageBitmap(null)
                mTextOcrResult!!.setText(R.string.ocr_result_tip)
                mBtnOcrStart!!.isEnabled = true
                mBtnOcrStart!!.text = "Start"
                mBtnOcrStart!!.setTextColor(Color.WHITE)
                mStartFlag = false
            }
            R.id.btn_finish -> {
                //인식 결과물을 MainActivity에 전달하고 종료
                val intent = intent
                intent.putExtra("STRING_OCR_RESULT", mStrOcrResult)
                setResult(RESULT_OK, intent)
                mOpenCvCameraView!!.disableView()
                finish()
            }
        }
    }

    fun rotateViews(degree: Int) {
        mBtnOcrStart!!.rotation = degree.toFloat()
        mBtnFinish!!.rotation = degree.toFloat()
        mTextOcrResult!!.rotation = degree.toFloat()
        when (degree) {
            0, 180 -> {

                //ROI 크기 조정 비율 변경
                mdWscale = 1.toDouble() / 2
                mdHscale = 1.toDouble() / 2


                //결과 TextView 위치 조정
                mRelativeParams = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                mRelativeParams!!.setMargins(0, convertDpToPixel(20f), 0, 0)
                mRelativeParams!!.addRule(RelativeLayout.CENTER_HORIZONTAL)
                mTextOcrResult!!.layoutParams = mRelativeParams
            }
            90, 270 -> {
                mdWscale = 1.toDouble() / 4 //h (반대)
                mdHscale = 3.toDouble() / 4 //w
                mRelativeParams = RelativeLayout.LayoutParams(
                    convertDpToPixel(300f),
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                mRelativeParams!!.setMargins(convertDpToPixel(15f), 0, 0, 0)
                mRelativeParams!!.addRule(RelativeLayout.CENTER_VERTICAL)
                mTextOcrResult!!.layoutParams = mRelativeParams
            }
        }
    }

    private fun convertDpToPixel(dp: Float): Int {
        val resources = applicationContext.resources
        val metrics = resources.displayMetrics
        val px = dp * (metrics.densityDpi / 160f)
        return px.toInt()
    }

    @Synchronized
    fun GetRotatedBitmap(bitmap: Bitmap?, degrees: Int): Bitmap? {
        var bitmap = bitmap
        if (degrees != 0 && bitmap != null) {
            val m = Matrix()
            m.setRotate(degrees.toFloat(), bitmap.width.toFloat() / 2, bitmap.height.toFloat() / 2)
            try {
                val b2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, m, true)
                if (bitmap != b2) {
                    //bitmap.recycle(); (일반적으로는 하는게 옳으나, ImageView에 쓰이는 Bitmap은 recycle을 하면 오류가 발생함.)
                    bitmap = b2
                }
            } catch (ex: OutOfMemoryError) {
                // We have no memory to rotate. Return the original bitmap.
            }
        }
        return bitmap
    }

    override fun onPause() {
        super.onPause()
        if (mOpenCvCameraView != null) mOpenCvCameraView!!.disableView()
    }

    override fun onResume() {
        super.onResume()
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "onResume :: Internal OpenCV library not found.")
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, this, mLoaderCallback)
        } else {
            Log.d(TAG, "onResume :: OpenCV library found inside package. Using it!")
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mOpenCvCameraView != null) mOpenCvCameraView!!.disableView()
    }

    override fun onCameraViewStarted(width: Int, height: Int) {

    }

    override fun onCameraViewStopped() {

    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
        // 프레임 획득
        imgInput = inputFrame!!.rgba()


        // 가로, 세로 사이즈 획득
        mRoiWidthT = imgInput!!.size().width * mdWscale
        val mRoiWidth: Int = mRoiWidthT.toInt()
        mRoiHeightT = imgInput!!.size().height * mdHscale
        val mRoiHeight: Int = mRoiHeightT.toInt()


        // 사이즈로 중심에 맞는 X , Y 좌표값 계산
        mRoiX = (imgInput!!.size().width - mRoiWidth).toInt() / 2
        mRoiY = (imgInput!!.size().height - mRoiHeight).toInt() / 2

        // ROI 영역 생성
        mRectRoi = Rect(mRoiX, mRoiY, mRoiWidth, mRoiHeight)


        // ROI 영역 흑백으로 전환
        mMatRoi = imgInput!!.submat(mRectRoi)
        Imgproc.cvtColor(mMatRoi, mMatRoi, Imgproc.COLOR_RGBA2GRAY)
        Imgproc.cvtColor(mMatRoi, mMatRoi, Imgproc.COLOR_GRAY2RGBA)
        mMatRoi!!.copyTo(imgInput!!.submat(mRectRoi))
        return imgInput!!

    }

    private inner class AsyncTess : AsyncTask<Bitmap?, Int?, String>() {
        override fun doInBackground(vararg mRelativeParams: Bitmap?): String? {
            //Tesseract OCR 수행
            sTess?.setImage(bmpResult)
            return sTess?.utF8Text
        }

        override fun onPostExecute(result: String) {
            //완료 후 버튼 속성 변경 및 결과 출력
            mBtnOcrStart!!.isEnabled = true
            mBtnOcrStart!!.text = "Retry"
            mBtnOcrStart!!.setTextColor(Color.WHITE)
            mStartFlag = true
            mStrOcrResult = result
            mTextOcrResult!!.text = mStrOcrResult
        }
    }

}