# Lab06 Notes

## 6.1 Local Analysis

O meu projeto não tem bugs nem vulnerabiblidades, obtendo A tanto em fiabilidade como segurança.  
Contudo, contém 19 *Code Smells*, e 1h18min de *technical debt*.
![Sonar Cube Euromillions Analysis](/imgs/euromillions.png)

| File                               | Issue      | Level | Problem description                                                                                                        | How to solve                                                                            |   |   |
|------------------------------------|------------|-------|----------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------|---|---|
| euromillions/EuromillionsDraw.java | Code Smell | Minor | The return type of method findMatchesFor should be an interface such as "List" rather than the implementation "ArrayList". | Make the function return a List rather than an ArrayList implementation.                |   |   |
| euromillions/Dip.java              | Code Smell | Major | For loop at line 68 is testing a variant stop condition.                                                                   | Refactor the code in order to not assign to the loop counter from within the loop body. | 
| euromillions/EuromillionsDraw.java | Code Smell | Minor | The constructor at line 42 doesn't need the type specification.                                                            | Replace the Dip type specification with the diamond operator <>.                        |   |

## 6.2 Technical Debt

Neste projeto, tenho 39min de *technical debt* e 21 *code smells*.  
*Technical debt* refere-se ao custo de manutenção de código associado a más escolhas em ambiente de desenvolvimento. Por exemplo, um desenvolvedor pode escolher não implementar um certo teste por ser muito demorado; no entanto, isto aumentará o *technical debt* associado ao projeto. Além disso, estas decisões podem mostrar-se onerosas no futuro.  
![Sonar Cube Cars Analysis](/imgs/cars.png)
