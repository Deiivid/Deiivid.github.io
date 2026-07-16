import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLVideoElement

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.getElementById("root")!!) {
        PortfolioApp()
    }
}

@Composable
private fun PortfolioApp() {
    var introProgress by remember { mutableFloatStateOf(0f) }
    var playbackKey by remember { mutableIntStateOf(0) }
    val introVideo = remember {
        document.getElementById("portfolio-intro") as? HTMLVideoElement
    }

    DisposableEffect(introVideo, playbackKey) {
        if (introVideo == null) {
            introProgress = 1f
            onDispose {}
        } else {
            var animationFrameId = 0
            var revealFrameId = 0
            var firstFrameAt: Double? = null
            var lastAdvanceAt: Double? = null
            var lastMediaTime = 0.0
            var portfolioShown = false

            fun showPortfolio() {
                if (portfolioShown) return
                portfolioShown = true
                introProgress = 1f
                revealFrameId = window.requestAnimationFrame {
                    revealFrameId = window.requestAnimationFrame {
                        introVideo.style.opacity = "0"
                        introVideo.style.setProperty("pointer-events", "none")
                    }
                }
            }

            lateinit var updateProgress: (Double) -> Unit
            updateProgress = { frameTime ->
                if (firstFrameAt == null) {
                    firstFrameAt = frameTime
                    lastAdvanceAt = frameTime
                }

                val mediaTime = introVideo.currentTime
                if (mediaTime > lastMediaTime + 0.002) {
                    lastMediaTime = mediaTime
                    lastAdvanceAt = frameTime
                }

                val elapsed = frameTime - (firstFrameAt ?: frameTime)
                val withoutAdvance = frameTime - (lastAdvanceAt ?: frameTime)
                val autoplayBlocked = elapsed >= 1_800.0 && mediaTime < 0.03 && introVideo.paused
                val playbackStalled = withoutAdvance >= 4_500.0 && !introVideo.ended
                val playbackFailed = introVideo.error != null

                if (
                    introVideo.ended ||
                    autoplayBlocked ||
                    playbackStalled ||
                    playbackFailed
                ) {
                    showPortfolio()
                } else {
                    animationFrameId = window.requestAnimationFrame(updateProgress)
                }
            }
            introVideo.style.opacity = "1"
            introVideo.style.setProperty("pointer-events", "auto")
            animationFrameId = window.requestAnimationFrame(updateProgress)
            introVideo.play()

            onDispose {
                window.cancelAnimationFrame(animationFrameId)
                window.cancelAnimationFrame(revealFrameId)
            }
        }
    }

    App(
        introProgress = introProgress,
        onReplayIntro = {
            introProgress = 0f
            playbackKey++
            introVideo?.style?.opacity = "1"
            introVideo?.style?.setProperty("pointer-events", "auto")
            introVideo?.currentTime = 0.0
            introVideo?.play()
        }
    )
}
