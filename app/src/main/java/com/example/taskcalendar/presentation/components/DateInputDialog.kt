package com.example.taskcalendar.presentation.components

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun DateInputDialog(
    onDismiss: () -> Unit,
    onSubmit: (String, String) -> Unit
) {
    val titleText = remember { mutableStateOf("") }
    val descText = remember { mutableStateOf("") }

    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
        },
        text = {
            Column {
                Text("Enter Title")
                Spacer(modifier = Modifier.height(5.dp))
                TextField(
                    value = titleText.value,
                    onValueChange = { newText -> titleText.value = newText },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFE1E1E1),
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text("Enter Description")
                Spacer(modifier = Modifier.height(5.dp))
                TextField(
                    value = descText.value,
                    onValueChange = { newText -> descText.value = newText },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RectangleShape,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFE1E1E1),
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
            }

        },
        confirmButton = {
            Button(
                onClick = {
                    if(titleText.value.trim().isNotEmpty()){
                        onSubmit(titleText.value, descText.value)
                        onDismiss()
                    }
                    else{
                        Toast.makeText(context, "Title cannot be empty", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text("Submit")
            }
        }
    )
}
