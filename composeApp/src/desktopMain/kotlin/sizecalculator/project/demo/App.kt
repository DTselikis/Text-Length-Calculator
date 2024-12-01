package sizecalculator.project.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalStdlibApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        var inputText by remember { mutableStateOf(TextFieldValue("", TextRange(0))) }
        var sizeText by remember { mutableStateOf("00000000") }

        val clipboardManager = LocalClipboardManager.current
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState
                )
            }
        ) { innerPadding ->
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                TextField(
                    value = sizeText,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            clipboardManager.setText(
                                annotatedString = buildAnnotatedString { append(sizeText) }
                            )

                            scope.launch {
                                snackbarHostState.showSnackbar("Copied!")
                            }
                        }
                    ) {
                        Text("Copy size")
                    }
                    Button(
                        onClick = {
                            clipboardManager.setText(
                                annotatedString = buildAnnotatedString {
                                    append(sizeText)
                                    append(inputText.text)
                                }
                            )

                            scope.launch {
                                snackbarHostState.showSnackbar("Copied!")
                            }
                        }
                    ) {
                        Text("Copy all")
                    }
                    Button(
                        onClick = {
                            inputText = inputText.copy(text = "")
                            sizeText = "00000000"
                        }
                    ) {
                        Text("Clear")
                    }
                }
                TextField(
                    value = inputText,
                    onValueChange = {
                        inputText = it
                        sizeText = inputText.text.length.toHexString()
                    },
                    placeholder = {
                        Text("Paste or type here")
                    },

                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}