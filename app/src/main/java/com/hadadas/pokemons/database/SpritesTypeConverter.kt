package com.hadadas.pokemons.database

import androidx.room.TypeConverter
import com.hadadas.pokemons.domain.Sprites
import org.json.JSONObject

class SpritesTypeConverter {

    @TypeConverter
    fun fromSprites(sprites: Sprites): String {
        return JSONObject()
            .apply {
                put("frontDefault", sprites.frontDefault)
                put("backDefault", sprites.backDefault)
            }
            .toString()
    }

    @TypeConverter
    fun toSprites(spritesJson: String): Sprites {
        val json = JSONObject(spritesJson)
        return Sprites(json.optString("frontDefault"), json.optString("backDefault"))
    }
}