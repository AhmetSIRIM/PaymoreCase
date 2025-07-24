package com.paymorecase.ui.component

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.paymorecase.ui.R
import com.paymorecase.ui.theme.PaymoreCaseTheme
import com.paymorecase.ui.utilty.ResponsivenessCheckerPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentTopBar(
    title: String,
    modifier: Modifier = Modifier,
    @DrawableRes
    navigationIcon: Int = R.drawable.ic_arrow_left,
    navigationIconAction: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = navigationIconAction,
            ) {
                Icon(
                    painter = painterResource(id = navigationIcon),
                    contentDescription = null,
                )
            }
        },
    )
}

@Preview(showBackground = true)
@ResponsivenessCheckerPreview
@Composable
private fun PaymentButtonPreview() {
    PaymoreCaseTheme {
        val context = LocalContext.current

        Scaffold(
            topBar = {
                PaymentTopBar(
                    title = "Home Page",
                    navigationIconAction = {
                        Toast.makeText(
                            context,
                            "Navigation Icon Clicked",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            )
        }
    }
}