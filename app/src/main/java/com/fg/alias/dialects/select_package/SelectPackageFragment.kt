package com.fg.alias.dialects.select_package

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fg.alias.R
import com.fg.alias.dialects.game.GameStateViewModel
import com.fg.alias.dialects.select_package.addTeam.AddTeamDialog
import com.fg.alias.dialects.select_package.addTeam.TeamsAdapter
import com.fg.alias.dialects.select_package.chooseVocabulary.ChooseVocabularyDialog
import com.fg.alias.dialects.utils.*
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.fragment_select_package.*
import kotlin.random.Random

class SelectPackageFragment : Fragment(R.layout.fragment_select_package){

    private var wordsTotalCount = 100
    private var timeTotal = ONE_MIN

    private lateinit var adapter: TeamsAdapter
    private val viewModel: GameStateViewModel by lazy {
        ViewModelProvider(requireActivity()).get(GameStateViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListener()
        setupAdapter()
    }

    private fun setupAdapter() {
        val namesArrayList = ArrayList<String>()
        val nameArray = ArrayList<String>()
        nameArray.addAll(resources.getStringArray(R.array.teams_name))

        val firstTeamName = nameArray[Random.nextInt(0,nameArray.size-1)]
        namesArrayList.add(firstTeamName)
        nameArray.remove(firstTeamName)
        namesArrayList.add(nameArray[Random.nextInt(0,nameArray.size-1)])

        adapter = TeamsAdapter(namesArrayList, true)
        recyclerView.adapter = adapter
    }


    private fun setupClickListener() {
        tvTimePlus.setOnClickListener { setTotalTime(true) }
        tvTimeMinus.setOnClickListener { setTotalTime(false) }
        tvWordsPlus.setOnClickListener { setWordsCount(true) }
        tvWordsMinus.setOnClickListener { setWordsCount(false) }
        tvAddTeam.setOnClickListener { addTeam() }
        tvVocabularyName.setOnClickListener { chooseVocabulary() }
        tvStart.setOnClickListener { startGame() }
        scLastWordTime.setOnCheckedChangeListener { buttonView, isChecked ->
            onLastWordAdditionalTime(isChecked) }
    }

    private fun onLastWordAdditionalTime(isChecked: Boolean) {
        viewModel.isAdditionalTimeForLastWork = isChecked
    }

    private fun startGame(){
        //todo add validation
        if(viewModel.getWordListSize() == 0){
            viewModel.setWordsList(getAllVocabularyList(requireContext()) as ArrayList<Word>)//delete
        }

        viewModel.setMaxScore(wordsTotalCount)
        viewModel.setRoundTime(timeTotal)
        if (adapter.getTeamsList().size >=2){
            viewModel.setTeams(adapter.getTeamsList())
        }else{
            Toast.makeText(requireContext(),
                "для гри вам потрібно мінімум дві команди",
                Toast.LENGTH_SHORT).show()
            return
        }
        println("QQ start game ${adapter.getTeamsList()} \n ${tvVocabularyName.text}")
        FirebaseLogs.customEvent(FirebaseAnalytics.Event.LEVEL_START, "" +
                "${adapter.getTeamsList()} \n" +
                " ${tvVocabularyName.text} ")
        findNavController().popBackStack(R.id.menuFragment, false);
        findNavController().navigate(R.id.mainScreenFragment)
    }

    private fun chooseVocabulary() {
        val dialog = ChooseVocabularyDialog(object : ChooseVocabularyDialog.OnVocabularySelected{
            override fun onVocabularySelected(vocabulary: Vocabulary) {}
            override fun onVocabularySelectedList(
                vocabularyList: ArrayList<Vocabulary>,
                selectedVocabularies: String,
            ) {
                if (vocabularyList.isEmpty()){
                    viewModel.setWordsList(getAllWordArray(requireContext()))
                }else{
                    viewModel.setWordsList(
                        getWordsListByVocabulary(vocabularyList, requireContext()) as ArrayList<Word>)
                }

                if (selectedVocabularies.isEmpty() || (selectedVocabularies == "Обрані словники:")){
                    tvVocabularyName.text = "Оберіть словник"
                }else{
                    tvVocabularyName.text = selectedVocabularies
                            .replace("\n", ", ")
                            .replaceFirst(", ", " ")
                }
            }
        })
        dialog.show(childFragmentManager, dialog.tag)
    }

    private fun addTeam() {
        val dialog = AddTeamDialog(object : AddTeamDialog.OnTeamSelected{
            override fun onNewTeamAdded(name: String) {
                adapter.addTeam(name)
            }
        }, adapter.getTeamsList())
        dialog.show(childFragmentManager, dialog.tag)
    }

    private fun setWordsCount(isPlus: Boolean){
        if (isPlus){
            wordsTotalCount+=10
        }else{
            if (wordsTotalCount > 10){
                wordsTotalCount-=10
            }
        }
        tvWordsTotal.text = wordsTotalCount.toString()
    }

    private fun setTotalTime(isPlus: Boolean){
        if (isPlus){
        if (timeTotal > FIVE_MIN){
                timeTotal+= TEN_SEC
            }
        }else{
            if (timeTotal > TEN_SEC){
                timeTotal-= TEN_SEC
            }
        }
        tvTimeTotal.text = getFormatTime(timeTotal)
    }

    companion object{
        const val TEN_SEC = 10000
        const val ONE_MIN = 1*60*1000L
        const val FIVE_MIN = 5*60*1000L
    }
}