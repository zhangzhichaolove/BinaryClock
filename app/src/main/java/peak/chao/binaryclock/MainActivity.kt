package peak.chao.binaryclock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import peak.chao.binaryclock.ui.theme.BinaryClockTheme

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

@Preview(showBackground = true)
@Composable
fun Content() {
    Column {
        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(White)
        ) {
            TopBar()
        }
        NavBar()
    }
}

@Composable
fun TopBar() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(28.dp, 28.dp, 28.dp, 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(Modifier.clip(CircleShape), color = Color.LightGray) {
            Image(
                painterResource(id = R.drawable.ic_launcher_foreground), "头像",
                Modifier
                    .clip(
                        CircleShape
                    )
                    .size(64.dp)
            )
        }
        Column(
            Modifier
                .padding(start = 14.dp)
                .weight(1f)
        ) {
            Text(text = "欢迎回来!", fontSize = 14.sp, color = Red)
            Text(
                text = "peakchao",
                fontSize = 18.sp,
                color = Black,
                fontWeight = FontWeight.Bold
            )
        }
        Image(
            painterResource(id = R.drawable.ic_launcher_foreground),
            "通知",
            Modifier.size(32.dp)
        )
    }
}

data class NavItemData(@DrawableRes val icon: Int, val description: String)

@Composable
fun NavBar() {
    // 使用remember创建可记忆的状态，以便在重新组合时保持状态
    val selected: MutableState<Int> = remember { mutableStateOf(0) }
    val items = listOf(
        NavItemData(R.drawable.ic_launcher_foreground, "首页"),
        NavItemData(R.drawable.ic_launcher_foreground, "标签"),
        NavItemData(R.drawable.ic_launcher_foreground, "日历"),
        NavItemData(R.drawable.ic_launcher_foreground, "我的")
    )
    Row {
        items.forEachIndexed { index, navItemData ->
            NavItem(navItemData.icon, navItemData.description, selected.value == index) {
                selected.value = index
            }
        }
    }
}

@Composable
fun RowScope.NavItem(
    @DrawableRes iconRes: Int,
    description: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Button(
        onClick = { onClick() },
        Modifier
            .weight(1f), shape = RectangleShape, colors = ButtonDefaults.outlinedButtonColors()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = description,
                modifier = if (isSelected) Modifier
                    .size(40.dp) else Modifier
                    .size(35.dp),
                tint = if (isSelected) Red else Gray,
            )
            Text(
                text = description,
                color = if (isSelected) Red else Gray,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}