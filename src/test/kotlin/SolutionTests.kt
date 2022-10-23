import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class SolutionTests {

    @Test
    fun test_basic() {
        val input = Solver::class.java.getResource("test_input_1.json")!!.readText()
        val model = Model.fromJsonString(input)
        val solver = Solver(model)

        assertEquals(6620, solver.solveLAHC())
    }

    @Test
    fun test_big() {
        val input = Solver::class.java.getResource("test_input_2.json")!!.readText()
        val model = Model.fromJsonString(input)
        val solver = Solver(model)

        assertEquals(88315, solver.solveLAHC(n=10000, earlyStopping=500000))
    }
}