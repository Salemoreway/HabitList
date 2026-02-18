package com.example.habittracker

import android.os.Bundle
import android.view.Display
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.style.LineHeightStyle.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.habittracker.ui.theme.HabitTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   HabitAdd(

                        modifier = Modifier.padding(innerPadding)
                   )
                }
            }
        }
    }
}
@Composable
fun HabitAdd(modifier: Modifier = Modifier){
    var habits by remember { mutableStateOf(listOf<Habits>()) }
    Column(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top) {
    Row(modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) {
        Button(onClick = {}) {
            Text("Add Habit")
        }
    }
    LazyColumn(modifier= modifier.fillMaxSize()){
       items(habits) {

       }
        }
    }
}
data class Habits(var habitName: String, var numberIterations: Int){

}

