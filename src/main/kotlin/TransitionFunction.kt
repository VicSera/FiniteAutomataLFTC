typealias TransitionOutput = Pair<Char, String>
typealias Transition = Pair<String, Set<TransitionOutput>>
typealias TransitionFunction = (String) -> Set<TransitionOutput>

val TransitionOutput.symbol get() = this.first
val TransitionOutput.state get() = this.second

val Transition.inputSymbol get() = this.first
val Transition.outputs get() = this.second
