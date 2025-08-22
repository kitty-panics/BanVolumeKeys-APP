package kitty.panics.banvolumekeys

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.media.AudioManager
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent

class BanVolumeAllKeysService : AccessibilityService() {

    private lateinit var audioManager: AudioManager
    private var originalVolume = 0

    override fun onServiceConnected() {
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        originalVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        val info = AccessibilityServiceInfo().apply {
            eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            flags = AccessibilityServiceInfo.FLAG_REQUEST_FILTER_KEY_EVENTS
            notificationTimeout = 100
        }
        this.serviceInfo = info
    }

    override fun onKeyEvent(event: KeyEvent): Boolean {
        return when {
            event.keyCode == KeyEvent.KEYCODE_VOLUME_DOWN && event.action == KeyEvent.ACTION_DOWN -> true
            event.keyCode == KeyEvent.KEYCODE_VOLUME_UP && event.action == KeyEvent.ACTION_DOWN -> true
            else -> super.onKeyEvent(event)
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
    }

    override fun onInterrupt() {
    }
}