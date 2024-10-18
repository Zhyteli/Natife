package com.test.natife.presentation.sreens.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.test.natife.presentation.ui.theme.spacing

@Composable
fun SearchInTextField(
    text: String,
    modifier: Modifier,
    onValueChange: (TextFieldValue) -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue(text)) }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = modifier,
        value = textFieldValue,
        onValueChange = {
            textFieldValue = it
        },
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
        shape = RoundedCornerShape(14.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            cursorColor = MaterialTheme.colorScheme.primary,
            selectionColors = TextSelectionColors(
                handleColor = MaterialTheme.colorScheme.primary,
                backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
            ),
            errorBorderColor = MaterialTheme.colorScheme.error,
            errorCursorColor = MaterialTheme.colorScheme.error,
            errorTextColor = MaterialTheme.colorScheme.error
        ),
        placeholder = {
            Text(
                text = "funny cats",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onValueChange(textFieldValue)
                keyboardController?.hide()
            }
        )
    )

}

@Preview(showBackground = true)
@Composable
fun SearchInTextFieldPreview() {
    SearchInTextField(text = "",
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.small),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
    )
}