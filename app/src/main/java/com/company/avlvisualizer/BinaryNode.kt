package com.company.avlvisualizer

import androidx.compose.ui.geometry.Offset

typealias Visitor = (BinaryNode?) -> Unit
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
}