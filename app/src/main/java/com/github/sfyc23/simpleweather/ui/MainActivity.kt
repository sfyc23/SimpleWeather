package com.github.sfyc23.simpleweather.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.github.sfyc23.simpleweather.R
import com.github.sfyc23.simpleweather.ui.city.CityActivity
import com.github.sfyc23.simpleweather.util.rxbus.busRemoveAllStickyEvents
import com.github.sfyc23.weather.ui.fragments.DailyFragment
import com.github.sfyc23.weather.ui.fragments.HourFragment
import com.github.sfyc23.weather.ui.fragments.OverviewFragment
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var mFragment: Fragment? = null

    companion object Factory {
        val LAST_UPDATE_TIME = "lastUpdateTime"
        val DEFAULT_TIME = -1L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.mainFab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)


        selectFragment(R.id.nav_overview)

    }

    var overviewFragment: OverviewFragment? = null
    var dailyFragment: DailyFragment? = null
    var hourFragment: HourFragment? = null

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_city -> startActivity(CityActivity.getStartActivity(this))
            else -> toast("22")
        }
        return true
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        selectFragment(item.itemId)

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }


    private fun selectFragment(fragmentId: Int) {

        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        hideAllFragment(transaction)
        when (fragmentId) {
            R.id.nav_overview -> if (null == overviewFragment) {
                overviewFragment = OverviewFragment.newInstance()
                transaction.add(R.id.contentFrame, overviewFragment, "overviewFragment")
            } else {
                transaction.show(overviewFragment)
            }
            R.id.nav_daily -> if (null == dailyFragment) {
                dailyFragment = DailyFragment.newInstance()
                transaction.add(R.id.contentFrame, dailyFragment, "dailyFragment")
            } else {
                transaction.show(dailyFragment)
            }
            R.id.nav_hourly -> if (null == hourFragment) {
                hourFragment = HourFragment.newInstance ()
                transaction.add(R.id.contentFrame, hourFragment, "trending")
            } else {
                transaction.show(hourFragment)
            }

        }

        transaction.commit()
    }

    private fun hideAllFragment(transaction: FragmentTransaction) {
        if (null != overviewFragment) {
            transaction.hide(overviewFragment)
        }
        if (null != dailyFragment) {
            transaction.hide(dailyFragment)
        }
        if (null != hourFragment) {
            transaction.hide(hourFragment)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        busRemoveAllStickyEvents()
    }
}
