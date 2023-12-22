package com.bonface.pokespectra.libs.dispatcher

import com.google.common.io.Resources.getResource
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.File
import java.net.HttpURLConnection

class PokeSpectraDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when(request.path) {
            "/pokemon?limit=100&offset=0" -> {
                MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(createMockResponse("pokemon_response.json"))
            }
            "/pokemon/1" -> {
                MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(createMockResponse("pokemon_details_response.json"))
            }
            "/pokemon-species/1" -> {
                MockResponse().setResponseCode(HttpURLConnection.HTTP_OK)
                    .setBody(createMockResponse("pokemon_species_response.json"))
            }
            else -> throw IllegalArgumentException("Path not found: ${request.path}")
        }
    }

    private fun createMockResponse(filePath: String) = String(File(getResource(filePath).path).readBytes())

}