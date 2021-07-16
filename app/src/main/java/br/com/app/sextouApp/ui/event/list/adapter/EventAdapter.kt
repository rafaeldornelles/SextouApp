package br.com.app.sextouApp.ui.event.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.app.sextouApp.R
import br.com.app.sextouApp.model.Event

class EventAdapter(var listEvent:List<Event>) :RecyclerView.Adapter<EventAdapter.EventViewHolder>(){
    var listenerEventClick: ListenerEventClick?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        var inflate = LayoutInflater.from(parent.context).inflate(R.layout.item_event,parent,false)
        return EventViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(listEvent[position])
    }

    override fun getItemCount(): Int {
        return listEvent.size
    }

    fun updateList(list:List<Event>){
        this.listEvent = list
        notifyDataSetChanged()
    }

    inner class EventViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        fun bind(event: Event){
            itemView.findViewById<TextView>(R.id.textTitle).text = event.name
            itemView.findViewById<TextView>(R.id.textData).text =
                if(event.date == null)
                "Sem data definida"
                else event.date

            if(event.status){
                itemView.findViewById<ImageView>(R.id.image_status).setImageResource(R.drawable.ic_done)
            }else{
                itemView.findViewById<ImageView>(R.id.image_status).setImageResource(R.drawable.ic_todo)
            }
        }
    }

    interface ListenerEventClick{
        fun onClickView(event:Event);
        fun onClickUpdate(event:Event);
    }
}