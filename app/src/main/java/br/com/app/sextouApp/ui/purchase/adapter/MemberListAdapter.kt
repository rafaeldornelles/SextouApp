package br.com.app.sextouApp.ui.purchase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.app.sextouApp.databinding.ItemMembersBinding
import br.com.app.sextouApp.model.Member

class MemberListAdapter(var members: List<Member>, private val listener: OnMemberClickListener): RecyclerView.Adapter<MemberListAdapter.MemberListViewHolder>() {
    private lateinit var binding: ItemMembersBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberListViewHolder {
        binding = ItemMembersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberListViewHolder, position: Int) {
        holder.bind(members[position], position)
    }

    override fun getItemCount(): Int {
        return members.size
    }

    inner class MemberListViewHolder(val binding: ItemMembersBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(member: Member, position: Int){
            binding.member = member
            binding.root.setOnClickListener { listener.onMemberClick(member, position) }
        }
    }

    interface OnMemberClickListener{
        fun onMemberClick(member: Member, position: Int)
    }
}