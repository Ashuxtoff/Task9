package com.example.task9new.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.domain.useCases.GetAllHabitsUseCase
import com.example.task9new.App
import com.example.task9new.R
import com.example.task9new.presentation.fragments.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity :
    AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

//    @Inject
//    lateinit var baseHabitsListFragment : BaseHabitsListFragment
//
//    @Inject
//    lateinit var appInformationFragment: AppInformationFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as App).appComponent.injectMainActivity(this)

        val drawerToggle = ActionBarDrawerToggle(this, navigationDrawerLayout, R.string.openDrawer, R.string.closeDrawer)
        navigationDrawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState() // cинхронизирует стотояние бургера или стрелка назад при открывании/закрывании меню??
        navigationViewMenu.setNavigationItemSelectedListener(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.habitsListToolbarTitle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentPlaceholder, BaseHabitsListFragment.newInstance())
            .commit()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuItemStartScreen -> {
                navigationDrawerLayout.closeDrawer(GravityCompat.START)
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentPlaceholder, BaseHabitsListFragment.newInstance())
                    .commit()
            }

            R.id.menuItemAppInformation -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentPlaceholder, AppInformationFragment.newInstance())
                    .commit()
            }
        }
        navigationDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onOptionsItemSelected(item : MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {

            if (navigationDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                navigationDrawerLayout.closeDrawer(GravityCompat.START)
                return true
            }

            val currentFragment =
                supportFragmentManager.fragments.last().childFragmentManager.fragments.find { it.isVisible }
                    ?:
                    supportFragmentManager.fragments.last()


            if (currentFragment != null &&
                (currentFragment is AppInformationFragment || currentFragment is FormFragment)
            ) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragmentPlaceholder, BaseHabitsListFragment.newInstance())
                    .commit()
            }

            if (currentFragment != null &&
                (currentFragment is BottomSheetFragment || currentFragment is HabitsListFragment)) {
                navigationDrawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return true
    }
}