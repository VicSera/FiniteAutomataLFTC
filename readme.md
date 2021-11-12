# Finite Automata
## Formal Languages and Compiler Design

## `FA.in` structure (ISO/IEC 14977 standard EBNF):
 
    FA.in = "Q={", state, {", ", state}, "}"
            "SIGMA={", symbol, {", ", symbol}, "}"
            "q0=", state
            "F={", state, {", ", state}, "}"
            {state, "->", transitionOutput, {"|", transitionOutput}}
    

    number = {"0" | ... | "9"}
    
    symbol = "0" | ... | "9" | "a" | ... | "z" | "A" | ... | "Z"
    
    state = "S", number
    
    transitionOutput = symbol, state
