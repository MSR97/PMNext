package com.pomonext.pomonext.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pomonext.pomonext.ui.theme.LineGrayC
import com.pomonext.pomonext.ui.theme.PomoFirstC
import com.pomonext.pomonext.ui.theme.PomoSecC

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    labelId: String = "Email",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(
        modifier = modifier,
        valueState = emailState,
        labelId = labelId,
        enabled = enabled,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction,
    )

}

@Composable
fun PasswordInput(
    modifier: Modifier,
    passwordState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    passwordVisibility: MutableState<Boolean>,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    val visualTransformation =
        if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation()

    InputField(
        modifier = modifier,
        valueState = passwordState,
        labelId = labelId,
        enabled = enabled,
        keyboardType = KeyboardType.Password,
        imeAction = imeAction,
        onAction = onAction,
        visualTransformation = visualTransformation,
        trailingIcon = { PasswordVisibility(passwordVisibility = passwordVisibility) }


    )

}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value
    IconButton(onClick = { passwordVisibility.value = !!visible }) {
        Icons.Default.Close

    }
}


@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {

    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        label = { Text(text = labelId,color = Color.White) },
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp, color = Color.White),
        modifier = Modifier
            .background(Color.Transparent)
            .padding(bottom = 5.dp, start = 5.dp, end = 5.dp)
            .width(350.dp),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        visualTransformation = visualTransformation,
        trailingIcon = null,
        keyboardActions = onAction,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White)

        )

}

@Composable
fun AddPomoTaskFAB(onTap: () -> Unit) {
    FloatingActionButton(
        modifier = Modifier
            .shadow(shape = RoundedCornerShape(50), elevation = 5.dp),
        onClick = onTap,
        shape = RoundedCornerShape(50.dp),
        backgroundColor = Color.White
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add PomoTask",
            tint = PomoFirstC
        )

    }
}


@Composable
fun PomoPagesIndicator(from: String , to: String) {
    Box(
        Modifier
            .fillMaxWidth()
    ) {
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp),
        ) {
            val canvasWidth = size.width
            drawLine(
                start = Offset(x = 0f, y = 0f),
                end = Offset(x = canvasWidth, y = 0f),
                color = PomoSecC.copy(alpha = .4f),
                strokeWidth = 2F,
//                pathEffect = pathEffect
            )
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,
        ) {
            Surface(
                Modifier
                    .size(30.dp)
                    .shadow(shape = RoundedCornerShape(50), elevation = 5.dp),
                shape = RoundedCornerShape(50),
                color = Color.White
            ) {
                Text(
                    modifier = Modifier.padding(top = 6.5.dp),
                    text = "$from/$to",
                    textAlign = TextAlign.Center,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = PomoSecC

                )

            }


        }
    }

}