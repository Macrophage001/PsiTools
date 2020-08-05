package com.macrophage.psitools.common.init;

import com.macrophage.psitools.common.item.ItemPsiCore;
import com.macrophage.psitools.common.item.curio.ItemBaubleController;
import com.macrophage.psitools.common.item.curio.ItemPsigemAmulet;
import com.macrophage.psitools.common.item.curio.ItemPsigemBelt;
import com.macrophage.psitools.common.item.curio.ItemPsigemRing;
import com.macrophage.psitools.util.ModInfo;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, ModInfo.MODID);

    public static final RegistryObject<Item> bauble_controller = ITEMS.register("bauble_controller", ItemBaubleController::new);

    public static final RegistryObject<Item> psi_core = ITEMS.register("psi_core", ItemPsiCore::new);
    public static final RegistryObject<Item> psigem_ring = ITEMS.register("psigem_ring", ItemPsigemRing::new);
    public static final RegistryObject<Item> psigem_belt = ITEMS.register("psigem_belt", ItemPsigemBelt::new);
    public static final RegistryObject<Item> psigem_amulet = ITEMS.register("psigem_amulet", ItemPsigemAmulet::new);

    public static void init()
    {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
