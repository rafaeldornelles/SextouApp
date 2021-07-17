package br.com.app.sextouApp.ui.purchase.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.app.sextouApp.R
import br.com.app.sextouApp.databinding.FragmentMemberTabBinding
import br.com.app.sextouApp.model.Member
import br.com.app.sextouApp.ui.purchase.PurchaseViewModel
import br.com.app.sextouApp.ui.purchase.adapter.MemberListAdapter

class MembersTabFragment: Fragment(), MemberListAdapter.OnMemberClickListener {

    private lateinit var binding: FragmentMemberTabBinding
    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(PurchaseViewModel::class.java)
    }
    private val adapter by lazy { MemberListAdapter(viewModel.members.value?: emptyList(), this) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemberTabBinding.inflate(inflater, container, false)
        binding.membersRecyclerView.adapter = adapter
        binding.membersRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        binding.fabMember.setOnClickListener { findNavController().navigate(R.id.action_itemFragment_to_memberFormFragment) }
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.members.observe(viewLifecycleOwner){
            adapter.members = it
            adapter.notifyDataSetChanged()
        }
        viewModel.listMembers()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onMemberClick(member: Member, position: Int) {
        viewModel.setMemberFormValue(member, position)
        findNavController().navigate(R.id.action_itemFragment_to_memberFormFragment)
    }
}