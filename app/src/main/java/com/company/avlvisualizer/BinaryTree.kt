package com.company.avlvisualizer

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import java.util.*

class BinaryTree {

    var root: BinaryNode? = null
    override fun toString() = root?.toString() ?: "Empty"

    fun insert(value: Int) {
        root = insert(root, value)
    }

    private fun insert(
        node: BinaryNode?,
        value: Int
    ): BinaryNode {
        node ?: return BinaryNode(value)
        if (value < node.value) {
            node.leftChild = insert(node.leftChild, value)
        } else {
            node.rightChild = insert(node.rightChild, value)
        }
        node.height = 1 + maxOf(node.leftChild?.height ?: 0, node.rightChild?.height ?: 0)
        return node
    }

    fun traversePreOrder(offsetVisit: OffsetVisitor) {
        traversePreOrder(offsetVisit, Offset(0f, 0f), root, 0)
    }

    private fun traversePreOrder(offsetVisit: OffsetVisitor, offset: Offset, node: BinaryNode?, depth: Int) {
        offsetVisit(offset, node)
        if (node?.leftChild != null) traversePreOrder(offsetVisit, Offset(x = offset.x - (100f * (node.height + 1)), y = offset.y + 100f), node?.leftChild, depth + 1)
        if (node?.rightChild != null) traversePreOrder(offsetVisit, Offset(x = offset.x + (100f * (node.height + 1)), y = offset.y + 100f), node?.rightChild, depth + 1)

    }
    // Breadth-first traversal
    fun forEachLevelOrder(visit: Visitor) {
        // First, iterate back the root we are on
        visit(root)
        // Then, initialize a queue and populate with all children
        val queue = LinkedList<BinaryNode?>()
        queue.addLast(root?.leftChild)
        queue.addLast(root?.rightChild)

        // Create a temporary node used to process the queue
        // Dequeue first child

        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            // Iterate back the value
            visit(node)
            if (node == null) {
                continue
            }
            queue.addLast(node.leftChild)
            queue.addLast(node.rightChild)
        }
        // Move on to either next child or next level w/e first
    }
}