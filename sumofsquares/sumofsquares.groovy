// cerner_2^5_2019
// Groovy example of map, filter, reduce
//

def numbers = [1, 2, 3, 4, 5, 6, 7]

def isEven = { it % 2 == 0 }
def square = { it * it}
def add = { num1, num2 -> num1 + num2 }

def sum = numbers
        .findAll(isEven)
        .collect(square)
        .sum(0);
        
println sum