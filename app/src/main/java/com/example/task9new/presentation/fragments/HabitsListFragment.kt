package com.example.task9new.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.RepositoryImpl
import com.example.domain.objects.Habit
import com.example.domain.repository.Repository
import com.example.domain.useCases.GetAllHabitsUseCase
import com.example.task9new.App
import com.example.task9new.R
import com.example.task9new.presentation.recyclerViewEntities.HabitsListAdapter
import com.example.task9new.presentation.recyclerViewEntities.OnItemClickListener
import com.example.task9new.presentation.viewModels.HabitsListViewModel
import kotlinx.android.synthetic.main.fragment_habits_list.*
import javax.inject.Inject
import kotlin.random.Random

class HabitsListFragment : Fragment(), OnItemClickListener {

    var habits: MutableList<Habit> = mutableListOf()

//    @Inject
    private lateinit var viewModel: HabitsListViewModel

//    @Inject
//    private lateinit var bottomSheetFragment: BottomSheetFragment

//    @Inject
//    private lateinit var formFragment: FormFragment

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var getAllHabitsUseCase: GetAllHabitsUseCase

    val pageId = Random.nextLong()

    companion object {
        private const val HABITS_LIST_ARGS = "isUseful"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as App).appComponent.injectHabitsListFragment(this)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HabitsListViewModel(repository, getAllHabitsUseCase) as T
            }
        })[HabitsListViewModel::class.java]
        viewModel.setCurrentHabitsList(arguments?.getBoolean(HABITS_LIST_ARGS) ?: true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_habits_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager
            .beginTransaction()
            .add(R.id.bottomSheetFragmentPlaceholder, BottomSheetFragment())
            .commitNow()


        viewModel.currentHabitsList.observe(this.activity as LifecycleOwner) {
            habits.clear()
            habits.addAll(it)
            habitsRecyclerView.adapter?.notifyDataSetChanged()
        }

        habitsRecyclerView.adapter = HabitsListAdapter(habits, this)

        addHabit.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragmentPlaceholder, FormFragment.newInstance(null))
                ?.commit()
        }

    }

    override fun onItemClicked(habit: Habit) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragmentPlaceholder, FormFragment.newInstance(habit))
            ?.commit()
    }


//    override fun onResume() {
//        super.onResume()
//        viewModel.setCurrentHabitsList(arguments?.getBoolean(HABITS_LIST_ARGS) ?: true)
//    }


}