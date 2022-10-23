import java.io.File


fun main(args: Array<String>) {
    if (args.size != 1) {
        throw IllegalArgumentException("Provide one argument")
    }

    val inputJson = File(args.first()).readText()
    val model = Model.fromJsonString(inputJson)
    Solver(model).solve()
}