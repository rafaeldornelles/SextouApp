package br.com.app.sextouApp.ui.event.adapter

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class EventAdapter :RecyclerView.Adapter<EventAdapter.EventViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


    inner class EventViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        fun bind(){

        }
    }

    interface ListenerEventClick{
        fun onClickView();
        fun onClickUpdate();
    }
}