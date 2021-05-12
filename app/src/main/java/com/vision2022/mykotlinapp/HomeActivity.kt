package com.vision2022.mykotlinapp

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vision2022.mykotlinapp.baseui.BaseActivity
import com.vision2022.mykotlinapp.model.DataModel
import com.vision2022.mykotlinapp.network.ApiClient
import com.vision2022.mykotlinapp.ui.DataAdpter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : BaseActivity() {

    lateinit var progerssProgressDialog: ProgressDialog
    var dataList = ArrayList<DataModel>()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: DataAdpter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView = findViewById(R.id.recycler_view)
        //setting up the adapter
        recyclerView.adapter= DataAdpter(dataList,this)
        recyclerView.layoutManager= LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        progerssProgressDialog=ProgressDialog(this)
        progerssProgressDialog.setTitle("Loading")
        progerssProgressDialog.setCancelable(false)
        progerssProgressDialog.show()
        //get token from shared preference
        getData()
    }

    private fun getData() {
        val call: Call<List<DataModel>> = ApiClient.getClient.getPhotos()
        call.enqueue(object : Callback<List<DataModel>> {
            override fun onResponse(call: Call<List<DataModel>>?, response: Response<List<DataModel>>?) {
                progerssProgressDialog.dismiss()
                dataList.addAll(response!!.body()!!)
                recyclerView.adapter?.notifyDataSetChanged();
            }
            override fun onFailure(call: Call<List<DataModel>>?, t: Throwable?) {
                Toast.makeText(applicationContext,t.toString(), Toast.LENGTH_SHORT).show()
                progerssProgressDialog.dismiss()
            }
        })
    }

}