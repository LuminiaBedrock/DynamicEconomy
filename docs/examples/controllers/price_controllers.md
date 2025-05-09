# Price Controllers
Price controllers allows you to manage and customize the price dynamics of an item

___
[FixedPriceController](../../../src/main/java/com/mefrreex/dynamiceconomy/impl/controller/price/FixedPriceController.java) - A fixed price controller. The item will have a base price.) - A fixed price controller. The item will have a base price.   
___
[ScaledPriceController](../../../src/main/java/com/mefrreex/dynamiceconomy/impl/controller/price/ScaledPriceController.java) - A price controller that implements dynamic scaling based on item availability. Adjusts prices proportionally to the difference between current and initial stock levels
___
[DemandBoostPriceController](../../../src/main/java/com/mefrreex/dynamiceconomy/impl/controller/price/DemandBoostPriceController.java) - A price controller that applies demand-based price boosts. Implements threshold-based price increases when item availability drops below specified levels.
___
[ExponentialGrowthPriceController](../../../src/main/java/com/mefrreex/dynamiceconomy/impl/controller/price/ExponentialGrowthPriceController.java) - A price controller that implements exponential price growth. Prices increase exponentially based on item scarcity using the formula: basePrice × e^(growthRate × missingAmount)
___
[LinearDepreciationPriceController](../../../src/main/java/com/mefrreex/dynamiceconomy/impl/controller/price/LinearDepreciationPriceController.java) - A price controller that implements linear depreciation pricing. Prices decrease linearly as item availability increases, with a minimum price floor.
___
[ThresholdPriceController](../../../src/main/java/com/mefrreex/dynamiceconomy/impl/controller/price/ThresholdPriceController.java) - A price controller that applies multiplier factors based on quantity thresholds. Prices increase when item quantities exceed configured threshold levels.    

Example of usage ThresholdPriceController:
```java
PriceController controller = new ThresholdPriceController(Map.of(
        100.0, 2.0, // When stock ≥ 100, price doubles
        50.0, 1.5,  // When stock ≥ 50, price ×1.5
        10.0, 1.2   // When stock ≥ 10, price ×1.2
));
```
___

[CompositePriceController](../../../src/main/java/com/mefrreex/dynamiceconomy/impl/controller/price/CompositePriceController.java) - A composite price controller that chains multiple price calculation strategies. Applies each controller in sequence, using the previous result as input for the next.

Example of usage CompositePriceController:
```java
PriceController controller = new CompositePriceController(List.of(
        new ScaledPriceController(2),
        new DemandBoostPriceController(3, 1.5)
));
```

___
[DelegatingPriceController](../../../src/main/java/com/mefrreex/dynamiceconomy/impl/controller/price/DelegatingPriceController.java) - A delegating price controller that routes price calculations to specific controllers based on item ID, with fallback to a default controller when no specific controller is found.

Example of usage DelegatingPriceController:
```java
PriceController controller = new DelegatingPriceController(id -> switch (id) {
    case "bread" -> new ScaledPriceController(2);
    case "apple" -> new ScaledPriceController(0.3);
    case "milk" -> new ScaledPriceController(0.5);
    default -> null;
}, new FixedPriceController());
```