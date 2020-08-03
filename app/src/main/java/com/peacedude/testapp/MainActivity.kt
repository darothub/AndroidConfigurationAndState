package com.peacedude.testapp

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.*

const val PORTRAIT = 1
const val LANDSCAPE = 1
class MainActivity : AppCompatActivity() {
    var orientationChange: Int = 0
    var counter = 0
    lateinit var h: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var orienta = resources.configuration.orientation
        if(orienta == PORTRAIT) text_view.text = getString(R.string.portrait)
        else text_view.text = getString(R.string.landscape)

        Handler().postDelayed({ text_view_state.text = "oncreate" }, 1000)

        add_fragment_btn.setOnClickListener {
//            root_container.visibility = View.GONE
            var bundle = Bundle()

            if (supportFragmentManager.backStackEntryCount < 1){

                bundle.putString("position", "${supportFragmentManager.backStackEntryCount}")
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, FirstFragment().apply {
                        arguments = bundle
                    })
                    .addToBackStack("fragments")
                    .commit()


                Toast.makeText(this, "First fragment added", Toast.LENGTH_LONG).show()
            }
            else if (supportFragmentManager.backStackEntryCount == 1){
                bundle.putString("position", "${supportFragmentManager.backStackEntryCount}")
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, SecondFragment().apply {
                        arguments = bundle
                    })
                    .addToBackStack("fragments")
                    .commit()
                Toast.makeText(this, "Second fragment added", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this, "No fragment to add", Toast.LENGTH_LONG).show()
            }

        }

        remove_fragment_btn.setOnClickListener {
            supportFragmentManager.popBackStack()
        }



    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        counter += 1
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show()
            text_view.text = getString(R.string.landscape)
            text_view.append(" $counter")
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            text_view.text = getString(R.string.portrait)
            text_view.append(" $counter")
        }

        Log.i("Main", "$counter")
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        var textCounter = counter
        outState.putInt("counter", textCounter)
        Log.i("Main", "Savedstated")
    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed({ text_view_state.text = "onStart" }, 2000)

    }

    override fun onResume() {
        super.onResume()
//        text_view.append(" $counter")
        Handler().postDelayed({ text_view_state.text = "onResume" }, 3000)

    }

    override fun onPause() {
        Handler().postDelayed({ text_view_state.text = "onPause" }, 4000)
        super.onPause()

    }

    override fun onDestroy() {
        Handler().postDelayed({ text_view_state.text = "onDestroy" }, 5000)
        super.onDestroy()

    }

    override fun onBackPressed() {
        super.onBackPressed()

        if(supportFragmentManager.backStackEntryCount <= 1){
            root_container.visibility = View.VISIBLE
        }
    }


}

