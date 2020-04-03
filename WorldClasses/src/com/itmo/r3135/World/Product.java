package com.itmo.r3135.World;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Класс com.itmo.com.itmo.r3135.World.Product.
 */
public class Product implements Comparable<Product>, Serializable {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Double price; //Поле может быть null, Значение поля должно быть больше 0
    private String partNumber; //Длина строки должна быть не меньше 21, Поле не может быть null
    private Float manufactureCost; //Поле не может быть null
    private UnitOfMeasure unitOfMeasure; //Поле не может быть null
    private Person owner; //Поле не может быть null

    private static int idCounter;

    static {
        idCounter = 1;
    }

    {
        creationDate = LocalDateTime.now();
    }

    /**
     * Конструктор для класса com.itmo.com.itmo.r3135.World.Product
     *
     * @param name            - имя
     * @param coordinates     - класса координат
     * @param price           - цена
     * @param partNumber      - номер партии
     * @param manufactureCost - цена производства
     * @param unitOfMeasure   - единицы измерения продукта
     * @param owner           - владелец
     */
    public Product(String name, Coordinates coordinates, Double price, String partNumber, Float manufactureCost, UnitOfMeasure unitOfMeasure, Person owner) {
        this.id = idCounter;
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.partNumber = partNumber;
        this.manufactureCost = manufactureCost;
        this.unitOfMeasure = unitOfMeasure;
        this.owner = owner;
        idCounter++;
    }

    /**
     * Проверяет элемент на сообтетвтвовение требованиям коллекции
     */
    public boolean checkNull() {
        try {
            return name == null || name.isEmpty() || coordinates == null ||
                    coordinates.getX() == null || coordinates.getY() <= -50 ||
                    creationDate == null || price <= 0 ||
                    partNumber == null || partNumber.length() < 21 ||
                    manufactureCost == null || unitOfMeasure == null || owner == null ||
                    owner.getName() == null || owner.getName().isEmpty() ||
                    owner.getBirthday() == null || owner.getEyeColor() == null || owner.getHairColor() == null;
        } catch (Exception ex) {
            System.out.println("В процессе проверки объекта произошла ошибка");
            return true;
        }
    }
    /**
     * Устанавливает id орпеделенному элементу коллекции.
     *
     * @param id - id предмета
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Возвращает id элемента.
     *
     * @return id элемента коллекции.
     */
    public int getId() {
        return id;
    }

    /**
     * Воозвращает поле name элемента коллекции.
     *
     * @return - имя
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает дату создания.
     *
     * @param creationDate - дата создания.
     */
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Возвращает координаты в формате класса com.itmo.com.itmo.r3135.World.Coordinates.
     *
     * @return - класс com.itmo.com.itmo.r3135.World.Coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Возвращает поле price элемента коллекции.
     *
     * @return - цена.
     */
    public Double getPrice() {
        return price;
    }

    @Override
    public int compareTo(Product o) {
        return (int) ((this.getPrice() - o.getPrice()) * 100);
    }
    public static String printRequest() {
        return ("------------------------\n" +
                "Требования к элементу:\n" +
                "------------------------\n" +
                "Product: {\n" +
                "    String name --- Поле не может быть null, Строка не может быть пустой\n" +
                "    Coordinates coordinates; --- Поле не может быть null\n" +
                "    Long price --- Поле может быть null, Значение поля должно быть больше 0\n" +
                "    String partNumber --- Длина строки должна быть не меньше 21, Поле не может быть null\n" +
                "    manufactureCost --- Поле не может быть null;\n" +
                "    UnitOfMeasure unitOfMeasure --- Поле не может быть null\n" +
                "    Person owner --- Поле не может быть null\n" +
                "}\n" +
                "Coordinates: {\n" +
                "    Float x --- Максимальное значение поля: 82, Поле не может быть null\n" +
                "    Long y --- Значение поля должно быть больше -244, Поле не может быть null\n" +
                "}\n" +
                "Person: {\n" +
                "    String name --- Поле не может быть null, Строка не может быть пустой\n" +
                "    java.time.LocalDateTime birthday --- Поле не может быть null\n" +
                "    Color eyeColor --- Поле не может быть null\n" +
                "    Color hairColor --- Поле не может быть null\n" +
                "}\n" +
                "UnitOfMeasure: {\n" +
                "    PCS,\n" +
                "    LITERS,\n" +
                "    GRAMS,\n" +
                "    MILLIGRAMS;\n" +
                "}\n" +
                "Color: {\n" +
                "    GREEN,\n" +
                "    RED,\n" +
                "    BLACK,\n" +
                "    BLUE,\n" +
                "    YELLOW;\n}");
    }
    @Override
    public String toString() {
        return "------------------------------------------------------------------------\n" +
                "ID: " + id + "\n" +
                "Название: " + name + "\n" +
                "Координаты: " + "\n" +
                coordinates + "\n" +
                "Дата создания: " + creationDate + "\n" +
                "Цена: " + price + "\n" +
                "Номер партии: " + partNumber + "\n" +
                "Стоимость изготовления: " + manufactureCost + "\n" +
                "Единицы измерения: " + unitOfMeasure + "\n" +
                "Владелец: " + owner + "\n" +
                "------------------------------------------------------------------------\n";
    }
}
