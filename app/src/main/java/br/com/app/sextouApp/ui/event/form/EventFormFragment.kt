package br.com.app.sextouApp.ui.event.form

import android.app.DatePickerDialog
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import br.com.app.sextouApp.R
import br.com.app.sextouApp.databinding.EventFormFragmentBinding
import br.com.app.sextouApp.repository.ListenerCrudFirebase
import java.text.SimpleDateFormat
import java.util.*

class EventFormFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private val viewModel: EventFormViewModel by lazy {
        ViewModelProvider(this).get(EventFormViewModel::class.java)
    }
    private val mDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    private lateinit var dataBinding: EventFormFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = EventFormFragmentBinding.inflate(inflater, container, false)
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this
        dataBinding.buttonDate.setOnClickListener { showDatePicker(container?.context!!) }
        dataBinding.SaveButton.setOnClickListener { save(container?.context!!) }
        requireActivity().title = getString(R.string.novo_evento_title)
        return dataBinding.root
    }


    private fun save(context: Context){
        viewModel.save(dataBinding.buttonDate.text.toString(),object: ListenerCrudFirebase {
            override fun onSuccess() {
                Toast.makeText(context,"Novo evento Salvo",Toast.LENGTH_LONG).show()
                activity?.onBackPressed()
            }

            override fun onError() {
                Toast.makeText(context,"Erro ao salvar",Toast.LENGTH_LONG).show()

            }

        })


    }



    private fun showDatePicker(context: Context) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(context, this, year, month, day).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        dataBinding.buttonDate.text = mDateFormat.format(calendar.time)
    }

}