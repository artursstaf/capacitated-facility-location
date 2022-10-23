import org.junit.jupiter.api.Test

internal class SolutionTests {

    @Test
    fun test_basic() {
        val input = Solver::class.java.getResource("test_input_1.json").readText()
        val model = Model.fromJsonString(input)
        val solver = Solver(model)
        solver.solve()
    }
}