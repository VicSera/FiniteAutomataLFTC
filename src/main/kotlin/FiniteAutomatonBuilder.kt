import java.io.File

class FiniteAutomatonBuilder(path: String) {
    private val file = File(path)
    private val state = "S[0-9]+"
    private val symbol = "[0-9a-zA-Z]+"

    fun build(): FiniteAutomaton {
        var states: Set<String>? = null
        var alphabet: Set<String>? = null
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

        return FiniteAutomaton(

        )
    }

    private fun readStates(line: String): Set<String>? {
        return "Q=[{]($state)(?:,($state))*[}]".toRegex().matchEntire(line)?.groupValues?.toSet()
    }

    private fun readAcceptStates(line: String): Set<String>? {
        return "F=[{]($state)(?:,($state))*[}]".toRegex().matchEntire(line)?.groupValues?.toSet()
    }

    private fun readAlphabet(line: String): Set<String>? {
        return "SIGMA=[{]($symbol)(?:,($symbol))*[}]".toRegex().matchEntire(line)?.groupValues?.toSet()
    }

    private fun readInitialState(line: String, states: Set<String>?): String? {
        val anyState = states?.reduce{ acc, state -> "$acc|$state" } ?: return null
        return "q0=($anyState)".toRegex().matchEntire(line)?.groupValues?.get(0)
    }

    private fun readTransition(line: String, states: Set<String>?, alphabet: Set<String>?): Transition? {
        val anyState = states?.reduce{ acc, state -> "$acc|$state" } ?: return null
        val anySymbol = alphabet?.reduce{ acc, symbol -> "$acc|$symbol" } ?: return null
        val transitionOutput = "($anyState)($anySymbol)"
        val sep = Regex.escape("|")

        var inputSymbol = ""
        var symbol = ""
        val transitions = emptySet<TransitionOutput>().toMutableSet()

        "($anyState)->$transitionOutput(?:$sep$transitionOutput)*".toRegex()
            .matchEntire(line)
            ?.groupValues
            ?.forEachIndexed { index, value ->
                if (index == 0)
                    inputSymbol = value
                else if (index % 2 == 1)
                    symbol = value
                else {
                    transitions.add(TransitionOutput(symbol, value))
                }
            } ?: return null

        return Transition(inputSymbol, transitions)
    }
}
