class Solver(
    private val model: Model,
) {
    // Randomly initialize solution (Retailers to random Warehouse)
    private var solution: Map<Retailer, Warehouse> = model.retailers.associateWith { model.warehouses.random() }
    private var bestSolution: Map<Retailer, Warehouse> = solution


    companion object {
        private const val HARD_CONS_MULT = 100_000
    }

    private fun calculateCost() =
        // Activation costs for activated warehouses (soft)
        solution.values.toSet().sumOf { it.activationCost } +
                // Transportation costs (soft)
                solution.entries.sumOf { (retailer, warehouse) -> warehouse.transportationCost[retailer]!! * retailer.demand } +
                // Warehouse capacity exceeded
                HARD_CONS_MULT * solution.values.toSet().map { warehouse ->
            if (warehouse.capacity < solution.entries.filter { it.value == warehouse }.sumOf { it.key.demand }) 1 else 0
        }.sum()


    private fun hardConstraintsFailed() = solution.values.any { warehouse ->
        warehouse.capacity < solution.entries.filter { it.value == warehouse }.sumOf { it.key.demand }
    }


    private fun step() {

    }


    fun solve() {

        println("Warehouse capacity exceeded: ${hardConstraintsFailed()}")
        println("Cost: ${calculateCost()}")
        println("Warehouses activated ${bestSolution.values.map { it.name }.toSet()} ")
        println("(Retailer:Warehouse) mapping: ")
        bestSolution.entries.forEach {
            println("(${it.key.name}: ${it.value.name})")
        }
    }
}