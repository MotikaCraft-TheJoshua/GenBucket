package com.reliableplugins.genbucket.manager;

import com.reliableplugins.genbucket.GenBucket;
import com.reliableplugins.genbucket.hook.BuildCheckHook;
import com.reliableplugins.genbucket.hook.PluginHook;
import com.reliableplugins.genbucket.hook.VaultHook;
import com.reliableplugins.genbucket.hook.combat.CombatLogXHook;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class HookManager {

    private GenBucket plugin;
    private Map<String, PluginHook> pluginMap = new HashMap<>();

    public HookManager(GenBucket plugin) {
        this.plugin = plugin;
        hookPlugin(new BuildCheckHook());
        hookPlugin(new VaultHook());
        hookPlugin(new CombatLogXHook());
    }

    private void hookPlugin(PluginHook pluginHook) {
        for (String name : pluginHook.getPlugins()) {
            if (plugin.getServer().getPluginManager().getPlugin(name) == null) continue;
            plugin.getLogger().log(Level.INFO, String.format("Successfully hooked into %s", name));
            pluginMap.put(pluginHook.getName().toLowerCase(), (PluginHook<?>) pluginHook.setup(plugin));
        }
    }

    public BuildCheckHook getBuildChecks() {
        return (BuildCheckHook) pluginMap.get("buildcheck");
    }

    public VaultHook getVault() {
        return (VaultHook) pluginMap.get("vault");
    }

    public CombatLogXHook getCombat() { return (CombatLogXHook) pluginMap.get("combat"); }

}
