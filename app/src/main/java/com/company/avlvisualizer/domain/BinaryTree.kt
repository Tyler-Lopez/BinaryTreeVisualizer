package com.company.avlvisualizer.domain

abstract class BinaryTree {
    var size: Int = 0
        protected set

    abstract fun insert(value: Int)
    abstract fun remove(value: Int)
    abstract fun contains(value: Int): Boolean
    abstract fun returnComposableData(): List<NodeComposableData>
    abstract fun balanceTree(): BinaryNodeTree
}