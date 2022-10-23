data class ModelJson(
    val retailers: List<RetailerJson>,
    val potential_warehouse_sites: List<WarehouseJson>,
    val transportation_cost_matrix: List<List<Int>>
)

data class WarehouseJson(val name: String, val capacity: Int, val cost: Int)

data class RetailerJson(val name: String, val demand: Int)
