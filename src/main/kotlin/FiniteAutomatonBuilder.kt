import java.io.File

class FiniteAutomatonBuilder(path: String) {
    private val file = File(path)
    private val state = "S[0-9]+"
    private val symbol = "[0-9a-zA-Z]"

    fun build(): FiniteAutomaton {
        var states: Set<String>? = null
        var alphabet: Set<Char>? = null
        var initialState: String? = null
        var acceptStates: Set<String>? = null
        val transitions = emptySet<Transition>().toMutableSet()
        file.forEachLine { line ->
            readStates(line)?.let {
                if (states == null)
                    states = it
                else
                    throw FABuilderException("States defined twice")
            }

            readAlphabet(line)?.let {
                if (alphabet == null)
                    alphabet = it
                else
                    throw FABuilderException("Alphabet defined twice")
            }

            readInitialState(line, states)?.let {
                if (initialState == null)
                    initialState = it
                else
                    throw FABuilderException("Initial state defined twice")
            }

            readAcceptStates(line)?.let {
                if (acceptStates == null)
                    acceptStates = it
                else
                    throw FABuilderException("Accept states defined twice")
            }

            readTransition(line, states, alphabet)?.let {
                transitions.add(it)
            }
        }

        if (states == null)
            throw FABuilderException("No states defined")
        if (alphabet == null)
            throw FABuilderException("No alphabet defined")
        if (transitions.isEmpty())
            throw FABuilderException("No transitions defined")
        if (initialState == null)
            throw FABuilderException("No initial state defined")
        if (acceptStates == null)
            throw FABuilderException("No accept states defined")

        return FiniteAutomaton(
            states!!, alphabet!!, transitions, initialState!!, acceptStates!!
        )
    }



    private fun readStates(line: String): Set<String>? {
        return "Q=[{]($state)(?:,($state))*[}]".toRegex().matchEntire(line)?.groupValues?.drop(1)?.toSet()
    }

    private fun readAcceptStates(line: String): Set<String>? {
        return "F=[{]($state)(?:,($state))*[}]".toRegex().matchEntire(line)?.groupValues?.drop(1)?.toSet()
    }

    private fun readAlphabet(line: String): Set<Char>? {
        return "SIGMA=[{]($symbol)(?:,($symbol))*[}]".toRegex().matchEntire(line)?.groupValues?.map { it[0] }?.drop(1)?.toSet()
    }

    private fun readInitialState(line: String, states: Set<String>?): String? {
        val anyState = states?.reduce{ acc, state -> "$acc|$state" } ?: return null
        return "q0=($anyState)".toRegex().matchEntire(line)?.groupValues?.get(1)
    }

    private fun readTransition(line: String, states: Set<String>?, alphabet: Set<Char>?): Transition? {
        val anyState = states?.reduce{ acc, state -> "$acc|$state" } ?: return null
        val anySymbol = alphabet?.map{ it.toString() }?.reduce{ acc, symbol -> "$acc|$symbol" } ?: return null
        val transitionOutput = "($anySymbol)($anyState)"

        var inputSymbol = ""
        var symbol: Char? = null
        val transitions = emptySet<TransitionOutput>().toMutableSet()

        val matches = "($anyState)->$transitionOutput(?:\\|$transitionOutput)*".toRegex()
            .matchEntire(line)
            ?.groupValues
            ?.drop(1) ?: return null

        matches.forEachIndexed { index, value ->
                if (index == 0)
                    inputSymbol = value
                else if (index % 2 == 1)
                    symbol = value[0]
                else {
                    transitions.add(TransitionOutput(symbol!!, value))
                }
            }

        return Transition(inputSymbol, transitions)
    }
}
