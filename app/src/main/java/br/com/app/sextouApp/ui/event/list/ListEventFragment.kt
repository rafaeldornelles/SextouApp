package br.com.app.sextouApp.ui.event.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.app.sextouApp.databinding.FragmentListEventBinding
import br.com.app.sextouApp.model.Event
import br.com.app.sextouApp.repository.ListenerList
import br.com.app.sextouApp.ui.event.list.adapter.EventAdapter

class ListEventFragment : Fragment() {

    private val viewModel: ListEventViewModel by lazy {
        ViewModelProvider(this).get(ListEventViewModel::class.java)
    }

    private lateinit var mAdapter : EventAdapter
    private lateinit var dataBinding : FragmentListEventBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = FragmentListEventBinding.inflate(inflater, container, false)
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this

        viewModel.listEvent(object: ListenerList<Event> {
            override fun onError(message: String?) {
                Toast.makeText(context,"Erro ao se conectar: ${message}",Toast.LENGTH_LONG).show()
            }

            override fun onSuccess(list: List<Event>) {
               createAdapter(list)
            }

            override fun onReload(list: List<Event>) {
                updateAdapter(list)
            }

        })
        return dataBinding.root
    }

    private fun createAdapter(list: List<Event>) {
        this.mAdapter = EventAdapter(list)
        val recycler = dataBinding.listEvent
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter
    }

    private fun updateAdapter(list: List<Event>) {
        this.mAdapter.updateList(list)
    }


}

