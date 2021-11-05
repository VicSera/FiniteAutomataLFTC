class FiniteAutomaton(
    val states: Set<String>,
    val alphabet: Set<String>,
    val transitionFunction: TransitionFunction,
    val initialState: String,
    val acceptedStates: Set<String>
) {

}
