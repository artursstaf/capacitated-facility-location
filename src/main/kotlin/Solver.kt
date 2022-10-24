class Solver(
    private val model: Model,
) {
    // Randomly initialize solution (Retailers to random Warehouse)
    private var solution: Map<Retailer, Warehouse> = model.retailers.associateWith { model.warehouses.random() }.toMutableMap()
    private var bestSolution: Map<Retailer, Warehouse> = solution.toMap()
    private var bestSolutionCost: Int = calculateCost(bestSolution)


    companion object {
        private const val HARD_CONSTRAINT_MULTIPLIER = 1_000_000
    }

    private fun calculateCost(solution: Map<Retailer, Warehouse>) =
        // Activation costs for activated warehouses (soft)
        solution.values.toSet().sumOf { it.activationCost } +
                // Transportation costs (soft)
                solution.entries.sumOf { (retailer, warehouse) -> warehouse.transportationCost[retailer]!! * retailer.demand } +
                // Warehouse capacity exceeded
                HARD_CONSTRAINT_MULTIPLIER * solution.values.toSet().map { warehouse ->
            if (warehouse.capacity < solution.entries.filter { it.value == warehouse }.sumOf { it.key.demand }) 1 else 0
        }.sum()

    private fun warehouseCapacityExceeded(solution: Map<Retailer, Warehouse>) = solution.values.toSet().any { warehouse ->
        warehouse.capacity < solution.entries.filter { it.value == warehouse }.sumOf { it.key.demand }
    }

    // Generate new solution returns new solution, by re-mapping Retailer to another Warehouse
    private fun step(): MutableMap<Retailer, Warehouse> {
        val newSolution = solution.toMutableMap()

        val (retailer, warehouse) = solution.entries.random()
        val randomWarehouse = model.warehouses.minus(warehouse).random()
        newSolution[retailer] = randomWarehouse

        return newSolution
    }

    // Optimize using Late Acceptance Hill Climbing
    fun solveLAHC(n: Int = 50, earlyStopping: Int = 10_000): Int {
        val costList = MutableList(n) { calculateCost(solution) }
        var k = 0
        var iterationsNotImproved = 0

        while (true) {
            k %= n

            val cost = calculateCost(solution)
            val newSolution = step()
            val newCost = calculateCost(newSolution)

            if (newCost - bestSolutionCost < 0) {
                bestSolution = newSolution
                bestSolutionCost = newCost
                println("Found better solution after ${iterationsNotImproved + 1} steps, Best Cost: $bestSolutionCost")
                iterationsNotImproved = 0
            } else {
                iterationsNotImproved += 1
            }

            if ((newCost - cost < 0) or (newCost < costList[k])) {
                solution = newSolution
            }

            costList[k] = newCost
            k += 1

            // Stopping criterion
            if (iterationsNotImproved >= earlyStopping) {
                println("Optimization stopped")
                break
            }
        }

        println("Warehouse capacity exceeded: ${warehouseCapacityExceeded(bestSolution)}")
        println("Cost: ${calculateCost(bestSolution)}")
        println("Warehouses used ${bestSolution.values.map { it.name }.toSet()} ")
        println("(Retailer:Warehouse) mapping: ")

        bestSolution.entries.forEach {
            println("(${it.key.name} [${it.key.demand}] -${it.value.transportationCost[it.key]}-> ${it.value.name} [${it.value.capacity}])")
        }

        return bestSolutionCost
    }
}