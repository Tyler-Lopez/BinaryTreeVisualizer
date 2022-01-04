package com.company.avlvisualizer.domain

// This is selectable from a drop-down, thus implements dropdownable
// Does not override .Thumbnail() in parent
enum class BinaryTreeBalanceType : Dropdownable {
    UNBALANCED {
        override fun toString(): String = "None"
    },
    AVL_TREE {
        override fun toString(): String = "AVL Tree"
    },
    MIN_HEAP {
        override fun toString(): String = "MIN Heap"
    },
    MAX_HEAP {
        override fun toString(): String = "MAX Heap"
    };

    companion object {
        fun getBalanceTypes(): List<BinaryTreeBalanceType> {
            return listOf(
                UNBALANCED,
                AVL_TREE,
                MIN_HEAP,
                MAX_HEAP
            )
        }
    }
}