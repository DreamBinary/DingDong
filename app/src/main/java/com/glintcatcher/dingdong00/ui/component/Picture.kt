package com.glintcatcher.dingdong00.ui.component

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest


@Composable
fun TakePhoto() {

    val context = LocalContext.current
    val bitmap: MutableState<Bitmap?> = remember {
        mutableStateOf(null)
    }
    val uri: MutableState<Uri?> = remember {
        mutableStateOf(null)
    }
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context).data(uri.value).build()
    )
    val takePhoto = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = {
            bitmap.value = it
        }
    )
    val pickPhoto =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument(),
            onResult = {
                it?.let {
                    uri.value = it
                }
            }
        )

    val permission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {
            if (it[Manifest.permission.CAMERA] == true) {
                takePhoto.launch(null)
            }
            if (it[Manifest.permission.READ_EXTERNAL_STORAGE] == true) {
                pickPhoto.launch(arrayOf("image/*"))
            }
        }
    )

    AnimatedVisibility(uri.value != null) {
        Image(painter = painter, contentDescription = null)
    }

}