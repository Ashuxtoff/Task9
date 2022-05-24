package com.example.task9new.presentation.recyclerViewEntities

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.objects.Habit
import com.example.task9new.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_habit_list.*

class HabitsListViewHolder(override val containerView : View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bind(habit : Habit, clickListener: OnItemClickListener) {
        habitTitle.text = habit.title //пока я не сделал apply plugin в build.gradle и experimental = true, у меня не получалось увидеть это поле. Почему так, и правильно ли я решил проблему?
        habitDescription.text = habit.description
        habitPriority.text = habit.priority.toString()
        habitType.text = containerView.context.getString(habit.type.resId) // все это передам в гет стринг
        //habit_periodicity.text = "${habit.eventsCount} per ${habit.timeIntervalType.value}"

        habitPeriodicity.text = containerView.context.getString(
            R.string.periodicityValueString, habit.eventsCount,
            containerView.context.getString(habit.timeIntervalType.resId))
        // почему так не получилось?
        // можно брать контекст из вью


        containerView.setOnClickListener {
            clickListener.onItemClicked(habit)
        }

        containerView.findViewById<Button>(R.id.doHabitButton).setOnClickListener{
            clickListener.onItemButtonClicked(habit)
        }
    }



}