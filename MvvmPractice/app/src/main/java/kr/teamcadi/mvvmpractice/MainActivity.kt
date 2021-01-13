package kr.teamcadi.mvvmpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kr.teamcadi.mvvmpractice.databinding.ActivityMainBinding
import kr.teamcadi.mvvmpractice.db.SubscriberDAO
import kr.teamcadi.mvvmpractice.db.SubscriberDatabase
import kr.teamcadi.mvvmpractice.db.SubscriberRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao:SubscriberDAO = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this,factory).get(SubscriberViewModel::class.java)
        binding.myViewModel = subscriberViewModel

        binding.lifecycleOwner = this
        initRecyclerView()
    }

    private fun initRecyclerView(){
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        displaySubscribersList()

    }

    private fun displaySubscribersList() {
        subscriberViewModel.subscribers.observe(this, Observer { Log.i("MYTAG",it.toString())
            binding.subscriberRecyclerView.adapter = MyRecyclerViewAdapter(it)
        })
    }
}