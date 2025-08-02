package theia;

import java.util.Collections;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

public class MinimumPaymentTest extends TestCase {

  public void testNoDiscount() {

    List<List<String>> prices = List.of(
        List.of("10"),
        List.of("20"));

    List<List<String>> discounts = Collections.emptyList();
    int minimumPayment = MinimumPayment.calculate(prices, discounts);
    Assert.assertEquals("Minimum payment doesn't match", 30, minimumPayment);
  }

  public void testFixedPriceDiscount() {
    List<List<String>> prices = List.of(
        List.of("20", "SUMMER", "BFRIDAY"),
        List.of("10", "SUMMER"));

    List<List<String>> discounts = List.of(
        List.of("SUMMER", "1", "15"),
        List.of("BFRIDAY", "1", "5"));

    int minimumPayment = MinimumPayment.calculate(prices, discounts);
    Assert.assertEquals(15, minimumPayment);
  }

  public void testFixedPriceSubstractDiscount() {
    List<List<String>> prices = List.of(
        List.of("20", "SUMMER", "BFRIDAY"),
        List.of("10", "BFRIDAY"));

    List<List<String>> discounts = List.of(
        List.of("SUMMER", "2", "15"),
        List.of("BFRIDAY", "2", "5"));

    int minimumPayment = MinimumPayment.calculate(prices, discounts);
    Assert.assertEquals(10, minimumPayment);
  }

  public void testPercentagePriceDiscount() {

    List<List<String>> prices = List.of(
        List.of("20", "SUMMER", "BFRIDAY"),
        List.of("10", "BFRIDAY"));

    List<List<String>> discounts = List.of(
        List.of("SUMMER", "3", "10"),
        List.of("BFRIDAY", "3", "90"));

    int minimumPayment = MinimumPayment.calculate(prices, discounts);
    Assert.assertEquals(3, minimumPayment);
  }

  public void testMultipleValuesForSameTag() {
    List<List<String>> prices = List.of(
        List.of("20", "SUMMER", "BFRIDAY"),
        List.of("10", "SUMMER"));

    List<List<String>> discounts = List.of(
        List.of("SUMMER", "1", "15"),
        List.of("SUMMER", "1", "5"),
        List.of("BFRIDAY", "1", "5"),
        List.of("BFRIDAY", "2", "20"));

    int minimumPayment = MinimumPayment.calculate(prices, discounts);
    Assert.assertEquals(5, minimumPayment);
  }
}
