package com.company.avlvisualizer

// This is selectable from a drop-down, thus implements dropdownable
// Does not override .thumbnail() in parent
enum class BinaryTreeBalanceType : Dropdownable {
    UNBALANCED {
        override fun toString(): String = "None"
    },
    AVL_TREE {
        override fun toString(): String = "AVL Tree"
    };

    companion object {
        fun getBalanceTypes(): List<BinaryTreeBalanceType> {
            return listOf(
                UNBALANCED,
                AVL_TREE
            )
        }
    }
}