package com.pomonext.pomonext.screens.login

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pomonext.pomonext.R
import com.pomonext.pomonext.components.EmailInput
import com.pomonext.pomonext.components.PasswordInput
import com.pomonext.pomonext.navigation.PomoScreens
import com.pomonext.pomonext.ui.theme.PomoFirstC
import com.pomonext.pomonext.utils.isEmailAndPasswordValid
import com.pomonext.pomonext.widgets.SocialMediaLogos
import com.pomonext.pomonext.widgets.TomatoLogo

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val showLoginForm = rememberSaveable() { mutableStateOf(true) }
    val resize = remember {
        Animatable(0f)

    }
    val heightSize = remember {
        mutableStateOf(420.dp)

    }
    val context = LocalContext.current

    Box(
        modifier = Modifier

            .fillMaxWidth()
            .fillMaxHeight()
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .background(
                Color(
                    red = 0.7372549176216125f,
                    green = 0f,
                    blue = 0.27843138575553894f,
                    alpha = 1f
                )
            )

            .padding(start = 0.dp, top = 0.dp, end = 0.dp, bottom = 0.dp)

            .alpha(1f)
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TomatoLogo(modifier = Modifier.size(200.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    //Initial Height
                    .height(heightSize.value)
                    .clip(
                        RoundedCornerShape(
                            topStart = 1.dp,
                            topEnd = 1.dp,
                            bottomStart = 200.dp,
                            bottomEnd = 200.dp
                        )
                    )

                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFBC0047),
                                Color(0xFFFFFFFF),

                                )
                        )
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(top = 0.dp)
                ) {

                    if (showLoginForm.value) {
                        heightSize.value = 420.dp
                        UserForm(
                            loading = false,
                            isCreateAccount = false,
                            context
                        ) { email, password ->
                            //FireBase login
                            onFirebaseLogin(loginViewModel, email, password, navController)

                        }
                        ForgotPassword()


                    } else {
                        heightSize.value = 450.dp
                        UserForm(
                            loading = false,
                            isCreateAccount = true,
                            context
                        ) { email, password ->
                            //FireBase Create Account
                            onFirebaseCreateAccount(loginViewModel, email, password, navController)
                        }
                    }
                    CreateNewAccount(showLoginForm.value) { isOnLogin ->
                        showLoginForm.value = isOnLogin
                    }

                }


            }
            Spacer(modifier = Modifier.size(5.dp))
            SignUp()
            Spacer(modifier = Modifier.size(20.dp))
            SocialMediaLogin()


        }

    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    context: Context = LocalContext.current,
    onDone: (String, String) -> Unit = { email, pwd -> }
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val confirmPassword = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()

    }
    val validationResult = isEmailAndPasswordValid(
        isCreateAccount,
        email.value,
        password.value,
        confirmPassword.value
    )
    val modifier = Modifier
        .fillMaxHeight()

        .verticalScroll(rememberScrollState())

    if (isCreateAccount)
        Text(
            text = stringResource(R.string.create_acct_help),
            modifier = Modifier.padding(10.dp),
            color = Color.White,
            fontSize = 16.sp
        ) else Text(
        text = ""
    )
    EmailInput(
        emailState = email,
        enabled = !loading,
        onAction = KeyboardActions {
            focusManager.moveFocus(FocusDirection.Down)

        })
    Spacer(modifier = Modifier.size(5.dp))
    PasswordInput(
        modifier = Modifier
            .focusTarget(),
        passwordState = password,
        labelId = "Password",
        enabled = !loading,
        passwordVisibility = passwordVisibility,
        imeAction = if (isCreateAccount) ImeAction.Next else ImeAction.Done,
        onAction = KeyboardActions {
            onSubmitButton(
                isCreateAccount,
                focusManager,
                validationResult,
                onDone,
                email,
                password,
                context
            )
        }
    )
    Spacer(modifier = Modifier.size(5.dp))

    if (isCreateAccount) {
        PasswordInput(
            modifier = Modifier
                .focusTarget(),
            passwordState = confirmPassword,
            labelId = "Confirm Password",
            enabled = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {

                onSubmitButton(
                    isCreateAccount,
                    focusManager,
                    validationResult,
                    onDone,
                    email,
                    password,
                    context
                )

            }
        )
    }

    SubmitButton(
        textId = if (isCreateAccount) "Create Account" else "Login",
        loading = loading,
        validInputs = valid
    ) {
        onSubmitButton(
            isCreateAccount,
            focusManager,
            validationResult,
            onDone,
            email,
            password,
            context
        )
        keyboardController?.hide()

    }


}


@Composable
fun SubmitButton(textId: String, loading: Boolean, validInputs: Boolean, onCLick: () -> Unit) {
    Button(
        onClick = onCLick,
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape,

        ) {
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.size(20.dp))
        } else {
            Text(
                text = textId, modifier = Modifier.padding(5.dp), style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }

    }
}

@Composable
fun ForgotPassword() {
    Text(
        modifier = Modifier.clickable { },
        text = stringResource(R.string.forgot_password),
        color = Color(0xff2d2626),
        textAlign = TextAlign.Start,
        style = TextStyle(
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
    )
}

@Composable
fun CreateNewAccount(
    isLogin: Boolean = true,
    onSignUp: (Boolean) -> Unit = { isOnLogin -> }
) {
    Text(
        modifier = Modifier
            .padding(top = 20.dp)
            .clickable {
                onSignUp(!isLogin)


            },
        text = if (isLogin) stringResource(R.string.create_acc) else stringResource(R.string.existing_acc),
        color = PomoFirstC,
        textAlign = TextAlign.Start,
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SignUp() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painterResource(id = R.drawable.line_divider_right),
            contentDescription = null,
            modifier = Modifier.padding(5.dp)
        )

        Text(
            text = stringResource(R.string.sign_up_with),
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            modifier = Modifier
                .width(120.dp)
                //.height(15.dp)

                .alpha(1f),
            color = Color(red = 1f, green = 1f, blue = 1f, alpha = 1f),
            fontWeight = FontWeight.Medium,
            fontStyle = FontStyle.Normal,
        )
        Image(
            painterResource(id = R.drawable.line_divider_left),
            contentDescription = null, modifier = Modifier.padding(2.90.dp)
        )


    }

}

@Composable
fun SocialMediaLogin() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        SocialMediaLogos()
    }
}

private fun onSubmitButton(
    isCreateAccount: Boolean,
    focusManager: FocusManager,
    validationResult: Any,
    onDone: (String, String) -> Unit,
    email: MutableState<String>,
    password: MutableState<String>,
    context: Context
) {
    when {
        isCreateAccount -> {
            if (validationResult == true) {
                focusManager.clearFocus()
                onDone(email.value.trim(), password.value.trim())
            } else {
                Toast.makeText(context, validationResult.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        else -> {
            if (validationResult == true) {
                focusManager.clearFocus()
                onDone(email.value.trim(), password.value.trim())
            } else {
                Toast.makeText(context, validationResult.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
    focusManager.clearFocus()
}

private fun onFirebaseLogin(
    loginViewModel: LoginViewModel,
    email: String,
    password: String,
    navController: NavHostController
) {
    loginViewModel.signInWithEmailAndPassword(email, password) {
        navController.navigate(PomoScreens.HomeScreen.name)
    }
}

private fun onFirebaseCreateAccount(
    loginViewModel: LoginViewModel,
    email: String,
    password: String,
    navController: NavHostController
) {
    loginViewModel.createUserWithEmailAndPassword(email, password) {
        navController.navigate(PomoScreens.HomeScreen.name)
    }
}



