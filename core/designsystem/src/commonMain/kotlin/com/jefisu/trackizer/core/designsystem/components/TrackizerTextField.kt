package com.jefisu.trackizer.core.designsystem.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.jefisu.trackizer.core.designsystem.Gray50
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import compose.icons.FeatherIcons
import compose.icons.feathericons.X
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TrackizerTextField(
    text: String,
    onTextChange: (String) -> Unit,
    fieldName: String,
    modifier: Modifier = Modifier,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
) {
    var isFocused by rememberSaveable { mutableStateOf(false) }

    val textField: @Composable RowScope.() -> Unit = {
        BasicTextField(
            value = text,
            onValueChange = onTextChange,
            cursorBrush = SolidColor(LocalContentColor.current),
            singleLine = true,
            textStyle = LocalTextStyle.current,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { isFocused = it.isFocused },
        )
        if (text.isNotEmpty()) {
            IconButton(onClick = { onTextChange("") }) {
                Icon(
                    imageVector = FeatherIcons.X,
                    contentDescription = "Clear text",
                )
            }
        }
    }

    TrackizerTextFieldDefaults.DecorationBox(
        fieldName = fieldName,
        modifier = modifier,
        isFocused = isFocused,
        textField = textField,
        horizontalAlignment = horizontalAlignment,
    )
}

@Preview
@Composable
private fun TrackizerTextFieldPreview() {
    var text by rememberSaveable { mutableStateOf("") }

    TrackizerTheme {
        TrackizerTextField(
            text = text,
            onTextChange = { text = it },
            fieldName = "E-mail",
        )
    }
}

internal object TrackizerTextFieldDefaults {

    @Composable
    fun DecorationBox(
        fieldName: String,
        isFocused: Boolean,
        modifier: Modifier = Modifier,
        activeColor: Color = Color.White,
        inactiveColor: Color = Gray50,
        fieldNameStyle: TextStyle = TrackizerTheme.typography.bodyMedium,
        horizontalAlignment: Alignment.Horizontal = Alignment.Start,
        textField: @Composable RowScope.() -> Unit,
    ) {
        val colorAnim by animateColorAsState(
            targetValue = if (isFocused) activeColor else inactiveColor,
            label = "TrackizerTextField",
        )

        ObserveKeyboard(isFocused)

        Column(
            horizontalAlignment = horizontalAlignment,
            modifier = modifier,
        ) {
            CompositionLocalProvider(
                LocalContentColor provides colorAnim,
                LocalTextStyle provides TrackizerTheme.typography.bodyLarge.copy(
                    color = colorAnim,
                ),
            ) {
                Text(
                    text = fieldName,
                    style = fieldNameStyle,
                    color = colorAnim,
                    fontWeight = FontWeight.Medium,
                )
                Spacer(Modifier.height(TrackizerTheme.spacing.small))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(TrackizerTheme.size.textFieldHeight)
                        .drawBehind {
                            drawRoundRect(
                                color = colorAnim,
                                cornerRadius = CornerRadius(16.dp.toPx()),
                                style = Stroke(width = 1.dp.toPx()),
                            )
                        }
                        .padding(
                            start = TrackizerTheme.spacing.medium,
                            end = 4.dp,
                        ),
                ) {
                    textField()
                }
            }
        }
    }

    @Composable
    private fun ObserveKeyboard(isFocused: Boolean) {
        val focusManager = LocalFocusManager.current
        val isVisibleKeyboard = WindowInsets.ime.getBottom(LocalDensity.current) > 0
        LaunchedEffect(isVisibleKeyboard) {
            if (!isVisibleKeyboard && isFocused) focusManager.clearFocus()
        }
    }
}
