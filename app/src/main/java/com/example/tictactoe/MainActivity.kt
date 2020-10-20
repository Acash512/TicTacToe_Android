package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener{

    private var crossTurn = true
    private var turnCount = 0
    private lateinit var board : Array<Array<Button>>
    private val boardStatus = Array(3){IntArray(3)}
    private var scoreX = 0
    private var score0 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        board = arrayOf(arrayOf(button1,button2,button3),
                        arrayOf(button4,button5,button6),
                        arrayOf(button7,button8,button9))

        for(i in board){
            for(button in i){
                button.setOnClickListener(this)
            }
        }

        initialize()

        reset.setOnClickListener {
            initialize()
        }
    }

    private fun initialize() {
        GameStatus.text = "X's turn"
        crossTurn = true
        turnCount = 0

        for(i in 0..2){
            for(j in 0..2){
                board[i][j].apply{
                    text = ""
                    isEnabled= true
                }
                boardStatus[i][j] = -1
            }
        }
    }

    override fun onClick(v: View) {
        when(v.id){
            button1.id->{
                updateBoardStatus(0,0)
            }

            button2.id->{
                updateBoardStatus(0,1)
            }

            button3.id->{
                updateBoardStatus(0,2)
            }

            button4.id->{
                updateBoardStatus(1,0)
            }

            button5.id->{
                updateBoardStatus(1,1)
            }

            button6.id->{
                updateBoardStatus(1,2)
            }

            button7.id->{
                updateBoardStatus(2,0)
            }

            button8.id->{
                updateBoardStatus(2,1)
            }

            button9.id->{
                updateBoardStatus(2,2)
            }
        }

        crossTurn = !crossTurn
        turnCount++

        if(!checkWinner()) {
            if (turnCount == 9) {
                updateText("Tie")
            } else {
                val txt = if (crossTurn) "X" else "0"
                updateText("$txt's turn")
            }
        }else {
            if(!crossTurn){
                scoreX++
                Score.text = "X : $scoreX\t\t0 : $score0"
            }else{
                score0++
                Score.text = "X : $scoreX\t\t0 : $score0"
            }
            disableAllButtons()
        }
    }

    private fun checkWinner() : Boolean {
        for(i in 0..2){
            if(boardStatus[i][0]!=-1 && boardStatus[i][0]==boardStatus[i][1] && boardStatus[i][1]==boardStatus[i][2]){
                updateText("${board[i][0].text} wins")
                return true
            }
        }

        for(i in 0..2){
            if(boardStatus[0][i]!=-1 && boardStatus[0][i]==boardStatus[1][i] && boardStatus[1][i]==boardStatus[2][i]){
                updateText("${board[0][i].text} wins")
                return true
            }
        }

        if(boardStatus[1][1]!=-1 && boardStatus[0][0]==boardStatus[1][1] && boardStatus[1][1]==boardStatus[2][2]){
            updateText("${board[1][1].text} wins")
            return true
        }

        if(boardStatus[1][1]!=-1 && boardStatus[0][2]==boardStatus[1][1] && boardStatus[1][1]==boardStatus[2][0]){
            updateText("${board[1][1].text} wins")
            return true
        }

        return false
    }

    private fun updateText(txt:String) {
        GameStatus.text = txt
    }

    private fun disableAllButtons() {
        for(i in board){
            for(button in i){
                button.isEnabled = false
            }
        }
    }

    private fun updateBoardStatus(row:Int,col:Int) {
        val txt = if(crossTurn) "X" else "0"
        val value = if(crossTurn) 1 else 0
        board[row][col].apply{
            text = txt
            isEnabled = false
        }
        boardStatus[row][col] = value
    }
}