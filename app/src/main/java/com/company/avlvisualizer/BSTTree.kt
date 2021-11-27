package com.company.avlvisualizer

import java.util.*

class BSTTree {

    var root: BSTNode? = null
    override fun toString() = root?.toString() ?: "Empty"

    fun insert(value: Int) {
        root = insert(root, value, 0)
    }

    private fun insert(
        node: BSTNode?,
        value: Int,
        index: Int
    ): BSTNode {
        node ?: return BSTNode(value, index)
        if (value < node.value) {
            node.leftChild = insert(node.leftChild, value, index)
        } else {
            node.rightChild = insert(node.rightChild, value, index)
        }
        return node
    }

    fun traversePreOrder(offsetVisit: OffsetVisitor) {

    }
    // Breadth-first traversal
    fun forEachLevelOrder(visit: Visitor) {
        // First, iterate back the root we are on
        visit(root)
        // Then, initialize a queue and populate with all children
        val queue = LinkedList<BSTNode?>()
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