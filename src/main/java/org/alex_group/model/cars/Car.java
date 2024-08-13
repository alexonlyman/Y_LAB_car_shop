package org.alex_group.model.cars;

import java.util.Objects;
/**
 * The Car class represents a car with various attributes.
 */
public class Car {
    private Integer id;
    private String markName;
    private String modelName;
    private Integer productionYear;
    private Integer price;
    private String productionCountry;
    private String colour;
    private Integer count;

    /**
     * Constructs a new Car object with the specified parameters.
     *
     * @param markName          the make of the car
     * @param modelName         the model of the car
     * @param productionYear    the production year of the car
     * @param price             the price of the car
     * @param productionCountry the production country of the car
     * @param colour            the colour of the car
     * @param count             the count of the car
     */
    public Car(String markName, String modelName, Integer productionYear, Integer price,
               String productionCountry, String colour,Integer count) {
        this.markName = markName;
        this.modelName = modelName;
        this.productionYear = productionYear;
        this.price = price;
        this.productionCountry = productionCountry;
        this.colour = colour;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public Car setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getMarkName() {
        return markName;
    }

    public Car setMarkName(String markName) {
        this.markName = markName;
        return this;
    }

    public String getModelName() {
        return modelName;
    }

    public Car setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public Car setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public Car setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public String getProductionCountry() {
        return productionCountry;
    }

    public Car setProductionCountry(String productionCountry) {
        this.productionCountry = productionCountry;
        return this;
    }

    public String getColour() {
        return colour;
    }

    public Car setColour(String colour) {
        this.colour = colour;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id) && Objects.equals(markName, car.markName) && Objects.equals(modelName, car.modelName) && Objects.equals(productionYear, car.productionYear) && Objects.equals(price, car.price) && Objects.equals(productionCountry, car.productionCountry) && Objects.equals(colour, car.colour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, markName, modelName, productionYear, price, productionCountry, colour);
    }

    public Integer getCount() {
        return count;
    }

    public Car setCount(Integer count) {
        this.count = count;
        return this;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", markName='" + markName + '\'' +
                ", modelName='" + modelName + '\'' +
                ", productionYear=" + productionYear +
                ", price=" + price +
                ", productionCountry='" + productionCountry + '\'' +
                ", colour='" + colour + '\'' +
                ", count=" + count +
                '}';
    }
}
