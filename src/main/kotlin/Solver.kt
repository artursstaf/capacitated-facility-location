class Solver(
    private val Model: String

) {
    fun solve(): Solution = Solution("mock")
}

data class Solution(val mock: String)