package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esri.arcgisruntime.ArcGISRuntimeEnvironment
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.geometry.SpatialReferences
import com.esri.arcgisruntime.layers.ArcGISSceneLayer
import com.esri.arcgisruntime.mapping.*
import com.esri.arcgisruntime.mapping.view.Camera
import com.esri.arcgisruntime.mapping.view.LayerSceneProperties
import com.esri.arcgisruntime.mapping.view.SceneView
import com.esri.arcgisruntime.portal.Portal
import com.esri.arcgisruntime.portal.PortalItem

import com.example.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val sceneView: SceneView by lazy {
        activityMainBinding.sceneView
    }
    private fun setupScene() {

        val elevationSource =
            ArcGISTiledElevationSource("https://elevation3d.arcgis.com/arcgis/rest/services/WorldElevation3D/Terrain3D/ImageServer")
        val surface = Surface(listOf(elevationSource))

        val portal = Portal("https://www.arcgis.com/")
        val portalItem = PortalItem(portal, "2342ab7928834076a1240fb93c60e978")
        val lyonBuildings = ArcGISSceneLayer(portalItem).apply {
            surfacePlacement = LayerSceneProperties.SurfacePlacement.ABSOLUTE
            altitudeOffset = 6.0
        }

        val scene = ArcGISScene(Basemap.createImagery()).apply {
            baseSurface = surface
            operationalLayers.add(lyonBuildings)
        }

        sceneView.scene = scene

        // Camera(latitude, longitude, altitude, heading, pitch, roll)
        sceneView.setViewpointCamera(Camera(45.7556, 4.8431, 270.0, 0.0, 75.0, 0.0))

//        val scene = ArcGISScene(BasemapStyle.ARCGIS_HILLSHADE_DARK)
//        sceneView.scene = scene
//        val elevationSource = ArcGISTiledElevationSource("https://elevation3d.arcgis.com/arcgis/rest/services/WorldElevation3D/Terrain3D/ImageServer")
//        val surface = Surface(listOf(elevationSource))
//        surface.elevationExaggeration = 2.5f
//        scene.baseSurface = surface
//       // val cameraLocation = Point(-118.794, 33.909, 5330.0, SpatialReferences.getWgs84())
//        val camera = Camera(51.50843075, -0.098585086, 0.658117563,0.0,0.0,0.5)
//
//        sceneView.setViewpointCamera(camera)
    }
    private fun setApiKeyForApp(){
        // set your API key
        // Note: it is not best practice to store API keys in source code. The API key is referenced
        // here for the convenience of this tutorial.

        ArcGISRuntimeEnvironment.setApiKey("AAPK4c8c483877ec4772a7d4c7850baf5f855kYYM-5NZRpPEZYinQgoUPW5xVueJqiItjjkqqdTQTLwQ0rETBkrTBI7JGdTjPm7")

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)
        setApiKeyForApp()
        setupScene()
    }
    override fun onPause() {
        sceneView.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        sceneView.resume()
    }

    override fun onDestroy() {
        sceneView.dispose()
        super.onDestroy()
    }
}