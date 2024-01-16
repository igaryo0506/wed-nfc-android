package com.example.wednfc

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wednfc.ui.theme.WedNfcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WedNfcTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainView()
                }
            }
        }
    }
}

@Composable
fun MainView(modifier: Modifier = Modifier) {
    var selectedTabIndex by remember { mutableStateOf(0)}
    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex
        ) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = {
                    selectedTabIndex = 0
                },
                text = {
                    Text(
                        text = "書き込む"
                    )
                }
            )

            Tab(
                selected = selectedTabIndex == 1,
                onClick = {
                    selectedTabIndex = 1
                },
                text = {
                    Text(
                        text = "読み込む"
                    )
                }
            )
        }
        when (selectedTabIndex) {
            0 -> WriteScreen()
            1 -> ReadScreen()
        }
    }
}

@Composable
fun ReadScreen() {
    // NFC読み取り用UI
     Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
         Column(
             horizontalAlignment = Alignment.CenterHorizontally,
             modifier = Modifier.padding(16.dp)
         ) {
             Text(text = "NFCタグのデータをここに表示")
             Spacer(modifier = Modifier.height(16.dp))
             Button(
                 onClick = {},
             ) {
                 Text(text = "読み取る")
             }
         }
     }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteScreen() {
    var text by remember { mutableStateOf("")}

    // NFC書き込み用UI
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("データを入力") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {},
            ) {
                Text(text = "読み取る")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WedNfcTheme {
        MainView()
    }
}