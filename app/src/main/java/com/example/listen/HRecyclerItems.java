package com.example.listen;

public class HRecyclerItems {
    String songName, songComposedBy, songUrl, songImageUrl;

    public HRecyclerItems() {
    }

    public HRecyclerItems(String songName, String songComposedBy, String songUrl, String songImageUrl) {
        this.songName = songName;
        this.songComposedBy = songComposedBy;
        this.songUrl = songUrl;
        this.songImageUrl = songImageUrl;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongComposedBy() {
        return songComposedBy;
    }

    public void setSongComposedBy(String songComposedBy) {
        this.songComposedBy = songComposedBy;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getSongImageUrl() {
        return songImageUrl;
    }

    public void setSongImageUrl(String songImageUrl) {
        this.songImageUrl = songImageUrl;
    }
}
