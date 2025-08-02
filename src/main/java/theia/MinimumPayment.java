package theia;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MinimumPayment {

  private static final String FIXED = "1";
  private static final String SUBTRACT = "2";
  private static final String PERCENTAGE = "3";

  record Product(BigDecimal basePrice, List<String> tags) {
    public Product(List<String> productStr) {
      this(new BigDecimal(productStr.get(0)),
          List.copyOf(productStr.subList(1, productStr.size())));
    }
  }

  record Discount(String tag, String type, BigDecimal amount) {
    public Discount(List<String> discountStr) {
      this(discountStr.get(0), discountStr.get(1), new BigDecimal(discountStr.get(2)));
    }
  }

  public static int calculate(List<List<String>> prices, List<List<String>> discounts) {
    List<Product> products = parse(prices);
    Map<String, List<Discount>> discountsPerTag = getDiscountsMap(discounts);

    BigDecimal total = products.stream()
        .map(product -> getAllPrices(product, discountsPerTag))
        .map(pricesPerProduct -> Collections.min(pricesPerProduct, Comparator.naturalOrder()))
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    return total.setScale(0, RoundingMode.DOWN).intValue();
  }

  private static List<Product> parse(List<List<String>> repr) {
    return repr.stream()
        .map(Product::new)
        .toList();
  }

  private static Map<String, List<Discount>> getDiscountsMap(List<List<String>> repr) {
    return repr.stream()
        .map(Discount::new)
        .collect(Collectors.groupingBy(Discount::tag));
  }

  private static List<BigDecimal> getAllPrices(Product product, Map<String, List<Discount>> discountsPerTag) {
    List<BigDecimal> prices = new ArrayList<>(product.tags().size() + 1);
    prices.add(product.basePrice());

    product.tags().stream()
        .map(discountsPerTag::get)
        .filter(Objects::nonNull)
        .flatMap(List::stream)
        .map(discount -> applyDiscount(product.basePrice(), discount))
        .forEach(prices::add);

    return prices;
  }

  private static BigDecimal applyDiscount(BigDecimal price, Discount discount) {
    return switch (discount.type()) {
      case FIXED -> discount.amount();
      case SUBTRACT -> BigDecimal.ZERO.max(price.subtract(discount.amount()));
      case PERCENTAGE ->
        price.subtract(price.multiply(discount.amount().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_DOWN)));
      default -> price;
    };
  }
}
