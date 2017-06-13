package com.github.sfyc23.simpleweather.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.antonioleiva.weatherapp.extensions.DelegatesExt
import com.github.sfyc23.simpleweather.R
import com.github.sfyc23.simpleweather.data.event.CityEvent
import com.github.sfyc23.simpleweather.ui.city.CityActivity
import com.github.sfyc23.simpleweather.util.rxbus.busRemoveAllStickyEvents
import com.github.sfyc23.simpleweather.util.rxbus.busRemoveStickyEvent
import com.github.sfyc23.simpleweather.util.rxbus.busToObservableSticky
import com.github.sfyc23.weather.ui.fragments.DailyFragment
import com.github.sfyc23.weather.ui.fragments.HourFragment
import com.github.sfyc23.weather.ui.fragments.OverviewFragment
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.jetbrains.anko.toast

class MainActivity : RxAppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    var overviewFragment: OverviewFragment? = null
    var dailyFragment: DailyFragment? = null
    var hourFragment: HourFragment? = null

    var spCityName: String by DelegatesExt.preference(CityActivity.SP_KEY_CITY_NAME, CityActivity.SP_VLAUE_DEFAULT_NAME)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = spCityName
        setSupportActionBar(toolbar)

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        navigationView.setCheckedItem(R.id.nav_overview)
        selectFragment(R.id.nav_overview)
//
        busToObservableSticky(CityEvent::class.java)
                .compose(this.bindToLifecycle())
                .subscribe {
                    toolbar.title = it.cityName

                }


    }


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
            R.id.action_city -> startActivity(CityActivity.getStartIntent(this))
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

        if (fragmentId != R.id.nav_about) {
            hideAllFragment(transaction)
        }

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
                hourFragment = HourFragment.newInstance()
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
        busRemoveStickyEvent(CityEvent::class.java)
        busRemoveAllStickyEvents()

    }
}
