package app.reality.androiduaalsphere

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import app.reality.androiduaalsphere.ui.theme.AndroidUaaLSphereTheme
import com.unity3d.player.UnityPlayer
import com.unity3d.player.UnityPlayerForActivityOrService

class MainActivity : ComponentActivity() {

    private lateinit var unityPlayer: UnityPlayerForActivityOrService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        unityPlayer = UnityPlayerForActivityOrService(this)
        // FrameLayoutからComposeに載せ替えるために不要な親FrameLayoutからremoveしておく
        unityPlayer.frameLayout?.removeView(unityPlayer.view)

        enableEdgeToEdge()
        setContent {
            AndroidUaaLSphereTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UnityViewComposable(unityView = unityPlayer.view)
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        onButtonClicked = { label -> unitySendMessage(label) }
                    )
                }
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        unityPlayer.windowFocusChanged(hasFocus)
    }

    override fun onResume() {
        super.onResume()
        unityPlayer.onResume()
    }

    fun unitySendMessage(message: String) {
        UnityPlayer.UnitySendMessage("MessageReceiver", "OnMessage", message)
    }
}

@Composable
fun UnityViewComposable(unityView: View) {
    AndroidView(factory = { unityView }, update = { view -> })
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
    onButtonClicked: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), verticalArrangement = Arrangement.Top
    ) {
        Text("Hello Compose $name")
        Spacer(Modifier.size(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("white", "red", "blue").forEach { label ->
                Button(onClick = { onButtonClicked(label) }) {
                    Text(label)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidUaaLSphereTheme {
        Greeting("Android")
    }
}