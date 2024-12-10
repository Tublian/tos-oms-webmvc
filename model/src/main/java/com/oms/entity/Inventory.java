
package com.oms.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@IdClass(InventoryId.class)
@Entity
@Table(name = "INVENTORY")
public class Inventory {

    @NotNull
    @Id
    @Column(name = "SKU_ID", nullable = false)
    private String skuId;

    @NotNull
    @Id
    @Column(name = "STORE_ID", nullable = false)
    private String storeId;

    @NotNull
    @Column(name = "QTY", nullable = false)
    private int quantity;

    public Inventory() {
    }

    public Inventory(String skuId, String storeId, int quantity) {
        this.skuId = skuId;
        this.storeId = storeId;
        this.quantity = quantity;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}