package com.jefisu.trackizer.core.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import compose.icons.FeatherIcons
import compose.icons.feathericons.Eye
import compose.icons.feathericons.EyeOff
import compose.icons.feathericons.X
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import trackizer.core.designsystem.generated.resources.Res
import trackizer.core.designsystem.generated.resources.password

@Composable
fun TrackizerPasswordTextField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    var isFocused by rememberSaveable { mutableStateOf(false) }
    var showPassword by rememberSaveable { mutableStateOf(false) }

    val textField: @Composable RowScope.() -> Unit = {
        Box(modifier = Modifier.weight(1f)) {
            BasicTextField(
                value = text,
                onValueChange = onTextChange,
                cursorBrush = SolidColor(LocalContentColor.current),
                singleLine = true,
                textStyle = LocalTextStyle.current,
                keyboardActions = keyboardActions,
                keyboardOptions = keyboardOptions,
                visualTransformation = if (showPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { isFocused = it.isFocused },
            )
        }
        Spacer(Modifier.width(TrackizerTheme.spacing.small))
        if (text.isNotEmpty()) {
            IconButton(
                onClick = { onTextChange("") },
                modifier = Modifier.offset(x = TrackizerTheme.spacing.small),
            ) {
                Icon(
                    imageVector = FeatherIcons.X,
                    contentDescription = "Clear text",
                )
            }
        }
        IconButton(onClick = { showPassword = !showPassword }) {
            Icon(
                imageVector = if (showPassword) {
                    FeatherIcons.EyeOff
                } else {
                    FeatherIcons.Eye
                },
                contentDescription = "Password visibility",
            )
        }
    }

    TrackizerTextFieldDefaults.DecorationBox(
        modifier = modifier,
        fieldName = stringResource(Res.string.password),
        isFocused = isFocused,
        textField = textField,
    )
}

@Preview
@Composable
private fun TrackizerPasswordTextFieldPreview() {
    TrackizerTheme {
        TrackizerPasswordTextField(
            text = "",
            onTextChange = {},
        )
    }
}
