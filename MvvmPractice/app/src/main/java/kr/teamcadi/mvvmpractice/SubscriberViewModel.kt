package kr.teamcadi.mvvmpractice

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kr.teamcadi.mvvmpractice.db.Subscriber
import kr.teamcadi.mvvmpractice.db.SubscriberRepository

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel(),Observable {

    val subscribers = repository.subscribers

    @Bindable
    val inputName = MutableLiveData<String>()
    @Bindable
    val inputEmail = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()

    val clearAllOrDeleteButtonText = MutableLiveData<String>()


    init{
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun saveOrUpdate(){
        val name : String = inputName.value!!
        val email : String = inputEmail.value!!
        insert(Subscriber(0,name,email))
        inputName.value = null
        inputEmail.value = null
    }

    fun clearAllOrDelete(){
        clearAll()

    }
    fun insert(subscriber: Subscriber) : Job = viewModelScope.launch{
        viewModelScope.launch {
            repository.insert(subscriber)
        }
    }
    fun update(subscriber: Subscriber) : Job = viewModelScope.launch{
        viewModelScope.launch {
            repository.update(subscriber)
        }
    }
    fun delete(subscriber: Subscriber) : Job = viewModelScope.launch{
        viewModelScope.launch {
            repository.delete(subscriber)
        }
    }
    fun clearAll() : Job = viewModelScope.launch{
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}