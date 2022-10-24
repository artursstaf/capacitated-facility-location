import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class SolutionTests {

    @Test
    fun testBasic() {
        val start = System.currentTimeMillis()
        val input = SolutionTests::class.java.getResource("test_input_1.json")!!.readText()
        val model = Model.fromJsonString(input)
        val solver = Solver(model)
        assertEquals(6620, solver.solveLAHC())
        println("Execution time ${(System.currentTimeMillis() - start)/1000.0}s")
    }

    @Test
    fun testConvergeLowerWarehouseCost() {
        val start = System.currentTimeMillis()
        val input = SolutionTests::class.java.getResource("test_input_2.json")!!.readText()
        val model = Model.fromJsonString(input)
        val solver = Solver(model)
        assertEquals(2880, solver.solveLAHC())
        println("Execution time ${(System.currentTimeMillis() - start)/1000.0}s")
    }

    @Test
    fun testConvergeLowerTransportationCost() {
        val start = System.currentTimeMillis()
        val input = SolutionTests::class.java.getResource("test_input_3.json")!!.readText()
        val model = Model.fromJsonString(input)
        val solver = Solver(model)
        assertEquals(1940, solver.solveLAHC())
        println("Execution time ${(System.currentTimeMillis() - start)/1000.0}s")
    }

    @Test
    fun testDoesNotExceedWarehouseCapacity() {
        val start = System.currentTimeMillis()
        val input = SolutionTests::class.java.getResource("test_input_4.json")!!.readText()
        val model = Model.fromJsonString(input)
        val solver = Solver(model)
        assertEquals(5550, solver.solveLAHC())
        println("Execution time ${(System.currentTimeMillis() - start)/1000.0}s")
    }

    @Test
    fun testBig() {
        val start = System.currentTimeMillis()
        val input = SolutionTests::class.java.getResource("test_input_5.json")!!.readText()
        val model = Model.fromJsonString(input)
        val solver = Solver(model)
        assert(solver.solveLAHC(n=50, earlyStopping=500000) < 100000)
        println("Execution time ${(System.currentTimeMillis() - start)/1000.0}s")
    }
}

