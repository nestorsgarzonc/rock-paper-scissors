package com.example.gamewithcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel

enum class GameOptions {
    ROCK, PAPER, SCISSORS
}

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val model: RockPaperScissorsViewModel by viewModels()
        super.onCreate(savedInstanceState)
        setContent {
            RockPaperScissorsScreen(model)
        }
    }
}


class RockPaperScissorsViewModel : ViewModel() {
    var userScore by mutableStateOf(0)
        private set
    var machineScore by mutableStateOf(0)
        private set
    var machineOption by mutableStateOf(GameOptions.PAPER)
    var message by mutableStateOf<String?>(null)

    init {
        getMachineOption()
    }

    private fun getMachineOption() {
        val options = GameOptions.values()
        options.shuffle()
        machineOption = options.take(1)[0]
    }

    fun selectOption(userOption: GameOptions) {
        if (userOption == machineOption) {
            userScore++
            machineScore++
            message = "Tie"
        } else if (userOption == GameOptions.ROCK && machineOption == GameOptions.PAPER) {
            message = ("You lost")
            machineScore++
        } else if (userOption == GameOptions.ROCK && machineOption == GameOptions.SCISSORS) {
            message = ("You Win")
            userScore++
        } else if (userOption == GameOptions.PAPER && machineOption == GameOptions.ROCK) {
            message = ("You Win")
            userScore++
        } else if (userOption == GameOptions.PAPER && machineOption == GameOptions.SCISSORS) {
            message = ("You lost")
            machineScore++
        } else if (userOption == GameOptions.SCISSORS && machineOption == GameOptions.ROCK) {
            message = ("You lost")
            machineScore++
        } else if (userOption == GameOptions.SCISSORS && machineOption == GameOptions.PAPER) {
            message = ("You Win")
            userScore++
        } else {
            message = ("An error has occurred")
        }
        getMachineOption()
    }

}


@ExperimentalAnimationApi
@Composable
fun RockPaperScissorsScreen(viewModel: RockPaperScissorsViewModel) {
    val machineCounter = viewModel.machineScore
    val userCounter = viewModel.userScore

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    "Score",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                    textAlign = TextAlign.Center,
                )
                Spacer(
                    modifier = Modifier.height(12.dp)
                )
                Row {
                    Text("User: $userCounter")
                    Text(" - ")
                    Text("Machine: $machineCounter")
                }
                Spacer(
                    modifier = Modifier.height(12.dp)
                )
                Image(
                    painter = painterResource(R.drawable.rock),
                    contentDescription = "IMG"
                )
                Spacer(
                    modifier = Modifier.height(12.dp)
                )
                Text("Select your option")
                Spacer(
                    modifier = Modifier.height(12.dp)
                )
                AnimatedVisibility(visible = viewModel.message != null) {
                    Column {
                        Text("${viewModel.message}")
                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier.align(
                        alignment = Alignment.CenterHorizontally
                    )
                )
                {
                    Button(onClick = {
                        viewModel.selectOption(GameOptions.ROCK)
                    }) {
                        Text("Rock")
                    }
                    Spacer(
                        modifier = Modifier.width(16.dp)
                    )
                    Button(onClick = {
                        viewModel.selectOption(GameOptions.PAPER)
                    }) {
                        Text("Paper")
                    }
                    Spacer(
                        modifier = Modifier.width(16.dp)
                    )
                    Button(onClick = {
                        viewModel.selectOption(GameOptions.SCISSORS)
                    }) {
                        Text("Scissors")
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(24.dp)
                        .align(
                            alignment = Alignment.Start
                        )
                )
                {
                    Text("Hint: ")
                    Text("machine option is ${viewModel.machineOption} ")
                }
            }
        }
    )
}