package com.tetris.modern.rl.sync;

import android.content.Context;
import androidx.work.*;
import com.tetris.modern.rl.auth.AuthManager;
import com.tetris.modern.rl.auth.SyncStrategy;
import com.tetris.modern.rl.data.dao.ScoreDao;
import com.tetris.modern.rl.data.entities.ScoreEntity;
import com.tetris.modern.rl.network.ApiService;
import com.tetris.modern.rl.network.models.*;
import dagger.hilt.android.qualifiers.ApplicationContext;
import kotlinx.coroutines.flow.StateFlow;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002\u00a8\u0006\u0003"}, d2 = {"toEntity", "Lcom/tetris/modern/rl/data/entities/ScoreEntity;", "Lcom/tetris/modern/rl/network/models/LeaderboardEntry;", "app_debug"})
public final class SyncManagerKt {
    
    private static final com.tetris.modern.rl.data.entities.ScoreEntity toEntity(com.tetris.modern.rl.network.models.LeaderboardEntry $this$toEntity) {
        return null;
    }
}