package com.company.avlvisualizer

import androidx.compose.ui.geometry.Offset
import java.lang.Integer.max

typealias Visitor = (BinaryNode?) -> Unit
typealias ComposableNodeVisitor = (NodeComposableData) -> Unit

typealias OffsetVisitor = (Offset, BinaryNode?, Offset?) -> Unit

data class BinaryNode(
    var value: Int
) {
    // CONSIDERING PUTTING THIS IN A DIFFERENT CLASS...
    var leftChild: BinaryNode? = null
    var rightChild: BinaryNode? = null

    val min: BinaryNode?
        get() = leftChild?.min ?: this

    var height = 0

    val leftHeight: Int
        get() = leftChild?.height ?: -1

    val rightHeight: Int
        get() = rightChild?.height ?: -1

    val balanceFactor: Int
        get() = leftHeight - rightHeight


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


    fun traverseInOrder(visit: (BinaryNode) -> Unit) {
        visit(this)
        leftChild?.traverseInOrder(visit)
        rightChild?.traverseInOrder(visit)
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
}