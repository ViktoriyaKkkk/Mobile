package com.example.lesson_4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lesson_4.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


interface ActivityFunctions {
    fun showLikeClickEvent(name: String)
    fun showFormClickEvent(name: String)
}

interface AsyncActivity {
    fun updateAdapterData()
    fun getCurrentLenght(): Int
}

class MainActivity : AppCompatActivity(), AsyncActivity {

    private var personsToAddData = mutableListOf<Persons>()
    private var personsCurrentList = mutableListOf<Persons>()

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PersonsAdapter
    private var fragment: AsyncFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = PersonsAdapter(object : ActivityFunctions {
            override fun showLikeClickEvent(name: String) {
                Snackbar.make(binding.root, "Нажат лайк: $name", 500).show()
            }

            override fun showFormClickEvent(name: String) {
                Snackbar.make(binding.root, "Нажата карточка: $name", 500).show()
            }
        })


        val recentFragment = supportFragmentManager.findFragmentByTag(AsyncFragment.TAG)

        if (recentFragment == null) {
            fragment = AsyncFragment()
            Log.i("[APP]", "Create AsyncFragment")
            supportFragmentManager.beginTransaction().add(fragment!!, AsyncFragment.TAG).commit()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.recycleview.layoutManager = layoutManager
        binding.recycleview.adapter = adapter

        personsCurrentList = PersonsHolder.getPersonsList()

    }

    override fun updateAdapterData() {
        if (personsCurrentList.size > 0) personsToAddData.add(personsCurrentList.removeFirst())

        adapter.personsList = personsToAddData
        adapter.notifyDataSetChanged()
    }

    override fun getCurrentLenght(): Int {
        return personsCurrentList.size
    }


}