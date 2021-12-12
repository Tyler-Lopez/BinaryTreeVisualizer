package com.company.avlvisualizer

/*

We will traverse a list of node data, and return a list of node composable data

 */

data class NodeComposableData(
    val path: List<BinaryNodeChild>, // EX: LEFT, LEFT, RIGHT, LEFT
    val value: Int,
)
