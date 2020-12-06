package com.example.progressindicator

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import com.example.progressindicator.ui.ProgressIndicatorTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProgressIndicatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {
    val stateLoading = remember { mutableStateOf<Boolean>(false) }
    val coroutineScope = rememberCoroutineScope()

    val doLoadingForAWhile: () -> Unit = {
        coroutineScope.launch {
            stateLoading.value = true
            delay(2000)
            stateLoading.value = false
        }
    }

    if (stateLoading.value) {
        ContentWhenLoading()
    } else {
        ContentWhenNotLoading(onClickLoading = doLoadingForAWhile)
    }
}

@Composable
fun ContentWhenLoading(){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(1f).background(color = Color.Gray)
    ) {
        Surface(modifier = Modifier.preferredSize(100.dp), shape = RoundedCornerShape(10.dp)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Loading ...")
                Spacer(modifier = Modifier.preferredSize(20.dp))
                CircularProgressIndicator(
                    modifier = Modifier.preferredSize(50.dp),
                    color = Color.LightGray
                )
            }
        }
    }
}

@Composable
fun ContentWhenNotLoading(onClickLoading: () -> Unit){
    val context = AmbientContext.current

    val showToast = {
        Toast.makeText(context, "This is a toast", Toast.LENGTH_SHORT).show()
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(1f)
    ) {
        Button(onClick = { showToast() }) {
            Text("Show toast")
        }

        Spacer(modifier = Modifier.preferredHeight(20.dp))

        Button(onClick = { onClickLoading() }) {
            Text("Show progress for 2 seconds")
        }
    }
}
