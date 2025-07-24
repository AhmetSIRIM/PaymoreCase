package com.paymorecase.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paymorecase.ui.standart.ScreenPaddingDimensions
import com.paymorecase.ui.theme.PaymoreCaseTheme
import com.paymorecase.ui.utilty.ResponsivenessCheckerPreview
import com.paymorecase.ui.R as uiRes

@Composable
internal fun MainScreen(
    uiState: MainContract.UiState,
) {
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(
                    horizontal = ScreenPaddingDimensions.extraLarge,
                    vertical = ScreenPaddingDimensions.large
                ),
        ) {
            Text(text = stringResource(uiRes.string.main_screen))
        }
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
                uiState = MainContract.UiState(),
            )
        }
    }
}