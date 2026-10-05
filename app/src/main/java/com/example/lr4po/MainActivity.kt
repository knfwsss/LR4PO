package com.example.lr4po

import android.os.Bundle
import java.math.BigDecimal
import java.math.RoundingMode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lr4po.ui.theme.LR4POTheme
import kotlin.math.pow

private val AppPurple = Color(0xFF6200EE)
private val FieldTeal = Color(0xFF03DAC5)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LR4POTheme {
                SumScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SumScreen(modifier: Modifier = Modifier) {
    var xInput by remember { mutableStateOf("") }
    var nInput by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("LR4PO") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppPurple,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputRow(
                label = "x",
                value = xInput,
                onValueChange = { xInput = it },
                keyboardType = KeyboardType.Decimal
            )
            InputRow(
                label = "n",
                value = nInput,
                onValueChange = { nInput = it },
                keyboardType = KeyboardType.Number
            )
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = result,
                fontSize = 20.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(28.dp))
            Button(
                onClick = { result = calculate(xInput, nInput) },
                colors = ButtonDefaults.buttonColors(containerColor = AppPurple)
            ) {
                Text("ОК")
            }
        }
    }
}

fun calculate(xRaw: String, nRaw: String): String {
    val xText = xRaw.trim().replace(',', '.')
    val nText = nRaw.trim()
    if (xText.isEmpty() || nText.isEmpty()) {
        return "Введите x и n"
    }
    val x = xText.toDoubleOrNull() ?: return "Некорректное число x"
    val n = nText.toIntOrNull()
    if (n == null || n < 1 || nText.contains('.') || nText.contains(',')) {
        return "n должно быть натуральным числом"
    }

    var sum = 0.0
    for (k in 1..n) {
        val denominator = 4.0.pow(k) + 5.0.pow(k + 2)
        sum += x / denominator
    }
    return formatNumber(sum)
}

private fun formatNumber(value: Double): String {
    return BigDecimal.valueOf(value)
        .setScale(6, RoundingMode.HALF_UP)
        .stripTrailingZeros()
        .toPlainString()
}

@Composable
private fun InputRow(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            modifier = Modifier.width(88.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        TextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = FieldTeal,
                unfocusedIndicatorColor = FieldTeal,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            ),
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SumPreview() {
    LR4POTheme(dynamicColor = false) {
        SumScreen()
    }
}
