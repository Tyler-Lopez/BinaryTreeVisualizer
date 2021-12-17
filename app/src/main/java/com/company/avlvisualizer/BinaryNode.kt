package com.company.avlvisualizer

import androidx.compose.ui.geometry.Offset

typealias Visitor = (BinaryNode?) -> Unit
typealias ComposableNodeVisitor = (NodeComposableData) -> Unit

typealias OffsetVisitor = (Offset, BinaryNode?, Offset?) -> Unit

data class BinaryNode(
    val value: Int
) {
    var height: Int = 0
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

    fun clonePathWithInsert(path: List<BinaryNodeChild>, newMove: BinaryNodeChild): List<BinaryNodeChild> {
        val toReturn = mutableListOf<BinaryNodeChild>()
        for (move in path) {
            toReturn.add(move)
        }
        toReturn.add(newMove)
        return toReturn
    }

}