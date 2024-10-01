package Exercise1.Structural;

// Component Interface
interface Pizza {
    String getDescription();
    double getCost();
}

// Concrete Component
class PlainPizza implements Pizza {
    @Override
    public String getDescription() {
        return "Plain Pizza";
    }

    @Override
    public double getCost() {
        return 5.00;
    }
}

// Decorator
abstract class PizzaDecorator implements Pizza {
    protected Pizza pizza;

    public PizzaDecorator(Pizza pizza) {
        this.pizza = pizza;
    }

    @Override
    public String getDescription() {
        return pizza.getDescription();
    }

    @Override
    public double getCost() {
        return pizza.getCost();
    }
}

// Concrete Decorators
class Cheese extends PizzaDecorator {
    public Cheese(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + ", Cheese";
    }

    @Override
    public double getCost() {
        return pizza.getCost() + 1.00;
    }
}

class Pepperoni extends PizzaDecorator {
    public Pepperoni(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + ", Pepperoni";
    }

    @Override
    public double getCost() {
        return pizza.getCost() + 1.50;
    }
}

// Main Application
public class DecoratorPatternDemo {
    public static void main(String[] args) {
        Pizza pizza = new PlainPizza();
        System.out.println(pizza.getDescription() + " Cost: $" + pizza.getCost());

        Pizza cheesePizza = new Cheese(pizza);
        System.out.println(cheesePizza.getDescription() + " Cost: $" + cheesePizza.getCost());

        Pizza fullyLoadedPizza = new Pepperoni(new Cheese(pizza));
        System.out.println(fullyLoadedPizza.getDescription() + " Cost: $" + fullyLoadedPizza.getCost());
    }
}
