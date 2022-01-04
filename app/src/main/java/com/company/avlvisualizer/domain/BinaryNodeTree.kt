package com.company.avlvisualizer.domain

import java.util.*
import kotlin.math.max

class BinaryNodeTree(var isAVL: Boolean = false) : BinaryTree() {
    private var root: BinaryNode? = null

    override fun toString() = root?.toString() ?: "Empty"

    override fun insert(value: Int) {
        root = if (isAVL) {
            insertAVL(root, value)
        } else {
            insertUnbalanced(root, value)
        }
        size++
    }

    private fun insertUnbalanced(
        node: BinaryNode?,
        value: Int
    ): BinaryNode {
        node ?: return BinaryNode(value)
        if (value < node.value) {
            node.leftChild = insertUnbalanced(node.leftChild, value)
        } else {
            node.rightChild = insertUnbalanced(node.rightChild, value)
        }
        node.height = 1 + maxOf(node.leftChild?.height ?: 0, node.rightChild?.height ?: 0)
        return node
    }

    // 2nd value is the max height of tree
    override fun returnComposableData(): List<NodeComposableData> {
        val toReturn = mutableListOf<NodeComposableData>()
        root?.traverseInOrderWithPath(
            path = listOf(),
            visit = {
                toReturn.add(it)
            }
        )
        return toReturn
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

    override fun contains(value: Int): Boolean {
        root ?: return false

        var found = false
        root?.traverseInOrder {
            if (value == it.value) {
                found = true
            }
        }
        return found
    }


    override fun remove(value: Int) {
        root = if (isAVL) removeAVL(root, value)
        else removeUnbalanced(root, value)
        size--
    }

    private fun removeUnbalanced(node: BinaryNode?, value: Int): BinaryNode? {
        node ?: return null

        when {
            value == node.value -> {
                if (node.leftChild == null && node.rightChild == null) {
                    return null
                }
                if (node.leftChild == null) {
                    return node.rightChild
                }
                if (node.rightChild == null) {
                    return node.leftChild
                }
                node.rightChild?.min?.value?.let {
                    node.value = it
                }
                node.rightChild = removeUnbalanced(node.rightChild, node.value)
                // Targeted value has been found
            }
            value < node.value -> node.leftChild = removeUnbalanced(node.leftChild, value)
            else -> node.rightChild = removeUnbalanced(node.rightChild, value)
        }
        return node
    }

    private fun removeAVL(node: BinaryNode?, value: Int): BinaryNode? {
        node ?: return null

        when {
            value == node.value -> {
                // 1
                if (node.leftChild == null && node.rightChild == null) {
                    return null
                }
                // 2
                if (node.leftChild == null) {
                    return node.rightChild
                }
                // 3
                if (node.rightChild == null) {
                    return node.leftChild
                }
                // 4
                node.rightChild?.min?.value?.let {
                    node.value = it
                }

                node.rightChild = removeAVL(node.rightChild, node.value)
            }
            value < node.value -> node.leftChild = removeAVL(node.leftChild, value)
            else -> node.rightChild = removeAVL(node.rightChild, value)
        }
        val balancedNode = balanced(node)
        balancedNode.height = max(
            balancedNode.leftHeight,
            balancedNode.rightHeight
        ) + 1
        return balancedNode
    }

    // AVL FUNCTIONS
    private fun balanced(node: BinaryNode): BinaryNode {
        return when (node.balanceFactor) {
            2 -> {
                if (node.leftChild?.balanceFactor == -1) {
                    leftRightRotate(node)
                } else {
                    rightRotate(node)
                }
            }
            -2 -> {
                if (node.rightChild?.balanceFactor == 1) {
                    rightLeftRotate(node)
                } else {
                    leftRotate(node)
                }
            }
            else -> node
        }
    }

    private fun leftRotate(node: BinaryNode): BinaryNode {
        // 1
        val pivot = node.rightChild!!
        // 2
        node.rightChild = pivot.leftChild
        // 3
        pivot.leftChild = node
        // 4
        node.height = max(node.leftHeight, node.rightHeight) + 1
        pivot.height = max(pivot.leftHeight, pivot.rightHeight) + 1
        // 5
        return pivot
    }

    private fun rightRotate(node: BinaryNode): BinaryNode {
        val pivot = node.leftChild!!
        node.leftChild = pivot.rightChild
        pivot.rightChild = node
        node.height = max(node.leftHeight, node.rightHeight) + 1
        pivot.height = max(pivot.leftHeight, pivot.rightHeight) + 1
        return pivot
    }

    private fun rightLeftRotate(node: BinaryNode): BinaryNode {
        val rightChild = node.rightChild ?: return node
        node.rightChild = rightRotate(rightChild)
        return leftRotate(node)
    }

    private fun leftRightRotate(node: BinaryNode): BinaryNode {
        val leftChild = node.leftChild ?: return node
        node.leftChild = leftRotate(leftChild)
        return rightRotate(node)
    }

    private fun insertAVL(node: BinaryNode?, value: Int): BinaryNode {
        node ?: return BinaryNode(value)
        if (value < node.value) {
            node.leftChild = insertAVL(node.leftChild, value)
        } else {
            node.rightChild = insertAVL(node.rightChild, value)
        }
        val balancedNode = balanced(node)
        balancedNode.height = max(
            balancedNode.leftHeight,
            balancedNode.rightHeight
        ) + 1
        return balancedNode
    }

    override fun asBalancedTree(): BinaryNodeTree {
        val toReturn = BinaryNodeTree(isAVL = true)
        forEachLevelOrder {
            if (it != null) toReturn.insert(it.value)
        }
        return toReturn
    }

    override fun heapify(isMin: Boolean): HeapTree {
        val toReturn = HeapTree(isMin)
        root?.traverseInOrder {
            toReturn.insert(it.value)
        }
        return toReturn
    }

}