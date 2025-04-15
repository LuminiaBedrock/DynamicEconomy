package com.mefrreex.dynamiceconomy.impl.controller.item;

import com.mefrreex.dynamiceconomy.api.controller.item.ItemController;
import com.mefrreex.dynamiceconomy.api.model.ItemData;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

public class BasicItemController implements ItemController {

    private final Function<String, ItemData> itemResolver;
    private final ItemData defaultData;

    public BasicItemController(
            @NotNull Function<String, ItemData> itemResolver,
            @NotNull ItemData defaultData
    ) {
        this.itemResolver = Objects.requireNonNull(itemResolver, "itemResolver must not be null");
        this.defaultData = Objects.requireNonNull(defaultData, "defaultData must not be null");
    }

    @Override
    public ItemData getItemData(@NotNull String id) {
        return Objects.requireNonNullElse(itemResolver.apply(id), defaultData);
    }
}
