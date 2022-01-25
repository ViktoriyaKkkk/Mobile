package com.example.myapplication6.views

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication6.data.Node
import com.example.myapplication6.adapters.NodeClickListener
import com.example.myapplication6.adapters.NodeListAdapter
import com.example.myapplication6.databinding.FragmentMainBinding
import com.example.myapplication6.views.CreateNodeActivity.Companion.VALUE
import com.example.myapplication6.views.CreateNodeActivity.Companion.REQUEST_CREATE_NODE
import com.example.myapplication6.views.NodeRelationshipsActivity.Companion.NODE_ID
import com.example.myapplication6.views.NodeRelationshipsActivity.Companion.REQUEST_UPDATE_RELATIONSHIPS
import com.example.myapplication6.viewmodels.NodeViewModel
import com.example.myapplication6.viewmodels.NodeViewModelFactory
import com.example.myapplication6.viewmodels.RepositoryInitializer

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var recyclerView: RecyclerView
    private var nodeItems: MutableList<NodeItem> = mutableListOf()
    private var nodeViewModel: NodeViewModel? = null
    private val adapter: NodeListAdapter = NodeListAdapter(nodeItems, object : NodeClickListener {
        override fun showRelationshipActivity(id: Int) {
            val intent = Intent(this@MainFragment.context, NodeRelationshipsActivity::class.java)
            intent.putExtra(NODE_ID, id)
            startActivityForResult(intent, REQUEST_UPDATE_RELATIONSHIPS)
        }
    })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = adapter

        val viewModelFactory = NodeViewModelFactory(RepositoryInitializer.getRepository(requireContext()))
        nodeViewModel = ViewModelProvider(this, viewModelFactory).get(NodeViewModel::class.java)
        updateNodes()

        binding.createNodeButton.setOnClickListener {
            val intent = Intent(this@MainFragment.context, CreateNodeActivity::class.java)
            startActivityForResult(intent, REQUEST_CREATE_NODE)
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CREATE_NODE && resultCode == RESULT_OK) {
            data?.let {
                addNewNode(it)
            }
        }

        if (requestCode == REQUEST_UPDATE_RELATIONSHIPS) {
            updateNodes()
        }
    }

    private fun updateNodes() {
        nodeViewModel?.getAllNodes()?.let { newNodes ->
            nodeItems.clear()
            newNodes.forEach { node ->
                nodeItems.add(NodeItem(node, Color.valueOf(Color.WHITE)))
            }
            setColors()
            adapter.notifyDataSetChanged()
        }
    }


    private fun addNewNode(intent: Intent) {
        val node = Node(
            id = nodeItems.size + 1,
            value = intent.getStringExtra(VALUE).toString().toInt(),
            nodes = mutableListOf(),
        )
        nodeViewModel?.insertNode(node)
        nodeItems.add(NodeItem(node, Color.valueOf(Color.WHITE)))
        adapter.notifyDataSetChanged()
    }

    private fun setColors() {
        val allNodes = nodeItems.map { it.node }
        for (index in nodeItems.indices) {
            val node = nodeItems[index].node
            when {
                node.hasParent(allNodes) && node.nodes.size > 0 -> nodeItems[index] = NodeItem(node, Color.valueOf(Color.RED))
                node.hasParent(allNodes) -> nodeItems[index] = NodeItem(node, Color.valueOf(Color.BLUE))
                node.nodes.size > 0 -> nodeItems[index] = NodeItem(node, Color.valueOf(Color.YELLOW))
            }
        }
    }

}
