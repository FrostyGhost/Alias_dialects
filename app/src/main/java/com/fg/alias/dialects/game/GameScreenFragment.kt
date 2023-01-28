package com.fg.alias.dialects.game

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.fg.alias.R
import com.fg.alias.dialects.game.result.ResultDialog
import com.fg.alias.dialects.game.winer.WinnerDialog
import com.fg.alias.dialects.utils.AbstractCardStackListener
import com.fg.alias.dialects.utils.Team
import com.fg.alias.dialects.utils.getFormatTime
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import kotlinx.android.synthetic.main.fragment_main_menu.*
import java.util.concurrent.TimeUnit


class GameScreenFragment : Fragment(R.layout.fragment_main_menu){

    private var isFirstSwipe = true
    private var score = 0
    private var swipedCard = 0
    private lateinit var adapter: CardStackAdapter
    private var roundTime = TimeUnit.MINUTES.toMillis(1)
    private var isLastCardCorrect = false
    private var isCurrentCardPos = 0
    private var isAdditionalTime = false
    private var roundTimer: CountDownTimer? = null
    private var additionalTimer: CountDownTimer? = null
    private val viewModel: GameStateViewModel? by lazy {
        ViewModelProvider(requireActivity()).get(GameStateViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBaseValues()
        setupCardStackView()
        setupClickListeners()
        setupObserver()
    }

    private fun setupBaseValues(){
        roundTime = viewModel?.getRoundTime() ?:TimeUnit.MINUTES.toMillis(1)
        setupTimer()
        setupAdditionalTimer()
        isLastCardCorrect = false
    }

    private fun setupObserver() {
        viewModel?.currentTeamName?.observe(viewLifecycleOwner) {
            tvTeamName.text = it
        }
    }

    private fun setupClickListeners() {
        tvScore.setOnClickListener {
            startGame()
            cardStackView.visibility = View.VISIBLE
            tvScore.visibility = View.GONE
        }
    }

    private fun cardSwiped(direction: Direction?){
        swipedCard++
        if (direction != null){
            when (direction){
                Direction.Left -> {
                    onWrongAnswer()
                }
                Direction.Right ->{
                    onCorrectAnswer()
                }
                Direction.Bottom ->{
                    onWrongAnswer()
                }
                Direction.Top ->{
                    onCorrectAnswer()
                }
            }

            viewModel?.addUsedWord(adapter.getWordByPosition(isCurrentCardPos), isLastCardCorrect)
            tvScore.text = score.toString()
            checkIsLastAdditionalWord()
            checkIsFirstCard()
        }
    }

    private fun checkIsFirstCard(){
        if (isFirstSwipe){
            isFirstSwipe = false
            additionalTimer?.cancel()
            roundTimer?.cancel()
            roundTimer?.start()
        }
    }

    private fun checkIsLastAdditionalWord(){
        if (isAdditionalTime){
            showResults()
            additionalTimer?.cancel()
            isAdditionalTime = false
        }
    }

    private fun onCorrectAnswer(){
        if (!isFirstSwipe){
            isLastCardCorrect = true
            score++
        }
    }

    private fun onWrongAnswer(){
        if (!isFirstSwipe){
            isLastCardCorrect = false
            score--
        }
    }

    private fun setupTimer(){
        roundTimer = object : CountDownTimer(
            roundTime,
            TimeUnit.SECONDS.toMillis(1)){
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                if (isAdded){
                    tvTimer.text = getFormatTime(millisUntilFinished)
                }
            }
            override fun onFinish() {
                if (isAdded && viewModel?.isAdditionalTimeForLastWork != true){
                    showResults()
                }else{
                    isAdditionalTime = true
                    additionalTimer?.cancel()
                    additionalTimer?.start()
                }
            }
    } }

    private fun setupAdditionalTimer(){
        additionalTimer = object : CountDownTimer(
            1*60*1000L,
            TimeUnit.SECONDS.toMillis(1)){
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                if (isAdded){
                    tvTimer.text = getFormatTime(millisUntilFinished)
                }
            }
            override fun onFinish() {
                if (isAdded){
                    showResults()
                }
            }
        }
    }

    private fun showResults(){
        cardStackView.visibility = View.GONE
        showResultDialog()
    }

    private fun showResultDialog(){
        val usedWordList = viewModel?.getTempUsedWordList()
        val dialog = ResultDialog(object : ResultDialog.OnDialogDismiss{
            override fun onDismiss(score: Int) {
                cardStackView.visibility = View.VISIBLE

                swipedCard = 0
                checkWinner(score)
            }
        }, usedWordList?: ArrayList(), score)
        dialog.show(childFragmentManager, dialog.tag)
    }


    private fun checkWinner(score: Int) {
        val winnerTeam = viewModel?.saveTeamResult(score)
        if (winnerTeam != null){
            showWinDialog(winnerTeam)
        }else{
            startGame()
        }
    }

    private fun showWinDialog(winnerTeam: Team) {
        val dialog = WinnerDialog(viewModel?.getTeamsList()?: ArrayList(), winnerTeam,
        object : ResultDialog.OnDialogDismiss{
            override fun onDismiss(score: Int) {
                Toast.makeText(requireContext(), "Вітаємо!!", Toast.LENGTH_SHORT).show()
                viewModel?.clear()
                findNavController().popBackStack(R.id.menuFragment, false);
                findNavController().navigate(R.id.menuFragment)
            }
        })
        dialog.show(childFragmentManager, dialog.tag)
    }

    private fun setupCardStackView() {
        val layoutManager = CardStackLayoutManager(requireContext(),
            object : AbstractCardStackListener() {
                override fun onCardSwiped(direction: Direction?) {
                    cardSwiped(direction)
                }
                override fun onCardDisappeared(view: View?, position: Int) {

                }

                override fun onCardAppeared(view: View?, position: Int) {
                    super.onCardAppeared(view, position)
                    isCurrentCardPos = position
                }
            })

        layoutManager.setStackFrom(StackFrom.Top)
        layoutManager.setVisibleCount(3)
        layoutManager.setTranslationInterval(8.0f)
        layoutManager.setDirections(Direction.FREEDOM)
        cardStackView.layoutManager = layoutManager
        adapter = CardStackAdapter()
        cardStackView.adapter = adapter
        startGame()
    }

    private fun startGame(){
        score = 0
        swipedCard = 0
        isFirstSwipe = true
        tvScore.text = score.toString()
        adapter.setWordList(viewModel?.getWordsList()?: ArrayList())
    }

}