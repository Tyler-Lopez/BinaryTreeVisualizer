package com.company.avlvisualizer


typealias Visitor<T> = (T) -> Unit

class BSTNode<T>(val value: T) {
    var leftChild: BSTNode<T>? = null
    var rightChild: BSTNode<T>? = null

    fun height(node: BSTNode<T>? = this): Int {
        return node?.let {
            1 + maxOf(
                    height(node.leftChild),
                    height(node.rightChild)
            )
        } ?: -1
    }
}