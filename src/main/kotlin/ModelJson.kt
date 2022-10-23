data class ModelJson(
    val retailers: List<Int>,
    val potential_warehouse_sites: List<WarehouseJson>,
    val transportation_cost_matrix: List<List<Int>>
)

data class WarehouseJson(val capacity: Int, val cost: Int)
