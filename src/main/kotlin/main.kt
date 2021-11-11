fun main() {
    val builder = FiniteAutomatonBuilder("FA.in")
    val fa = builder.build()
    val menu = Menu(fa)

    menu.display()
}
