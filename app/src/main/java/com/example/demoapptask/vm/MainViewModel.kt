package com.example.demoapptask.vm

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import com.example.demoapptask.AdapterClass
import com.example.demoapptask.UserClass
import com.example.demoapptask.network.ApiClient
import com.example.demoapptask.network.MyApi
import com.example.demoapptask.response.Data
import com.example.demoapptask.response.MyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    lateinit var recylerListData: MutableLiveData<List<Data>?>

    init {
        recylerListData = MutableLiveData()
    }

    fun getRecylerDataObserver(): MutableLiveData<List<Data>?> {
        return recylerListData
    }

    fun callApi(token: String) {

        val userClass = UserClass(76.7179, 30.7046)
        var apiInterface = ApiClient.client!!.create(MyApi::class.java)
        var result = apiInterface.getList(token, userClass)
        result.enqueue(object : Callback<MyResponse> {
            override fun onResponse(call: Call<MyResponse>, response: Response<MyResponse>) {
                if (response.isSuccessful) {
                    val resp = response.body()
                    if (resp != null) {
                        recylerListData.postValue(resp!!.data)
                    }
                } else {
                    recylerListData.postValue(null)
                }
            }

            override fun onFailure(call: Call<MyResponse>, t: Throwable) {
                recylerListData.postValue(null)

                Log.e("error", t.message.toString())
            }
        })

    }
}