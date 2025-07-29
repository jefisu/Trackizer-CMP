# 🧠 AI Agent Instructions: Jetpack Compose Component Creation

This guide provides conventions for AI Agents to follow when creating **Jetpack Compose components**. The focus is on consistency, stateless design, and clear structure.

---

## ✅ General Rules

* **Stateless Components Only**: All components must be stateless.
* **Behavioral Logic**: If the component requires internal behavior (e.g. animation), encapsulate it *internally* within the Composable without affecting external state.
* **File Naming**: Use `PascalCase.kt`, matching the component name.
* **Composable Naming**: Must start with a capital letter. Example: `TrackizerButton()`.
* **No Business Logic**: Avoid use-case/domain logic inside the UI component.

---

## 🎨 UI & Styling

* Always accept a `Modifier` as a parameter and apply it *last* using `.then(...)`.
* Use theming from `TrackizerTheme` or equivalent if provided (e.g. spacing, typography, sizes).
* Avoid hardcoded dimensions/colors. Prefer `Dp`, `Color`, and `TextStyle` from the theme.

---

## 🔁 Component Variants

* If the component has multiple types (e.g., `Primary`, `Secondary`, `Loading`), use sealed classes or enums to represent them.
* Previews must demonstrate all visual variants.
* For multiple variations, implement `PreviewParameterProvider`. Example:

```kotlin
private class ButtonPreviewParameter : PreviewParameterProvider<ButtonType> {
    override val values get() = sequenceOf(ButtonType.Primary, ButtonType.Secondary)
}

@Preview
@Composable
private fun TrackizerButtonPreview(
    @PreviewParameter(ButtonPreviewParameter::class) type: ButtonType,
) {
    TrackizerTheme {
        TrackizerButton(
            text = "Get Started",
            onClick = {},
            type = type,
            modifier = Modifier.padding(TrackizerTheme.spacing.small),
            isLoading = false,
        )
    }
}
```

Include separate previews for specific states like `isLoading`, `disabled`, etc.

---

# ⚙️ Recommended Parameters (Updated)

- **`modifier: Modifier = Modifier`** → **Mandatory**
- Other parameters should be exposed **only if relevant** for component customization.
    - If exposed, they must have **default values**.
- Avoid exposing unnecessary parameters; prefer internal defaults when possible.
- Common examples with defaults:
    - `enabled: Boolean = true`
    - `isLoading: Boolean = false`
    - `contentPadding: PaddingValues = ButtonDefaults.ContentPadding`
    - `onClick: () -> Unit = {}` → only if necessary

---

## ✨ Optional Enhancements

If applicable:

* Support slot-based content with `@Composable content: @Composable () -> Unit`
* Use `remember`, `animate*AsState`, and `derivedStateOf` only when necessary
* Provide sensible accessibility defaults (like `contentDescription = null` if decorative)

---

## 📦 File Layout Example

File name: `TrackizerButton.kt`

Contents:

* `TrackizerButton(...)`
* `sealed class ButtonType(...)`
* Previews using `@Preview`
* Preview variants using `PreviewParameterProvider`

---

Ensure **simplicity** and **consistency** across all components. Avoid overengineering.
