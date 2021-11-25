package com.company.avlvisualizer

import java.util.*


typealias Visitor = (BSTNode?) -> Unit


class BSTNode(
    val value: Int,
    var depth: Int = 1
) {

    var leftChild: BSTNode? = null
    var rightChild: BSTNode? = null

    fun height(node: BSTNode? = this): Int {
        return node?.let {
            1 + maxOf(
                    height(node.leftChild),
                    height(node.rightChild)
            )
        } ?: -1
    }


    // Breadth-first traversal
    fun forEachLevelOrder(visit: Visitor) {
        // First, iterate back the root we are on
        visit(this)
        // Then, initialize a queue and populate with all children
        val queue = ArrayDeque<BSTNode?>()
        queue.addLast(this.leftChild)
        queue.addLast(this.rightChild)

        // Create a temporary node used to process the queue
        // Dequeue first child

        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            // Iterate back the value
            visit(node)
            if (node != null) {
                queue.addLast(node.leftChild)
                queue.addLast(node.rightChild)
            }
            // Move on to either next child or next level w/e first
            else break
        }

    }

    // Breadth-first list

    fun traverseInOrder(visit: Visitor) {
        leftChild?.traverseInOrder(visit) // If left child is NOT null traverse to it
        visit(this) // Callback to visitor
        rightChild?.traverseInOrder(visit)
    }

    override fun toString() = diagram(this)

    private fun diagram(node: BSTNode?,
                        top: String = "",
                        root: String = "",
                        bottom: String = ""): String {
        return node?.let {
            if (node.leftChild == null && node.rightChild == null) {
                "$root${node.value}\n"
            } else {
                diagram(node.rightChild, "$top ", "$top┌──", "$top│ ") +
                        root + "${node.value}\n" + diagram(node.leftChild, "$bottom│ ", "$bottom└──", "$bottom ")
            }
        } ?: "${root}null\n"
    }
}