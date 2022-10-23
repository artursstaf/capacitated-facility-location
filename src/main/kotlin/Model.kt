import com.beust.klaxon.Klaxon

data class Model(
    val retailers: List<Retailer>, val warehouses: List<Warehouse>
) {
    companion object {
        fun fromJsonString(json: String): Model {
            val parsedJson = Klaxon().parse<ModelJson>(json)!!

            val retailers = parsedJson.retailers.map { Retailer(demand = it) }

            val warehouses = parsedJson.potential_warehouse_sites.mapIndexed { idx, warehouse ->
                val transportationCosts = parsedJson.transportation_cost_matrix[idx].mapIndexed { retailerId, cost ->
                    retailers[retailerId] to cost
                }.toMap()

                Warehouse(warehouse.capacity, warehouse.cost, transportationCosts)
            }

            return Model(retailers, warehouses)
        }
    }
}

data class Retailer(val demand: Int, var warehouse: Warehouse? = null)

data class Warehouse(
    val capacity: Int,
    val activationCost: Int,
    val transportationCost: Map<Retailer, Int>,
    var activated: Boolean = false,
)
