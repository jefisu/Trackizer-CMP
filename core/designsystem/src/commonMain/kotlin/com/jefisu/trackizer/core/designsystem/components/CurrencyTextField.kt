package com.jefisu.trackizer.core.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jefisu.trackizer.core.designsystem.BorderBrush
import com.jefisu.trackizer.core.designsystem.Gray20
import com.jefisu.trackizer.core.designsystem.Gray40
import com.jefisu.trackizer.core.designsystem.Gray60
import com.jefisu.trackizer.core.designsystem.Gray70
import com.jefisu.trackizer.core.designsystem.TrackizerTheme
import com.jefisu.trackizer.core.designsystem.util.CurrencyVisualTransformation
import com.jefisu.trackizer.core.ui.LocalSettings
import compose.icons.FeatherIcons
import compose.icons.feathericons.Minus
import compose.icons.feathericons.Plus
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CurrencyTextField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    maxLength: Int = 8,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    priceChangeStep: Int = 100,
) {
    val focusManager = LocalFocusManager.current
    val density = LocalDensity.current
    val imeInsets = WindowInsets.ime
    val isKeyboardVisible by remember {
        derivedStateOf {
            imeInsets.getBottom(density) > 0
        }
    }

    fun updatePrice(value: Int) {
        val newValue = (text.toIntOrNull() ?: 0) + value
        if (newValue > -1) onTextChange(newValue.toString())
    }

    fun handleTextChange(newValue: String) {
        val isDigit = newValue.all { it.isDigit() }
        val isValidLength = newValue.length <= maxLength
        val isValidValue = !newValue.startsWith("0")
        if (isDigit && isValidLength && isValidValue) {
            onTextChange(newValue)
        }
    }

    LaunchedEffect(isKeyboardVisible) {
        if (!isKeyboardVisible) {
            focusManager.clearFocus()
        }
    }

    CurrencyInputLayout(
        modifier = modifier,
        onDecrement = { updatePrice(-priceChangeStep) },
        onIncrement = { updatePrice(priceChangeStep) },
        textField = {
            CurrencyInputField(
                text = text,
                onTextChange = ::handleTextChange,
                label = label,
                keyboardActions = keyboardActions,
                keyboardOptions = keyboardOptions,
            )
        },
    )
}

@Composable
private fun CurrencyInputLayout(
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    textField: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ActionIcon(
            icon = FeatherIcons.Minus,
            onClick = onDecrement,
        )
        Box(modifier = Modifier.weight(1f)) {
            textField()
        }
        ActionIcon(
            icon = FeatherIcons.Plus,
            onClick = onIncrement,
        )
    }
}

@Composable
private fun CurrencyInputField(
    text: String,
    onTextChange: (String) -> Unit,
    label: String?,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
) {
    val settings = LocalSettings.current

    fun handleKeyEvent(event: KeyEvent): Boolean = when (event.key) {
        Key.Delete -> {
            onTextChange(text.dropLast(1))
            true
        }

        else -> false
    }

    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        modifier = Modifier.onPreviewKeyEvent(::handleKeyEvent),
        singleLine = true,
        visualTransformation = CurrencyVisualTransformation(settings.currency),
        cursorBrush = SolidColor(Color.White),
        textStyle = TrackizerTheme.typography.headline6.copy(
            textAlign = TextAlign.Center,
            color = Color.White,
        ),
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        decorationBox = { textFieldContent ->
            CurrencyFieldDecoration(
                label = label,
                content = textFieldContent,
            )
        },
    )
}

@Composable
private fun CurrencyFieldDecoration(label: String?, content: @Composable () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        label?.let { labelText ->
            Text(
                text = labelText,
                style = TrackizerTheme.typography.headline3,
                color = Gray40,
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
        content()
        Spacer(modifier = Modifier.height(TrackizerTheme.spacing.small))
        HorizontalDivider(
            color = Gray70,
            modifier = Modifier.width(TrackizerTheme.size.currencyDividerWidth),
        )
    }
}

@Composable
fun ActionIcon(icon: ImageVector, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = Gray20.copy(alpha = 0.05f),
        contentColor = Gray60,
        border = BorderStroke(
            width = 1.dp,
            brush = BorderBrush,
        ),
        modifier = modifier,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = when (icon) {
                FeatherIcons.Plus -> "Increase value"
                FeatherIcons.Minus -> "Decrease value"
                else -> null
            },
            modifier = Modifier
                .padding(4.dp)
                .size(52.dp),
        )
    }
}

@Preview
@Composable
private fun CurrencyTextFieldPreview() {
    var text by remember { mutableStateOf("") }

    TrackizerTheme {
        CurrencyTextField(
            text = text,
            onTextChange = { text = it },
            label = "Monthly Price",
            modifier = Modifier.padding(TrackizerTheme.spacing.medium),
        )
    }
}
