package ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.composables.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.lyrian.kotlinmultiplatformsandbox.android.core.constants.ComposeConstants

@Composable
fun ButtonOutlinedSmall(
    text: String,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    textVerticalPadding: Dp = 0.dp,
    textHorizontalPadding: Dp = 16.dp,
    textColor: Color = Color.DarkGray,
    borderStrokeColor: Color = Color.Black.copy(alpha = 0.2f)
) {
    TextButton(
        enabled = onClick != null,
        onClick = onClick ?: {},
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp
        ),
        border = BorderStroke(2.dp, borderStrokeColor),
        shape = RoundedCornerShape(ComposeConstants.SMALL_BUTTON_CORNER_PERCENT),
        contentPadding = PaddingValues(0.dp),
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.White,
            contentColor = Color.DarkGray
        )
    ) {
        Text(
            text = text.uppercase(),
            modifier = Modifier
                .padding(vertical = textVerticalPadding, horizontal = textHorizontalPadding),
            color = textColor
        )
    }
}