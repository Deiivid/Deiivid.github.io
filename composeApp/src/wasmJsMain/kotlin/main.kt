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

private const val INTRO_DURATION_MS = 8_200.0

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

    DisposableEffect(introLayer, playbackKey) {
        if (introLayer == null) {
            introProgress = 1f
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
                introProgress = (elapsed / INTRO_DURATION_MS).toFloat().coerceIn(0f, 0.994f)

                if (elapsed >= INTRO_DURATION_MS) {
                    showPortfolio()
                } else {
                    animationFrameId = window.requestAnimationFrame(updateProgress)
                }
            }
            introLayer.style.opacity = "1"
            introLayer.style.setProperty("pointer-events", "auto")
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
            introLayer?.style?.display = "none"
            introLayer?.style?.opacity = "1"
            introLayer?.style?.setProperty("pointer-events", "auto")
            playbackKey++
        }
    )
}
