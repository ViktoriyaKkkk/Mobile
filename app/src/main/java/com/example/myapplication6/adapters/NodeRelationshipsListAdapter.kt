package com.example.myapplication6.adapters

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication6.databinding.ItemRelationshipBinding

import com.example.myapplication6.data.Node
import com.example.myapplication6.views.NodeRelationshipItem
import com.example.myapplication6.views.NodeRelationshipType

class NodeRelationshipsListAdapter(
    private val nodes: List<NodeRelationshipItem>,
    private val currentNode: Node,
    private val nodeRelationshipType: NodeRelationshipType,
    private var nodeRelationshipClickListener: NodeRelationshipClickListener
) : RecyclerView.Adapter<NodeRelationshipsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRelationshipBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = nodes[position]
        val text = holder.relationship
        if (nodes[position].isRelationship) {
            text.setBackgroundColor(Color.GREEN)
        } else {
            text.setBackgroundColor(Color.WHITE)
        }
        text.text = "id:" + currentNode.id + " value:" + currentNode.value + " - " +
                "id:" + item.node.id + " value:" + item.node.value

        text.setOnClickListener {
            switchRelationship(item)
        }
    }

    private fun switchRelationship(item: NodeRelationshipItem) {
        val newRelationship = !item.isRelationship
        if (newRelationship) {
            if (nodeRelationshipType == NodeRelationshipType.PARENT) {
                currentNode.nodes.add(item.node)
                nodeRelationshipClickListener.onRelationshipClick(currentNode)
            } else {
                item.node.nodes.add(currentNode)
                nodeRelationshipClickListener.onRelationshipClick(item.node)
            }
        } else {
            if (nodeRelationshipType == NodeRelationshipType.PARENT) {
                currentNode.nodes.remove(item.node)
                nodeRelationshipClickListener.onRelationshipClick(currentNode)
            } else {
                item.node.nodes.remove(currentNode)
                nodeRelationshipClickListener.onRelationshipClick(item.node)
            }
        }
    }

    override fun getItemCount(): Int = nodes.size

    inner class ViewHolder(binding: ItemRelationshipBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val relationship: TextView = binding.itemRelationship
    }

}
