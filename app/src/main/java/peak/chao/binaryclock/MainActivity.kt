package peak.chao.binaryclock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import peak.chao.binaryclock.ui.theme.BinaryClockTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Collections
import java.util.Locale
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BinaryClockTheme {
                Content()
            }
        }
    }
}

/**
 * 获取当前时间并分割
 */
fun getCurrentTime(): List<String> {
    // 获取当前时间
    val currentTime = Calendar.getInstance()
    // 创建SimpleDateFormat对象并设置时间格式
    val formatter = SimpleDateFormat("yyyy MM dd HH mm ss", Locale.CHINA)
    // 格式化时间
    val formattedTime = formatter.format(currentTime.time)
    val timeArray = formattedTime.split(" ")
    return timeArray
}

/**
 * 10进制数字分离为二进制数字对应的单个数字
 */
fun numberSplitBinarySingleNumber(str: String, minLength: Int): MutableList<String> {
    val binaryNumber = Integer.toBinaryString(str.toInt())
    val results = mutableListOf<String>()
    // 长度不足时，进行补0操作，以保持固定长度
    if (binaryNumber.length < minLength) {
        results.addAll(Collections.nCopies(minLength - binaryNumber.length, "0"))
    }
    binaryNumber.forEachIndexed { index, it ->
        // 计算公式：index*(2^x)
        results.add(
            Math.round(
                it.toString().toDouble() * 2.0.pow((binaryNumber.length - index - 1))
            ).toString()
        )
    }
    return results
}

/**
 * 矩形块组件
 */
@Composable
fun ItemPiece(text: String) {
    Column(Modifier.padding(6.dp)) {
        if (text.toInt() > 0) {
            Text(
                text = text,
                color = Color.White,
                modifier = Modifier
                    .background(Color(0xff33ccff), shape = RoundedCornerShape(5.dp))
                    .padding(horizontal = 5.dp)
            )
        } else {
            Text(
                text = text,
                color = Color.Gray,
                modifier = Modifier
                    .background(Color.Gray, shape = RoundedCornerShape(5.dp))
                    .padding(horizontal = 5.dp)
            )
        }
    }
}

/**
 * 时间显示组件
 */
@Composable
fun TimeItem(text: String, showTime: Boolean) {
    Text(
        text = if (showTime) text else "-".repeat(text.length),
        color = Color.White,
        modifier = Modifier
            .background(Color(0xffff00ff), shape = RoundedCornerShape(5.dp))
            .padding(horizontal = 5.dp)
    )
}

@Composable
fun Content() {
    val showTime = remember { mutableStateOf(false) }
    val timeFlow = remember { mutableStateOf(getCurrentTime()) }
    LaunchedEffect(Unit) {
        while (true) {
            timeFlow.value = getCurrentTime()
            delay(1000)
        }
    }
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(end = 10.dp), horizontalArrangement = Arrangement.End
        ) {
            Button(onClick = { showTime.value = !showTime.value }) {
                Text(text = if (showTime.value) "隐藏时间" else "显示时间")
            }
        }
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                val tenNumbers = numberSplitBinarySingleNumber(timeFlow.value[0], 12)
                tenNumbers.forEach {
                    ItemPiece(it)
                }
                TimeItem(timeFlow.value[0], showTime.value)
            }
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                val tenNumbers = numberSplitBinarySingleNumber(timeFlow.value[1], 4)
                tenNumbers.forEach {
                    ItemPiece(it)
                }
                TimeItem(timeFlow.value[1], showTime.value)
            }
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                val tenNumbers = numberSplitBinarySingleNumber(timeFlow.value[2], 5)
                tenNumbers.forEach {
                    ItemPiece(it)
                }
                TimeItem(timeFlow.value[2], showTime.value)
            }
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                val tenNumbers = numberSplitBinarySingleNumber(timeFlow.value[3], 5)
                tenNumbers.forEach {
                    ItemPiece(it)
                }
                TimeItem(timeFlow.value[3], showTime.value)
            }
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                val tenNumbers = numberSplitBinarySingleNumber(timeFlow.value[4], 6)
                tenNumbers.forEach {
                    ItemPiece(it)
                }
                TimeItem(timeFlow.value[4], showTime.value)
            }
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                val tenNumbers = numberSplitBinarySingleNumber(timeFlow.value[5], 6)
                tenNumbers.forEach {
                    ItemPiece(it)
                }
                TimeItem(timeFlow.value[5], showTime.value)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BinaryClockTheme {
        Content()
    }
}