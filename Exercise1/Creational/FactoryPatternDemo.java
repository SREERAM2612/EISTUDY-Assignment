package Exercise1.Creational;

// Shape Interface
interface Shape {
    void draw();
}

// Concrete Shape Classes
class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a Circle");
    }
}

class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Drawing a Rectangle");
    }
}

// Factory Class
class ShapeFactory {
    public static Shape createShape(String shapeType) {
        if (shapeType.equalsIgnoreCase("CIRCLE")) {
            return new Circle();
        } else if (shapeType.equalsIgnoreCase("RECTANGLE")) {
            return new Rectangle();
        }
        return null;
    }
}

// Main Application
public class FactoryPatternDemo {
    public static void main(String[] args) {
        Shape shape1 = ShapeFactory.createShape("CIRCLE");
        shape1.draw();

        Shape shape2 = ShapeFactory.createShape("RECTANGLE");
        shape2.draw();
    }
}

