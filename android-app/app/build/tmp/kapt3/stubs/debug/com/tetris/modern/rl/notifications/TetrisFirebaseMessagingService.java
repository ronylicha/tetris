package com.tetris.modern.rl.notifications;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;
import javax.inject.Inject;
import kotlinx.coroutines.Dispatchers;
import android.content.Context;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\t\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\fH\u0002J\u001c\u0010\u000e\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\fH\u0002J\u001c\u0010\u000f\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\fH\u0002J\u0010\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u001c\u0010\u0013\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\fH\u0002J\u001c\u0010\u0014\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\fH\u0002J\u0010\u0010\u0015\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\rH\u0016J\u0010\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\rH\u0002R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\u0019"}, d2 = {"Lcom/tetris/modern/rl/notifications/TetrisFirebaseMessagingService;", "Lcom/google/firebase/messaging/FirebaseMessagingService;", "()V", "notificationManager", "Lcom/tetris/modern/rl/notifications/TetrisNotificationManager;", "getNotificationManager", "()Lcom/tetris/modern/rl/notifications/TetrisNotificationManager;", "setNotificationManager", "(Lcom/tetris/modern/rl/notifications/TetrisNotificationManager;)V", "handleAchievementNotification", "", "data", "", "", "handleBeatenByFriendNotification", "handleDailyChallengeNotification", "handleGenericNotification", "message", "Lcom/google/firebase/messaging/RemoteMessage;", "handleNewRecordNotification", "handleTournamentNotification", "onMessageReceived", "onNewToken", "token", "sendTokenToServer", "app_debug"})
public final class TetrisFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @javax.inject.Inject
    public com.tetris.modern.rl.notifications.TetrisNotificationManager notificationManager;
    
    public TetrisFirebaseMessagingService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.tetris.modern.rl.notifications.TetrisNotificationManager getNotificationManager() {
        return null;
    }
    
    public final void setNotificationManager(@org.jetbrains.annotations.NotNull
    com.tetris.modern.rl.notifications.TetrisNotificationManager p0) {
    }
    
    @java.lang.Override
    public void onNewToken(@org.jetbrains.annotations.NotNull
    java.lang.String token) {
    }
    
    @java.lang.Override
    public void onMessageReceived(@org.jetbrains.annotations.NotNull
    com.google.firebase.messaging.RemoteMessage message) {
    }
    
    private final void handleNewRecordNotification(java.util.Map<java.lang.String, java.lang.String> data) {
    }
    
    private final void handleBeatenByFriendNotification(java.util.Map<java.lang.String, java.lang.String> data) {
    }
    
    private final void handleDailyChallengeNotification(java.util.Map<java.lang.String, java.lang.String> data) {
    }
    
    private final void handleAchievementNotification(java.util.Map<java.lang.String, java.lang.String> data) {
    }
    
    private final void handleTournamentNotification(java.util.Map<java.lang.String, java.lang.String> data) {
    }
    
    private final void handleGenericNotification(com.google.firebase.messaging.RemoteMessage message) {
    }
    
    private final void sendTokenToServer(java.lang.String token) {
    }
}