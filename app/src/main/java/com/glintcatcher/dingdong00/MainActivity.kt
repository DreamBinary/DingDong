package com.glintcatcher.dingdong00

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.view.WindowCompat
import com.glintcatcher.dingdong00.ui.page.Main
import com.glintcatcher.dingdong00.ui.theme.MyTheme
import com.glintcatcher.dingdong00.utils.toast
import com.glintcatcher.dingdong00.viewmodel.InsertViewModel
import com.glintcatcher.dingdong00.viewmodel.RemindViewModel
import com.glintcatcher.dingdong00.viewmodel.TabViewModel
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
open class MainActivity : ComponentActivity() {
    private val REQUEST_SCAN = 2
    private val remindViewModel: RemindViewModel by viewModels()
    private val insertViewModel: InsertViewModel by viewModels()
    private val tabViewModel: TabViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val options = HmsScanAnalyzerOptions.Creator()
//            .setHmsScanTypes(HmsScan.DATAMATRIX_SCAN_TYPE).create()
        val registerImage =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                if (it[Manifest.permission.CAMERA] == true && it[Manifest.permission.READ_EXTERNAL_STORAGE] == true) {
                    ScanUtil.startScan(
                        this, REQUEST_SCAN,
                        HmsScanAnalyzerOptions.Creator().create()
                    )
                }
            }

        // 桌面快捷添加
        val shortcut = ShortcutInfoCompat.Builder(this, "id1")
            .setShortLabel("添加")
            .setLongLabel("快捷添加提醒")
            .setIcon(IconCompat.createWithResource(this, R.drawable.ic_launch))
            .setIntent(Intent(this, MainActivity::class.java).apply {
                action = Intent.ACTION_VIEW
            })
            .build()
        ShortcutManagerCompat.pushDynamicShortcut(this, shortcut)

        val expand = intent.action == Intent.ACTION_VIEW

        //设置为沉浸式状态栏
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MyTheme.colors.background
                ) {
                    Main(remindViewModel, insertViewModel, tabViewModel, expand) {
                        registerImage.launch(
                            arrayOf(
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                        )
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK || data == null) {
            return
        }
        if (requestCode == REQUEST_SCAN) {
            val hmsScan: HmsScan? = data.getParcelableExtra(ScanUtil.RESULT)
            hmsScan?.getOriginalValue()?.let {
                insertViewModel.getScanInfo(it)
                it.toast()
            }
        }
    }
}

