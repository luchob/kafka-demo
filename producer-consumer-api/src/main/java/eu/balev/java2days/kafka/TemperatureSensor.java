package eu.balev.java2days.kafka;

import java.util.Random;
import java.util.function.Supplier;

/**
 * A fake temperature sensor :-)
 */
public class TemperatureSensor implements Supplier<Double> {

  private Random random = new Random();

  @Override
  public Double get() {
    return 20 + random.nextDouble() * 5;
  }
}
