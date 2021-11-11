class Menu(val fa: FiniteAutomaton) {
    fun display() {
       while (true)
           menuLoop()
    }

    private fun menuLoop() {
        println("""
            1. Display states
            2. Display alphabet
            3. Display initial state
            4. Display accept states
            5. Display transitions
            6. Check whether sequence is accepted by FA
        """.trimIndent())

        when(readLine()!!.toInt()) {
            1 -> println("States: ${fa.states.reduce{acc, state -> "$acc, $state"}}")
            2 -> println("Alphabet: ${fa.alphabet.map{ it.toString() }.reduce{acc, symbol -> "$acc, $symbol"}}")
            3 -> println("Initial state: ${fa.initialState}")
            4 -> println("Accept states: ${fa.acceptStates.reduce{ acc, state -> "$acc, $state"}}")
            5 -> printTransitions()
            6 -> checkSequence()
        }
    }

    private fun checkSequence() {
        val sequence = readLine()!!

        try {
            if (fa.accepts(sequence))
                println("Sequence is accepted!")
            else
                println("Sequence is not accepted")
        } catch(e: FAException) {
            println(e.message)
        }

    }

    private fun printTransitions() {
        println("Transitions:")
        fa.transitions.forEach { transition ->
            println("${transition.inputSymbol} -> ${transition.outputs
                .map { out -> "${out.symbol}${out.state}" }
                .reduce { acc, out -> "$acc|$out"}}")
        }
    }
}
