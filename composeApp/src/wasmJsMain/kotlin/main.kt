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
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLImageElement

private const val INTRO_DURATION_MS = 8_200.0

private data class DeskCollectiblesPose(
    val timeSeconds: Double,
    val left: Double,
    val top: Double,
    val width: Double
)

private val deskCollectiblesTimeline = listOf(
    DeskCollectiblesPose(0.0, left = 0.302, top = 0.418, width = 0.106),
    DeskCollectiblesPose(1.394, left = 0.295, top = 0.422, width = 0.108),
    DeskCollectiblesPose(2.378, left = 0.175, top = 0.360, width = 0.100),
    DeskCollectiblesPose(3.198, left = 0.175, top = 0.360, width = 0.100),
    DeskCollectiblesPose(4.182, left = 0.175, top = 0.438, width = 0.090),
    DeskCollectiblesPose(4.838, left = 0.175, top = 0.438, width = 0.090),
    DeskCollectiblesPose(5.822, left = 0.052, top = 0.536, width = 0.144),
    DeskCollectiblesPose(8.2, left = 0.052, top = 0.536, width = 0.144)
)

private val portraitDeskCollectiblesTimeline = listOf(
    DeskCollectiblesPose(0.0, left = 0.470, top = 0.420, width = 0.055),
    DeskCollectiblesPose(1.394, left = 0.470, top = 0.420, width = 0.055),
    DeskCollectiblesPose(2.378, left = 0.470, top = 0.360, width = 0.050),
    DeskCollectiblesPose(3.198, left = 0.470, top = 0.360, width = 0.050),
    DeskCollectiblesPose(4.182, left = 0.470, top = 0.440, width = 0.050),
    DeskCollectiblesPose(4.838, left = 0.470, top = 0.440, width = 0.050),
    DeskCollectiblesPose(5.822, left = 0.470, top = 0.570, width = 0.060),
    DeskCollectiblesPose(8.2, left = 0.470, top = 0.570, width = 0.060)
)

private fun updateDeskCollectibles(
    image: HTMLImageElement?,
    mediaTimeSeconds: Double
) {
    if (image == null) return

    val timeline = if (window.innerHeight > window.innerWidth) {
        portraitDeskCollectiblesTimeline
    } else {
        deskCollectiblesTimeline
    }
    val first = timeline.first()
    val last = timeline.last()
    val time = mediaTimeSeconds.coerceIn(first.timeSeconds, last.timeSeconds)
    val endIndex = timeline.indexOfFirst { time <= it.timeSeconds }
        .takeIf { it >= 0 }
        ?: timeline.lastIndex
    val end = timeline[endIndex]
    val start = timeline[(endIndex - 1).coerceAtLeast(0)]
    val duration = end.timeSeconds - start.timeSeconds
    val linearProgress = if (duration <= 0.0) 1.0 else (time - start.timeSeconds) / duration
    val progress = linearProgress * linearProgress * (3.0 - 2.0 * linearProgress)

    fun lerp(from: Double, to: Double) = from + (to - from) * progress

    image.style.left = "${lerp(start.left, end.left) * 100.0}%"
    image.style.top = "${lerp(start.top, end.top) * 100.0}%"
    image.style.width = "${lerp(start.width, end.width) * 100.0}%"
}

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
    val introLayer = remember {
        document.getElementById("portfolio-intro") as? HTMLElement
    }
    val deskCollectibles = remember {
        document.getElementById("desk-collectibles") as? HTMLImageElement
    }

    DisposableEffect(introLayer, deskCollectibles, playbackKey) {
        if (introLayer == null) {
            introProgress = 1f
            updateDeskCollectibles(deskCollectibles, deskCollectiblesTimeline.last().timeSeconds)
            onDispose {}
        } else {
            var animationFrameId = 0
            var revealFrameId = 0
            var restartFrameId = 0
            var firstFrameAt: Double? = null
            var portfolioShown = false

            fun showPortfolio() {
                if (portfolioShown) return
                portfolioShown = true
                introProgress = 1f
                updateDeskCollectibles(deskCollectibles, deskCollectiblesTimeline.last().timeSeconds)
                revealFrameId = window.requestAnimationFrame {
                    revealFrameId = window.requestAnimationFrame {
                        introLayer.style.opacity = "0"
                        introLayer.style.setProperty("pointer-events", "none")
                    }
                }
            }

            lateinit var updateProgress: (Double) -> Unit
            updateProgress = { frameTime ->
                if (firstFrameAt == null) {
                    firstFrameAt = frameTime
                }

                val elapsed = frameTime - (firstFrameAt ?: frameTime)
                val elapsedSeconds = elapsed / 1_000.0
                introProgress = (elapsed / INTRO_DURATION_MS).toFloat().coerceIn(0f, 0.994f)
                updateDeskCollectibles(deskCollectibles, elapsedSeconds)

                if (elapsed >= INTRO_DURATION_MS) {
                    showPortfolio()
                } else {
                    animationFrameId = window.requestAnimationFrame(updateProgress)
                }
            }
            introLayer.style.opacity = "1"
            introLayer.style.setProperty("pointer-events", "auto")
            updateDeskCollectibles(deskCollectibles, 0.0)
            restartFrameId = window.requestAnimationFrame {
                introLayer.style.display = "block"
                animationFrameId = window.requestAnimationFrame(updateProgress)
            }

            onDispose {
                window.cancelAnimationFrame(animationFrameId)
                window.cancelAnimationFrame(revealFrameId)
                window.cancelAnimationFrame(restartFrameId)
            }
        }
    }

    App(
        introProgress = introProgress,
        onReplayIntro = {
            introProgress = 0f
            updateDeskCollectibles(deskCollectibles, 0.0)
            introLayer?.style?.display = "none"
            introLayer?.style?.opacity = "1"
            introLayer?.style?.setProperty("pointer-events", "auto")
            playbackKey++
        }
    )
}
