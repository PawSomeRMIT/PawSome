package com.example.pawsome.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FailureScreen(
    errorType: String = "N/A",
    errorDesc: String = "N/A"
) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { TitleText(value = errorType) },
            text = { NormalText(value = errorDesc) },
            confirmButton = {
                TextButton(onClick = {
                    openDialog.value = false
                }) {
                   Text(text = "Retry")
                }
            },
            shape = RoundedCornerShape(12),
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewFailureScreen() {
    FailureScreen(errorType = "Authentication Fail", errorDesc = "Existing account with this email")
}