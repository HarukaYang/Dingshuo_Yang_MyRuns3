package com.example.dingshuo_yang_myruns3

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocalStorageViewModel: ViewModel() {
    val profilePhotoImage = MutableLiveData<Bitmap>()

}
