class Solver(
    private val model: Model,
) {
    private var solution: Map<Warehouse, MutableList<Retailer>> = model.warehouses.associateWith { mutableListOf() }
    private var bestSolution = solution

    companion object {
        private const val HARD_CONS_MULT = 100_000
    }

    private fun calculateCost() =
        // Activation costs for activated warehouses (soft)
        model.warehouses.sumOf {
            if (solution[it]!!.any()) it.activationCost else 0

            // Transportation costs (soft)
        } + model.warehouses.sumOf { warehouse ->
            solution[warehouse]!!.sumOf { warehouse.transportationCost[it]!! * it.demand }

            // Warehouse capacity exceeded
        } + HARD_CONS_MULT * model.warehouses.map { warehouse -> if (warehouse.capacity < solution[warehouse]!!.sumOf { it.demand }) 1 else 0 }
            .sum()

    private fun hardConstraintsFailed() =
        model.warehouses.any { warehouse -> warehouse.capacity < solution[warehouse]!!.sumOf { it.demand } }


    // Randomly assign retailers to warehouses
    private fun initializeSolution() {
        model.retailers.forEach {
            val warehouse = model.warehouses.random()
            solution[warehouse]!!.add(it)
        }
    }

    private fun step() {
    }


    fun solve() {
        initializeSolution()



        println("Warehouse capacity exceeded: ${hardConstraintsFailed()}")
        println("Cost: ${calculateCost()}")
        println("Warehouses activated ${model.warehouses.filter { bestSolution[it]!!.any() }.map { it.name }}")
        println("(Retailer:Warehouse) mapping: ")
        model.retailers.forEach { retailer ->
            val warehouse = model.warehouses.find { bestSolution[it]!!.contains(retailer) }!!
            println("(${retailer.name}: ${warehouse.name})")
        }
    }
}