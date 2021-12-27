package com.company.avlvisualizer

import androidx.compose.ui.geometry.Offset
import java.lang.Integer.max

typealias Visitor = (BinaryNode?) -> Unit
typealias ComposableNodeVisitor = (NodeComposableData) -> Unit

typealias OffsetVisitor = (Offset, BinaryNode?, Offset?) -> Unit

data class BinaryNode(
    val value: Int
) {
    var height: Int = 0

    // CONSIDERING PUTTING THIS IN A DIFFERENT CLASS...
    val leftHeight: Int
        get() = leftChild?.height ?: -1

    val rightHeight: Int
        get() = rightChild?.height ?: -1

    val balanceFactor: Int
        get() = leftHeight - rightHeight

    var leftChild: BinaryNode? = null
    var rightChild: BinaryNode? = null


    override fun toString() = diagram(this)

    private fun diagram(
        node: BinaryNode?,
        top: String = "",
        root: String = "",
        bottom: String = ""
    ): String {
        return node?.let {
            if (node.leftChild == null && node.rightChild == null) {
                "$root${node.value}\n"
            } else {
                diagram(node.rightChild, "$top ", "$top┌──", "$top│ ") +
                        root + "${node.value}\n" + diagram(
                    node.leftChild,
                    "$bottom│ ",
                    "$bottom└──",
                    "$bottom "
                )
            }
        } ?: "${root}null\n"
    }


    fun traverseInOrderWithPath(path: List<BinaryNodeChild>, visit: ComposableNodeVisitor) {
        visit(NodeComposableData(path, value, height))
        leftChild?.traverseInOrderWithPath(clonePathWithInsert(path, BinaryNodeChild.LEFT), visit)
        rightChild?.traverseInOrderWithPath(clonePathWithInsert(path, BinaryNodeChild.RIGHT), visit)
    }

    fun clonePathWithInsert(
        path: List<BinaryNodeChild>,
        newMove: BinaryNodeChild
    ): List<BinaryNodeChild> {
        val toReturn = mutableListOf<BinaryNodeChild>()
        for (move in path) {
            toReturn.add(move)
        }
        toReturn.add(newMove)
        return toReturn
    }

    // AVL BALANCING FUNCTIONS
    companion object {
        private fun leftRotate(node: BinaryNode): BinaryNode {
            val pivot = node.rightChild!!
            node.rightChild = pivot.leftChild
            pivot.leftChild = node
            node.height = maxOf(node.leftHeight, node.rightHeight) + 1
            pivot.height = maxOf(pivot.leftHeight, node.rightHeight) + 1
            return pivot
        }

        private fun rightRotate(node: BinaryNode): BinaryNode {
            val pivot = node.leftChild!!
            node.leftChild = pivot.rightChild
            pivot.rightChild = node
            node.height = maxOf(node.leftHeight, node.rightHeight) + 1
            pivot.height = maxOf(pivot.leftHeight, node.rightHeight) + 1
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

        fun balanced(node: BinaryNode): BinaryNode {
            return when (node.balanceFactor) {
                2 -> {
                    if (node.leftChild?.balanceFactor == -1) {
                        leftRightRotate(node)
                    } else {
                        rightRotate(node)
                    }
                }
                3 -> {
                    if (node.rightChild?.balanceFactor == 1) {
                        rightLeftRotate(node)
                    } else {
                        leftRotate(node)
                    }
                }
                else -> node
            }
        }
    }
}