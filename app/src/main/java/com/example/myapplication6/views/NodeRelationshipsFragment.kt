package com.example.myapplication6.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication6.data.Node
import com.example.myapplication6.adapters.NodeRelationshipClickListener
import com.example.myapplication6.adapters.NodeRelationshipsListAdapter
import com.example.myapplication6.databinding.FragmentNodeRelationshipsBinding
import com.example.myapplication6.viewmodels.NodeViewModel
import com.example.myapplication6.viewmodels.NodeViewModelFactory
import com.example.myapplication6.viewmodels.RepositoryInitializer

class NodeRelationshipsFragment : Fragment() {

    private lateinit var binding: FragmentNodeRelationshipsBinding
    private var nodeId = 1
    private var nodeRelationshipType: NodeRelationshipType = NodeRelationshipType.PARENT
    private lateinit var recyclerView: RecyclerView
    private lateinit var nodeViewModel: NodeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nodeId = it.getInt(ARG_NODE_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNodeRelationshipsBinding.inflate(inflater)

        recyclerView = binding.recyclerView

        val viewModelFactory = NodeViewModelFactory(RepositoryInitializer.getRepository(requireContext()))
        nodeViewModel = ViewModelProvider(this, viewModelFactory)
            .get(NodeViewModel::class.java)

        initNodes()

        binding.parentButton.setOnClickListener{
            binding.childButton.isChecked = false
            binding.parentButton.isChecked = true
            nodeRelationshipType = NodeRelationshipType.PARENT
            initNodes()
        }

        binding.childButton.setOnClickListener {
            binding.childButton.isChecked = true
            binding.parentButton.isChecked = false
            nodeRelationshipType = NodeRelationshipType.CHILD
            initNodes()
        }

        return binding.root
    }

    private fun initNodes() {
        val listNodes = nodeViewModel.getAllNodes()
        val nodeList: MutableList<NodeRelationshipItem> = mutableListOf()
        val currentNode = listNodes.find { it.id == nodeId }!!

        listNodes.forEach { node ->
            when (nodeRelationshipType) {
                NodeRelationshipType.PARENT -> {
                    if (node.canBeChildFor(currentNode)) {
                        nodeList.add(NodeRelationshipItem(node, currentNode.hasDirectChild(node)))
                    }

                }
                NodeRelationshipType.CHILD -> {
                    if (currentNode.canBeChildFor(node)) {
                        nodeList.add(NodeRelationshipItem(node, node.hasDirectChild(currentNode)))
                    }
                }
            }
        }

        with(recyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = NodeRelationshipsListAdapter(nodeList, currentNode, nodeRelationshipType, object : NodeRelationshipClickListener {
                override fun onRelationshipClick(parent: Node) {
                    nodeViewModel.updateNode(parent.id, parent.nodes).invokeOnCompletion {
                        initNodes()
                    }
                }
            })
        }
    }

    companion object {

        const val ARG_NODE_ID = ""

        fun newInstance(nodeId: Int) =
            NodeRelationshipsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_NODE_ID, nodeId)
                }
            }
    }
}