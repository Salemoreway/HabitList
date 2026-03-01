package com.example.habittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
fun HabitAdd(modifier: Modifier = Modifier) {
    var habits by remember { mutableStateOf(listOf<Habits>()) }
    var isDialogOpen by remember { mutableStateOf(false) }
    var habitName by remember { mutableStateOf("") }
    var habitError by remember { mutableStateOf(false) }
    var habitToDelete by remember { mutableStateOf<Habits?>(null) }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text("My habits")
        val name = habitName.trim()
        if (name.isEmpty()) {
            Text("No habits yet. Add your first one.")
        }
        if (isDialogOpen) {
            AlertDialog(
                onDismissRequest = { isDialogOpen = false },
                confirmButton = {

                    Button(onClick = {
                        if (habitName.isBlank()) {
                            habitError = true
                            return@Button
                        }
                        val newHabit = Habits(habitName, 0)
                        habits = habits + newHabit
                        habitName = ""
                        isDialogOpen = false


                    }) {
                        Text("Add")
                    }

                }, dismissButton = {
                    Button(onClick = {
                        isDialogOpen = false
                        habitError = false
                        habitName = ""
                    }) {
                        Text("Cancel")
                    }
                },
                title = { Text(text = "Add your Habit") },
                text = {
                    Column() {
                        TextField(
                            value = habitName,
                            onValueChange = {
                                habitName = it
                                if (habitError) habitError = false
                            },
                            singleLine = true,
                            label = { Text("Habit name") },
                            isError = habitError
                        )
                        if (habitError) {
                            Text("Enter name of habit", color = Color.Red)
                        }
                    }
                }
            )
        }
        if (habitToDelete != null) {
            AlertDialog(
                onDismissRequest = { habitToDelete = null },
                title = { Text("Do you want delete it") },
                text = { Text("Are you sure you want to delete\"${habitToDelete!!.habitName}\"?") },
                confirmButton = {
                    Button(onClick = {
                        habits = habits - habitToDelete!!
                        habitToDelete = null

                    }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        habitToDelete = null
                    }) { Text("No") }
                }
            )
        }
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(Modifier.fillMaxSize()) {
                items(habits) { habit ->
                    CardShowing(
                        item = habit,
                        onConfirmClick = {
                            habits = habits.map {
                                if (it == habit) it.copy(numberIterations = it.numberIterations + 1)
                                else it
                            }
                        },
                        onDeleteClick = {
                            habitToDelete = habit
                        }
                    )
                }
            }

            Button(
                onClick = { isDialogOpen = true },
                shape = AbsoluteRoundedCornerShape(8.dp),
                modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text("+")
            }
        }

    }

}

data class Habits(var habitName: String, var numberIterations: Int){

}

@Composable
fun CardShowing(
    item: Habits,
    onConfirmClick:() -> Unit,
    onDeleteClick:() -> Unit
    ){
    val borderColor : Color =
    if(item.numberIterations >=10){
        Color(0xFFD63686)
    } else  Color(0xFF018786)
    Row(Modifier.padding(8.dp)
        .fillMaxWidth()
        .border(
       border = BorderStroke(2.dp, borderColor),
           shape= RoundedCornerShape(20)
       ), horizontalArrangement =  Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
   ) {
       Text("${item.habitName} ", Modifier.padding(8.dp))
       Text("${item.numberIterations} ", Modifier.padding(8.dp))
            Row(Modifier.padding(8.dp)) {

                IconButton(onClick = onConfirmClick, Modifier.padding(8.dp)) {
                    Icon(imageVector = Icons.Default.Done, contentDescription = " ")
                }
                IconButton(onClick = onDeleteClick, Modifier.padding(8.dp)) {
                 Icon(imageVector = Icons.Default.Delete, contentDescription = " ")
                }
            }
   }
}
