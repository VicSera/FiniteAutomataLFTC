class FiniteAutomaton(
    val states: Set<String>,
    val alphabet: Set<Char>,
    val transitions: Set<Transition>,
    val initialState: String,
    val acceptStates: Set<String>
) {
    private val transitionFunction = transitionsToFunction(transitions)

    fun accepts(sequence: String): Boolean {
        if (!isDeterministic())
            throw FAException("This FA is not deterministic!")

        var currentState = initialState
        sequence.forEach { symbol ->
            val transitions = transitionFunction(currentState)
            transitions.find { it.symbol == symbol }?.let { currentState = it.state }
                ?: return false
        }

        return currentState in acceptStates
    }

    private fun isDeterministic(): Boolean {
        transitions.forEach { transition ->
            val possibleSymbols = transition.outputs.map { it.symbol }
            if (possibleSymbols.size != possibleSymbols.toSet().size)
                return false
        }
        return true
    }

    private fun transitionsToFunction(transitions: Set<Transition>): TransitionFunction {
        val map = transitions.associate { transition -> transition.inputSymbol to transition.outputs }

        return fun(symbol: String ): Set<TransitionOutput> {
            return map[symbol]?: emptySet()
        }
    }
}
