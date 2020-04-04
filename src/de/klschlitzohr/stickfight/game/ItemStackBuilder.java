package de.klschlitzohr.stickfight.game;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackBuilder {

    private ItemMeta itemMeta;
    private ItemStack itemStack;

    public ItemStackBuilder(Material material, int amount) {
        itemStack = new ItemStack(material,2);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemStackBuilder addEnchantment(Enchantment enchantment, int strengst, boolean visible) {
        itemMeta.addEnchant(enchantment,strengst,visible);
        return this;
    }

    public ItemStackBuilder setDisplayName(String name) {
        itemMeta.setDisplayName(name.replace("§", "\u00a7"));
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
