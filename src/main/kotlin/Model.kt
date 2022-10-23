import com.beust.klaxon.Klaxon

data class Model(
    val retailers: Set<Retailer>, val warehouses: Set<Warehouse>
) {
    companion object {
        fun fromJsonString(json: String): Model {
            val parsedJson = Klaxon().parse<ModelJson>(json)!!

            val retailers = parsedJson.retailers.map { Retailer(it.name, it.demand) }

            val warehouses = parsedJson.potential_warehouse_sites.mapIndexed { idx, warehouse ->
                val transportationCosts = parsedJson.transportation_cost_matrix[idx].mapIndexed { retailerId, cost ->
                    retailers[retailerId] to cost
                }.toMap()

                Warehouse(warehouse.name, warehouse.capacity, warehouse.cost, transportationCosts)
            }

            return Model(retailers.toSet(), warehouses.toSet())
        }
    }
}

data class Retailer(val name: String, val demand: Int)

data class Warehouse(
    val name: String,
    val capacity: Int,
    val activationCost: Int,
    val transportationCost: Map<Retailer, Int>,
)
