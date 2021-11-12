https://github.com/VicSera/FiniteAutomataLFTC
# Finite Automata
## Formal Languages and Compiler Design
<img src="C:\programming\FiniteAutomataLFTC\FA.svg" alt="FA">

## `FA.in` structure (ISO/IEC 14977 standard EBNF):
 
    FA.in = "Q={", state, {", ", state}, "}\n",
            "SIGMA={", symbol, {", ", symbol}, "}\n",
            "q0=", state, "\n",
            "F={", state, {", ", state}, "}\n",
            {state, "->", transitionOutput, {"|", transitionOutput}};
    

    number = {"0" | ... | "9"};
    
    symbol = "0" | ... | "9" | "a" | ... | "z" | "A" | ... | "Z";
    
    state = "S", number;
    
    transitionOutput = symbol, state;

