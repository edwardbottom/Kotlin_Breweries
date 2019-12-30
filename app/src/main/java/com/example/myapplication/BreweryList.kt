package com.example.myapplication

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_brewery_list.*
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
//import kotlinx.coroutines.launch


class BreweryList : Fragment() {
    private var breweryList: ArrayList<Brewery> = ArrayList<Brewery>()
    private lateinit var viewModel : BreweryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        // Get the ViewModel.
        viewModel= ViewModelProviders.of(this).get(BreweryViewModel::class.java)

        // inside onCreate()

        val job = GlobalScope.launch(Dispatchers.Main) {
            viewModel.getBreweries()
        }

        // Create the observer which updates the UI.
        val brewObserver = Observer<ArrayList<Brewery>> { newBreweries ->
            // Update the UI, in this case, a TextView.
            breweryList = newBreweries!!
            // RecyclerView node initialized here
            brewery_recycler_view.apply {
                // set a LinearLayoutManager to handle Android
                // RecyclerView behavior
                layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
                // set the custom adapter to the RecyclerView
                adapter = ListAdapter(viewModel.response.value as ArrayList<Brewery>)
            }

        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.response.observe(this, brewObserver)

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_brewery_list, container, false)

    // populate the views now that the layout has been inflated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        // RecyclerView node initialized here
//        brewery_recycler_view.apply {
//            // set a LinearLayoutManager to handle Android
//            // RecyclerView behavior
//            layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
//            // set the custom adapter to the RecyclerView
//            adapter = ListAdapter(viewModel.response.value as ArrayList<Brewery>)
//        }


    }

    //example of using callbacks instead of coroutines
//    private fun getData() {
//        val call: Call<List<Brewery>> = ApiClient.getClient.getBreweries()
//        call.enqueue(object : Callback<List<Brewery>> {
//            override fun onResponse(call: Call<List<Brewery>>?, response: Response<List<Brewery>>?) {
//                breweryList.addAll(response!!.body()!!)
//                println(breweryList.size.toString() + " is the size")
//                brewery_recycler_view.adapter?.notifyDataSetChanged()
//            }
//
//            override fun onFailure(call: Call<List<Brewery>>?, t: Throwable?) {
//                println("failed")
//            }
//
//        })
//    }

    companion object {
        fun newInstance(): BreweryList = BreweryList()
    }

    inner class BreweryViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.brewery_item, parent, false)) {
        private var breweryNameView: TextView? = null
        private var breweryAddressView: TextView? = null


        init {
            breweryNameView = itemView.findViewById(R.id.list_brewery_name)
            breweryAddressView = itemView.findViewById(R.id.list_brewery_address)
        }

        fun bind(brewery: Brewery) {
            breweryNameView?.text = brewery.name
            breweryAddressView?.text = brewery.street
        }

    }


    inner class ListAdapter(private val list: ArrayList<Brewery>)
        : RecyclerView.Adapter<BreweryViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreweryViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return BreweryViewHolder(inflater, parent)
        }

        override fun onBindViewHolder(holder: BreweryViewHolder, position: Int) {
            val movie: Brewery = list[position]
            holder.bind(movie)
        }

        override fun getItemCount(): Int = list.size

    }
}


