package com.company.avlvisualizer

enum class BinaryTreeBalanceType : Dropdownable {
    UNBALANCED {
        override fun toString(): String = "Unbalanced"
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