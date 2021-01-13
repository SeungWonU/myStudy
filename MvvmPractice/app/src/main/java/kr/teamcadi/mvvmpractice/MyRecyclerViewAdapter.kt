package kr.teamcadi.mvvmpractice

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.teamcadi.mvvmpractice.databinding.ListItemBinding
import kr.teamcadi.mvvmpractice.db.Subscriber

class MyRecyclerViewAdapter(private val subscribers: List<Subscriber>) : RecyclerView.Adapter<MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
    }

}

class MyViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(subscriber: Subscriber){
        binding.nameTextView.text = subscriber.name
        binding.emailTextView.text = subscriber.email
    }
}