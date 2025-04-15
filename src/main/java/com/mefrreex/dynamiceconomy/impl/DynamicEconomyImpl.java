package com.mefrreex.dynamiceconomy.impl;

import com.mefrreex.dynamiceconomy.api.DynamicEconomy;
import com.mefrreex.dynamiceconomy.api.controller.item.ItemController;
import com.mefrreex.dynamiceconomy.api.controller.price.PriceController;
import com.mefrreex.dynamiceconomy.api.model.ItemData;
import com.mefrreex.dynamiceconomy.exception.DynamicEconomyException;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Builder
public class DynamicEconomyImpl implements DynamicEconomy {

    private final Map<String, Integer> itemCounts = new HashMap<>();
    private final ItemController itemController;
    private final PriceController priceController;

    public DynamicEconomyImpl(@NotNull ItemController itemController, @NotNull PriceController priceController) {
        this.itemController = Objects.requireNonNull(itemController, "itemController must not be null");
        this.priceController = Objects.requireNonNull(priceController, "priceController must not be null");
    }

    @Override
    public double getCurrentPrice(String id) {
        ItemData data = itemController.getItemData(id);
        if (data == null) {
            throw new DynamicEconomyException("Item data not found for id: " + id);
        }
        int currentCount = itemCounts.getOrDefault(id, data.initialAmount());
        return priceController.calculatePrice(id, currentCount, data);
    }

    @Override
    public void increaseItemCount(String id, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count must be non-negative");
        }
        initializeItemCountIfAbsent(id);
        itemCounts.merge(id, count, Integer::sum);
    }

    @Override
    public void decreaseItemCount(String id, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count must be non-negative");
        }
        initializeItemCountIfAbsent(id);

        int current = itemCounts.getOrDefault(id, 0);
        int updated = Math.max(0, current - count);
        itemCounts.put(id, updated);
    }

    @Override
    public void setItemCount(String id, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Item count cannot be negative");
        }
        initializeItemCountIfAbsent(id);
        itemCounts.put(id, count);
    }

    private void initializeItemCountIfAbsent(String id) {
        if (!itemCounts.containsKey(id)) {
            ItemData data = itemController.getItemData(id);
            if (data == null) {
                throw new DynamicEconomyException("Item data not found for id: " + id);
            }
            itemCounts.put(id, data.initialAmount());
        }
    }
}
