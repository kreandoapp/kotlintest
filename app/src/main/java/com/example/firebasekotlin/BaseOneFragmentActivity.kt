package com.example.firebasekotlin

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

abstract class BaseOneFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_one_fragment_activity_layout)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    protected fun showFragment(fragment: Fragment, bundle: Bundle?) {
        val supportFragmentManager = supportFragmentManager
        val transaction = supportFragmentManager.beginTransaction()
        fragment.arguments = bundle

        transaction.replace(R.id.frame_layout, fragment)
        transaction.commit()
    }

    protected fun showFragment(fragment: Fragment) {
        val supportFragmentManager = supportFragmentManager
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.frame_layout, fragment)
        transaction.commit()
    }

    protected fun showFragment(fragment: Fragment, animation: Boolean) {
        val supportFragmentManager = supportFragmentManager
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.bottom_to_top, R.anim.top_to_bottom);
        transaction.replace(R.id.frame_layout, fragment)
        transaction.commit()
    }

     fun addFragment(fragment: Fragment) {
        if (supportFragmentManager.findFragmentByTag(fragment.javaClass.simpleName) == null) {
            supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_start, R.anim.enter_end, R.anim.exit_start, R.anim.exit_end)
                    .add(R.id.frame_layout, fragment, fragment.javaClass.simpleName)
                    .addToBackStack(fragment.javaClass.simpleName)
                    .commit()
        }
    }

    fun volverMenu(){
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStackImmediate()
        }else{
            finish()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            finish()
        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            android.R.id.home -> {
                if (supportFragmentManager.backStackEntryCount > 1) {
                    supportFragmentManager.popBackStackImmediate()
                }else{
                    finish()
                    true
                }

            }

            else -> {
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                super.onOptionsItemSelected(item)
            }
        }
        return false

    }



    internal fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    // llamar desde laactividad siempre en OnStart
    protected fun setToolbarTitle(title: String) {
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar!!.title = title
    }

}