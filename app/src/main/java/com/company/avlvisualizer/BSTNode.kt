package com.company.avlvisualizer

import androidx.compose.ui.geometry.Offset
import java.util.*


typealias Visitor = (BSTNode?) -> Unit
typealias OffsetVisitor = (Offset, BSTNode?) -> Unit

class BSTNode(
    val value: Int,
    var index: Int
) {

    var leftChild: BSTNode? = null
    var rightChild: BSTNode? = null


    override fun toString() = diagram(this)

    private fun diagram(
        node: BSTNode?,
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