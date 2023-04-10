package ru.alishev.springcourse;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {
    private List<Music> musicList = new ArrayList<>();
    public List<Music> getMusicList() {
        return musicList;
    }
    public void setMusicList(List<Music> musicList) {
        this.musicList = musicList;
    }

    private String name;
    public String getName() {        return name;    }
    public void setName(String name) {
        this.name = name;
    }
    private int volume;
    public int getVolume() {
        return volume;
    }
    public void setVolume(int volume) {
        this.volume = volume;
    }

    //IoC
    public MusicPlayer(){}

    /*
    private Music music;
    public MusicPlayer(Music music){
        this.music = music;
    }
    public void setMusic(Music music) {
        this.music = music;
    } */
    public void playMusic(){
        //System.out.println("Playing: " + music.getSong());
        for (Music msc : musicList) {
            System.out.println("Playing: " + msc.getSong());
        }
    }
}
