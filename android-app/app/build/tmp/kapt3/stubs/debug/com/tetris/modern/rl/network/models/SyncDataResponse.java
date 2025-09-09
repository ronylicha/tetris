package com.tetris.modern.rl.network.models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0010\b\u0002\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007\u0012\u0006\u0010\t\u001a\u00020\b\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00c6\u0003J\u0011\u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0007H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\bH\u00c6\u0003J9\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u0010\b\u0002\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00072\b\b\u0002\u0010\t\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u0018\u001a\u00020\u00032\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001J\t\u0010\u001c\u001a\u00020\bH\u00d6\u0001R\u001e\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\t\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001d"}, d2 = {"Lcom/tetris/modern/rl/network/models/SyncDataResponse;", "", "success", "", "serverData", "Lcom/tetris/modern/rl/network/models/LocalSyncData;", "conflicts", "", "", "lastSync", "(ZLcom/tetris/modern/rl/network/models/LocalSyncData;Ljava/util/List;Ljava/lang/String;)V", "getConflicts", "()Ljava/util/List;", "getLastSync", "()Ljava/lang/String;", "getServerData", "()Lcom/tetris/modern/rl/network/models/LocalSyncData;", "getSuccess", "()Z", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class SyncDataResponse {
    @com.google.gson.annotations.SerializedName(value = "success")
    private final boolean success = false;
    @com.google.gson.annotations.SerializedName(value = "server_data")
    @org.jetbrains.annotations.NotNull
    private final com.tetris.modern.rl.network.models.LocalSyncData serverData = null;
    @com.google.gson.annotations.SerializedName(value = "conflicts")
    @org.jetbrains.annotations.Nullable
    private final java.util.List<java.lang.String> conflicts = null;
    @com.google.gson.annotations.SerializedName(value = "last_sync")
    @org.jetbrains.annotations.NotNull
    private final java.lang.String lastSync = null;
    
    public SyncDataResponse(boolean success, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.models.LocalSyncData serverData, @org.jetbrains.annotations.Nullable
    java.util.List<java.lang.String> conflicts, @org.jetbrains.annotations.NotNull
    java.lang.String lastSync) {
        super();
    }
    
    public final boolean getSuccess() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.network.models.LocalSyncData getServerData() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<java.lang.String> getConflicts() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getLastSync() {
        return null;
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.network.models.LocalSyncData component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<java.lang.String> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.network.models.SyncDataResponse copy(boolean success, @org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.network.models.LocalSyncData serverData, @org.jetbrains.annotations.Nullable
    java.util.List<java.lang.String> conflicts, @org.jetbrains.annotations.NotNull
    java.lang.String lastSync) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
}