/**
 * The custom Application class for the CookBook app.
 * Configures Coil for image loading, specifically supporting video frame decoding.
 */
package com.example.cookbook

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.VideoFrameDecoder

class CookBookApplication : Application(), ImageLoaderFactory {
    /**
     * Creates and configures the Coil ImageLoader with support for video frames.
     */
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .components {
                add(VideoFrameDecoder.Factory())
            }
            .build()
    }
}
