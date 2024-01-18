package com.example.testy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testy.ui.theme.TestyTheme

var currentValue = 0f
var currentOperation: String? = null
var previousValue = 0f

var unaryOperators = arrayOf("%", "square")
var binaryOperators = arrayOf("+", "-", "*", "/")

var screenWidth = 0
var screenHeight = 0


fun onNumberClicked(value: Int) : String {
    // sztuczki, żeby dało się poprawnie wpisywać liczby dziesiętne
    // najważniejsze, że działa :)
    if (currentValue == currentValue.toInt().toFloat())
    {
        currentValue = (currentValue.toInt().toString() + value.toString()).toFloat()
    }
    else
    {
        currentValue = (currentValue.toString() + value.toString()).toFloat()
    }

    return currentValue.toString()
}


fun onBinaryOperatorClicked(operator: String) {
    currentOperation = operator
    previousValue = currentValue
    currentValue = 0f
}

fun onEqualsClicked() : String
{
    when (currentOperation)
    {
        "+" -> currentValue += previousValue
        "-" -> currentValue = previousValue - currentValue
        "*" -> currentValue *= previousValue
        "/" -> currentValue = previousValue / currentValue
        null -> {}
    }
    return currentValue.toString()
}

fun clear() : String
{
    currentValue = 0f
    currentOperation = null
    previousValue = 0f

    return "0"
}

fun onUnaryOperatorClicked(operator: String) : String
{
    when (operator)
    {
        "+-" -> currentValue = -currentValue
        "%" -> currentValue /= 100
        "square" -> currentValue *= currentValue
        //"sqrt" -> currentValue = sqrt(currentValue)
        "log" -> currentValue = kotlin.math.log(currentValue.toDouble(), 10.0).toFloat()
    }

    return currentValue.toString()
}

@Preview
@Composable
fun MainContent() {
    var text by remember { mutableStateOf("0") }
    screenWidth = LocalConfiguration.current.screenWidthDp
    screenHeight = LocalConfiguration.current.screenHeightDp

    Column{
        Row(modifier = Modifier.weight(0.3f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Spacer(modifier = Modifier.width((screenWidth * 0.1).dp))
            Text(text, textAlign = TextAlign.Right, fontSize = 80.sp)
        }

        Row{
            MyButton(
                onClick = { text = clear() }
            )
            {
                Text(text = "C")
            }

            for (i in 0 until 2)
            {
                var operator = unaryOperators[i]
                MyButton(onClick = { text = onUnaryOperatorClicked(operator) }){
                    Text(text = operator)
                }
            }

            MyButton(onClick = { onBinaryOperatorClicked("/") }) {
                Text(text = "/")
            }


        }

        for (j in 0 until 3)
        {
            Row{
                for (i in 1 until 4)
                {
                    val v = j * 3 + i
                    MyButton(onClick = { text = onNumberClicked(v) }) {
                        Text(text = v.toString())
                    }
                }
                var operator = binaryOperators[j]
                MyButton(onClick = { onBinaryOperatorClicked(operator) }) {
                    Text(text = operator)
                }
            }
        }


        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(0.25f),
            horizontalArrangement = Arrangement.SpaceBetween) {
            MyButton(onClick = { text = onUnaryOperatorClicked("+-") }) {
                Text(text = "+-")
            }

            MyButton(onClick = { text = onNumberClicked(0) }) {
                Text(text = "0")
            }
            MyButton(onClick = { text = onUnaryOperatorClicked("log") }) {
                Text(text = "log")
            }
            MyButton(onClick = { text = onEqualsClicked() }) {
                Text(text = "=")
            }
        }
    }
}

@Composable
fun MyButton(
    onClick: () -> Unit,
    content: @Composable () -> Unit
)
{
    Button(
        shape = RectangleShape,
        onClick = onClick, modifier =
        Modifier.width((screenWidth / 4).dp)
            .height((screenHeight / 8).dp)
            .border(1.dp, Color.Black, RectangleShape)
    )
    {
        content()
    }

}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent()
                }
            }
        }
    }
}

