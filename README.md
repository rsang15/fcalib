# [FCAlib](https://julianmendez.github.io/fcalib/)

*Library for Formal Concept Analysis*

## Overview

**FCAlib** is an open-source, extensible library for Formal Concept Analysis (FCA) tool developers. It provides basic functionalities that are needed for building an FCA tool. It supports incomplete contexts and includes efficient implementations of basic FCA algorithms like implicational closure, next-closed set, etc. It implements the attribute exploration algorithm in such a way that it can be used together with a custom implemented expert. 

FCAlib is extended by [OntoComPlib](https://julianmendez.github.io/ontocomplib/) for using attribute exploration together with OWL ontologies. The following code segment shows how to create a formal context, add attributes to it, create an expert for this context, and start attribute exploration:

```java
  // Create a formal context whose attributes are of type String, and whose objects have
  // identifiers of type String
  FormalContext<String,String> context = new FormalContext<String,String>();
  
  // Create an expert for this context
  MyExpertClass<String> expert = new MyExpertClass<String>(context);
        
  // Add attributes to this context
  context.addAttribute("a");
  context.addAttribute("b");
  context.addAttribute("c");
        
  // Set expert for this context
  context.setExpert(expert);
  // Context listens to the actions of the expert
  expert.addExpertActionListener(context);
        
  // Create an expert action for starting attribute exploration           
  StartExplorationAction<String,String,FullObject<String,String>> action = 
          new StartExplorationAction<String,String,FullObject<String,String>>();
  action.setContext(context);
  // Fire the action, exploration starts...
  expert.fireExpertAction(action);
```

The following code segment shows how to create a set of implications for the
above context, add implications to it, and compute next-closure:

```java
  // Create a set of implications for the above context. Attributes are of type String
  ImplicationSet<String> = new ImplicationSet<String>(context);
          
  // Create a new implication with empty premise and conclusion
  Implication<String> imp = new Implication<String>();
          
  // Add attribute "a" to the premise
  imp.getPremise().add("a");
          
  // Add attribute "b" to the conclusion
  imp.getConclusion().add("b");
          
  // Add this implication to the implication set
  implications.add(imp);
          
  // Compute the next-closed set after mySet, and update mySet
  mySet = implications.nextClosure(mySet);
```

## License

[GNU General Public License version 3.0](https://www.gnu.org/licenses/gpl-3.0.txt)


## Developers

Original Developer: [Barış Sertkaya](https://www.frankfurt-university.de/~sertkaya/)

Additional Developers: [Julian Mendez](https://julianmendez.github.io), [Francesco Kriegel](https://github.com/francesco-kriegel), [Daniel Borchmann](https://github.com/exot)


