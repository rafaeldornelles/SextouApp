package br.com.app.sextouApp.ui.event.list

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.app.sextouApp.R
import br.com.app.sextouApp.databinding.FragmentListEventBinding
import br.com.app.sextouApp.model.Event
import br.com.app.sextouApp.repository.ListenerCrudFirebase
import br.com.app.sextouApp.repository.ListenerList
import br.com.app.sextouApp.ui.event.list.adapter.EventAdapter

class ListEventFragment : Fragment(), EventAdapter.ListenerEventClick {

    private val viewModel: ListEventViewModel by lazy {
        ViewModelProvider(this).get(ListEventViewModel::class.java)
    }

    private lateinit var mAdapter: EventAdapter
    private lateinit var dataBinding: FragmentListEventBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = FragmentListEventBinding.inflate(inflater, container, false)
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this

        dataBinding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listEventFragment_to_eventFormFragment)
        }

        viewModel.listEvent(object : ListenerList<Event> {
            override fun onError(message: String?) {
                Toast.makeText(context, "Erro ao se conectar: ${message}", Toast.LENGTH_LONG).show()
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
        this.mAdapter.listenerEventClick = this
        val recycler = dataBinding.listEvent
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = mAdapter
    }

    private fun updateAdapter(list: List<Event>) {
        this.mAdapter.updateList(list)
    }

    override fun onClickView(event: Event) {
        //Direciona a lista de produtos
    }

    override fun onClickUpdate(event: Event) {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle("Deseja Remover o evento ${event.name} da sua lista ?")
                setPositiveButton(R.string.remove,
                    DialogInterface.OnClickListener { dialog, id ->
                        deleteEvent(event)
                    })
                setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                    })
            }
            builder.create()
        }
        alertDialog?.show()



    }
    private fun deleteEvent(event:Event){

        viewModel.deleteEvent(event,object: ListenerCrudFirebase {
            override fun onSuccess() {
                Toast.makeText(context,"Evento Removido",Toast.LENGTH_LONG).show()
            }

            override fun onError() {
                Toast.makeText(context,"Erro ao remover",Toast.LENGTH_LONG).show()

            }

        })
    }
}

