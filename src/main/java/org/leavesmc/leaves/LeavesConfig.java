package org.leavesmc.leaves;

import net.minecraft.server.MinecraftServer;

public final class LeavesConfig {
    public static ProtocolConfig protocol = new ProtocolConfig();

    public static class ProtocolConfig {
        public BladerenConfig bladeren = new BladerenConfig();

        public static class BladerenConfig {
            public boolean enable = true;
            public boolean msptSyncProtocol = true;
            public int msptSyncTickInterval = 20;
        }

        public SyncmaticaConfig syncmatica = new SyncmaticaConfig();

        public static class SyncmaticaConfig {
            public boolean enable = true;
            public boolean useQuota = true;
            public int quotaLimit = 40000000;
        }

        public PCAConfig pca = new PCAConfig();

        public static class PCAConfig {
            public boolean enable = true;
            public PcaPlayerEntityType syncPlayerEntity = PcaPlayerEntityType.OPS;

            public enum PcaPlayerEntityType {
                NOBODY, BOT, OPS, OPS_AND_SELF, EVERYONE
            }
        }

        public AppleSkinConfig appleskin = new AppleSkinConfig();

        public static class AppleSkinConfig {
            public boolean enable = true;
            public int syncTickInterval = 20;
        }

        public ServuxConfig servux = new ServuxConfig();

        public static class ServuxConfig {
            public boolean structureProtocol = true;
            public boolean entityProtocol = true;
            public boolean hudMetadataProtocol = true;
            public boolean hudMetadataShareSeed = true;
            public boolean litematicsProtocol = true;
        }

        public boolean bborProtocol = true;
        public boolean jadeProtocol = true;
        public AlternativePlaceType alternativeBlockPlacement = AlternativePlaceType.LITEMATICA;

        public enum AlternativePlaceType {
            NONE, CARPET, CARPET_FIX, LITEMATICA
        }

        public boolean xaeroMapProtocol = true;
        public int xaeroMapServerID = MinecraftServer.getServer().getMotd().hashCode();
        public boolean leavesCarpetSupport = true;
        public boolean lmsPasterProtocol = true;
        public boolean reiServerProtocol = true;
        public boolean chatImageProtocol = true;

    }
}
