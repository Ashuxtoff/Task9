package com.example.task9new.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.RepositoryImpl
import com.example.domain.objects.Habit
import com.example.domain.objects.HabitType
import com.example.domain.objects.TimeIntervalType
import com.example.domain.repository.Repository
import com.example.domain.useCases.GetHabitByIdUseCase
import com.example.domain.useCases.PutHabitUseCase
import com.example.task9new.App
import com.example.task9new.R
import com.example.task9new.presentation.MainActivity
import com.example.task9new.presentation.viewModels.FormViewModel
import kotlinx.android.synthetic.main.fragment_form.*
import javax.inject.Inject

class FormFragment: Fragment(), TextWatcher {

    private val priorities get() = resources.getStringArray(R.array.priorities)
    private val typesOfTimeInterval get() = TimeIntervalType.values().map { getString(it.resId) }
    private var currentType = HabitType.USEFUL
    private var currentTimeIntervalType = TimeIntervalType.DAYS

    private lateinit var viewModel : FormViewModel

    private var habit : Habit? = null

    @Inject
    lateinit var repository : Repository

    @Inject
    lateinit var getHabitByIdUseCase: GetHabitByIdUseCase

    @Inject
    lateinit var putHabitUseCase: PutHabitUseCase

    companion object {

        private const val HABIT_ARG = "habit"
        private const val ID_ARG = "id"
        private const val EMPTY_STRING = ""


        fun newInstance(uuid : String?) : FormFragment{
            val fragment = FormFragment()

            if (uuid != null) { // убрать этот блок
                val bundle = Bundle().apply {
                    putString(ID_ARG, uuid)
                }
                fragment.arguments = bundle
            }

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as App).appComponent.injectFormFragment(this)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory { // посмотреть, сработает ли с другой втьюмоделью, и тогда тут  тоже переделать
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FormViewModel(
                    repository,
                    arguments?.getString(ID_ARG) ?: EMPTY_STRING,
                    getHabitByIdUseCase,
                    putHabitUseCase) as T
            }
        })[FormViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.habitLD.observe(this.activity as LifecycleOwner) {
            setFields(it)
        }

        val activity = activity as MainActivity

        activity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)

        if (arguments == null) {
            activity.supportActionBar?.setTitle(R.string.addHabitToolbarTitle)
        }
        else {
            activity.supportActionBar?.setTitle(R.string.editHabitToolbarTitle)
        }

        priorityInput.setAdapter(
            ArrayAdapter(view.context, android.R.layout.simple_dropdown_item_1line, priorities)
        )

        timeIntervalInput.apply {
            setAdapter(
                ArrayAdapter(
                    view.context,
                    android.R.layout.simple_dropdown_item_1line,
                    typesOfTimeInterval
                )
            )
            addTextChangedListener(this@FormFragment)
        }

        usefulTypeRadiobutton.setOnClickListener {
            currentType = HabitType.USEFUL
            countOfEventsInput.hint = getString(R.string.hintEventsCountUseful)
        }

        badTypeRadiobutton.setOnClickListener {
            currentType = HabitType.BAD
            countOfEventsInput.hint = getString(R.string.hintEventsCountBad)
        }

        usefulTypeRadiobutton.isChecked = true
        countOfEventsInput.hint = getString(R.string.hintEventsCountUseful)
        currentType = HabitType.USEFUL


//        if (habit != null) {
//            titleInput.setText(habit?.title)
//            descriptionInput.setText(habit?.description)
//            priorityInput.setText(habit?.priority.toString() as CharSequence)
//            when (habit?.type) {
//                HabitType.USEFUL -> {
//                    usefulTypeRadiobutton.isChecked = true
//                    currentType = HabitType.USEFUL
//                }
//                HabitType.BAD -> {
//                    badTypeRadiobutton.isChecked = true
//                    currentType = HabitType.BAD
//                }
//            }
//            countOfEventsInput.setText(habit?.eventsCount.toString() as CharSequence)
//            currentTimeIntervalType = habit?.timeIntervalType ?: TimeIntervalType.DAYS
//            timeIntervalInput.setText(getText(currentTimeIntervalType.resId))
//            button.text = getString(R.string.editButtonText)
//        }

        button.setOnClickListener {

            val title = titleInput.text.toString()
            val description = descriptionInput.text.toString()
            val priorityString = priorityInput.text.toString()
            val countOfEventsString = countOfEventsInput.text.toString()

            if (title.isEmpty() || priorityString.isEmpty() || countOfEventsString.isEmpty() || timeIntervalInput.text.toString().isEmpty()) {
                Toast.makeText(view.context, getString(R.string.emptyRequiredFieldsToast), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            viewModel.processForm(
                title,
                description,
                priorityString.toInt(),
                currentType,
                countOfEventsString.toInt(),
                currentTimeIntervalType
            )

            activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentPlaceholder, BaseHabitsListFragment.newInstance())
                .commit()
        }
    }

    fun setFields(habit : Habit) {
        titleInput.setText(habit?.title)
        descriptionInput.setText(habit?.description)
        priorityInput.setText(habit?.priority.toString() as CharSequence)
        when (habit?.type) {
            HabitType.USEFUL -> {
                usefulTypeRadiobutton.isChecked = true
                currentType = HabitType.USEFUL
            }
            HabitType.BAD -> {
                badTypeRadiobutton.isChecked = true
                currentType = HabitType.BAD
            }
        }
        countOfEventsInput.setText(habit?.eventsCount.toString() as CharSequence)
        currentTimeIntervalType = habit?.timeIntervalType ?: TimeIntervalType.DAYS
        timeIntervalInput.setText(getText(currentTimeIntervalType.resId))
        button.text = getString(R.string.editButtonText)
    }


    //Text Watcher methods
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        return
    }

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        currentTimeIntervalType = TimeIntervalType.values().find { getString(it.resId)  == text.toString()} ?: TimeIntervalType.DAYS
    }

    override fun afterTextChanged(p0: Editable?) {
        return
    }
}