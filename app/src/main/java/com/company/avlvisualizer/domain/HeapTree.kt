package com.company.avlvisualizer.domain


class HeapTree(var isMin: Boolean = false) : BinaryTree() {

    var elements: ArrayList<Int> = ArrayList()

    override var size: Int = 0
        get() = elements.size


    override fun insert(value: Int) {
        elements.add(value) // 1
        siftUp(size - 1) // 2
        //
    }


    private fun siftUp(index: Int) {
        var child = index
        var parent = parentIndex(child)
        while (child > 0 && compare(elements[child], elements[parent]) > 0) {
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
                compare(elements[left], elements[candidate]) > 0
            ) {
                candidate = left //5
            }
            if (right < size &&
                compare(elements[right], elements[candidate]) > 0
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
        val index = indexAt(value)
        if (index >= size)
            return // 1

        if (index == size - 1) {
            elements.removeAt(size - 1) // 2
        } else {
            swap(index, size - 1) // 3
            val item = elements.removeAt(size - 1) // 4
            siftDown(index) // 5
            siftUp(index)
            item
        }
    }

    fun indexAt(value: Int): Int {
        for ((index, element) in elements.withIndex()) {
            if (value == element)
                return index
        }
        return -1
    }

    override fun contains(value: Int): Boolean {
        for (element in elements) {
            if (value == element)
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

    private fun traverseWithPath(
        curr: Int,
        height: Int,
        path: List<BinaryNodeChildType>,
        visit: ComposableNodeVisitor
    ) {
        if (curr > elements.lastIndex)
            return
        visit(NodeComposableData(path, elements[curr], height))
        traverseWithPath(
            curr = leftChildIndex(curr),
            height = height - 1,
            clonePathWithInsert(path, BinaryNodeChildType.LEFT),
            visit
        )
        traverseWithPath(
            curr = rightChildIndex(curr),
            height = height - 1,
            clonePathWithInsert(path, BinaryNodeChildType.RIGHT),
            visit
        )
    }
    private fun traverse(
        curr: Int,
        visit: (Int) -> Unit
    ) {
        if (curr > elements.lastIndex)
            return
        visit(elements[curr])
        traverse(curr = leftChildIndex(curr), visit = visit)
        traverse(curr = rightChildIndex(curr), visit = visit)
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
        val toReturn = BinaryNodeTree(isAVL = true)
        for (element in elements) toReturn.insert(element)
        return toReturn
    }

    fun asUnbalancedTree(): BinaryNodeTree {
        val toReturn = BinaryNodeTree(isAVL = false)
        traverse(0) {
            toReturn.insert(it)
        }
        return toReturn
    }

    override fun heapify(isMin: Boolean): HeapTree {
        this.isMin = isMin

        if (elements.isNotEmpty()) {
            (size / 2 downTo 0).forEach {
                siftDown(it)
            }
        }
        return this
    }

    // Accepts indices
    private fun swap(i: Int, j: Int) {
        val tmp = elements[i]
        elements[i] = elements[j]
        elements[j] = tmp
    }


    // Accepts values
    private fun compare(i: Int, j: Int): Int = if (isMin) j - i else i - j

    private fun getMaxHeight(): Int {
        var height = 0
        var curr = elements.lastIndex
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
