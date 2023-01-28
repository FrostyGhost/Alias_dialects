package com.fg.alias.dialects.select_package.chooseVocabulary

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.fg.alias.dialects.utils.Vocabulary
import com.fg.alias.dialects.utils.getAllVocabularies
import kotlinx.android.synthetic.main.dialog_choose_vocabulary.*

class ChooseVocabularyDialog(
    private val listener: OnVocabularySelected
) :
    DialogFragment(com.fg.alias.R.layout.dialog_choose_vocabulary){

    private val adapter: VocabularyAdapter = VocabularyAdapter(object : OnVocabularySelected{
        override fun onVocabularySelected(vocabulary: Vocabulary) {
            updateSelectedVocabulary(vocabulary)
        }

        override fun onVocabularySelectedList(
            vocabularyList: ArrayList<Vocabulary>,
            selectedVocabularies: String
        ) {}
    })

    private val selectedVocabularyList = ArrayList<Vocabulary>()

    private fun updateSelectedVocabulary(vocabulary: Vocabulary) {
        if (vocabulary == Vocabulary.ALL){
            selectedVocabularyList.clear()
            selectedVocabularyList.add(vocabulary)
        }else{
            if (selectedVocabularyList.contains(vocabulary)){
                selectedVocabularyList.remove(vocabulary)
            }else{
                selectedVocabularyList.add(vocabulary)
            }

            if (selectedVocabularyList.size >= 2){
                if (selectedVocabularyList.contains(Vocabulary.ALL)){
                    selectedVocabularyList.remove(Vocabulary.ALL)
                }
            }
        }

        tvTitle.text = "Обрані словники:"
        for (voc in selectedVocabularyList){
            tvTitle.append("\n" + voc.localise)
        }
    }

    override fun onStart() {
        super.onStart()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.width = ConstraintLayout.LayoutParams.MATCH_PARENT
        params.height = ConstraintLayout.LayoutParams.MATCH_PARENT
        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupClickListener()
    }

    private fun setupClickListener() {
        tvSelectVoc.setOnClickListener { dismiss() }
    }

    private fun setupAdapter() {
        vocabularyRecyclerView.adapter = adapter
        adapter.addVocabularies(getAllVocabularies())
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (!tvTitle.text.isNullOrEmpty()){
            listener.onVocabularySelectedList(selectedVocabularyList, tvTitle.text.toString())
        }else{
            listener.onVocabularySelectedList(selectedVocabularyList, "")
        }
    }

    interface OnVocabularySelected{
        fun onVocabularySelected(vocabulary: Vocabulary)
        fun onVocabularySelectedList(vocabularyList: ArrayList<Vocabulary>,
                                     selectedVocabularies: String)
    }
}