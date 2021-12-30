package com.company.avlvisualizer

import androidx.compose.ui.geometry.Offset
import java.util.*
import kotlin.math.pow
import kotlin.math.max

class BinaryTree {

    var root: BinaryNode? = null
    override fun toString() = root?.toString() ?: "Empty"

    fun insert(value: Int, avlInsert: Boolean = false) {
        root = if (avlInsert) {
            insertAVL(root, value)
        } else {
            insertUnbalanced(root, value)
        }
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

    fun traversePreOrder(offsetVisit: OffsetVisitor) {
        traversePreOrder(offsetVisit, Offset(0f, 0f), root, 0)
    }

    // 2nd value is the max height of tree
    fun returnComposableData(): List<NodeComposableData> {
        val toReturn = mutableListOf<NodeComposableData>()
        root?.traverseInOrderWithPath(
            path = listOf(),
            visit = {
                toReturn.add(it)
            }
        )
        return toReturn
    }


    private fun traversePreOrder(
        offsetVisit: OffsetVisitor,
        offset: Offset,
        node: BinaryNode?,
        depth: Int,
        parentOffset: Offset? = null
    ) {
        offsetVisit(offset, node, parentOffset)
        if (node?.leftChild != null) traversePreOrder(
            offsetVisit,
            Offset(
                x = offset.x - 1000f - ((node.height * node.height).toDouble().pow(3).toFloat()),
                y = offset.y + 80000f
            ),
            node?.leftChild,
            depth + 1,
            offset
        )
        if (node?.rightChild != null) traversePreOrder(
            offsetVisit,
            Offset(
                x = offset.x + 1000f + ((node.height * node.height).toDouble().pow(3).toFloat()),
                y = offset.y + 80000f
            ),
            node?.rightChild,
            depth + 1,
            offset
        )
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

    fun contains(value: Int): Boolean {
        forEachLevelOrder {
            if (value == it?.value) {
                return@forEachLevelOrder
            }
        }
        return false
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

    fun balanceTree(): BinaryTree {
        val toReturn = BinaryTree()
        forEachLevelOrder {
            if (it != null) toReturn.insert(it.value, true)
        }
        return toReturn
    }
}