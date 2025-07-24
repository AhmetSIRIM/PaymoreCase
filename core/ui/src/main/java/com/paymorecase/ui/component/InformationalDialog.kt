package com.paymorecase.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.paymorecase.ui.standart.ScreenPaddingDimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationalDialog(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    title: String,
    description: String,
    buttonTextAndActionPair: Pair<String, () -> Unit>,
) {

    // In order to solve the problem that InformationalDialog stays on the
    // screen for a long time when navigating to another place with the
    // button in InformationalDialog, this state is managed in the component
    var isInformationalDialogOpen by rememberSaveable { mutableStateOf(true) }

    if (!isInformationalDialogOpen) return

    BasicAlertDialog(
        onDismissRequest = { /* no-op */ }, // We decided not to allow the user to dismiss the Dialog
        modifier = modifier
    ) {
        ElevatedCard(
            shape = MaterialTheme.shapes.medium,
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(all = ScreenPaddingDimensions.medium),
            ) {
                Image(
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.CenterHorizontally),
                    painter = painterResource(icon),
                    contentDescription = null,
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(text = title)

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = description)

                Spacer(modifier = Modifier.height(48.dp))

                Button(
                    onClick = {
                        buttonTextAndActionPair.second()
                        isInformationalDialogOpen = false
                    },
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth(),
                ) {
                    Text(
                        text = buttonTextAndActionPair.first,
                    )
                }
            }
        }
    }
}