package com.company.avlvisualizer.domain

abstract class BinaryTree {
    open var size: Int = 0
        protected set

    abstract fun insert(value: Int)
    abstract fun remove(value: Int)
    abstract fun contains(value: Int): Boolean
    abstract fun returnComposableData(): List<NodeComposableData>
    abstract fun asBalancedTree(): BinaryNodeTree
    abstract fun heapify(isMin: Boolean): HeapTree
}