package com.macrophage.psitools.util;

public class ModInfo {
    public static final String MODID = "psitools";
    public static final String NAME  = "PsiTools";
    public static final String VERSION = "0.3.4";
    public static final String CLIENT_SIDE = "com.macrophage.psitools.client.proxy.ClientProxy";
    public static final String SERVER_SIDE = "com.macrophage.psitools.common.proxy.CommonProxy";
    public static final String BAUBLE_CONTROLLER = "bauble_controller";

    public static class ITEMS {
        public static class MATERIALS {
            public static final String PLATE = "psimetal_plate";
            public static final String SIMPLE_BATTERY = "simple_battery";
            public static final String BATTERY = "psimetal_battery";
            public static final String PSI_CORE = "psi_core";
        }
        public static class BAUBLES {
            public static final String RING = "psigem_ring";
            public static final String BELT = "psigem_belt";
            public static final String AMULET = "psigem_amulet";
        }
    }

    public static class BLOCKS {
        public static class MATERIALS {
            public static final String CASING = "psimetal_casing";
        }
    }
}
