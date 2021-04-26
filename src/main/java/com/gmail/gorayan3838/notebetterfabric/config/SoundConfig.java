package com.gmail.gorayan3838.notebetterfabric.config;

import com.gmail.gorayan3838.notebetterfabric.NoteBetterFabric;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = NoteBetterFabric.modID)
public class SoundConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    SoundConfig.Mapping[] mappings = new SoundConfig.Mapping[]{new Mapping()};

    public static class Mapping {
        private String block = "minecraft:air";
        private Sound sound = new Sound();

        public String getBlock() {
            return block;
        }

        public Sound getSound() {
            return sound;
        }
    }

    public static class Sound {
        private String sound = "minecraft:sound";
        private float volume = 1.0f;
        private float pitch = 1.0f;

        public String getSound() {
            return sound;
        }

        public float getVolume() {
            return volume;
        }

        public float getPitch() {
            return pitch;
        }
    }

    public Mapping[] getMappings() {
        return mappings;
    }
}
