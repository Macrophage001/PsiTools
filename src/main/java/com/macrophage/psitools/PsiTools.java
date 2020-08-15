package com.macrophage.psitools;

import com.macrophage.psitools.common.handler.BlockEvents;
import com.macrophage.psitools.common.handler.CraftingEvents;
import com.macrophage.psitools.common.init.ModItems;
import com.macrophage.psitools.common.item.curio.ItemPsiCurio;
import com.macrophage.psitools.common.spell.base.PsiToolsSpellPieces;
import com.macrophage.psitools.util.ModInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosAPI;
import top.theillusivec4.curios.api.capability.CuriosCapability;
import top.theillusivec4.curios.api.capability.ICurio;
import top.theillusivec4.curios.api.imc.CurioIMCMessage;
import vazkii.psi.common.core.PsiCreativeTab;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod("psitools")
public class PsiTools
{
    private static final Logger LOGGER = LogManager.getLogger();

    public PsiTools() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        ModItems.init();
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new CraftingEvents());
        MinecraftForge.EVENT_BUS.register(new BlockEvents());
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.debug("Registering PsiTools spell pieces...");
        PsiToolsSpellPieces.init();
        LOGGER.debug("Registration complete!");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        InterModComms.sendTo(CuriosAPI.MODID, CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("ring").setSize(2));
        InterModComms.sendTo(CuriosAPI.MODID, CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("belt"));
        InterModComms.sendTo(CuriosAPI.MODID, CuriosAPI.IMC.REGISTER_TYPE, () -> new CurioIMCMessage("necklace"));
    }

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<ItemStack> evt) {
        ItemStack stack = evt.getObject();

        if (!(stack.getItem() instanceof ItemPsiCurio)) {
            return;
        }
        ItemPsiCurio psiCurio = new ItemPsiCurio(stack);
        evt.addCapability(CuriosCapability.ID_ITEM, new ICapabilityProvider() {
            LazyOptional<ICurio> curio = LazyOptional.of(() -> psiCurio);

            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap,
                                                     @Nullable Direction side) {

                return CuriosCapability.ITEM.orEmpty(cap, curio);
            }
        });
    }


    private void processIMC(final InterModProcessEvent event)
    {

    }

    public static ResourceLocation location(String _path)
    {
        return new ResourceLocation(ModInfo.MODID, _path);
    }
}
