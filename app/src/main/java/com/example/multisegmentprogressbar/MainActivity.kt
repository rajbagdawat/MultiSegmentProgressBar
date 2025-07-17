package com.example.multisegmentprogressbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.multisegment_pb.MultiSegmentProgressBar
import com.example.multisegment_pb.ProgressOrientation
import com.example.multisegmentprogressbar.ui.theme.MultiSegmentProgressBarTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MultiSegmentProgressBarTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    RandomProgressScreen(Modifier.padding(padding))
                }
            }
        }
    }
}

@Composable
fun RandomProgressScreen(modifier: Modifier = Modifier) {
    var acceptedValue by remember { mutableStateOf(0f) }
    var inProgressValue by remember { mutableStateOf(0f) }
    var rejectedValue by remember { mutableStateOf(0f) }
    val totalValue = 100f

    fun generateRandomValues() {
        inProgressValue = Random.nextInt(40, 90).toFloat()
        acceptedValue = Random.nextInt(0, (inProgressValue - 10).toInt()).toFloat()
        rejectedValue = Random.nextInt(0, (inProgressValue - acceptedValue).toInt()).toFloat()
    }

    // Generate once on first composition
    LaunchedEffect(Unit) {
        generateRandomValues()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Accepted: ${acceptedValue.toInt()}, Rejected: ${rejectedValue.toInt()}, InProgress: ${inProgressValue.toInt()}",
            style = MaterialTheme.typography.bodyLarge
        )

        // Horizontal Bar
        MultiSegmentProgressBar(
            acceptedValue = acceptedValue,
            rejectedValue = rejectedValue,
            inProgressValue = inProgressValue,
            totalValue = totalValue,
            orientation = ProgressOrientation.Horizontal,
            cornerRadiusDp = 8f,
            modifier = Modifier.height(10.dp)
        )
        Row(
            Modifier
                .fillMaxWidth()
                .height(200.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            MultiSegmentProgressBar(
                acceptedValue = acceptedValue,
                rejectedValue = rejectedValue,
                inProgressValue = inProgressValue,
                totalValue = totalValue,
                orientation = ProgressOrientation.Vertical,
                cornerRadiusDp = 10f,
                modifier = Modifier
                    .width(70.dp)
                    .height(120.dp)
            )
            Spacer(Modifier.width(15.dp))
            MultiSegmentProgressBar(
                acceptedValue = acceptedValue,
                rejectedValue = rejectedValue,
                inProgressValue = inProgressValue,
                totalValue = totalValue,
                orientation = ProgressOrientation.Vertical,
                cornerRadiusDp = 10f,
                modifier = Modifier
                    .width(70.dp)
                    .height(120.dp)
            )
        }
        Button(onClick = { generateRandomValues() }) {
            Text("Generate Random Progress")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RandomProgressScreenPreview() {
    MultiSegmentProgressBarTheme {
        RandomProgressScreen()
    }
}