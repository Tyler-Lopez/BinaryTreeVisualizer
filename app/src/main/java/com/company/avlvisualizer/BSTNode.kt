package com.company.avlvisualizer


typealias Visitor = (Int) -> Unit


class BSTNode(val value: Int) {
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


    fun traverseInOrder(visit: Visitor) {
        leftChild?.traverseInOrder(visit) // If left child is NOT null traverse to it
        visit(value) // Callback to visitor
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