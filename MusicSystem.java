/**
* 
*
* @author Dmitriy Cherenkov
*/

import java.util.List;
import java.util.Arrays;

//класс, представляющий песни
class Song {
    private String artist;
    private String title;
    
    public Song(String artist, String title) {
        this.artist = artist;
        this.title = title;
    }
    
    public String getArtist() {
        return artist;
    }
    
    public String getTitle() {
        return title;
    }
}

//класс, представляющий звуковоспроизводящее устройство
class SoundDevice {
    private String name;
    private List<String> supportedMediaTypes;
    
    public SoundDevice(String name, List<String> supportedMediaTypes) {
        this.name = name;
        this.supportedMediaTypes = supportedMediaTypes;
    }
    
    public void playSong(Song song, String mediaType) {
        if (!supportedMediaTypes.contains(mediaType)) {
            System.out.println("Ошибка: неподдерживаемый тип " + mediaType);
            return;
        }
        System.out.println("Играет " + song.getTitle() + " от " + song.getArtist() + 
                           " на " + name + " с использованием " + mediaType);
    }
}

public class MusicSystem {
    public static void main(String[] args) {
        //создаем звуковоспроизводящие устройства
        SoundDevice vinylPlayer = new SoundDevice("Vinyl player", Arrays.asList("vinyl"));
        SoundDevice cdPlayer = new SoundDevice("CD player", Arrays.asList("cd"));
        SoundDevice universalPlayer = new SoundDevice("Universal player", Arrays.asList("vinyl", "cd", "mp3"));
        
        //создаем песни
        Song song1 = new Song("Исполнитель 1", "Название 1");
        Song song2 = new Song("Исполнитель 2", "Название 2");
        Song song3 = new Song("Исполнитель 3", "Название 3");
        
        //воспроизводим песни на разных устройствах
        vinylPlayer.playSong(song1, "cd"); // ошибка, неподдерживаемый носитель
        cdPlayer.playSong(song2, "cd");
        universalPlayer.playSong(song1, "vinyl");
        universalPlayer.playSong(song2, "cd");
        universalPlayer.playSong(song3, "mp3");
    }
}
