
package com.oms.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;

public class InventoryId implements Serializable {

    @Column(name = "SKU_ID")
    private String skuId;

    @Column(name = "STORE_ID")
    private String storeId;

    public InventoryId() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryId that = (InventoryId) o;
        return Objects.equals(skuId, that.skuId) &&
               Objects.equals(storeId, that.storeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skuId, storeId);
    }
}