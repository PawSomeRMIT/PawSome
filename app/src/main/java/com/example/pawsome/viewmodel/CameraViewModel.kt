//package com.example.pawsome.viewmodel
//
//import android.content.Context
//import android.net.Uri
//import androidx.camera.core.CameraSelector
//import androidx.camera.core.ImageCapture
//import androidx.camera.core.ImageCaptureException
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.LifecycleOwner
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.google.common.util.concurrent.ListenableFuture
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.launch
//import java.io.File
//import java.text.SimpleDateFormat
//import java.util.Locale
//import javax.inject.Inject
//
//@HiltViewModel
//class CameraViewModel @Inject constructor() : ViewModel() {
//    var imageUri by mutableStateOf<Uri?>(null)
//    private var imageCapture: ImageCapture? = null
//
//    fun startCamera(
//        lifecycleOwner: LifecycleOwner,
//        cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
//    ) {
//        viewModelScope.launch {
//            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
//            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//
//            imageCapture = ImageCapture.Builder().build()
//
//            try {
//                // Unbind all use cases before rebinding
//                cameraProvider.unbindAll()
//
//                // Bind use cases to camera
//                cameraProvider.bindToLifecycle(
//                    lifecycleOwner,
//                    cameraSelector,
//                    imageCapture
//                )
//            } catch (exc: Exception) {
//                // Handle exceptions
//            }
//        }
//    }
//
//    fun takePicture(context: Context) {
//        val imageCapture = imageCapture ?: return
//
//        // Create a file to store the image
//        val photoFile = createFile(context.getExternalFilesDir(null), FILENAME_FORMAT)
//
//        // Create output options object which contains file + metadata
//        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
//
//
//        imageCapture.takePicture(
//            outputOptions,
//            ContextCompat.getMainExecutor(context),
//            object : ImageCapture.OnImageSavedCallback {
//                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
//                    val savedUri = output.savedUri ?: return
//                    imageUri = savedUri // Directly assign to imageUri
//                }
//
//                override fun onError(exc: ImageCaptureException) {
//                    // Handle error
//                }
//            }
//        )
//    }
//
//    companion object {
//        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
//        private fun createFile(baseFolder: File?, format: String): File {
//            return File(
//                baseFolder, SimpleDateFormat(format, Locale.US)
//                    .format(System.currentTimeMillis()) + ".jpg"
//            )
//        }
//    }
//}
