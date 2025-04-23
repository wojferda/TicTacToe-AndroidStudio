package com.example.kolko

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TicTacToeScreen(modifier: Modifier = Modifier) {
    var board by remember { mutableStateOf(List(9) { "" }) }
    var currentPlayer by remember { mutableStateOf("X") }
    var winner by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = winner?.let { "$it wins!" } ?: "Player: $currentPlayer",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        TicTacToeBoard(board) { index ->
            if (board[index].isEmpty() && winner == null) {
                board = board.toMutableList().apply { set(index, currentPlayer) }
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                winner = checkWinner(board)
            }
        }
        if (winner != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                board = List(9) { "" }
                currentPlayer = "X"
                winner = null
            }) {
                Text("Restart")
            }
        }
    }
}

@Composable
fun TicTacToeBoard(board: List<String>, onCellClick: (Int) -> Unit) {
    Column {
        for (row in 0..2) {
            Row {
                for (col in 0..2) {
                    val index = row * 3 + col
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                            .clickable { onCellClick(index) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = board[index],
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

fun checkWinner(board: List<String>): String? {
    val winPatterns = listOf(
        listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // Wiersze
        listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // Kolumny
        listOf(0, 4, 8), listOf(2, 4, 6)  // Przekątne
    )
    for (pattern in winPatterns) {
        val (a, b, c) = pattern
        if (board[a] == board[b] && board[b] == board[c] && board[a].isNotEmpty()) {
            return board[a]
        }
    }
    return null
}
