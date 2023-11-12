package io.redspace.ironsspellbooks.compat;

import io.redspace.ironsspellbooks.compat.apotheosis.ApotheosisHandler;
import io.redspace.ironsspellbooks.compat.supplementaries.SupplementariesActualImpl;
import io.redspace.ironsspellbooks.compat.supplementaries.SupplementariesProxy;
import io.redspace.ironsspellbooks.compat.tetra.TetraActualImpl;
import io.redspace.ironsspellbooks.compat.tetra.TetraProxy;
import net.minecraftforge.fml.ModList;

import java.util.Map;

public class CompatHandler {
    private static final Map<String, Runnable> MOD_MAP = Map.of(
            "tetra", () -> TetraProxy.PROXY = new TetraActualImpl(),
            "supplementaries", () -> SupplementariesProxy.PROXY = new SupplementariesActualImpl(),
            "apotheosis", ApotheosisHandler::init
    );

    public static void init() {
        MOD_MAP.forEach((modid, supplier) -> {
            if (ModList.get().isLoaded(modid)) {
                supplier.run();
            }
        });
    }
}
