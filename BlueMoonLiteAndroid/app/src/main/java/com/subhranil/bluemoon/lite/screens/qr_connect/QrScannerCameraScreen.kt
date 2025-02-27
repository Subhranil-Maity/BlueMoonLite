package com.subhranil.bluemoon.lite.screens.qr_connect

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.subhranil.bluemoon.lite.qr.BarcodeAnalyzer
import kotlinx.serialization.Serializable

@Serializable
object QrScannerCameraScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrScannerCameraScreen(
    viewModel: QrScannerViewModel,
    navHostController: NavHostController
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val onAction = viewModel::onAction
    val connectSheet = rememberModalBottomSheetState()

    val localContext = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(localContext)
    }
    if (state.shouldShowBottomSheet) {
        HostBottomSheet(
            connectSheet,
            onAction,
            state,
            navHostController
        )
    }
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val previewView = PreviewView(context)
            val preview = Preview.Builder().build()
            val selector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()

            preview.surfaceProvider = previewView.surfaceProvider

            val imageAnalysis = ImageAnalysis.Builder().build()
            imageAnalysis.setAnalyzer(
                ContextCompat.getMainExecutor(context),
                BarcodeAnalyzer() {
//                    Toast.makeText(context, "Scanned: $it", Toast.LENGTH_SHORT).show()
                    viewModel.checkServerConnection(it, navHostController)
                }
            )

            runCatching {
                cameraProviderFuture.get().bindToLifecycle(
                    lifecycleOwner,
                    selector,
                    preview,
                    imageAnalysis
                )
            }.onFailure {
                Log.e("CAMERA", "Camera bind error ${it.localizedMessage}", it)
            }
            previewView
        }
    )

}
