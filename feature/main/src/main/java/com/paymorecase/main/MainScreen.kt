package com.paymorecase.main

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymorecase.common.launchAppDetailsSettings
import com.paymorecase.domain.model.common.PaymentTypeEnum
import com.paymorecase.ui.component.PaymentButton
import com.paymorecase.ui.theme.PaymoreCaseTheme
import com.paymorecase.ui.utilty.ResponsivenessCheckerPreview
import com.paymorecase.ui.R as uiRes

@Composable
internal fun MainScreen(
    onNavigateToNFC: (PaymentTypeEnum) -> Unit,
    onNavigateToQR: (PaymentTypeEnum) -> Unit,
    onNavigateToSales: () -> Unit,
) {

    val context = LocalContext.current
    val activity = LocalActivity.current

    val permissionRequesterActivityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isCameraPermissionGranted ->
            when (isCameraPermissionGranted) {
                true -> onNavigateToQR(PaymentTypeEnum.QR_CODE)
                false -> Toast.makeText(
                    context,
                    uiRes.string.camera_permission_required,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PaymentButton(
            text = stringResource(uiRes.string.pay_with_credit_card_with_nfc),
            icon = painterResource(uiRes.drawable.ic_credit_card),
            onClick = { onNavigateToNFC(PaymentTypeEnum.CREDIT_CARD) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PaymentButton(
            text = stringResource(uiRes.string.pay_with_loyalty_card_with_nfc),
            icon = painterResource(uiRes.drawable.ic_loyalty),
            onClick = { onNavigateToNFC(PaymentTypeEnum.LOYALTY_CARD) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PaymentButton(
            text = stringResource(uiRes.string.pay_with_qr_code),
            icon = painterResource(uiRes.drawable.ic_qr),
            onClick = {
                handleCameraClick(
                    context = context,
                    onCameraPermissionGranted = { onNavigateToQR(PaymentTypeEnum.QR_CODE) },
                    onTheRationaleShowed = {
                        Toast.makeText(
                            context,
                            uiRes.string.camera_permission_required,
                            Toast.LENGTH_SHORT
                        ).show()

                        context.launchAppDetailsSettings()
                    },
                    permissionRequesterActivityResultLauncher = permissionRequesterActivityResultLauncher,
                    activity = activity
                )
            }
        )

        Spacer(modifier = Modifier.height(64.dp))

        FilledTonalButton(
            content = { Text(stringResource(uiRes.string.navigate_to_sales)) },
            onClick = { onNavigateToSales() }
        )
    }
}

@ResponsivenessCheckerPreview
@Composable
@Preview
private fun MainScreenPreview() {
    PaymoreCaseTheme {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainScreen(
                onNavigateToNFC = {},
                onNavigateToQR = {},
                onNavigateToSales = {}
            )
        }
    }
}