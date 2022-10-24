import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class SolutionTests {

    @Test
    fun testBasic() {
        val input = SolutionTests::class.java.getResource("test_input_1.json")!!.readText()
        val model = Model.fromJsonString(input)
        val solver = Solver(model)

        assertEquals(6620, solver.solveLAHC())
    }

    @Test
    fun testConvergeLowerWarehouseCost() {
        val input = SolutionTests::class.java.getResource("test_input_2.json")!!.readText()
        val model = Model.fromJsonString(input)
        val solver = Solver(model)

        assertEquals(2880, solver.solveLAHC())
    }

    @Test
    fun testConvergeLowerTransportationCost() {
        val input = SolutionTests::class.java.getResource("test_input_3.json")!!.readText()
        val model = Model.fromJsonString(input)
        val solver = Solver(model)

        assertEquals(1940, solver.solveLAHC())
    }

    @Test
    fun testDoesNotExceedWarehouseCapacity() {
        val input = SolutionTests::class.java.getResource("test_input_4.json")!!.readText()
        val model = Model.fromJsonString(input)
        val solver = Solver(model)

        assertEquals(5550, solver.solveLAHC())
    }

    @Test
    fun testBig() {
        val input = SolutionTests::class.java.getResource("test_input_5.json")!!.readText()
        val model = Model.fromJsonString(input)
        val solver = Solver(model)

        assert(solver.solveLAHC(n=50, earlyStopping=500000) < 100000)
    }
}

