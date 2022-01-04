package com.company.avlvisualizer.domain


class HeapTree(var isMin: Boolean = false) : BinaryTree() {

    var values: ArrayList<Int> = ArrayList()

    override var size: Int = 0
        get() = values.size


    override fun insert(value: Int) {
        TODO("Not yet implemented")
    }


    private fun siftUp(index: Int) {
        var child = index
        var parent = parentIndex(child)
        while (child > 0 && compare(values[child], values[parent]) > 0) {
            swap(child, parent)
            child = parent
            parent = parentIndex(child)
        }
    }

    private fun siftDown(index: Int) {
        var parent = index // 1
        while (true) { // 2
            val left = leftChildIndex(parent) //3
            val right = rightChildIndex(parent)
            var candidate = parent // 4
            if (left < size &&
                compare(values[left], values[candidate]) > 0
            ) {
                candidate = left //5
            }
            if (right < size &&
                compare(values[right], values[candidate]) > 0
            ) {
                candidate = right // 6
            }
            if (candidate == parent) {
                return // 7
            }
            swap(parent, candidate) // 8
            parent = candidate
        }
    }

    override fun remove(value: Int) {
        TODO("Not yet implemented")
    }

    override fun contains(value: Int): Boolean {
        for (value in values) {
            if (value == value)
                return true
        }
        return false
    }

    override fun returnComposableData(): List<NodeComposableData> {
        val toReturn = mutableListOf<NodeComposableData>()
        if (size <= 0) return toReturn
        traverseWithPath(curr = 0, height = getMaxHeight(), path = listOf()) {
            toReturn.add(it)
        }
        return toReturn
    }

    private fun traverseWithPath(curr: Int, height: Int, path: List<BinaryNodeChildType>, visit: ComposableNodeVisitor) {

        if (curr > values.lastIndex)
            return

        visit(NodeComposableData(path, values[curr], height))

        traverseWithPath(
            curr = leftChildIndex(curr),
            height = height - 1,
            clonePathWithInsert(path, BinaryNodeChildType.LEFT),
            visit
        )

        traverseWithPath(
            curr = rightChildIndex(curr),
            height = height - 1,
            clonePathWithInsert(path, BinaryNodeChildType.LEFT),
            visit
        )

    }

    private fun clonePathWithInsert(
        path: List<BinaryNodeChildType>,
        newMove: BinaryNodeChildType
    ): List<BinaryNodeChildType> {
        val toReturn = mutableListOf<BinaryNodeChildType>()
        for (move in path) {
            toReturn.add(move)
        }
        toReturn.add(newMove)
        return toReturn
    }

    override fun asBalancedTree(): BinaryNodeTree {
        TODO("Not yet implemented")
    }

    fun asUnbalancedTree(): BinaryNodeTree {
        TODO()
    }

    override fun heapify(isMin: Boolean): HeapTree {
        TODO("Not yet implemented")
    }

    // Accepts indices
    private fun swap(i: Int, j: Int) {
        val tmp = values[i]
        values[i] = values[j]
        values[j] = tmp
    }


    // Accepts values
    private fun compare(i: Int, j: Int): Int = if (isMin) i - j else j - i

    private fun getMaxHeight(): Int {
        var height = 0
        var curr = values.lastIndex
        while (curr != 0) {
            height++
            curr = parentIndex(curr)
        }
        return height
    }

    private fun leftChildIndex(index: Int) = (2 * index) + 1

    private fun rightChildIndex(index: Int) = (2 * index) + 2

    private fun parentIndex(index: Int) = (index - 1) / 2

}
